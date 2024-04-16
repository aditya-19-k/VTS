package com.example.vts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDeviceActivity extends AppCompatActivity {

    EditText edtVehicleName, edtVehicleNo, edtLatitude, edtLongitude, edtImageUrl, edtDeviceId;
    Button btnAddVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        edtVehicleName = findViewById(R.id.edtVehicleName);
        edtVehicleNo = findViewById(R.id.edtVehicleNo);
        edtLatitude = findViewById(R.id.edtLatitude);
        edtLongitude = findViewById(R.id.edtLongitude);
        edtImageUrl = findViewById(R.id.edtImageurl);
        btnAddVehicle = findViewById(R.id.btnAddVehicle);
        edtDeviceId = findViewById(R.id.edtDeviceId);

        String deviceId = edtDeviceId.getText().toString();

        // Get the SharedPreferences object
        SharedPreferences sharedPref = getSharedPreferences("usernameSP", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "abc");

        btnAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    // Write a message to the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users/"+username+"/Devices/");
                    myRef.child("d1").child("deviceName").setValue(edtVehicleName.getText().toString());
                    myRef.child("d1").child("deviceNum").setValue(edtVehicleNo.getText().toString());
                    myRef.child("d1").child("latitude").setValue(edtLatitude.getText().toString());
                    myRef.child("d1").child("longitude").setValue(edtLongitude.getText().toString());
                    myRef.child("d1").child("deviceImage").setValue(edtImageUrl.getText().toString());
                    Toast.makeText(AddDeviceActivity.this, "Device added Successfully!", Toast.LENGTH_SHORT).show();

                    edtVehicleName.setText("");
                    edtVehicleNo.setText("");
                    edtLatitude.setText("");
                    edtLongitude.setText("");
                    edtImageUrl.setText("");
                    edtDeviceId.setText("");
                }catch (Exception e){
                    Toast.makeText(AddDeviceActivity.this, "Unable to add Device", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}