package com.example.android.wearable.datalayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

/**
 * Created by ingeborgoftedal on 17/07/16.
 */
public class GetDataFromHandheld extends Activity {

    private int count = 0;
    TextView textView;

    private DataMap dataMap;
    private ArrayList<DataMap> points;
    private ArrayList<Intent> intentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        textView = (TextView)findViewById(R.id.textView);


    }
    private void getData(){
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
    }
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            intentList = intent.getParcelableArrayListExtra("pointsList");
            Log.v("points på plass 8", intentList.get(8).getStringExtra("user_id"));
            // Display message in UI
            textView.setText(intentList.get(9).getStringExtra("user_id"));
        }
    }
}
