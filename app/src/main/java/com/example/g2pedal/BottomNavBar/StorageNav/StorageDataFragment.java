package com.example.g2pedal.BottomNavBar.StorageNav;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.widget.TextView;

import com.example.g2pedal.Adapter.StorageDataAdapter;
import com.example.g2pedal.DatabaseHelper.GalleryHelper;
import com.example.g2pedal.Model.StorageDataModel;
import com.example.g2pedal.R;
import com.example.g2pedal.Repository.StorageDataRepository;

import android.content.ContentResolver;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    StorageDataRepository storageDataRepository;

    private static final int REQUEST_PERMISSION = 1;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_storage_data, container, false);
        txtCategory = view.findViewById(R.id.tvStorageData);
        rvStorageData = view.findViewById(R.id.rv_storage_data);
        ImageButton btnBack = view.findViewById(R.id.btnStorageDataToHome);


        String category = getArguments().getString("category");
        String storageLabel = "Kho chứa "+ category;
        txtCategory.setText(storageLabel);

        List<StorageDataModel> storageDataModelList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            } else {
                initData(category);

            }
        } else {
            initData(category);

        }

        StorageDataAdapter storageDataAdapter = new StorageDataAdapter(getContext(),this.storageDataRepository.getStorageDataModelList());
        rvStorageData.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2, RecyclerView.VERTICAL,false);
        rvStorageData.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.HORIZONTAL));
        rvStorageData.setLayoutManager(layoutManager);
        rvStorageData.setItemAnimator(new DefaultItemAnimator());
        rvStorageData.setAdapter(storageDataAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại HomeFragment
                goBack();
                storageDataRepository.delDB();
            }
        });

        return view;
    }
    private void goBack(){
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }
    public Uri getUri (int resId){
        return Uri.parse("android.resource://"  + getContext().getPackageName().toString() + "/" + resId);
    }
    private void initData(String category){
        ArrayList<StorageDataModel> alProduct = new ArrayList<>();
        List<Uri> imageUris = GalleryHelper.getAllImages(requireContext(),category);

        for (int i = 0; i < imageUris.size(); i++) {
            String id = Integer.toString(i);
            StorageDataModel p = new StorageDataModel(id, category + "_" + i, "", "");
            p.setDataIMG(imageUris.get(i));
            float price = Float.parseFloat(String.format("%.2f", new Random().nextFloat() * 10000));
            String strPrice = Float.toString(price);
            p.setPrice(strPrice);

            if (i % 3 != 1) {
                p.setStatus("Còn hàng");
            } else {
                p.setStatus("Hết hàng");
            }
            if (imageIsNull(p.getDataIMG())) {
                alProduct.add(p);
            }
        }
        this.storageDataRepository = new StorageDataRepository(alProduct);

    }
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    private boolean imageIsNull(Uri imgUri) {
        try{
        ContentResolver contentResolver = requireContext().getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(imgUri);
        Drawable drawable = Drawable.createFromStream(inputStream, imgUri.toString());

        // Nếu drawable không null, tức là imageUri đại diện cho một hình ảnh hợp lệ
        if (drawable != null) {
            return true;
        }
        }
         catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}