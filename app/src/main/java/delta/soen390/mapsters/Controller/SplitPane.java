package delta.soen390.mapsters.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Activities.SlidingFragment;
import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.LocationService;


public class SplitPane {
    private View mContent;
    private BuildingInfo mCurrentBuilding;
    private LocationService mLocationService;
    private MapsActivity mContext;

    //View Components
    private TextView mBuildingName;
    private TextView mBuildingCode;
    private TextView mCampus;
    private TextView mBuildingServices;
    private ImageView mBuildingPictureView;
    private ArrayList<TextView> mCurrentPaneText = new ArrayList<>();


    //Directions
    private ImageButton mDirectionButton;
    private DirectionEngine mDirectionEngine;
    private DirectionEngine.DirectionPath mCurrentDirectionPath;
    private LatLng mStartingLocation;
    private TextView mTextInfo;

    public SplitPane(View slideView, float anchorPoint, LocationService locationService, MapsActivity context) {
        mContext = context;
        mContent =slideView;

//        setAnchorPoint(anchorPoint);
        mCurrentBuilding = null;
        mLocationService = locationService;

        //initializing components
        mBuildingName = (TextView) mContent.findViewById(R.id.building_name);
        mBuildingCode = (TextView) mContent.findViewById(R.id.building_code);
        mCampus = (TextView) mContent.findViewById(R.id.campus);
        mBuildingServices = (TextView) mContent.findViewById(R.id.building_services);
        mBuildingPictureView = (ImageView) mContent.findViewById(R.id.building_image);
        mBuildingPictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlidingFragment slidingFragment = new SlidingFragment();
                FragmentManager fragmentManager = mContext.getSupportFragmentManager();

                fragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.sliding_container,slidingFragment )
                        .commit();


            }
        });
        mDirectionButton = (ImageButton) mContent.findViewById(R.id.direction_button);
        mDirectionButton.setOnClickListener(directionBtnListener);
    }

    public void updateContent(BuildingInfo buildingInfo) {
        if (mCurrentBuilding == null) {
            mDirectionButton.setVisibility(View.VISIBLE);
        }

        mCurrentBuilding = buildingInfo;

        mBuildingName.setText(buildingInfo.getBuildingName());
        mBuildingCode.setText(buildingInfo.getBuildingCode());
        mCampus.setText(buildingInfo.getCampus());


        clearViews();
        // Create text views for the services and departments
        displayBuildingInfo(mCurrentBuilding.getServices(), "Services");
        displayBuildingInfo(mCurrentBuilding.getDepartments(), "Departments");

        ImageLoader img = ImageLoader.getInstance();
        img.init(ImageLoaderConfiguration.createDefault(mContext.getApplicationContext()));
        ImageLoader.getInstance().displayImage(buildingInfo.getImageUrl(), mBuildingPictureView);

    }






    private View.OnClickListener directionBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
    mContext.getDirections();

        }

    };



    private void displayBuildingInfo(ArrayList<String[]> info, String title) {
        if(info.size() > 0) {
            // Get the building pane layout so that we can add text views to it.
            LinearLayout buildingPane = (LinearLayout) mContext.findViewById(R.id.building_info);

            // Create title text view and add it to the pane
            TextView titleRow = new TextView(mContext);
            titleRow.setText(title);
            titleRow.setTextAppearance(mContext, android.R.style.TextAppearance_Small);
            titleRow.setTextColor(mContext.getResources().getColor(R.color.concordia_main_color));
            buildingPane.addView(titleRow);
            mCurrentPaneText.add(titleRow);

            TextView infoRow;

            for (final String[] infoArray : info) {
                infoRow = new TextView(mContext);

                infoRow.setText(infoArray[0]);

                // make the text view clickable and go to teh link associated with the service or department
                infoRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(infoArray[1]));
                        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(browserIntent);
                    }
                });

                infoRow.setTextAppearance(mContext, android.R.style.TextAppearance_Small);

                // add the textview to the linear layout
                buildingPane.addView(infoRow);

                // save a reference to the textview for later
                mCurrentPaneText.add(infoRow);
            }
        }
    }

    public void clearViews () {
        if(mCurrentPaneText != null && mCurrentPaneText.size() > 0) {
            for (TextView row : mCurrentPaneText) {
                row.setVisibility(View.GONE);
            }
        }
    }
}
