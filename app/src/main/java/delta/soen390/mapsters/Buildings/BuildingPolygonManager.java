package delta.soen390.mapsters.Buildings;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Mathieu on 2/8/2015.
 */
public class BuildingPolygonManager {


    //<editor-fold desc="Singleton Definition">
    private static BuildingPolygonManager sBuildingPolygonManager;

    private BuildingPolygonManager()
    {
        initialize();
    }

    public static BuildingPolygonManager getInstance()
    {
        return sBuildingPolygonManager;
    }
    //</editor-fold>

    private ArrayList<BuildingPolygon> mBuildingPolygons;

    public void initialize()
    {
        mBuildingPolygons  = new ArrayList<BuildingPolygon>();


    }

    public BuildingPolygon getClickedPolygon(LatLng point)
    {

        for(int i = 0; i < mBuildingPolygons.size(); ++i)
        {
            BuildingPolygon buildingPolygon = mBuildingPolygons.get(i);
            if(buildingPolygon.isPointInsidePolygon(point))
                return buildingPolygon;
        }

        return null;
    }

    public BuildingPolygon getBuildingPolygon(String buildingCode)
    {
        for(int i = 0; i < mBuildingPolygons.size(); ++i)
        {
            BuildingPolygon buildingPolygon = mBuildingPolygons.get(i);
            if(buildingPolygon.getBuildingInfo().getBuildingCode().compareTo(buildingCode) == 0)
                return buildingPolygon;
        }
        return null;
    }

};
