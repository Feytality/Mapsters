package delta.soen390.mapsters.ViewMode;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.LatLng;

import java.util.Collection;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.Effects.EffectManager;
import delta.soen390.mapsters.IndoorDirectory.BuildingFloor;
import delta.soen390.mapsters.IndoorDirectory.RoomPolygonOverlay;
import delta.soen390.mapsters.Utils.GoogleMapCamera;

/**
 * Created by Mathieu on 3/29/2015.
 */
public class IndoorsViewMode extends  ViewMode {

    private BuildingFloor mFloor;
    private GoogleMapCamera mGoogleCamera;
    private String mFloorCode =  null;
    public IndoorsViewMode(BuildingFloor floor)
    {
        mFloor = floor;

    }

    public IndoorsViewMode(String floorCode)
    {
        mFloorCode = floorCode;
    }

    public IndoorsViewMode()
    {

    }
    @Override
    public void setup(MapsActivity activity, ViewMode previousViewMode) {

        GoogleMapCamera camera = activity.getGoogleMapCamera();

        //Switching floors!
        if(previousViewMode.getClass() == IndoorsViewMode.class && mFloor == null)
        {
            IndoorsViewMode previousView = (IndoorsViewMode) previousViewMode;
            BuildingFloor previousFloor = previousView.mFloor;
            //Check if the previous floor ever existed
            if(previousFloor == null) {
                return;
            }
            if(mFloorCode == null)
            {
                //Called whenever the google map floor plan needs to be synchronized with the
                //passed floor
                //set currently focused tab to current
                updateGoogleMapFloor(activity.getGoogleMap(),previousFloor);
                mFloorCode = previousFloor.getFloorName();
            }

            BuildingInfo info = previousView.mFloor.getParentBuilding();
            mFloor = info.getFloorAt(mFloorCode);

            if(mFloor == null)
                return;
            //camera.moveToTarget(mFloor.getCenterPoint(),mFloor.getZoomLevel(),33.75f);

        }
        else {
            if(mFloor == null)
                return;
            camera.animateToTarget(mFloor.getCenterPoint(),mFloor.getZoomLevel(),33.75f,1500);
        }


        activity.indoorConfiguration();
        camera.lockCamera();


        mFloor.activateFloorOverlays();
        mOverlays = mFloor.getRoomPolygonOverlays();
    }

    private void updateGoogleMapFloor(GoogleMap map, BuildingFloor floor)
    {
        IndoorBuilding building = map.getFocusedBuilding();
        if(building != null) {
            for (IndoorLevel level : building.getLevels()) {
                //set the indoor floor level in the google maps to the currently set floor level
                if (level.getName().equals(floor.getFloorName())) {
                    level.activate();
                }
            }
        }
    }

    @Override
    public void cleanup(MapsActivity activity) {
        activity.outdoorConfiguration();

        if(mFloor == null)
            return;
        Collection<RoomPolygonOverlay> roomOverlays = mFloor.getRoomPolygonOverlays();

        EffectManager effectManager = activity.getEffectManager();
        for(RoomPolygonOverlay overlay : roomOverlays)
        {
            effectManager.removeEffect(overlay);
        }
    }
}
