package com.example.yurynikolaev.myapplication;

public class ListModel {

    private String name;
    private String desc;
    private String objImage;
    private double latitude;
    private double longitude;

    public ListModel() {}

    public ListModel(String name, String desc, String objImage, double latitude, double longitude) {
        this.name = name;
        this.desc = desc;
        this.objImage = objImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getObjImage() { return objImage; }

    public void setObjImage(String objImage) {
        this.objImage = objImage;
    }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
