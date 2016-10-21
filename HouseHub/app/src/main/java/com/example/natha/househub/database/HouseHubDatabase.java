package com.example.natha.househub.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.natha.househub.validation.HouseHubValidator;

/**
 * Created by natha on 10/16/2016.
 */
public class HouseHubDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "house_hub";
    private static final int DB_VERSION = 1;
    public static final String DEVICE_TABLE = "DEVICE";
    public static final String DEVICE_ID = "_id";
    public static final String DEVICE_NAME = "NAME";
    public static final String DEVICE_IP_ADDRESS = "IP_ADDRESS";
    public static final String DEVICE_SOCKET = "SOCKET_NUM";
    public static final String DEVICE_APP_NAME = "APP_NAME";
    public static final String DEVICE_CONNECTED = "CONNECTED";
    public static final String[] DEVICE_ARRAY = {
            DEVICE_ID, DEVICE_NAME, DEVICE_IP_ADDRESS, DEVICE_SOCKET, DEVICE_APP_NAME, DEVICE_CONNECTED
    };

    public static final int DEVICE_NAME_MAX_LENGTH = 20;
    public static final int DEVICE_IP_MAX_LENGTH = 15;
    public static final int DEVICE_SOCKET_MAX_LENGTH = 15;

    public HouseHubDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion <1) {
            db.execSQL("CREATE TABLE " + DEVICE_TABLE + " (" +
                    DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DEVICE_NAME + " CHAR("+ DEVICE_NAME_MAX_LENGTH +") NOT NULL," +
                    DEVICE_IP_ADDRESS + " CHAR("+ DEVICE_IP_MAX_LENGTH +") NOT NULL, " +
                    DEVICE_APP_NAME + " CHAR("+ DEVICE_NAME_MAX_LENGTH +") NOT NULL," +
                    DEVICE_CONNECTED + " INTEGER NOT NULL," +
                    DEVICE_SOCKET + " INTEGER NOT NULL UNIQUE);");
        }
    }
}
