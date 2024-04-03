package com.example.g2pedal.BottomNavBar.StorageNav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.g2pedal.R;

public class StorageActivity extends AppCompatActivity {
    private TextView categoryTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        categoryTextView = findViewById(R.id.category_text_view);

        // Lấy dữ liệu từ Intent
        String category = getIntent().getStringExtra("category");

        // Hiển thị dữ liệu
        categoryTextView.setText(category);
    }
}