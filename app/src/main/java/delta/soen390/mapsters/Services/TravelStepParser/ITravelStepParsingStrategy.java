package delta.soen390.mapsters.Services.TravelStepParser;

import android.graphics.Color;

import delta.soen390.mapsters.Services.TravelResponseInfo;

/**
 * Created by Mathieu on 3/14/2015.
 *
 */
public interface ITravelStepParsingStrategy {


    //Must return whether the string passed is valid according to this strategy
    boolean isValidString(TravelResponseInfo.TravelStep str);

    //If possible, extracts the tag from the string
    //Returns empty string if no tag has been found
    String getTag(TravelResponseInfo.TravelStep str);

    int getColor(TravelResponseInfo.TravelStep str);



}
