package delta.soen390.mapsters.IndoorDirectory;

import com.google.android.gms.maps.GoogleMap;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;

/**
 * Created by Mathieu on 3/22/2015.
 */
public class RoomPolygonOverlay extends PolygonOverlay {

    private String mName = "";

    public RoomPolygonOverlay(MapsActivity activity ,String name )
    {
        super(activity);
        mName = name;
    }

    public RoomPolygonOverlay(MapsActivity activity)
    {
        super(activity);
    }

    public String getName()
    {
        return mName;
    }


}
