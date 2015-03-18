package delta.soen390.mapsters.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import delta.soen390.mapsters.Activities.MapsActivity;

/**
 * Created by Felicia on 2015-03-18.
 */
public class NotificationGetDirectionsListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
     //   MapsActivity lol = (MapsActivity) context;

        String location = intent.getStringExtra("EventLocation");
        if(location != null){
            Toast.makeText(context,location,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context,"nope",
                    Toast.LENGTH_LONG).show();
        }
    }
}
