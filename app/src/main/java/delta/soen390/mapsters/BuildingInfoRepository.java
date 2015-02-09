package delta.soen390.mapsters;

/**
 * Created by georgevalergas on 15-02-08.
 */
public class BuildingInfoRepository {
    private static BuildingInfoRepository ourInstance = new BuildingInfoRepository();
//    private JSONObject buildingJson;
    public static BuildingInfoRepository getInstance() {
        return ourInstance;
    }

    private BuildingInfoRepository() {
        //fill up the data
    }

    //TODO
    public BuildingInfo getBuildingInfo(String buildingCode) {
        return null;
    }
}
