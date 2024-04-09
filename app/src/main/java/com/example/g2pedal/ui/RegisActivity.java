package com.example.g2pedal.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.g2pedal.DTO.UserDTO;
import com.example.g2pedal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://g2pedal-default-rtdb.firebaseio.com/");
        firebaseAuth = FirebaseAuth.getInstance();

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
                    checkExistingUser(getFullNameForm,getMailForm,getPhoneForm, getPasswordForm);
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
    public void checkExistingUser(String fullName, String mail, String phone, String password) {
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("mail").hasChild("mail:"+mail)) {
//                    Toast.makeText(RegisActivity.this, "mail đã có người dùng", Toast.LENGTH_SHORT).show();
//                }else if (snapshot.hasChild(("phone"))){
//                    Toast.makeText(RegisActivity.this, "SDT đã có người dùng", Toast.LENGTH_SHORT).show();
//
//                }else {
//                }
                UserDTO regisUser = new UserDTO(fullName, mail, phone, password);
                registerUser(regisUser);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void registerUser(UserDTO user) {
        firebaseAuth.createUserWithEmailAndPassword(user.getMail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisActivity.this, LoginActivity.class);
                            startActivity(intent);
                            pushInfoUser(user);
                        } else {
                            Toast.makeText(RegisActivity.this, "Email đã tồn tại, đổi tài khoản email khác " , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void pushInfoUser(UserDTO user){
        databaseReference.child("users").child(user.getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(RegisActivity.this, "Số điện thoại đã có người dùng", Toast.LENGTH_SHORT).show();
                } else {
                    String uid = firebaseAuth.getUid().toString();
                    databaseReference.child("users").child(uid).child("FullName").setValue(user.getFullName());
                    databaseReference.child("users").child(uid).child("Phone").setValue(user.getPhone());
                    databaseReference.child("users").child(uid).child("Mail").setValue(user.getMail());
                    databaseReference.child("users").child(uid).child("Password").setValue(user.getPassword());
                    Toast.makeText(RegisActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}