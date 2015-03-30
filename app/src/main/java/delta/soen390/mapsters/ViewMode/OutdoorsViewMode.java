package delta.soen390.mapsters.ViewMode;

import java.util.ArrayList;
import java.util.Collection;

import delta.soen390.mapsters.Buildings.BuildingPolygonOverlay;
import delta.soen390.mapsters.Buildings.PolygonDirectory;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.IndoorDirectory.BuildingFloor;
import delta.soen390.mapsters.Utils.GoogleMapCamera;

/**
 * Created by Mathieu on 3/29/2015.
 */
public class OutdoorsViewMode extends  ViewMode {

    private float mMinimumZoomLevelTreshold = 17f;
    PolygonDirectory mDirectory;
    public OutdoorsViewMode(PolygonDirectory directory)
    {
        mDirectory = directory;
        mOverlays = mDirectory.getBuildingOverlays();
    }

    @Override
    public void setup(GoogleMapCamera camera) {
        camera.unlockCamera();
        camera.allowIndoorsDisplay(false);

        //Check the camera zoom level
        if(camera.getCurrentZoomLevel() > mMinimumZoomLevelTreshold)
        {
            camera.animateToTarget(camera.getCurrentPosition(),mMinimumZoomLevelTreshold,1000);
        }

        mDirectory.activateBuildingOverlays();

    }

    @Override
    public void cleanup(GoogleMapCamera camera) {
       camera.allowIndoorsDisplay(true);
    }
}
