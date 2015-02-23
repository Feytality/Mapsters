package delta.soen390.mapsters.SanityTest;

import android.test.InstrumentationTestCase;

/**
 * Created by Patrick on 15-02-21.
 * Sanity Test
 */

public class SanityTest extends InstrumentationTestCase {
    public void testSanity() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected,reality);
    }
}
