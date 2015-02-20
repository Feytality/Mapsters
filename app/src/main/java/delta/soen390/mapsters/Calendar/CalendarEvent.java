package delta.soen390.mapsters.Calendar;

import com.google.api.client.util.DateTime;

import java.util.Date;

/**
 * Created by Mathieu on 2/20/2015.
 */
public class CalendarEvent {

    //The building code, represented by 1-2 characters, Ie. "CC"
    private String      mBuildingCode;

    //The building number, maybe put float for sub classes (Ie. 2.102)?
    private String      mFullLocation;

    //The full event name, typically the course name
    private String      mName;

    //the start date and time of the event
    private DateTime    mStartTime;

    //The end date and time of the event
    private DateTime    mEndTime;

    public CalendarEvent(
            String buildingCode,
            String fullLocation,
            String name,
            DateTime startTime,
            DateTime endTime)
    {
        mBuildingCode   = buildingCode;
        mFullLocation   = fullLocation;
        mName           = name;
        mStartTime      = startTime;
        mEndTime        = endTime;
    }

    public String   getBuildingCode()       {return mBuildingCode;}
    public String   getFullLocation()       {return mFullLocation;}
    public String   getEventName()          {return mName;}
    public DateTime getStartTime()          {return mStartTime;}
    public DateTime getEndTime()            {return mEndTime;}

}
