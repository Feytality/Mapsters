/*
package delta.soen390.mapsters.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.GregorianCalendar;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;

*/
/**
 * Created by Felicia on 2015-02-27.
 *//*

public class CalendarEventReceiver extends BroadcastReceiver {

    private Context mContext;
    private CalendarEventManager mCalendarEventManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        mCalendarEventManager = new CalendarEventManager(mContext);

        Calendar now = GregorianCalendar.getInstance();
        int dayOfWeek = now.get(Calendar.DATE);
        if(dayOfWeek != 1 && dayOfWeek != 7) {

                // Pop the added notification from the event queue
                mCalendarEventManager.popNextEvent();

                // TODO: add call to resync in event manager.
            } else {
                // Could not hook up notification.
            }
        }

}
*/
