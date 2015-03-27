package delta.soen390.mapsters.Utils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Mathieu on 3/25/2015.
 */
public class GoogleMapCamera {

    private GoogleMap mMap;

    public GoogleMapCamera(GoogleMap map)
    {
        mMap = map;
    }


    public void moveToTarget(LatLng position)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
    public void moveToTarget(LatLng position,float zoomLevel)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,zoomLevel));
    }

    public void animateToTarget(LatLng position, int speed)
    {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(position),speed,
                new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onCancel() {

            }
        });
    }
    public void animateToTarget(LatLng position, float zoomLevel, int speed)
    {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,zoomLevel),
                speed,new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
