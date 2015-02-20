package delta.soen390.mapsters.Buildings;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;

import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.Data.JsonReader;

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
	    if(sBuildingPolygonManager == null)
		    sBuildingPolygonManager = new BuildingPolygonManager();
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
            if(buildingPolygon.isPointInsidePolygon(point)) {
	            return buildingPolygon;
            }
        }

        return null;
    }

	public void loadResources(GoogleMap gMap, final SplitPane splitPane,Activity activity)
	{

		JSONObject jsonBuildingPolygons = JsonReader.ReadJsonFromFile(activity.getApplicationContext(),"buildingJson.json");
        PolygonSerializer polygonSerializer = new PolygonSerializer(gMap);

        mBuildingPolygons = polygonSerializer.CreatePolygonArray(jsonBuildingPolygons);

        //Set the listener

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
               BuildingPolygon polygon = getClickedPolygon(point);
                if(polygon != null) {
                    BuildingInfo buildingInfo = polygon.getBuildingInfo();
                    Log.i(buildingInfo.getBuildingCode(), "Inside!");
                    splitPane.updateContent(buildingInfo);
                }

            }
        });

    }

    public BuildingPolygon getBuildingPolygon(String buildingCode)
    {
        for(int i = 0; i < mBuildingPolygons.size(); ++i)
        {
            BuildingPolygon buildingPolygon = mBuildingPolygons.get(i);
            if(buildingPolygon.getBuildingInfo().getBuildingCode().compareTo(buildingCode) == 0) {
	            return buildingPolygon;
            }
        }

        return null;
    }

};
