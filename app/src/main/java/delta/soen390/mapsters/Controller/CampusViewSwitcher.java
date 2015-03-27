package delta.soen390.mapsters.Controller;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Utils.GoogleMapCamera;
import delta.soen390.mapsters.ViewComponents.CampusSwitchUI;

/**
 * Created by Mathieu on 2/11/2015.
 */
public class CampusViewSwitcher {
    private CampusView mLoyolaCampusView;
    private CampusView mSgwCampusView;

    private ViewSwitcher mMapSwitcher;

    private MapsActivity mActivity;
    private GoogleMapCamera mCamera;
    private GoogleMap  mMap;
    private CampusSwitchUI mSwitchUI;

    private boolean mIsLoyolaDisplayed = true;

    public CampusViewSwitcher(MapsActivity activity, GoogleMap map, CampusSwitchUI switchUI)
    {
        mSwitchUI = switchUI;
        mActivity = activity;
        mCamera = mActivity.getGoogleMapCamera();
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
        //TODO Cat:This maps to a setting
        //Set default starting view to Loyola
        mLoyolaCampusView.LoadView(mMap);
        mIsLoyolaDisplayed = true;
    }

    public LatLng parseCoordinate(String coor){

        double lat =Double.valueOf(coor.substring(coor.indexOf("(")+1,coor.indexOf(",")));
        double lng = Double.valueOf(coor.substring(coor.indexOf(",")+1,coor.indexOf(")")));
        LatLng ll= new LatLng(lat,lng);
        return ll;
    }


    public void zoomToLatLong(int zoomLevel,BuildingInfo info){
        Log.i("","This is the current campus"+mSwitchUI.getCurrentCampus().toString());
        if (!info.getCampus().equals(mSwitchUI.getCurrentCampus())){
            mSwitchUI.toggleCampusSwitch();
        }
        zoomToLatLong(zoomLevel, info.getCoordinates());
    }

    public void zoomToLatLong(int zoomLevel, LatLng latLng){
        mCamera.moveToTarget(latLng,zoomLevel);
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
