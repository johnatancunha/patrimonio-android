package br.com.johnjohncc.patrimonio.entities;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Created by WGL003 on 30/06/2017.
 */

public class Item {

    private Integer id;
    private String title;
    private String Description;
    private String invoice;
//    private Calendar acquisitionDate;
    private Integer quantity;
    private Integer number;
    private String serialNumber;
//    private BigDecimal price;
    private String notice;
    private Integer fatherItemId;
//    private Calendar deletedAt;
//    private Calendar createdAt;
//    private Calendar updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Integer getFatherItemId() {
        return fatherItemId;
    }

    public void setFatherItemId(Integer fatherItemId) {
        this.fatherItemId = fatherItemId;
    }

//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
}
