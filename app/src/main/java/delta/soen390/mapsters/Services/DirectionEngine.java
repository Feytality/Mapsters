package delta.soen390.mapsters.Services;

import android.content.Context;
import android.graphics.Path;

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

import delta.soen390.mapsters.Data.Campus;

/**
 * Created by Mathieu on 3/1/2015.
 * DirectionEngine is used to poll directions from two points given certain predefined settings
 */
public class DirectionEngine {

    public enum DirectionPreference
    {
        STM_ONLY,
        SHUTTLE_ONLY,
        STM_SHUTTLE
    };

    private GoogleMap mMap;
    private GeoApiContext mGeoContext;
    private Context mAppContext;
    private DirectionPreference mDirectionPreference;

    public DirectionEngine(Context appContext, GoogleMap gMap)
    {
        mMap = gMap;
        mAppContext = appContext;
        mGeoContext = new GeoApiContext().setApiKey("AIzaSyCDsbX2OWOnFJRJ_oHMls-HRtncbpMc_qI");
        mDirectionPreference = DirectionPreference.STM_ONLY;
    }



    public void SetPreference(DirectionPreference preference)
    {
        mDirectionPreference = preference;
    }

    public DirectionPath GenerateDirectionPath(LatLng initialLocation, LatLng finalLocation)
    {
        Campus.Name initialCampus   = Campus.getNearestCampus(initialLocation);
        Campus.Name finalCampus     = Campus.getNearestCampus(finalLocation);

        DirectionsRequestProvider directionProvider = new DirectionsRequestProvider(mAppContext,mGeoContext);

        DirectionPath path = new DirectionPath(mMap);

        ArrayList<TravelResponseInfo> travelResponses = new ArrayList<TravelResponseInfo>();

        //Cross campus travel
        if(initialCampus != finalCampus)
        {
            long shuttleDuration = 0;
            if(mDirectionPreference == DirectionPreference.SHUTTLE_ONLY || mDirectionPreference == DirectionPreference.STM_SHUTTLE){

                //Represents the 3 legs of the user's travel route
                DirectionsApiRequest initialRequest,transitRequest,finalRequest;

                transitRequest = directionProvider.getNextShuttle(initialCampus);
                TravelResponseInfo transitTravelInfo = new TravelResponseInfo(transitRequest);

                initialRequest  = directionProvider.getBasicRequest(initialLocation,transitTravelInfo.getStartPoint(),TravelMode.WALKING);
                finalRequest    = directionProvider.getBasicRequest(transitTravelInfo.getDestinationPoint(),finalLocation,TravelMode.WALKING);

                //Add initial TravelResponseinfo first
                travelResponses.add(new TravelResponseInfo(initialRequest));

                //Transit route goes in after
                travelResponses.add(transitTravelInfo);

                travelResponses.add(new TravelResponseInfo(finalRequest));

                if(mDirectionPreference == DirectionPreference.STM_SHUTTLE) {
                    for (int i = 0; i < travelResponses.size(); ++i) {
                        shuttleDuration += travelResponses.get(i).getTotalDuration();
                    }
                }
            }
            else if(mDirectionPreference == DirectionPreference.STM_ONLY) {
                DirectionsApiRequest request = directionProvider.getStmRequest(initialLocation, finalLocation);
                travelResponses.add(new TravelResponseInfo(request));
            }
            if(mDirectionPreference == DirectionPreference.STM_SHUTTLE) {
                DirectionsApiRequest request = directionProvider.getStmRequest(initialLocation, finalLocation);
                TravelResponseInfo stmTravelInfo = new TravelResponseInfo(request);
                if(stmTravelInfo.getTotalDuration() < shuttleDuration)
                {
                    travelResponses.clear();
                    travelResponses.add(stmTravelInfo);
                }
            }
        }
        //Inner campus travel, not shuttle/stm
        else
        {
            DirectionsApiRequest request = directionProvider.getBasicRequest(initialLocation,finalLocation,TravelMode.WALKING);
            travelResponses.add(new TravelResponseInfo(request));
        }

        path.AddTravelResponseInfo(travelResponses);
        return path;
    }

    public class DirectionPath
    {
        private GoogleMap mMap;
        private ArrayList<TravelResponseInfo.TravelStep> mTravelSteps;
        private double  mTotalCost;
        private long    mTotalDuration;
        private boolean mIsDirty = false;
        private PolylineOptions mPolylineOptions;
        private Polyline mPolyline;

        public DirectionPath(GoogleMap gMap)
        {
            mTotalCost = 0;
            mTotalDuration = 0;

            mMap = gMap;
            mTravelSteps = new ArrayList<TravelResponseInfo.TravelStep>();
            constructPath();
        }

        public void AddTravelResponseInfo(TravelResponseInfo info)
        {
            mIsDirty = true;
            ArrayList<TravelResponseInfo.TravelStep> travelSteps = info.getTravelSteps();

            mTotalCost += info.getTotalCost();
            mTotalDuration += info.getTotalDuration();
            for(int i = 0; i < travelSteps.size(); ++i)
            {
                TravelResponseInfo.TravelStep step = travelSteps.get(i);
                mTravelSteps.add(step);
            }


        }

        public void AddTravelResponseInfo(ArrayList<TravelResponseInfo> travelResponseInfos)
        {
            for(int i = 0; i < travelResponseInfos.size(); ++i) {
                AddTravelResponseInfo(travelResponseInfos.get(i));
            }
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
            mPolylineOptions = new PolylineOptions();
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

                    mPolylineOptions.add(point);
                }
            }
        }

        public double getTotalCost(){
            return mTotalCost;
        }

        public long getTotalDuration(){
            return mTotalDuration;
        }

    }


}
