package delta.soen390.mapsters.ViewComponents;

import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import delta.soen390.mapsters.Controller.CampusViewSwitcher;
import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 2/11/2015.
 */
public class CampusSwitchUI {
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
                        Toast.makeText(mActivity.getApplicationContext(), "Show SGW Map", Toast.LENGTH_SHORT).show();
					} else {
                        Toast.makeText(mActivity.getApplicationContext(), "Show Loyola Map", Toast.LENGTH_SHORT).show();
					}
                }
            }
			);
        }
    }




}