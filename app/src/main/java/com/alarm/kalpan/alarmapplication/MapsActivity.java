package com.alarm.kalpan.alarmapplication;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    ArrayList<Geofence> geofenceList = new ArrayList<>();
    private final static int MY_PERMISSION_FINE = 101;
    //private FusedLocationProviderClient fusedLocationProviderClient;
    private FusedLocationProviderClient mFusedLocationClient;
    PendingIntent GeofencePendingIntent;
    private GeofencingClient mGeofencingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mGeofencingClient = LocationServices.getGeofencingClient(this);


//        googleApiClient =new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();


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

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            moveCameratoLocation(location.getLatitude(), location.getLongitude(), 15);
                            Toast.makeText(getApplicationContext(), "Moving to current location ", Toast.LENGTH_LONG).show();


                            if (location != null) {
                                //Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


            mMap.setMyLocationEnabled(true);
            //Toast.makeText(getApplicationContext(), "222222222222222222 ", Toast.LENGTH_LONG).show();

//            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//                @Override
//                public boolean onMyLocationButtonClick() {
//
//                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
//                    try {
//                        Task t = fusedLocationProviderClient.getLastLocation();
//
//                        if (t.isComplete()) {
//                            moveCameratoLocation(fusedLocationProviderClient.getLastLocation().getResult().getLatitude(), fusedLocationProviderClient.getLastLocation().getResult().getLongitude(), 15);
//                            System.out.println(fusedLocationProviderClient.getLastLocation().getResult().getLatitude());
//                        }
//
//
//                    } catch (SecurityException e) {
//
//                    }
//
//                    return true;
//                }
//            });


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
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude));

                mMap.addMarker(marker);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    String key = point.latitude + "_" + point.longitude;

                    makeGeofence(point.latitude, point.longitude, key);

                    mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());

                }


            }
        });


        PlaceAutocompleteFragment placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.search_bar);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mMap.clear();

//                 Log.d("Maps", "Place selected: " + place.getName ());
//                Geocoder geocoder=new Geocoder(getApplicationContext());
//                List <Address> addressList;
//                Address goToaddress;
//                try {
//
//
//                    addressList= geocoder.getFromLocationName(place.getName().toString(),1);
//                    if(addressList.size()==0)
//                    {
//                                Toast.makeText(getApplicationContext(),"TRY AGAIN ",Toast.LENGTH_LONG).show();
//                                return;
//                    }
//                    goToaddress=addressList.get(0);
//                    mMap.addMarker(new MarkerOptions().position(latLng).title("My location"));
//                    moveCameratoLocation(goToaddress.getLatitude(),goToaddress.getLongitude(),15);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


                LatLng latLng = place.getLatLng();
                Geocoder geocoder = new Geocoder(getApplicationContext());


                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(addressList.get(0).getLocality() + "," + addressList.get(0).getCountryName()));
                    moveCameratoLocation(latLng.latitude, latLng.longitude, 10);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        // mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // mMap.setOnMyLocationButtonClickListener(this);
        //mMap.setOnMyLocationClickListener(this);

    }

//    @Override
//    public void onMyLocationClick(@NonNull Location location) {
//        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public boolean onMyLocationButtonClick() {
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
//        // Return false so that we don't consume the event and the default behavior still occurs
//        // (the camera animates to the user's current position).
//        return false;
//    }


    public void moveCameratoLocation(double lat, double lng, float zoom) {

        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.animateCamera(update);
    }

    private PendingIntent getGeofencePendingIntent() {
        if (GeofencePendingIntent != null) {
            return GeofencePendingIntent;
        }
        Intent intent = new Intent(this, LocationAlarm.class);

        GeofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return GeofencePendingIntent;
    }

    private void makeGeofence(double lat, double lang, String key) {
        geofenceList.add(new Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(
                        lat, lang, 100
                )
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(0)
                .build());
    }

    private GeofencingRequest getGeofencingRequest() {


        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();


        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        builder.addGeofences(geofenceList);

        return builder.build();
    }

}

