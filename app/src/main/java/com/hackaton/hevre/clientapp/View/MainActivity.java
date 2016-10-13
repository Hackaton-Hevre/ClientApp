package com.hackaton.hevre.clientapp.View;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hackaton.hevre.clientapp.Control.LOGIN_STATUS;
import com.hackaton.hevre.clientapp.Control.RESTService.GetResponseCallback;
import com.hackaton.hevre.clientapp.Control.RESTService.HypotheticalApi;
import com.hackaton.hevre.clientapp.R;


public class MainActivity extends ActionBarActivity {

    private HypotheticalApi mApi = HypotheticalApi.getInstance();
    private Button mSanityButon;
    private Button mGetLocationButton;
    private Button mDistanceButton;
    private Button mConnectButton;
    private Button mRegisterButton;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private Location mLocation;
    private Location mAmitsHouse;
// ran hahomo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAllElement();
    }

    private void initAllElement() {
        initLocationManagers();
        initButtons();
    }

    private void initButtons() {
        mSanityButon = (Button) findViewById(R.id.sanityButton);
        mSanityButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApi.getUserProfile("avichay", new GetResponseCallback<String>() {
                    @Override
                    public void onDataReceived(String profile) {
                        Toast.makeText(getBaseContext(), "Received!" + profile, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mAmitsHouse = new Location("Amit");
        mAmitsHouse.setLatitude(32.414133);
        mAmitsHouse.setLongitude(34.951222);

        mGetLocationButton = (Button) findViewById(R.id.getLocationButton);
        mGetLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, mLocationListener);
                    Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double latitude=0;
                    double longitude=0;
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Toast.makeText(getBaseContext(), "Your location is : " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getBaseContext(), "Please open your GPS", Toast.LENGTH_LONG).show();
                }
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

        mConnectButton = (Button) findViewById(R.id.Login_button);
        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo : implement
                final String username = ((TextView) findViewById(R.id.username_text)).getText().toString();
                final String password = ((TextView) findViewById(R.id.password_text)).getText().toString();

                if (!checkLoginInput(username, password))
                {
                    Toast.makeText(getBaseContext(), "Invalid user name or password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mApi.login(username, password, new GetResponseCallback<String>() {
                        @Override
                        public void onDataReceived(String result) {
                            try {
                                int res = Integer.parseInt(result);
                                switch (res)
                                {
                                    case LOGIN_STATUS.INVALID_CREDENTIALS:
                                        Toast.makeText(getBaseContext(), "User name or password are not right", Toast.LENGTH_SHORT).show();
                                        break;
                                    case LOGIN_STATUS.NAME_ALREADY_EXISTS:
                                        Toast.makeText(getBaseContext(), "User name is already used", Toast.LENGTH_SHORT).show();
                                        break;
                                    case LOGIN_STATUS.NAME_DOESNT_EXIST:
                                        Toast.makeText(getBaseContext(), "Wrong username", Toast.LENGTH_SHORT).show();
                                        break;
                                    case LOGIN_STATUS.SUCCESS:
                                        // todo : save credentials
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        Bundle b = new Bundle();
                                        b.putString("user", username); //Your id
                                        intent.putExtras(b); //Put your id to your next Intent
                                        startActivity(intent);
                                        finish();
                                        break;
                                    default:
                                        break;
                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getBaseContext(), "Some error occured, try again later", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                    });
                }
            }

            private boolean checkLoginInput(String username, String password) {
                return (!(username.equals("")) && !(username == null)) && (!(password.equals("")) && !(password == null)) ;
            }
        });

        mRegisterButton = (Button) findViewById(R.id.Register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initLocationManagers() {
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try
                {
                    mLocation = location;
                    int maxDist = 1;
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
        mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
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
