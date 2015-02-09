package delta.soen390.mapsters;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by georgevalergas on 15-02-08.
 */
public class BuildingInfo {
    public enum Campus { LOY, SGW }
    private String mBuildingCode;
    private String mBuildingName;
    private Campus mCampus;
//    TODO Create BuildingServices Class
//    TODO Create Image member
    private String[] mBuildingServices;
    private LatLng mCoordinates;

    public BuildingInfo(String mBuildingCode, String mBuildingName, Campus mCampus,
                        String[] mBuildingServices, LatLng mCoordinates) {
        this.mBuildingCode = mBuildingCode;
        this.mBuildingName = mBuildingName;
        this.mCampus = mCampus;
        this.mBuildingServices = mBuildingServices;
        this. mCoordinates = mCoordinates;
    }

    public String getBuildingCode() {
        return mBuildingCode;
    }

    public void setBuildingCode(String mBuildingCode) {
        this.mBuildingCode = mBuildingCode;
    }

    public String getBuildingName() {
        return mBuildingName;
    }

    public void setBuildingName(String mBuildingName) {
        this.mBuildingName = mBuildingName;
    }

    public Campus getCampus() {
        return mCampus;
    }

    public void setCampus(Campus mCampus) {
        this.mCampus = mCampus;
    }

    public String[] getBuildingServices() {
        return mBuildingServices;
    }

    public void setBuildingServices(String[] mBuildingServices) {
        this.mBuildingServices = this.mBuildingServices;
    }

    public LatLng getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(LatLng mCoordinates) {
        this.mCoordinates = mCoordinates;
    }
}
