package delta.soen390.mapsters.Services;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by georgevalergas on 15-03-01.
 */
public class TravelResponseInfo {
    //start & arrival time in milliseconds
    private long    mStartTime;
    private long    mArrivalTime;
    private LatLng  mStartPoint;
    private LatLng  mDestinationPoint;
    private ArrayList<TravelStep> mTravelSteps;
    private long    mDuration;


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

            mDuration               = leg.duration.inSeconds;
            if( departureTime != null && arrivalTime != null){
                mStartTime          = departureTime.toDate().getTime();
                mArrivalTime        = arrivalTime.toDate().getTime();
            }

            mTravelSteps        = directionsToTravelSteps(leg.steps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TravelResponseInfo(long startTime, long arrivalTime, EncodedPolyline encodedPolyline, DirectionsStep[] directionsSteps) {
        this.mStartTime         = startTime;
        this.mArrivalTime       = arrivalTime;
        List<LatLng> decodedLinePoints = encodedPolyline.decodePath();
        this.mStartPoint        = decodedLinePoints.get(0);
        this.mDestinationPoint  = decodedLinePoints.get(decodedLinePoints.size() -1);
        this.mTravelSteps       = directionsToTravelSteps(directionsSteps);
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

    public long getDuration()
    {
        return mDuration;
    }

    private ArrayList<TravelStep> directionsToTravelSteps(DirectionsStep[] directionsSteps) {
        ArrayList<TravelStep> travelSteps = new ArrayList<>();
        for (DirectionsStep step : directionsSteps) {
            TravelStep t = new TravelStep(step.htmlInstructions, step.polyline);
            travelSteps.add(t);
        }
        return travelSteps;
    }

    public class TravelStep {
        public TravelStep(String stepName, EncodedPolyline encodedPolyLine) {
            this.stepName = stepName;
            this.encodedPolyLine = encodedPolyLine;
        }
        private String stepName;
        private EncodedPolyline encodedPolyLine;

        public String getStepName() {
            return this.stepName;
        }
        public  EncodedPolyline getEncodedPolyLine() {
            return this.encodedPolyLine;
        }



    }
}
