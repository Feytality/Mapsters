package delta.soen390.mapsters.Controller;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.Utils.GoogleMapstersUtils;


public class SplitPane {
    private SlidingUpPanelLayout mLayout;
    private BuildingInfo mCurrentBuilding;
    private LocationService mLocationService;
    private Context mContext;

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

    public SplitPane(View view, float anchorPoint, LocationService locationService, Context context) {
        mContext = context;
        mLayout = (SlidingUpPanelLayout) view;
        mLayout.setAnchorPoint(anchorPoint);
        mCurrentBuilding = null;
        mLocationService = locationService;

        //initializing components
        mBuildingName = (TextView) mLayout.findViewById(R.id.building_name);
        mBuildingCode = (TextView) mLayout.findViewById(R.id.building_code);
        mCampus = (TextView) mLayout.findViewById(R.id.campus);
        mBuildingServices = (TextView) mLayout.findViewById(R.id.building_services);
        mBuildingPictureView = (ImageView) mLayout.findViewById(R.id.building_image);
        mDirectionButton = (ImageButton) mLayout.findViewById(R.id.direction_button);
        mDirectionButton.setOnClickListener(directionBtnListener);
    }

    public SplitPane(View view, float anchorPoint, LocationService locationService, Activity activity) {
        mContext = activity;
        mLayout =(SlidingUpPanelLayout) view;
        mLayout.setAnchorPoint(anchorPoint);
        mCurrentBuilding = null;
        mLocationService = locationService;

        //initializing components
        mBuildingName = (TextView) mLayout.findViewById(R.id.building_name);
        mBuildingCode = (TextView) mLayout.findViewById(R.id.building_code);
        mCampus = (TextView) mLayout.findViewById(R.id.campus);
        mBuildingServices = (TextView) mLayout.findViewById(R.id.building_services);
        mBuildingPictureView = (ImageView) mLayout.findViewById(R.id.building_image);
        mDirectionButton = (ImageButton) mLayout.findViewById(R.id.direction_button);
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

    public void setDirectionEngine(DirectionEngine directionEngine)
    {
        mDirectionEngine = directionEngine;
    }

    private View.OnClickListener directionBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i("Direction Button", "Clicked!");
            Location lastLocation = mLocationService.getLastLocation();
            if (lastLocation == null) {
                Log.i("last direction", "null");
                return;
            } else {
                Log.i("Current Coords", mLocationService.getLastLocation().getLatitude() + " " + mLocationService.getLastLocation().getLongitude());
            }

            //TODO toast notify user of connectivity problem
            if(mDirectionEngine == null) {
                return;
            }


            LatLng currentBuildingCoordinates = mCurrentBuilding.getCoordinates();
            if(currentBuildingCoordinates == null)
                return;

            if(mCurrentDirectionPath != null){
                mCurrentDirectionPath.hideDirectionPath();
            }

            if(mStartingLocation == null) {
                mCurrentDirectionPath = mDirectionEngine.GenerateDirectionPath(
                        new com.google.maps.model.LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()),
                        GoogleMapstersUtils.toDirectionsLatLng(currentBuildingCoordinates));
            } else {
                // Else, starting location is set (by placing marker on map), use the choosen location coordinates instead.
                mCurrentDirectionPath = mDirectionEngine.GenerateDirectionPath(
                        GoogleMapstersUtils.toDirectionsLatLng(mStartingLocation),
                        GoogleMapstersUtils.toDirectionsLatLng(currentBuildingCoordinates));
            }

            mCurrentDirectionPath.showDirectionPath();

        }

    };

    public void setStartingLocation(LatLng startingLocation) {
        // Note: should be able to set it null to clear it
        mStartingLocation = startingLocation;
        if(startingLocation != null)
            Log.i("Set starting location!", startingLocation.toString());

    }

    private void displayBuildingInfo(ArrayList<String[]> info, String title) {
        if(info.size() > 0) {
            // Get the building pane layout so that we can add text views to it.
            LinearLayout buildingPane = (LinearLayout) mLayout.findViewById(R.id.building_info);

            // Create title text view and add it to the pane
            TextView titleRow = new TextView(mContext);
            titleRow.setText(title);
            titleRow.setTextAppearance(mContext, android.R.style.TextAppearance_Small);
            titleRow.setTextColor(mContext.getResources().getColor(R.color.concordia_main_color));
            buildingPane.addView(titleRow);
            mCurrentPaneText.add(titleRow);

            TextView infoRow;

            for (String[] infoArray : info) {
                infoRow = new TextView(mContext);

                infoRow.setText(infoArray[0]);
                infoRow.setTextAppearance(mContext, android.R.style.TextAppearance_Small);
                //infoRow.setTextColor(mContext.getResources().getColor(R.color.concordia_main_color));
                // newRow.setId();

                // add the textview to the linearlayou
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
