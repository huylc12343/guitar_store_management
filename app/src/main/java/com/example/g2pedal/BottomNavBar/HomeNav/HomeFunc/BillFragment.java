package com.example.g2pedal.BottomNavBar.HomeNav.HomeFunc;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.g2pedal.Adapter.BillAdapter;
import com.example.g2pedal.Model.BillModel;
import com.example.g2pedal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rvMyBill;


    public BillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillFragment newInstance(String param1, String param2) {
        BillFragment fragment = new BillFragment();
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
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        rvMyBill = view.findViewById(R.id.rv_bill);
        List<BillModel> billModelList = new ArrayList<>();
        billModelList.add(new BillModel("0",getUri(R.drawable.guitar_1),"Guitar","Fender Statocaster","Fender","Black","1","17.000.000VND"));
        billModelList.add(new BillModel("1",getUri(R.drawable.guitar_2),"Guitar","Fender Statocaster","Fender","BabyBlue","1","17.000.000VND"));

        BillAdapter billAdapter = new BillAdapter(getContext(), billModelList);
        rvMyBill.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1, RecyclerView.VERTICAL,false);
        rvMyBill.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));
        rvMyBill.setLayoutManager(layoutManager);
        rvMyBill.setItemAnimator(new DefaultItemAnimator());
        rvMyBill.setAdapter(billAdapter);

        ImageButton btnBack = view.findViewById(R.id.btnBillToHome);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại HomeFragment
                goBack();
            }
        });

        return view;
        // Inflate the layout for this fragment
    }
    private void goBack(){
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }
    public Uri getUri (int resId){
        return Uri.parse("android.resource://"  + getContext().getPackageName().toString() + "/" + resId);
    }
}