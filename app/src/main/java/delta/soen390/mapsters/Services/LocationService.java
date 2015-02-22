package delta.soen390.mapsters.Services;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;

public class LocationService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private LocationSource.OnLocationChangedListener mLocationListener;
    private static final LocationRequest REQUEST = LocationRequest.create().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    public LocationService(Context context) {
        mContext = context;
        mGoogleApiClient = buildGoogleLocationApiClient();
        mLocationListener = null;
    }

    protected synchronized GoogleApiClient buildGoogleLocationApiClient() {
        return new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("Google Location Service","Connected");
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                REQUEST,
                this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Google Location Service","Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Google Location Service","Failed");
    }

    public Location getLastLocation(){
        return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setLocationListener(LocationSource.OnLocationChangedListener listener){
        mLocationListener = listener;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mLocationListener != null) {
            mLocationListener.onLocationChanged(location);
        }
    }
}
