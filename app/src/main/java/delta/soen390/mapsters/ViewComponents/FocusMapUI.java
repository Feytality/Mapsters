package delta.soen390.mapsters.ViewComponents;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;

import delta.soen390.mapsters.Activities.MapsActivity;

/**
 * Created by Felicia on 2015-02-13.
 */
public class FocusMapUI implements LocationListener, LocationSource {

    private GoogleMap mMap;
    private MapsActivity mMapsActivity;

    // Functionality of the button.
    private OnLocationChangedListener mListener;
    private LocationManager mLocationManager;

    // For debugging purposes
    private boolean debug = false;

    public FocusMapUI(GoogleMap map, MapsActivity mapsActivity) {
        mMap = map;
        mMapsActivity = mapsActivity;

        determineGpsEnabled();
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap map) {
        mMap = map;
    }

    public MapsActivity getmMapsActivity() {
        return mMapsActivity;
    }

    public LocationManager getmLocationManager() {
        return mLocationManager;
    }


    /**
     * Responsible for determining if the GPS functionality is disabled on the device.
     */
    public void determineGpsEnabled() {
        mLocationManager = (LocationManager) mMapsActivity.getSystemService(mMapsActivity.LOCATION_SERVICE);

        if(mLocationManager != null) {
            boolean gpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(gpsEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 10F, this);
            }
            else if(networkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10F, this);
            }
            else {
                Toast.makeText(mMapsActivity.getApplicationContext(), "GPS is disabled on this device.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(mMapsActivity.getApplicationContext(), "Location Manager is null.", Toast.LENGTH_SHORT).show();
        }

    }

    public void removeUpdates() {
        if(mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }

    }

    public void setLocationSource() {
        mMap.setLocationSource(this);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if( mListener != null ) {
            mListener.onLocationChanged(location);
            // Moves the camera to where the user is positioned.
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //if(debug) Toast.makeText(this, "Status changed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        //if(debug) Toast.makeText(this, "Provider enabled.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        //if(debug) Toast.makeText(this, "Provider disabled.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }
}
