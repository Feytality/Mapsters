package delta.soen390.mapsters.ActivitiesTest;

import android.view.View;
import android.test.ActivityInstrumentationTestCase2;
import delta.soen390.mapsters.Activities.LaunchActivity;
import delta.soen390.mapsters.R;

/**
 * Created by pattycakes on 15-03-08.
 *
 */
public class LaunchActivityTest extends ActivityInstrumentationTestCase2<LaunchActivity> {

    private LaunchActivity mActivity;
    private View mLaunch;

    public LaunchActivityTest() {
        super(LaunchActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mLaunch = mActivity.findViewById(R.id.title); // should return null because LaunchActivity shouldn't have a title value at all
    }

    public void testPreConditions() {
        assertNotNull(mActivity);

    }
    public void testLaunchNullValue() {
        assertNull(mLaunch);
    }
}
