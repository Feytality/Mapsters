package delta.soen390.mapsters.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import delta.soen390.mapsters.R;


public class GetSteps extends Fragment {


    private ViewGroup myLinearLayout;
    private TabHost t;
    private TextView rowTextView;

    public GetSteps() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_get_steps, container, false);
    }


    public void createSteps(View view){

        myLinearLayout =(ViewGroup) view.findViewById(R.id.steps_layout);
         t = (TabHost)view;


        if(t.getCurrentTabTag().contains("shu")){
            rowTextView = new TextView(myLinearLayout.getContext());
            rowTextView.setText("bacon");
            myLinearLayout.addView(rowTextView);
            rowTextView = new TextView(myLinearLayout.getContext());
            rowTextView.setText("bacon");
            myLinearLayout.addView(rowTextView);
        }else{
            rowTextView = new TextView(myLinearLayout.getContext());
            rowTextView.setText("bacon");
            myLinearLayout.addView(rowTextView);
            rowTextView = new TextView(myLinearLayout.getContext());
            rowTextView.setText("bacon");
            myLinearLayout.addView(rowTextView);
            rowTextView = new TextView(myLinearLayout.getContext());
            rowTextView.setText("bacon");
            myLinearLayout.addView(rowTextView);
            rowTextView = new TextView(myLinearLayout.getContext());
            rowTextView.setText("bacon");
            myLinearLayout.addView(rowTextView);
        }


    }

    public void yay(){
        Log.e("","")
;
    }

}
