package delta.soen390.mapsters.Calendar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import 	java.util.Calendar;
import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;

/**
 * Created by Felicia on 2015-02-22.
 */
public class CalendarEventNotification {

    private Context mContext;
    private CalendarEvent mCalendarEvent;
    private MapsActivity mMapsActivity;

    public CalendarEventNotification(Context context, MapsActivity mapsActivity, CalendarEvent calendarEvent) {
        mContext = context;
        mCalendarEvent = calendarEvent;
        mMapsActivity = mapsActivity;
    }

    // TODO make time aware , need to add notification list to CalendarEvent objects.
    private void makeTimeAware() {
        Intent alarmIntent = new Intent(mContext , CalendarEventNotification.class);
        // Create alarm manager to enable notification to fire at later time.
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, alarmIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 12); // To be something like CalendarEvent.getNotification.
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        // Fire notification at this time.
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        Intent i = new Intent(mContext, MapsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.ic_launcher);

        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.notification);

        // For now just show when the notification was create.
        String collapsedText = mContext.getResources().getString(R.string.collapsed, mCalendarEvent.getEventName());
        contentView.setTextViewText(R.id.textView, collapsedText);

        notification.contentView = contentView;

        // Show expanded view of notification
        if (Build.VERSION.SDK_INT >= 16) {
            RemoteViews expandedView =
                    new RemoteViews(mContext.getPackageName(), R.layout.notification_expanded);
            String expandedText = mContext.getResources().getString(R.string.expanded, mCalendarEvent.getEventName());
            expandedView.setTextViewText(R.id.expandedTextView, expandedText);
            notification.bigContentView = expandedView;
        }

        // To show the notification
        NotificationManager nm = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        nm.notify(0, notification);
    }

    // TODO
    public void handleDirectionClick() {
        final Button button = (Button) mMapsActivity.findViewById(R.id.get_directions_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO hook up directions here using the location from CalendarEvent
            }
        });
    }
}
