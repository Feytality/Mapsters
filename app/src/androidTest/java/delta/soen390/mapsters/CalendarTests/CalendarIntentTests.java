package delta.soen390.mapsters.CalendarTests;

import android.content.Intent;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import delta.soen390.mapsters.Calendar.CalendarIntent;

/**
 * Created by Amanda on 2015-02-23.
 */
public class CalendarIntentTests extends AndroidTestCase{
    private CalendarIntent mCIntent;
    private CalendarIntentContext mMockContext;

    @Override
    protected void setUp() throws Exception {
        mCIntent = new CalendarIntent();
        mMockContext = new CalendarIntentContext();
    }

    /**
     *  Tests to ensure that when intent is null, pushIntent method returns False
     */
    public void testCalendarPushIntentNullIntent() throws Exception{
        assertTrue(mCIntent.pushIntent(mMockContext));
    }

    private class CalendarIntentContext extends MockContext{
        @Override
        public void startActivity(Intent intent) {

        }
    }

}
