package com.example.natha.househub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.natha.househub.Domain.Device;
import com.example.natha.househub.database.HouseHubDatabase;
import com.example.natha.househub.util.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natha on 10/16/2016.
 */
public class DeviceDao {

    private Context context;
    private SQLiteDatabase db;

    public DeviceDao(Context context) {
        setContext(context);
    }

    private class DeleteDevice extends AsyncTask<Integer, Void, Boolean> {

        protected Boolean doInBackground(Integer... deviceId) {
            try {
                HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
                db = houseHubDatabase.getWritableDatabase();
                db.delete(HouseHubDatabase.DEVICE_TABLE, HouseHubDatabase.DEVICE_ID + " = ?", new String[]{Integer.toString(deviceId[0])});
                db.close();
                return true;
            }
            catch(SQLiteException sqle) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {

            if (success) {
                Toast.makeText(context, "Successfully deleted the device.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "There was a problem deleting the device.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class AddDevice extends AsyncTask<Device, Void, Boolean> {

        protected Boolean doInBackground(Device... deviceArray) {
            try {
                Device device = deviceArray[0];
                HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
                db = houseHubDatabase.getWritableDatabase();
                db.insert(HouseHubDatabase.DEVICE_TABLE, null, DeviceUtil.generateContentValuesFromDevice(device, false));
                db.close();
                return true;
            }
            catch(SQLiteException sqle) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {

            if (success) {
                Toast.makeText(context, "Successfully added the device.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "There was a problem adding the device.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class UpdateDevice extends AsyncTask<Device, Void, Boolean> {
        protected Boolean doInBackground(Device... deviceArray) {
            try {
                Device device = deviceArray[0];
                HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
                db = houseHubDatabase.getWritableDatabase();

                db.update(HouseHubDatabase.DEVICE_TABLE, DeviceUtil.generateContentValuesFromDevice(device, true), HouseHubDatabase.DEVICE_ID + "=?", new String[] {Integer.toString(device.getId())});
                db.close();
                return true;
            }
            catch(SQLiteException sqle) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {

            if (success) {
                Toast.makeText(context, "Successfully updated the device.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "There was a problem updating the device.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void setContext(Context c) {
        context = c;
    }

    public void deleteDevice(int deviceId) {
        DeleteDevice deleteDevice = new DeleteDevice();
        deleteDevice.execute(deviceId);
    }

    public void addDevice(Device device) {
        AddDevice addDevice = new AddDevice();
        addDevice.execute(device);
    }

    public void updateDevice(Device device) {
        UpdateDevice updateDevice = new UpdateDevice();
        updateDevice.execute(device);
    }

    public List<Device> getDeviceList() {
        List<Device> deviceList = null;
        try {
            deviceList = new ArrayList<Device>();
            HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
            db = houseHubDatabase.getReadableDatabase();
            final Cursor cursor = db.query(HouseHubDatabase.DEVICE_TABLE, HouseHubDatabase.DEVICE_ARRAY, null, null, null, null, HouseHubDatabase.DEVICE_NAME);
            if(cursor.isBeforeFirst()) {
                while(cursor.moveToNext()) {
                    deviceList.add(DeviceUtil.generateDeviceFromCursor(cursor));
                }
            }
            db.close();
        }
        catch(SQLiteException sqle) {
            Toast.makeText(context, "There was a problem getting the device information", Toast.LENGTH_SHORT).show();
        }
        return deviceList;
    }

    public Device getCurrentConnection() {
        Device device;
        try {
            HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
            db = houseHubDatabase.getReadableDatabase();
            Cursor cursor = db.query(HouseHubDatabase.DEVICE_TABLE, HouseHubDatabase.DEVICE_ARRAY, HouseHubDatabase.DEVICE_CONNECTED + "=?", new String[] {Integer.toString(1)}, null, null, null);
            if(cursor.moveToFirst()) {
                device = DeviceUtil.generateDeviceFromCursor(cursor);
            }
            else {
                device = new Device();
                device.setAppName("None");
            }
            db.close();
        }
        catch(SQLiteException sqle) {
            Toast.makeText(context, "There was a problem getting the device information", Toast.LENGTH_SHORT).show();
            device = new Device();
            device.setAppName("Error");
        }
        return device;
    }

    public void disconnectCurrentDevice() {
        try {
            HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
            db = houseHubDatabase.getWritableDatabase();
            Cursor cursor = db.query(HouseHubDatabase.DEVICE_TABLE, HouseHubDatabase.DEVICE_ARRAY, HouseHubDatabase.DEVICE_CONNECTED + "=?", new String[] {Integer.toString(1)}, null, null, null);
            if(cursor.moveToFirst()) {
                Device device = DeviceUtil.generateDeviceFromCursor(cursor);
                device.setConnected(0);
                db.update(HouseHubDatabase.DEVICE_TABLE, DeviceUtil.generateContentValuesFromDevice(device, true), HouseHubDatabase.DEVICE_ID + "=?", new String[]{Integer.toString(device.getId())});
                Toast.makeText(context, "Found an already connected device. Disconnecting", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Did not find an already connected device.", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
        catch(SQLiteException sqle) {
            Toast.makeText(context, "There was a problem getting the device information", Toast.LENGTH_SHORT).show();
        }
    }

    public Device getDevice(int id) {
        Device device = null;
        try {
            HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
            db = houseHubDatabase.getReadableDatabase();
            Cursor cursor = db.query(HouseHubDatabase.DEVICE_TABLE, HouseHubDatabase.DEVICE_ARRAY, HouseHubDatabase.DEVICE_ID + "=?", new String[] {Integer.toString(id)}, null, null, null);
            if(cursor.moveToFirst()) {
                device = DeviceUtil.generateDeviceFromCursor(cursor);
            }
            db.close();
        }
        catch(SQLiteException sqle) {
            Toast.makeText(context, "There was a problem getting the device information", Toast.LENGTH_SHORT).show();
        }
        return device;
    }

    public boolean isSocketNumberUnique(int socketNumber) {
        try {
            HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
            db = houseHubDatabase.getReadableDatabase();
            Cursor cursor = db.query(HouseHubDatabase.DEVICE_TABLE, new String[] {HouseHubDatabase.DEVICE_SOCKET}, HouseHubDatabase.DEVICE_SOCKET + "=?", new String[] {Integer.toString(socketNumber)}, null, null, null);
            if(cursor.moveToFirst()) {
                return false;
            }
            db.close();
            return true;
        }
        catch(SQLiteException sqle) {
            Toast.makeText(context, "There was a problem getting the device information", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
