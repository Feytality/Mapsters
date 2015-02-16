package delta.soen390.mapsters.Buildings;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Niofire on 2/15/2015.
 */
public class PolygonSerializer {

	private GoogleMap mGoogleMap;
	public PolygonSerializer(GoogleMap googleMap)
	{
		mGoogleMap = googleMap;
	}

	public BuildingPolygon CreatePolygon(JSONObject object)
	{
		if(object == null)
			return null;

		String buildingCode = "";
		String buildingName = "";
		String campus = "";
		LatLng coordinates = new LatLng(0,0);
		ArrayList<LatLng> boundingCoordinates = new ArrayList<LatLng>();



		try {
			buildingCode    = object.getString("buildingcode");

			buildingName    = object.getString("buildingname");

			campus          = object.getString("campus");

			coordinates     = new LatLng(object.getDouble("latitude"),object.getDouble("longitude"));



		} catch (Exception e) {
			e.printStackTrace();
		}

		BuildingInfo buildingInfo = new BuildingInfo(buildingCode,buildingName,campus,null,coordinates,boundingCoordinates);

		return new BuildingPolygon(mGoogleMap,buildingInfo);
	}

	public BuildingPolygon CreatePolygon(String jsonString)
	{
		JSONObject jsonObject;
		try{
			jsonObject = new JSONObject(jsonString);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return CreatePolygon(jsonObject);
	}

	public BuildingPolygon[] CreatePolygonArray(JSONObject object) {
		ArrayList<BuildingPolygon> buildingPolygons = new ArrayList<BuildingPolygon>();

		//TODO Implement loading code
		try {

			Iterator<?> keys = object.keys();

			while (keys.hasNext()) {
				String key = (String) keys.next();
				object.getJSONObject(key);

				if (object.get(key) instanceof JSONObject) {

				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return (BuildingPolygon[])buildingPolygons.toArray();
	}

}
