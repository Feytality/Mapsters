package delta.soen390.mapsters.Controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

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
    private AutoCompleteTextView mTextView;

    public ProtoSearchBox(MapsActivity context){
        mContext = context;
        List<String> listingList = setAllSearch();
        Collections.sort(listingList);
        mTextView = (AutoCompleteTextView) mContext.findViewById(R.id.global_search);

        mTextView.setThreshold(1);
        ArrayAdapter adapter = new ArrayAdapter(mContext.getApplicationContext(),R.layout.my_list_item_style,listingList);
        mTextView.setAdapter(adapter);

        mTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mTextView.getText().toString().contains("Search")) {
                    mTextView.setText("");

                }
            }
        });


        mTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                String result = BuildingPolygonManager.getInstance().getBuildingInfoByKeyword(parent.getItemAtPosition(position).toString()).getCoordinates().toString();
               mContext.keywordResult(result);
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mTextView.getWindowToken(), 0);
            }

        });

//        ImageButton btn = (ImageButton) mContext.findViewById(R.id.clr_button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textView.setText("");
//            }
//        });








    }

    public List<String> setAllSearch(){
        List<String> listingList = BuildingPolygonManager.getInstance().getAllBuildings();
        listingList.addAll(BuildingPolygonManager.getInstance().getAllDepartments());
        listingList.addAll(BuildingPolygonManager.getInstance().getAllServices());
        return listingList;
    }


}
