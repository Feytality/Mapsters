package delta.soen390.mapsters;


import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;


public class MapsActivity extends FragmentActivity {

    private TextView textPointer;
    // Might be null if Google Play services APK is not available.
    private GoogleMap mMap;

    // For focus on my location button in top right corner.
    private FocusMapUI mFocusMapUI;
	
    private CampusSwitch mCampusSwitch;
    private CampusViewSwitcher mCampusViewSwitcher;

    private BuildingInfoRepository bir;
    private boolean debug = false;

    private SlidingUpPanelLayout mLayout;
    private static final String TAG = "DemoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initialize();

    }

    private void initialize()
    {
        //Setup the google map
        initializeMap();

        //Initialize the Campus Switch
        mCampusViewSwitcher = new CampusViewSwitcher(this,mMap);
        mCampusSwitch = new CampusSwitch(this,mCampusViewSwitcher);

        //Initialize the focus current location button
        mFocusMapUI = new FocusMapUI(mMap, this);
        mFocusMapUI.determineGpsEnabled();

        //Initialize the building info repository
        //TODO remove BuildingInfoRepository
        bir = BuildingInfoRepository.getInstance();

        //Initialize the SlidingUpPanel
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setAnchorPoint(0.50f);
    }


    @Override
    public void onPause()
    {
        mFocusMapUI.removeUpdates();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        if(mFocusMapUI.getmLocationManager() != null)  {
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */

    private void initializeMap()
    {
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment))
                .getMap();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
           initializeMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                mFocusMapUI.setmMap(mMap);
                mFocusMapUI.setLocationSource();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        // leaving comment here for reference. pls don't shoot me. *shoots george*
        mMap.setMyLocationEnabled(true); // Shows location button on top right.
    }

}

