package com.example.android.wearable.datalayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.ArrayList;

/**
 * Created by ingeborgoftedal on 17/07/16.
 */
public class ListenerService extends WearableListenerService {

    private static final String WEARABLE_DATA_PATH = "/wearable_data";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        ArrayList<DataMap> pointsList;

        for (DataEvent event : dataEvents) {

            // Check the data type

            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(WEARABLE_DATA_PATH)) {}
                DataMapItem dataItem = DataMapItem.fromDataItem (event.getDataItem());
                pointsList = dataItem.getDataMap().getDataMapArrayList("pointsList");
                //dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                Log.v("myTag", "DataMap received on watch: " + pointsList.size());
                Log.v("KLOKKA FÅR DATA", "hurra" + "");
                onPointsReceived(pointsList);
            }
        }
    }
    public void onPointsReceived(ArrayList<DataMap> list){
        ArrayList<Parcelable> parcelableArrayList = new ArrayList<Parcelable>();

        for(DataMap map : list){
            Points points = new Points();
            DataMapParcelableUtils.putParcelable(map, "user_id",points);
            DataMapParcelableUtils.putParcelable(map, "route_id", points);
            DataMapParcelableUtils.putParcelable(map, "latitude", points);
            DataMapParcelableUtils.putParcelable(map, "longitude", points);
        }


        Intent dataIntent = new Intent();
        dataIntent.setAction(Intent.ACTION_SEND);

        dataIntent.putExtras(list.get(0).toBundle());

        LocalBroadcastManager.getInstance(this).sendBroadcast(dataIntent);

    }
    public void fromDataMapToParcelable(ArrayList<DataMap> list){

    }
}