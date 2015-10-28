package com.rpappa.ryan.joysticktest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Context context = getApplicationContext();
        final SharedPreferences sharedPref = context.getSharedPreferences(
                "com.rpappa.ryan.joysticktest", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        final String ipKey = "com.rpappa.ryan.joysticktest.ip";
        final String portKey = "com.rpappa.ryan.joysticktest.port";

        final String ip = sharedPref.getString(ipKey, "192.168.110.127");
        final int port = sharedPref.getInt(portKey, 5000);

        Button saveButton = (Button) findViewById(R.id.button2);
        final EditText ipText = (EditText) findViewById(R.id.editText);
        final EditText portText = (EditText) findViewById(R.id.editText2);

        ipText.setText(ip);
        portText.setText(String.valueOf(port));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(ipKey, ipText.getText().toString());
                editor.putInt(portKey, Integer.valueOf(portText.getText().toString()));
                test1.ip = ipText.getText().toString();
                test1.port = Integer.valueOf(portText.getText().toString());
                editor.apply();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
