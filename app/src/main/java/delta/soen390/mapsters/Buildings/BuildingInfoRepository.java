package delta.soen390.mapsters.Buildings;

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
            Log.i("JSON", "Creating BIR");
            mBuildingHash = new HashMap<>();

            Iterator<String> keys = mBuildingJson.keys();


            int intaja = 0;
            while( keys.hasNext() ){
                intaja++;
                String key = (String)keys.next();
                if( mBuildingJson.getJSONObject(key)  != null){
                    JSONObject currentBuilding = mBuildingJson.getJSONObject(key);
                    String buildingCode = (String) currentBuilding.getString("buildingcode");
                    String buildingName = (String) currentBuilding.getString("buildingname");
                    String campus =       (String) currentBuilding.getString("campus");
                    Double lat =          (Double) currentBuilding.getDouble("latitude");
                    Double lng =          (Double) currentBuilding.getDouble("longitude");
                    LatLng coords = new LatLng(lat,lng);
                    BuildingInfo buildingInfo = new BuildingInfo();
                    buildingInfo.setBuildingCode(buildingCode);
                    buildingInfo.setBuildingName(buildingName);
                    buildingInfo.setCampus(campus);
                    buildingInfo.setCoordinates(coords);
                    Log.i("ALL",buildingInfo.getBuildingName() );
                    Log.i("ALL",buildingInfo.getBuildingCode() );
                    Log.i("ALL",buildingInfo.getCoordinates().toString() );
                    Log.i("ALL",buildingInfo.getCampus());
                    mBuildingHash.put(key,buildingInfo);

                }
                Log.i("INT",Integer.toString(intaja) );
            }
        } catch(JSONException je) {
            je.printStackTrace();
        }

    }


    public BuildingInfo getBuildingInfo(String buildingCode) {
        return  mBuildingHash.get(buildingCode);
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
