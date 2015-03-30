package delta.soen390.mapsters.ViewMode;

import java.util.ArrayList;
import java.util.Collection;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.Utils.GoogleMapCamera;

/**
 * Created by Mathieu on 3/29/2015.
 */
public class ViewModeController {

    private ViewMode mCurrentViewMode;
    private Collection<PolygonOverlay> mCurrentlyActiveHighlights = new ArrayList<PolygonOverlay>();
    private GoogleMapCamera mMapCamera;

    private final MapsActivity mActivity;
    public ViewModeController(GoogleMapCamera camera, MapsActivity activity)
    {
        mMapCamera = camera;
        mActivity = activity;
    }
    public void setViewMode(ViewMode mode)
    {
        //clear all highlights
        clearAllHighlights();

        if(mCurrentViewMode != null)
        {
            mCurrentViewMode.cleanup(mMapCamera,mActivity);
        }


        //Setup the new view mode.
        mCurrentViewMode = mode;
        mCurrentViewMode.setup(mMapCamera,mActivity);
    }

    public void activateHighlight(String attribute, HighlightEffect effect)
    {
        if(mCurrentViewMode == null)
        {
            return;
        }
        Collection<? extends PolygonOverlay> overlays = mCurrentViewMode.getOverlayWithAttribute(attribute);

        for(PolygonOverlay overlay : overlays)
        {
            overlay.highlight(effect.updateEffect());

            if(!mCurrentlyActiveHighlights.contains(overlay))
            {
                mCurrentlyActiveHighlights.add(overlay);
            }
        }
    }

    public void clearHighlightWithAttribute(String attribute)
    {
        for(PolygonOverlay highlights : mCurrentlyActiveHighlights)
        {
            if(highlights.containsAttribute(attribute))
            {
                highlights.unfocus();
            }
        }
    }

    public void clearAllHighlights()
    {
        for(PolygonOverlay highlights : mCurrentlyActiveHighlights)
        {
            highlights.unfocus();
            mCurrentlyActiveHighlights.remove(highlights);
        }
    }
}
