package delta.soen390.mapsters.Services;

import android.content.Context;
import android.graphics.Path;
import android.opengl.Visibility;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.api.services.calendar.Calendar;
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
import delta.soen390.mapsters.Utils.GoogleMapstersUtils;

/**
 * Created by Mathieu on 3/1/2015.
 * DirectionEngine is used to poll directions from two points given certain predefined settings
 */
public class DirectionEngine {
    public enum DirectionType
    {
        TRANSIT,
        WALKING,
        BICYCLE,
        DRIVING,
        SHUTTLE,
    }

    private GoogleMap mMap;
    private GeoApiContext mGeoContext;
    private Context mAppContext;
    public ArrayList<DirectionPath> mDirectionPaths = new ArrayList<DirectionPath>();
    private LatLng mInitialLocation = null, mFinalLocation = null;
    public DirectionEngine(Context appContext, GoogleMap gMap)
    {
        mMap = gMap;
        mAppContext = appContext;
        mGeoContext = new GeoApiContext().setApiKey("AIzaSyCDsbX2OWOnFJRJ_oHMls-HRtncbpMc_qI");
    }

    public void setInitialLocation(LatLng initialLocation)
    {
        mInitialLocation = initialLocation;
    }

    public void setFinalLocation(LatLng finalLocation)
    {
        mFinalLocation = finalLocation;
    }
    public void updateDirectionPath( DirectionType... types  ) {

        clearEngineState();

        DirectionsRequestProvider directionProvider = new DirectionsRequestProvider(mAppContext, mGeoContext);
        //Engine has not been set up properly, empty path list returned.
        if (mInitialLocation == null || mFinalLocation == null) {
            return;
        }


        //Go through every direction type and generate the directionpath
        for (DirectionType dType : types) {
            DirectionPath path = new DirectionPath(mMap, dType);
            ArrayList<TravelResponseInfo> responseInfos = new ArrayList<TravelResponseInfo>();

            DirectionsApiRequest travelRequest = null;
            switch (dType) {
                case SHUTTLE:
                    DirectionsApiRequest toShuttleRequest, fromShuttleRequest;

                    Campus.Name startingCampus = Campus.getNearestCampus(mInitialLocation);
                    Campus.Name endCampus = Campus.getNearestCampus(mFinalLocation);

                    //If student is remaining on campus, he does not need to take the shuttle. Make him walk!
                    if (startingCampus == endCampus) {
                        travelRequest = directionProvider.getBasicRequest(mInitialLocation, mFinalLocation, TravelMode.WALKING);
                        responseInfos.add(new TravelResponseInfo(travelRequest));
                    } else {
                        travelRequest = directionProvider.getShuttleRequest(startingCampus);
                        TravelResponseInfo shuttleTravelResponseInfo = new TravelResponseInfo(travelRequest);

                        toShuttleRequest = directionProvider.getBasicRequest(mInitialLocation, shuttleTravelResponseInfo.getStartPoint(), TravelMode.WALKING);
                        fromShuttleRequest = directionProvider.getBasicRequest(shuttleTravelResponseInfo.getDestinationPoint(), mFinalLocation, TravelMode.WALKING);

                        //Add initial TravelResponseinfo first
                        responseInfos.add(new TravelResponseInfo(toShuttleRequest));
                        responseInfos.add(shuttleTravelResponseInfo);
                        responseInfos.add(new TravelResponseInfo(fromShuttleRequest));
                    }
                    break;

                case BICYCLE:
                    travelRequest = directionProvider.getBasicRequest(mInitialLocation, mFinalLocation, TravelMode.BICYCLING);
                    responseInfos.add(new TravelResponseInfo(travelRequest));
                    break;

                case DRIVING:
                    travelRequest = directionProvider.getBasicRequest(mInitialLocation, mFinalLocation, TravelMode.DRIVING);
                    responseInfos.add(new TravelResponseInfo(travelRequest));
                    break;

                case TRANSIT:
                    travelRequest = directionProvider.getBasicRequest(mInitialLocation, mFinalLocation, TravelMode.TRANSIT);
                    responseInfos.add(new TravelResponseInfo(travelRequest));
                    break;

                case WALKING:
                    travelRequest = directionProvider.getBasicRequest(mInitialLocation, mFinalLocation, TravelMode.WALKING);
                    responseInfos.add(new TravelResponseInfo(travelRequest));
                    break;

                default:
                    break;
            }

            //Create direction path if there are any valid travel responses
            if (!responseInfos.isEmpty()) {
                path.AddTravelResponseInfo(responseInfos);
                mDirectionPaths.add(path);
            }
        }
    }

    public void clearEngineState()
    {
        for(DirectionPath path : mDirectionPaths)
        {
            path.clear();
        }

        mDirectionPaths.clear();
    }

    public void showDirectionPath(DirectionType directionType)
    {
        for(DirectionPath path : mDirectionPaths)
        {
            if(path.getType() == directionType) {
                path.show();
            }
            else {
                path.hide();
            }
        }
    }

    public class DirectionPath
    {
        private GoogleMap mMap;
        private ArrayList<TravelResponseInfo.TravelStep> mTravelSteps;
        private ArrayList<Polyline> mPolylines = new ArrayList<Polyline>();
        private double  mTotalCost;
        private long    mTotalDuration;
        private boolean mIsDirty = false;

        private DirectionType mDirectionType;

        public DirectionPath(GoogleMap gMap, DirectionType directionType)
        {
            mTotalCost = 0;
            mTotalDuration = 0;
            mDirectionType = directionType;
            mMap = gMap;
            mTravelSteps = new ArrayList<TravelResponseInfo.TravelStep>();
            constructPath();
        }

        public DirectionType getType()
        {
            return mDirectionType;
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

        public void hide()
        {
            for(Polyline line : mPolylines)
            {
                line.setVisible(false);
            }
        }
        public void show()
        {
            if(mIsDirty)
            {
                constructPath();
            }

            for(Polyline line : mPolylines)
            {
                line.setVisible(true);
            }
        }

        public void clear()
        {
            for(Polyline line : mPolylines)
            {
                line.remove();
            }
            mPolylines.clear();
            mIsDirty = true;
        }

        private void constructPath()
        {
            clear();
            mIsDirty = false;
            //Every single travel step needs to have its has its own line.
            for(TravelResponseInfo.TravelStep step : mTravelSteps)
            {
                List<LatLng> linePoints = step.getEncodedPolyLine().decodePath();
                PolylineOptions option = new PolylineOptions();

                //Add all of the step's waypoints to the polyline option in order
                //to create a path
                for(LatLng point : linePoints)
                {
                    option.add(GoogleMapstersUtils.toMapsLatLng(point));
                }

                //Set the line color
                option.color(step.getColor());

                //Add the line on the map!
                mPolylines.add(mMap.addPolyline(option));
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
