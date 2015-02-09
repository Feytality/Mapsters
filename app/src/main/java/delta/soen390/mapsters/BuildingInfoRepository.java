package delta.soen390.mapsters;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by georgevalergas on 15-02-08.
 */
public class BuildingInfoRepository {
    private static BuildingInfoRepository ourInstance = new BuildingInfoRepository();
    private JSONObject mBuildingJson;
    private HashMap <String, BuildingInfo> mBuildingHash;

    public static BuildingInfoRepository getInstance() {
        return ourInstance;
    }

    private BuildingInfoRepository() {
        //turn JSON from file into Hashmap < String, BuildingInfo >
        try {
            mBuildingJson = new JSONObject(loadJSONFromAsset());
            Log.i("JSON", mBuildingJson.getJSONObject("CC").toString());
            mBuildingHash = new HashMap<>();

            Iterator<?> keys = mBuildingJson.keys();
            while( keys.hasNext() ){
                String key = (String)keys.next();
                if( mBuildingJson.get(key) instanceof JSONObject ){
                    String buildingCode = (String) mBuildingJson.get("buildingcode");
                    String buildingName = (String) mBuildingJson.get("buildingname");
                    String campus = (String) mBuildingJson.get("campus");
                    Double lat = (Double) mBuildingJson.get("latitude");
                    Double lng = (Double) mBuildingJson.get("longitude");
                    LatLng coords = new LatLng(lat,lng);
                    BuildingInfo buildingInfo = new BuildingInfo();
                    buildingInfo.setBuildingCode(buildingCode);
                    buildingInfo.setBuildingName(buildingName);
                    buildingInfo.setCampus(campus);
                    buildingInfo.setCoordinates(coords);
                    mBuildingHash.put(key,buildingInfo);
                }
            }
        } catch(JSONException je) {
            je.printStackTrace();
        }

    }

    //TODO
    public BuildingInfo getBuildingInfo(String buildingCode) {
        return null;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            String file = "assets/buildingJson.json";
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
