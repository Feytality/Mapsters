package delta.soen390.mapsters.Controller;

import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import delta.soen390.mapsters.Buildings.BuildingInfo;
import delta.soen390.mapsters.R;

/**
 * Created by Cat on 2/12/2015.
 */
public class UIMapper {
    private final Activity context;
    public UIMapper(Activity a){
        context = a;
    }


    public void loadBuildingInfo(BuildingInfo buildingInfo){
        TextView textPointer;

        textPointer= (TextView) context.findViewById(R.id.building_code);
        textPointer.setText(buildingInfo.getBuildingCode());//

        textPointer= (TextView) context.findViewById(R.id.campus);
        textPointer.setText(buildingInfo.getCampus().toString());//

        textPointer= (TextView) context.findViewById(R.id.building_name);
        textPointer.setText(buildingInfo.getBuildingName());//

        //Loops tru these to set the services
        textPointer = (TextView) context.findViewById(R.id.t1);
        textPointer.setText(context.getResources().getText(R.string.t1));
        textPointer.setMovementMethod(LinkMovementMethod.getInstance());

    }

}
