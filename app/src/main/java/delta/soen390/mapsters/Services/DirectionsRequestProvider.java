package delta.soen390.mapsters.Services;

import android.content.Context;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.sql.Driver;
import java.util.Date;

import delta.soen390.mapsters.Data.Campus;

/**
 * Created by georgevalergas on 15-03-01.
 */
public class DirectionsRequestProvider {
    private GeoApiContext   mGeoApiContext;
    private Context         mAppContext;

    private static final LatLng sgwShuttle = new LatLng(45.4971514,-73.5787977);
    private static final LatLng loyShuttle = new LatLng(45.458949,-73.6383713);


    public DirectionsRequestProvider(Context applicationContext, GeoApiContext geoContext)
    {
        mGeoApiContext  = geoContext;
        mAppContext     = applicationContext;
    }


    public DirectionsApiRequest getBasicRequest(LatLng initial, LatLng destination, TravelMode mode)
    {
        return DirectionsApi.newRequest(mGeoApiContext)
                .origin(initial)
                .destination(destination)
                .mode(mode);
    }

    public DirectionsApiRequest getNextShuttle(Campus.Name startCampus) {

        DirectionsApiRequest dar;

        //time next shuttle departs in milliseconds
        long nextDeparture;
        if (startCampus.equals(Campus.Name.LOY)) {
            dar = getBasicRequest(loyShuttle,sgwShuttle,TravelMode.DRIVING);
            nextDeparture = ShuttleUtils.getNextBusLoy(new Date(),mAppContext).getTime();
        } else {
            dar = getBasicRequest(sgwShuttle,loyShuttle,TravelMode.DRIVING);
            nextDeparture = ShuttleUtils.getNextBusSgw(new Date(),mAppContext).getTime();
        }

        dar.departureTime(new DateTime(nextDeparture));
        return dar;
    }

    public DirectionsApiRequest getStmRequest(LatLng initial, LatLng destination)
    {
        DirectionsApiRequest request = getBasicRequest(initial, destination, TravelMode.TRANSIT);
        request.departureTime(new DateTime());
        return request;
    }
}
