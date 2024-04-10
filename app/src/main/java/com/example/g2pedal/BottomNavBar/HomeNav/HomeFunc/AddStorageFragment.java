package com.example.g2pedal.BottomNavBar.HomeNav.HomeFunc;

import static android.app.Activity.RESULT_OK;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.g2pedal.DTO.ProductDTO;
import com.example.g2pedal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddStorageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStorageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imgUri;
    private Bitmap bitmap;
    private Uri pathUri;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Button btnAdd;
    private ProgressBar progressBar;
    private EditText productNameEditText,productPriceEditText,productColorEditText,
            productCategoryEditText,productQtyEditText,productBrandEditText;

    ImageView productIMG;
    public AddStorageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddStorageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStorageFragment newInstance(String param1, String param2) {
        AddStorageFragment fragment = new AddStorageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_storage, container, false);

        productIMG = view.findViewById(R.id.productIMG);
        productNameEditText = view.findViewById(R.id.productNameEditText);
        productPriceEditText = view.findViewById(R.id.productPriceEditText);
        productColorEditText = view.findViewById(R.id.productColorEditText);
        productCategoryEditText = view.findViewById(R.id.productCategoryEditText);
        productQtyEditText = view.findViewById(R.id.productQtyEditText);
        productBrandEditText = view.findViewById(R.id.productBrandEditText);
        progressBar = view.findViewById(R.id.progressBar);

        btnAdd = view.findViewById(R.id.btnAddNewProduct);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        productIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        ImageButton btnBack = view.findViewById(R.id.btnAddStorageToHome);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại HomeFragment
                goBack();
            }
        });
        return view;
    }
    private void goBack(){
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }
    private void openImagePicker() {
        // Kiểm tra quyền truy cập bộ nhớ
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
        } else {
            // Mở thư viện ảnh
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImagePicker();
        } else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show(); // Sửa thành getActivity()
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pathUri = data.getData();
            productIMG.setImageURI(pathUri);
        }
    }

    private void uploadImage() {
        String name = productNameEditText.getText().toString();
        String brand = productBrandEditText.getText().toString();
        String category = productCategoryEditText.getText().toString();
        String color = productColorEditText.getText().toString();
        String priceText = productPriceEditText.getText().toString();
        String quantityText = productQtyEditText.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(brand) || TextUtils.isEmpty(category)
                || TextUtils.isEmpty(color) || TextUtils.isEmpty(priceText) || TextUtils.isEmpty(quantityText)) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pathUri != null) {
            progressBar.setVisibility(View.VISIBLE); // Hiển thị ProgressBar khi bắt đầu tải lên
            StorageReference fileRef = storage.getReference().child("images/" + System.currentTimeMillis() + ".png");
            fileRef.putFile(pathUri)
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressBar.setProgress((int) progress); // Cập nhật tiến độ của ProgressBar
                    })
                    .addOnSuccessListener(taskSnapshot -> {
                        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String photoUrl = uri.toString();

                            uploadProduct(photoUrl);
                            progressBar.setVisibility(View.INVISIBLE); // Ẩn ProgressBar sau khi tải lên thành công
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE); // Ẩn ProgressBar nếu tải lên thất bại
                    });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }



    private void uploadProduct(String photoUrl) {

        String name = productNameEditText.getText().toString();
        String brand = productBrandEditText.getText().toString();
        String category = productCategoryEditText.getText().toString();
        String color = productColorEditText.getText().toString();
        String priceText = productPriceEditText.getText().toString();
        String quantityText = productQtyEditText.getText().toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String importDate = sdf.format(new Date());

        ProductDTO product = new ProductDTO();
        product.setName(name);
        product.setBrand(brand);
        product.setCategory(category);
        product.setColor(color);
        product.setImportDate(importDate);

        String productId = "PROD_" + System.currentTimeMillis();
        product.setProductId(productId);

        // Thay đổi theo logic ứng dụng của bạn
        String status = (Integer.parseInt(quantityText) == 0) ? "Sold Out" : "In Stock";
        product.setStatus(status);

        // Chuyển đổi kiểu dữ liệu từ String sang Double và Integer
        try {
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);
            product.setPrice(price);
            product.setQuantity(quantity);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Lỗi định dạng số", Toast.LENGTH_SHORT).show();
            return;
        }

        product.setImageUrl(photoUrl);

        String documentName = "PROD_" + System.currentTimeMillis();
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products").child(documentName);

        product.setProductId(documentName);

// Up dữ liệu lên Realtime Database
        databaseRef.setValue(product)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi khi thêm sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//        String documentName = "PROD_" + System.currentTimeMillis();
//        DocumentReference documentRef = db.collection("products").document(documentName);
//
//        product.setProductId(documentName);
//
//        // Thêm tài liệu vào Firestore
//        documentRef.set(product)
//                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show())
//                .addOnFailureListener(e -> Toast.makeText(getContext(), "Lỗi khi thêm sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

}