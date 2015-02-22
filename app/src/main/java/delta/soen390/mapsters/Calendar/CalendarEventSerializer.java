package delta.soen390.mapsters.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;

import com.google.api.client.util.DateTime;

import java.util.ArrayList;

/**
 * The purpose of this class is to persist events from the user's Android calendar.
 *
 * Created by Mathieu on 2/20/2015.
 */
public class CalendarEventSerializer {
    private CalendarEventImporter mImporter;

    /**
     * Constructor accepting the current context of the application
     *
     * @param   context
     */
    public CalendarEventSerializer(Context context)
    {
        mImporter = new CalendarEventImporter(context);
    }

    /**
     * Obtains a CalendarEvent list of events. The number of next events will be specified by
     * user through their preferences.
     *
     * @param   numberOfEvents    number of next events to retrieve from calendar
     * @return  List of calendar events
     */
    public ArrayList<CalendarEvent> getUpcomingEvents(int numberOfEvents) {
        ArrayList<CalendarEvent> upcomingEvents = new ArrayList<>();

        //Builds a cursor which can be queried to obtain information about the events.
        Cursor eventCursor = mImporter.getEventCursor(numberOfEvents);

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
                            eventCursor.getString(endDatetimeIndex)
                    );

                    if(calendarEvent != null) {
                        upcomingEvents.add(calendarEvent);
                    }
            } while (eventCursor.moveToNext());
        }

        return upcomingEvents;
    }

    /**
     * Creates a calendar event object based on the specified title, location, start and end date.
     *
     * @param   title
     * @param   fullLocation
     * @param   startDateTime
     * @param   endDateTime
     * @return  Calendar event object.
     */
    private CalendarEvent createCalendarEvent(String title, String fullLocation, String startDateTime, String endDateTime)
    {
        //Parse the building code out of the given full location
        String buildingCode = "";

        //Full location is invalid if it does not contain at least 1 characters
        if (fullLocation.length() < 1) {
            return null;
        }

        //Extract the building code from the full location
        int maximumCycles = (fullLocation.length() > 1 ? 2 : 1);
        for(int i = 0; i < maximumCycles; ++i)
        {
            char letter = fullLocation.charAt(i);
            if(Character.isLetter(letter)){
                buildingCode += letter;
            }
        }

        DateTime startDt    = millisToDateTime(startDateTime);
        DateTime endDt      = millisToDateTime(endDateTime);


        return new CalendarEvent(buildingCode, fullLocation, title, startDt, endDt);
    }

    /**
     * Converts millisecond to a DateTime object. Might belong in a utility class instead.
     *
     * @param   millisString
     * @return  DateTime object represented by the accepted string.
     */
    private DateTime millisToDateTime(String millisString)
    {
        if(millisString == null) {
            return null;
        }
        long millis = Long.parseLong(millisString);
        return new DateTime(millis);
    }
}
