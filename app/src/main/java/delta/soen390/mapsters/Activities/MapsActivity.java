package delta.soen390.mapsters.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.Calendar.CalendarEventManager;
import delta.soen390.mapsters.Calendar.CalendarEventNotification;
import delta.soen390.mapsters.Controller.CampusViewSwitcher;
import delta.soen390.mapsters.Controller.NavigationDrawer;
import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.ViewComponents.CampusSwitchUI;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, LocationSource, GoogleMap.OnMapLongClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,SplashActivity.class));
        setContentView(R.layout.activity_maps);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActionBar().hide();
        }
        //Setup the google map
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMapAsync(this);

        setImageOptions();

        //initialize location
        mLocationService = new LocationService(getApplicationContext());

        mCampusSwitchUI = new CampusSwitchUI(this, mCampusViewSwitcher);


        //Initialize the SlidingUpPanel
        splitPane = new SplitPane(findViewById(R.id.sliding_layout), 0.50f, mLocationService, getApplicationContext());

        //Initialize the CalendarEventManager
        mCalendarEventManager = new CalendarEventManager(this.getApplicationContext());
        mCalendarEventManager.updateEventQueue();

        //Initialize notifications
        mCalendarEventNotification = new CalendarEventNotification(this.getApplicationContext(), this);
        mCalendarEventNotification.handleNotifications();

        //Initialize Navigation Drawer
        mDrawer = new NavigationDrawer(this);
        mDrawer.addButton();

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
        splitPane.setDirectionEngine(mDirectionEngine);

        googleMap.setOnMapLongClickListener(this);
        mGoogleMap = googleMap;

    }

    @Override
    public boolean onMyLocationButtonClick() {
        if(mMarker != null) {
            mMarker.remove();
        }
        //splitPane.setStartingLocation(new LatLng(mLocationService.getLastLocation().getLatitude(), mLocationService.getLastLocation().getLongitude()));
        splitPane.setStartingLocation(null);
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
        splitPane.setStartingLocation(point);
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

}

