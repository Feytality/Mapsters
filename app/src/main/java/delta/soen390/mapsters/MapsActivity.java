package delta.soen390.mapsters;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ViewSwitcher mMapSwitcher;
    private Switch mCampusSwitch;
    private Animation slideInLeft, slideOutRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        hookUpSwitch();
    }

    /**
     * Responsible for adding the switch of both campuses (SGW and Loyola), and the functionality
     * of viewing these campuses on the map.
     */
    private void hookUpSwitch() {
        mCampusSwitch = (Switch)findViewById(R.id.campusSwitch);
        mMapSwitcher = (ViewSwitcher) findViewById(R.id.mapSwitcher);

        slideInLeft = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        slideOutRight = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);

        mMapSwitcher.setInAnimation(slideInLeft);
        mMapSwitcher.setOutAnimation(slideOutRight);

        if (mCampusSwitch != null) {
            mCampusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(MapsActivity.this, "Show SGW Map", Toast.LENGTH_SHORT).show();
                        mMapSwitcher.showNext();
                    } else {
                        Toast.makeText(MapsActivity.this, "Show Loyola Map", Toast.LENGTH_SHORT).show();
                        mMapSwitcher.showPrevious();
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
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
        // leaving comment here for reference. pls don't shoot me.
        //        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
