package delta.soen390.mapsters.Utils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by georgevalergas on 15-03-29.
 */
public class ImageMarkerFactory {
    GoogleMap mGoogleMap;
    MarkerOptions mMarkerOptions;
    public ImageMarkerFactory(GoogleMap googleMap, LatLng latLng, int resourceId) {
        mGoogleMap = googleMap;
        mMarkerOptions = new MarkerOptions();
        mMarkerOptions
            .position(latLng)
            .icon(BitmapDescriptorFactory.fromResource(resourceId))
            .draggable(false);
    }

    public Marker placeMarker() {
        return mGoogleMap.addMarker(mMarkerOptions);
    }


}
