package delta.soen390.mapsters.Activities;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.Controller.CampusViewSwitcher;
import delta.soen390.mapsters.ViewComponents.FocusMapUI;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.ViewComponents.CampusSwitchUI;


public class MapsActivity extends FragmentActivity implements AdapterView.OnItemClickListener {

    private TextView textPointer;
    // Might be null if Google Play services APK is not available.
    private GoogleMap mMap;

    // For focus on my location button in top right corner.
    private FocusMapUI mFocusMapUI;
	
    private CampusSwitchUI mCampusSwitchUI;
    private CampusViewSwitcher mCampusViewSwitcher;

    private boolean debug = false;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private SlidingUpPanelLayout mLayout;
    private static final String TAG = "DemoActivity";
    private ListView mListView;
    private Button mNavBtn;

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
        mCampusSwitchUI = new CampusSwitchUI(this,mCampusViewSwitcher);
        mMap.setBuildingsEnabled(false);
        //Initialize the focus current location button
        mFocusMapUI = new FocusMapUI(mMap, this);
        mFocusMapUI.determineGpsEnabled();

        //Initialize the SlidingUpPanel
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setAnchorPoint(0.50f);
        //Initialize the Navigation drawer
        initializeNavDrawer();

        //initialize hamburger btn
        mNavBtn = (Button)findViewById(R.id.btn_nav_drawer);
        mNavBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    });



        BuildingPolygonManager.getInstance().loadResources(mMap,this);
    }

private void initializeNavDrawer(){
    String[] menuItems = new String[]{
            getString(R.string.title_section1),
            getString(R.string.title_section2),
            getString(R.string.title_section3),
    };
    mListView = (ListView) findViewById(R.id.left_drawer);
    mListView.setAdapter(new ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_activated_1,
            android.R.id.text1, menuItems
    ));
    mListView.setOnItemClickListener(this);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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

        if(mFocusMapUI.getLocationManager() != null)  {
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
                mFocusMapUI.setMap(mMap);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"Fuckin "+position,Toast.LENGTH_SHORT).show();
    mDrawerLayout.closeDrawer(Gravity.LEFT);
    }
}

