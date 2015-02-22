package delta.soen390.mapsters.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;

/**
 * Created by Felicia on 2015-02-22.
 */
public class CalendarEventNotification {

    private Context mContext;

    public CalendarEventNotification(Context context) {
        mContext = context;
        createNotification();
    }

    private void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        Intent i = new Intent(mContext, MapsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(mContext, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);

        // Sets the ticker text
        builder.setTicker("New event!");

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.directions_icon);

        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.notification);

        // For now just show when the notification was create.
        final String time = DateFormat.getTimeInstance().format(new Date()).toString();
        final String text = mContext.getResources().getString(R.string.collapsed, time);
        contentView.setTextViewText(R.id.textView, text);

        notification.contentView = contentView;

        // Show expanded view of notification
        if (Build.VERSION.SDK_INT >= 16) {
            RemoteViews expandedView =
                    new RemoteViews(mContext.getPackageName(), R.layout.notification_expanded);
            notification.bigContentView = expandedView;
        }

        // To show the notification
        NotificationManager nm = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        nm.notify(0, notification);
    }
}
