package delta.soen390.mapsters.Controller;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
        mCampus.setText(buildingInfo.getCampus().toString());
        setInfoText();

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

    private void setInfoText() {
        ArrayList<String[]> departments = mCurrentBuilding.getDepartments();
        ArrayList<String[]> services = mCurrentBuilding.getServices();

        if(services.size() >= 1) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.service1);
            mTextInfo.setText(services.get(0)[0]);
        } else {
            return;
        }

        if(services.size() >= 2) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.service2);
            mTextInfo.setText(services.get(1)[0]);
        } else {
            return;
        }

        if(services.size() >= 3) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.service3);
            mTextInfo.setText(services.get(2)[0]);
        } else {
            return;
        }
        if(services.size() >= 4) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.service4);
            mTextInfo.setText(services.get(3)[0]);
        } else {
            return;
        }
        if(services.size() >= 5) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.service5);
            mTextInfo.setText(services.get(4)[0]);
        } else {
            return;
        }

        if(departments.size() >= 1) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.department1);
            mTextInfo.setText(departments.get(0)[0]);
        } else {
            return;
        }

        if(departments.size() >= 2) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.department2);
            mTextInfo.setText(departments.get(1)[0]);
        } else {
            return;
        }
        if(departments.size() >= 3) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.department3);
            mTextInfo.setText(departments.get(2)[0]);
        } else {
            return;
        }
        if(departments.size() >= 4) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.department4);
            mTextInfo.setText(departments.get(3)[0]);
        } else {
            return;
        }
        if(departments.size() >= 5) {
            mTextInfo = (TextView) mLayout.findViewById(R.id.department5);
            mTextInfo.setText(departments.get(4)[0]);
        } else {
            return;
        }
    }

}
