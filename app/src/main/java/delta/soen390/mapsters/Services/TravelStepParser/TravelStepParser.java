package delta.soen390.mapsters.Services.TravelStepParser;

import android.content.Context;

import java.util.ArrayList;

import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.TravelResponseInfo;

/**
 * Created by Mathieu on 3/15/2015.
 */
public class TravelStepParser {

    ArrayList<ITravelStepParsingStrategy> mParsingStrategies = new ArrayList<ITravelStepParsingStrategy>();
    int mDefaultColor = 0;

    public TravelStepParser(Context c)
    {
        initializeStrategies(c);

        mDefaultColor = c.getResources().getColor(R.color.travel_step_default);
    }

    private void initializeStrategies(Context c)
    {
        mParsingStrategies.add( new TravelStepSubwayParser(c));
        mParsingStrategies.add( new TravelStepWalkingParser(c));
        mParsingStrategies.add(new TravelStepBusParser(c));
        mParsingStrategies.add(new TravelStepShuttleParser(c));
        mParsingStrategies.add(new TravelStepDrivingParser(c));
        //mParsingStrategies.add(new TravelStepDrivingParser(c));
       // mParsingStrategies.add( new )
    }

    public int getColor(TravelResponseInfo.TravelStep step)
    {
        for(ITravelStepParsingStrategy strategy : mParsingStrategies)
        {
            if(strategy.isValidString(step))
            {
                return strategy.getColor(step);
            }
        }
        return mDefaultColor;
    }

    public String getTag(TravelResponseInfo.TravelStep step)
    {
        for(ITravelStepParsingStrategy strategy : mParsingStrategies)
        {
            if(strategy.isValidString(step))
            {
                return strategy.getTag(step);
            }
        }
        return "";
    }
}
