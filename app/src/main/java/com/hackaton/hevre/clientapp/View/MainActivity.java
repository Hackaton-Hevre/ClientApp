package com.hackaton.hevre.clientapp.View;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hackaton.hevre.clientapp.Control.GetResponseCallback;
import com.hackaton.hevre.clientapp.Control.HypotheticalApi;
import com.hackaton.hevre.clientapp.R;


public class MainActivity extends ActionBarActivity {

    private HypotheticalApi mApi = HypotheticalApi.getInstance();
    private Button mSanityButon;
    private Button mGetLocationButton;
    private Button mDistanceButton;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private Location mLocation;
    private Location mAmitsHouse;
    private TextView mLongitude;
    private TextView mLatitude;

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

        mLongitude = (TextView) findViewById(R.id.longitude);
        mLatitude = (TextView) findViewById(R.id.latitude);

        mAmitsHouse = new Location("Amit");
        mAmitsHouse.setLatitude(32.414133);
        mAmitsHouse.setLongitude(34.951222);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.w("!!!!!!!!!!!!!", "IN ONLOCATIONCHANGED");
                try
                {
                    mLocation = location;
                    /*mLongitude.setText(location.getLongitude() + "");
                    mLatitude.setText(location.getLatitude() + "");*/
                    int maxDist = 250;
                    if (mApi.distFrom(mLocation.getLatitude(), mLocation.getLongitude(), mAmitsHouse.getLatitude(), mAmitsHouse.getLongitude()) < maxDist)
                    {
                        Toast.makeText(getBaseContext(), "You are now less than " + maxDist + " from Amit's house!!!", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // todo : what is the provider
        try
        {
            mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
            Log.w("!!!!!!!!!!!!!", "YES");
        }
        catch (Exception e)
        {
            Log.w("!!!!!!!!!!!!!", "NO");
            e.printStackTrace();
        }

        mGetLocationButton = (Button) findViewById(R.id.getLocationButton);
        mGetLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, mLocationListener);
                Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double latitude=0;
                double longitude=0;
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(getBaseContext(), "Your location is : " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
            }
        });

        mDistanceButton = (Button) findViewById(R.id.distanceButton);
        mDistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    float dist = mApi.distFrom(mLocation.getLatitude(), mLocation.getLongitude(), mAmitsHouse.getLatitude(), mAmitsHouse.getLongitude());
                    Toast.makeText(getBaseContext(), "Distance from Amit's house is : " + dist, Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
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
