package delta.soen390.mapsters.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by georgevalergas on 15-03-29.
 */
public class TextMarkerFactory implements GoogleMap.InfoWindowAdapter {
    GoogleMap mGoogleMap;
    MarkerOptions mMarkerOptions;
    Context mContext;
    public TextMarkerFactory(Context context,GoogleMap googleMap, LatLng latLng, String textMessage, String color) {
        mContext = context;
        mGoogleMap = googleMap;
        googleMap.setInfoWindowAdapter(this);
        mMarkerOptions = new MarkerOptions();
        mMarkerOptions
                .position(latLng)
                .title(textMessage)
                .snippet(color)
                .draggable(false);
    }

    public Marker placeMarker() {
        Marker m = mGoogleMap.addMarker(mMarkerOptions);
        m.showInfoWindow();
        return m;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView tv = new TextView(mContext);
        if (marker.getSnippet() != null) {
            tv.setTextColor(Color.parseColor(marker.getSnippet()));
        }
        tv.setBackgroundColor(0x99FFFFFF);
        tv.setText(marker.getTitle());
        return tv;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
