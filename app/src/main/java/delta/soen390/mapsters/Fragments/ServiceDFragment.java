package delta.soen390.mapsters.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.ListAdapter;
import delta.soen390.mapsters.R;


public class ServiceDFragment extends Fragment {
    ListView listingView;
    private AutoCompleteTextView text;
    View view;
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


        List<String> listingList = BuildingPolygonManager.getInstance().getAllServices();
        Collections.sort(listingList);

        listingView.setAdapter(new ListAdapter(getActivity().getApplicationContext(),
                R.layout.my_list_item_style, listingList));




        listingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {
                Intent returnIntent = new Intent();
                String result = BuildingPolygonManager.getInstance().getBuildingInfoByService(parent.getItemAtPosition(position).toString()).getCoordinates().toString();
                returnIntent.putExtra("result",result);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });

        text = (AutoCompleteTextView) view.findViewById(R.id.directory_search);
        text.setThreshold(1);

        ArrayAdapter adapter = new ArrayAdapter
                (getActivity().getApplicationContext(),R.layout.my_list_item_style,listingList);
        text.setAdapter(adapter);







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
                String result = BuildingPolygonManager.getInstance().getBuildingInfoByService(parent.getItemAtPosition(position).toString()).getCoordinates().toString();
                returnIntent.putExtra("result",result);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
            }

        });

        ImageButton btn = (ImageButton) view.findViewById(R.id.clr_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("");
            }
        });

        return view;
    }

}
