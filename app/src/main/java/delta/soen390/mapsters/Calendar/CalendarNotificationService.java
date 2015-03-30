package delta.soen390.mapsters.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class CalendarNotificationService extends Service {

    private CalendarNotification amCalendarNotification;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        amCalendarNotification = new CalendarNotification(this.getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        amCalendarNotification.sendTest();
        return super.onStartCommand(intent, flags, startId);
    }
}
