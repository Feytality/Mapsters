package delta.soen390.mapsters.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.joda.time.DateTime;

import java.util.Date;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.Buildings.BuildingPolygonManager;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.Services.ShuttleBusService;


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
    private ImageButton mDirectionButton;
    private AlertDialog mAlertDialog;

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
        initDialog();
    }

    public void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Would you be interested taking the Shuttle Bus?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Dialog","YES");

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Dialog","No");
                    }
                });
        mAlertDialog = builder.create();
    }

    public void updateContent(BuildingInfo buildingInfo) {
        if (mCurrentBuilding == null) {
            mDirectionButton.setVisibility(View.VISIBLE);
        }

        mCurrentBuilding = buildingInfo;

        mBuildingName.setText(buildingInfo.getBuildingName());
        mBuildingCode.setText(buildingInfo.getBuildingCode());
        mCampus.setText(buildingInfo.getCampus());

        ImageLoader.getInstance().displayImage(buildingInfo.getImageUrl(), mBuildingPictureView);
    }

    private View.OnClickListener directionBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i("Direction Button", "Clicked!");

            if (mLocationService.getLastLocation() == null) {
                Log.i("last direction", "null");
            } else {
                Log.i("Current Coords", mLocationService.getLastLocation().getLatitude() + " " + mLocationService.getLastLocation().getLongitude());
            }

            mAlertDialog.show();

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=" + mLocationService.getLastLocation().getLatitude() + "," +
                            mLocationService.getLastLocation().getLongitude() +
                            "&daddr=" + mCurrentBuilding.getCoordinates().latitude + "," +
                            mCurrentBuilding.getCoordinates().longitude));

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mContext.startActivity(intent);
        }

    };
}