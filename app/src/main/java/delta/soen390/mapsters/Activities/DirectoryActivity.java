package delta.soen390.mapsters.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import delta.soen390.mapsters.Fragments.BuildingDFragment;
import delta.soen390.mapsters.Fragments.DepartmentDFragment;
import delta.soen390.mapsters.Fragments.ServiceDFragment;
import delta.soen390.mapsters.R;

public class DirectoryActivity extends FragmentActivity {

    ListView fruitView;
    private AutoCompleteTextView actv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_directory_placeholder);
        int myParam=0;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            myParam = extras.getInt("Directory");//values map to navigationdrawer.java
            Toast.makeText(this,Integer.toString(myParam),Toast.LENGTH_SHORT).show();
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
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}






