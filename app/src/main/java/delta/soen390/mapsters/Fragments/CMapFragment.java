package delta.soen390.mapsters.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.Calendar.CalendarEventManager;
import delta.soen390.mapsters.Calendar.CalendarEventNotification;
import delta.soen390.mapsters.Controller.CampusViewSwitcher;
import delta.soen390.mapsters.Controller.NavigationDrawer;
import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.ViewComponents.CampusSwitchUI;



public class CMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, LocationSource {

    private View view;
    private FragmentActivity mActivity;
    private CampusSwitchUI mCampusSwitchUI;
    private CampusViewSwitcher mCampusViewSwitcher;
    private LocationService mLocationService;
    private NavigationDrawer mDrawer;
    private SplitPane splitPane;
    private static final String TAG = "DemoActivity";
    private CalendarEventManager mCalendarEventManager;
    private CalendarEventNotification mCalendarEventNotification;
    private SupportMapFragment fragment;
    private GoogleMap map;
    private Button mNavBtn;

    public static CMapFragment newInstance(String param1, String param2) {
        CMapFragment fragment = new CMapFragment();
        Bundle args = new Bundle();



        fragment.setArguments(args);
        return fragment;
    }

    public CMapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_map, container, false);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
        }
       mActivity = getActivity();

        mLocationService = new LocationService(mActivity.getApplicationContext());


        mCampusSwitchUI = new CampusSwitchUI(view, mActivity, mCampusViewSwitcher);

        //Initialize the SlidingUpPanel
       splitPane = new SplitPane(view.findViewById(R.id.sliding_layout), 0.50f, mLocationService, mActivity);

        //Initialize the CalendarEventManager
        mCalendarEventManager = new CalendarEventManager(mActivity.getApplicationContext());
        mCalendarEventManager.updateEventQueue();

        mNavBtn = (Button)view.findViewById(R.id.btn_nav_drawer);
        mNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout)mActivity.findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    @Override
    public void onPause() {
        super.onPause();
        mLocationService.getGoogleApiClient().disconnect();
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>

     * This should only be called once and when we are sure that map is not null.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            googleMap = fragment.getMap();
            googleMap.setLocationSource(this);
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMyLocationButtonClickListener(this);
            googleMap.setBuildingsEnabled(false);
            googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
            mCampusSwitchUI = new CampusSwitchUI(view,mActivity,new CampusViewSwitcher(mActivity, map,mCampusSwitchUI));
            BuildingPolygonManager.getInstance().loadResources(map, splitPane, mActivity.getApplicationContext());
        }
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

    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
            map = fragment.getMap();
            map.setLocationSource(this);
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(this);
            map.setBuildingsEnabled(false);
            map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
            mCampusSwitchUI = new CampusSwitchUI(view,mActivity,new CampusViewSwitcher(mActivity, map,mCampusSwitchUI));
            BuildingPolygonManager.getInstance().loadResources(map, splitPane, mActivity.getApplicationContext());
        }
    }

}
