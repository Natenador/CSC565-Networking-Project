package com.example.natha.househub.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natha.househub.Domain.Device;
import com.example.natha.househub.R;
import com.example.natha.househub.activity.MainActivity;
import com.example.natha.househub.dao.DeviceDao;
import com.example.natha.househub.database.HouseHubDatabase;
import com.example.natha.househub.validation.DeviceValidator;

import java.util.HashMap;
import java.util.Map;

public class DeviceEdit extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int deviceId;
    private boolean newDevice;
    private Device device;

    private EditText deviceName;
    private EditText deviceIp;
    private EditText deviceSocket;
    private EditText appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_edit);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        deviceName = (EditText) findViewById(R.id.edit_device_name);
        deviceIp = (EditText) findViewById(R.id.edit_ip_address);
        deviceSocket = (EditText) findViewById(R.id.edit_socket_num);
        appName = (EditText) findViewById(R.id.edit_app_name);

        deviceId = getIntent().getIntExtra(HouseHubDatabase.DEVICE_ID, -1);
        if(deviceId >= 0) {
            newDevice = false;
            populateEditScreen(deviceId);
        }
        else {
            newDevice = true;
            device = new Device();
            deviceSocket.setText(Integer.toString(Device.getCurrentSocketNumber()), TextView.BufferType.EDITABLE);
        }
    }

    private void populateEditScreen(int id) {
        DeviceDao deviceDao = new DeviceDao(this);
        device = deviceDao.getDevice(id);

        deviceName.setText(device.getName());
        deviceIp.setText(device.getIpAddress());
        deviceSocket.setText(Integer.toString(device.getSocketNumber()), TextView.BufferType.EDITABLE);
        appName.setText(device.getAppName());
    }

    private Device createNewDeviceFromInput() {
        device.setName(deviceName.getText().toString());
        device.setIpAddress(deviceIp.getText().toString());
        device.setAppName(appName.getText().toString());
        device.setSocketNumber(Integer.parseInt(deviceSocket.getText().toString()));
        device.setConnected(0);

        return device;
    }

    private void makeDeviceChanges() {
        device.setName(deviceName.getText().toString());
        device.setIpAddress(deviceIp.getText().toString());
        device.setAppName(appName.getText().toString());
        device.setSocketNumber(Integer.parseInt(deviceSocket.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.device_edit, menu);
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
                Map<String, String> deviceMap = new HashMap<String, String>();
                deviceMap.put(HouseHubDatabase.DEVICE_NAME, deviceName.getText().toString());
                deviceMap.put(HouseHubDatabase.DEVICE_IP_ADDRESS, deviceIp.getText().toString());
                deviceMap.put(HouseHubDatabase.DEVICE_APP_NAME, appName.getText().toString());
                deviceMap.put(HouseHubDatabase.DEVICE_SOCKET, deviceSocket.getText().toString());
                DeviceValidator deviceValidator = new DeviceValidator();
                if(deviceValidator.isValid(deviceMap)) {
                    //Add device validation here
                    DeviceDao deviceDao = new DeviceDao(this);
                    if(newDevice) {
                        deviceDao.addDevice(createNewDeviceFromInput());
                    }
                    else {
                        makeDeviceChanges();
                        deviceDao.updateDevice(device);
                    }
                    Intent mainIntent = new Intent(this, MainActivity.class);
                    startActivity(mainIntent);
                    return true;
                }
                else {
                    Toast.makeText(this, deviceValidator.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }


            default: return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.view_devices) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
