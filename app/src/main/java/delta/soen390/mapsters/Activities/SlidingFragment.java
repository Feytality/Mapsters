package delta.soen390.mapsters.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.R;



public class SlidingFragment extends Fragment{
    private SplitPane splitPane;
    private SlidingUpPanelLayout panelLayout;
    private View view;



    public interface OnDataPass {
        public void onDataPass(SplitPane data);
    }

    OnDataPass dataPasser;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;



    }



    public SlidingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_sliding, container, false);
        MapsActivity mapsActivity = (MapsActivity)dataPasser;
        panelLayout =(SlidingUpPanelLayout) mapsActivity.findViewById(R.id.sliding_layout);
        view.setFocusableInTouchMode(true);

        splitPane = new SplitPane(view, 0.50f, mapsActivity.getLocationService(), mapsActivity);
        passData(splitPane);
        return view;
    }

    public void passData(SplitPane data) {
        dataPasser.onDataPass(data);

    }



}
