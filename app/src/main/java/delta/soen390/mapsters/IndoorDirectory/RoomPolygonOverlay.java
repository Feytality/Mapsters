package delta.soen390.mapsters.IndoorDirectory;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import com.google.android.gms.maps.GoogleMap;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 3/22/2015.
 */
public class RoomPolygonOverlay extends PolygonOverlay {

    private String mName = "";
    private BuildingFloor mFloor;

    public RoomPolygonOverlay(MapsActivity activity ,String name )
    {
        super(activity);
        loadResources(activity.getApplicationContext());
        mName = name;
    }
    public void loadResources(Context c)
    {
        setUnfocusedColor(Color.TRANSPARENT);
        setFocusColor(c.getResources().getColor(R.color.concordia_light));
    }

    public void setFloor(BuildingFloor floor)
    {
        mFloor = floor;
    }
    public BuildingFloor getFloor()
    {
        return mFloor;
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
