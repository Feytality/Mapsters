package delta.soen390.mapsters.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;

import java.util.Collection;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Effects.EffectManager;
import delta.soen390.mapsters.Effects.MarkerEffect;
import delta.soen390.mapsters.Effects.MonochromeOverlayEffect;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.ViewMode.ViewModeController;


public class IndoorModeFragment extends Fragment {
    View view;
    public IndoorModeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_indoor_mode, container, false);
        final MapsActivity mapsActivity = (MapsActivity) getActivity();
mapsActivity.requestLockPanel();

        final EffectManager mEffectManager = mapsActivity.getEffectManager();
        final GoogleMap googleMap = mapsActivity.getGoogleMap();
        ImageButton entryBtn = (ImageButton) view.findViewById(R.id.show_entry_points);
        entryBtn.setOnClickListener(new View.OnClickListener() {
            private boolean isActive = false;
            @Override
            public void onClick(View v) {
                ViewModeController controller = mapsActivity.getViewModeController();

                if(!isActive)
                {
                    Collection<? extends PolygonOverlay> overlays = controller.getCurrentlyActiveByAttribute("Entry");
                    for(PolygonOverlay overlay :overlays)
                    {
                        mEffectManager.addEffect(overlay,new MonochromeOverlayEffect(overlay, Color.argb(150,200,0,0)));
                    }

                }
                else
                {
                    Collection<? extends PolygonOverlay> overlays = controller.getCurrentlyActiveByAttribute("Entry");
                    for(PolygonOverlay overlay :overlays)
                    {
                        mEffectManager.removeEffect(overlay);
                    }
                }
                isActive = !isActive;
            }
        });

        ImageButton bathroomBtn = (ImageButton) view.findViewById(R.id.show_bathrooms);
        bathroomBtn.setOnClickListener(new View.OnClickListener() {
            private boolean isActive = false;
            @Override
            public void onClick(View v) {
                ViewModeController controller = mapsActivity.getViewModeController();
                if(!isActive)
                {
                    Collection<? extends PolygonOverlay> overlays = controller.getCurrentlyActiveByAttribute("Facility");
                    for(PolygonOverlay overlay :overlays)
                    {
                        mEffectManager.addEffect(overlay,new MonochromeOverlayEffect(overlay, Color.argb(150,255,255,0)));
                    }

                }
                else
                {
                    Collection<? extends PolygonOverlay> overlays = controller.getCurrentlyActiveByAttribute("Facility");
                    for(PolygonOverlay overlay :overlays)
                    {
                        mEffectManager.removeEffect(overlay);
                    }
                }
                isActive = !isActive;


            }
        });

        ImageButton fountainBtn = (ImageButton) view.findViewById(R.id.show_water_fountain);
        fountainBtn.setOnClickListener(new View.OnClickListener() {
            private boolean isActive = false;
            @Override
            public void onClick(View v) {
                ViewModeController controller = mapsActivity.getViewModeController();
                if(!isActive)
                {
                    Collection<? extends PolygonOverlay> overlays = controller.getCurrentlyActiveByAttribute("Fountain");
                    for(PolygonOverlay overlay :overlays)
                    {
                        mEffectManager.addEffect(overlay,new MarkerEffect(googleMap,overlay,R.drawable.fountain_marker));
                    }

                }
                else
                {
                    Collection<? extends PolygonOverlay> overlays = controller.getCurrentlyActiveByAttribute("Fountain");
                    for(PolygonOverlay overlay :overlays)
                    {
                        mEffectManager.removeEffect(overlay);
                    }
                }
                isActive = !isActive;


            }
        });

        return view;
    }

}
