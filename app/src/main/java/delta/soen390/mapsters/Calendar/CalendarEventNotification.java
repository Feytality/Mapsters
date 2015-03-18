package delta.soen390.mapsters.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import 	java.util.Calendar;
import java.util.Date;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Utils.TimeUtil;

/**
 * Creates a notification for every single calendar event imported from the users Google calendar.
 * Created by Felicia on 2015-02-22.
 */
public class CalendarEventNotification {

    private Context mContext;
    private CalendarEvent mCalendarEvent;
    private MapsActivity mMapsActivity;
    private CalendarEventManager mCalendarEventManager;

    // incharge of popping the queue

    //update query to get events to get now + notificationbefofre

    public CalendarEventNotification(Context context, MapsActivity mapsActivity) {
        mContext = context;
        mMapsActivity = mapsActivity;
        mCalendarEventManager = new CalendarEventManager(mContext);
    }

    public CalendarEventNotification(Context context, MapsActivity mapsActivity, CalendarEvent calendarEvent) {
        mContext = context;
        mCalendarEvent = calendarEvent;
        mMapsActivity = mapsActivity;
        mCalendarEventManager = new CalendarEventManager(mContext);
    }

    /**
     * Hooks up the very next notification that is in the user's event queue.
     */
    public void handleNotifications() {
        Intent alarmIntent = new Intent(mContext, CalendarEventReceiver.class);

        // Create alarm manager to enable notification to fire at later time.
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);

        // not sure which one will work better
        //PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, alarmIntent, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the next event in the event queue.
        Calendar calendar = Calendar.getInstance();
        mCalendarEvent = mCalendarEventManager.getNextEvent();

        if(mCalendarEvent != null) {
            Date notificationTime = TimeUtil.subtractDates(mCalendarEvent.getStartTime(), mCalendarEvent.getBeforeEventNotification());
            if (notificationTime != null) {
                calendar.setTime(notificationTime);

                // Fire notification at this time. This will be caught in the receiver.
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                // could not get notification time, don't add a notification.
                System.out.println("Could not add a notification, there was no time to set.");
            }
        } else {
            // there is no next calendar event, don't show any notification.
            System.out.println("Could not add a notification, there was no time to set.");
        }
    }


    public void createNotification() {
        Intent notificationIntent = new Intent(mContext, NotificationGetDirectionsListener.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra("EventName", mCalendarEvent.getEventName());
        notificationIntent.putExtra("EventLocation", mCalendarEvent.getFullLocation());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = buildNotificationView(pendingIntent);


        // To show the notification
        NotificationManager nm = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        nm.notify(0, notification);


    }

    private Notification buildNotificationView(PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.ic_launcher);

        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        Notification notification = builder.build();

        // Inflate the notification layout as RemoteViews
        RemoteViews collapsedView = new RemoteViews(mContext.getPackageName(), R.layout.notification);

        // Show the name of the next class inside the notification
        collapsedView.setTextViewText(R.id.textView, mContext.getResources().getString(R.string.collapsed, mCalendarEvent.getEventName()));

        notification.contentView = collapsedView;

        RemoteViews expandedView = null;

        // Show expanded view of notification
        if (Build.VERSION.SDK_INT >= 16) {
            expandedView =
                    new RemoteViews(mContext.getPackageName(), R.layout.notification_expanded);
            expandedView.setTextViewText(R.id.expandedTextView, mContext.getResources().getString(R.string.expanded, mCalendarEvent.getEventName()));
            notification.bigContentView = expandedView;
        }

        if(expandedView != null) {
            expandedView.setOnClickPendingIntent(R.id.expanded_get_directions, pendingIntent);
        }


        if(collapsedView != null) {
            collapsedView.setOnClickPendingIntent(R.id.collapsed_get_directions, pendingIntent);
        }

        return notification;

    }
}
