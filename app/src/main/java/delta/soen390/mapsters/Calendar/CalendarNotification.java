package delta.soen390.mapsters.Calendar;


import android.content.Context;
import android.support.v4.app.NotificationCompat;

import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.NotificationService;

public class CalendarNotification extends NotificationService{

    public CalendarNotification(Context context) {
        super(context);
    }

    public void sendTest(){
        NotificationCompat.Builder builder = this.getBuilder();
        builder.setContentTitle("Hello There").setContentText("Take you to a room").setSmallIcon(R.drawable.ic_launcher);


        this.notify(builder);
    }
}
