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

    public PolygonSerializer(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    public BuildingPolygon createPolygon(JSONObject object) {
        if (object == null) {
            return null;
        }

        String buildingCode = "";
        String buildingName = "";
        String campus = "";
        String buildingImageUrl = "http://www.concordia.ca";
        LatLng coordinates = new LatLng(0, 0);
        ArrayList<LatLng> boundingCoordinates = new ArrayList<LatLng>();

        try {
            buildingCode = object.getString("buildingcode");

            buildingName = object.getString("buildingname");

            buildingImageUrl += object.getString("image");

            campus = object.getString("campus");

            coordinates = new LatLng(object.getDouble("latitude"), object.getDouble("longitude"));

            boundingCoordinates = extractBoundaryCoordinates(object.getJSONObject("Polygon"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        BuildingInfo buildingInfo = new BuildingInfo(buildingCode, buildingName, campus, buildingImageUrl, null, coordinates, boundingCoordinates);

        return new BuildingPolygon(mGoogleMap, buildingInfo);
    }

    public BuildingPolygon createPolygon(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return createPolygon(jsonObject);
    }

    private ArrayList<LatLng> extractBoundaryCoordinates(JSONObject object) {
        if(object == null) {
            return null;
        }

        ArrayList<LatLng> boundaryCoordinates = new ArrayList<>();

        try {
            JSONObject outerBoundaryObj = object.getJSONObject("outerBoundaryIs");
            JSONObject linearRingObj = outerBoundaryObj.getJSONObject("LinearRing");

            //Get coordinate array
            String[] coordinateStringArray = linearRingObj.getString("coordinates").split(" |,");

            for (int i = 0; i < coordinateStringArray.length; i += 3) {
                double lat = Double.parseDouble(coordinateStringArray[i]);
                double lng = Double.parseDouble(coordinateStringArray[i + 1]);

                boundaryCoordinates.add(new LatLng(lng, lat));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boundaryCoordinates;
    }

    public ArrayList<BuildingPolygon> createPolygonArray(JSONObject object) {

        if (object == null) {
            return null;
        }
        ArrayList<BuildingPolygon> buildingPolygons = new ArrayList<>();

        try {
            Iterator<?> keys = object.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject polygonObject = object.getJSONObject(key);

                if (object.get(key) instanceof JSONObject) {
                    BuildingPolygon polygon = createPolygon(polygonObject);
                    if (polygon != null) {
                        buildingPolygons.add(polygon);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return buildingPolygons;
    }

}
