package com.example.g2pedal.DAO;

import com.example.g2pedal.DTO.UserDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDAO {
    private DatabaseReference databaseReference;

    public UserDAO() {
        // Tham chiếu đến "users" trong cơ sở dữ liệu Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void getUserData(String uid, final OnUserDataLoadedListener listener) {
        DatabaseReference userRef = databaseReference.child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy dữ liệu từ snapshot và tạo đối tượng UserDTO
                    String fullName = snapshot.child("FullName").getValue(String.class);
                    String email = snapshot.child("Mail").getValue(String.class);
                    String password = snapshot.child("Password").getValue(String.class);
                    String phone = snapshot.child("Phone").getValue(String.class);
                    UserDTO user = new UserDTO(fullName, email, phone, password);
                    user.setUid(uid);
                    listener.onUserDataLoaded(user);
                } else {
                    // Người dùng không tồn tại trong cơ sở dữ liệu
                    listener.onUserNotFound();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Xử lý lỗi
                listener.onDataLoadFailed(error.getMessage());
            }
        });
    }

    public interface OnUserDataLoadedListener {
        void onUserDataLoaded(UserDTO user);
        void onUserNotFound();
        void onDataLoadFailed(String errorMessage);
    }
}