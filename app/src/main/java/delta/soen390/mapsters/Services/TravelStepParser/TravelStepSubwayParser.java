package delta.soen390.mapsters.Services.TravelStepParser;

import android.content.Context;

import java.util.ArrayList;

import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 3/14/2015.
 */
public class TravelStepSubwayParser implements ITravelStepParsingStrategy {
    private static ArrayList<SubwayLineParser> mSubwayParsers = new ArrayList<SubwayLineParser>();

    private void initializeSubwayParsers(Context c) {
        if(mSubwayParsers.isEmpty())
        {
            //TODO Fetch values dynamically

            mSubwayParsers.clear();

            // Green Line initialization
            String greenLineTerminal1 = "Angrignon";
            String greenLineTerminal2 = "Honoré-Beaugrand";
            mSubwayParsers.add( new SubwayLineParser(greenLineTerminal1,greenLineTerminal2,
                    c.getResources().getColor(R.color.travel_step_subway_green_line),
                    "Green Line"));

            //Orange Line initialization
            String orangeLineTerminal1 = "Montmorency";
            String orangeLineTerminal2 = "Côte-Vertu";
            mSubwayParsers.add( new SubwayLineParser(orangeLineTerminal1,orangeLineTerminal2,
                    c.getResources().getColor(R.color.travel_step_subway_orange_line),
                    "Orange Line"));

            //Orange Line initialization
            String blueLineTerminal1 = "Snowdon";
            String blueLineTerminal2 = "Saint-Michel";
            mSubwayParsers.add( new SubwayLineParser(blueLineTerminal1,blueLineTerminal2,
                    c.getResources().getColor(R.color.travel_step_subway_blue_line),
                    "Blue Line"));

            //Orange Line initialization
            String yellowLineTerminal1 = "Berri-UQAM";
            String yellowLineTerminal2 = "Longueuil";
            mSubwayParsers.add( new SubwayLineParser(yellowLineTerminal1,yellowLineTerminal2,
                    c.getResources().getColor(R.color.travel_step_subway_yellow_line),
                    "Yellow Line"));
        }
    }
    public TravelStepSubwayParser(Context c)
    {
        initializeSubwayParsers(c);
    }


    //Must return whether the string passed is valid according to this strategy
    public boolean isValidString(String str){
        for(int i = 0; i < mSubwayParsers.size(); ++i) {
            SubwayLineParser parser = mSubwayParsers.get(i);
            if(parser.isValid(str))
                return true;
        }
        return false;
    }

    public String getTag(String str)
    {
        for(int i = 0; i < mSubwayParsers.size(); ++i)
        {
            SubwayLineParser parser = mSubwayParsers.get(i);
            if(parser.isValid(str))
            {
                return parser.getTag();
            }
        }
        return "";
    }

    public int getColor(String str)
    {
        for(int i = 0; i < mSubwayParsers.size(); ++i)
        {
            SubwayLineParser parser = mSubwayParsers.get(i);
            if(parser.isValid(str))
            {
                return parser.getColor();
            }
        }
        return 0;
    }



    // SubwayLineParser class
    // The SubwayLineParser class handles parsing related to the different metro lines
    private class SubwayLineParser{

        //The two terminal stations of the line
        private String[] mTerminals = new String[2];
        private int mColor;
        private String mTag;
        private final String sSubwayTag = "Subway";
        public SubwayLineParser(String firstTerminal, String secondTerminal, int color, String tag)
        {
            mTerminals[0] = firstTerminal;
            mTerminals[1] = secondTerminal;
            mColor = color;
            mTag = tag;
        }

        public int getColor() {

            return mColor;
        }

        public boolean isValid(String str)
        {
            for(int i = 0; i < mTerminals.length; ++i)
            {
                if(str.contains(mTerminals[i]) && str.contains(sSubwayTag))
                    return true;
            }
            return false;
        }

        public String getTag()
        {
            return mTag;
        }
    }
}
