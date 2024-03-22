package com.example.vts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{

//    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    DrawerLayout drawerLayout;
    ImageButton btnDrawerToggle;
    NavigationView navigationView;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

//        Declare
        sharedPreferences = this.getSharedPreferences("login",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        drawerLayout = findViewById(R.id.drawerLayout);
        btnDrawerToggle = findViewById(R.id.btnDrawerToggle);
        navigationView = findViewById(R.id.navigationView);


        // Open navigation drawer by clicking on Menu icon
        btnDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });
        // Read & Write to HeaderView
        View headerView = navigationView.getHeaderView(0);
        TextView tvEmailDH = headerView.findViewById(R.id.tvEmailDH);
        TextView tvNameDH = headerView.findViewById(R.id.tvNameDH);
        ImageView userIcon = headerView.findViewById(R.id.userIcon);

        try {
            mAuth = FirebaseAuth.getInstance();
            String email = mAuth.getCurrentUser().getEmail();
            tvEmailDH.setText(email);
            tvNameDH.setText("");


//            String name = reference.getPath().toString();toString
//            tvNameDH.setText(name);

//            Glide.with(userIcon.getContext())
//                    .load("https://www.zmo.ai/wp-content/uploads/2023/11/Sea-girl-created-by-ZMO.webp")
//                    .placeholder(R.drawable.person)
//                    .fitCenter()
//                    .circleCrop()
//                    .error(R.drawable.error_image)
//                    .into(userIcon);
        }
        catch (Exception e){
        }

        Intent intent = new Intent();
        String name = intent.getStringExtra("nameKey");
        if (name != null) {

            tvNameDH.setText(name);
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();

        }

        // Perform action by selection navigation item
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navMenu){
                    drawerLayout.close();
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.navContact){
                    drawerLayout.close();
                    Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "About us", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.navShare){
                    drawerLayout.close();
                    Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.navDevices){
                    drawerLayout.close();
                    Intent intent = new Intent(MainActivity.this,DevicesActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.navLogout){
                    drawerLayout.close();
                    Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                    logout();
                }
                return false;
            }
        });

    }

    private void getData() {

        databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    String latitude = (String) dataSnapshot.child("latitude").getValue();
                    String longitude = (String) dataSnapshot.getChildren().toString();
                    String username = (String) dataSnapshot.child("users").child("Aditya ").child("name").getValue();
                    Toast.makeText(MainActivity.this,  username, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Failed to read value.", databaseError.toException());
            }

        });

    }

    // logout method
    private void logout() {
        editor.putString("isLogin", "false");
        editor.commit();

        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}