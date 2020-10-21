package com.frskynet.as_deliveryreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
