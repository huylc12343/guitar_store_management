package com.example.g2pedal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.g2pedal.MainActivity;
import com.example.g2pedal.R;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tvRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnDangNhapDN);
        tvRegis = (TextView)findViewById(R.id.dangky);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptAccess();
            }
        });
        tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisActivity();
            }
        });
    }
    private void acceptAccess(){
        Intent iMain = new Intent(LoginActivity.this, MainActivity.class);
        startActivities(new Intent[]{iMain});
    }
    private void openRegisActivity(){
        Intent iRegis = new Intent(LoginActivity.this, RegisActivity.class);
        startActivities(new Intent[]{iRegis});
    }
}