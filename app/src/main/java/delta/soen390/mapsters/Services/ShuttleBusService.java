package delta.soen390.mapsters.Services;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

import delta.soen390.mapsters.Buildings.BuildingInfo;
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
    private final String mAPIKey = "AIzaSyCDsbX2OWOnFJRJ_oHMls-HRtncbpMc_qI";
    private GeoApiContext mGeoApiContext;
    private Context mContext;

    String[] mon_thuArrayTimes, friArrayTimes;

    //TODO change to singleton but too lazy now
    public ShuttleBusService (Context context){
        mGeoApiContext = new GeoApiContext().setApiKey(mAPIKey);
        sgwShuttle  = new LatLng(45.4971514,-73.5787977);
        loyShuttle = new LatLng(45.458949,-73.6383713);

        mLoyolaCampus = new Campus(CampusEnum.LOY, loyShuttle);
        mSGWCampus = new Campus(CampusEnum.SGW, sgwShuttle);

        mContext = context;
    }

    public ShuttleResponseObject ShuttleBusWins (LatLng currentLocation, BuildingInfo destinationBuilding) {
        Campus startCampus = null;
        Campus endCampus = null;
        if (mLoyolaCampus.isClose(currentLocation)) {
            startCampus = mLoyolaCampus;
            endCampus = mSGWCampus;
        } else if (mSGWCampus.isClose(currentLocation)){
            startCampus = mSGWCampus;
            endCampus = mLoyolaCampus;
        } else {
            return new ShuttleResponseObject(false,null,null,null);
        }

        //shuttle length
        ShuttleResponseObject shuttleLength = getShuttleBusTime(currentLocation,destinationBuilding,startCampus,endCampus);
        ShuttleResponseObject stmLength = getTransitTime(currentLocation,destinationBuilding, startCampus);


        //compare shuttle length to stm length
        if (shuttleLength.getmShuttleArrive().before(stmLength.getmShuttleArrive())) {
            return shuttleLength;
        }

        return stmLength;
    }

    private ShuttleResponseObject getShuttleBusTime(LatLng currentLocation, BuildingInfo destination, Campus startCampus, Campus endCampus){
        //find out how long it takes to get to shuttle bus
        Long walkingTimeToShuttle = getDuration(currentLocation, startCampus.getShuttlePointPoint() , TravelMode.WALKING, startCampus);
        Long shuttleDuration =  getDuration(startCampus.getShuttlePointPoint(), destination.getCoordinates(),TravelMode.DRIVING, startCampus);
        Long walkingTimeFromShuttleToDestination = getDuration(endCampus.getShuttlePointPoint(), destination.getCoordinates(), TravelMode.WALKING, startCampus);
        Long totalShuttleTimeSeconds = walkingTimeToShuttle + shuttleDuration + walkingTimeFromShuttleToDestination;

        //todo refactor this later
        Calendar busDepart = findNextBus(startCampus);

        Calendar busArrive = findNextBus(startCampus);
        busArrive.add(Calendar.SECOND, totalShuttleTimeSeconds.intValue());

        return new ShuttleResponseObject(true,busArrive,busDepart,startCampus);
    }

    private ShuttleResponseObject getTransitTime(LatLng currentLocation, BuildingInfo destination, Campus startCampus){
        Long stmTime = getDuration(currentLocation,destination.getCoordinates(),TravelMode.TRANSIT, startCampus);
        Calendar stmArrive = Calendar.getInstance();
        stmArrive.add(Calendar.SECOND, stmTime.intValue());

        return new ShuttleResponseObject(false,stmArrive, Calendar.getInstance(),startCampus);
    }

    /*
    *
    *   TRAVELMODE.DRIVING is reserved for shuttle (Since we're not giving driving directions)
    *   TRAVELMODE.WALKING is used for everything that's not shuttle and not STM
    * */
    private Long getDuration(LatLng start, LatLng end, TravelMode travelMode, Campus startCampus) {
        com.google.maps.model.LatLng startLatLng = new com.google.maps.model.LatLng(start.latitude,start.latitude);
        com.google.maps.model.LatLng endLatLng = new com.google.maps.model.LatLng(end.latitude,end.latitude);
        DirectionsApiRequest dar = DirectionsApi.newRequest(mGeoApiContext)
                .origin(startLatLng)
                .destination(endLatLng)
                .mode(travelMode);
        if (travelMode.equals(travelMode.DRIVING)) {
            //if they're taking the shuttle
            dar.departureTime(new DateTime(findNextBus(startCampus)));
        }

        if (travelMode.equals(travelMode.TRANSIT)) {
            dar.departureTime(DateTime.now());
        }

        DirectionsRoute[] routes = dar.awaitIgnoreError();
        if (routes.length<=0) //can't find a trip
            return Long.MIN_VALUE;
        return routes[0].legs[0].duration.inSeconds;
    }

    private Calendar findNextBus(Campus campus){
        switch (campus.getName()) {
            case LOY:
                setNextBusLoy();
                break;
            case SGW:
                setNextBusSgw();
                break;
        }

        return getNextBusTime();
    }

    private void setNextBusLoy()
    {
        Resources res = mContext.getResources();
        mon_thuArrayTimes = res.getStringArray(R.array.Loyola_MonToThu);
        friArrayTimes = res.getStringArray(R.array.Loyola_Fri);
    }

    private void setNextBusSgw()
    {
        Resources res = mContext.getResources();
        mon_thuArrayTimes = res.getStringArray(R.array.SGW_MonToThu);
        friArrayTimes = res.getStringArray(R.array.SGW_Fri);
    }

    private Calendar getNextBusTime()
    {
        Calendar cal = Calendar.getInstance();

        //Monday - Thursday
        if(cal.get(Calendar.DAY_OF_WEEK)>= Calendar.MONDAY && cal.get(Calendar.DAY_OF_WEEK)<= Calendar.THURSDAY)
        {
            //if it's before the last shuttle
            if(cal.before(parseTimeString(mon_thuArrayTimes[mon_thuArrayTimes.length -1])))
            {
                cal = findNext(cal, mon_thuArrayTimes);
            }
            //check first friday time
            else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
            {
                cal = parseTimeString(friArrayTimes[0]);
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            //check first mon-thurs time
            else
            {
                cal = parseTimeString(mon_thuArrayTimes[0]);
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
        //Friday
        }else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
        {
            //check if before the last shuttle
            if(cal.before(friArrayTimes[friArrayTimes.length -1]))
            {
                cal =  findNext(cal, friArrayTimes);
            }
            //get the next one on Monday
            else
            {
                cal = parseTimeString(mon_thuArrayTimes[0]);
                cal.add(Calendar.DAY_OF_YEAR, 3);
            }
        }

        //get first on monday
        else
        {
            cal = parseTimeString(mon_thuArrayTimes[0]);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        return cal;
    }

    private Calendar findNext(Calendar cal, String[] arr)
    {
        for(String s: arr)
        {
            if(cal.before(parseTimeString(s)))
            {
                return parseTimeString(s);
            }
        }
        Log.i("utils.java findNext","no next bus found for date:" + cal.toString());
        return null;
    }

    private Calendar parseTimeString(String time)
    {
        Calendar calendar = Calendar.getInstance();

        try{
            String[] arr = time.split(":");
            int hours = Integer.parseInt(arr[0]);
            int mins = Integer.parseInt(arr[1]);
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, mins);
        }
        catch(Exception e)
        {
            Log.e("Utils.java parseTimeString","The supplied times are not properly formatted. Verify that your schedule times follow the hh:mm pattern");
        }

        return calendar;
    }
}
