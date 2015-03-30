package delta.soen390.mapsters.IndoorDirectory;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlayManager;
import delta.soen390.mapsters.Utils.GoogleMapCamera;

/**
 * Created by Mathieu on 3/22/2015.
 */
public class BuildingFloor {

    private ArrayList<RoomPolygonOverlay> mRooms = new ArrayList<>();

    private LatLng mCoordinates;
    private float mZoomLevel = 19.25f;
    private float mOrientationOffset = 0;

    private BuildingInfo mBuildingInfo;

    private PolygonOverlayManager mPolygonManager;
    private String mFloorName = "";


    public BuildingFloor(PolygonOverlayManager manager,ArrayList<RoomPolygonOverlay> overlays, LatLng floorCoordinate, String floorName)
    {
        mFloorName = floorName;
        mCoordinates = floorCoordinate;
        mPolygonManager = manager;
        for(RoomPolygonOverlay overlay : overlays)
        {
            mRooms.add(overlay);
        }
    }

    public void activateFloorOverlays() {
        mPolygonManager.setActiveOverlays(mRooms);
    }
    public RoomPolygonOverlay getRoomOverlay(String roomName)
    {
        for(RoomPolygonOverlay overlay : mRooms)
        {
            if(overlay.getName().equals(roomName))
            {
                return overlay;
            }
        }
        return null;
    }

    public Collection<RoomPolygonOverlay> getRoomPolygonOverlays()
    {
        return mRooms;
    }
    //Focusing a floor will activate all of the floor overlays with the unfocused field
    public void activate()
    {
        mPolygonManager.setActiveOverlays(mRooms);

        //unfocus all rooms in the floor
        for(RoomPolygonOverlay overlay : mRooms)
        {
            overlay.unfocus();
        }
    }
    public void setParentBuilding(BuildingInfo info)
    {
        mBuildingInfo = info;
    }

    public BuildingInfo getParentBuilding()
    {
        return mBuildingInfo;
    }
    public float getZoomLevel() {
        return mZoomLevel;
    }
    public void deactivate()
    {
        for(RoomPolygonOverlay overlay : mRooms)
        {
            overlay.deactivate();
            overlay.unfocus();
        }
    }

    public String getFloorName(){
        return mFloorName;
    }
    public void setOrientationOffset(float offset)
    {
        mOrientationOffset = offset;
    }
    public float getOrientationOffset() { return mOrientationOffset;}

    public LatLng getCoordinates() { return mCoordinates; }

}
