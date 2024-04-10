package com.example.g2pedal.BottomNavBar.HomeNav.HomeFunc;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g2pedal.Adapter.BillAdapter;
import com.example.g2pedal.DTO.BillDTO;
import com.example.g2pedal.DTO.ProductDTO;
import com.example.g2pedal.Model.BillModel;
import com.example.g2pedal.Model.SearchModel;
import com.example.g2pedal.Model.StorageDataModel;
import com.example.g2pedal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private Button btn_pay;
    private RecyclerView rvMyBill;
    private TextView txt_pay;
    private List<StorageDataModel> billModelList = new ArrayList<>();
    private BillAdapter billAdapter;
    BillModel billModel = BillModel.getInstance();
    List<String> billItemsId = billModel.getBillItems();

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

        btn_pay = view.findViewById(R.id.btn_pay);
        rvMyBill = view.findViewById(R.id.rv_bill);
        txt_pay = view.findViewById(R.id.totalPay);
        updateTotalPay(billModel.getPay());
        queryProduct();

        billAdapter = new BillAdapter(getContext(), billModelList,this);
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

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completePayment();
            }
        });
        return view;
        // Inflate the layout for this fragment
    }
    private void goBack(){
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }

    private void queryProduct() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("products");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                billModelList.clear(); // Xóa dữ liệu cũ

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Chuyển đổi DataSnapshot thành đối tượng ProductDTO
                    ProductDTO product = snapshot.getValue(ProductDTO.class);
                    if (product != null) {
                        // Kiểm tra xem product có trùng id với billItemsId không
                        if (billItemsId.contains(product.getProductId())) {
                            billModelList.add(new StorageDataModel(product.getProductId(),
                                    product.getImageUrl(), product.getName(), product.getCategory(),
                                    String.valueOf(product.getPrice()), product.getStatus()));
                        }
                    }
                }

                // Cập nhật adapter và hiển thị dữ liệu
                billAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error when reading data", databaseError.toException());
            }
        });
    }
    private void completePayment() {
        // Tạo thông tin đơn hàng
        String orderId = "BILL_" + System.currentTimeMillis();
        List<String> productIds = new ArrayList<>();
        double totalPay = billModel.getPay();

        // Lấy danh sách id sản phẩm từ billModel
        for (StorageDataModel item : billModelList) {
            productIds.add(item.getId());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        // Tạo đối tượng Order
        BillDTO bill = new BillDTO(orderId, productIds, totalPay, currentDate);

        // Thực hiện cập nhật lên Firebase Realtime Database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.child("bill").child(orderId).setValue(bill);

        // Cập nhật quantity và status của sản phẩm trong giỏ hàng
        for (String productId : billItemsId) {
            databaseRef.child("products").child(productId).child("quantity").setValue(0);
            databaseRef.child("products").child(productId).child("status").setValue("Sold Out");
        }

        // Xóa giỏ hàng sau khi thanh toán
        billModel.clearBill();

        // Hiển thị thông báo hoàn thành thanh toán
        Toast.makeText(getContext(), "Đã thanh toán thành công!", Toast.LENGTH_SHORT).show();
        billModelList.clear();
        txt_pay.setText("Tổng hóa đơn: 0.0 Dollar");
        // Refresh danh sách sản phẩm trong giỏ hàng
        billAdapter.notifyDataSetChanged();
    }
    public void updateTotalPay(double totalPay) {
        String totalText = "Tổng hóa đơn: " + totalPay + " Dollar";
        txt_pay.setText(totalText);
    }

}