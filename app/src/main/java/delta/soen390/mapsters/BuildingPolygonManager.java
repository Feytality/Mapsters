package delta.soen390.mapsters;

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

    }


    public static BuildingPolygonManager getInstance()
    {
        return sBuildingPolygonManager;
    }
    //</editor-fold>

    private ArrayList<BuildingPolygon> mPolygons;

    public void initialize()
    {
        //mPolygons = new ArrayList<BuildingPolygon>();


    }

    //TODO implement getClickedPolygon
    public BuildingPolygon getClickedPolygon(LatLng point)
    {
        return null;
    }

};
