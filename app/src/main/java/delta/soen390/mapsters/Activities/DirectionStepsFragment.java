package delta.soen390.mapsters.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import delta.soen390.mapsters.Controller.DirectionStep;
import delta.soen390.mapsters.Controller.SplitPane;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Utils.DirectionsStepAdapter;


public class DirectionStepsFragment extends Fragment {

    public DirectionStepsFragment() {
        // Required empty public constructor
    }

    private OnDataPass dataPasser;
    public interface OnDataPass {
        public void onDataPass(SplitPane data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private List createSteps(int size) {
        List result = new ArrayList();
        for (int i=1; i <= size; i++) {
            ArrayList<String> dummy = new ArrayList<>();
            dummy.add("1. Test direction");
            DirectionStep ds = new DirectionStep();
            ds.setStep(ds.getStepPrefix() + i);
            ds.setSteps(dummy);

            result.add(ds);
        }

        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MapsActivity mapsActivity = (MapsActivity) dataPasser;
        RecyclerView recList = (RecyclerView) mapsActivity.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mapsActivity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        DirectionsStepAdapter ca = new DirectionsStepAdapter(createSteps(30));
        recList.setAdapter(ca);
        return inflater.inflate(R.layout.fragment_direction_steps, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataPasser = (OnDataPass) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void passData(SplitPane data) {
        dataPasser.onDataPass(data);

    }

}
