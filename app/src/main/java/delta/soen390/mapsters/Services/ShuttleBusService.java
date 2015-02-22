package delta.soen390.mapsters.Services;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.R;

public class ShuttleBusService {

    static String[] mon_thu,fri;
    public static Date getNextBusLoy(Date date,Context con)
    {
        Resources res = con.getResources();
        mon_thu = res.getStringArray(R.array.Loyola_MonToThu);
        fri = res.getStringArray(R.array.Loyola_Fri);
        return getNextBus(date);
    }

    public static Date getNextBusSgw(Date date,Context con)
    {
        Resources res = con.getResources();
        mon_thu = res.getStringArray(R.array.SGW_MonToThu);
        fri = res.getStringArray(R.array.SGW_Fri);
        return getNextBus(date);
    }

    private static Date getNextBus(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if(cal.get(Calendar.DAY_OF_WEEK) >= Calendar.MONDAY && cal.get(Calendar.DAY_OF_WEEK) <= Calendar.THURSDAY)
        {
            if(isBefore(cal, mon_thu[mon_thu.length -1]))
            {
                setTime(cal, findNext(cal,mon_thu));
            }
            else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
            {
                setTime(cal, fri[0]);
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            else
            {
                setTime(cal, mon_thu[0]);
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
        }else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
        {
            if(isBefore(cal, fri[fri.length -1]))
            {
                setTime(cal, findNext(cal,fri));
            }
            else
            {
                setTime(cal, mon_thu[0]);
                cal.add(Calendar.DAY_OF_YEAR, 3);
            }
        }
        else
        {
            setTime(cal, mon_thu[0]);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        return cal.getTime();
    }

    private static String findNext(Calendar cal, String[] arr)
    {
        for(String s: arr)
        {
            if(isBefore(cal,s))
            {
                return s;
            }
        }
        Log.i("utils.java findNext","no next bus found for date:" + cal.toString());
        return null;
    }

    private static void setTime(Calendar date,String time)
    {
        try{
            String[] arr = time.split(":");
            int hours = Integer.parseInt(arr[0]);
            int mins = Integer.parseInt(arr[1]);
            date.set(Calendar.HOUR_OF_DAY, hours);
            date.set(Calendar.MINUTE, mins);
        }
        catch(Exception e)
        {
            Log.e("Utils.java setTime","The supplied times are not properly formatted. Verify that your schedule times follow the hh:mm pattern");
        }
    }
    private static boolean isBefore(Calendar date,String time)
    {
        try{
            String[] arr = time.split(":");
            int hours = Integer.parseInt(arr[0]);
            int mins = Integer.parseInt(arr[1]);
            return (date.get(Calendar.HOUR_OF_DAY) < hours) || (date.get(Calendar.HOUR_OF_DAY) == hours && date.get(Calendar.MINUTE) < mins);
        }
        catch(Exception e)
        {
            Log.e("Utils.java isBefore","The supplied times are not properly formatted. Verify that your schedule times follow the hh:mm pattern");
            return true;
        }
    }


    //returns the location of the shuttle bus to take, depending on the destination
    public static ShuttleLocation getNearestShuttle(String destinationBuildingCode) {
        LatLng sgwShuttle = new LatLng(45.4971514,-73.5787977);
        LatLng loyShuttle = new LatLng(45.458949,-73.6383713);
        BuildingInfo destinationBuilding = BuildingPolygonManager
                .getInstance().getBuildingPolygon(destinationBuildingCode).getBuildingInfo();

        if (destinationBuilding.getCampus() == "LOY") {
            return new ShuttleLocation("SGW");
        }
        else {
            return new ShuttleLocation("LOY");
        }
    }


    public static class ShuttleLocation {
        private String mCampus;
        private LatLng mCoordinates;
        private ShuttleLocation destination;
        private static LatLng SGW_SHUTTLE_COORDS = new LatLng(45.4971514,-73.5787977);
        private static LatLng LOY_SHUTTLE_COORDS = new LatLng(45.458949,-73.6383713);

        public ShuttleLocation(String campus) {
            this.mCampus = campus;
            if(mCampus == "LOY") {
                this.mCoordinates = LOY_SHUTTLE_COORDS ;
                if (this.destination != null) {
                    this.destination = new ShuttleLocation("SGW");
                }
            } else {
                this.mCoordinates = SGW_SHUTTLE_COORDS;
                if (this.destination != null) {
                    this.destination = new ShuttleLocation("LOY");
                }
            }
        }
        public String getCampus() {return mCampus;}
        public LatLng getCoordinates() {return mCoordinates;}
        public ShuttleLocation getDestination() {return destination;}
    }

}
