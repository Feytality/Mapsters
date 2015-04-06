package delta.soen390.mapsters.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Buildings.BuildingPolygonOverlay;
import delta.soen390.mapsters.Buildings.PolygonDirectory;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Utils.ListAdapter;


public class ServiceDFragment extends Fragment {
    ListView listingView;
    private AutoCompleteTextView text;
    private PolygonDirectory mPolygonDirectory;
    private View view;
    public ServiceDFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_directories, container, false);
        listingView = (ListView) view.findViewById(android.R.id.list);
        listingView.setFastScrollEnabled(true);


        mPolygonDirectory = MapsActivity.sPolygonDirectory;
        ArrayList<String> listingList = mPolygonDirectory.getAllServices();

        Collections.sort(listingList);

        listingView.setAdapter(new ListAdapter(getActivity().getApplicationContext(),
                R.layout.my_list_item_style, listingList));






        listingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {
                Intent returnIntent = new Intent();
                Log.e("************", parent.getItemAtPosition(position).toString());
                BuildingPolygonOverlay overlay = mPolygonDirectory.getBuildingByService(parent.getItemAtPosition(position).toString());

                String result = overlay.getBuildingInfo().getBuildingCode();
                returnIntent.putExtra("result",result);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
                hideKeyboard();

            }
        });

        text = (AutoCompleteTextView) view.findViewById(R.id.directory_search);
        text.setThreshold(1);

        ArrayAdapter adapter = new ArrayAdapter
                (getActivity().getApplicationContext(),R.layout.my_list_item_style,listingList);
        text.setAdapter(adapter);

        text.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    Intent returnIntent = new Intent();

                    BuildingPolygonOverlay overlay = mPolygonDirectory.getBuildingByService(text.getText().toString());
                    if (overlay==null){
                        return false;}
                    String result = overlay.getBuildingInfo().getBuildingCode();
                    returnIntent.putExtra("result",result);
                    getActivity().setResult(Activity.RESULT_OK, returnIntent);
                    getActivity().finish();
hideKeyboard();
                }
                return false;
            }
        });





        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (text.getText().toString().contains("Search")) {
                    text.setText("");

                }
            }
        });


        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                BuildingPolygonOverlay overlay = mPolygonDirectory.getBuildingByService(parent.getItemAtPosition(position).toString());
                String result = overlay.getBuildingInfo().getBuildingCode();
                returnIntent.putExtra("result",result);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
                hideKeyboard();

            }

        });

        Button btn = (Button) view.findViewById(R.id.clr_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("");
            }
        });

        return view;
    }


    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
    }
}
