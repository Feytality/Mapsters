package delta.soen390.mapsters.Services.TravelStepParser;

import android.content.Context;

import java.util.ArrayList;

import delta.soen390.mapsters.R;

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
       // mParsingStrategies.add( new )
    }

    public int getColor(String str)
    {
        for(ITravelStepParsingStrategy strategy : mParsingStrategies)
        {
            if(strategy.isValidString(str))
            {
                return strategy.getColor(str);
            }
        }
        return 0;
    }

    public String getTag(String str)
    {
        for(ITravelStepParsingStrategy strategy : mParsingStrategies)
        {
            if(strategy.isValidString(str))
            {
                return strategy.getTag(str);
            }
        }
        return "";
    }
}
