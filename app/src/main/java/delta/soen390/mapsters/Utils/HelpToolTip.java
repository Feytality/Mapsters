package delta.soen390.mapsters.Utils;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.maps.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;

/**
 * Created by Cat on 3/22/2015.
 */
public class HelpToolTip implements View.OnClickListener{
    private final MapsActivity mActivity;
    private Target expose;
    private ShowcaseView showcaseView;
    private ShowcaseView stopView;

    private int step;


    public HelpToolTip(Activity activity,int viewId,int startStep){
        step=startStep;
        mActivity = (MapsActivity)activity;

        stopView = new ShowcaseView.Builder(activity)
                .setTarget(Target.NONE)
                .setStyle(R.style.blockShowcaseTheme)
                             .build();

        showcaseView = new ShowcaseView.Builder(activity)
                .setTarget(Target.NONE)
                .setStyle(R.style.CustomShowcaseTheme)
                .setOnClickListener(this)
                .setContentTitle("Welcome")
                .setContentText("Here is some basic info")
                .build();


        mActivity.onKeyDown(KeyEvent.KEYCODE_BACK,new KeyEvent(KeyEvent.KEYCODE_BACK,KeyEvent.KEYCODE_BACK));



        showcaseView.setButtonText("Lets Start!");
        stopView.setButtonText("");
        showcaseView.setBlocksTouches(true);
         stopView.setBlocksTouches(true);
        stopView.hideButton();
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = ((Number) (activity.getResources().getDisplayMetrics().density * 12))
                .intValue();
        margin+=90;
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.setMargins(margin, margin, margin, margin+ 120);
        showcaseView.setButtonPosition(lps);


    }

    public HelpToolTip(Activity activity,int viewId){
        expose = new ViewTarget(viewId,activity);
        mActivity = (MapsActivity)activity;

        showcaseView = new ShowcaseView.Builder(activity)
                .setTarget(expose)
                .setContentTitle("bacon")
                .setContentText("morebacon")
                .build();
        showcaseView.setButtonText("Got it!");

    }


    public void startTutorial(){
        DrawerLayout drawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        SlidingUpPanelLayout panel = (SlidingUpPanelLayout)mActivity.findViewById(R.id.sliding_layout);
        showcaseView.setButtonText("Got it!");
        switch (step){
            case 0:
                drawerLayout.closeDrawer(Gravity.LEFT);

                expose = new ViewTarget(mActivity.findViewById(R.id.btn_nav_drawer));
                showcaseView.setShowcase(expose,true);
                showcaseView.setContentTitle(mActivity.getString(R.string.tut_title0));
                showcaseView.setContentText(mActivity.getString(R.string.tut_text0));
                break;
            case 1:
                drawerLayout.openDrawer(Gravity.LEFT);
                expose = new ViewTarget(mActivity.findViewById(R.id.nav_options));
                showcaseView.setShowcase(expose,true);
                showcaseView.setContentTitle(mActivity.getString(R.string.tut_title1));
                showcaseView.setContentText(mActivity.getString(R.string.tut_text1));
                break;

            case 2:
                drawerLayout.closeDrawer(Gravity.LEFT);

                expose = new ViewTarget(mActivity.findViewById(R.id.drawer_layout));
                mActivity.onMapClick(new LatLng(45.4973,-73.579));
                showcaseView.setShowcase(expose,true);
                showcaseView.setContentTitle(mActivity.getString(R.string.tut_title2));
                showcaseView.setContentText(mActivity.getString(R.string.tut_text2));
                break;

            case 3:
                expose = new ViewTarget(mActivity.findViewById(R.id.campusSwitch));
                showcaseView.setShowcase(expose,true);
                showcaseView.setContentTitle(mActivity.getString(R.string.tut_title3));
                showcaseView.setContentText(mActivity.getString(R.string.tut_text3));
                break;

            case 4:
                expose = new ViewTarget(mActivity.findViewById(R.id.locate_me));
                showcaseView.setShowcase(expose,true);
                showcaseView.setContentTitle(mActivity.getString(R.string.tut_title4));
                showcaseView.setContentText(mActivity.getString(R.string.tut_text4));
                break;

            case 5:
                expose = new ViewTarget(mActivity.findViewById(R.id.global_search));
                showcaseView.setShowcase(expose,true);
                showcaseView.setContentTitle(mActivity.getString(R.string.tut_title5));
                showcaseView.setContentText(mActivity.getString(R.string.tut_text5));
                break;

            case 6:
                expose = new ViewTarget(mActivity.findViewById(R.id.building_name));
                showcaseView.setShowcase(expose,true);
                showcaseView.setContentTitle(mActivity.getString(R.string.tut_title6));
                showcaseView.setContentText(mActivity.getString(R.string.tut_text6));
                break;

            case 7:
                panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                expose = new ViewTarget(mActivity.findViewById(R.id.drawer_layout));
                showcaseView.setShowcase(expose,true);
                showcaseView.setContentTitle(mActivity.getString(R.string.tut_title7));
                showcaseView.setContentText(mActivity.getString(R.string.tut_text7));
                break;

            case 8:
                expose = new ViewTarget(mActivity.findViewById(R.id.direction_button));
                showcaseView.setShowcase(expose,true);
                showcaseView.setContentTitle(mActivity.getString(R.string.tut_title8));
                showcaseView.setContentText(mActivity.getString(R.string.tut_text8));
                break;

            case 9:
                showcaseView.hide();
                stopView.hide();
                panel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                break;




        }
        step++;




    }

    @Override
    public void onClick(View v) {
        startTutorial();
    }
}
