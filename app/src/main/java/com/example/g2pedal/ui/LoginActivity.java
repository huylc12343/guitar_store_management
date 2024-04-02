package com.example.g2pedal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.g2pedal.MainActivity;
import com.example.g2pedal.R;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnDangNhapDN);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptAccess();
            }
        });
    }
    private void acceptAccess(){
        Intent iMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivities(new Intent[]{iMain});
    }
}