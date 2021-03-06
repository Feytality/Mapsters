package delta.soen390.mapsters.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.Buildings.BuildingPolygonOverlay;
import delta.soen390.mapsters.Buildings.PolygonDirectory;
import delta.soen390.mapsters.Calendar.CalendarNotification;
import delta.soen390.mapsters.Controller.CampusViewSwitcher;
import delta.soen390.mapsters.Controller.NavigationDrawer;
import delta.soen390.mapsters.Controller.SearchBox;
import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.Effects.EffectManager;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlayManager;
import delta.soen390.mapsters.IndoorDirectory.RoomPolygonOverlay;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.Utils.GoogleMapCamera;
import delta.soen390.mapsters.Utils.GoogleMapstersUtils;
import delta.soen390.mapsters.ViewComponents.CampusSwitchUI;
import delta.soen390.mapsters.ViewMode.IndoorsViewMode;
import delta.soen390.mapsters.ViewMode.OutdoorsViewMode;
import delta.soen390.mapsters.ViewMode.ViewModeController;

public class MapsActivity extends FragmentActivity implements SlidingFragment.OnDataPass, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, LocationSource,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener{

    private TextView textPointer;
    private CampusSwitchUI mCampusSwitchUI;
    private CampusViewSwitcher mCampusViewSwitcher;
    private LocationService mLocationService;
    private NavigationDrawer mDrawer;
    private SplitPane splitPane;
    private InputMethodManager mImm;
    private static final String TAG = "DemoActivity";
    private GoogleMapCamera mCamera;
    // For calendar and notifications
    private PolygonOverlayManager mPolygonOverlayManager;
    private DirectionEngine mDirectionEngine;

    private EffectManager mEffectManager;

    private ViewModeController mViewModeController;
    public static PolygonDirectory sPolygonDirectory;

    // For current location, ask if theres another way to get map
    private GoogleMap mGoogleMap;
    private Marker mMarker;

    private SlidingUpPanelLayout mSlidingUpPanelLayout;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i("Intent", "the extra is:" + intent.getStringExtra("buildingCode"));
        findBuilding(intent.getStringExtra("buildingCode"));
    }

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

        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mCampusSwitchUI = new CampusSwitchUI(this, mCampusViewSwitcher);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMapAsync(this);

        setImageOptions();
        //Initialize the SlidingUpPanel
        initializeSlidingPane();
        mSlidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mSlidingUpPanelLayout.setTouchEnabled(false);

        //Initialize the CalendarEventManager
       // mCalendarEventManager = new CalendarEventManager(this.getApplicationContext());
        //CalendarEventManager.updateEventQueue();

        //mCalendarEventManager = new CalendarEventManager(this.getApplicationContext()); todo fix the alarms
        CalendarNotification calendarNotification = new CalendarNotification(this.getApplicationContext());
        calendarNotification.sendTest();

        

        //Initialize Navigation Drawer
        mDrawer = new NavigationDrawer(this);
        mDrawer.addButton();

        //EffectManager Initialization
        mEffectManager = new EffectManager();
