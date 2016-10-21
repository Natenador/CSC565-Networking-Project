package com.example.natha.househub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natha.househub.Domain.Device;
import com.example.natha.househub.R;
import com.example.natha.househub.dao.DeviceDao;
import com.example.natha.househub.database.HouseHubDatabase;

public class EditDevice extends AppCompatActivity {

    private int deviceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        deviceId = getIntent().getIntExtra(HouseHubDatabase.DEVICE_ID, -1);
        if(deviceId >= 0) {
            populateEditScreen(deviceId);
        }
        else {
            EditText deviceSocket = (EditText) findViewById(R.id.edit_socket_num);
            deviceSocket.setText(Integer.toString(Device.getCurrentSocketNumber()), TextView.BufferType.EDITABLE);
        }
    }

    private void populateEditScreen(int id) {
        DeviceDao deviceDao = new DeviceDao(this);
        Device device = deviceDao.getDevice(id);
        EditText deviceName = (EditText) findViewById(R.id.edit_device_name);
        EditText deviceIp = (EditText) findViewById(R.id.edit_ip_address);
        EditText deviceSocket = (EditText) findViewById(R.id.edit_socket_num);
        EditText appName = (EditText) findViewById(R.id.edit_app_name);

        deviceName.setText(device.getName());
        deviceIp.setText(device.getIpAddress());
        deviceSocket.setText(Integer.toString(Device.getCurrentSocketNumber()), TextView.BufferType.EDITABLE);
        appName.setText(device.getAppName());
    }

    private Device createDeviceFromInput() {
        Device device = new Device();
        EditText deviceName = (EditText) findViewById(R.id.edit_device_name);
        EditText deviceIp = (EditText) findViewById(R.id.edit_ip_address);
        EditText deviceSocket = (EditText) findViewById(R.id.edit_socket_num);
        EditText appName = (EditText) findViewById(R.id.edit_app_name);

        device.setId(deviceId);
        device.setName(deviceName.getText().toString());
        device.setIpAddress(deviceIp.getText().toString());
        device.setAppName(appName.getText().toString());
        device.setSocketNumber(Integer.parseInt(deviceSocket.getText().toString()));
        device.setConnected(0);

        return device;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_device_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.finish:
                DeviceDao deviceDao = new DeviceDao(this);
                if(deviceId < 0) {
                    deviceDao.addDevice(createDeviceFromInput());
                }
                else {

                }
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                return true;

            default: return super.onOptionsItemSelected(item);
        }


    }

}
