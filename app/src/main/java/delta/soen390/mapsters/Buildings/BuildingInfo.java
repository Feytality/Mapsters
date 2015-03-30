package delta.soen390.mapsters.Buildings;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

import delta.soen390.mapsters.Data.Campus;
import delta.soen390.mapsters.IndoorDirectory.BuildingFloor;

/**
 * Created by georgevalergas on 15-02-08.
 */
public class BuildingInfo {
//    TODO Create Image member
    private String mBuildingCode;
    private String mBuildingName;
    private Campus.Name mCampus;
    private String mImageUrl;
    private LatLng mCoordinates;
    private ArrayList<LatLng> mBoundingCoordinates;
    private   String mAddress ;
    private   boolean mAccess ;
    private   boolean mInfo ;
    private   boolean mParking ;
    private HashMap<String,BuildingFloor> mFloors = new HashMap<>();
    private ArrayList<String[]> mBuildingServices;
    private ArrayList<String[]> mBuildingDepartments;
    private boolean mBikeRack;

    public BuildingInfo() {
    }

    public BuildingInfo(String buildingCode, String buildingName, String campus, String imageUrl,
                        LatLng coordinates, ArrayList<LatLng> boundingCoordinates,
                        ArrayList<String[]> services, ArrayList<String[]> departments, String address, boolean info, boolean parking, boolean access,boolean bikeRack) {
        mImageUrl = imageUrl;
        mBuildingCode = buildingCode;
        mBuildingName = buildingName;
        mCampus = Campus.getCampusAsEnum(campus);
        mCoordinates = coordinates;
        mBoundingCoordinates = boundingCoordinates;
        mAddress =address;
        mAccess= access;
        mInfo = info;
        mBikeRack=bikeRack;
        mParking = parking;
        mBuildingServices = services;
        mBuildingDepartments = departments;


    }

    public void addFloor(String level, BuildingFloor floor)
    {
        floor.setParentBuilding(this);
        mFloors.put(level,floor);
    }

    public BuildingFloor getFloorAt(String floorLevel)
    {
        return mFloors.get(floorLevel);
    }
    public BuildingFloor getDefaultFloor() {
        if(mFloors.size() == 0)
        {
            return null;
        }
        //Returns the first building
        return mFloors.values().iterator().next();
    }

    public String getBuildingCode() {
        return mBuildingCode;
    }
    public String getAddress() {
        return mAddress;
    }
    public boolean hasInfo(){return mInfo;}
    public boolean hasAccessibility(){return mAccess;}
    public boolean hasParking(){return mParking;}

    public boolean hasBikeRack(){ return mBikeRack;}
    public String getBuildingName() {
        return mBuildingName;
    }

    public Campus.Name getCampus() {
        return mCampus;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public LatLng getCoordinates() {
        return mCoordinates;
    }

    public ArrayList<LatLng> getBoundingCoordinates() {
        return mBoundingCoordinates;
    }

    public ArrayList<String[]> getServices() {
        return mBuildingServices;
    }

    public ArrayList<String[]> getDepartments() {
        return mBuildingDepartments;
    }
}
