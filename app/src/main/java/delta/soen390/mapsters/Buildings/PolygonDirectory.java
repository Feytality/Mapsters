package delta.soen390.mapsters.Buildings;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Data.JsonReader;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlayManager;
import delta.soen390.mapsters.IndoorDirectory.BuildingFloor;
import delta.soen390.mapsters.IndoorDirectory.RoomPolygonOverlay;
import delta.soen390.mapsters.IndoorDirectory.RoomPolygonOverlayFactory;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Utils.FileUtility;

/**
 * Created by Mathieu on 3/26/2015.
 */
public class PolygonDirectory {
    private HashMap<String,BuildingPolygonOverlay> mBuildingOverlays;
    private ArrayList<String> mDepartmentList;
    private ArrayList<String> mServiceList;
    private ArrayList<String> mBuildingCodes;
    private Context mContext;
    private PolygonOverlayManager mPolygonManager;
    private float   borderWidth     = 4.0f;

    public PolygonDirectory()
    {


    }

    public void loadResources(MapsActivity activity)
    {
        mContext = activity.getApplicationContext();
        mPolygonManager = activity.getPolygonOverlayManager();

        loadBuildingData(activity);
        loadFloorData(activity);

        initialize();
    }

    private boolean loadBuildingData(MapsActivity activity)
    {
        JSONObject jsonBuildingPolygons = JsonReader.ReadJsonFromFile(mContext, "buildingJson.json");

        GoogleMap gMap = activity.getGoogleMap();
        if(gMap == null) {
            return false;
        }
        PolygonSerializer polygonSerializer = new PolygonSerializer(activity);

        ArrayList<BuildingPolygonOverlay> buildingPolygons = polygonSerializer.createPolygonArray(jsonBuildingPolygons);


        //Fill in the hash map with the building code as the key
        mBuildingOverlays = new HashMap<>();
        for(BuildingPolygonOverlay overlay : buildingPolygons)
        {
            overlay.loadResources(mContext);
            overlay.unfocus();
            overlay.setBorderWidth(borderWidth);
            mBuildingOverlays.put(overlay.getBuildingInfo().getBuildingCode(),overlay);
        }

        mPolygonManager.setActiveOverlays(mBuildingOverlays.values());
        return true;
    }

    public Collection<BuildingPolygonOverlay> getBuildingOverlays()
    {
        return mBuildingOverlays.values();
    }

    private boolean loadFloorData(MapsActivity activity)
    {
        String resourceDirectory = mContext.getResources().getString(R.string.data_directory_floor_overlay);

        //Get a list of all the files present in that directory
        String[] fileList = FileUtility.getFileInDirectory(mContext, resourceDirectory);

        RoomPolygonOverlayFactory factory = new RoomPolygonOverlayFactory(activity);

        PolygonOverlayManager polygonManager = activity.getPolygonOverlayManager();
        //for every file, get the building code + floor number
        for(String fileName : fileList)
        {
            String filePath = resourceDirectory +"/" + fileName;


            //Remove the .kml extension
            String name = fileName;
            if(name.contains(".kml"))
            {
                name = name.substring(0, name.indexOf(".kml"));
            }
            //split the filename with at the underscore, left side is buildingcode, right side floor number
            String[] floorData = name.split("_");

            //should have 2 values, building code at 0 and floor number at 1
            if(floorData.length == 2)
            {
                String buildingCode = floorData[0];
                String floorLevel = floorData[1];

                ArrayList<RoomPolygonOverlay> roomOverlays = factory.generatePolygonOverlay(filePath);

                BuildingPolygonOverlay buildingOverlay = mBuildingOverlays.get(buildingCode);
                BuildingFloor floor = new BuildingFloor(polygonManager, roomOverlays,buildingOverlay.getCenterPoint());

                if(buildingOverlay != null){
                    //Floor should not be active by default
                    floor.deactivate();
                    buildingOverlay.getBuildingInfo().addFloor(floorLevel,floor);
                }
            }
        }
        return true;
    }

    private void initialize()
    {
        mServiceList = new ArrayList<String>();
        mDepartmentList = new ArrayList<String>();
        mBuildingCodes = new ArrayList<String>();

        for(BuildingPolygonOverlay overlay : mBuildingOverlays.values())
        {
            BuildingInfo info = overlay.getBuildingInfo();
           ArrayList<String[]> departments = info.getDepartments();
           for(String[] dept : departments) {
               if (dept.length > 0) {
                   mDepartmentList.add(dept[0]);
               }
           }
            ArrayList<String[]> services = info.getServices();
            for(String[] serv : services) {
                if (serv.length > 0) {
                    mServiceList.add(serv[0]);
                }
            }

            mBuildingCodes.add(info.getBuildingCode());
        }
    }

    public BuildingPolygonOverlay getBuildingByCode(String buildingCode)
    {
        return mBuildingOverlays.get(buildingCode);
    }

    public BuildingPolygonOverlay getBuildingByDepartment(String department)
    {
        if (department != null && !department.equals("")) {
            for(BuildingPolygonOverlay overlay : mBuildingOverlays.values())
            {
                ArrayList<String[]> departments = overlay.getBuildingInfo().getDepartments();
                for (String[] departmentRecord: departments)
                if(departmentRecord[0].contains(department))
                    return overlay;

            }


        }
        return null;
    }


    public void activateBuildingOverlays()
    {
        mPolygonManager.setActiveOverlays(mBuildingOverlays.values());
    }

    public BuildingPolygonOverlay getBuildingByService(String service)
    {
        if (service != null && !service.equals("")) {
            for(BuildingPolygonOverlay overlay : mBuildingOverlays.values())
            {
                ArrayList<String[]> services = overlay.getBuildingInfo().getServices();
                for (String[] serviceRecord: services)
                              if(serviceRecord[0].contains(service)) {
                    return overlay;
                }
            }
        }
        return null;
    }

    public BuildingPolygonOverlay getBuildingByKeyword(String keyword)
    {
        BuildingPolygonOverlay overlay = getBuildingByCode(keyword);
        if(overlay != null)
            return overlay;
        overlay = getBuildingByDepartment(keyword);
        if(overlay!=null)
            return overlay;
        overlay= getBuildingByService(keyword);
        return overlay;

    }

    public final ArrayList<String> getAllServices()
    {
        return mServiceList;
    }
    public final ArrayList<String> getAllDepartments()
    {
        return mDepartmentList;
    }
    public final ArrayList<String> getAllBuildingCodes()
    {
        return mBuildingCodes;
    }
    public  ArrayList<String> getAllDirectoryInfo()
    {

        ArrayList<String> str = new ArrayList<String>();
        str.addAll(getAllDepartments());
        str.addAll(getAllServices());
        str.addAll(getAllBuildingCodes());
        return str;
    }



}
