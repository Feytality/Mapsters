package delta.soen390.mapsters.Activities;

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
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.TravelResponseInfo;
import delta.soen390.mapsters.Utils.DirectionsStepAdapter;


public class DirectionStepsFragment extends Fragment {

    private DirectionEngine.DirectionType mDirectionType;
    public DirectionStepsFragment() {
        // Required empty public constructor
    }
    int d;
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Bundle args = getArguments();
       int directionTypeOrdinalValue =  args.getInt("DirectionType");
       mDirectionType = DirectionEngine.DirectionType.values()[directionTypeOrdinalValue];
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_direction_steps, container, false);
        MapsActivity mapsActivity = (MapsActivity) getActivity();
        RecyclerView recList = (RecyclerView) view.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mapsActivity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        DirectionEngine directionEngine = mapsActivity.getDirectionEngine();

        ArrayList<TravelResponseInfo.TravelStep> travelSteps = directionEngine.getTravelSteps(mDirectionType);
        if(travelSteps == null)
        {
            travelSteps = new ArrayList<>();
        }
        DirectionsStepAdapter ca = new DirectionsStepAdapter(createTravelSteps(travelSteps),mapsActivity);
        recList.setAdapter(ca);
        return view;
    }

    private List createTravelSteps(ArrayList<TravelResponseInfo.TravelStep> steps) {
        List result = new ArrayList();
        for (int i = 1; i < steps.size(); i++) {
            ArrayList<String> stepName = new ArrayList<>();
            stepName.add(steps.get(i).getDescription());
            DirectionStep ds = new DirectionStep();
            ds.setStep(ds.getStepPrefix() + i);
            ds.setSteps(stepName);
            result.add(ds);
        }

        return result;
    }







}
