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
import com.example.g2pedal.DAO.UserDAO;
import com.example.g2pedal.DTO.UserDTO;
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

        bottomNavigationView = binding.bottomNavigationView;

        UserDAO userDAO = new UserDAO();
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        userDAO.getUserData(uid, new UserDAO.OnUserDataLoadedListener()  {

            @Override
            public void onUserDataLoaded(UserDTO user) {
                String greetingText = "Xin chào " + user.getFullName();
                txtGreeting = (TextView)findViewById(R.id.helloText);
                txtGreeting.setText(greetingText);
            }

            @Override
            public void onUserNotFound() {
                // Người dùng không tồn tại trong cơ sở dữ liệu
            }

            @Override
            public void onDataLoadFailed(String errorMessage) {
                // Xử lý khi có lỗi xảy ra trong quá trình tải dữ liệu
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.homeNav) {
                    replaceFragment(new HomeFragment());
                } else if (id == R.id.storageNav) {
                    replaceFragment(new StorageFragment());
                } else if (id == R.id.userNav) {
                    Intent intent = getIntent();
                    String toFragPhone = intent.getStringExtra("uid");
                    UserFragment fragment = new UserFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", toFragPhone);
                    fragment.setArguments(bundle);
                    replaceFragment(fragment);
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