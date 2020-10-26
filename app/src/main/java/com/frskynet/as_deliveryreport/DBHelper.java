package com.frskynet.as_deliveryreport;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by F R Summit on 21th October,2020
 * Simplexhub Limited
 * frsummit@simplexhub.com
 */
class DBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "AS_DeliveryReport.db";
    final static int DB_VERSION = 2;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
        onCreate(this.getWritableDatabase());
        System.out.println("Constructor got called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY;
        QUERY = "CREATE TABLE IF NOT EXISTS DELIVERY_MAN (" +
                                "DELIVERY_MAN_TABLE_ID VARCHAR(40) PRIMARY KEY," +
                                "NAME VARCHAR(50)," +
                                "EMAIL VARCHAR(40)," +
                                "PHONE VARCHAR(15)," +
                                "ADDRESS VARCHAR(255)," +
                                "USERNAME VARCHAR(40)," +
                                "PASSWORD VARCHAR(30)," +
                                "IS_APPROVED VARCHAR(2)" +
                            ")";
        db.execSQL(QUERY);

        QUERY = "CREATE TABLE IF NOT EXISTS DELIVERY_REPORT (" +
                                "DELIVERY_REPORT_TABLE_ID VARCHAR(40) PRIMARY KEY," +
                                "DELIVERY_MAN_ID VARCHAR(50)," +
                                "CUSTOMER_NAME VARCHAR(50)," +
                                "CUSTOMER_NAME_OVERRIDE VARCHAR(50)," +
                                "ORDER_NUMBER VARCHAR(50)," +
                                "ORDER_NUMBER_OVERRIDE VARCHAR(50)," +
                                "ORDER_BY VARCHAR(50)," +
                                "ORDER_BY_OVERRIDE VARCHAR(50)," +
                                "ORDER_DATE VARCHAR(30)," +
                                "ORDER_DATE_OVERRIDE VARCHAR(30)," +
                                "DELIVERY_DATE VARCHAR(30)," +
                                "DELIVERY_DATE_OVERRIDE VARCHAR(30)," +
                                "DELIVERED_TO_NAME VARCHAR(50)," +
                                "DELIVERED_TO_NAME_OVERRIDE VARCHAR(50)," +
                                "STATUS VARCHAR(50)," +
                                "COMMENT VARCHAR(255)," +
                                "IMAGE_URL VARCHAR(255)," +
                                "SIGNATURE_URL VARCHAR(255)"
                            +")";
        db.execSQL(QUERY);
    }

    public void addDeliveryManDetails(DeliveryMan deliveryMan) {
        String QUERY = "INSERT INTO DELIVERY_MAN VALUES ('"
                                + deliveryMan.getId() + "', '"
                                + deliveryMan.getName() + "', '"
                                + deliveryMan.getEmail() + "', '"
                                + deliveryMan.getPhone() + "', '"
                                + deliveryMan.getAddress() + "', '"
                                + deliveryMan.getUsername() + "', '"
                                + deliveryMan.getPassword() + "', '"
                                + deliveryMan.getIsApproved() + "');";
        this.getWritableDatabase().execSQL(QUERY);
    }

    public void addDeliveryReport(Report report) {
        String QUERY = "INSERT INTO DELIVERY_REPORT VALUES ('"
                                + report.getId() + "', '"
                                + report.getDeliveryManId() + "', '"
                                + report.getCustomerName() + "', '"
                                + report.getCustomerNameOverride() + "', '"
                                + report.getOrderNumber() + "', '"
                                + report.getOrderNumberOverride() + "', '"
                                + report.getOrderBy() + "', '"
                                + report.getOrderByOverride() + "', '"
                                + report.getOrderDate() + "', '"
                                + report.getOrderDateOverride() + "', '"
                                + report.getDeliveryDate() + "', '"
                                + report.getDeliveryDateOverride() + "', '"
                                + report.getDeliveredToName() + "', '"
                                + report.getDeliveredToNameOverride() + "', '"
                                + report.getStatus() + "', '"
                                + report.getComments() + "', '"
                                + report.getImageURL() + "', '"
                                + report.getSignatureURL() + "');";
        this.getWritableDatabase().execSQL(QUERY);
    }

    public void updateDeliveryReport(Report report) {
        String QUERY = "UPDATE DELIVERY_REPORT SET "
                                + "CUSTOMER_NAME_OVERRIDE = " + "'" + report.getCustomerNameOverride() + "', "
                                + "ORDER_NUMBER_OVERRIDE = " + "'" + report.getOrderDateOverride() + "', "
                                + "ORDER_BY_OVERRIDE = " + "'" + report.getOrderByOverride() + "', "
                                + "ORDER_DATE_OVERRIDE = " + "'" + report.getOrderDateOverride() + "', "
                                + "DELIVERY_DATE_OVERRIDE = " + "'" + report.getDeliveryDateOverride() + "', "
                                + "DELIVERED_TO_NAME_OVERRIDE = " + "'" + report.getDeliveredToNameOverride() + "', "
                                + "STATUS = " + "'" + report.getStatus() + "', "
                                + "COMMENT = " + "'" + report.getComments() + "' "
                                + " WHERE DELIVERY_REPORT_TABLE_ID = " + "'" + report.getId() + "'";
        this.getWritableDatabase().execSQL(QUERY);
    }

    public void updateDeliveryReportImageUrl(String reportId, String url) {
        String QUERY = "UPDATE DELIVERY_REPORT SET "
                + "IMAGE_URL = " + "'" + url + "' "
                + " WHERE DELIVERY_REPORT_TABLE_ID = " + "'" + reportId + "'";
        this.getWritableDatabase().execSQL(QUERY);
    }

    public void updateDeliveryReportSignatureImageUrl(String reportId, String url) {
        String QUERY = "UPDATE DELIVERY_REPORT SET "
                + "SIGNATURE_URL = " + "'" + url + "' "
                + " WHERE DELIVERY_REPORT_TABLE_ID = " + "'" + reportId + "'";
        this.getWritableDatabase().execSQL(QUERY);
    }

    /*public void addDeliveryReport(ArrayList<Report> reportArrayList) {

        for (int i = 0; i< reportArrayList.size(); i++) {
            Report report = new Report();
            report = reportArrayList.get(i);
            System.out.println(">>>>>>>>>> " + report.getId());
            String QUERY = "INSERT INTO DELIVERY_REPORT VALUES ('"
                                    + report.getId() + "', '"
                                    + report.getDeliveryManId() + "', '"
                                    + report.getCustomerName() + "', '"
                                    + report.getCustomerNameOverride() + "', '"
                                    + report.getOrderNumber() + "', '"
                                    + report.getOrderNumberOverride() + "', '"
                                    + report.getOrderBy() + "', '"
                                    + report.getOrderByOverride() + "', '"
                                    + report.getOrderDate() + "', '"
                                    + report.getOrderDateOverride() + "', '"
                                    + report.getDeliveryDate() + "', '"
                                    + report.getDeliveryDateOverride() + "', '"
                                    + report.getDeliveredToName() + "', '"
                                    + report.getDeliveredToNameOverride() + "', '"
                                    + report.getStatus() + "', '"
                                    + report.getComments() + "', '"
                                    + report.getImageURL() + "', '"
                                    + report.getSignatureURL() + "');";
            this.getWritableDatabase().execSQL(QUERY);
        }
//        String QUERY = "INSERT INTO DELIVERY_REPORT VALUES ('"
//                                + report.getId() + "', '"
//                                + report.getDeliveryManId() + "', '"
//                                + report.getCustomerName() + "', '"
//                                + report.getCustomerNameOverride() + "', '"
//                                + report.getOrderNumber() + "', '"
//                                + report.getOrderNumberOverride() + "', '"
//                                + report.getOrderBy() + "', '"
//                                + report.getOrderByOverride() + "', '"
//                                + report.getOrderDate() + "', '"
//                                + report.getOrderDateOverride() + "', '"
//                                + report.getDeliveryDate() + "', '"
//                                + report.getDeliveryDateOverride() + "', '"
//                                + report.getDeliveredToName() + "', '"
//                                + report.getDeliveredToNameOverride() + "', '"
//                                + report.getStatus() + "', '"
//                                + report.getComments() + "', '"
//                                + report.getImageURL() + "', '"
//                                + report.getSignatureURL() + "');";
//        this.getWritableDatabase().execSQL(QUERY);
    }*/

    public ArrayList<DeliveryMan> getAllDeliveryMenList() {
        ArrayList<DeliveryMan> list = new ArrayList<>();
        String QUERY = "SELECT * from DELIVERY_MAN";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(QUERY, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        DeliveryMan deliveryMan = new DeliveryMan();

                        deliveryMan.setId(cursor.getString(0));
                        deliveryMan.setName(cursor.getString(1));
                        deliveryMan.setEmail(cursor.getString(2));
                        deliveryMan.setPhone(cursor.getString(3));
                        deliveryMan.setAddress(cursor.getString(4));
                        deliveryMan.setUsername(cursor.getString(5));
                        deliveryMan.setPassword(cursor.getString(6));
                        deliveryMan.setIsApproved(cursor.getString(7));

                        list.add(deliveryMan);
                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }
        return list;
    }

    public ArrayList<Report> getAllDeliveryReportList() {
        ArrayList<Report> list = new ArrayList<>();
        String QUERY = "SELECT * from DELIVERY_REPORT";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(QUERY, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Report deliveryReport = new Report();

                        deliveryReport.setId(cursor.getString(0));
                        deliveryReport.setDeliveryManId(cursor.getString(1));
                        deliveryReport.setCustomerName(cursor.getString(2));
                        deliveryReport.setCustomerNameOverride(cursor.getString(3));
                        deliveryReport.setOrderNumber(cursor.getString(4));
                        deliveryReport.setOrderNumberOverride(cursor.getString(5));
                        deliveryReport.setOrderBy(cursor.getString(6));
                        deliveryReport.setOrderByOverride(cursor.getString(7));
                        deliveryReport.setOrderDate(cursor.getString(8));
                        deliveryReport.setOrderDateOverride(cursor.getString(9));
                        deliveryReport.setDeliveryDate(cursor.getString(10));
                        deliveryReport.setDeliveryDateOverride(cursor.getString(11));
                        deliveryReport.setDeliveredToName(cursor.getString(12));
                        deliveryReport.setDeliveredToNameOverride(cursor.getString(13));
                        deliveryReport.setStatus(cursor.getString(14));
                        deliveryReport.setComments(cursor.getString(15));
                        deliveryReport.setImageURL(cursor.getString(16));
                        deliveryReport.setSignatureURL(cursor.getString(17));

                        list.add(deliveryReport);
                    } while (cursor.moveToNext());
                }
            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {}
            }
        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }
        return list;
    }

    public void removeAllTable() {
        String QUERY;
        QUERY = "DROP TABLE IF EXISTS DELIVERY_MAN";
        this.getWritableDatabase().execSQL(QUERY);
        QUERY = "DROP TABLE IF EXISTS DELIVERY_REPORT";
        this.getWritableDatabase().execSQL(QUERY);
    }

    public void removeAllFromDeliveryReport() {
        String QUERY;
        QUERY = "DELETE FROM DELIVERY_REPORT";
        this.getWritableDatabase().execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
