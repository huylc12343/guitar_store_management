package com.example.g2pedal.FirebaseHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.g2pedal.BottomNavBar.UserNav.UserFragment;
import com.example.g2pedal.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFirebaseHelper {
    private DatabaseReference databaseReference;
    private Context context;

    public UserFirebaseHelper(Context context) {
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://g2pedal-default-rtdb.firebaseio.com/");
    }

    public void checkExistingUser(String fullName, String mail, String phone,  String password) {
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(phone)) {
                    Toast.makeText(context, "Số điện thoại đã có người dùng", Toast.LENGTH_SHORT).show();
                } else {
                    registerData(fullName, phone, mail, password);
                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void registerData(String fullName, String phone, String mail, String password) {
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(phone)) {
                    Toast.makeText(context, "Số điện thoại đã có người dùng", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").child(phone).child("fullname").setValue(fullName);
                    databaseReference.child("users").child(phone).child("mail").setValue(mail);
                    databaseReference.child("users").child(phone).child("password").setValue(password);
                    Toast.makeText(context, "Đăng ký thành công ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void login(String phoneNumber, String password) {
        retrieveUserData(phoneNumber, new UserDataListener() {
            @Override
            public void onUserDataLoaded(String fullName, String email, String username, String retrievedPassword) {
                if (retrievedPassword != null && retrievedPassword.equals(password)) {
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("phone", phoneNumber);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else {
                    Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void retrieveUserData(String phone, UserDataListener listener) {
        databaseReference.child("users").child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fullName = snapshot.child("fullname").getValue(String.class);
                    String email = snapshot.child("mail").getValue(String.class);
                    String username = snapshot.child("username").getValue(String.class);
                    String password = snapshot.child("password").getValue(String.class);

                    listener.onUserDataLoaded(fullName, email, username, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onCancelled(error);
            }
        });
    }

    public interface UserDataListener {
        void onUserDataLoaded(String fullName, String email, String username, String password);
        void onCancelled(DatabaseError error);
    }
}