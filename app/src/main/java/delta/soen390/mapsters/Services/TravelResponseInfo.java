package delta.soen390.mapsters.Services;

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


    public TravelResponseInfo(long startTime, long arrivalTime, EncodedPolyline encodedPolyline, ArrayList<TravelStep> travelSteps) {
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        List<LatLng> decodedLinePoints = encodedPolyline.decodePath();
        this.startPoint = decodedLinePoints.get(0);
        this.destinationPoint = decodedLinePoints.get(decodedLinePoints.size() -1);
        this.travelSteps = travelSteps;
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

    private class TravelStep {

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
