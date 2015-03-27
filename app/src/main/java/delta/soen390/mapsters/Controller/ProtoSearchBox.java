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
import delta.soen390.mapsters.Buildings.BuildingPolygonOverlay;
import delta.soen390.mapsters.Buildings.PolygonDirectory;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlayManager;
import delta.soen390.mapsters.R;

/**
 * Created by Cat on 3/25/2015.
 */
public class ProtoSearchBox {

    private  MapsActivity mContext;
    private AutoCompleteTextView mTextView;
    private PolygonOverlayManager mPolygonManager;
    public ProtoSearchBox(MapsActivity context){
        mContext = context;
        mPolygonManager = context.getPolygonOverlayManager();
        List<String> listingList = mPolygonManager.getPolygonDirectory().getAllDirectoryInfo();
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
                PolygonDirectory polygonDirectory = mPolygonManager.getPolygonDirectory();
                BuildingPolygonOverlay overlay=polygonDirectory.getBuildingByKeyword(parent.getItemAtPosition(position).toString());
                if (overlay==null)
                    return;
                String result = overlay.getBuildingInfo().getCoordinates().toString();
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



}
