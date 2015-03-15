package delta.soen390.mapsters.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import delta.soen390.mapsters.R;


public class DirOptionFragment extends Fragment {


    private FragmentTabHost tabHost;

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

        tabHost = new FragmentTabHost(getActivity());
        inflater.inflate(R.layout.fragment_dir_option, tabHost);
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(tabHost.newTabSpec("simple").setIndicator("Simple"), GetSteps.class, null);
        tabHost.addTab(tabHost.newTabSpec("contacts").setIndicator("Contacts"), DirOptionFragment.class, null);


        return tabHost;
    }



}
