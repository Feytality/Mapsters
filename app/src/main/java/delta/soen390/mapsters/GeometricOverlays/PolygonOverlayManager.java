package delta.soen390.mapsters.GeometricOverlays;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Buildings.BuildingPolygonOverlay;
import delta.soen390.mapsters.Buildings.PolygonDirectory;
import delta.soen390.mapsters.IndoorDirectory.RoomPolygonOverlay;

/**
 * Created by Mathieu on 3/22/2015.
 */
public class PolygonOverlayManager {

    private Collection<? extends PolygonOverlay> mCurrentlyActiveOverlays = null;
    private PolygonOverlay mCurrentlyFocusedOverlay = null;
    private PolygonDirectory mDirectory;

    public PolygonOverlayManager()
    {
        mDirectory = new PolygonDirectory();

    }

    public void loadResources(MapsActivity activity)
    {
        mDirectory.loadResources(activity);
    }

    public void setActiveOverlays(Collection<? extends PolygonOverlay> overlays)
    {
        deactivateOverlays();

        if(overlays == null) {
            return;
        }

        mCurrentlyActiveOverlays = overlays;

        for(PolygonOverlay overlay : mCurrentlyActiveOverlays)
        {
            overlay.activate();
        }
    }

    public void focusOverlay(PolygonOverlay overlay)
    {
        unfocusOverlay();
        mCurrentlyFocusedOverlay = overlay;

        //check if indoors view

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
            overlay.deactivate();
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

    public PolygonDirectory getPolygonDirectory()
    {
        return mDirectory;
    }


}
