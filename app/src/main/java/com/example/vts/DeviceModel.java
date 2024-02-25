package com.example.vts;


public class DeviceModel {
    String deviceName,deviceNum,deviceImage,latitude,longitude;

    DeviceModel(){

    }

    public DeviceModel(String deviceName, String deviceNum, String deviceImage, String latitude, String longitude) {
        this.deviceName = deviceName;
        this.deviceNum = deviceNum;
        this.deviceImage = deviceImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getDeviceImage() {
        return deviceImage;
    }

    public void setDeviceImage(String deviceImage) {
        this.deviceImage = deviceImage;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
