package com.example.natha.househub.util;

import android.database.Cursor;

import com.example.natha.househub.Domain.Device;
import com.example.natha.househub.database.HouseHubDatabase;

/**
 * Created by natha on 10/20/2016.
 */
public class DeviceUtil {

    public static Device generateDeviceFromCursor(Cursor cursor) {
        Device device = new Device();
        device.setId(cursor.getInt(cursor.getColumnIndex(HouseHubDatabase.DEVICE_ID)));
        device.setName(cursor.getString(cursor.getColumnIndex(HouseHubDatabase.DEVICE_NAME)));
        device.setAppName(cursor.getString(cursor.getColumnIndex(HouseHubDatabase.DEVICE_APP_NAME)));
        device.setSocketNumber(cursor.getInt(cursor.getColumnIndex(HouseHubDatabase.DEVICE_SOCKET)));
        device.setIpAddress(cursor.getString(cursor.getColumnIndex(HouseHubDatabase.DEVICE_IP_ADDRESS)));
        device.setConnected(cursor.getInt(cursor.getColumnIndex(HouseHubDatabase.DEVICE_CONNECTED)));
        return device;
    }
}
