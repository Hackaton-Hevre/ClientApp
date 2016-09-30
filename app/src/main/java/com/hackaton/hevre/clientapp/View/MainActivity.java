package com.hackaton.hevre.clientapp.View;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hackaton.hevre.clientapp.Control.GetResponseCallback;
import com.hackaton.hevre.clientapp.Control.HypotheticalApi;
import com.hackaton.hevre.clientapp.R;


public class MainActivity extends ActionBarActivity {

    private HypotheticalApi mApi = HypotheticalApi.getInstance();
    private Button mSanityButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSanityButon = (Button) findViewById(R.id.sanityButton);
        mSanityButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApi.getUserProfile("avichay", new GetResponseCallback() {
                    @Override
                    public void onDataReceived(String profile) {
                        Toast.makeText(getBaseContext(), "Received!" + profile, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
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
