package delta.soen390.mapsters.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.WindowManager;

import delta.soen390.mapsters.Fragments.BuildingDFragment;
import delta.soen390.mapsters.Fragments.DepartmentDFragment;
import delta.soen390.mapsters.Fragments.ServiceDFragment;
import delta.soen390.mapsters.R;

public class DirectoryActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_directory_placeholder);
        int myParam=0;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            myParam = extras.getInt("Directory");//values map to navigationdrawer.java
        }




        Fragment buildings = new BuildingDFragment();
        Fragment services = new ServiceDFragment();
        Fragment departments = new DepartmentDFragment();
        Fragment[] directories = {buildings,
                services,departments };

// Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.m_container, directories[myParam])
                .commit();
        //prevent keyboard from popping up
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}






