package com.example.proyectoandroid;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private static final int LOCATION_CODE_REQUEST = 666;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private boolean permissionDenied = false;

    private FusedLocationProviderClient mFusedLocationProvider;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private int contador;
    private String token;
    private int id;
    private String username;
    public double lati;
    public  double longi;
    private double inLati;
    private double inLongi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        contador = 0;
        Bundle params = getIntent().getExtras();
        inLati=params.getDouble("latitudeIn");
        inLongi=params.getDouble("longitudeIn");
        token= params.getString("token");
        id= params.getInt("id");
        username= params.getString("username");

        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult==null){
                    return;
                }
                for(Location location:locationResult.getLocations()){
                    CameraUpdate pos = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
                    lati=location.getLatitude();
                    longi=location.getLongitude();
                    CameraUpdate zoom= CameraUpdateFactory.zoomTo(10);
                    mMap.moveCamera(pos);
                    mMap.moveCamera(zoom);
                }
            }
        };

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initializeTrackingLocation() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationProvider.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
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
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
//         Add a marker in Sydney and move the camera

//        Bundle params = getIntent().getExtras();
//        inLati=params.getDouble("latitudeIn");
//        inLongi=params.getDouble("longitudeIn");
//        LatLng sydney = new LatLng(inLati, inLongi);
//        Toast.makeText(this, inLati+"  "+inLongi, Toast.LENGTH_SHORT).show();
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        enableLocation();
    }
    private void enableLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(mMap!=null){
                mMap.setMyLocationEnabled(true);
                initializeTrackingLocation();
            }
        }else{
            ActivityCompat.requestPermissions(this,PERMISSIONS, LOCATION_CODE_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode!= LOCATION_CODE_REQUEST){
            return;
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            enableLocation();
        }else{
            permissionDenied=true;
        }
    }
    public void enviarUbicacion(View view){
        Intent intent = new Intent(this, MensajesActivity.class);
        Bundle parametros = new Bundle();
        parametros.putString("username",username);
        parametros.putInt("id",id);
        parametros.putString("token",token);
        parametros.putDouble("latitud",lati);
        parametros.putDouble("longitud",longi);
        Toast.makeText(this, "lat:"+lati+"long:"+longi, Toast.LENGTH_SHORT).show();
        intent.putExtras(parametros);
        startActivity(intent);
        finish();
    }
    public void back(View view){
        Intent intent = new Intent(this, MensajesActivity.class);
        Bundle parametros = new Bundle();
        parametros.putString("username",username);
        parametros.putInt("id",id);
        parametros.putString("token",token);
        intent.putExtras(parametros);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        contador++;
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in selected position " + contador ));
    }
}