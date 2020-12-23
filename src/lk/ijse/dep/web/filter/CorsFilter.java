package lk.ijse.dep.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* With Servlets ( /* [WildCard Mapping] == / [Default Servlet] )*/
/* With Filters ( /* [WildCard Mapping] != / (http://localhost:8080/pos/ )*/
@WebFilter(filterName = "CorsFilter", urlPatterns = "/*")
public class CorsFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
        chain.doFilter(request, resp);
    }
}
