package com.frskynet.as_deliveryreport;

/**
 * Created by F R Summit on 21th October,2020
 * Simplexhub Limited
 * frsummit@simplexhub.com
 */
public class Report {
    private String id;
    private String deliveryManId;
    private String customerName;
    private String customerNameOverride;
    private String orderNumber;
    private String orderNumberOverride;
    private String orderBy;
    private String orderByOverride;
    private String orderDate;
    private String orderDateOverride;
    private String deliveryDate;
    private String deliveryDateOverride;
    private String deliveredToName;
    private String deliveredToNameOverride;
    private String status;
    private String comments;
    private String imageURL;
    private String signatureURL;

    public Report() {
    }

    public Report(String id, String deliveryManId, String customerName, String customerNameOverride, String orderNumber, String orderNumberOverride, String orderBy, String orderByOverride, String orderDate, String orderDateOverride, String deliveryDate, String deliveryDateOverride, String deliveredToName, String deliveredToNameOverride, String status, String comments, String imageURL, String signatureURL) {
        this.id = id;
        this.deliveryManId = deliveryManId;
        this.customerName = customerName;
        this.customerNameOverride = customerNameOverride;
        this.orderNumber = orderNumber;
        this.orderNumberOverride = orderNumberOverride;
        this.orderBy = orderBy;
        this.orderByOverride = orderByOverride;
        this.orderDate = orderDate;
        this.orderDateOverride = orderDateOverride;
        this.deliveryDate = deliveryDate;
        this.deliveryDateOverride = deliveryDateOverride;
        this.deliveredToName = deliveredToName;
        this.deliveredToNameOverride = deliveredToNameOverride;
        this.status = status;
        this.comments = comments;
        this.imageURL = imageURL;
        this.signatureURL = signatureURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryManId() {
        return deliveryManId;
    }

    public void setDeliveryManId(String deliveryManId) {
        this.deliveryManId = deliveryManId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNameOverride() {
        return customerNameOverride;
    }

    public void setCustomerNameOverride(String customerNameOverride) {
        this.customerNameOverride = customerNameOverride;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumberOverride() {
        return orderNumberOverride;
    }

    public void setOrderNumberOverride(String orderNumberOverride) {
        this.orderNumberOverride = orderNumberOverride;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderByOverride() {
        return orderByOverride;
    }

    public void setOrderByOverride(String orderByOverride) {
        this.orderByOverride = orderByOverride;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDateOverride() {
        return orderDateOverride;
    }

    public void setOrderDateOverride(String orderDateOverride) {
        this.orderDateOverride = orderDateOverride;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryDateOverride() {
        return deliveryDateOverride;
    }

    public void setDeliveryDateOverride(String deliveryDateOverride) {
        this.deliveryDateOverride = deliveryDateOverride;
    }

    public String getDeliveredToName() {
        return deliveredToName;
    }

    public void setDeliveredToName(String deliveredToName) {
        this.deliveredToName = deliveredToName;
    }

    public String getDeliveredToNameOverride() {
        return deliveredToNameOverride;
    }

    public void setDeliveredToNameOverride(String deliveredToNameOverride) {
        this.deliveredToNameOverride = deliveredToNameOverride;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSignatureURL() {
        return signatureURL;
    }

    public void setSignatureURL(String signatureURL) {
        this.signatureURL = signatureURL;
    }
}
