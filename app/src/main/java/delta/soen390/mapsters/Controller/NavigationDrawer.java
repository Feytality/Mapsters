package delta.soen390.mapsters.Controller;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
        Toast.makeText(mContext, "Fuckin " + position, Toast.LENGTH_SHORT).show();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }
}
