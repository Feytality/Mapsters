package delta.soen390.mapsters.Buildings;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import delta.soen390.mapsters.Activities.MapsActivity;

/**
 * Created by Niofire on 2/15/2015.
 */
public class PolygonSerializer {

    private GoogleMap mGoogleMap;
    private MapsActivity mActivity;
    public PolygonSerializer(MapsActivity activity) {
        mActivity = activity;

    }

    public BuildingPolygonOverlay createPolygon(JSONObject object) {
        if (object == null) {
            return null;
        }

        String buildingCode = "";
        String buildingName = "";
        String campus = "";
        String buildingImageUrl = "http://www.concordia.ca";
        LatLng coordinates = new LatLng(0, 0);
        ArrayList<LatLng> boundingCoordinates = new ArrayList<>();
        ArrayList<String[]> services = new ArrayList<>();
        ArrayList<String[]> departments = new ArrayList<>();
        String address = "";
        boolean access = false;
        boolean info = false;
        boolean parking = false;
        boolean bikeRack = false;

        try {
            buildingCode = object.getString("buildingcode");
            address = object.getString("civicaddress");
            parking = object.getBoolean("parkinglot");
            access = object.getBoolean("accessibility");
            info = object.getBoolean("infokiosk");
            bikeRack = object.getBoolean("bikerack");
            buildingName = object.getString("buildingname");

            buildingImageUrl += object.getString("image");

            campus = object.getString("campus");

            coordinates = new LatLng(object.getDouble("latitude"), object.getDouble("longitude"));

            boundingCoordinates = extractBoundaryCoordinates(object.getJSONObject("Polygon"));

            services = extractList(object.getJSONArray("serviceslinks"));

            departments = extractList(object.getJSONArray("departmentslinks"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        BuildingInfo buildingInfo = new BuildingInfo(buildingCode, buildingName, campus, buildingImageUrl,
                                    coordinates, boundingCoordinates, services, departments,address,info,parking,access,bikeRack);
        BuildingPolygonOverlay overlay = new BuildingPolygonOverlay(mActivity, buildingInfo);
        if(overlay.isValidOverlay())
        {
            return overlay;
        }
        return null;
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

    private ArrayList<String[]> extractList(JSONArray object){
        if(object == null) {
            return null;
        }

        ArrayList<String[]> services = new ArrayList<>();

        try {
            for(int i = 0; i < object.length(); i++) {
                {
                    JSONObject service = new JSONObject(object.get(i).toString());
                    String text = service.get("linkText").toString();
                    String path = service.get("linkPath").toString();
                    String[] serviceArray = { text, path };
                    services.add(serviceArray);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services;
    }

    public ArrayList<BuildingPolygonOverlay> createPolygonArray(JSONObject object) {

        if (object == null) {
            return null;
        }
        ArrayList<BuildingPolygonOverlay> buildingPolygons = new ArrayList<>();

        try {
            Iterator<?> keys = object.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject polygonObject = object.getJSONObject(key);

                if (object.get(key) instanceof JSONObject) {
                    BuildingPolygonOverlay polygon = createPolygon(polygonObject);
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
