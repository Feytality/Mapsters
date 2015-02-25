package delta.soen390.mapsters.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Mathieu on 2/21/2015.
 */
public class CalendarIntent {

    private Calendar mCalendar;
    private Activity mActivity;
    public CalendarIntent()
    {
        mCalendar = new GregorianCalendar();
    }

    public boolean pushIntent(Context context)
    {
        //Push intent
        Intent intent = buildIntent();
        if(intent == null)
            return false;
        context.startActivity(intent);
        return true;
    }

    private Intent buildIntent()
    {
        long currentTime = mCalendar.getTime().getTime();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();

        //Set the what time to display the calendar
        builder.appendPath("time");
        builder.appendPath(Long.toString(currentTime));
        return new Intent(Intent.ACTION_VIEW, builder.build())
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
