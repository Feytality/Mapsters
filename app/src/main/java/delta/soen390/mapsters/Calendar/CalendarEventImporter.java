package delta.soen390.mapsters.Calendar;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.DateUtils;

import java.util.Date;

import delta.soen390.mapsters.R;

/**
 *  The purpose of this class is to import the calendar named "School" from the user's Android
 *  calendar.
 *
 * Created by Mathieu on 2/20/2015.
 */
public class CalendarEventImporter {

    private String[] mCalendarProjections;
    private String[] mCalendarEventProjections;
    private Context mContext;

    public CalendarEventImporter(Context context)
    {
        mContext = context;
        mCalendarProjections = new String[]
                {
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.ACCOUNT_TYPE
                };

        mCalendarEventProjections = new String[]
                {
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.DTEND,
                        CalendarContract.Events.EVENT_LOCATION,
                };
    }

    /**
     *  Retrieves the calendar ID of the user's calendar named "School"
     *
     *  @return ID of school calendar
     */
    private long getCalendarId()
    {
        //Projection are basically the columns that you are fetching from the calendar database.
        //For each string added, a column will be added at that index location
        //The columns describes CALENDAR characteristics
        String[] projection =
                new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.ACCOUNT_TYPE};

        //Emits the db query, returns array of calendar which fits the query.
        //Each calendars contains columns passed in the projection String array.
        Cursor calCursor = mContext.getContentResolver().query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                null,
                null,
                CalendarContract.Calendars._ID + " ASC");

        if(calCursor == null) {
            return -1;
        }

        //I want to know which column contains the calendar name attribute
        //hint: It's column 1!
        int nameColumnIndex = calCursor.getColumnIndex("name");

        //This will give the me the id column!
        int idColumnIndex = calCursor.getColumnIndex("_id");

        String mapsterCalendarName = mContext.getResources().getString(R.string.mapsters_calendar_name);

        //Iterate through all of the received calendar values
        if (calCursor.moveToFirst()) {
            do {

                //Get the calendar id
                long id = calCursor.getLong(idColumnIndex);

                //Fetch the name of the calendar pointed to by the calendar cursor
                String calendarName = calCursor.getString(nameColumnIndex);

                //I wanna fnetch TestCalendar here, so I just hardcoded this right in!
                if(calendarName != null && calendarName.compareTo(mapsterCalendarName) == 0) {
                    return id;
                }
            } while (calCursor.moveToNext());
        }
        return -1;
    }

    /**
     * Retrieves the object which can later be queried for information about the next calendar
     * events.
     *
     * @param numberOfEvents
     * @return
     */
    public Cursor getEventCursor(int numberOfEvents)
    {
        long calendarId = getCalendarId();
        if(calendarId == -1)
            return null;

        Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();

        //the current time, in milliseconds
        long now = new Date().getTime();

        ContentUris.appendId(builder, now);
        ContentUris.appendId(builder, now + DateUtils.DAY_IN_MILLIS * 7);

        Cursor eventCursor = mContext.getContentResolver().query(builder.build(),
                mCalendarEventProjections, "Calendar_id=" + calendarId,
                null, "startDay ASC, startMinute ASC limit " + numberOfEvents);

        if (eventCursor.moveToFirst()) {
            do {
                String data = eventCursor.getString(0);
                System.out.print(data);
            } while(eventCursor.moveToNext());
        }
        return eventCursor;
    }
}
