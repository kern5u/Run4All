package fr.esstin.run4all;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.location.Location.distanceBetween;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //latitude1,longitude1
    double[] enregistrement_coordonnees = new double[2];
    float[] result = new float[1];

    double distance = 0;
    double longitude = 0;
    double latitude = 0;

    long basePause = 0;
    long temps = 0;
    long ts_debutRun = 0;

    boolean first_passage = true;
    boolean bool_pause = false;

    Chronometer chrono;
    Button pause = null;
    Button stop = null;
    GoogleMap mMap;
    DataBaseHandler bdd;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ts_debutRun = System.currentTimeMillis()/1000;

        bdd = new DataBaseHandler(this);

        chrono = (Chronometer) findViewById(R.id.chronometer);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        chrono.start();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onLocationChanged(Location location) {
                Log.d("Ici", "==============PASSE!!============");
                if (!bool_pause) {
                    /*====Definition de la position====*/
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    LatLng position = new LatLng(latitude, longitude);
                    mMap.clear();

                    /*====Gestion du marker====*/
                    MarkerOptions mp = new MarkerOptions();
                    mp.position(position);
                    mp.title("Ma position");
                    mMap.addMarker(mp);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 16));

                    /*====Calcul de la position====*/
                    if (first_passage) {
                        enregistrement_coordonnees[0] = latitude;
                        enregistrement_coordonnees[1] = longitude;
                        first_passage = false;
                    } else {
                        distanceBetween(enregistrement_coordonnees[0], enregistrement_coordonnees[1], latitude, longitude, result);
                        enregistrement_coordonnees[0] = latitude;
                        enregistrement_coordonnees[1] = longitude;
                        distance += (double) result[0];
                        Log.d("Distance", "Distance = " + distance);
                    }
                } else {
                    first_passage = true;
                }
            }
        };

        //Definition des critères de selection du fournisseur de position
        Criteria critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        critere.setCostAllowed(false);
        critere.setPowerRequirement(Criteria.POWER_HIGH);
        critere.setSpeedRequired(true);

        String provider = locationManager.getBestProvider(critere, false); //True = resultat ne peux que correspondre aux critères

        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Ici", "==============CLICK!!============");
                bool_pause = !bool_pause;
                if (bool_pause) {
                    basePause = chrono.getBase() - SystemClock.elapsedRealtime();
                    chrono.stop();

                } else {
                    chrono.setBase(SystemClock.elapsedRealtime() + basePause);
                    chrono.start();
                }
            }

        });

        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                temps = (SystemClock.elapsedRealtime() - chrono.getBase())/100;
                Log.d("Debogage", "Temps chrono fin = " + temps);
                bdd.insertRunData(ts_debutRun, temps, distance);
                onPause();
                Intent intent = new Intent(MapsActivity.this, PerfActivity.class);
                startActivity(intent);
            }

        });


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putDouble("latitude", latitude);
        savedInstanceState.putDouble("longitude", longitude);
        Log.d("Debug/map", "latitude on save : " + latitude);
        Log.d("Debug/map", "longitude on save : " + longitude);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        latitude = savedInstanceState.getDouble("latitude");
        longitude = savedInstanceState.getDouble("longitude");
        Log.d("Debug/map","latitude on restore : "+latitude);
        Log.d("Debug/map","longitude on restore : "+longitude);
    }
    */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //mMap.addMarker(new MarkerOptions().position(position_initiale).title("Ma position"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(position_initiale));
    }

    //=======Quand appuie sur le bouton return=============
    @Override
    public void onPause(){
        super.onPause();
        Log.i("onPause", "inside onPause");
        chrono.stop();
        //Durée du rafraichissement (ms)/distance de rafr (m)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);
    }
}
