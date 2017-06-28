package com.samples.location.reversegeocoding;

import android.app.Activity;
import android.location.*;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GeocoderActivity extends Activity {

    private TextView text;
    private LocationManager manager;
    private Geocoder geocoder;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            printLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            printLocation(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geocoder);

        text = (TextView)findViewById(R.id.text);
        geocoder = new Geocoder(getApplicationContext());

        manager = (LocationManager)getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        Location loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        printLocation(loc);
    }

    public void printLocation(Location location){
        if (location != null){
            text.setText("Longitude:\t" + location.getLongitude() +
                    "\nLatitude:\t" + location.getLatitude());
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 20);
                for (int i = 0; i<addresses.size(); i++){
                    Address address = addresses.get(i);
                    text.append("\nAddress# " + i +
                            "\n\tLocality: " + address.getLocality() +
                            "\n\tCountryName: " + address.getCountryName() +
                            "-" + address.getCountryCode() +
                            "\n\tFeatureName: " + address.getFeatureName() +
                            "\n\tPostalCode: " + address.getPostalCode()
                    );
                }
            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            text.setText("Location unavailable");
        }

    }
}
