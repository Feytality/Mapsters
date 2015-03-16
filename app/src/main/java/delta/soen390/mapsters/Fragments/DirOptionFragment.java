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
import delta.soen390.mapsters.R;


public class DirOptionFragment extends Fragment {


    private FragmentTabHost tabHost;
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
         String isCycling = getString(R.string.is_cycling);
         String isWalking = getString(R.string.is_walking);
         String isDriving = getString(R.string.is_driving);
         String isShuttle = getString(R.string.is_shuttle);
        tabHost = new FragmentTabHost(getActivity());

        View view = inflater.inflate(R.layout.fragment_dir_option, tabHost);
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        //Always here
        tabHost.addTab(tabHost.newTabSpec("STM").setIndicator("STM"), GetSteps.class, null);

        if (defaults.contains(isShuttle)){
            tabHost.addTab(tabHost.newTabSpec(isShuttle).setIndicator(isShuttle), DirectionStepsFragment.class, null);
        } if (defaults.contains(isDriving)){
            tabHost.addTab(tabHost.newTabSpec(isDriving).setIndicator(isDriving), GetSteps.class, null);
        } if (defaults.contains(isWalking)){
            tabHost.addTab(tabHost.newTabSpec(isWalking).setIndicator(isWalking), DirectionStepsFragment.class, null);
        } if (defaults.contains(isCycling)){
            tabHost.addTab(tabHost.newTabSpec(isCycling).setIndicator(isCycling), GetSteps.class, null);


            //Listerner BAY - refactor

            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
            Toast.makeText(getActivity().getApplicationContext(), TravelMode.BICYCLING.toString(),Toast.LENGTH_SHORT).show();
                    git = new GetSteps();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.steps_layout, git)
                            .commit();
                    git.createSteps(tabHost);

                }
            });


        }        return tabHost;
    }



}
