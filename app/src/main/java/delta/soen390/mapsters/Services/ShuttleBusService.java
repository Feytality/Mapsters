package delta.soen390.mapsters.Services;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.Data.Campus;
import delta.soen390.mapsters.Data.CampusEnum;
import delta.soen390.mapsters.Data.ShuttleResponseObject;
import delta.soen390.mapsters.R;
//}
//Check if Shuttle would be better
//getDuration from current location to shuttle start
//find out when next shuttle would come
//getDuration for shuttle trip
//getDuration from shuttle end to destination building
//get Duration of super easy stm trip
//compare
//IFF shuttle is better Ask user if they want to take the shuttle
// route them from shuttle Destination to their destination building
//            if(ShuttleWins(currentLocation,mCurrentBuilding.getBuildingCode())) {
//                //popup
//            }
public class ShuttleBusService {

    private final LatLng sgwShuttle;
    private final LatLng loyShuttle;
    private final Campus mLoyolaCampus;
    private final Campus mSGWCampus;
    private Context mContext;

    String[] mon_thuArrayTimes, friArrayTimes;

    //TODO change to singleton but too lazy now
    public ShuttleBusService (Context context){
        sgwShuttle  = new LatLng(45.4971514,-73.5787977);
        loyShuttle = new LatLng(45.458949,-73.6383713);


        mLoyolaCampus = new Campus(CampusEnum.LOY, loyShuttle);
        mSGWCampus = new Campus(CampusEnum.SGW, sgwShuttle);

        mContext = context;
    }

    public ShuttleResponseObject ShuttleBusWins (LatLng currentLocation) {
        CampusEnum currentCampus = null;
        if (mLoyolaCampus.isClose(currentLocation)) {
            currentCampus = CampusEnum.LOY;
        } else if (mSGWCampus.isClose(currentLocation)){
            currentCampus = CampusEnum.SGW;
        } else {
            return null;
        }

        BuildingInfo destinationBuilding = BuildingPolygonManager.getInstance().getBuildingPolygon(destinationBuildingCode).getBuildingInfo();


        //find out how long it takes to get to shuttlebus
        long getToShuttleTime = getDuration(currentLocation, nearestShuttle.getCoordinates(), TravelMode.WALKING);
        long shuttleRideTime =  getDuration(nearestShuttle.getCoordinates(), nearestShuttle.getCoordinates(),TravelMode.DRIVING);
        long fromShuttleToDest = getDuration(nearestShuttle.getDestination().getCoordinates(), destinationBuilding.getCoordinates(), TravelMode.WALKING);

        long totalShuttleTime = getToShuttleTime + shuttleRideTime + fromShuttleToDest;
        Log.e("get2shuttle",String.valueOf(getToShuttleTime));
        //add these, compare to stm
        long stmTime = getDuration(currentLocation, destinationBuilding.getCoordinates(),TravelMode.TRANSIT);
        if (totalShuttleTime < stmTime) {
            return true;
        } else {
            return false;
        }
        return null;
    }

    /*
    *
    *   TRAVELMODE.DRIVING is reserved for shuttle (Since we're not giving driving directions)
    *   TRAVELMODE.WALKING is used for everything that's not shuttle and not STM
    * */
    private long getDuration(LatLng start, LatLng end, TravelMode travelMode) {
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCDsbX2OWOnFJRJ_oHMls-HRtncbpMc_qI");
        com.google.maps.model.LatLng startLatLng = new com.google.maps.model.LatLng(start.latitude,start.latitude);
        com.google.maps.model.LatLng endLatLng = new com.google.maps.model.LatLng(end.latitude,end.latitude);
        DirectionsApiRequest dar = DirectionsApi.newRequest(context)
                .origin(startLatLng)
                .destination(endLatLng)
                .mode(travelMode);
        if (travelMode.equals(travelMode.DRIVING)) {
            //if they're taking the shuttle
            dar.departureTime(new DateTime(getNextBusLoy(new Date())));
        }
        return dar.awaitIgnoreError()[0].legs[0].duration.inSeconds;
    }

    private Date getNextBusLoy(Date date)
    {
        Resources res = mContext.getResources();
        mon_thuArrayTimes = res.getStringArray(R.array.Loyola_MonToThu);
        friArrayTimes = res.getStringArray(R.array.Loyola_Fri);
        return getNextBus(date);
    }

    private Date getNextBusSgw(Date date)
    {
        Resources res = mContext.getResources();
        mon_thuArrayTimes = res.getStringArray(R.array.SGW_MonToThu);
        friArrayTimes = res.getStringArray(R.array.SGW_Fri);
        return getNextBus(date);
    }

    private Date getNextBus(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if(cal.get(Calendar.DAY_OF_WEEK) >= Calendar.MONDAY && cal.get(Calendar.DAY_OF_WEEK) <= Calendar.THURSDAY)
        {
            if(isBefore(cal, mon_thuArrayTimes[mon_thuArrayTimes.length -1]))
            {
                setTime(cal, findNext(cal, mon_thuArrayTimes));
            }
            else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
            {
                setTime(cal, friArrayTimes[0]);
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            else
            {
                setTime(cal, mon_thuArrayTimes[0]);
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
        }else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
        {
            if(isBefore(cal, friArrayTimes[friArrayTimes.length -1]))
            {
                setTime(cal, findNext(cal, friArrayTimes));
            }
            else
            {
                setTime(cal, mon_thuArrayTimes[0]);
                cal.add(Calendar.DAY_OF_YEAR, 3);
            }
        }
        else
        {
            setTime(cal, mon_thuArrayTimes[0]);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        return cal.getTime();
    }

    private String findNext(Calendar cal, String[] arr)
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

    private void setTime(Calendar date,String time)
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
    private boolean isBefore(Calendar date,String time)
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
}
