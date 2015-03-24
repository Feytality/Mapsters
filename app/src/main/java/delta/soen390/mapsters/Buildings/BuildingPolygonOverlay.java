package delta.soen390.mapsters.Buildings;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;

import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;

/**
 * Created by Niofire on 2/7/2015.
 */
public class BuildingPolygonOverlay extends PolygonOverlay {

    private BuildingInfo mBuildingInfo;

	//UI Polygon displayed on the gmap isntance
	public BuildingPolygonOverlay(GoogleMap googleMap, BuildingInfo buildingInfo)
	{
        super(googleMap);

		mBuildingInfo   = buildingInfo;

        initializePolygon();
	}

    @Override
    public void focus()
    {
        super.focus();

        BuildingPolygonManager.getInstance().clickAndPopulate(this);
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
