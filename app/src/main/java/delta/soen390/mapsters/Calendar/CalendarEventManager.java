package delta.soen390.mapsters.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Mathieu on 2/20/2015.
 */
public class CalendarEventManager {
    private CalendarEventSerializer mSerializer;

    private int mQueueSize = 5;
    private AlarmManager mAlarmManager;
    private Context mContext;


    public CalendarEventManager(Context context) {
        mContext = context;
        mSerializer = new CalendarEventSerializer(context);
        ArrayList<CalendarEvent> calendarEvents = mSerializer.getUpcomingEvents(mQueueSize);
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        processEvents(calendarEvents);
    }

    public void processEvents(ArrayList<CalendarEvent> events){
        for (CalendarEvent e : events) {
            setAlarm(e);
        }
    }

    public void setAlarm(CalendarEvent event) {
        Intent intent = new Intent(mContext,CalendarNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, intent, 0);

        mAlarmManager.set(AlarmManager.RTC,event.getStartTime().getTime(),pendingIntent);

        Log.i("alarm-setting","set this alarm "+ event.getStartTime().toLocaleString() + " " + event.getEventName());
    }
}
