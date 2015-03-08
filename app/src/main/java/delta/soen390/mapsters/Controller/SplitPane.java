package delta.soen390.mapsters.Controller;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
        setInfoText();

        ImageLoader img = ImageLoader.getInstance();
        img.init(ImageLoaderConfiguration.createDefault(mContext.getApplicationContext()));
        ImageLoader.getInstance().displayImage(buildingInfo.getImageUrl(), mBuildingPictureView);

    }






    private View.OnClickListener directionBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
    mContext.getDirections();

        }

    };



    private void setInfoText() {
        ArrayList<String[]> departments = mCurrentBuilding.getDepartments();
        ArrayList<String[]> services = mCurrentBuilding.getServices();

        if(services.size() >= 1) {
            mTextInfo = (TextView) mContent.findViewById(R.id.service1);
            mTextInfo.setText(services.get(0)[0]);
        } else {
            return;
        }

        if(services.size() >= 2) {
            mTextInfo = (TextView) mContent.findViewById(R.id.service2);
            mTextInfo.setText(services.get(1)[0]);
        } else {
            return;
        }

        if(services.size() >= 3) {
            mTextInfo = (TextView) mContent.findViewById(R.id.service3);
            mTextInfo.setText(services.get(2)[0]);
        } else {
            return;
        }
        if(services.size() >= 4) {
            mTextInfo = (TextView) mContent.findViewById(R.id.service4);
            mTextInfo.setText(services.get(3)[0]);
        } else {
            return;
        }
        if(services.size() >= 5) {
            mTextInfo = (TextView) mContent.findViewById(R.id.service5);
            mTextInfo.setText(services.get(4)[0]);
        } else {
            return;
        }

        if(departments.size() >= 1) {
            mTextInfo = (TextView) mContent.findViewById(R.id.department1);
            mTextInfo.setText(departments.get(0)[0]);
        } else {
            return;
        }

        if(departments.size() >= 2) {
            mTextInfo = (TextView) mContent.findViewById(R.id.department2);
            mTextInfo.setText(departments.get(1)[0]);
        } else {
            return;
        }
        if(departments.size() >= 3) {
            mTextInfo = (TextView) mContent.findViewById(R.id.department3);
            mTextInfo.setText(departments.get(2)[0]);
        } else {
            return;
        }
        if(departments.size() >= 4) {
            mTextInfo = (TextView) mContent.findViewById(R.id.department4);
            mTextInfo.setText(departments.get(3)[0]);
        } else {
            return;
        }
        if(departments.size() >= 5) {
            mTextInfo = (TextView) mContent.findViewById(R.id.department5);
            mTextInfo.setText(departments.get(4)[0]);
        } else {
            return;
        }
    }

}
