package com.example.g2pedal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g2pedal.FirebaseHelper.FirebaseHelper;
import com.example.g2pedal.R;

public class RegisActivity extends AppCompatActivity {
    private FirebaseHelper firebaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        firebaseHelper = new FirebaseHelper(this);

        EditText fullName = findViewById(R.id.rgFullname);
        EditText phone = findViewById(R.id.rgPhone);
        EditText mail = findViewById(R.id.rgEmail);
        EditText password = findViewById(R.id.rgPassword);
        Button btnRegis = findViewById(R.id.btnRegisrg);
        Button btnBack = findViewById(R.id.btnBackLogin);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getFullNameForm = fullName.getText().toString();
                String getPhoneForm = phone.getText().toString();
                String getMailForm = mail.getText().toString();
                String getPasswordForm = password.getText().toString();

                if (getFullNameForm.isEmpty() || getPhoneForm.isEmpty() || getMailForm.isEmpty()
                        || getPasswordForm.isEmpty()) {
                    Toast.makeText(RegisActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseHelper.checkExistingUser(getFullNameForm,getMailForm,getPhoneForm, getPasswordForm);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}