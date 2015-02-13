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
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;


public class MapsActivity extends FragmentActivity {

    // Might be null if Google Play services APK is not available.
    private GoogleMap mMap;

    // For focus on my location button in top right corner.
    private FocusMapUI mFocusMapUI;
	
    private CampusSwitch mCampusSwitch;
    private CampusViewSwitcher mCampusViewSwitcher;


    private SlidingUpPanelLayout mLayout;
    private static final String TAG = "DemoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        BuildingInfoRepository.getInstance();

        // Setting up for focus button.
        mFocusMapUI = new FocusMapUI(mMap, this);
        mFocusMapUI.determineGpsEnabled();

        setUpMapIfNeeded();

        hookUpSwitch();

        setBuilding();

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setAnchorPoint(0.50f);
        mLayout.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
                Log.i(TAG, mLayout.getPanelState().toString());

            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");
                Log.i(TAG, mLayout.getPanelState().toString());

            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");
                Log.i(TAG, mLayout.getPanelState().toString());

            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
                Log.i(TAG, mLayout.getPanelState().toString());

            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "onPanelHidden");
                Log.i(TAG, mLayout.getPanelState().toString());

            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                Log.i(TAG, mLayout.getPanelState().toString());
                mLayout.setPanelState(PanelState.ANCHORED);
            }
        });
    }

    public void setBuilding() {
        //Hall
        BuildingPolygon hall = new BuildingPolygon(mMap,
                new LatLng(45.4967893,-73.5788298),
                new LatLng(45.49737590000001,-73.5782182),
                new LatLng(45.4980001,-73.5794628),
                new LatLng(45.4973383,-73.580085),
                new LatLng(45.4967893, -73.5788298));
        hall.setVisibility(true);
        hall.setFillColor(Color.TRANSPARENT);


        BuildingPolygon EV = new BuildingPolygon(mMap,
                new LatLng(45.4951574,-73.5778749),
                new LatLng(45.4957966,-73.577199),
                new LatLng(45.496029799999995,-73.5777247),
                new LatLng(45.495744,-73.57803580000001) ,
                new LatLng(45.4961426, -73.5789478),
                new LatLng(45.495728899999996, -73.5792589),
                new LatLng(45.4951574,-73.5778749));
        EV.setVisibility(true);
        EV.setFillColor(Color.TRANSPARENT);


        BuildingPolygon LB = new BuildingPolygon(mMap,
                new LatLng(45.4973571,-73.5781056),
                new LatLng(45.4967028,-73.5787332),
                new LatLng(45.4961952, -73.5777193),
                new LatLng(45.496868299999996, -73.5770649),
                new LatLng(45.4973571, -73.5781056));

        LB.setVisibility(true);
        LB.setFillColor(Color.TRANSPARENT);


        BuildingPolygon CBuilding = new BuildingPolygon(mMap,
                new LatLng(45.458445,-73.6407191),
                new LatLng(45.458302,-73.6408424),
                new LatLng(45.4580009, -73.6400592),
                new LatLng(45.4581364, -73.6399519),
                new LatLng(45.458445,-73.6407191  ));

        CBuilding.setVisibility(true);
        CBuilding.setFillColor(Color.TRANSPARENT);

        Log.i(TAG,"This is the building method");
    }

    /**
     * Responsible for adding the switch of both campuses (SGW and Loyola), and the functionality
     * of viewing these campuses on the map.
     */
    private void hookUpSwitch() {
        mCampusViewSwitcher = new CampusViewSwitcher(this,mMap);
        mCampusSwitch = new CampusSwitch(this,mCampusViewSwitcher);

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
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment))
                    .getMap();
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
        //        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        mMap.setMyLocationEnabled(true); // Shows location button on top right.
    }

}

