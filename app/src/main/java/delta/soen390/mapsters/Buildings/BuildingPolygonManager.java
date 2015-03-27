package delta.soen390.mapsters.Buildings;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import org.json.JSONObject;

import java.util.ArrayList;

import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.Data.JsonReader;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlayManager;
import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 2/8/2015.
 */
public class BuildingPolygonManager {


    //<editor-fold desc="Singleton Definition">
    private static BuildingPolygonManager sBuildingPolygonManager;
    private SplitPane mSplitPane;

    private ArrayList<BuildingPolygonOverlay> mBuildingPolygons = new ArrayList<BuildingPolygonOverlay>();

    //Whenever the user clicks a building, that building is focused.
    //Only one building can be focused at a time
    private BuildingPolygonOverlay mCurrentlyFocusedBuilding;
    private BuildingInfo mCurrentBuildingInfo;

    private BuildingPolygonManager() {

    }

    public static BuildingPolygonManager getInstance() {
	    if(sBuildingPolygonManager == null) {
            sBuildingPolygonManager = new BuildingPolygonManager();
        }
        return sBuildingPolygonManager;
    }
    //</editor-fold>


















}

