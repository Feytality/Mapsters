package delta.soen390.mapsters.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.maps.model.TravelMode;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashSet;
import java.util.Set;

import delta.soen390.mapsters.Activities.DirectionStepsFragment;
import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;


public class DirOptionFragment extends Fragment {


    private FragmentTabHost mTabHost;
    private GetSteps git;
    private SlidingUpPanelLayout panelLayout;

    public DirOptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> defaults = prefs.getStringSet("transit_views", new HashSet<String>());
        final String isCycling = getString(R.string.is_cycling);
        final   String isWalking = getString(R.string.is_walking);
        final  String isDriving = getString(R.string.is_driving);
        final   String isShuttle = getString(R.string.is_shuttle);
        final   String isTransit = getString(R.string.is_Transit);
        mTabHost = new FragmentTabHost(getActivity());

        View view = inflater.inflate(R.layout.fragment_dir_option, mTabHost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        //Always here
        mTabHost.addTab(mTabHost.newTabSpec(isTransit).setIndicator("",getResources().getDrawable(R.drawable.bus)), DirectionStepsFragment.class, null);

        if (defaults.contains(isShuttle)){
            mTabHost.addTab(mTabHost.newTabSpec(isShuttle).setIndicator("",getResources().getDrawable(R.drawable.van)), DirectionStepsFragment.class, null);
        } if (defaults.contains(isDriving)){
            mTabHost.addTab(mTabHost.newTabSpec(isDriving).setIndicator("",getResources().getDrawable(R.drawable.car)), DirectionStepsFragment.class, null);
        } if (defaults.contains(isWalking)){
            mTabHost.addTab(mTabHost.newTabSpec(isWalking).setIndicator("",getResources().getDrawable(R.drawable.walking)), DirectionStepsFragment.class, null);
        } if (defaults.contains(isCycling)) {
            mTabHost.addTab(mTabHost.newTabSpec(isCycling).setIndicator("",getResources().getDrawable(R.drawable.cycling)), DirectionStepsFragment.class, null);
        }




            //Listerner BAY - refactor

            mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    Toast.makeText(getActivity().getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();
                    if (tabId.equals(isShuttle)) {
                        MapsActivity m = (MapsActivity) getActivity();
                        m.getDirections(TravelMode.TRANSIT);
                    }
                    if (tabId.equals(isCycling)) {
                        MapsActivity m = (MapsActivity) getActivity();
                        m.getDirections(TravelMode.BICYCLING);
                    }
                    if (tabId.equals(isDriving)) {
                        MapsActivity m = (MapsActivity) getActivity();
                        m.getDirections(TravelMode.DRIVING);
                    }
                    if (tabId.equals(isTransit)) {
                        MapsActivity m = (MapsActivity) getActivity();
                        m.getDirections(TravelMode.TRANSIT);
                    }
                    if (tabId.equals(isWalking)) {
                        MapsActivity m = (MapsActivity) getActivity();
                        m.getDirections(TravelMode.WALKING);
                    }

                }

            });


                return mTabHost;
    }



}
