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

        //Tham chiếu đến cơ sở dữ liệu qua đường link url
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://g2pedal-default-rtdb.firebaseio.com/");
        //khởi tạo 1 đối tượng singleton để xác thực các thông tin người dùng
        firebaseAuth = FirebaseAuth.getInstance();

        //ánh xạ
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
                //CheckNull EditText trước khi đăng ký tài khoản mới
                if (getFullNameForm.isEmpty() || getPhoneForm.isEmpty() || getMailForm.isEmpty()
                        || getPasswordForm.isEmpty()) {
                    Toast.makeText(RegisActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    //tạo đối tượng User để sử dụng trong hàm đăng ký người dùng
                    createUser(getFullNameForm,getMailForm,getPhoneForm, getPasswordForm);
                }
            }
        });
        //quay lại Activity Login
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisActivity.this, LoginActivity.class));
                finish();//kết thúc Activity đăng ký
            }
        });
    }

    public void createUser(String fullName, String mail, String phone, String password) {
        UserDTO regisUser = new UserDTO(fullName, mail, phone, password);
        registerUser(regisUser);
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
                            //đẩy dữ liệu vào realtime database khi đăng ký thành công
                            pushInfoUser(user);
                        } else {
                            Toast.makeText(RegisActivity.this, "Email đã tồn tại, đổi tài khoản email khác " , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    // hàm dữ liệu sau khi đăng ký lên realtime database
    private void pushInfoUser(UserDTO user){
        String uid = firebaseAuth.getUid();
        databaseReference.child("users").child(uid).child("FullName").setValue(user.getFullName());
        databaseReference.child("users").child(uid).child("Phone").setValue(user.getPhone());
        databaseReference.child("users").child(uid).child("Mail").setValue(user.getMail());
        databaseReference.child("users").child(uid).child("Password").setValue(user.getPassword());
        Toast.makeText(RegisActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
    }
}