package delta.soen390.mapsters.ViewMode;

import delta.soen390.mapsters.Activities.MapsActivity;
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
    public void setup(MapsActivity activity, ViewMode previousViewMode) {

        GoogleMapCamera camera = activity.getGoogleMapCamera();
        camera.unlockCamera();
        camera.allowIndoorsDisplay(false);
        activity.outdoorConfiguration();
        //Check the camera zoom level
        if(camera.getCurrentZoomLevel() > mMinimumZoomLevelTreshold)
        {
            camera.animateToTarget(camera.getCurrentPosition(),mMinimumZoomLevelTreshold,camera.getDefaultBearing(), 1000);
        }

        mDirectory.activateBuildingOverlays();

    }

    @Override
    public void cleanup(MapsActivity activity) {
        GoogleMapCamera camera = activity.getGoogleMapCamera();
       camera.allowIndoorsDisplay(true);
       activity.indoorConfiguration();
    }
}
