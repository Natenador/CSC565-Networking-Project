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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natha on 10/16/2016.
 */
public class DeviceDao {

    private Context context;
    private SQLiteDatabase db;
    private List<Device> deviceList = new ArrayList<Device>();
    Device device;

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
                ContentValues deviceToAdd = new ContentValues();
                deviceToAdd.put(HouseHubDatabase.DEVICE_NAME, device.getName());
                deviceToAdd.put(HouseHubDatabase.DEVICE_IP_ADDRESS, device.getIpAddress());
                deviceToAdd.put(HouseHubDatabase.DEVICE_APP_NAME, device.getAppName());
                deviceToAdd.put(HouseHubDatabase.DEVICE_CONNECTED, device.isConnected());
                db.insert(HouseHubDatabase.DEVICE_TABLE, null, deviceToAdd);
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

    private class GetOneDevice extends AsyncTask<Integer, Void, Boolean> {

        protected Boolean doInBackground(Integer... deviceId) {
            try {
                HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
                db = houseHubDatabase.getReadableDatabase();
                Cursor cursor = db.query(HouseHubDatabase.DEVICE_TABLE, HouseHubDatabase.DEVICE_ARRAY, HouseHubDatabase.DEVICE_ID + "=?", new String[] {Integer.toString(deviceId[0])}, null, null, null);
                if(cursor.moveToFirst()) {
                    device.setId(cursor.getInt(0));
                    device.setName(cursor.getString(1));
                    device.setIpAddress(cursor.getString(2));
                    device.setSocketNumber(cursor.getInt(3));
                    device.setAppName(cursor.getString(4));
                    device.setConnected(cursor.getInt(5));
                }
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

    private class GetDevices extends AsyncTask<Void, Void, Boolean> {

        protected Boolean doInBackground(Void... nothing) {
            try {
                HouseHubDatabase houseHubDatabase = new HouseHubDatabase(context);
                db = houseHubDatabase.getReadableDatabase();
                Cursor cursor = db.query(HouseHubDatabase.DEVICE_TABLE, HouseHubDatabase.DEVICE_ARRAY, null, null, null, null, HouseHubDatabase.DEVICE_NAME);
                if(cursor.isBeforeFirst()) {
                    while(cursor.moveToNext()) {
                        Device device = new Device();
                        device.setId(cursor.getInt(0));
                        device.setName(cursor.getString(1));
                        device.setIpAddress(cursor.getString(2));
                        device.setSocketNumber(cursor.getInt(3));
                        device.setAppName(cursor.getString(4));
                        device.setConnected(cursor.getInt(5));
                        deviceList.add(device);
                    }
                }
                db.close();
                return true;
            }
            catch(SQLiteException sqle) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {

            if (!success) {
                Toast.makeText(context, "There was a problem getting devices.", Toast.LENGTH_SHORT).show();
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

    public List<Device> getDeviceList() {
        GetDevices getDevices = new GetDevices();
        getDevices.execute();
        return deviceList;
    }

    public Device getDevice(int id) {
        GetOneDevice getOneDevice = new GetOneDevice();
        getOneDevice.execute(id);
        return device;
    }
}
