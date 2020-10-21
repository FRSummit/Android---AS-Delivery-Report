package com.frskynet.as_deliveryreport;

/**
 * Created by F R Summit on 21th October,2020
 * Simplexhub Limited
 * frsummit@simplexhub.com
 */
public class Report {
    private String orderNumber;
    private String orderBy;
    private String orderDate;
    private String deliveryDate;
    private String deliveredToName;
    private String comments;

    public Report() {
    }

    public Report(String orderNumber, String orderBy, String orderDate, String deliveryDate, String deliveredToName, String comments) {
        this.orderNumber = orderNumber;
        this.orderBy = orderBy;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.deliveredToName = deliveredToName;
        this.comments = comments;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveredToName() {
        return deliveredToName;
    }

    public void setDeliveredToName(String deliveredToName) {
        this.deliveredToName = deliveredToName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
