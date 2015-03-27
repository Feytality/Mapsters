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

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import delta.soen390.mapsters.Activities.DirectionStepsFragment;
import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;


public class DirOptionFragment extends Fragment {


    private FragmentTabHost mTabHost;
    private SlidingUpPanelLayout panelLayout;
    private ArrayList<TabHost.TabSpec> mTabSpecs = new ArrayList<>();
    private DirectionEngine mDirectionEngine;
    public DirOptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void addTab(String tabId, DirectionEngine.DirectionType directionType)
    {
        //MapsActivity activity = (MapsActivity)getActivity();
        Bundle args = new Bundle();
        args.putInt("DirectionType", directionType.ordinal());
        mTabHost.addTab(mTabHost.newTabSpec(tabId).setIndicator(tabId), DirectionStepsFragment.class, args);
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
        addTab(isTransit, DirectionEngine.DirectionType.TRANSIT);

        MapsActivity activity = ((MapsActivity)getActivity());
        mDirectionEngine = activity.getDirectionEngine();

        //Transit is the default value which is always present, hence show the transit path
        mDirectionEngine.showDirectionPath(DirectionEngine.DirectionType.TRANSIT);

        //update the tab
        if (defaults.contains(isShuttle)){
            addTab(isShuttle, DirectionEngine.DirectionType.SHUTTLE);
        } if (defaults.contains(isDriving)){
            addTab(isDriving, DirectionEngine.DirectionType.DRIVING);
        } if (defaults.contains(isWalking)){
            addTab(isWalking, DirectionEngine.DirectionType.WALKING);
        } if (defaults.contains(isCycling)) {
            addTab(isCycling, DirectionEngine.DirectionType.BICYCLE);
        }

            //Listerner BAY - refactor

            mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {

                    Toast.makeText(getActivity().getApplicationContext(),tabId,Toast.LENGTH_SHORT).show();

                    if(mDirectionEngine == null) {
                        MapsActivity activity = ((MapsActivity)getActivity());
                        mDirectionEngine = activity.getDirectionEngine();
                        if(mDirectionEngine == null) {
                            return;
                        }
                    }

                    DirectionEngine.DirectionType directionType = DirectionEngine.DirectionType.TRANSIT;
                    if(tabId.equals(isCycling))
                    {
                        directionType = DirectionEngine.DirectionType.BICYCLE;
                    }
                    else if(tabId.equals(isTransit))
                    {
                        directionType = DirectionEngine.DirectionType.TRANSIT;
                    }
                    else if(tabId.equals(isShuttle))
                    {
                        directionType = DirectionEngine.DirectionType.SHUTTLE;
                    }
                    else if(tabId.equals(isWalking))
                    {
                        directionType = DirectionEngine.DirectionType.WALKING;
                    }
                    else if(tabId.equals(isDriving))
                    {
                        directionType = DirectionEngine.DirectionType.DRIVING;
                    }

                    mDirectionEngine.showDirectionPath(directionType);


                }

            });
                return mTabHost;
    }



}
