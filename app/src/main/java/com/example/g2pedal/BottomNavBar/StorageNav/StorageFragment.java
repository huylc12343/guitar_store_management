package com.example.g2pedal.BottomNavBar.StorageNav;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.g2pedal.MainActivity;
import com.example.g2pedal.R;
import com.example.g2pedal.ui.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StorageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StorageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView backbtn;

    public StorageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StorageFragment newInstance(String param1, String param2) {
        StorageFragment fragment = new StorageFragment();
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
//        backbtn = (ImageView) backbtn.findViewById(R.id.back_press);
//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                requireActivity().getSupportFragmentManager().popBackStack();
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage, container, false);

        Button guitarLayout = view.findViewById(R.id.btnGuitarCategory);
        Button amplifierLayout = view.findViewById(R.id.btnAmpCategory);
        Button pedalLayout = view.findViewById(R.id.btnPedalCategory);
        Button otherLayout = view.findViewById(R.id.btnOtherCategory);

        guitarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StorageActivity.class);
                intent.putExtra("category", "Guitar");
                startActivity(intent);

            }
        });

        amplifierLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StorageActivity.class);
                intent.putExtra("category", "Amplifier");
                startActivity(intent);
            }
        });

        pedalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StorageActivity.class);
                intent.putExtra("category", "Pedal");
                startActivity(intent);
            }
        });

        otherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StorageActivity.class);
                intent.putExtra("category", "Other");
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_storage, container, false);
        return view;
    }
}