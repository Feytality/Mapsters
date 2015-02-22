package delta.soen390.mapsters.Controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.joda.time.DateTime;

import java.util.Date;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.Services.ShuttleBusService;


public class SplitPane {
    private SlidingUpPanelLayout mLayout;
    private BuildingInfo mCurrentBuilding;
    private LocationService mLocationService;
    private Context mContext;

    //View Components
    private TextView mBuildingName;
    private TextView mBuildingCode;
    private TextView mCampus;
    private TextView mBuildingServices;
    private ImageView mBuildingPictureView;
    private ImageButton mDirectionButton;

    public SplitPane(View view, float anchorPoint, LocationService locationService, Context context) {
        mContext = context;
        mLayout = (SlidingUpPanelLayout) view;
        mLayout.setAnchorPoint(anchorPoint);
        mCurrentBuilding = null;
        mLocationService = locationService;

        //initializing components
        mBuildingName = (TextView) mLayout.findViewById(R.id.building_name);
        mBuildingCode = (TextView) mLayout.findViewById(R.id.building_code);
        mCampus = (TextView) mLayout.findViewById(R.id.campus);
        mBuildingServices = (TextView) mLayout.findViewById(R.id.building_services);
        mBuildingPictureView = (ImageView) mLayout.findViewById(R.id.building_image);
        mDirectionButton = (ImageButton) mLayout.findViewById(R.id.direction_button);
        mDirectionButton.setOnClickListener(directionBtnListener);
    }

    public void updateContent(BuildingInfo buildingInfo) {
        if (mCurrentBuilding == null) {
            mDirectionButton.setVisibility(View.VISIBLE);
        }

        mCurrentBuilding = buildingInfo;

        mBuildingName.setText(buildingInfo.getBuildingName());
        mBuildingCode.setText(buildingInfo.getBuildingCode());
        mCampus.setText(buildingInfo.getCampus());

        ImageLoader.getInstance().displayImage(buildingInfo.getImageUrl(), mBuildingPictureView);
    }

    private View.OnClickListener directionBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i("Direction Button", "Clicked!");
            //Get Current Location
            double currentLat = 45, currentLng = -73; //instead of 0,0...at least now it's near MTL
            LatLng currentLocation = null;
            if (mLocationService.getLastLocation() == null) {
                Log.i("last direction", "null");
                //TODO Throw error so new intent below doesn't cause crash
            } else {
                currentLat = mLocationService.getLastLocation().getLatitude();
                currentLng = mLocationService.getLastLocation().getLongitude();
                currentLocation = new LatLng(currentLat,currentLng);
                Log.i("Current Coords", currentLat + " " + currentLng);
            }
            ShuttleWins(currentLocation,mCurrentBuilding.getBuildingCode());
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

//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                    Uri.parse("http://maps.google.com/maps?saddr=" + currentLat + "," +
//                            currentLng +
//                            "&daddr=" + mCurrentBuilding.getCoordinates().latitude + "," +
//                            mCurrentBuilding.getCoordinates().longitude));
//
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            mContext.startActivity(intent);
        }

    };
    //determines whether shuttle bus is better
    private boolean ShuttleWins(LatLng currentLocation, String destinationBuildingCode) {
        ShuttleBusService.ShuttleLocation nearestShuttle = ShuttleBusService.getNearestShuttle(destinationBuildingCode);
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
            dar.departureTime(new DateTime(ShuttleBusService.getNextBusLoy(new Date(),mContext)));
        }
        return dar.awaitIgnoreError()[0].legs[0].duration.inSeconds;
    }

}
