package delta.soen390.mapsters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

/**
 * Created by Niofire on 2/7/2015.
 */
public class BuildingPolygon {

    private BuildingInfo mBuildingInfo;

	//Contains the 4 rectangle corner of the Building Rectangle
	private ArrayList<LatLng> _vertices;

	//UI Polygon displayed on the gmap isntance
	private Polygon _polygon;

	//Used to test for touch input
	private BoundingBox2D _boundingBox2D;


	public BuildingPolygon(ArrayList<LatLng> vertices, GoogleMap googleMap)
	{
		_vertices = vertices;

		createPolygon(googleMap);
	}

	public BuildingPolygon(GoogleMap googleMap, LatLng... points)
	{
		_vertices = new ArrayList<LatLng>();
		for (LatLng latLng : points)
		{
			_vertices.add(latLng);
		}

		createPolygon(googleMap);
	}

	private void createPolygon(GoogleMap googleMap)
	{
		//if no vertices, no polygon!
		if(_vertices.size() <= 0)
			return;

		//Create the Polygon Options which will be used to instantiate the actual google map Polygon UI element
		PolygonOptions polygonOptions = new PolygonOptions();

		//Load in the vertices! yayy
		for(int i = 0; i < _vertices.size(); ++i)
		{
			LatLng point = _vertices.get(i);

			polygonOptions.add(_vertices.get(i));
		}


		//add first vertice at the end in order to create a complete polygon loop
		if(_vertices.size() > 0)
			polygonOptions.add(_vertices.get(0));


		_boundingBox2D = new BoundingBox2D(_vertices);

		_polygon = googleMap.addPolygon(polygonOptions);

	}

	public void setFillColor(int color)
	{
		_polygon.setFillColor(color);
	}
	public void setVisibility(boolean isVisible)
	{
		_polygon.setVisible(isVisible);
	}


	public boolean isPointInsideRectangle(LatLng point)
	{
		//Test if point is inside box
		return _boundingBox2D.isPointInsideBoundingBox(
				new Vector2D(
					point.latitude,
					point.longitude));
	}

    public BuildingInfo getBuildingInfo() {
        return mBuildingInfo;
    }


}
