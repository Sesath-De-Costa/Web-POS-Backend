package lk.ijse.dep.web.model;

import java.math.BigDecimal;

/**
 * @author : Ranjith Suranga <suranga@ijse.lk>
 * @since : 12/10/20
 **/
public class Item {
    private String code;
    private String description;
    private int qty;
    private BigDecimal unitPrice;

    public Item() {
    }

    public Item(String code, String description, int qty, BigDecimal unitPrice) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnHand=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
