package delta.soen390.mapsters.Controller;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.R;


public class SplitPane {
    private SlidingUpPanelLayout mLayout;
    private BuildingInfo currentBuilding;

    //View Components
    private TextView buildingName;
    private TextView buildingCode;
    private TextView campus;
    private TextView buildingServices;
    private ImageView buildingPicture;

    public SplitPane(View view, float anchorPoint){
        mLayout = (SlidingUpPanelLayout) view;
        mLayout.setAnchorPoint(anchorPoint);
        currentBuilding = null;

        //initializing components
        buildingName = (TextView) mLayout.findViewById(R.id.building_name);
        buildingCode = (TextView) mLayout.findViewById(R.id.building_code);
        campus = (TextView) mLayout.findViewById(R.id.campus);
        buildingServices = (TextView) mLayout.findViewById(R.id.building_services);
        buildingPicture = (ImageView) mLayout.findViewById(R.id.building_image);
    }

    public void updateContent(BuildingInfo buildingInfo) {
        currentBuilding = buildingInfo;

        buildingName.setText(buildingInfo.getBuildingName());
        buildingCode.setText(buildingInfo.getBuildingCode());
        campus.setText(buildingInfo.getCampus());

        //TODO add more later
    }



}
