package delta.soen390.mapsters.ActivitiesTest;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import delta.soen390.mapsters.Activities.MapsActivity;

/**
 * Created by Patrick on 15-03-08.
 *
 */
public class MapsActivityTest extends ActivityInstrumentationTestCase2<MapsActivity>{

    private MapsActivity mActivity;
    private View mMaps;

    public MapsActivityTest() {
        super(MapsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
    }

    public void testPreConditions() {
        assertNull(mActivity);
    }
    public void testMapsNullValue() {
        assertNull(mMaps);
    }
}
