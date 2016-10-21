package com.example.natha.househub.validation;

import android.content.Context;

import com.example.natha.househub.Domain.Device;
import com.example.natha.househub.database.HouseHubDatabase;

import java.net.InetAddress;
import java.util.Map;

/**
 * Created by natha on 10/20/2016.
 */
public class DeviceValidator {

    private Map<String, String> device;
    private String errorMessage;

    public boolean isValid(Map<String, String> device) {
        this.device = device;
        validateDeviceName();
        validateIpAddress();
        validateSocketNumber();
        validateAppName();
        return foundError();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private boolean foundError() {
        return errorMessage == null;
    }

    private void validateDeviceName() {
        String name = device.get(HouseHubDatabase.DEVICE_NAME);
        if(HouseHubValidator.isEmpty(name)) {
            errorMessage = "Enter a Device Name.";
        }
        if(HouseHubValidator.exceedsLength(name, HouseHubDatabase.DEVICE_NAME_MAX_LENGTH)) {
            errorMessage = "The Device Name cannot be more than " + HouseHubDatabase.DEVICE_NAME_MAX_LENGTH + " characters.";
        }
    }

    private void validateIpAddress() {
        String ipAddress = device.get(HouseHubDatabase.DEVICE_IP_ADDRESS);
        if(HouseHubValidator.isEmpty(ipAddress)) {
            errorMessage = "Enter an IP Address.";
        }
        if(HouseHubValidator.exceedsLength(ipAddress, HouseHubDatabase.DEVICE_IP_MAX_LENGTH)) {
            errorMessage = "IP Address cannot be more than " + HouseHubDatabase.DEVICE_IP_MAX_LENGTH + " characters.";
        }
        //Maybe check for valid IP here later, after you figure out how to do it.
    }

    private void validateSocketNumber() {
        String socketNumber = device.get(HouseHubDatabase.DEVICE_SOCKET);
        System.out.println(socketNumber);
        if(HouseHubValidator.isEmpty(socketNumber)) {
            errorMessage = "Enter a Socket Number.";
        }
        if(HouseHubValidator.lengthIsBetween(socketNumber, HouseHubDatabase.DEVICE_SOCKET_MAX_LENGTH, HouseHubDatabase.DEVICE_SOCKET_MAX_LENGTH)) {
            errorMessage = "Socket Number must be 5 digits long.";
        }
        if(HouseHubValidator.isNotANumber(socketNumber)) {
            errorMessage = "Socket Number must be a number (duh).";
        }
        if(HouseHubValidator.isNegative(socketNumber)) {
            errorMessage = "Socket Number cannot be negative.";
        }
    }

    private void validateAppName() {
        String appName = device.get(HouseHubDatabase.DEVICE_APP_NAME);
        if(HouseHubValidator.isEmpty(appName)) {
            errorMessage = "Enter an App Name.";
        }
        if(HouseHubValidator.exceedsLength(appName, HouseHubDatabase.DEVICE_NAME_MAX_LENGTH)) {
            errorMessage = "App Name cannot be more than " + HouseHubDatabase.DEVICE_NAME_MAX_LENGTH + " characters.";
        }
    }


}
