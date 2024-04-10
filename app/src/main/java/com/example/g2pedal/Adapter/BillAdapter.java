package com.example.g2pedal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.g2pedal.Model.BillModel;
import com.example.g2pedal.R;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.viewHolder> {
    private Context context;
    private List<BillModel> billModels;

    public BillAdapter(Context context, List<BillModel> billModels) {
        this.context = context;
        this.billModels = billModels;
    }

    @NonNull
    @Override
    public BillAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill,parent,false);
        return new BillAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.viewHolder holder, int position) {
        final BillModel billModel = billModels.get(position);
        holder.billId.setText(""+billModel.getId());
        holder.billTittle.setText(""+billModel.getTittle());
        holder.billCategory.setText("Loại sản phẩm "+billModel.getCategory());
//        holder.billBrand.setText("Hãng: "+billModel.getBrand());
//        holder.billColor.setText("Màu: "+billModel.getColor());
        holder.billQty.setText("Số lượng: "+billModel.getQuantity());
        holder.billPrice.setText("Giá: "+billModel.getPrice());
        holder.billIMG.setImageURI(billModel.getProductIMG());


    }

    @Override
    public int getItemCount() {
        return billModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView billIMG;
        private TextView billId,billTittle,billCategory,billBrand,billColor,billQty,billPrice;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            billIMG=itemView.findViewById(R.id.iv_billIMG);
            billId = itemView.findViewById(R.id.txt_billId);
            billTittle=itemView.findViewById(R.id.txt_billTittle);
            billCategory=itemView.findViewById(R.id.txt_billCategory);
//            billBrand=itemView.findViewById(R.id.txt_billBrand);
//            billColor=itemView.findViewById(R.id.txt_billColor);
            billQty=itemView.findViewById(R.id.txt_billQty);
            billPrice=itemView.findViewById(R.id.txt_billPrice);

        }
    }
}
