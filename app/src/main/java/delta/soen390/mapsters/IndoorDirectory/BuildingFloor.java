package delta.soen390.mapsters.IndoorDirectory;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

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

    private LatLng mCenterPoint;
    private float mZoomLevel = 19.25f;
    private float mOrientationOffset = 0;
    private BuildingInfo mBuildingInfo;
    private LatLng mSouthWest;
    private LatLng mNorthEast;
    private PolygonOverlayManager mPolygonManager;
    private String mFloorName = "";


    public BuildingFloor(PolygonOverlayManager manager,ArrayList<RoomPolygonOverlay> overlays, String floorName)
    {
        mFloorName = floorName;
        mPolygonManager = manager;

        if(overlays.isEmpty())
            return;
        LatLng startingCoordinate = overlays.get(0).getCenterPoint();
        double minLat  = startingCoordinate.latitude, minLng = startingCoordinate.longitude,
                maxLat = startingCoordinate.latitude, maxLng = startingCoordinate.longitude;

        for(RoomPolygonOverlay overlay : overlays)
        {

            LatLng southWest = overlay.getSouthWest();
            LatLng northEast = overlay.getNorthEast();

            //find the most southwest point
            minLat = Math.min(minLat,southWest.latitude);
            minLng = Math.min(minLng,southWest.longitude);

            //find the most northeast point
            maxLat = Math.max(maxLat,northEast.latitude);
            maxLng = Math.max(maxLng,northEast.longitude);

            overlay.setFloor(this);
            mRooms.add(overlay);

        }

        mSouthWest = new LatLng(minLat,minLng);
        mNorthEast = new LatLng(maxLat,maxLng);

        mCenterPoint = new LatLng(
                (mNorthEast.latitude + mSouthWest.latitude) / 2,
                (mNorthEast.longitude + mSouthWest.longitude) / 2);

    }
    public void setZoomLevel(float zoomLevel)
    {
        mZoomLevel = zoomLevel;
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

    public LatLng getCenterPoint() { return mCenterPoint; }
    public LatLng getSouthWest() { return mSouthWest;}
    public LatLng getNorthEast() { return mNorthEast;}

}
