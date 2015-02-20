package delta.soen390.mapsters.Controller;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.ViewComponents.FocusMapUI;


public class SplitPane {
    private SlidingUpPanelLayout mLayout;
    private BuildingInfo currentBuilding;
    private FocusMapUI focusMapUI;
    private Context mContext;

    //View Components
    private TextView buildingName;
    private TextView buildingCode;
    private TextView campus;
    private TextView buildingServices;
    private ImageView buildingPictureView;
    private ImageButton directionButton;

    public SplitPane(View view, float anchorPoint, FocusMapUI mFocusMapUI, Context context){
        mContext = context;
        mLayout = (SlidingUpPanelLayout) view;
        mLayout.setAnchorPoint(anchorPoint);
        currentBuilding = null;
        focusMapUI = mFocusMapUI;

        //initializing components
        buildingName = (TextView) mLayout.findViewById(R.id.building_name);
        buildingCode = (TextView) mLayout.findViewById(R.id.building_code);
        campus = (TextView) mLayout.findViewById(R.id.campus);
        buildingServices = (TextView) mLayout.findViewById(R.id.building_services);
        buildingPictureView = (ImageView) mLayout.findViewById(R.id.building_image);
        directionButton = (ImageButton) mLayout.findViewById(R.id.direction_button);
        directionButton.setOnClickListener(directionBtnListener);
    }

    public void updateContent(BuildingInfo buildingInfo) {
        if (currentBuilding==null) {
            directionButton.setVisibility(View.VISIBLE);
        }

        currentBuilding = buildingInfo;

        buildingName.setText(buildingInfo.getBuildingName());
        buildingCode.setText(buildingInfo.getBuildingCode());
        campus.setText(buildingInfo.getCampus());

        ImageLoader.getInstance().displayImage(buildingInfo.getImageUrl(), buildingPictureView);
    }

    private View.OnClickListener directionBtnListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Log.i("Direction Button", "Clicked!");

            //LatLng latLng = new LatLng(focusMapUI.getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(),
            //        focusMapUI.getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr="+45.50312+","+-73.56938200000002+"&daddr="+currentBuilding.getCoordinates().latitude+","+currentBuilding.getCoordinates().longitude));

            mContext.startActivity(intent);
        }

    };
}
