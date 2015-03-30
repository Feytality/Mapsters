package delta.soen390.mapsters.Effects;

import android.graphics.Color;

/**
 * Created by Mathieu on 3/29/2015.
 */
public class HighlightEffect  {

    private Integer mStartingColor = null, mEndColor = null;
    private float mPeriod = 1000;
    private long mPreviousTime = 0;
    private float mCurrentOffset = 0;

    public HighlightEffect(int startingColor)
    {
        mStartingColor = startingColor;
        mPreviousTime = System.currentTimeMillis();
    }
    public HighlightEffect(int r, int g, int b, int a)
    {
        mStartingColor = Color.argb(a,r,g,b);
    }

    public HighlightEffect setPeriod(float millis)
    {
       mPeriod = millis;
        return this;
    }

    public HighlightEffect setEndColor(int c)
    {
        mEndColor = c;
        return this;
    }

    public HighlightEffect setEndColor(int r, int g, int b, int a)
    {
        mEndColor = Color.argb(a,r,g,b);
        return this;
    }

    public int updateEffect()
    {
        if(mEndColor == null) {
            return mStartingColor;
        }
        long deltaTime = System.currentTimeMillis() - mPreviousTime;
        //mCurrentOffset +=
        //float angle = deltaTime
        return mStartingColor;
    }
}
