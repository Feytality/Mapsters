package delta.soen390.mapsters.CalendarTests;

import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;

import delta.soen390.mapsters.Calendar.CalendarEventImporter;

/**
 * Created by Felicia on 2015-02-25.
 */
public class CalendarEventImporterTests extends AndroidTestCase {

    private CalendarEventImporter mCEvent;
    private CalendarImporterContext mMockContext;

    @Override
    protected void setUp() throws Exception {
        mMockContext = new CalendarImporterContext();
        mCEvent = new CalendarEventImporter(mMockContext);
    }

    public void testGetEventCursorNullCursor() throws Exception {
        Cursor retVal = mCEvent.getEventCursor(5);
        assertNull(retVal);
    }

    private class CalendarImporterContext extends MockContext {
        @Override
        public ContentResolver getContentResolver() {
            return new MockContentResolver();
        }
    }
}
