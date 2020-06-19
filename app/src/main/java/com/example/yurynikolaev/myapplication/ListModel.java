package com.example.yurynikolaev.myapplication;

public class ListModel {

    private String name, desc, imageUrl, audiodesc;
    private double latitude, longitude;

    public ListModel() {}

    public ListModel(String name, String desc, String imageUrl,String audiodesc, double latitude,double longitude) {
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.audiodesc = audiodesc;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() { return desc; }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAudioDesc() { return audiodesc; }

    public void setAudioDesc(String audiodesc) {
        this.audiodesc = audiodesc;
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
