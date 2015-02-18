package delta.soen390.mapsters.Buildings;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

import delta.soen390.mapsters.Geometry.BoundingBox2D;
import delta.soen390.mapsters.Geometry.Vector2D;

/**
 * Created by Niofire on 2/7/2015.
 */
public class BuildingPolygon {

    private BuildingInfo mBuildingInfo;

	//UI Polygon displayed on the gmap isntance
	private Polygon mPolygon;

	//Used to test for touch input
	private BoundingBox2D mBoundingBox2D;
	private GoogleMap mGoogleMap;

	public BuildingPolygon(GoogleMap googleMap, BuildingInfo buildingInfo)
	{
		mGoogleMap      = googleMap;
		mBuildingInfo   = buildingInfo;

		createPolygon();
	}

	private void createPolygon()
	{
		ArrayList<LatLng> vertices = mBuildingInfo.getBoundingCoordinates();
		//if no vertices, no polygon!
		if(vertices.size() <= 0) {
			return;
		}

		//Create the Polygon Options which will be used to instantiate the actual google map Polygon UI element
		PolygonOptions polygonOptions = new PolygonOptions();

		//Load in the vertices!
		for(int i = 0; i < vertices.size(); ++i)
		{
			LatLng point = vertices.get(i);

			polygonOptions.add(vertices.get(i));
		}


		//add first vertex at the end in order to create a complete polygon loop
		if(vertices.size() > 0) {
			polygonOptions.add(vertices.get(0));
		}


		mBoundingBox2D = new BoundingBox2D(vertices);

		mPolygon = mGoogleMap.addPolygon(polygonOptions);

	}

	public void setFillColor(int color)
	{
		mPolygon.setFillColor(color);
	}

	public void setVisibility(boolean isVisible)
	{
		mPolygon.setVisible(isVisible);
	}

	public boolean isPointInsidePolygon(LatLng point)
	{
		//Test if point is inside box
        if(mBoundingBox2D == null)
            return false;
		return mBoundingBox2D.isPointInsideBoundingBox(new Vector2D(point.latitude, point.longitude));
	}

    public BuildingInfo getBuildingInfo() {
        return mBuildingInfo;
    }
}
