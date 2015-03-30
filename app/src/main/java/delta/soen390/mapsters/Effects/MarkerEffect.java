package delta.soen390.mapsters.Effects;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.Utils.ImageMarkerFactory;

/**
 * Created by Cat on 3/30/2015.
 */
public class MarkerEffect implements IEffect{

    private GoogleMap mMap;
    private PolygonOverlay mOverlay;
    private int mDrawableId;
    private Marker mMarker;
    public MarkerEffect(GoogleMap map, PolygonOverlay overlay, int drawableID)
    {
        mMap = map;
        mOverlay = overlay;
        mDrawableId = drawableID;
    }

    @Override
    public void onStart() {
        ImageMarkerFactory factory = new ImageMarkerFactory(mMap,mOverlay.getCenterPoint(),mDrawableId);
        mMarker = factory.placeMarker();
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onEnd() {
        if(mMarker == null)
            return;
        mMarker.remove();
    }
}
