package delta.soen390.mapsters.CalendarTests;

import android.content.ContentResolver;
import android.test.AndroidTestCase;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

import com.google.api.client.util.DateTime;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Calendar.CalendarEvent;
import delta.soen390.mapsters.Calendar.CalendarEventNotification;

/**
 *
 * Created by Felicia on 2015-02-25.
 *
 */
public class CalendarEventNotificationTests extends AndroidTestCase {
    private CalendarNotificationContext mMockContext;
    private MapsActivity mMapsActivity;
    private CalendarEvent mCEvent;
    private CalendarEventNotification mCENotification;

    @Override
    protected void setUp() throws Exception {
        mMockContext = new CalendarNotificationContext();
        mMapsActivity = new MapsActivity();
        mCEvent = new CalendarEvent("H", "SGW", "Hall", new DateTime(1424877564), new DateTime(1424877564), new DateTime(1424877564));
        mCENotification = new CalendarEventNotification(mMockContext, mMapsActivity, mCEvent);
    }

    public void testCreateNotification() throws Exception {
        // TODO find a way to fake PendingIntent
       // mCENotification.createNotification();
    }

    private class CalendarNotificationContext extends MockContext {
        @Override
        public String getPackageName() {
            return "";
        }

        @Override
        public ContentResolver getContentResolver() {
            return new MockContentResolver();
        }
    }

}
