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

    public void getUserData(String phone, final OnUserDataLoadedListener listener) {
        DatabaseReference userRef = databaseReference.child(phone);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy dữ liệu từ snapshot và tạo đối tượng UserDTO
                    String fullName = snapshot.child("fullname").getValue(String.class);
                    String email = snapshot.child("mail").getValue(String.class);
                    String password = snapshot.child("password").getValue(String.class);

                    UserDTO user = new UserDTO(fullName, email, phone, password);

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