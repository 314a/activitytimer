package moasis.activitytimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MyActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "moasis.activitiytimer.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        // list saved activity protocols
        String[] files = fileList();
        for (String file : files) {
            Log.i("File", file.toString());
        }

        //new ArrayAdapter<String>(this, R.id.listView, files);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, files));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Clicking on items
            }
        });

    }
    /** Called when the user clicks the add protocol button*/
    public void addLog(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ProtocolActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
    }
}
