package com.example.natha.househub.util;

import android.content.ContentValues;
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

    public static ContentValues generateContentValuesFromDevice(Device device, boolean includeId) {
        ContentValues deviceContent = new ContentValues();
        if(includeId) {
            deviceContent.put(HouseHubDatabase.DEVICE_ID, device.getId());
        }
        deviceContent.put(HouseHubDatabase.DEVICE_NAME, device.getName());
        deviceContent.put(HouseHubDatabase.DEVICE_IP_ADDRESS, device.getIpAddress());
        deviceContent.put(HouseHubDatabase.DEVICE_APP_NAME, device.getAppName());
        deviceContent.put(HouseHubDatabase.DEVICE_CONNECTED, device.isConnected());
        deviceContent.put(HouseHubDatabase.DEVICE_SOCKET, device.getSocketNumber());
        return deviceContent;
    }
}
