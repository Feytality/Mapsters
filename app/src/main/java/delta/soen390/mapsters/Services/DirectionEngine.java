package delta.soen390.mapsters.Services;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.model.LatLng;

import com.google.android.gms.maps.model.PolylineOptions;
import com.google.common.collect.MapMaker;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.TravelMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu on 3/1/2015.
 * DirectionEngine is used to poll directions from two points given certain predefined settings
 */
public class DirectionEngine {

    public enum DirectionPreference
    {
        STM_ONLY,
        SHUTTLE_ONLY,
        STM_SHUTTLE_ONLY
    };

    private GoogleMap mMap;
    private GeoApiContext mGeoContext;
    private Context mAppContext;
    public DirectionEngine(Context appContext, GoogleMap gMap)
    {
        mMap = gMap;
        mAppContext = appContext;
        mGeoContext = new GeoApiContext().setApiKey("AIzaSyCDsbX2OWOnFJRJ_oHMls-HRtncbpMc_qI");
    }

    private DirectionPreference mDirectionPreference;

    public void SetPreference(DirectionPreference preference)
    {
        mDirectionPreference = preference;
    }

    public DirectionPath GenerateDirectionPath(LatLng initialLocation, LatLng finalLocation)
    {
        boolean isSameCampus = isSameCampus(initialLocation,finalLocation);
        DirectionsRequestProvider directionProvider = new DirectionsRequestProvider(mAppContext,mGeoContext);

        DirectionsApiRequest request = directionProvider.getBasicRequest(initialLocation,finalLocation,TravelMode.WALKING);
        TravelResponseInfo travelResponseInfo = new TravelResponseInfo(request);

        DirectionPath path = new DirectionPath(mMap);

        return path;
    }

    private boolean isSameCampus(LatLng p1, LatLng p2)
    {
       //Latitude delimiter for campus
        double campusLatitudeDelimiter = 45.48;

        return (p1.lat      < campusLatitudeDelimiter
                && p2.lat   < campusLatitudeDelimiter)
                || (p1.lat  > campusLatitudeDelimiter
                && p2.lat   > campusLatitudeDelimiter);
    }

    public class DirectionPath
    {
        private GoogleMap mMap;
        private ArrayList<TravelResponseInfo.TravelStep> mTravelSteps;
        private boolean mIsDirty = false;
        private PolylineOptions mPolylineOptions;
        private Polyline mPolyline;

        public DirectionPath(GoogleMap gMap)
        {
            mMap = gMap;
            mTravelSteps = new ArrayList<TravelResponseInfo.TravelStep>();
            constructPath();
        }

        public void AddTravelStep(TravelResponseInfo.TravelStep travelStep)
        {
            mIsDirty = true;
        }

        public void showDirectionPath()
        {
            if(mIsDirty)
            {
                constructPath();
                mIsDirty = false;
            }
            mPolyline = mMap.addPolyline(mPolylineOptions);
        }


        public void hideDirectionPath()
        {
            if(mPolyline == null)
                return;
            mPolyline.remove();

        }

        public void constructPath()
        {
            PolylineOptions path = new PolylineOptions();
            for(int i = 0; i < mTravelSteps.size(); ++i)
            {
                TravelResponseInfo.TravelStep travelStep = mTravelSteps.get(i);
                List<LatLng> linePoints = travelStep.getEncodedPolyLine().decodePath();

                //Add all the travel step's subpoints to the path
                for(int j = 0; j < linePoints.size(); ++j)
                {
                    LatLng p = linePoints.get(j);

                    //Polyline only accepts LatLngs of the gms.maps.model package
                    com.google.android.gms.maps.model.LatLng point =
                            new com.google.android.gms.maps.model.LatLng( p.lat,p.lng);

                    path.add(point);
                }
            }
        }

    }


}
