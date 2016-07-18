package com.example.android.wearable.datalayer;

import java.io.Serializable;

public class Points implements Serializable {
    private int point_number;
    private String user_id;
    private String route_id;
    private double latitude;
    private double longitude;
    private int repetition;

    /*public Point(int point_number,String user_id, String route_id, double latitude, double longitude, int repetition){
        this.point_number=point_number;
        this.user_id=user_id;
        this.route_id=route_id;
        this.latitude=latitude;
        this.longitude=longitude;
        this.repetition=repetition;
    }*/

    public Points(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Points(String user_id, String route_id, double latitude, double longitude){
        this.user_id=user_id;
        this.route_id=route_id;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public Points(){

    }



    /*public int getPointNumber(){
        return point_number;
    }*/

    public String getUser_id(){
        return user_id;
    }

    public String getRoute_id(){
        return route_id;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public int getRepetition(){
        return repetition;
    }

}
