package delta.soen390.mapsters.Buildings;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.R;

/**
 * Created by Niofire on 2/7/2015.
 */
public class BuildingPolygonOverlay extends PolygonOverlay {

    private BuildingInfo mBuildingInfo;

	//UI Polygon displayed on the gmap isntance
	public BuildingPolygonOverlay(MapsActivity activity, BuildingInfo buildingInfo)
	{
        super(activity);

		mBuildingInfo   = buildingInfo;

        initializePolygon();
	}

    @Override
    public void focus()
    {
        super.focus();

    }

    public void loadResources(Context c)
    {
        setFocusColor(c.getResources().getColor(R.color.concordia_dark));
        setUnfocusedColor(c.getResources().getColor(R.color.concordia_light));
    }

	private void initializePolygon()
	{
		ArrayList<LatLng> vertices = mBuildingInfo.getBoundingCoordinates();
		//if no vertices, no polygon!
		if(vertices.size() <= 0) {
			return;
		}

        createPolygon(vertices);
	}

    public BuildingInfo getBuildingInfo() {
        return mBuildingInfo;
    }
}
