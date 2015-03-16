package delta.soen390.mapsters.Services.TravelStepParser;

import android.graphics.Color;

/**
 * Created by Mathieu on 3/14/2015.
 *
 */
public interface ITravelStepParsingStrategy {


    //Must return whether the string passed is valid according to this strategy
    boolean isValidString(String str);

    //If possible, extracts the tag from the string
    //Returns empty string if no tag has been found
    String getTag(String str);

    int getColor(String str);



}
