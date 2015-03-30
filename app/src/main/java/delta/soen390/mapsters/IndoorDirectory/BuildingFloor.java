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

    private HashMap<String,RoomPolygonOverlay> mRooms = new HashMap<>();

    private LatLng mCoordinates;
    private float mZoomLevel = 20.0f;
    private int mFloorLevel = 0;
    private float mOrientationOffset = 0;

    private BuildingInfo mBuildingInfo;

    private PolygonOverlayManager mPolygonManager;


    public BuildingFloor(PolygonOverlayManager manager,ArrayList<RoomPolygonOverlay> overlays, LatLng floorCoordinate)
    {
        mCoordinates = floorCoordinate;
        mPolygonManager = manager;
        for(RoomPolygonOverlay overlay : overlays)
        {
            mRooms.put(overlay.getName(),overlay);
        }
    }

    public void activateFloorOverlays() {
        mPolygonManager.setActiveOverlays(mRooms.values());
    }
    public RoomPolygonOverlay getRoomOverlay(String roomName)
    {
        return mRooms.get(roomName);
    }

    //Focusing a floor will activate all of the floor overlays with the unfocused field
    public void activate()
    {
        mPolygonManager.setActiveOverlays(mRooms.values());

        //unfocus all rooms in the floor
        for(RoomPolygonOverlay overlay : mRooms.values())
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
        for(RoomPolygonOverlay overlay : mRooms.values())
        {
            overlay.deactivate();
            overlay.unfocus();
        }
    }

    public void setOrientationOffset(float offset)
    {
        mOrientationOffset = offset;
    }
    public float getOrientationOffset() { return mOrientationOffset;}

    public LatLng getCoordinates() { return mCoordinates; }

}
