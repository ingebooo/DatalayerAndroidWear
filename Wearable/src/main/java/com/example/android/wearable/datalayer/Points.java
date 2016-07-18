package com.example.android.wearable.datalayer;

import android.os.Parcel;
import android.os.Parcelable;

public class Points implements Parcelable {
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

    protected Points(Parcel in) {
        point_number = in.readInt();
        user_id = in.readString();
        route_id = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        repetition = in.readInt();
    }

    public static final Creator<Points> CREATOR = new Creator<Points>() {
        @Override
        public Points createFromParcel(Parcel in) {
            return new Points(in);
        }

        @Override
        public Points[] newArray(int size) {
            return new Points[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(point_number);
        parcel.writeString(user_id);
        parcel.writeString(route_id);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeInt(repetition);
    }
}
