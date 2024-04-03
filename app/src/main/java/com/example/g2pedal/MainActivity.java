package com.example.g2pedal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.g2pedal.BottomNavBar.HomeNav.HomeFragment;
import com.example.g2pedal.BottomNavBar.StorageNav.StorageFragment;
import com.example.g2pedal.BottomNavBar.UserNav.UserFragment;
import com.example.g2pedal.databinding.ActivityMainBinding;
import com.example.g2pedal.ui.NotificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActivityMainBinding binding;
    ImageButton notiBtn;
    TextView txtGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        String greetingText = "Xin chào "+"getName()";
        txtGreeting = (TextView)findViewById(R.id.helloText);
        txtGreeting.setText(greetingText);
        bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.homeNav) {
                    replaceFragment(new HomeFragment());
                } else if (id == R.id.storageNav) {
                    replaceFragment(new StorageFragment());
                } else if (id == R.id.userNav) {
                    replaceFragment(new UserFragment());
                }
                return true;
            }
        });

        notiBtn = binding.notificationButton;
        notiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotiActivity();
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
    private void openNotiActivity(){
        Intent iNoti = new Intent(MainActivity.this, NotificationActivity.class);
        startActivities(new Intent[]{iNoti});
    }
}