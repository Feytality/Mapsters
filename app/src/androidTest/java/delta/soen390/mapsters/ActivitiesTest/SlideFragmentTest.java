package delta.soen390.mapsters.ActivitiesTest;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import delta.soen390.mapsters.Activities.SlideFragment;

/**
 * Created by Patrick on 15-03-08.
 *
 */
public class SlideFragmentTest extends ActivityInstrumentationTestCase2 {

    private SlideFragment mFragment;
    private View mSlide;

    public SlideFragmentTest() {
        super(SlideFragment.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
    }

    public void testPreConditions() {
        assertNull(mFragment);
    }
    public void testSlideFragmentNullValue() {
        assertNull(mSlide);
    }

}
