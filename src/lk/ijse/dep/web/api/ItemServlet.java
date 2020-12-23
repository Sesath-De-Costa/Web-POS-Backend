package lk.ijse.dep.web.api;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.dep.web.model.Item;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ItemServlet", urlPatterns = "/items")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");
        resp.setContentType("application/json");

        try (Connection connection = cp.getConnection()) {
            PrintWriter out = resp.getWriter();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item" + ((code != null) ? " WHERE code=?" : ""));
            if (code != null) {
                pstm.setObject(1, code);
            }
            ResultSet rst = pstm.executeQuery();
            List<Item> itemList = new ArrayList<>();
            while (rst.next()) {
                code = rst.getString(1);
                String description = rst.getString(2);
                BigDecimal unitPrice = rst.getBigDecimal(4);
                int qty = rst.getInt(3);
                itemList.add(new Item(code, description, qty, unitPrice));
            }

            if (code != null && itemList.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                Jsonb jsonb = JsonbBuilder.create();
                out.println(jsonb.toJson(itemList));
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");
        if (code == null || !code.matches("I\\d{3}")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");
        try (Connection connection = cp.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
            pstm.setObject(1, code);
            if (pstm.executeQuery().next()) {
                pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
                pstm.setObject(1, code);
                boolean success = pstm.executeUpdate() > 0;
                if (success) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException throwables) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throwables.printStackTrace();
        }
    }
}
