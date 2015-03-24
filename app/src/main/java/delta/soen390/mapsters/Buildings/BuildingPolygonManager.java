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
        initialize();
    }

    public static BuildingPolygonManager getInstance() {
	    if(sBuildingPolygonManager == null) {
            sBuildingPolygonManager = new BuildingPolygonManager();
        }
        return sBuildingPolygonManager;
    }
    //</editor-fold>


    public void initialize()
    {

    }

	public void loadResources(GoogleMap gMap, final SplitPane splitPane, Context context) {
		JSONObject jsonBuildingPolygons = JsonReader.ReadJsonFromFile(context,"buildingJson.json");
        PolygonSerializer polygonSerializer = new PolygonSerializer(gMap);
        mSplitPane = splitPane;
        mBuildingPolygons       = polygonSerializer.createPolygonArray(jsonBuildingPolygons);

        //TODO load from values
        float   borderWidth     = 4.0f;//context.getResources().getDimension(R.dimen.polygon_border_width);
        int buildingUnfocusFillColor      = context.getResources().getColor(R.color.concordia_dark);
        int buildingFocusFillColor         = context.getResources().getColor(R.color.concordia_light);

        for(BuildingPolygonOverlay buildingOverlay : mBuildingPolygons)
        {
            buildingOverlay.setBorderWidth(borderWidth);

            //Set the focus/unfocused color for the overlays
            buildingOverlay.setFocusColor(buildingFocusFillColor);
            buildingOverlay.setUnfocusedColor(buildingUnfocusFillColor);

            //Set the overlay state to unfocused in order to reset the new passed unfocused color
            buildingOverlay.unfocus();
        }

    }

    public void clickPolygon(LatLng point){
        PolygonOverlay polygon = PolygonOverlayManager.getInstance().getClickedPolygon(point);

        //Null check and make sure the clicked polygon is actually a building and not a room
        if( (polygon != null) && (polygon.getClass() == BuildingPolygonOverlay.class) )
        {
            clickAndPopulate((BuildingPolygonOverlay)polygon);
        }
    }


    public void clickAndPopulate(BuildingPolygonOverlay buildingPolygon){
        mCurrentBuildingInfo = buildingPolygon.getBuildingInfo();
        //Focus the selected building

        mCurrentlyFocusedBuilding = buildingPolygon;
        mSplitPane.updateContent(mCurrentBuildingInfo);
    }

    public BuildingInfo getCurrentBuildingInfo(){
        return mCurrentBuildingInfo;
    }


    public void activateBuildingOverlays()
    {
        PolygonOverlayManager.getInstance().setActiveOverlays(mBuildingPolygons);
    }


    public BuildingPolygonOverlay getBuildingPolygonByBuildingCode(String buildingCode)
    {
        buildingCode = buildingCode.toUpperCase();

        if (buildingCode != null && !buildingCode.equals("")) {
            for (int i = 0; i < mBuildingPolygons.size(); ++i) {
                BuildingPolygonOverlay buildingPolygon = mBuildingPolygons.get(i);
                if (buildingPolygon.getBuildingInfo().getBuildingCode().compareTo(buildingCode) == 0) {
                    return buildingPolygon;
                }
            }
        }

        return null;
    }


    public BuildingInfo getBuildingInfoByService(String service) {
        if (service != null && !service.equals("")) {
            for (int i = 0; i < mBuildingPolygons.size(); ++i) {
                BuildingPolygonOverlay buildingPolygon = mBuildingPolygons.get(i);
                ArrayList<String[]> services = buildingPolygon.getBuildingInfo().getServices();
                for (String[] serv : services) {
                    if(serv[0].equals(service)) {
                        return buildingPolygon.getBuildingInfo();
                    }
                }
            }
        }

        return null;
    }

    public BuildingInfo getBuildingInfoByDepartment(String department) {
        if (department != null && !department.equals("")) {
            for (int i = 0; i < mBuildingPolygons.size(); ++i) {
                BuildingPolygonOverlay buildingPolygon = mBuildingPolygons.get(i);
                ArrayList<String[]> departments = buildingPolygon.getBuildingInfo().getDepartments();
                for (String[] dept : departments) {
                    if(dept[0].equals(department)) {
                        return buildingPolygon.getBuildingInfo();
                    }
                }
            }
        }

        return null;
    }

    public ArrayList<String> getAllDepartments(){
        ArrayList<String> allDepartments= new ArrayList<>();
        for (int i = 0; i < mBuildingPolygons.size(); ++i) {
            BuildingPolygonOverlay buildingPolygon = mBuildingPolygons.get(i);
            ArrayList<String[]> departments = buildingPolygon.getBuildingInfo().getDepartments();
            for (String[] dept : departments) {
                allDepartments.add(dept[0]);
            }
        }

        return allDepartments;
    }

    public ArrayList<String> getAllServices(){
        ArrayList<String> allServices= new ArrayList<>();
        for (int i = 0; i < mBuildingPolygons.size(); ++i) {
            BuildingPolygonOverlay buildingPolygon = mBuildingPolygons.get(i);
            ArrayList<String[]> services = buildingPolygon.getBuildingInfo().getServices();
            for (String[] serv : services) {
                allServices.add(serv[0]);
            }
        }

        return allServices;
    }

    public ArrayList<String> getAllBuildings(){
        ArrayList<String> allBuildings= new ArrayList<>();
        for (int i = 0; i < mBuildingPolygons.size(); ++i) {
            BuildingPolygonOverlay buildingPolygon = mBuildingPolygons.get(i);
            allBuildings.add(buildingPolygon.getBuildingInfo().getBuildingCode());

        }
        return allBuildings;
    }
}

