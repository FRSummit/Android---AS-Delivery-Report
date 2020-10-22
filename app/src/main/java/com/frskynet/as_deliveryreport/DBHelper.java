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
        QUERY = "CREATE TABLE IF NOT EXISTS LOGIN_DETAILS (" +
                                "LOGIN_TABLE_ID VARCHAR(40) PRIMARY KEY," +
                                "USERNAME_MAIL_PHONE VARCHAR(40)," +
                                "PASSWORD VARCHAR(30)" +
                            ")";
        db.execSQL(QUERY);

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
                                "ON_BEHALF_OF VARCHAR(50)," +
                                "ORDER_NUMBER VARCHAR(50)," +
                                "ORDER_BY VARCHAR(50)," +
                                "ORDER_DATE VARCHAR(30)," +
                                "DELIVERY_DATE VARCHAR(30)," +
                                "DELIVERED_TO_NAME VARCHAR(50)," +
                                "COMMENT VARCHAR(255)"
                            +")";
        db.execSQL(QUERY);
    }

    public void addLoginDetails(DeliveryMan deliveryMan) {
        String QUERY = "INSERT INTO LOGIN_DETAILS VALUES ('"
                                + deliveryMan.getId() + "', '"
                                + deliveryMan.getUsername() + "', '"
                                + deliveryMan.getPassword() + "');";
        this.getWritableDatabase().execSQL(QUERY);
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

    public void addDeliveryReport(Report report, String deliveryManId) {
        String QUERY = "INSERT INTO DELIVERY_REPORT VALUES ('"
                                + report.getId() + "', '"
                                + deliveryManId + "', '"
                                + report.getOnBehalfOf() + "', '"
                                + report.getOrderNumber() + "', '"
                                + report.getOrderBy() + "', '"
                                + report.getOrderDate() + "', '"
                                + report.getDeliveryDate() + "', '"
                                + report.getDeliveredToName() + "', '"
                                + report.getComments() + "');";
        this.getWritableDatabase().execSQL(QUERY);
    }

    public ArrayList<DeliveryMan> getAllLoginDetails() {
        ArrayList<DeliveryMan> list = new ArrayList<>();
        String QUERY = "SELECT * from LOGIN_DETAILS";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(QUERY, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        DeliveryMan deliveryMan = new DeliveryMan();

                        deliveryMan.setId(cursor.getString(0));
                        deliveryMan.setUsername(cursor.getString(1));
                        deliveryMan.setPassword(cursor.getString(2));

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
        Map<String, ArrayList<Report>> myMap = new HashMap<>();

        String QUERY = "SELECT * from DELIVERY_REPORT";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(QUERY, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        Report deliveryReport = new Report();

                        deliveryReport.setId(cursor.getString(0));
//                        deliveryReport.setDe(cursor.getString(1));
                        deliveryReport.setOnBehalfOf(cursor.getString(2));
                        deliveryReport.setOrderNumber(cursor.getString(3));
                        deliveryReport.setOrderBy(cursor.getString(4));
                        deliveryReport.setOrderDate(cursor.getString(5));
                        deliveryReport.setDeliveryDate(cursor.getString(6));
                        deliveryReport.setDeliveredToName(cursor.getString(7));
                        deliveryReport.setComments(cursor.getString(8));

                        list.add(deliveryReport);
                        myMap.put(cursor.getString(1), list);
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
        QUERY = "DROP TABLE IF EXISTS LOGIN_DETAILS";
        this.getWritableDatabase().execSQL(QUERY);
        QUERY = "DROP TABLE IF EXISTS DELIVERY_MAN";
        this.getWritableDatabase().execSQL(QUERY);
        QUERY = "DROP TABLE IF EXISTS DELIVERY_REPORT";
        this.getWritableDatabase().execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
