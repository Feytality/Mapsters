package delta.soen390.mapsters.IndoorDirectory;

import com.google.android.gms.maps.GoogleMap;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;

/**
 * Created by Mathieu on 3/22/2015.
 */
public class RoomPolygonOverlay extends PolygonOverlay {

    private String mName = "";
    private String mDescription = "";

    public RoomPolygonOverlay(MapsActivity activity ,String name, String description )
    {
        super(activity);
        mName = name;
        mDescription = description;
    }

    public RoomPolygonOverlay(MapsActivity activity)
    {
        super(activity);
    }

    public String getName()
    {
        return mName;
    }

    public String getDescription()
    {
        return mDescription;
    }

}
