package delta.soen390.mapsters.Controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import java.util.Collections;
import java.util.List;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.R;

/**
 * Created by Cat on 3/25/2015.
 */
public class ProtoSearchBox {

    private  MapsActivity mContext;
    private AutoCompleteTextView textView;

    public ProtoSearchBox(MapsActivity context){
        mContext = context;
        List<String> listingList = setAllSearch();
        Collections.sort(listingList);
        textView = (AutoCompleteTextView) mContext.findViewById(R.id.global_search);
        View v =mContext.findViewById(R.id.SearchBarFragment);
        v.setVisibility(View.GONE);
        textView.setThreshold(1);
        ArrayAdapter adapter = new ArrayAdapter(mContext.getApplicationContext(),R.layout.my_list_item_style,listingList);
        textView.setAdapter(adapter);

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView.getText().toString().contains("Search")) {
                    textView.setText("");

                }
            }
        });


        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                String result = BuildingPolygonManager.getInstance().getBuildingPolygonByBuildingCode(parent.getItemAtPosition(position).toString()).getBuildingInfo().getCoordinates().toString();
                returnIntent.putExtra("result",result);
                mContext.setResult(Activity.RESULT_OK, returnIntent);
                mContext.finish();
            }

        });

        ImageButton btn = (ImageButton) mContext.findViewById(R.id.clr_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
            }
        });








    }

    public List<String> setAllSearch(){
        List<String> listingList = BuildingPolygonManager.getInstance().getAllBuildings();
        listingList.addAll(BuildingPolygonManager.getInstance().getAllDepartments());
        listingList.addAll(BuildingPolygonManager.getInstance().getAllServices());
        return listingList;
    }


}
