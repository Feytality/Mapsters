package delta.soen390.mapsters.ViewMode;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Buildings.PolygonDirectory;
import delta.soen390.mapsters.Utils.GoogleMapCamera;

/**
 * Created by Mathieu on 3/29/2015.
 */
public class OutdoorsViewMode extends  ViewMode {

    private final MapsActivity mActivity;
    private float mMinimumZoomLevelTreshold = 17f;
    public OutdoorsViewMode(PolygonDirectory directory , MapsActivity mapsActivity)
    {
        mActivity = mapsActivity;
        mOverlays = directory.getBuildingOverlays();
    }

    @Override
    public void setup(GoogleMapCamera camera) {
        camera.unlockCamera();
        camera.allowIndoorsDisplay(false);
        mActivity.outdoorConfiguration();
        //Check the camera zoom level
        if(camera.getCurrentZoomLevel() > mMinimumZoomLevelTreshold)
        {
            camera.animateToTarget(camera.getCurrentPosition(),mMinimumZoomLevelTreshold,1000);
        }
    }

    @Override
    public void cleanup(GoogleMapCamera camera) {
       camera.allowIndoorsDisplay(true); mActivity.indoorConfiguration();
    }
}
