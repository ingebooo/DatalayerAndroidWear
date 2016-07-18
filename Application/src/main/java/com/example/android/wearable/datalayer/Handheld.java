package com.example.android.wearable.datalayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ingeborgoftedal on 17/07/16.
 */
public class Handheld extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleClient;
    ArrayList<DataMap> pointData = new ArrayList<DataMap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);

        // Build a new GoogleApiClient
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }
    public void fillArrayList(){

        for (double i = 0; i < 10; i++) {
            DataMap dataMap = new DataMap();
            dataMap.putString("user_id", i + "");
            dataMap.putString("route_id", i + "");
            dataMap.putDouble("latitude", i);
            dataMap.putDouble("longitude", i);
            pointData.add(dataMap);
        }
    }
    // Connect to the data layer when the Activity starts
    @Override
    protected void onStart() {
        super.onStart();
        googleClient.connect();
    }

// Send a data object when the data layer connection is successful.

    @Override
    public void onConnected(Bundle connectionHint) {

        String WEARABLE_DATA_PATH = "/wearable_data";
        fillArrayList();
        //Requires a new thread to avoid blocking the UI
        new SendToDataLayerThread(WEARABLE_DATA_PATH, pointData).start();
    }

    // Disconnect from the data layer when the Activity stops
    @Override
    protected void onStop() {
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
        super.onStop();
    }

    // Placeholders for required connection callbacks
    @Override
    public void onConnectionSuspended(int cause) { }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) { }


    // Data layer and lifecycle implementation (Step 2)
class SendToDataLayerThread extends Thread {
    String path;
        ArrayList<DataMap> dataMapArrayList;

    // Constructor for sending data objects to the data layer
    SendToDataLayerThread(String p, ArrayList<DataMap> dataList) {
        path = p;
        dataMapArrayList = dataList;
    }

    public void run() {
        PutDataMapRequest dataMap = PutDataMapRequest
                .create(path);

        dataMap.getDataMap().putDataMapArrayList("pointsList", dataMapArrayList);

        PutDataRequest request = dataMap.asPutDataRequest();

        DataApi.DataItemResult result = Wearable.DataApi.putDataItem(googleClient, request).await();
        if (result.getStatus().isSuccess()) {
            Log.v("myTag", "DataMap: " + dataMapArrayList.size() + " sent successfully to data layer ");
        }
        else {
            // Log an error
            Log.v("myTag", "ERROR: failed to send DataMapArrayList to data layer");
        }
    }
}
}