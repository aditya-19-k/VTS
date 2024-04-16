package com.example.vts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.reactivex.rxjava3.annotations.NonNull;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    DatabaseReference databaseReference;
    public double latitudeValue;
    public double longitudeValue;
    public String latitude;
    public String longitude;
    public GoogleMap mMap;
    Button mapTypeButton;

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

//        mapTypeButton = findViewById(R.id.map_type_button);
//        mapTypeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.normal_map) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return true;
        } else if (id == R.id.hybrid_map) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            return true;
        } else if (id == R.id.satellite_map) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return true;
        }
        else if (id == R.id.terrain_map) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    // This method is called when the map is ready to be used.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Get the SharedPreferences object
        SharedPreferences sharedPref = getSharedPreferences("usernameSP", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "abc");
        databaseReference = FirebaseDatabase.getInstance().getReference("users/"+username+"/Devices/d1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    latitude = String.valueOf(dataSnapshot.child("latitude").getValue());
                    longitude = String.valueOf(dataSnapshot.child("longitude").getValue());
                    latitudeValue = Double.parseDouble(latitude);
                    longitudeValue = Double.parseDouble(longitude);

                    try {
                        LatLng myPos = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPos));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions()
                                .visible(true)
                                .position(myPos)
                                .title("Your Vehical"));
                    }catch (Exception e){
                        LatLng myPos = new LatLng(18.472414115434123, 73.93671275345763);

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPos));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(16f));
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions()
                                .visible(true)
                                .position(myPos)
                                .title("Your Vehical"));
                        Toast.makeText(MapsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }



//                        Toast.makeText(MapsActivity.this, latitude + "," + longitude, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }

        });

    }

}
