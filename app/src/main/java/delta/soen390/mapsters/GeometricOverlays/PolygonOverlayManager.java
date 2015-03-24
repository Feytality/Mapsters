package delta.soen390.mapsters.GeometricOverlays;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import delta.soen390.mapsters.Buildings.BuildingPolygonOverlay;

/**
 * Created by Mathieu on 3/22/2015.
 */
public class PolygonOverlayManager {

    private ArrayList<? extends PolygonOverlay> mCurrentlyActiveOverlays = null;
    private PolygonOverlay mCurrentlyFocusedOverlay = null;

    private PolygonOverlayManager()
    {

    }

    private static PolygonOverlayManager sPolygonOverlayManager = null;

    public static PolygonOverlayManager getInstance() {
        if (sPolygonOverlayManager == null){
            sPolygonOverlayManager = new PolygonOverlayManager();
        }
        return sPolygonOverlayManager;
    }

    public void setActiveOverlays(ArrayList<? extends PolygonOverlay> overlays)
    {
        deactivateOverlays();

        if(overlays == null) {
            return;
        }

        mCurrentlyActiveOverlays = overlays;

        for(PolygonOverlay overlay : mCurrentlyActiveOverlays)
        {
            overlay.setVisibility(true);
        }
    }

    public void focusOverlay(PolygonOverlay overlay)
    {
        unfocusOverlay();
        mCurrentlyFocusedOverlay = overlay;
    }

    //Remove all focus effects of the BuildingPolygon
    //mainly resets color
    public void unfocusOverlay()
    {
        if(mCurrentlyFocusedOverlay == null) {
            return;
        }
        mCurrentlyFocusedOverlay.unfocus();
        mCurrentlyFocusedOverlay = null;
    }

    public void deactivateOverlays()
    {
        if(mCurrentlyActiveOverlays == null)
            return;

        for(PolygonOverlay overlay : mCurrentlyActiveOverlays)
        {
            //Turn overlay visibility off
            overlay.setVisibility(false);
        }
    }

    public PolygonOverlay getClickedPolygon(LatLng point) {

        if (point == null || mCurrentlyActiveOverlays == null)
        return null;

        for (PolygonOverlay overlay : mCurrentlyActiveOverlays) {
            if (overlay.isPointInsidePolygon(point)) {
                return overlay;
            }
        }
        return null;
    }

}
