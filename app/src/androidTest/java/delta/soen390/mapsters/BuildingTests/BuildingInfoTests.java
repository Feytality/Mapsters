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

    public BuildingInfoTests() {
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
        BuildingInfoTests p = new BuildingInfoTests();
        assertEquals(p.mBuildingInfo.getBuildingCode(), p.mBuildingCode);
    }

    public void testGetBuildingName() throws Exception {
        BuildingInfoTests p = new BuildingInfoTests();
        assertEquals(p.mBuildingInfo.getBuildingName(), p.mBuildingName);
    }

    public void testGetCampus() throws Exception {
        BuildingInfoTests p = new BuildingInfoTests();
        assertEquals(p.mBuildingInfo.getCampus(), p.mCampus);
    }

    public void testGetImageUrl() throws Exception {
        BuildingInfoTests p = new BuildingInfoTests();
        assertEquals(p.mBuildingInfo.getImageUrl(), p.mImageUrl);
    }

    public void testGetCoordinates() throws Exception {
        BuildingInfoTests p = new BuildingInfoTests();
        assertEquals(p.mBuildingInfo.getCoordinates(), p.mCoordinates);
    }

    public void testGetBoundingCoordinates() throws Exception {
        BuildingInfoTests p = new BuildingInfoTests();
        assertEquals(p.mBuildingInfo.getBoundingCoordinates(), p.mBoundingCoordinates);
    }

}
