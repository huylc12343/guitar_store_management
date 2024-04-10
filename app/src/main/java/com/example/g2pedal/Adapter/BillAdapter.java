package com.example.g2pedal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.g2pedal.BottomNavBar.HomeNav.HomeFunc.BillFragment;
import com.example.g2pedal.Model.BillModel;
import com.example.g2pedal.Model.SearchModel;
import com.example.g2pedal.Model.StorageDataModel;
import com.example.g2pedal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.viewHolder> {
    private BillFragment billFragment;
    private Context context;
    private BillModel bill = BillModel.getInstance();

    private List<StorageDataModel> productLists;

    public BillAdapter(Context context, List<StorageDataModel> productLists) {
        this.context = context;
        this.productLists = productLists;
    }
    public BillAdapter(Context context, List<StorageDataModel> productLists, BillFragment billFragment) {
        this.context = context;
        this.productLists = productLists;
        this.billFragment = billFragment;
    }

    @NonNull
    @Override
    public BillAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill,parent,false);
        return new BillAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.viewHolder holder, int position) {

        StorageDataModel productList = productLists.get(position);


        Picasso.get().load(productList.getDataIMG()).into(holder.billIMG);

        holder.billId.setText("ID: "+productList.getId());
        holder.billTittle.setText("Name: "+productList.getTittle());
        holder.billPrice.setText("Giá: "+productList.getPrice()+" Dollar");

        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del(productList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView billIMG, iv_del;
        private TextView billId,billTittle,billCategory,billBrand,billColor,billQty,billPrice;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            billIMG=itemView.findViewById(R.id.iv_billIMG);
            billId = itemView.findViewById(R.id.txt_billProductId);
            billTittle = itemView.findViewById(R.id.txt_billProductName);
            billPrice=itemView.findViewById(R.id.txt_billProductPrice);
            iv_del = itemView.findViewById(R.id.iv_del);
        }
    }
    private void del(StorageDataModel product){
        int position = productLists.indexOf(product);
        if (position != -1) {
            bill.getInstance().removeFromBill(product,position);
            productLists.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
            Toast.makeText(context, "Đã xóa sản phẩm " + product.getTittle()+" khỏi giỏ hàng!!", Toast.LENGTH_SHORT).show();
            billFragment.updateTotalPay(bill.getPay());
        }

    }


}
