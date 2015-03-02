package delta.soen390.mapsters.Services;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by georgevalergas on 15-03-01.
 */
public class TravelResponseInfo {
    //start & arrival time in milliseconds
    private long startTime;
    private long arrivalTime;
    private LatLng startPoint;
    private LatLng destinationPoint;
    private ArrayList<TravelStep> travelSteps;

    //Aladin's genie constructor. Takes request, fulfills request, but only 1.
    public TravelResponseInfo(DirectionsApiRequest directionsApiRequest) {
        try {
            DirectionsRoute[] directionsRoutes = directionsApiRequest.await();
            EncodedPolyline encodedPolyline = directionsRoutes[0].overviewPolyline;
            List<LatLng> directionsLinePoints = directionsRoutes[0].overviewPolyline.decodePath();
            LatLng startPoint = directionsLinePoints.get(0);
            LatLng destinationPoint = directionsLinePoints.get(directionsLinePoints.size() -1);
            long startTime = directionsRoutes[0].legs[0].departureTime.toDate().getTime();
            long arrivalTime = directionsRoutes[0].legs[0].arrivalTime.toDate().getTime();
            DirectionsStep[] directionsSteps = directionsRoutes[0].legs[0].steps;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TravelResponseInfo(long startTime, long arrivalTime, EncodedPolyline encodedPolyline, DirectionsStep[] directionsSteps) {
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        List<LatLng> decodedLinePoints = encodedPolyline.decodePath();
        this.startPoint = decodedLinePoints.get(0);
        this.destinationPoint = decodedLinePoints.get(decodedLinePoints.size() -1);
        this.travelSteps = travelSteps;
        this.travelSteps = directionsToTravelAdapter(directionsSteps);
    }

    public long getStartTime() {
        return this.startTime;
    }
    public long getArrivalTime() {
        return this.arrivalTime;
    }

    public ArrayList<TravelStep> getTravelSteps() {
        return  this.travelSteps;
    }
    public Iterator<TravelStep> getIterator()
    {
        return travelSteps.iterator();
    }

    public LatLng getStartPoint() {
        return startPoint;
    }
    public LatLng getDestinationPoint() {
        return destinationPoint;
    }

    private ArrayList<TravelStep> directionsToTravelAdapter(DirectionsStep[] directionsSteps) {
        ArrayList<TravelStep> travelSteps = new ArrayList<>();
        for (DirectionsStep dubstep : directionsSteps) {
            TravelStep t = new TravelStep(dubstep.htmlInstructions, dubstep.polyline);
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
