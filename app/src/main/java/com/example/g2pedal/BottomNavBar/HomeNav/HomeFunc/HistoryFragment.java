package com.example.g2pedal.BottomNavBar.HomeNav.HomeFunc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.g2pedal.Adapter.HistoryAdapter;
import com.example.g2pedal.R;
import com.example.g2pedal.Model.HistoryModel;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<HistoryModel> historyModels = new ArrayList<>();

    private RecyclerView rvStorageData;
    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Khởi tạo RecyclerView
        rvStorageData = view.findViewById(R.id.rv_history);
        rvStorageData.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        rvStorageData.setLayoutManager(layoutManager);
        HistoryAdapter adapter = new HistoryAdapter(getContext(), historyModels);
        rvStorageData.setAdapter(adapter);

        //tham chiếu vào bill trong realtime database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bill");

        //sắp xếp các bill theo thuộc tính "date"
        databaseReference.orderByChild("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot billSnapshot : dataSnapshot.getChildren()) {
                    String billId = billSnapshot.child("billId").getValue(String.class);
                    String date = billSnapshot.child("date").getValue(String.class);
                    Double totalPay = billSnapshot.child("totalPay").getValue(Double.class);
                    //tham chiếu vào trong productIds để lấy id sản phẩm đã bán
                    List<String> productIds = new ArrayList<>();
                    for (DataSnapshot productSnapshot : billSnapshot.child("productIds").getChildren()) {
                        String productId = productSnapshot.getValue(String.class);
                        productIds.add(productId);
                    }
                    HistoryModel historyModel = new HistoryModel(billId, productIds, totalPay, date);
                    historyModels.add(historyModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ImageButton btnBack = view.findViewById(R.id.btnHistoryToHome);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        return view;
    }
    private void goBack(){
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }
}