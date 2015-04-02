package delta.soen390.mapsters.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.Collections;
import java.util.List;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Buildings.BuildingPolygonOverlay;
import delta.soen390.mapsters.Buildings.PolygonDirectory;
import delta.soen390.mapsters.Fragments.IndoorModeFragment;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlayManager;
import delta.soen390.mapsters.IndoorDirectory.BuildingFloor;
import delta.soen390.mapsters.IndoorDirectory.RoomPolygonOverlay;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Utils.GoogleMapCamera;
import delta.soen390.mapsters.ViewMode.IndoorsViewMode;

/**
 * Created by Cat on 3/25/2015.
 */
public class ProtoSearchBox {

    private  MapsActivity mContext;
    private AutoCompleteTextView mTextView;
    private PolygonOverlayManager mPolygonManager;
    public ProtoSearchBox(final MapsActivity context){
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
                String resultKeyword = parent.getItemAtPosition(position).toString();

                //Check if result is a room number
                PolygonOverlay overlay;

                //Check if the passed result is a room number
                if((overlay = polygonDirectory.getRoomByCode(resultKeyword))!= null)
                {
                    launchIndoorViewMode((RoomPolygonOverlay)overlay);
                }
                //Check if the passed result is a building keyword
                else if((overlay = polygonDirectory.getBuildingByKeyword(resultKeyword))!= null)
                {
                    focusBuilding((BuildingPolygonOverlay)overlay);
                }

            }

        });



        Button btn = (Button) mContext.findViewById(R.id.clr_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTextView.setText("");
            }
        });




    }
    private void focusBuilding(BuildingPolygonOverlay overlay)
    {
        GoogleMapCamera camera = mContext.getGoogleMapCamera();
        camera.moveToTarget(overlay.getBuildingInfo().getCoordinates(),17);
        mContext.onMapClick(overlay.getBuildingInfo().getCoordinates());
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mTextView.getWindowToken(), 0);
    }

    private void launchIndoorViewMode(RoomPolygonOverlay overlay)
    {
        if(overlay == null)
            return;

        IndoorModeFragment indoorModeFragment = new IndoorModeFragment();
        FragmentManager fragmentManager = mContext.getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack("info")
                .replace(R.id.sliding_container, indoorModeFragment)
                .commit();

        BuildingFloor floor = overlay.getFloor();
        mContext.getViewModeController().setViewMode(new IndoorsViewMode(floor));
        overlay.focus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mTextView.getWindowToken(), 0);


    }




}
