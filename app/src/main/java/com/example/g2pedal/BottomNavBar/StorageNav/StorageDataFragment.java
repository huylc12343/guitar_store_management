package com.example.g2pedal.BottomNavBar.StorageNav;



import android.os.Bundle;

import com.example.g2pedal.DTO.ProductDTO;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.g2pedal.Adapter.StorageDataAdapter;
import com.example.g2pedal.Model.StorageDataModel;
import com.example.g2pedal.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StorageDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StorageDataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txtCategory;

    private RecyclerView rvStorageData;



    private String category;
    private TextView tvStorageData;

    private StorageDataAdapter storageDataAdapter;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products");
    List<StorageDataModel> productModelList = new ArrayList<>();


    public StorageDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StorageDataFragment newInstance(String param1, String param2) {
        StorageDataFragment fragment = new StorageDataFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage_data, container, false);

        tvStorageData = view.findViewById(R.id.tvStorageData);
        txtCategory = view.findViewById(R.id.tvStorageData);
        rvStorageData = view.findViewById(R.id.rv_storage_data);
        ImageButton btnBack = view.findViewById(R.id.btnStorageDataToHome);

        //thiết lập cho RecyclerView
        rvStorageData.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvStorageData.setLayoutManager(layoutManager);

        //khai báo adapter để truyền mảng chứa kiểu dữ liệu Model vào trong adapter
        storageDataAdapter = new StorageDataAdapter(getContext(), productModelList);
        rvStorageData.setAdapter(storageDataAdapter);

        //lấy category từ bundle
        Bundle bundle = getArguments();
        String data = bundle.getString("category");

        category = data;
        tvStorageData.setText(data);
        queryProduct();


        btnBack.setOnClickListener(v -> goBack());

        return view;
    }

    private void goBack(){
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }

    private void queryProduct(){
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productModelList.clear(); //xóa dữ liệu cũ

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //chuyển đổi DataSnapshot thành đối tượng ProductDTO
                    ProductDTO product = snapshot.getValue(ProductDTO.class);
                    //hàm if lọc dữ liệu theo category, nếu dữ liệu có trường category như
                    //yêu cầu thì add vào modelList đưa lên recyclerView
                    if (product != null && product.getCategory().equalsIgnoreCase(category)) {
                        productModelList.add(new StorageDataModel(product.getProductId(), product.getImageUrl(),
                                product.getName(), product.getCategory(),
                                String.valueOf(product.getPrice()), product.getStatus()));
                    }
                }

                //cập nhật adapter và hiển thị dữ liệu
                storageDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error when reading data", databaseError.toException());
            }
        });
    }

}