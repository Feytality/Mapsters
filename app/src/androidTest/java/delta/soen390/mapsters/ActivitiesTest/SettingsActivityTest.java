package delta.soen390.mapsters.ActivitiesTest;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import delta.soen390.mapsters.Activities.SettingsActivity;

/**
 * Created by Patrick on 15-03-08.
 *
 */
public class SettingsActivityTest extends ActivityInstrumentationTestCase2<SettingsActivity> {

    private SettingsActivity mActivity;
    private View mSettings;

    public SettingsActivityTest() {
        super(SettingsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
    }

    public void testPreConditions() {
        assertNull(mActivity);
    }
    public void testSettingsNullValue() {
        assertNull(mSettings);
    }
}
