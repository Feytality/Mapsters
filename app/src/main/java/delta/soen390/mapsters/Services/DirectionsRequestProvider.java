package delta.soen390.mapsters.Services;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

/**
 * Created by georgevalergas on 15-03-01.
 */
public class DirectionsRequestProvider {
    private GeoApiContext geoApiContext;
    private static final LatLng sgwShuttle = new LatLng(45.4971514,-73.5787977);
    private static final LatLng loyShuttle = new LatLng(45.458949,-73.6383713);
    public enum Campus {LOY, SGW}


    public DirectionsApiRequest getNextShuttle(GeoApiContext geoApiContext, Campus startCampus) {
        DirectionsApiRequest dar = DirectionsApi.newRequest(geoApiContext);
        if (startCampus.equals(Campus.LOY)) {
            dar.origin(loyShuttle).destination(sgwShuttle);
        } else {
            dar.origin(sgwShuttle).destination(loyShuttle);
        }
        dar.mode(TravelMode.DRIVING);
        return dar;
    }
}
