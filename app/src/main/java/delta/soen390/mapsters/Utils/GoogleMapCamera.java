package delta.soen390.mapsters.Utils;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Mathieu on 3/25/2015.
 */
public class GoogleMapCamera {

    private GoogleMap mMap;
    private float mDefaultBearing = 0;
    public GoogleMapCamera(GoogleMap map)
    {
        mMap = map;
        mDefaultBearing = mMap.getCameraPosition().bearing;
    }


    public void moveToTarget(LatLng position)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    public void moveToTarget(LatLng position,float zoomLevel)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,zoomLevel));
    }

    public void moveToTarget(LatLng currentTarget, float zoomLevel, float bearing)
    {
        CameraPosition position = CameraPosition.builder().target(currentTarget).zoom(zoomLevel).bearing(bearing).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }
    public void allowIndoorsDisplay(boolean value)
    {
        mMap.getUiSettings().setIndoorLevelPickerEnabled(value);
    }

    public float getDefaultBearing() { return mDefaultBearing;}
    public float getCurrentZoomLevel() { return mMap.getCameraPosition().zoom;}
    public LatLng getCurrentPosition() {return mMap.getCameraPosition().target;}
    public void lockCamera()
    {
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }
    public void unlockCamera()
    {
        mMap.getUiSettings().setAllGesturesEnabled(true);
    }

    public void rotateCamera(float bearing)
    {
        LatLng currentTarget = mMap.getCameraPosition().target;
        float zoom = mMap.getCameraPosition().zoom;
        CameraPosition position = CameraPosition.builder().target(currentTarget).zoom(zoom).bearing(bearing).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }
    public void resetCameraRotation()
    {
        rotateCamera(mDefaultBearing);
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
    public void animateToTarget(LatLng position, float zoomLevel, float bearing, int speed)
    {
        CameraPosition camPos = CameraPosition.builder().target(position).zoom(zoomLevel).bearing(bearing).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos),
                speed,new GoogleMap.CancelableCallback() {
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
