package com.example.vts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DevicesActivity extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView recyclerView;
    DeviceAdapter deviceAdapter;
    DatabaseReference databaseReference;
    CardView cardView;
    SearchView searchView;
    Button btnAddDevice;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        btnAddDevice = findViewById(R.id.btnAddDevice);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();


        try {
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }

//        String user = String.valueOf(FirebaseDatabase.getInstance().getReference().child("users").getParent());
//        Toast.makeText(this, ""+user, Toast.LENGTH_LONG).show();


        // Get the SharedPreferences object
        SharedPreferences sharedPref = getSharedPreferences("usernameSP", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "abc");

        databaseReference = FirebaseDatabase.getInstance().getReference("users/"+username+"/Devices");
        FirebaseRecyclerOptions<DeviceModel> options =
                new FirebaseRecyclerOptions.Builder<DeviceModel>()
                        .setQuery(databaseReference, DeviceModel.class)
                        .build();

        deviceAdapter = new DeviceAdapter(options,this);
        recyclerView.setAdapter(deviceAdapter);

        
        //Add device
        btnAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DevicesActivity.this,AddDeviceActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override protected void onStart()
    {
        super.onStart();
        deviceAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        deviceAdapter.stopListening();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(DevicesActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}