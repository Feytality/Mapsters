package delta.soen390.mapsters.BuildingTests;

import android.test.AndroidTestCase;

import com.google.android.gms.maps.model.LatLng;

import delta.soen390.mapsters.Buildings.BuildingPolygon;
import delta.soen390.mapsters.Buildings.BuildingPolygonManager;

/**
 * Created by Patrick on 15-02-23.
 *
 */
public class BuildingPolygonManagerTests extends AndroidTestCase {

    private BuildingPolygonManager mBuildingPolygonManager;
    private LatLng mLatLng;
    private String mBuildingCode;

    @Override
    protected void setUp() throws Exception {
        mBuildingPolygonManager = mBuildingPolygonManager.getInstance();
        mLatLng = new LatLng(0.0, 0.0);
        mBuildingCode = "H";
    }

    public void testInitialize() {
        assertNotNull(mBuildingPolygonManager);
    }

    public void testGetClickedPolygonNull() {
        BuildingPolygon retVal = mBuildingPolygonManager.getClickedPolygon(null);
        assertNull(retVal);
    }

    public void testGetClickedPolygonNotNull() {
        BuildingPolygon retVal = mBuildingPolygonManager.getClickedPolygon(mLatLng);
        assertNull(retVal);
    }

    public void testGetBuildingPolygonNull() {
        BuildingPolygon retVal = mBuildingPolygonManager.getBuildingPolygon(null);
        assertNull(retVal);
    }

    public void testGetBuildingPolygonEmpty() {
        BuildingPolygon retVal = mBuildingPolygonManager.getBuildingPolygon("");
        assertNull(retVal);
    }

}
