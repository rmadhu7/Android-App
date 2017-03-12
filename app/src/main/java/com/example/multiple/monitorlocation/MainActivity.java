package com.example.multiple.monitorlocation;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements LocationListener
{
        EditText editTextNumber,editTextDistance,editTextDuration;
        Button buttonOk,buttonBack;
        StringBuilder sb;
        String currentLatitude,currentLongitude,strlatilong,addresses,s,d,t;
        String returnAddress = "";
        long minTime;
        float minDistance;

        /*On Clicking Start button, the edittext fields are retrieved and sent to recentLocationUpdates method to send location updates*/
        public boolean doOk(View v)
        {
                s= editTextNumber.getText().toString();
                d=editTextDistance.getText().toString();
                t=editTextDuration.getText().toString();
                minTime= Long.parseLong(t);//Parsing to Long
                minDistance = Float.parseFloat(d); //Parsing to float
                Toast.makeText(getApplicationContext(), "Starting application", Toast.LENGTH_LONG).show();
                return true;
        }

        /*On Clicking back button, the application is minimized and still running in the background */
        public void doBack(View v)
        {
                finish();
        }


        /* Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                editTextNumber=(EditText) findViewById(R.id.editText1);
                editTextDuration=(EditText)findViewById(R.id.editText3);
                editTextDistance=(EditText)findViewById(R.id.editText2);
                sb = new StringBuilder("LOCATION_SERVICE\n");
                /* Calling Location Manager to check in GPS service in the device */
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                List<String> providers = locationManager.getAllProviders();
                String gpsProvider = null;
                /* Checks for all the providers*/
                for (String provider : providers)
                {
                    sb.append(provider + "\n");
                    if (provider.equals("gps"))// Checks gps provider
                    {
                        gpsProvider = provider;
                    }
                }
                /* Using  gps provider for location updates*/
                if (gpsProvider == null)
                {
                    sb.append("Could not find gps provider\n");
                }
                /*Using gps provider for location updates. Parameters - time, provider, distance is passed to it */
                else
                {
                    sb.append("Using gps provider\n");
                    locationManager.requestLocationUpdates(gpsProvider, minTime, minDistance, this);
                }
            }

            /* Called when Location is changed and requires an update, which in turn calls the recentLocationUpdate */
            @Override
            public void onLocationChanged(Location arg0)
            {

                    sb.append("onLocationChanged: " + arg0.getLatitude() + "-" + arg0.getLongitude() + "\n");
                    strlatilong=String.valueOf(sb);
                    currentLatitude=String.valueOf(arg0.getLatitude());
                    currentLongitude=String.valueOf(arg0.getLongitude());
                    GetAddress(currentLatitude, currentLongitude);

                    try
                    {
                        sendSmsMessage(s, returnAddress);
                        Toast.makeText(getApplicationContext(), "Sending message",Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    Toast.makeText(getApplicationContext(), returnAddress,Toast.LENGTH_LONG).show();
            }


            @Override
            public void onProviderDisabled(String arg0)
            {
                    // TODO Auto-generated method stub
                    sb.append("onProviderDisabled: " + arg0 + "\n");
            }

            @Override
            public void onProviderEnabled(String arg0)
            {
                    // TODO Auto-generated method stub
                    sb.append("onProviderEnabled: " + arg0 + "\n");
            }

            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2)
            {
                // TODO Auto-generated method stub

            }
            private void sendSmsMessage(String address, String message)
            {
                    SmsManager smsMgr = SmsManager.getDefault();
                    smsMgr.sendTextMessage(address, null, message, null, null);
            }

            /* Using geocoder service to convert the latitude and longitude to address*/
            /* then the address is sent to the SmsManager to send text message */
            public String GetAddress(String lat, String lon)
            {
                    Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

                    try
                    {
                        List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
                        if(addresses != null)
                        {
                            Address returnedAddress = addresses.get(0);
                            StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                            for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++)
                            {
                                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                            }
                            returnAddress = strReturnedAddress.toString();
                        }
                        else
                        {
                            returnAddress = "No Address returned!";
                        }
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        returnAddress = "Can't get Address!";
                    }
                    return returnAddress;
            }

            /* On pressing the back button in the device, the application is destroyed */
            @Override
            public void onBackPressed()
            {
                super.onBackPressed();
                this.finish();
                System.exit(0);
            }

}
