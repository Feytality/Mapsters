package delta.soen390.mapsters.Services;

import android.graphics.Color;
import android.graphics.Path;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import delta.soen390.mapsters.Services.TravelStepParser.TravelStepParser;


//TODO fetch a valid total cost
/**
 * Created by georgevalergas on 15-03-01.
 */
public class TravelResponseInfo {
    //start & arrival time in milliseconds
    private long    mStartTime;
    private long    mArrivalTime;
    private LatLng  mStartPoint;
    private LatLng  mDestinationPoint;
    private ArrayList<TravelStep> mTravelSteps = new ArrayList<TravelStep>();

    //Total travel duration in seconds
    private long    mTotalDuration;
    private double  mTotalCost;

    //Aladin's genie constructor. Takes request, fulfills request, but only 1.
    public TravelResponseInfo(DirectionsApiRequest directionsApiRequest) {
        try {
            DirectionsRoute[] directionsRoutes  = directionsApiRequest.await();
            List<LatLng> directionsLinePoints   = directionsRoutes[0].overviewPolyline.decodePath();

            DirectionsLeg leg = directionsRoutes[0].legs[0];
            mStartPoint         = directionsLinePoints.get(0);
            mDestinationPoint   = directionsLinePoints.get(directionsLinePoints.size() -1);

            DateTime departureTime  = leg.departureTime;
            DateTime arrivalTime    =  leg.arrivalTime;

            mTotalDuration          = leg.duration.inSeconds;
            mTotalCost              = 0.0;
            if( departureTime != null && arrivalTime != null){
                mStartTime          = departureTime.toDate().getTime();
                mArrivalTime        = arrivalTime.toDate().getTime();
            }

            mTravelSteps        = directionsToTravelSteps(leg.steps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setShuttleTravel()
    {
        ArrayList<LatLng> shuttlePoints = new ArrayList<>();
        for(TravelStep step : mTravelSteps)
        {
            if(step.getDirectionType() == DirectionEngine.DirectionType.DRIVING)
            {
                EncodedPolyline polyline = step.getEncodedPolyLine();
                shuttlePoints.addAll(polyline.decodePath());
            }
        }

        mTravelSteps.clear();
        EncodedPolyline polyline = new EncodedPolyline(shuttlePoints);
        mTravelSteps.add(new TravelStep("Concordia's Shuttle", DirectionEngine.DirectionType.SHUTTLE,polyline));

    }

    public TravelResponseInfo(long startTime, long arrivalTime, EncodedPolyline encodedPolyline, DirectionsStep[] directionsSteps) {
        mStartTime         = startTime;
        mArrivalTime       = arrivalTime;
        mTotalCost         = 0.0;
        List<LatLng> decodedLinePoints = encodedPolyline.decodePath();
        mStartPoint        = decodedLinePoints.get(0);
        mDestinationPoint  = decodedLinePoints.get(decodedLinePoints.size() -1);
        mTravelSteps       = directionsToTravelSteps(directionsSteps);
    }

    public long getStartTime() {
        return mStartTime;
    }

    public long getArrivalTime() {
        return mArrivalTime;
    }

    public ArrayList<TravelStep> getTravelSteps() {
        return  mTravelSteps;
    }

    public Iterator<TravelStep> getIterator()
    {
        return mTravelSteps.iterator();
    }

    public LatLng getStartPoint() {
        return mStartPoint;
    }

    public LatLng getDestinationPoint() {
        return mDestinationPoint;
    }

    public long getTotalDuration()
    {
        return mTotalDuration;
    }

    public double getTotalCost() { return mTotalCost;}

    private ArrayList<TravelStep> directionsToTravelSteps(DirectionsStep[] directionsSteps) {
        ArrayList<TravelStep> travelSteps = new ArrayList<>();
        for (DirectionsStep step : directionsSteps) {
            String stepDescription = step.htmlInstructions;
            EncodedPolyline stepLine = step.polyline;
            DirectionEngine.DirectionType type;
            switch(step.travelMode)
            {
                case TRANSIT:
                    type = DirectionEngine.DirectionType.TRANSIT;
                    break;
                case WALKING:
                    type = DirectionEngine.DirectionType.WALKING;
                    break;
                case BICYCLING:
                    type = DirectionEngine.DirectionType.BICYCLE;
                    break;
                case DRIVING:
                    type = DirectionEngine.DirectionType.DRIVING;
                    break;
                default:
                    type = DirectionEngine.DirectionType.DRIVING;
                    break;
            }
            travelSteps.add(new TravelStep(stepDescription,type,stepLine));
        }
        return travelSteps;
    }

    public class TravelStep {

        private int mPathColor = 0;
        private String mDisplayTag = "";
        private String mDescription;
        private EncodedPolyline mEncodedPolyLine;
        private DirectionEngine.DirectionType mDirectionType;

        public TravelStep(String description,DirectionEngine.DirectionType type, EncodedPolyline encodedPolyLine) {
            mDescription = description;
            mEncodedPolyLine = encodedPolyLine;
            mDirectionType = type;

        }

        public void loadAttributes(TravelStepParser parser) {

            mDisplayTag = parser.getTag(this);
            mPathColor = parser.getColor(this);
        }

        public DirectionEngine.DirectionType getDirectionType() { return mDirectionType;}
        public String getDisplayTag() { return mDisplayTag;}
        public String getDescription() {
            return mDescription;
        }
        public int getColor() { return mPathColor;}
        public  EncodedPolyline getEncodedPolyLine() {
            return mEncodedPolyLine;
        }



    }
}
