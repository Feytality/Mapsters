package delta.soen390.mapsters.Calendar;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.util.Log;

import com.google.api.client.util.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 2/20/2015.
 */
public class CalendarEventSerializer {
    private CalendarEventImporter mImporter;

    public ArrayList<CalendarEvent> getUpcomingEvents(int numberOfEvents) {
        ArrayList<CalendarEvent> upcomingEvents = new ArrayList<CalendarEvent>();

        //Builds a Uri, I think it's like query type, but not 100% sure.

        Cursor eventCursor = mImporter.GetEventCursor(numberOfEvents);

        //iterate through events
        if (eventCursor.moveToFirst()) {

            int locationIndex = eventCursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION);
            int startDateTimeIndex = eventCursor.getColumnIndex(CalendarContract.Events.DTSTART);
            int endDatetimeIndex = eventCursor.getColumnIndex(CalendarContract.Events.DTEND);
            int titleIndex = eventCursor.getColumnIndex(CalendarContract.Events.TITLE);

            do {

                    CalendarEvent calendarEvent = createCalendarEvent(
                            eventCursor.getString(titleIndex),
                            eventCursor.getString(locationIndex),
                            eventCursor.getString(startDateTimeIndex),
                            eventCursor.getString(endDatetimeIndex));

                    if(calendarEvent != null) {
                        upcomingEvents.add(calendarEvent);

                }
            } while (eventCursor.moveToNext());
        }

        return upcomingEvents;

    }

    private CalendarEvent createCalendarEvent(String title, String fullLocation,String startDateTime, String endDateTime)
    {

        //Parse the building code out of the given full location
        String buildingCode = "";

        //Full location is invalid if it does not contain at least 1 characters
        if(fullLocation.length() < 1)
            return null;

        //Extract the building code from the full location
        int maximumCycles = (fullLocation.length() > 1 ? 2 : 1);
        for(int i = 0; i < maximumCycles ; ++i)
        {
            char letter = fullLocation.charAt(i);
            if(Character.isLetter(letter)){
                buildingCode += letter;
            }
        }

        DateTime startDt    = millisToDateTime(startDateTime);
        DateTime endDt      = millisToDateTime(endDateTime);


        return new CalendarEvent(buildingCode,fullLocation,title,startDt,endDt);


    }

    private DateTime millisToDateTime(String millisString)
    {
        if(millisString == null)
            return null;
        long millis = Long.parseLong(millisString);
        return new DateTime(millis);

    }




    public CalendarEventSerializer(Context context)
    {
        mImporter = new CalendarEventImporter(context);
    }



}
