package delta.soen390.mapsters.Services;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;


public class NotificationService {

    public static int id;
    protected Context mContext;

    public NotificationService(Context context){
        mContext = context;
    }

    public NotificationCompat.Builder getBuilder(){
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(mContext);
        id++;
        return builder;
    }

    public void notify(NotificationCompat.Builder builder){
        NotificationManager manager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());
    }
}
