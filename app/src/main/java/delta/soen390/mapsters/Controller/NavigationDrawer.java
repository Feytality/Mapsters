package delta.soen390.mapsters.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import delta.soen390.mapsters.Activities.SettingsActivity;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Services.LocationService;

/**
 * Created by Cat on 2/21/2015.
 */
public class NavigationDrawer implements AdapterView.OnItemClickListener {

    private final Activity mContext;
    private final Button mNavBtn;
    private ListView mListView;
    private DrawerLayout mDrawerLayout;

    public NavigationDrawer(Activity a){
        mContext = a;
        mDrawerLayout = (DrawerLayout) mContext.findViewById(R.id.drawer_layout);

        String[] menuItems = new String[]{
                mContext.getString(R.string.nav_str_schedule),
                mContext.getString(R.string.nav_str_buildings),
                mContext.getString(R.string.nav_str_services),
                mContext.getString(R.string.nav_str_departments),
                mContext.getString(R.string.nav_str_settings),
        };
        mListView = (ListView) mContext.findViewById(R.id.left_drawer);
        mListView.setAdapter(new ArrayAdapter<String>(
                mContext,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1, menuItems
        ));
        mListView.setOnItemClickListener(this);

        mNavBtn = (Button)mContext.findViewById(R.id.btn_nav_drawer);
        mNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        String msg="";
        switch(position) {
            default:
            case 0://Schedule
                msg = "Show schedule";
                mContext.startActivity (new Intent(Intent.ACTION_VIEW, Uri.parse("content://com.android.calendar/time/")));
                break;
            case 1://Buildings
                msg = "Show Buildings activity/fragment";
                break;
            case 2://Services
                msg = "Show Services activity/fragment";
                break;
            case 3://Departments
                msg = "Show department activity/fragment";
                break;
            case 4://Settings
                msg = "Show settings activity/fragment";
                mContext.startActivity(new Intent(mContext, SettingsActivity.class));
                break;
        }

        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}