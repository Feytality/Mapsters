package delta.soen390.mapsters.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;


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
        MapsActivity mapsActivity = (MapsActivity) getActivity();
mapsActivity.requestLockPanel();

        ImageButton btn = (ImageButton) view.findViewById(R.id.show_services_btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("*************", getClass().toString());
            }
        });


        return view;
    }

}
