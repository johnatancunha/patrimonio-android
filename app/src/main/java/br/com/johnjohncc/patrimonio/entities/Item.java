package br.com.johnjohncc.patrimonio.entities;

import com.squareup.moshi.Json;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by WGL003 on 30/06/2017.
 */

public class Item implements Serializable{

    private Integer id;

    private String title;

    private String description;

    private String invoice;

//    @Json(name = "acquisition_date")
//    private Calendar acquisitionDate;

    private Integer quantity;

    private Integer number;

    @Json(name = "serial_number")
    private String serialNumber;
//    private BigDecimal price;

    private String notice;

    @Json(name = "father_item_id")
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
//
//    public Calendar getAcquisitionDate() {
//        return acquisitionDate;
//    }
//
//    public void setAcquisitionDate(Calendar acquisitionDate) {
//        this.acquisitionDate = acquisitionDate;
//    }
}
