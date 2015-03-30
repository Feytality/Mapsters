package delta.soen390.mapsters.ViewMode;

import com.google.android.gms.maps.model.LatLng;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.IndoorDirectory.BuildingFloor;
import delta.soen390.mapsters.Utils.GoogleMapCamera;

/**
 * Created by Mathieu on 3/29/2015.
 */
public class IndoorsViewMode extends  ViewMode {

    private final MapsActivity mActivity;
    private BuildingFloor mFloor;
    private GoogleMapCamera mGoogleCamera;
    public IndoorsViewMode(BuildingFloor floor, MapsActivity mapsActivity)
    {
        mFloor = floor;
        mActivity = mapsActivity;

    }
    @Override
    public void setup(GoogleMapCamera camera) {

        if(mFloor == null) {
            return;
        }

mActivity.indoorConfiguration();
        LatLng targetLocation = mFloor.getCoordinates();
        float zoomLevel = mFloor.getZoomLevel();
        camera.lockCamera();
        camera.animateToTarget(targetLocation,zoomLevel,2000);

    }

    @Override
    public void cleanup(GoogleMapCamera camera) {
        mActivity.outdoorConfiguration();
    }
}
