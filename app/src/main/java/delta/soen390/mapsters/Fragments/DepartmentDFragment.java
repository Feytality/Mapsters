package delta.soen390.mapsters.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import delta.soen390.mapsters.ListAdapter;
import delta.soen390.mapsters.R;



public class DepartmentDFragment extends Fragment {
    ListView fruitView;
    private AutoCompleteTextView actv;
    private MultiAutoCompleteTextView sBar;
    View view;
    public DepartmentDFragment() {
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

        fruitView = (ListView) view.findViewById(android.R.id.list);

        fruitView.setFastScrollEnabled(true);
        String[] fruits = getResources().getStringArray(R.array.fruits_array);

        List<String> fruitList = Arrays.asList(fruits);
        Collections.sort(fruitList);

        fruitView.setAdapter(new ListAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, fruitList));




        fruitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "" + parent.getItemAtPosition(position),
                        Toast.LENGTH_LONG).show();
            }
        });

        actv = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);
        String[] countries = getResources().
                getStringArray(R.array.fruits_array);
        ArrayAdapter adapter = new ArrayAdapter
                (getActivity().getApplicationContext(),android.R.layout.simple_dropdown_item_1line,countries);
        actv.setAdapter(adapter);







        actv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(actv.getText().toString().contains("Search")){
                    actv.setText("");

                }
            }
        });


        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), position, Toast.LENGTH_SHORT).show();
            }

        });

        Button btn = (Button) view.findViewById(R.id.clr_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actv.setText("");
            }
        });

        return view;
    }


}
