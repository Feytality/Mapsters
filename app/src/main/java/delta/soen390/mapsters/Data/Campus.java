package delta.soen390.mapsters.Data;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class Campus {

    private final LatLng mCenterPoint;
    private final CampusEnum mName;
    //To be changed: users should be 1k from the campus in order for the shuttle to be a viable option
    private final int mInsideDistance = 1000;

    public Campus (CampusEnum name,LatLng point){
        mName = name;
        mCenterPoint = point;
    }

    public CampusEnum getName(){
        return mName;
    }

    public LatLng getCenterPoint(){
        return mCenterPoint;
    }

    //TODO create geo fence method instead of this POS algo
    public boolean isClose(LatLng point){
        float[] result = new float[1];
        Location.distanceBetween(mCenterPoint.latitude, mCenterPoint.longitude, point.latitude, point.longitude, result);

        if (result[0] > mInsideDistance)
            return false;

        return true;
    }

}
