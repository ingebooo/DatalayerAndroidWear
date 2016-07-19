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

        ArrayList<Intent> intentList = new ArrayList<Intent>();

        for(DataMap map : list){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra("user_id", map.getString("user_id"));
            intent.putExtra("route_id", map.getString("route_id"));
            intent.putExtra("latitude", map.getDouble("latitude"));
            intent.putExtra("longitude", map.getDouble("longitude"));

            intentList.add(intent);
        }


        Intent dataIntent = new Intent();

        dataIntent.setAction(Intent.ACTION_SEND);

        dataIntent.putParcelableArrayListExtra("pointsList", intentList);

        LocalBroadcastManager.getInstance(this).sendBroadcast(dataIntent);

    }
}