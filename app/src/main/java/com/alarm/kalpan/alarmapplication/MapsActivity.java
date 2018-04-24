package com.alarm.kalpan.alarmapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private final static int MY_PERMISSION_FINE = 101;
    private FusedLocationProviderClient mFusedLocationClient;
//    PendingIntent GeofencePendingIntent;
//    private GeofencingClient mGeofencingClient;
    float radius = 200;
    CircleOptions circleOptions = new CircleOptions();
    //ArrayList<LatLng> latLngsList = new ArrayList<>();
    //ArrayList<Float> radiusList = new ArrayList<>();
    LatLng latLng;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        radius = getIntent().getFloatExtra("Radius", 200);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            moveCameratoLocation(location.getLatitude(), location.getLongitude(), 15);
                            Toast.makeText(getApplicationContext(), "Moving to current location ", Toast.LENGTH_LONG).show();


                            if (location == null) {
                                Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


            mMap.setMyLocationEnabled(true);
            //Toast.makeText(getApplicationContext(), "222222222222222222 ", Toast.LENGTH_LONG).show();

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Toast.makeText(getApplicationContext(), "Requesting Permission ", Toast.LENGTH_LONG).show();

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE);
                //requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }


        }

        if (mMap.isMyLocationEnabled()) {


            Toast.makeText(getApplicationContext(), "Location is enabled ", Toast.LENGTH_LONG).show();


        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // TODO Auto-generated method stub
                mMap.clear();

                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude));

                mMap.addMarker(marker);


                circleOptions = new CircleOptions()
                        .center(new LatLng(point.latitude, point.longitude))
                        .radius(radius)
                        .fillColor(0x30ff0000);
                mMap.addCircle(circleOptions);
                latLng=point;
                //radiusList.add(radius);
            }
        });


        /////////////////  CLEAR BUTTON

        Button clear_button = findViewById(R.id.map_clear);
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                latLng=null;

            }
        });


/////////////////   SET RADIUS BUTTON
        Button radius_button = findViewById(R.id.map_radius);
        radius_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Set Radius in meters");
                final EditText input = new EditText(MapsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setText(radius+"");
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (input.getText().toString() == null || input.getText().toString().isEmpty()) {
                            return;
                        }

                        radius = Float.parseFloat(input.getText().toString());
                        circleOptions.radius(radius);
                    }
                });


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });

        ///////////// ADD ALARM BUTTON
        Button add_button = findViewById(R.id.map_addAlarm);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    if(latLng==null)
                    {
                        Toast.makeText(getApplicationContext(),"Please select location",Toast.LENGTH_SHORT).show();
                        return;
                    }

//                        String key = latLng.latitude + ":" + latLng.longitude;
//
//                        Geofence g = makeGeofence(latLng.latitude, latLng.longitude, radius,key);
//                        mGeofencingClient.addGeofences(getGeofencingRequest(g), getGeofencePendingIntent(getIntent().getIntExtra("AlarmID",-1)));
                }
                Toast.makeText(getApplicationContext(),"Alarm is added",Toast.LENGTH_LONG).show();

                System.out.println("!!!!!!!! ALARM IS ADDED");
                Intent data = new Intent();
                data.putExtra("Radius",radius);
                data.putExtra("Latitude",latLng.latitude);
                data.putExtra("Longitude",latLng.longitude);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });


        PlaceAutocompleteFragment placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.search_bar);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mMap.clear();

                LatLng latLng = place.getLatLng();
                Geocoder geocoder = new Geocoder(getApplicationContext());


                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    //add the pin point
                    mMap.addMarker(new MarkerOptions().position(latLng).title(addressList.get(0).getLocality() + "," + addressList.get(0).getCountryName()));
                    circleOptions = new CircleOptions()
                            .center(new LatLng(latLng.latitude, latLng.longitude))
                            .radius(radius)
                            .fillColor(0x30ff0000);
                    mMap.addCircle(circleOptions);

                    moveCameratoLocation(latLng.latitude, latLng.longitude, 15);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });
    }


    public void moveCameratoLocation(double lat, double lng, float zoom) {

        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.animateCamera(update);
    }

//    private PendingIntent getGeofencePendingIntent(int requestCode) {
//        if (GeofencePendingIntent != null) {
//            System.out.println("FLAG REUSE");
//            return GeofencePendingIntent;
//        }
//        Intent intent = new Intent(getApplicationContext(), LocationAlarmReceiver.class);
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            GeofencePendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            startForegroundService(intent);
//
//
//        }
//
//        System.out.println("flag ashwin3");
//
//        return GeofencePendingIntent;
//    }
//
//    private Geofence makeGeofence(double lat, double lang, Float radius, String key) {
//        Geofence g = new Geofence.Builder()
//                .setRequestId(key) //v2 is the raduis
//                .setCircularRegion(
//                        lat, lang, radius   // the last argument is the radius in metres TODO: change this to dynamic
//                )
//                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
//                .setExpirationDuration(Geofence.NEVER_EXPIRE)
//                .build();
//
//        System.out.println("flag ashwin1");
//        return g;
//
//    }
//
//    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
//
//
//        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
//
//
//        // initial_transition_enter indicates that if user is already in one geofence, trigger the transition_enter move
//        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
//
//        builder.addGeofence(geofence);
//        System.out.println("flag ashwin2");
//        return builder.build();
//    }

}


