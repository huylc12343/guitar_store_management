package com.example.g2pedal.FirebaseHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.g2pedal.BottomNavBar.UserNav.UserFragment;
import com.example.g2pedal.DTO.UserDTO;
import com.example.g2pedal.MainActivity;
import com.example.g2pedal.ui.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class UserFirebaseHelper {
    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private Context context;


    public UserFirebaseHelper(Context context) {
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://g2pedal-default-rtdb.firebaseio.com/");
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void checkExistingUser(String fullName, String mail, String phone,  String password) {
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.hasChild("mail")) {
//                    Toast.makeText(context, "mail đã có người dùng", Toast.LENGTH_SHORT).show();
//                }else if (snapshot.hasChild(("phone"))){
//                    Toast.makeText(context, "SDT đã có người dùng", Toast.LENGTH_SHORT).show();
//
//                }else {
//                }
                UserDTO regisUser = new UserDTO(fullName, phone, mail, password);
                registerData(regisUser);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void pushInfoUser(UserDTO user){
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(context, "Số điện thoại đã có người dùng", Toast.LENGTH_SHORT).show();
                } else {
                    String uid = firebaseAuth.getUid().toString();
                    databaseReference.child("users").child(uid).child("FullName").setValue(user.getFullName());
                    databaseReference.child("users").child(uid).child("Phone").setValue(user.getPhone());
                    databaseReference.child("users").child(uid).child("Mail").setValue(user.getMail());
                    databaseReference.child("users").child(uid).child("Password").setValue(user.getPassword());
                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void registerData(UserDTO user) {
        firebaseAuth.createUserWithEmailAndPassword(user.getMail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser userAuth = firebaseAuth.getCurrentUser();
                            pushInfoUser(user);
                        } else {


                        }
                    }
                });

    }

    public void login(String email, String password) {

    }

    public interface UserDataListener {
        void onUserDataLoaded(String fullName, String email, String username, String password);
        void onCancelled(DatabaseError error);
    }
}