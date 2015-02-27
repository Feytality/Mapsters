package delta.soen390.mapsters.Buildings;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;

import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.Data.JsonReader;
import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 2/8/2015.
 */
public class BuildingPolygonManager {


    //<editor-fold desc="Singleton Definition">
    private static BuildingPolygonManager sBuildingPolygonManager;
    private int mBuildingFocusFillColor;
    private int mBuildingStandardFillColor;

    //Whenever the user clicks a building, that building is focused.
    //Only one building can be focused at a time
    private BuildingPolygon mCurrentlyFocusedBuilding;

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

	public void loadResources(GoogleMap gMap, final SplitPane splitPane,Context context)
	{

		JSONObject jsonBuildingPolygons = JsonReader.ReadJsonFromFile(context,"buildingJson.json");
        PolygonSerializer polygonSerializer = new PolygonSerializer(gMap);

        mBuildingPolygons       = polygonSerializer.CreatePolygonArray(jsonBuildingPolygons);
        //TODO load from values
        float   borderWidth     = 4.0f;//context.getResources().getDimension(R.dimen.polygon_border_width);
        mBuildingStandardFillColor      = context.getResources().getColor(R.color.concordia_dark);
        mBuildingFocusFillColor         = context.getResources().getColor(R.color.concordia_light);

        for(int i = 0; i < mBuildingPolygons.size(); ++i)
        {
            BuildingPolygon polygon = mBuildingPolygons.get(i);
            polygon.setBorderWidth(borderWidth);

            unfocusBuildingPolygon(polygon);
        }
        //Set the listener

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
               BuildingPolygon polygon = getClickedPolygon(point);
                if(polygon != null) {
                    BuildingInfo buildingInfo = polygon.getBuildingInfo();

                    //Focus the selected building
                    focusBuildingPolygon(polygon);
                    splitPane.updateContent(buildingInfo);
                }

            }
        });

    }

    //Will create a focus effect on the passed BuildingPolygon
    public void focusBuildingPolygon(BuildingPolygon polygon)
    {
        if(mCurrentlyFocusedBuilding != null) {
            unfocusBuildingPolygon(mCurrentlyFocusedBuilding);
        }
        mCurrentlyFocusedBuilding = polygon;
        mCurrentlyFocusedBuilding.setFillColor(mBuildingFocusFillColor);
    }

    //Remove all focus effects of the BuildingPolygon
    //mainly resets color
    public void unfocusBuildingPolygon(BuildingPolygon polygon)
    {
        polygon.setFillColor(mBuildingStandardFillColor);
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
