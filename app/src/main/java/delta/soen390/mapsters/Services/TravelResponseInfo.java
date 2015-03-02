package delta.soen390.mapsters.Services;

import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.Iterator;


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
