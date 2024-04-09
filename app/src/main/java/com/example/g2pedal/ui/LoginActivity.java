package com.example.g2pedal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g2pedal.FirebaseHelper.UserFirebaseHelper;
import com.example.g2pedal.MainActivity;
import com.example.g2pedal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvRegis;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://g2pedal-default-rtdb.firebaseio.com/");
        firebaseAuth = FirebaseAuth.getInstance();


        EditText txtUsername = findViewById(R.id.lgUsername);
        EditText txtPassword = findViewById(R.id.lgPassword);
        btnLogin = (Button) findViewById(R.id.btnLoginlg);
        tvRegis = (TextView)findViewById(R.id.btnRegislg);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getMailTxt = txtUsername.getText().toString();
                String getPasswordTxt = txtPassword.getText().toString();

                if (getMailTxt.isEmpty() || getPasswordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Nhập username hoặc password", Toast.LENGTH_SHORT).show();
                } else {
                    login(getMailTxt,getPasswordTxt);
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
    private void login(String email, String password){
        if (firebaseAuth != null) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                String uid = user.getUid(); // Lấy UID của người dùng
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("uid", uid); // Truyền UID qua Intent
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}