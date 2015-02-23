package delta.soen390.mapsters.BuildingTests;

import android.test.InstrumentationTestCase;
import delta.soen390.mapsters.Buildings.BuildingPolygonManager;

/**
 * Created by Patrick on 15-02-23.
 *
 */
public class BuildingPolygonManagerTests extends InstrumentationTestCase {

    private BuildingPolygonManager mBuildingPolygonManager;

    public void testInitialize() throws Exception {
        mBuildingPolygonManager.initialize();
        assertNotNull(mBuildingPolygonManager);
    }

}
