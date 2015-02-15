package delta.soen390.mapsters.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import delta.soen390.mapsters.R;


public class MainActivity extends ActionBarActivity {
private TextView textPointer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_pane);
        loadBuildingInfo();
    }

    public void loadBuildingInfo(){//uses building info
        loadHeadings();
        loadServices();

    }

    private void loadServices(){

        //for(int i=0;i<buildinginfo.length;i++) {
            textPointer = (TextView) findViewById(R.id.t1);
            textPointer.setText(getResources().getText(R.string.t1));
            textPointer.setMovementMethod(LinkMovementMethod.getInstance());

        //}
    }
    private void loadHeadings(){
        textPointer= (TextView) findViewById(R.id.building_code);
        textPointer.setText("");//
        textPointer= (TextView) findViewById(R.id.campus);
        textPointer.setText("");//
        textPointer= (TextView) findViewById(R.id.building_name);
        textPointer.setText("");//poifect
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
