package delta.soen390.mapsters.Calendar;


import java.util.Date;

/**
 *  The purpose of this class is to represent a calendar event object.
 *
 *  Created by Mathieu on 2/20/2015.
 */
public class CalendarEvent {

    //The building code, represented by 1-2 characters, Ie. "CC"
    private String mBuildingCode;

    //The building number, maybe put float for sub classes (Ie. 2.102)?
    private String mFullLocation;

    //The full event name, typically the course name
    private String mName;

    //the start date and time of the event
    private Date mStartTime;

    //The end date and time of the event
    private Date mEndTime;

    private Date mBeforeEventNotification;

    public CalendarEvent(String buildingCode, String fullLocation, String name, Date startTime,
                         Date beforeEventNotification) {
        mBuildingCode   = buildingCode;
        mFullLocation   = fullLocation;
        mName           = name;
        mStartTime      = startTime;
        //mEndTime        = endTime;
        mBeforeEventNotification = beforeEventNotification;
    }

    public String getBuildingCode() {
        return mBuildingCode;
    }

    public String getFullLocation() {
        return mFullLocation;
    }

    public String getEventName() {
        return mName;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public Date getEndTime() {
        return mEndTime;
    }

    public Date getBeforeEventNotification() {
        return mBeforeEventNotification;
    }
}
