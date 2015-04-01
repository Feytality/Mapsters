package delta.soen390.mapsters.ViewMode;

import java.util.ArrayList;
import java.util.Collection;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Effects.EffectManager;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;

/**
 * Created by Mathieu on 3/29/2015.
 */
public class ViewModeController {

    private ViewMode mCurrentViewMode;
    private Collection<PolygonOverlay> mCurrentlyActiveHighlights = new ArrayList<PolygonOverlay>();

    private final MapsActivity mActivity;
    public ViewModeController( MapsActivity activity)
    {
        mActivity = activity;
        mEffectManager = mActivity.getEffectManager();
    }
    private EffectManager mEffectManager;
    public void setViewMode(ViewMode mode)
    {

        if(mCurrentViewMode != null)
        {
            mCurrentViewMode.cleanup(mActivity);
        }

        //Setup the new view mode.
        mode.setup(mActivity,mCurrentViewMode);
        mCurrentViewMode = mode;
    }


    public Collection<? extends PolygonOverlay> getCurrentlyActiveByAttribute(String attribute){
        Collection<? extends PolygonOverlay> overlays = mCurrentViewMode.getOverlayWithAttribute(attribute);
        return overlays;
    }


}
