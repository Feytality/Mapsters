package delta.soen390.mapsters.ViewComponents;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.Controller.CampusViewSwitcher;
import delta.soen390.mapsters.Data.Campus;
import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 2/11/2015.
 */
public class CampusSwitchUI {
    private final Activity mFActivity;
    private final View mView;
    private Switch mCampusSwitch;
    private MapsActivity mActivity;
    private CampusViewSwitcher mCampusViewSwitcher;

    public CampusSwitchUI(MapsActivity activity, CampusViewSwitcher viewSwitcher)
    {
        mActivity = activity;
        mCampusViewSwitcher = viewSwitcher;
        mCampusSwitch = (Switch) mActivity.findViewById(R.id.campusSwitch);

        if (mCampusSwitch != null) {

		
            mCampusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mCampusViewSwitcher.SwitchView();

                    if (isChecked) {
                        Toast.makeText(mActivity.getApplicationContext(), "This is SGW Campus", Toast.LENGTH_SHORT).show();
					} else {
                        Toast.makeText(mActivity.getApplicationContext(), "This is Loyola Campus", Toast.LENGTH_SHORT).show();
					}
                }
            }
			);

        }
        mFActivity = null;
        mView = null;
    }

    public CampusViewSwitcher getCampusViewSwitcher(){return mCampusViewSwitcher;}

    public Campus.Name getCurrentCampus(){
       if (mCampusSwitch.isChecked()){
           return Campus.Name.SGW;
       }
       return Campus.Name.LOY;
    }

    public void toggleCampusSwitch(){
        mCampusSwitch.setChecked(!mCampusSwitch.isChecked());
    }

    public void verifySettings(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        if(prefs.getString("campus_list","SGW").equals("SGW")){
            mCampusSwitch.setChecked(true);

        }

    }

}
