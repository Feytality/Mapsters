package delta.soen390.mapsters.Controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;


import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.Fragments.DirOptionFragment;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.DirectionEngine;
import delta.soen390.mapsters.Services.LocationService;
import delta.soen390.mapsters.Utils.GoogleMapstersUtils;
import delta.soen390.mapsters.ViewMode.IndoorsViewMode;
import delta.soen390.mapsters.ViewMode.ViewModeController;


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
    private String mDestinationUrl;
    private String mDefaultUrl = "http://www.concordia.ca/";
    private ImageView mBike;


    //Directions UI
    private ImageButton mDirectionButton;
    private TextView mTextInfo;
    private ImageView mParking;
    private ImageView mInfo;
    private ImageView mAccess;
    private TextView mBuildingAddress;

    private View mIndoorsDirectoryButton;
    public SplitPane(View slideView, float anchorPoint, LocationService locationService, MapsActivity context) {
        mContext = context;
        mContent =slideView;

//        setAnchorPoint(anchorPoint);
        mCurrentBuilding = null;
        mLocationService = locationService;

        //initializing components
        initializeBuildingInformationDisplay();
        initializeDirectionButton();
        initializeIndoorsDirectoryButton();

    }

    private void initializeDirectionButton()
    {
        mDirectionButton = (ImageButton) mContent.findViewById(R.id.direction_button);
        if(mDirectionButton == null)
        {
            return;
        }
        mDirectionButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        DirectionEngine directionEngine = mContext.getDirectionEngine();

                        //Engine has not been set up, no directions available
                        if(directionEngine == null) {
                            return;
                        }
                        //Update the direction engine with all of the requested direction type
                        //from the settings
                        //Get the currently clicked overlay
                        directionEngine.setFinalLocation(GoogleMapstersUtils.toDirectionsLatLng(mCurrentBuilding.getCoordinates()));
                        directionEngine.updateDirectionEngine();

                        //DirOptionFragment is the view component of the direction pane
                        DirOptionFragment dirOptionFragment = new DirOptionFragment();

                        FragmentManager fragmentManager = mContext.getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack("info")
                                .replace(R.id.sliding_container, dirOptionFragment)
                                .commit();
                    }}
        );
    }

    private void initializeBuildingInformationDisplay()
    {
        mBuildingName = (TextView) mContent.findViewById(R.id.building_name);
        mBuildingCode = (TextView) mContent.findViewById(R.id.building_code);
        mCampus = (TextView) mContent.findViewById(R.id.campus);
        mBuildingServices = (TextView) mContent.findViewById(R.id.building_services);
        mBuildingPictureView = (ImageView) mContent.findViewById(R.id.building_image);
        mDirectionButton = (ImageButton) mContent.findViewById(R.id.direction_button);
    }

    private void initializeIndoorsDirectoryButton()
    {
        mIndoorsDirectoryButton =  mContent.findViewById(R.id.indoors_btn);

        if(mIndoorsDirectoryButton == null) {
            return;
        }

        mIndoorsDirectoryButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        //Get the currently focused building

                       //When clicked, notify a new indoorsview and notifiy the ViewModeController
                        if(mCurrentBuilding == null)
                        {
                            return;
                        }
                        //set the view to indoors!
                        mContext.getViewModeController().setViewMode(new IndoorsViewMode(mCurrentBuilding.getDefaultFloor()));
                        mContext.requestLowerPanel();
                    }}
        );
    }

    public void updateContent(BuildingInfo buildingInfo) {
        // Creates animation for split pane.
        createTranslateAnimation();

        if (mDirectionButton != null) {
            mDirectionButton.setVisibility(View.VISIBLE);
        }

        mDirectionButton.setVisibility(View.VISIBLE);
        SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) mContext.findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setTouchEnabled(true);

        //reconnect with views, if lost when swapping fragments
        mBuildingName = (TextView) mContext.findViewById(R.id.building_name);

        if(mBuildingName != null) {
            // Populate textual building information
            populateBuildingInformation(buildingInfo);
            // Populate iconic building information
            populateBuildingIcons(buildingInfo);

            ImageLoader img = ImageLoader.getInstance();
            img.init(ImageLoaderConfiguration.createDefault(mContext.getApplicationContext()));
            ImageLoader.getInstance().displayImage(buildingInfo.getImageUrl(), mBuildingPictureView);
        }
    }

    private int getDisplayHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


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
            mDestinationUrl = "";
            for (final String[] infoArray : info) {
                String destUrl = "";
                infoRow = new TextView(mContext);

                if(URLUtil.isValidUrl(infoArray[1])) {
                    mDestinationUrl = infoArray[1];
                } else {
                    // correct the url
                    if(!mDestinationUrl.equals("")) {
                        destUrl = mDestinationUrl.concat(infoArray[1].substring(1));
                    } else if(!infoArray[1].equals("")) {
                        destUrl = mDefaultUrl.concat(infoArray[1].substring(1));
                    } else {
                        destUrl = mDefaultUrl;
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    infoRow.setMovementMethod(LinkMovementMethod.getInstance());
                    infoRow.setText(Html.fromHtml("<a href=\"" + destUrl + "\">" + infoArray[0] + "</a>"));

                } else {
                    infoRow.setText(infoArray[0]);
                    // make the text view clickable and go to teh link associated with the service or department
                    infoRow.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            String url = "";
                            if(!URLUtil.isValidUrl(infoArray[1])) {
                                // correct the url
                                if(!mDestinationUrl.equals("")) {
                                    url = mDestinationUrl.concat(infoArray[1].substring(1));
                                } else if (!infoArray[1].equals("")) {
                                    url = mDefaultUrl.concat(infoArray[1].substring(1));
                                } else {
                                    url = mDefaultUrl;
                                }
                            } else {
                                url = infoArray[1];
                            }
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(browserIntent);
                        }
                    });
                }

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

    private void populateBuildingInformation(BuildingInfo buildingInfo) {
        mBuildingCode = (TextView) mContext.findViewById(R.id.building_code);
        mCampus = (TextView) mContext.findViewById(R.id.campus);
        mBuildingServices = (TextView) mContext.findViewById(R.id.building_services);
        mBuildingPictureView = (ImageView) mContext.findViewById(R.id.building_image);
        mBuildingAddress = (TextView) mContext.findViewById(R.id.address_txt);

        //set em
        mCurrentBuilding = buildingInfo;
        mBuildingName.setText(buildingInfo.getBuildingName());
        mBuildingCode.setText(buildingInfo.getBuildingCode());
        mCampus.setText(buildingInfo.getCampus().toString());
        mBuildingAddress.setText(buildingInfo.getAddress());

        clearViews();

        // Create text views for the services and departments
        displayBuildingInfo(mCurrentBuilding.getServices(), "Services");
        displayBuildingInfo(mCurrentBuilding.getDepartments(), "Departments");
    }

    private void populateBuildingIcons(BuildingInfo buildingInfo) {
        mParking = (ImageView) mContext.findViewById(R.id.parking_img);
        mInfo = (ImageView) mContext.findViewById(R.id.info_img);
        mAccess = (ImageView) mContext.findViewById(R.id.accessibility_img);
        mBike = (ImageView) mContext.findViewById(R.id.bikerack_img);

        if (!buildingInfo.hasParking() && mParking != null)
            mParking.setVisibility(View.GONE);
        else
            mParking.setVisibility(View.VISIBLE);

        if (!buildingInfo.hasInfo() && mInfo != null)
            mInfo.setVisibility(View.GONE);
        else
            mInfo.setVisibility(View.VISIBLE);

        if (!buildingInfo.hasAccessibility() && mAccess != null)
            mAccess.setVisibility(View.GONE);
        else
            mAccess.setVisibility(View.VISIBLE);

        if (!buildingInfo.hasBikeRack() && mBike != null)
            mBike.setVisibility(View.GONE);
        else
            mBike.setVisibility(View.VISIBLE);
    }

    private void createTranslateAnimation() {
        mContext.findViewById(R.id.sliding_container).clearAnimation();
        TranslateAnimation translation;
        translation = new TranslateAnimation(0f, 0F, 50f, 0f);
        translation.setStartOffset(0);
        translation.setDuration(500);
        translation.setFillAfter(true);
        translation.setInterpolator(new BounceInterpolator());
        mContext.findViewById(R.id.sliding_container).startAnimation(translation);
    }

}
