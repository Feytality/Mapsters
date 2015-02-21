package delta.soen390.mapsters;

import android.test.InstrumentationTestCase;

/**
 * Created by Patrick on 15-02-21.
 * First test case to see if Android Studio complains about anything
 */

public class ExampleTest extends InstrumentationTestCase {
    public void testSnapbackToReality() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected,reality);
    }
}
