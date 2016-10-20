package com.example.natha.househub.Domain;

/**
 * Created by natha on 10/16/2016.
 */
public class Device {

    private static int CURRENT_SOCKET_NUMBER = 13000;
    private int id;
    private String name;
    private String ipAddress;
    private int socketNumber;
    private String appName;
    private int connected;

    public static int getCurrentSocketNumber() {
        return CURRENT_SOCKET_NUMBER++;
    }

    public int getSocketNumber() {
        return socketNumber;
    }

    public void setSocketNumber(int socketNumber) {
        this.socketNumber = socketNumber;
    }

    public int isConnected() {
        return connected;
    }

    public void setConnected(int connected) {
        this.connected = connected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
