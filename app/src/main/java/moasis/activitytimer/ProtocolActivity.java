package moasis.activitytimer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProtocolActivity extends AppCompatActivity {
    StringBuilder protocol;
    String sep,nl,activitySelected;
    TextView protocolView;
    EditText nameProtocol;
    StringBuilder header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        sep = ";";
        nl = System.getProperty("line.separator");
        activitySelected="none";

        header = new StringBuilder();
        header.append("Activity").append(sep).append("Start").append(sep).append("Stop");

        protocol = new StringBuilder();
        protocol.append(header);
        // create console view
        Intent intent = getIntent();

        protocolView = (TextView) findViewById(R.id.logView);
        protocolView.setTextSize(12);
        protocolView.setText(protocol);

        // set name
        nameProtocol = (EditText) findViewById(R.id.nameLog);
        nameProtocol.setTextSize(14);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String formattedDate = sdf.format(date);

        nameProtocol.setText("Log "+formattedDate);
        // spinner to set/select activity
        final Spinner activitySpinner = (Spinner) findViewById(R.id.activitySpinner);
        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String items = activitySpinner.getSelectedItem().toString();
                if(items != null && !items.isEmpty()) {
                    //updateActivity(items); //Log.i("Selected item : ", items);
                    protocol.append("\n").append(items).append(sep);
                    protocolView.setText(protocol);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    public void start(View view) {
        // Do something in response to button
        Long tsLong = System.currentTimeMillis()/1000;
        String startTime = tsLong.toString();
        protocol.append(startTime).append(sep);
        protocolView.setText(protocol);
    }
    public void stop(View view) {
        // Do something in response to button
        Long tsLong = System.currentTimeMillis()/1000;
        String stopTime = tsLong.toString();
        protocol.append(stopTime);
        protocolView.setText(protocol);
    }
    public void saveLog(View view) {
        // Save protocol as text file
        String fn = nameProtocol.getText().toString();
        String filename = fn+".txt";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(protocol.toString().getBytes());
            outputStream.close();
            File f = new File(filename);
            String msg = getString(R.string.msg_saved_log)+" "+filename;
            Toast myToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            myToast.show();
        } catch (Exception e) { e.printStackTrace();}
    }
    public void sendLog(View view){
        // https://developer.android.com/training/sharing/send.html
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, protocol.toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }
    public void clearLog(View view) {
        protocol = new StringBuilder();
        protocol.append(header);
        protocolView.setText(protocol);
        Toast myToast = Toast.makeText(getApplicationContext(), getString(R.string.msg_clear_log), Toast.LENGTH_LONG);
        myToast.show();
    }
}
