package delta.soen390.mapsters.IndoorDirectory;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlayManager;

/**
 * Created by Mathieu on 3/22/2015.
 */
public class BuildingFloor {

    private HashMap<String,RoomPolygonOverlay> mRooms = new HashMap<>();

    private LatLng mCoordinates;
    private int mFloorLevel = 0;
    private float mOrientationOffset = 0;
    private PolygonOverlayManager mPolygonManager;


    public BuildingFloor(PolygonOverlayManager manager,ArrayList<RoomPolygonOverlay> overlays, LatLng floorCoordinate)
    {
        mCoordinates = floorCoordinate;

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

    public void setOrientationOffset(float offset)
    {
        mOrientationOffset = offset;
    }
    public float getOrientationOffset() { return mOrientationOffset;}

    public LatLng getCoordinates() { return mCoordinates; }

}
