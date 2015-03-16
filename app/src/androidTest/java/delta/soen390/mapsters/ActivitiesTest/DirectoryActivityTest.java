package delta.soen390.mapsters.ActivitiesTest;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import delta.soen390.mapsters.Activities.DirectoryActivity;
import delta.soen390.mapsters.R;

/**
 * Created by Patrick on 15-03-08.
 *
 */
public class DirectoryActivityTest extends ActivityInstrumentationTestCase2<DirectoryActivity> {

    private DirectoryActivity mActivity;
    private View mDirectory;

    public DirectoryActivityTest() {
        super(DirectoryActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mDirectory = mActivity.findViewById(R.id.image); // should return null because DirectoryActivity doesn't have an image
    }

    public void testPreConditions() {
        assertNotNull(mActivity);

    }
    public void testDirectoryNullValue() {
        assertNull(mDirectory);
    }

}