//        mCampusSwitchUI.verifySettings();
    }



    public void initializeSlidingPane(){

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

     * This should only be called once and when we are sure that map is not null.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setLocationSource(this);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setMyLocationEnabled(true);
        googleMap.setBuildingsEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
       initializeMap(googleMap);
    }

    private void initializeMap(GoogleMap googleMap) {
        //Initialize the Campus Switch
        mCampusSwitchUI = new CampusSwitchUI(this, new CampusViewSwitcher(this, googleMap, mCampusSwitchUI));

        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMapClickListener(this);
        mGoogleMap = googleMap;

        mCamera = new GoogleMapCamera(mGoogleMap);
        mViewModeController = new ViewModeController(this);

        //Initialize the Direction Engine
        mDirectionEngine = new DirectionEngine(getApplicationContext(),googleMap, mLocationService);


        initializeOverlays();

        // For now only checks preferred default map
        checkPreferences();

        //Select a building
        SearchBox pt = new SearchBox(this);

        //focus building
        googleMap.setOnIndoorStateChangeListener(new GoogleMap.OnIndoorStateChangeListener()
        {

            @Override
            public void onIndoorBuildingFocused() {
                mViewModeController.setViewMode(new IndoorsViewMode());
            }

            @Override
            public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {

                String floorLevelName = indoorBuilding.getLevels().get(indoorBuilding.getActiveLevelIndex()).getName();
                mViewModeController.setViewMode(new IndoorsViewMode(floorLevelName));
            }
        });

        findViewById(R.id.locate_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location currentLocation = mLocationService.getLastLocation();
                if (currentLocation != null)
                    mCamera.moveToTarget(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 17);
            }
        });
    }


    private void initializeOverlays() {
        //Initialize the Building Polygons
        mPolygonOverlayManager = new PolygonOverlayManager();
        mPolygonOverlayManager.loadResources(this);
        mPolygonOverlayManager.getPolygonDirectory().activateBuildingOverlays();

        if(sPolygonDirectory  == null) {
            sPolygonDirectory = mPolygonOverlayManager.getPolygonDirectory();
        }

        //Set the view mode to outdoors since default view is of the current campus
        mViewModeController.setViewMode(new OutdoorsViewMode(mPolygonOverlayManager.getPolygonDirectory()));
    }

    private void checkPreferences() {
        // Get preferred map start
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String campusDefault = prefs.getString("campus_list", getString(R.string.is_loyola));

        final String isSGW = getString(R.string.is_sgw);
        final String isLoyola = getString(R.string.is_loyola);
        BuildingPolygonOverlay overlay;
        if(campusDefault.contains(isSGW)){
            overlay = sPolygonDirectory.getBuildingByCode("H");
            onMapClick(overlay.getCenterPoint());
            mCamera.moveToTarget(overlay.getBuildingInfo().getCoordinates(), 17);
            mCampusSwitchUI.toggleCampusSwitch();
        } else if (campusDefault.contains(isLoyola)) {
            overlay = sPolygonDirectory.getBuildingByCode("CC");
            onMapClick(overlay.getCenterPoint());
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if(mMarker != null) {
            mMarker.remove();
        }

        //Set the initial location to the last polled user location
        Location lastLocation = mLocationService.getLastLocation();
        if(mDirectionEngine != null) {
            mDirectionEngine.setInitialLocation(null);
        }
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
       if(mDirectionEngine != null)
       {
           mDirectionEngine.setInitialLocation(GoogleMapstersUtils.toDirectionsLatLng(point));
       }

    }

    @Override
    public void deactivate() {
        mLocationService.setLocationListener(null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                findBuilding(result);


            }
            if (resultCode == RESULT_CANCELED) {

            }
        }//onActivityResul
    }
    public void findBuilding(String result) {
        BuildingPolygonOverlay overlay = mPolygonOverlayManager.getPolygonDirectory().getBuildingByCode(result);
        if(overlay == null)
            return;
        mCamera.moveToTarget(overlay.getBuildingInfo().getCoordinates(),17);
        onMapClick(overlay.getBuildingInfo().getCoordinates());

      

    }
  
    @Override
    public void onMapClick(LatLng latLng) {

        PolygonOverlay overlay = mPolygonOverlayManager.getClickedPolygon(latLng);
        if(overlay != null) {
            //Check if a building was clicked
            if(overlay.getClass() == BuildingPolygonOverlay.class)
            {
                if(mDirectionEngine.isDirectionPathEmpty()) {
                    BuildingInfo info = ((BuildingPolygonOverlay) overlay).getBuildingInfo();
                    splitPane.updateContent(info);

                } else {
                    //Do not want to update content because the user is in the wrong context. must clear directions first using back button.
                }
            }
            else if(overlay.getClass() == RoomPolygonOverlay.class)
            {
                //don't focus rooms
                return;
            }
            overlay.focus();
        }
            
            //set current buildind
            mImm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }


    public LocationService getLocationService() {
        return mLocationService;
    }

    public DirectionEngine getDirectionEngine()
    {
        return mDirectionEngine;
    }

    @Override
    public void onDataPass(SplitPane data) {

        splitPane =data;
    }



  //  public void onBackPressed(){}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){

            case KeyEvent.KEYCODE_BACK:
                    requestLowerPanel();
                    initializeSlidingPane();
                    if(!mDirectionEngine.isDirectionPathEmpty()) {
                        mDirectionEngine.clearEngineState();
                    }
                //clear all effects on currently selected polygons

                mPolygonOverlayManager.unfocusOverlay();
                mViewModeController.setViewMode( new OutdoorsViewMode(mPolygonOverlayManager.getPolygonDirectory()));
                    mSlidingUpPanelLayout.setTouchEnabled(false);
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


    public void requestLockPanel() {
        SlidingUpPanelLayout panel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
            panel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            panel.setTouchEnabled(false);
    }

    public  void indoorConfiguration(){
        setSearchComboVisible(false);
        findViewById(R.id.locate_me).setVisibility(View.INVISIBLE);
    }

    public  void outdoorConfiguration(){
        setSearchComboVisible(true);
            findViewById(R.id.locate_me).setVisibility(View.VISIBLE);
    }

    public void setSearchComboVisible(boolean value)
    {
        setUiElementVisible(R.id.search_combo, value);
    }

    public void setLocationButtonVisible(boolean value)
    {
        setUiElementVisible(R.id.locate_me, value);
    }

    private void setUiElementVisible(int viewId, boolean value)
    {
        int visibilityValue = View.VISIBLE;
        if(!value)
        {
            visibilityValue = View.INVISIBLE;
        }
        findViewById(viewId).setVisibility(visibilityValue);
    }


    public GoogleMapCamera getGoogleMapCamera() { return mCamera;}

    public GoogleMap getGoogleMap() { return mGoogleMap;}

    public PolygonOverlayManager getPolygonOverlayManager() { return mPolygonOverlayManager; }

    public ViewModeController getViewModeController() { return mViewModeController;}
     public EffectManager getEffectManager(){return mEffectManager;}

}

