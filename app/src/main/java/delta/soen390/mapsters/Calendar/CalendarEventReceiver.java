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

/**
 * Created by Felicia on 2015-02-27.
 */
public class CalendarEventReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Calendar now = GregorianCalendar.getInstance();
        int dayOfWeek = now.get(Calendar.DATE);
        if(dayOfWeek != 1 && dayOfWeek != 7) {
            Notification notif = buildNotification();
            NotificationManager nm = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
            nm.notify(0, notif);
        }
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        Intent i = new Intent(mContext, mContext.getClass());
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(MapsActivity.class);
        stackBuilder.addNextIntent(i);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.ic_launcher);

        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.notification);

        // For now just show when the notification was create.
        String collapsedText = mContext.getResources().getString(R.string.collapsed, "Soen384");
        contentView.setTextViewText(R.id.textView, collapsedText);

        notification.contentView = contentView;

        // Show expanded view of notification
        if (Build.VERSION.SDK_INT >= 16) {
            RemoteViews expandedView =
                    new RemoteViews(mContext.getPackageName(), R.layout.notification_expanded);
            String expandedText = mContext.getResources().getString(R.string.expanded, "Soen384");
            expandedView.setTextViewText(R.id.expandedTextView, expandedText);
            notification.bigContentView = expandedView;
        }

        // TODO: pop event queue here
        // TODO: resync here (meaning populate the queue as much as they can as of after event start date.

        return notification;
    }
}
