package delta.soen390.mapsters.ActivitiesTest;

import delta.soen390.mapsters.Activities.SplashActivity;
import delta.soen390.mapsters.R;

// Use ActivityInstrumentionTestCase2. Refer to the link below for more information
// http://developer.android.com/tools/testing/activity_test.html
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Patrick on 15-03-07.
 *
 */

public class SplashActivityTest extends ActivityInstrumentationTestCase2<SplashActivity> {

    private SplashActivity mActivity;
    private View mSplash;
    private TextView x;

    public SplashActivityTest() {
        super(SplashActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // If any of your test methods send key events to the application,
        // you must turn off touch mode before you start any activities; otherwise, the call is ignored.
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mSplash = mActivity.findViewById(R.id.splash);

    }

    public void testSanity(){
        x = (TextView)mSplash.findViewById(R.id.textView);
        assertEquals(x.getText(), "Mapster");
        ImageView y = (ImageView)mSplash.findViewById(R.id.imageView);
        assertEquals(y.getVisibility(), View.VISIBLE);
        int ID = y.getId();
        assertEquals(ID, R.id.imageView); // Making sure it's the same ID

    }

    public void testPreConditions() {
        assertNotNull(mActivity);
        assertNotNull(mSplash);
    }

    public void testContentView() {
        assertNotNull(mActivity.getWindow().getDecorView());
    }
}
