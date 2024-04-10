package com.example.g2pedal.BottomNavBar.HomeNav.HomeFunc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.g2pedal.Adapter.SearchAdapter;
import com.example.g2pedal.Adapter.StorageDataAdapter;
import com.example.g2pedal.DTO.ProductDTO;
import com.example.g2pedal.Model.SearchModel;
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
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText txtSearch;
    private RecyclerView rv_search;
    private SearchAdapter searchAdapter;
    private ProgressBar progressBar;
    List<SearchModel> searchModelList = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        txtSearch = view.findViewById(R.id.txtSearch);
        progressBar = view.findViewById(R.id.progressBarSearch);
        rv_search = view.findViewById(R.id.rv_search_fragment);

        rv_search.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rv_search.setLayoutManager(layoutManager);

        searchAdapter = new SearchAdapter(getContext(), searchModelList);
        rv_search.setAdapter(searchAdapter);


        ImageButton btnBack = view.findViewById(R.id.btnSearchToHome);
        ImageButton btnSearch = view.findViewById(R.id.btnSearchProduct);
//        searchModelList.add(new SearchModel("product.getProductId()", "https://firebasestorage.googleapis.com/v0/b/g2pedal.appspot.com/o/images%2F1712718790070.png?alt=media&token=84730978-0e1a-415e-a52f-73e72b48dae0",
//                "product.getCategory()"," product.getName()", "product.getBrand()", "product.getColor()", "product.getQuantity() + ", "product.getPrice() ", "product.getStatus()"," product.getImportDate()"));


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryProduct(txtSearch.getText().toString());
            }
        });
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
//    private void queryProduct(String productName) {
//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products");
//        databaseRef.orderByChild("name")
//                .startAt(productName)
//                .endAt(productName + "\uf8ff")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        searchModelList.clear(); // Xóa dữ liệu cũ
//
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            // Chuyển đổi DataSnapshot thành đối tượng ProductDTO
//                            ProductDTO product = snapshot.getValue(ProductDTO.class);
//                            if (product != null && product.getCategory().equalsIgnoreCase(productName)) {
//                                searchModelList.add(new SearchModel(product.getProductId(), product.getImageUrl(), product.getCategory(), product.getName(), product.getBrand(), product.getColor(), product.getQuantity() + "", product.getPrice() + "", product.getStatus(), product.getImportDate()));
//                            }
//                        }
//
//                        // Cập nhật adapter và hiển thị dữ liệu
//                        searchAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.e("Firebase", "Error when reading data", databaseError.toException());
//                    }
//                });
//    }
    private void queryProduct(String searchText) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products");
        progressBar.setVisibility(View.VISIBLE);
        // Sửa lại: Sử dụng searchText để tìm kiếm sản phẩm theo tên
        databaseRef.orderByChild("name")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        searchModelList.clear(); // Xóa dữ liệu cũ

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Chuyển đổi DataSnapshot thành đối tượng ProductDTO
                            ProductDTO product = snapshot.getValue(ProductDTO.class);
                            // Sửa lại: Bỏ điều kiện so sánh với category
                            if (product != null) {
                                // Chú ý: Cần đảm bảo constructor của SearchModel phù hợp với các trường bạn muốn sử dụng
                                searchModelList.add(new SearchModel(product.getProductId(), product.getImageUrl(), product.getCategory(), product.getName(), product.getBrand(), product.getColor(), product.getQuantity() + "", product.getPrice() + "", product.getStatus(), product.getImportDate()));

                            }
                        }
                        progressBar.setVisibility(View.GONE);
                        // Cập nhật adapter và hiển thị dữ liệu
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Firebase", "Error when reading data", databaseError.toException());
                    }
                });
    }

}