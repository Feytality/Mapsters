package delta.soen390.mapsters.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.Buildings.BuildingPolygon;
import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.Calendar.CalendarEventManager;
import delta.soen390.mapsters.Calendar.CalendarEventNotification;
import delta.soen390.mapsters.Controller.CampusViewSwitcher;
import delta.soen390.mapsters.Controller.NavigationDrawer;
import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.Fragments.SearchBarFragment;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.Utils.GoogleMapstersUtils;
import delta.soen390.mapsters.ViewComponents.CampusSwitchUI;

public class MapsActivity extends FragmentActivity implements SlidingFragment.OnDataPass, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, LocationSource,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, SearchBarFragment.SearchBarListener {

    private TextView textPointer;
    private CampusSwitchUI mCampusSwitchUI;
    private CampusViewSwitcher mCampusViewSwitcher;
    private LocationService mLocationService;
    private NavigationDrawer mDrawer;
    private SplitPane splitPane;
    private InputMethodManager mImm;
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
        startActivity(new Intent(this, SplashActivity.class));
        setContentView(R.layout.activity_maps);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActionBar().hide();
        }
        //Setup the google map
        // initialize location
        mLocationService = new LocationService(getApplicationContext());
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

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

        String msg = PreferenceManager.getDefaultSharedPreferences(this).getString("campus_list", "NOO");

    }


    public void initializeSlidingPane() {

        final SlidingFragment slidingFragment = new SlidingFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.sliding_container, slidingFragment, "info")
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
        mCampusSwitchUI = new CampusSwitchUI(this, new CampusViewSwitcher(this, googleMap, mCampusSwitchUI));

        //Initialize the Building Polygons
        BuildingPolygonManager.getInstance().loadResources(googleMap, splitPane, getApplicationContext());

        //Initialize the Direction Engine
        mDirectionEngine = new DirectionEngine(getApplicationContext(), googleMap);

        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMapClickListener(this);
        mGoogleMap = googleMap;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (mMarker != null) {
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
        if (mMarker != null) {
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
            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");
                mCampusSwitchUI.getmCampusViewSwitcher().cameraToPoint(result);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "whyyyyy", Toast.LENGTH_SHORT).show();
            }
        }
    }//onActivityResult


    @Override
    public void searchForRoom(String input) {
        boolean firstChar = false;
        String buildingCode = "";

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                firstChar = true;
                buildingCode += c;
                continue;
            }
            if (firstChar) {
                break;
            }
        }

        if (buildingCode.isEmpty()) {
            Toast.makeText(this, "Please put in a building code in this format H456", Toast.LENGTH_SHORT).show();
        }

        BuildingPolygon buildingPolygon = BuildingPolygonManager.getInstance().getBuildingPolygonByBuildingCode(buildingCode);

        if (buildingPolygon != null) {
            BuildingPolygonManager.getInstance().clickAndPopulate(buildingPolygon);
            mCampusSwitchUI.getmCampusViewSwitcher().zoomToLatLong(18, buildingPolygon.getBuildingInfo());
            return;
        }

        Toast.makeText(this, "Please enter a proper building code", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        BuildingPolygonManager.getInstance().clickPolygon(latLng);
        mImm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }


    public LocationService getLocationService() {
        return mLocationService;
    }


    public void getDirections() {
        Log.i("Direction Button", "Clicked!");
        Location lastLocation = mLocationService.getLastLocation();
        if (lastLocation == null) {
            Log.i("last direction", "null");
            return;
        } else {
            Log.i("Current Coords", mLocationService.getLastLocation().getLatitude() + " " + mLocationService.getLastLocation().getLongitude());
        }

        //TODO toast notify user of connectivity problem
        if (mDirectionEngine == null) {
            return;
        }


        LatLng currentBuildingCoordinates = BuildingPolygonManager.getInstance().getCurrentBuildingInfo().getCoordinates();
        if (currentBuildingCoordinates == null)
            return;

        if (mCurrentDirectionPath != null) {
            mCurrentDirectionPath.hide();
        }

        com.google.maps.model.LatLng startingLocation, endLocation;
        endLocation = GoogleMapstersUtils.toDirectionsLatLng(currentBuildingCoordinates);
        if (mStartingLocation == null) {
            startingLocation = new com.google.maps.model.LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        } else {
            startingLocation = GoogleMapstersUtils.toDirectionsLatLng(mStartingLocation);
        }
        mDirectionEngine.setInitialLocation(startingLocation);
        mDirectionEngine.setFinalLocation(endLocation);

        mDirectionEngine.updateDirectionPath(DirectionEngine.DirectionType.TRANSIT, DirectionEngine.DirectionType.WALKING);
        mDirectionEngine.showDirectionPath(DirectionEngine.DirectionType.TRANSIT);

    }

    public void setStartingLocation(LatLng startingLocation) {
        // Note: should be able to set it null to clear it
        mStartingLocation = startingLocation;
        if (startingLocation != null)
            Log.i("Set starting location!", startingLocation.toString());

    }


    public DirectionEngine getDirectionEngine()
    {
        return mDirectionEngine;
    }

    @Override
    public void onDataPass(SplitPane data) {

        splitPane = data;
    }


    //  public void onBackPressed(){}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                requestLowerPanel();
                initializeSlidingPane();
                return true;
        }
        this.onBackPressed();
        return super.onKeyDown(keyCode, event);
    }

    public void requestLowerPanel() {
        SlidingUpPanelLayout panel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        if (panel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
            panel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

}

