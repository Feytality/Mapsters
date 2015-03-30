package delta.soen390.mapsters.Fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.ViewMode.HighlightEffect;
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


        ImageButton entryBtn = (ImageButton) view.findViewById(R.id.show_entry_points);
        entryBtn.setOnClickListener(new View.OnClickListener() {
            private boolean isActive = false;
            @Override
            public void onClick(View v) {
                ViewModeController controller = mapsActivity.getViewModeController();

                if(!isActive)
                {
                    controller.activateHighlight("Entry", new HighlightEffect(255,0,0,150));

                }
                else
                {
                    controller.clearHighlightWithAttribute("Entry");
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
                    controller.activateHighlight("Facility", new HighlightEffect(255,255,0,150));
                }
                else
                {
                    controller.clearHighlightWithAttribute("Facility");
                }
                isActive = !isActive;


            }
        });


        return view;
    }

}
