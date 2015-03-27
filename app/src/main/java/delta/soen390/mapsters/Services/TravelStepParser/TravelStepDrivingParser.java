package delta.soen390.mapsters.Services.TravelStepParser;

import android.content.Context;

import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.TravelResponseInfo;

/**
 * Created by Mathieu on 3/23/2015.
 */
public class TravelStepDrivingParser implements ITravelStepParsingStrategy {

    private int mLineColor = 0;
    public TravelStepDrivingParser(Context c)
    {
        mLineColor = c.getResources().getColor(R.color.travel_step_driving);
    }
    //Must return whether the string passed is valid according to this strategy
    public boolean isValidString(TravelResponseInfo.TravelStep step)
    {
        return step.getDirectionType() == DirectionEngine.DirectionType.DRIVING;
    }

    //If possible, extracts the tag from the string
    //Returns empty string if no tag has been found
    public String getTag(TravelResponseInfo.TravelStep str)
    {
        return "";
    }

    public int getColor(TravelResponseInfo.TravelStep str)
    {
        return mLineColor;
    }
}
