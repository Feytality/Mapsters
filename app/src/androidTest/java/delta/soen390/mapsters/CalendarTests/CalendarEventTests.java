package delta.soen390.mapsters.CalendarTests;

import android.test.AndroidTestCase;

import com.google.api.client.util.DateTime;

import delta.soen390.mapsters.Calendar.CalendarEvent;

/**
* Created by georgevalergas on 15-03-08.
*/
public class CalendarEventTests extends AndroidTestCase {
    CalendarEvent mCalendarEvent;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DateTime before = new DateTime(1431167400);     // 05/09/2015 @ 10:30am (UTC)
        DateTime start  = new DateTime(1431171000); // 05/09/2015 @ 11:30am (UTC)
        DateTime end    = new DateTime(1431174600); // 05/09/2015 @ 12:30am (UTC)
        mCalendarEvent   = new CalendarEvent("H", "431", "SOEN 345", start, end, before);
    }

    public void testGetEventName() {
        assertEquals(mCalendarEvent.getEventName(),"SOEN 345");
    }
    public void testGetStartTime() {
        assertEquals(mCalendarEvent.getStartTime().getValue(),new DateTime(1431171000).getValue());
    }
    public void testGetBeforeEventNotification() {
        assertEquals(mCalendarEvent.getBeforeEventNotification().getValue(),new DateTime(1431167400).getValue());
    }

}
