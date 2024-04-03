package com.example.g2pedal.BottomNavBar.HomeNav;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.g2pedal.BottomNavBar.StorageNav.StorageActivity;
import com.example.g2pedal.MainActivity;
import com.example.g2pedal.R;
import com.example.g2pedal.ui.AddStorageActivity;
import com.example.g2pedal.ui.BillActivity;
import com.example.g2pedal.ui.HistoryActivity;
import com.example.g2pedal.ui.LoginActivity;
import com.example.g2pedal.ui.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btnSearch,btnBill,btnAddStorage,btnHistory;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnBill = view.findViewById(R.id.btnBill);
        btnAddStorage = view.findViewById(R.id.btnAddStorage);
        btnHistory = view.findViewById(R.id.btnHistory);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBillActivity();
            }
        });
        btnAddStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddStorageActivity();
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHistoryActivity();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private void openSearchActivity(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }
    private void openBillActivity(){
        Intent intent = new Intent(getActivity(), BillActivity.class);
        startActivity(intent);
    }
    private void openAddStorageActivity(){
        Intent intent = new Intent(getActivity(), AddStorageActivity.class);
        startActivity(intent);
    }
    private void openHistoryActivity(){
        Intent intent = new Intent(getActivity(), HistoryActivity.class);
        startActivity(intent);
    }
}