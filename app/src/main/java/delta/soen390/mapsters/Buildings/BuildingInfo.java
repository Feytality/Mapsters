package delta.soen390.mapsters.Buildings;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import delta.soen390.mapsters.Data.Campus;

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

    private ArrayList<String[]> mBuildingServices;
    private ArrayList<String[]> mBuildingDepartments;

    public BuildingInfo() {
    }

    public BuildingInfo(String buildingCode, String buildingName, String campus, String imageUrl,
                        LatLng coordinates, ArrayList<LatLng> boundingCoordinates,
                        ArrayList<String[]> services, ArrayList<String[]> departments) {
        mImageUrl = imageUrl;
        mBuildingCode = buildingCode;
        mBuildingName = buildingName;
        mCampus = Campus.getCampusAsEnum(campus);
        mCoordinates = coordinates;
        mBoundingCoordinates = boundingCoordinates;

        mBuildingServices = services;
        mBuildingDepartments = departments;
    }


    public String getBuildingCode() {
        return mBuildingCode;
    }

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
