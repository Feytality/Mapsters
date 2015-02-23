package delta.soen390.mapsters.BuildingTests;

import android.test.InstrumentationTestCase;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/**
 * Created by Patrick Nguyen on 15-02-21.
 * Making sure getters all function correctly
 */

public class BuildingInfoTests extends InstrumentationTestCase {

    private BuildingInfo mBuildingInfo;
    private String mBuildingCode;
    private String mBuildingName;
    private String mCampus;
    private String mImageUrl;
    private String[] mBuildingServices;
    private LatLng mCoordinates;
    private ArrayList<LatLng> mBoundingCoordinates;

    public void setUp() throws Exception {
        mBuildingCode = "YYZ";
        mBuildingName = "Airport terminal";
        mCampus = "SGW";
        mImageUrl = "http://concordia.ca";
        mBuildingServices = new String[5];
        mCoordinates = new LatLng(0, 0);
        mBoundingCoordinates = new ArrayList<>();
        mBuildingInfo = new BuildingInfo(mBuildingCode, mBuildingName, mCampus, mImageUrl, mBuildingServices,mCoordinates, mBoundingCoordinates);
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
