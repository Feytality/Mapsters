package delta.soen390.mapsters.Activities;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.Calendar.CalendarEventManager;
import delta.soen390.mapsters.Calendar.CalendarEventNotification;
import delta.soen390.mapsters.Controller.CampusViewSwitcher;
import delta.soen390.mapsters.Controller.NavigationDrawer;
import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.Utils.GoogleMapstersUtils;
import delta.soen390.mapsters.ViewComponents.CampusSwitchUI;

public class MapsActivity extends FragmentActivity implements SlidingFragment.OnDataPass, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, LocationSource, GoogleMap.OnMapLongClickListener {

    private TextView textPointer;
    private CampusSwitchUI mCampusSwitchUI;
    private CampusViewSwitcher mCampusViewSwitcher;
    private LocationService mLocationService;
    private NavigationDrawer mDrawer;
    private SplitPane splitPane;
    private static final String TAG = "DemoActivity";

    // For calendar and notifications
    private CalendarEventManager mCalendarEventManager;
    private CalendarEventNotification mCalendarEventNotification;

    private DirectionEngine mDirectionEngine;

    // For current location, ask if theres another way to get map
    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private LatLng mStartingLocation;
    private BuildingInfo mCurrentBuilding;
    private DirectionEngine.DirectionPath mCurrentDirectionPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,SplashActivity.class));
        setContentView(R.layout.activity_maps);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActionBar().hide();
        }
        //Setup the google map
        // initialize location
        mLocationService = new LocationService(getApplicationContext());
        mCampusSwitchUI = new CampusSwitchUI(this, mCampusViewSwitcher);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMapAsync(this);

        setImageOptions();
        //Initialize the SlidingUpPanel
        initializeSlidingPane();

        //Initialize the CalendarEventManager
        mCalendarEventManager = new CalendarEventManager(this.getApplicationContext());
        mCalendarEventManager.updateEventQueue();

        // Uncomment to test notifications.
        // mCalendarEventNotification = new CalendarEventNotification(this.getApplicationContext(), this,
        // new CalendarEvent("H", "H431", "SOEN 390", new DateTime(1425333436), new DateTime(1425333436), new DateTime(1425333436)));
        //  mCalendarEventNotification.createNotification();

        //Initialize notifications
        mCalendarEventNotification = new CalendarEventNotification(this.getApplicationContext(), this);
        mCalendarEventNotification.handleNotifications();

        //Initialize Navigation Drawer
        mDrawer = new NavigationDrawer(this);
        mDrawer.addButton();

        String msg = PreferenceManager.getDefaultSharedPreferences(this).getString("campus_list","NOO");

    }

    private void initializeSlidingPane(){
        SlidingFragment slidingFragment = new SlidingFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.sliding_container,slidingFragment )
                .commit();
    }

    public void setImageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationService.getGoogleApiClient().disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationService.getGoogleApiClient().connect();
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>

     * This should only be called once and when we are sure that map is not null.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setLocationSource(this);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setBuildingsEnabled(false);

       initializeMap(googleMap);
    }

    private void initializeMap(GoogleMap googleMap) {
        //Initialize the Campus Switch
        mCampusSwitchUI = new CampusSwitchUI(this, new CampusViewSwitcher(this, googleMap));

        //Initialize the Building Polygons
        BuildingPolygonManager.getInstance().loadResources(googleMap, splitPane, getApplicationContext());

        //Initialize the Direction Engine
        mDirectionEngine = new DirectionEngine(getApplicationContext(),googleMap);

        googleMap.setOnMapLongClickListener(this);
        mGoogleMap = googleMap;

    }

    @Override
    public boolean onMyLocationButtonClick() {
        if(mMarker != null) {
            mMarker.remove();
        }
//        splitPane.setStartingLocation(new LatLng(mLocationService.getLastLocation().getLatitude(), mLocationService.getLastLocation().getLongitude()));
        setStartingLocation(null);
        return false;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationService.setLocationListener(onLocationChangedListener);
    }

    @Override
    public void onMapLongClick(LatLng point) {
        if(mMarker != null){
            mMarker.remove();
        }
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(point).title(
                "Your starting location!"));

        // Set starting location.
       setStartingLocation(point);
    }

    @Override
    public void deactivate() {
        mLocationService.setLocationListener(null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){

                String result=data.getStringExtra("result");
                mCampusSwitchUI.getmCampusViewSwitcher().cameraToPoint(result);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,"whyyyyy",Toast.LENGTH_SHORT).show();            }
        }
    }//onActivityResult

    public LocationService getLocationService() {
        return mLocationService;
    }



    public void getDirections(){
        Log.i("Direction Button", "Clicked!");
        Location lastLocation = mLocationService.getLastLocation();
        if (lastLocation == null) {
            Log.i("last direction", "null");
            return;
        } else {
            Log.i("Current Coords", mLocationService.getLastLocation().getLatitude() + " " + mLocationService.getLastLocation().getLongitude());
        }

        //TODO toast notify user of connectivity problem
        if(mDirectionEngine == null) {
            return;
        }


        LatLng currentBuildingCoordinates = BuildingPolygonManager.getInstance().getCurrentBuildingInfo().getCoordinates();
        if(currentBuildingCoordinates == null)
            return;

        if(mCurrentDirectionPath != null){
            mCurrentDirectionPath.hideDirectionPath();
        }

        if(mStartingLocation == null) {
            mCurrentDirectionPath = mDirectionEngine.GenerateDirectionPath(
                    new com.google.maps.model.LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()),
                    GoogleMapstersUtils.toDirectionsLatLng(currentBuildingCoordinates));
        } else {
            // Else, starting location is set (by placing marker on map), use the choosen location coordinates instead.
            mCurrentDirectionPath = mDirectionEngine.GenerateDirectionPath(
                    GoogleMapstersUtils.toDirectionsLatLng(mStartingLocation),
                    GoogleMapstersUtils.toDirectionsLatLng(currentBuildingCoordinates));
        }

        mCurrentDirectionPath.showDirectionPath();

    }

    public void setStartingLocation(LatLng startingLocation) {
        // Note: should be able to set it null to clear it
        mStartingLocation = startingLocation;
        if(startingLocation != null)
            Log.i("Set starting location!", startingLocation.toString());

    }



    @Override
    public void onDataPass(SplitPane data) {

        splitPane =data;
    }
}

