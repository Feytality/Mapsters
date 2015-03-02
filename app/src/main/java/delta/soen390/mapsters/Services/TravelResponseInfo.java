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
    private ArrayList<TravelStep> travelSteps;

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


    public class TravelStep {

        public TravelStep(String stepName, EncodedPolyline encodedPolyLine) {
            this.stepName = stepName;
            this.decodedLinePoints = encodedPolyLine.decodePath();
        }
        private String stepName;
        private List<LatLng> decodedLinePoints;

        public String getStepName() {
            return this.stepName;
        }
        public LatLng getStartPoint() {
            return decodedLinePoints.get(0);
        }
        public LatLng getDestinationPoint() {
            return decodedLinePoints.get(decodedLinePoints.size() -1);
        }




    }
}
