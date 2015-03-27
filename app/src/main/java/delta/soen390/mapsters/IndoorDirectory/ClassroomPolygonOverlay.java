package delta.soen390.mapsters.IndoorDirectory;

import com.google.android.gms.maps.GoogleMap;

import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;

/**
 * Created by Mathieu on 3/22/2015.
 */
public class ClassroomPolygonOverlay extends PolygonOverlay {

    private String mName = "";
    private String mBuildingName;
    private int floor = 0;

    public ClassroomPolygonOverlay(GoogleMap map)
    {
        super(map);
    }
}
