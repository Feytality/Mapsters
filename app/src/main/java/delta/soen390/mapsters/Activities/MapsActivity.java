package delta.soen390.mapsters.Activities;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.Calendar.CalendarEventManager;
import delta.soen390.mapsters.Calendar.CalendarEventNotification;
import delta.soen390.mapsters.Calendar.CalendarIntent;
import delta.soen390.mapsters.Controller.CampusViewSwitcher;
import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.ViewComponents.CampusSwitchUI;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, LocationSource {

    private TextView textPointer;

    private CampusSwitchUI mCampusSwitchUI;
    private CampusViewSwitcher mCampusViewSwitcher;
    private LocationService mLocationService;
    private SplitPane splitPane;
    private static final String TAG = "DemoActivity";
    private CalendarEventManager mCalendarEventManager;
    private CalendarEventNotification mCalendarEventNotif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
     * This should only be called once and when we are sure that {@link } is not null.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setLocationSource(this);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(this);

        //Initialize the Campus Switch
        mCampusSwitchUI = new CampusSwitchUI(this, new CampusViewSwitcher(this, googleMap));
        //Initialize the Building Polygons
        BuildingPolygonManager.getInstance().loadResources(googleMap, splitPane, getApplicationContext());
        mCalendarEventNotif = new CalendarEventNotification(this.getApplicationContext());
    }

    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationService.setLocationListener(onLocationChangedListener);
    }

    @Override
    public void deactivate() {
        mLocationService.setLocationListener(null);
    }

    public void showNotificationClicked(View v) {
        // Create notification

    }
}

