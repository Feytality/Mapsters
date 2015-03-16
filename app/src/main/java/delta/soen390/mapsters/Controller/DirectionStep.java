package delta.soen390.mapsters.Controller;

import java.util.ArrayList;

/**
 * Created by Felicia on 2015-03-14.
 */
public class DirectionStep {
    private String mStep;
    private ArrayList<String> mSteps;
    private static final String STEP_PREFIX = "Step ";

    public void setStep(String step) {
        mStep = step;
    }

    public void setSteps(ArrayList<String> steps) {
        mSteps = steps;
    }

    public String getStep() {
        return mStep;
    }

    public ArrayList<String> getSteps() {
        return mSteps;
    }

    public String getStepPrefix() {
        return STEP_PREFIX;
    }
}
