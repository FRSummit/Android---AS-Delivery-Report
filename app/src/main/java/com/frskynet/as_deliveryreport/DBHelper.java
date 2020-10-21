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
        QUERY = "CREATE TABLE IF NOT EXISTS DELIVERY_MAN (_id varchar(40) primary key, show_date varchar(30), item_list varchar(255), item_list_quantity varchar(255))";
        db.execSQL(QUERY);
        QUERY = "CREATE TABLE IF NOT EXISTS DELIVERY_REPORT (_id varchar(40) primary key, show_date varchar(30), item_list varchar(255), item_list_quantity varchar(255))";
        db.execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
