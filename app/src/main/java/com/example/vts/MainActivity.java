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

import com.example.vts.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

//    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    DrawerLayout drawerLayout;
    ImageButton btnDrawerToggle;
    NavigationView navigationView;
    FirebaseAuth mAuth;
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
            String name = mAuth.getCurrentUser().getDisplayName();
            tvNameDH.setText(name);
        }
        catch (Exception e){
        }

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, tvEmailDH.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        // Perform action by selection navigation item
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navMenu){
                    drawerLayout.close();
                    Toast.makeText(MainActivity.this, "This is Menu", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.navContact){
                    drawerLayout.close();
                    Toast.makeText(MainActivity.this, "This is Contact", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.navShare){
                    drawerLayout.close();
                    Toast.makeText(MainActivity.this, "This is Share", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.navDevices){
                    drawerLayout.close();
                    Toast.makeText(MainActivity.this, "This is Devices", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.navLogout){
                    drawerLayout.close();
                    Toast.makeText(MainActivity.this, "This is Logout", Toast.LENGTH_SHORT).show();
                    logout();
                }
                return false;
            }
        });

    }

    // logout methon
    private void logout() {
        editor.putString("isLogin", "false");
        editor.commit();

        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}