package delta.soen390.mapsters.IndoorDirectory;

import java.util.ArrayList;

import delta.soen390.mapsters.Buildings.BuildingInfo;

/**
 * Created by Mathieu on 3/22/2015.
 */
public class BuildingFloor {
    private ArrayList<ClassroomPolygonOverlay> mClassrooms;

    private BuildingInfo mBuildingInfo;
    private int mFloorLevel = 0;


    public BuildingFloor()
    {

    }

    public void attachToBuilding(BuildingInfo info, int floorLevel)
    {
        mFloorLevel = floorLevel;
        mBuildingInfo = info;
    }

    //Load the polygons in the polygonManager
    public void activateFloor()
    {

    }

    public void deactivateFloor()
    {

    }

}
