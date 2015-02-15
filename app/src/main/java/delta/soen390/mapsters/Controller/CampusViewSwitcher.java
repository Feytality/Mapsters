package delta.soen390.mapsters.Controller;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 2/11/2015.
 */
public class CampusViewSwitcher {
    private CampusView mLoyolaCampusView;
    private CampusView mSgwCampusView;

    private ViewSwitcher mMapSwitcher;

    private MapsActivity mActivity;
    private GoogleMap  mMap;

    private boolean mIsLoyolaDisplayed = true;

    public CampusViewSwitcher(MapsActivity activity, GoogleMap map)
    {
        mActivity = activity;
        mMap = map;
        mMapSwitcher = (ViewSwitcher) mActivity.findViewById(R.id.mapSwitcher);
		
        LoadAnimation();
        LoadCampusViews();
    }

    public void SwitchView()
    {
        if(mIsLoyolaDisplayed) {
            mSgwCampusView.LoadView(mMap);
		} else {
            mLoyolaCampusView.LoadView(mMap);
		}
        mIsLoyolaDisplayed = !mIsLoyolaDisplayed;
			
        //Triggers the mapswitcher animation
        mMapSwitcher.showNext();
    }

    public boolean IsLoyolaDisplayed()
    {
        return mIsLoyolaDisplayed;
    }

    public boolean IsSgwDisplayed()
    {
        return !mIsLoyolaDisplayed;
    }
    private void LoadAnimation()
    {
        Animation slideInLeft = AnimationUtils.loadAnimation(mActivity,
                android.R.anim.slide_in_left);
        Animation slideOutRight = AnimationUtils.loadAnimation(mActivity,
                android.R.anim.slide_out_right);

        mMapSwitcher.setInAnimation(slideInLeft);
        mMapSwitcher.setOutAnimation(slideOutRight);
    }

    //TODO put hardcoded values in some .xml file
    private void LoadCampusViews()
    {
        float zoomLevel = 17;

        //Loyola Campus
        //<!-- 45.458565,-73.640064 LOY -->
        mLoyolaCampusView = new CampusView(new LatLng(45.458568, -73.640064), zoomLevel);

        //SGW Campus
        //45.497174,-73.578835,17z
        mSgwCampusView = new CampusView(new LatLng(45.497174, -73.578835), zoomLevel);

        //Set default starting view to Loyola
        mLoyolaCampusView.LoadView(mMap);
        mIsLoyolaDisplayed = true;
    }
	
	//The CampusView class holds the information of the different google map
    //views used.
    private class CampusView
    {
        LatLng mCoordinate;
        float mZoomLevel;

        public CampusView(LatLng coordinate, float zoomLevel)
        {
            mZoomLevel = zoomLevel;
            mCoordinate = coordinate;
        }

        public float GetZoomLevel()
        {
            return mZoomLevel;
        }

        public LatLng GetCoordinate()
        {
            return mCoordinate;
        }

		//If ever stakeholder wants to save the view, we'll have it
        public void SaveView(GoogleMap map)
        {
            mCoordinate = mMap.getCameraPosition().target;
            mZoomLevel = mMap.getCameraPosition().zoom;
        }
		
        public void LoadView(GoogleMap map)
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mCoordinate, mZoomLevel));
        }
    }
	
}
