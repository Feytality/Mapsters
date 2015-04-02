package delta.soen390.mapsters.Calendar;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.NotificationService;

public class CalendarNotification extends NotificationService{

    public CalendarNotification(Context context) {
        super(context);
    }

    public void sendTest(){
        NotificationCompat.Builder builder = this.getBuilder();
        builder.setContentTitle("Upcoming Class!").
                setContentText("SOEN 390 - Lecture from 4:15 to 5:55").
                setSmallIcon(R.drawable.ic_stat_con_u_emboss).setColor(mContext.getResources().getColor(R.color.concordia_main_color));

        Intent intent = new Intent(mContext, MapsActivity.class);
        intent.putExtra("buildingCode","H");
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent).setAutoCancel(true);

        this.notify(builder);
    }

    public void sendCalendarNotification(CalendarEvent event) {
        NotificationCompat.Builder builder = this.getBuilder();
        builder.setContentTitle("Upcoming class!").setContentText(event.getEventName() + " is between " + event.getStartTime().toString()).setSmallIcon(R.drawable.ic_launcher);

        Intent intent = new Intent(mContext, MapsActivity.class);
        intent.putExtra("buildingCode",event.getBuildingCode());
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent).setAutoCancel(true);

        this.notify(builder);
    }
}
