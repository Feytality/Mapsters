package delta.soen390.mapsters.BuildingTests;

import android.test.AndroidTestCase;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/**
 * Created by Patrick Nguyen on 15-02-21.
 * Making sure getters all function correctly
 */

public class BuildingInfoTests extends AndroidTestCase {

    private BuildingInfo mBuildingInfo;
    private String mBuildingCode;
    private String mBuildingName;
    private String mCampus;
    private String mImageUrl;
    private LatLng mCoordinates;
    private ArrayList<LatLng> mBoundingCoordinates;
    private ArrayList<String[]> mBuildingServices;
    private ArrayList<String[]> mDepartments;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mBuildingCode = "YYZ";
        mBuildingName = "Airport terminal";
        mCampus = "SGW";
        mImageUrl = "http://concordia.ca";
        mCoordinates = new LatLng(0, 0);
        mBoundingCoordinates = new ArrayList<>();
        mBuildingServices = new ArrayList<>(5);
        mDepartments = new ArrayList<>(5);
        mBuildingInfo = new BuildingInfo(mBuildingCode, mBuildingName, mCampus, mImageUrl,
                mCoordinates, mBoundingCoordinates, mBuildingServices, mDepartments);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetBuildingCode() throws Exception {
        assertEquals(mBuildingInfo.getBuildingCode(), mBuildingCode);
    }

    public void testGetBuildingName() throws Exception {
        assertEquals(mBuildingInfo.getBuildingName(), mBuildingName);
    }

    public void testGetCampus() throws Exception {
        assertEquals(mBuildingInfo.getCampus(), mCampus);
    }

    public void testGetImageUrl() throws Exception {
        assertEquals(mBuildingInfo.getImageUrl(), mImageUrl);
    }

    public void testGetCoordinates() throws Exception {
        assertEquals(mBuildingInfo.getCoordinates(), mCoordinates);
    }

    public void testGetBoundingCoordinates() throws Exception {
        assertEquals(mBuildingInfo.getBoundingCoordinates(), mBoundingCoordinates);
    }
}
