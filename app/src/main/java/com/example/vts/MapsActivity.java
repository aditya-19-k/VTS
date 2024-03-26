package com.example.vts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Interpolator;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.annotations.NonNull;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public double latitudeValue;
    public double longitudeValue;
    private FusedLocationProviderClient fusedLocationClient;
    public String latitude;
    public String longitude;
    public GoogleMap mMap;

    // onCreate method is called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the content view that renders the map.
        setContentView(R.layout.fragment_maps_activity);


//        // Get the SupportMapFragment and request notification
//        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.normal_map:
//                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                //do what you like
//            default :
//                return super.onOptionsItemSelected(item);
//        }
        Toast.makeText(this, String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
        return false;
    }



    // This method is called when the map is ready to be used.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        databaseReference = FirebaseDatabase.getInstance().getReference("location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    latitude = String.valueOf(dataSnapshot.child("latitude").getValue());
                    longitude = String.valueOf(dataSnapshot.child("longitude").getValue());
                    latitudeValue = Double.parseDouble(latitude);
                    longitudeValue = Double.parseDouble(longitude);
//                    Intent i = new Intent(MapsActivity.this, MapsActivity.class);
//                    i.putExtra("latitude",latitude);
//                    i.putExtra("longitude",longitude);
//                    startActivity(i);
                    SharedPreferences sharedPreferences = getSharedPreferences("location", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("latitude", latitude);
                    editor.putString("longitude", longitude);
                    editor.apply();

//                        Toast.makeText(MapsActivity.this, latitude + "," + longitude, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }

        });

        try {

//            ##############################


//            ################################
//            Intent i = getIntent();
//            latitudeValue = Double.parseDouble(i.getStringExtra("latitude"));
//            longitudeValue = Double.parseDouble(i.getStringExtra("longitude"));

            SharedPreferences sharedPreferences = getSharedPreferences("location", MODE_PRIVATE);
            latitude = sharedPreferences.getString("latitude", "");
            longitude = sharedPreferences.getString("longitude", "");
            Toast.makeText(MapsActivity.this, latitude + "," + longitude, Toast.LENGTH_SHORT).show();

            latitudeValue = Double.parseDouble(latitude);
            longitudeValue = Double.parseDouble(longitude);
            LatLng myPos = new LatLng(latitudeValue,longitudeValue);
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .visible(true)
                    .position(myPos)
                    .title("Your Vehical"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPos));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16f));

        }catch (Exception e){
            latitudeValue = 18.50254023911764;
            longitudeValue = 73.9394177840463;

            LatLng myPos = new LatLng(latitudeValue,longitudeValue);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPos));
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .visible(true)
                    .position(myPos)
                    .title("Your Vehical"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPos));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16f));

            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }


    }

}
