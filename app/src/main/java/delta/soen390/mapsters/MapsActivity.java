package delta.soen390.mapsters;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {



	private GoogleMap mMap; // Might be null if Google Play services APK is not available.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		setUpMapIfNeeded();
		canHazMapClick();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

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
		mMap.setBuildingsEnabled(false);
		LatLng mapCenter = new LatLng(45.4953144,-73.5785334);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				mapCenter, 19));
		final CameraPosition  cameraPosition = CameraPosition.builder()
				.target(mapCenter)
				.zoom(17)
				.bearing(0)
				.tilt(0)
				.build();

		wantSumPoly();
		mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
			public void onMapLoaded() {
				mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
						2000, null);
			}
		});
	}
	private BuildingPolygon test;
	private void wantSumPoly(){
		test = new BuildingPolygon(mMap,
				new LatLng(45.4973721,-73.5783416),
				new LatLng(45.4976993,-73.5790229),
				new LatLng(45.4971691,-73.5795432),
				new LatLng(45.4968344,-73.5788566));

		test.setFillColor(Color.CYAN);
		//EV
		PolygonOptions rectOptions2 = new PolygonOptions().fillColor(Color.argb(255,145,30,53))
				.add(new LatLng(45.49557480000001, -73.5788512),
						new LatLng(45.4951912, -73.5779071),
						new LatLng(45.4954319, -73.5776603),
						new LatLng(45.49584930000001, -73.5785776),
						new LatLng(45.49557480000001, -73.5788512));

// Get back the mutable Polygon
		Polygon polygon2 = mMap.addPolygon(rectOptions2);



	}

	private void canHazMapClick(){
		mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {
				Log.d("Map", point.toString());
				Toast.makeText(getApplicationContext(), isConU(point),
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	private String isConU(LatLng point){

		if(test.isPointInsideRectangle(point))
			return "This is EV Bitch!";
		return "You...uh.. missed";

	}

}