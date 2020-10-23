package com.frskynet.as_deliveryreport;

/**
 * Created by F R Summit on 22th October,2020
 * Simplexhub Limited
 * frsummit@simplexhub.com
 */
public class Configuration {

    public static final String APP_SCRIPT_WEB_APP_URL = "https://script.google.com/macros/s/AKfycbwWoc-iUdxs1zjHqXivBse1B9g_eFs_8Gxz8zaDAWesmrGDxavd/exec";
    public static final String ADD_USER_URL = APP_SCRIPT_WEB_APP_URL;
    public static final String GET_DELIVERY_PARTNER_URL = APP_SCRIPT_WEB_APP_URL+"?action=readAllDeliveryPartner";
    public static final String GET_DELIVERY_REPORT_LIST_URL = APP_SCRIPT_WEB_APP_URL+"?action=readAllDeliveryReport";

    public static final String KEY_DELIVERY_PARTNER_ID = "Id";
    public static final String KEY_DELIVERY_PARTNER_NAME = "Name";
    public static final String KEY_DELIVERY_PARTNER_EMAIL = "Email";
    public static final String KEY_DELIVERY_PARTNER_PHONE = "Phone";
    public static final String KEY_DELIVERY_PARTNER_ADDRESS = "Address";
    public static final String KEY_DELIVERY_PARTNER_USERNAME = "Username";
    public static final String KEY_DELIVERY_PARTNER_PASSWORD = "Password";
    public static final String KEY_DELIVERY_PARTNER_IS_APPROVED = "IsApproved";

    public static final String KEY_DELIVERY_REPORT_ID = "ReportId";
    public static final String KEY_DELIVERY_REPORT_PARTNER_ID = "DeliveryPartnerId";
    public static final String KEY_DELIVERY_REPORT_CUSTOMER_NAME = "CustomerName";
    public static final String KEY_DELIVERY_REPORT_CUSTOMER_NAME_OVERRIDE = "CustomerNameOverride";
    public static final String KEY_DELIVERY_REPORT_ORDER_NUMBER = "OrderNo";
    public static final String KEY_DELIVERY_REPORT_ORDER_NUMBER_OVERRIDE = "OrderNoOverride";
    public static final String KEY_DELIVERY_REPORT_ORDER_BY = "OrderBy";
    public static final String KEY_DELIVERY_REPORT_ORDER_BY_OVERRIDE = "OrderByOverride";
    public static final String KEY_DELIVERY_REPORT_ORDER_DATE = "OrderDate";
    public static final String KEY_DELIVERY_REPORT_ORDER_DATE_OVERRIDE = "OrderDateOverride";
    public static final String KEY_DELIVERY_REPORT_DELIVERY_DATE = "DeliveryDate";
    public static final String KEY_DELIVERY_REPORT_DELIVERY_DATE_OVERRIDE = "DeliveryDateOverride";
    public static final String KEY_DELIVERY_REPORT_DELIVERY_TO_NAME = "DeliveryToName";
    public static final String KEY_DELIVERY_REPORT_DELIVERY_TO_NAME_OVERRIDE = "DeliveryToNameOverride";
    public static final String KEY_DELIVERY_REPORT_STATUS = "Status";
    public static final String KEY_DELIVERY_REPORT_COMMENT = "Comment";
    public static final String KEY_DELIVERY_REPORT_IMAGE_URL = "ImageUrl";
    public static final String KEY_DELIVERY_REPORT_SIGNATURE_URL = "SignatureUrl";

    public static final String KEY_NAME = "uName";
    public static final String KEY_IMAGE = "uImage";
    public static final String KEY_ACTION = "action";

    public static final String KEY_USERS = "records";
}
