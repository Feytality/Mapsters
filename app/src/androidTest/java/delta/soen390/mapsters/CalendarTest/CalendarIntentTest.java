package delta.soen390.mapsters.CalendarTest;

import android.app.Activity;
import android.test.InstrumentationTestCase;

import delta.soen390.mapsters.Calendar.CalendarIntent;

/**
 * Created by Amanda on 2015-02-23.
 */
public class CalendarIntentTest extends InstrumentationTestCase{
    private CalendarIntent mCIntent = new CalendarIntent();
    private Activity mActivity;
    /*
    Tests to ensure that when intent is null, pushIntent method returns False
     */
    public void testPushIntentNullIntent() throws Exception{

        assertTrue(mCIntent.pushIntent(mActivity.getApplicationContext()));

    }
}
