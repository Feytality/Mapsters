package delta.soen390.mapsters.Services.TravelStepParser;

import android.content.Context;

import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.TravelResponseInfo;

/**
 * Created by Mathieu on 3/14/2015.
 */
public class TravelStepShuttleParser implements ITravelStepParsingStrategy {

    private int mLineColor = 0;
    public TravelStepShuttleParser(Context c)
    {
        mLineColor = c.getResources().getColor(R.color.travel_step_shuttle);
    }
    //Must return whether the string passed is valid according to this strategy
    public boolean isValidString(TravelResponseInfo.TravelStep step)
    {
        return step.getDirectionType() == DirectionEngine.DirectionType.SHUTTLE;
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
