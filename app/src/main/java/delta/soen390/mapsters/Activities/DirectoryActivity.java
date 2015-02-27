package delta.soen390.mapsters.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import delta.soen390.mapsters.R;

public class DirectoryActivity extends Activity {

    ListView fruitView;
    private AutoCompleteTextView actv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_directory_placeholder);

//        fruitView = (ListView) findViewById(android.R.id.list);
//
//        fruitView.setFastScrollEnabled(true);
//        String[] fruits = getResources().getStringArray(R.array.fruits_array);
//
//        List<String> fruitList = Arrays.asList(fruits);
//        Collections.sort(fruitList);
//
//        fruitView.setAdapter(new ListAdapter(this,
//                android.R.layout.simple_list_item_1, fruitList));
//
//
//
//
//        fruitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parent, View arg1,
//                                    int position, long arg3) {
//                Toast.makeText(getApplicationContext(),
//                        "" + parent.getItemAtPosition(position),
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//
//        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
//        actv.setThreshold(1);
//        String[] countries = getResources().
//                getStringArray(R.array.fruits_array);
//        ArrayAdapter adapter = new ArrayAdapter
//                (this,android.R.layout.simple_dropdown_item_1line,countries);
//        actv.setAdapter(adapter);
//
//
//        actv.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if(actv.getText().toString().contains("Search")){
//                    actv.setText("");
//
//                }
//            }
//        });
//
//
//        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Toast.makeText(getApplicationContext(),position,Toast.LENGTH_SHORT).show();
//            }
//
//        });
//
//    }
//
//
//
//
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //getMenuInflater().inflate(R.menu.main, menu);
//        return true;
    }
}






