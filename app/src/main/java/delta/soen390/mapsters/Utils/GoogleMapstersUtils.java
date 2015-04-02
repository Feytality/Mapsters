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
    public static float getBoundsZoomLevel(LatLng northeast,LatLng southwest,
                                    int width, int height) {

        final int GLOBE_WIDTH = 256; // a constant in Google's map projection
        final int ZOOM_MAX = 21;
        double latFraction = (latRad(northeast.latitude) - latRad(southwest.latitude)) / Math.PI;
        double lngDiff = northeast.longitude - southwest.longitude;
        double lngFraction = ((lngDiff < 0) ? (lngDiff + 360) : lngDiff) / 360;
        double latZoom = zoom(height, GLOBE_WIDTH, latFraction);
        double lngZoom = zoom(width, GLOBE_WIDTH, lngFraction);
        double zoom = Math.min(Math.min(latZoom, lngZoom),ZOOM_MAX);
        return (float)(zoom) - 0.85f;
    }
    private static double latRad(double lat) {
        double sin = Math.sin(lat * Math.PI / 180);
        double radX2 = Math.log((1 + sin) / (1 - sin)) / 2;
        return Math.max(Math.min(radX2, Math.PI), -Math.PI) / 2;
    }
    private static double zoom(double mapPx, double worldPx, double fraction) {
        final double LN2 = .693147180559945309417;
        return (Math.log(mapPx / worldPx / fraction) / LN2);
    }
}
