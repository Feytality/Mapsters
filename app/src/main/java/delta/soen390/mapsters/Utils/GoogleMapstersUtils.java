package delta.soen390.mapsters.Utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Mathieu on 3/1/2015.
 */
public class GoogleMapstersUtils {
    public static com.google.android.gms.maps.model.LatLng toMapsLatLng(com.google.maps.model.LatLng latLng)
    {
        return new com.google.android.gms.maps.model.LatLng(latLng.lat, latLng.lng);
    }

    public static com.google.maps.model.LatLng toDirectionsLatLng(com.google.android.gms.maps.model.LatLng latLng)
    {
        return new com.google.maps.model.LatLng(latLng.latitude,latLng.longitude);
    }
}
