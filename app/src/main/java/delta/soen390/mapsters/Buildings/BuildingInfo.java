package delta.soen390.mapsters.Buildings;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by georgevalergas on 15-02-08.
 */
public class BuildingInfo {
//    TODO Create BuildingServices Class
//    TODO Create Image member
//    private String[] mBuildingServices;
	private String mBuildingCode;
	private String mBuildingName;
	private String mCampus;
    private LatLng mCoordinates;
	private ArrayList<LatLng> mBoundingCoordinates;

    public BuildingInfo() {}

    public BuildingInfo(String buildingCode, String buildingName, String campus,
                        String[] buildingServices, LatLng coordinates, ArrayList<LatLng> boundingCoordinates) {

	    mBuildingCode           = buildingCode;
        mBuildingName           = buildingName;
        mCampus                 = campus;
        mCoordinates            = coordinates;
	    mBoundingCoordinates    = boundingCoordinates;
    }


    public String getBuildingCode() {
        return mBuildingCode;
    }

    public String getBuildingName() {
        return mBuildingName;
    }

	public String getCampus() {
        return mCampus;
    }

    public LatLng getCoordinates() {
        return mCoordinates;
    }

	public ArrayList<LatLng> getBoundingCoordinates() { return mBoundingCoordinates;}
}
