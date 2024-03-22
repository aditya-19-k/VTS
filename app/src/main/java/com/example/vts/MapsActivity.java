package com.example.vts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String latitudeAndLongitudeValue;
    public String[] parts;
    public double longitudeValue ;
    //    public double longitudeValue = Double.valueOf(parts[1]);
    public double latitudeValue ;
//    public double latitudeValue = Double.valueOf(parts[0]);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps_activity);

        //Read data from firebase
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("location/latlng");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                latitudeAndLongitudeValue = dataSnapshot.getValue(String.class);
                parts = latitudeAndLongitudeValue.split(",");  //Split the string form comma (,)
                Log.d("TAG", "Single Value: " + latitudeAndLongitudeValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }

        });

        try {

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
        catch (Exception exception){
            Toast.makeText(this, String.valueOf(exception), Toast.LENGTH_SHORT).show();
        }

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

//            latitudeValue = 18.50254023911764;
//            longitudeValue = 73.9394177840463;

        try {
            latitudeValue = Double.parseDouble(parts[0]);
            longitudeValue = Double.parseDouble(parts[1]);
            LatLng location = new LatLng(latitudeValue, longitudeValue);
            mMap.addMarker(new

                    MarkerOptions().position(location));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16f));

        }
        catch (Exception exception){
            Toast.makeText((Context) this, String.valueOf(exception), Toast.LENGTH_SHORT).show();
        }

    }

//    public class RefreshMap {
//
//        private final Map<String, String> map = new HashMap<>();
//
//        public void main(String[] args) {
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    // Refresh the map
//                    map.put("key", "AIzaSyBHKRg9MD0B6AWDLF64JBBzG8fWMsSF5TI");
//                }
//            }, 1000);
//        }
//    }

}