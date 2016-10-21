package com.example.natha.househub.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natha.househub.Domain.Device;
import com.example.natha.househub.R;
import com.example.natha.househub.activity.EditDevice;
import com.example.natha.househub.dao.DeviceDao;
import com.example.natha.househub.database.HouseHubDatabase;
import com.example.natha.househub.util.DeviceUtil;

/**
 * Created by natha on 10/16/2016.
 */
public class DeviceAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    public DeviceAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.device_layout, parent, false);
    }


    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        final Device device = DeviceUtil.generateDeviceFromCursor(cursor);
        TextView name = (TextView) view.findViewById(R.id.connection_device);
        name.setText(device.getName());
        TextView app = (TextView) view.findViewById(R.id.connection_app);
        app.setText("[" + device.getAppName() + "]");

        final Switch connected = (Switch) view.findViewById(R.id.connect);
        if(device.isConnected()) {
            connected.setChecked(true);
        }
        else {
            connected.setChecked(false);
        }

        connected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println(device.getAppName() + " - Connected? " + (isChecked ? "yes" : "no"));
                if(buttonView.isPressed()) {
                    if (isChecked) {
                        device.setConnected(1);
                    } else {
                        device.setConnected(0);
                    }
                    DeviceDao deviceDao = new DeviceDao(context);
                    deviceDao.updateDevice(device);
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {  //if list item is clicked, go to edit screen

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditDevice.class);  //go to EditDevice activity
                intent.putExtra(HouseHubDatabase.DEVICE_ID, cursor.getInt(cursor.getColumnIndex(HouseHubDatabase.DEVICE_ID)));
                context.startActivity(intent);
            }
        });
    }
}
