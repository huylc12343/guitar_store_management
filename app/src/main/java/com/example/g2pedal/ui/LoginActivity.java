package com.example.g2pedal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g2pedal.FirebaseHelper.FirebaseHelper;
import com.example.g2pedal.MainActivity;
import com.example.g2pedal.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseHelper firebaseHelper;

    Button btnLogin;
    TextView tvRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseHelper = new FirebaseHelper(this);

        EditText txtUsername = findViewById(R.id.lgUsername);
        EditText txtPassword = findViewById(R.id.lgPassword);
        btnLogin = (Button) findViewById(R.id.btnLoginlg);
        tvRegis = (TextView)findViewById(R.id.btnRegislg);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPhoneTxt = txtUsername.getText().toString();
                String getPasswordTxt = txtPassword.getText().toString();

                if (getPhoneTxt.isEmpty() || getPasswordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Nhập username hoặc password", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseHelper.login(getPhoneTxt,getPasswordTxt);
                }
            }
        });
        tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisActivity.class));
                finish();
            }
        });
    }
}