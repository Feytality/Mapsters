package delta.soen390.mapsters.ViewMode;

import com.google.android.gms.maps.model.LatLng;

import delta.soen390.mapsters.IndoorDirectory.BuildingFloor;
import delta.soen390.mapsters.Utils.GoogleMapCamera;

/**
 * Created by Mathieu on 3/29/2015.
 */
public class IndoorsViewMode extends  ViewMode {

    private BuildingFloor mFloor;
    private GoogleMapCamera mGoogleCamera;
    public IndoorsViewMode(BuildingFloor floor)
    {
        mFloor = floor;
    }
    @Override
    public void setup(GoogleMapCamera camera) {

        if(mFloor == null) {
            return;
        }
        LatLng targetLocation = mFloor.getCoordinates();
        float zoomLevel = mFloor.getZoomLevel();
        camera.lockCamera();
        camera.animateToTarget(targetLocation,zoomLevel,2000);

    }

    @Override
    public void cleanup(GoogleMapCamera camera) {

    }
}
