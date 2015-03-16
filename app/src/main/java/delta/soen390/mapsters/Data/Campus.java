package delta.soen390.mapsters.Data;

import com.google.maps.model.LatLng;

/**
 * Created by Mathieu on 3/2/2015.
 */
public class Campus {

    public enum Name {
        SGW ("SGW"),
        LOY ("LOY");


        private final String name;

        private Name(String s) {
            name = s;
        }

        public String toString(){
            return name;
        }

    }

    public static Name getNearestCampus(LatLng point)
    {
        //Latitude delimiter for campus
        //Lower is Loyola, above is SGW
        double campusLatitudeDelimiter = 45.48;

        return point.lat  < campusLatitudeDelimiter ? Name.LOY : Name.SGW;
    }

    public static Name getCampusAsEnum(String name){
        name = name.toUpperCase();
        if (Name.SGW.toString().equals(name))
            return Name.SGW;
        if (Name.LOY.toString().equals(name))
            return Name.LOY;
        return null;
    }
}
