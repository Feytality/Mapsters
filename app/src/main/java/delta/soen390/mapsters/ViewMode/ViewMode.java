package delta.soen390.mapsters.ViewMode;

import java.util.ArrayList;
import java.util.Collection;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.Utils.GoogleMapCamera;

/**
 * Created by Mathieu on 3/29/2015.
 */
public abstract class ViewMode {

    protected Collection<? extends PolygonOverlay> mOverlays;

    public  Collection<? extends PolygonOverlay> getOverlayWithAttribute(String attribute)
    {
        ArrayList<PolygonOverlay> attributeList = new ArrayList<PolygonOverlay>();
        if(mOverlays != null) {
            for (PolygonOverlay overlay : mOverlays) {
                if (overlay.containsAttribute(attribute)) {
                    attributeList.add(overlay);
                }
            }
        }
        return attributeList;
    }

    public abstract void setup(MapsActivity activity, ViewMode previousViewMode);

    public abstract void cleanup(MapsActivity activity);



}
