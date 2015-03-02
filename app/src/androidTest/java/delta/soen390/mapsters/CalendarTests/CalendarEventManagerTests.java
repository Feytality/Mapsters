package delta.soen390.mapsters.CalendarTests;

import android.content.ContentResolver;
import android.test.AndroidTestCase;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

import delta.soen390.mapsters.Calendar.CalendarEvent;
import delta.soen390.mapsters.Calendar.CalendarEventManager;

/**
 * Created by Felicia on 2015-02-25.
 */
public class CalendarEventManagerTests extends AndroidTestCase {
    CalendarEventManager mCEManager;
    CalendarManagerContext mMockContext;

    @Override
    protected void setUp() throws Exception {
        mMockContext = new CalendarManagerContext();
        mCEManager = new CalendarEventManager(mMockContext);
    }

    public void testUpdateEventQueueInitialization() throws Exception {
        mCEManager.updateEventQueue();
        int size = mCEManager.getQueueSize();
        assertEquals(size, 0);
    }

    public void testGetNextEventEmpty() throws Exception {
        mCEManager.updateEventQueue();
        CalendarEvent nextEvent = mCEManager.getNextEvent();
        assertNull(nextEvent);
    }

    private class CalendarManagerContext extends MockContext {
        @Override
        public ContentResolver getContentResolver() {
            return new MockContentResolver();
        }
    }
}
