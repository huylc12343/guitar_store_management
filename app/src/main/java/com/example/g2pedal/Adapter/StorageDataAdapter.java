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

import com.example.g2pedal.Model.BillModel;
import com.example.g2pedal.Model.StorageDataModel;
import com.example.g2pedal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StorageDataAdapter extends RecyclerView.Adapter<StorageDataAdapter.ViewHolder> {
    private Context context;
    private BillModel bill = BillModel.getInstance();
    private List<StorageDataModel> storageDataModels;

    public StorageDataAdapter(Context context, List<StorageDataModel> storageDataModels) {
        this.context = context;
        this.storageDataModels = storageDataModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_storage_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StorageDataModel storageDataModel = storageDataModels.get(position);

        Picasso.get().load(storageDataModel.getDataIMG()).into(holder.storageDataIMG);

        holder.storageDataTittle.setText(storageDataModel.getTittle());
        holder.storageDataPrice.setText(storageDataModel.getPrice() + " Dollar");
        holder.storageDataId.setText("ID: " + storageDataModel.getId());
        holder.storageDataStatus.setText(storageDataModel.getStatus());

        if (storageDataModel.getStatus().equals("Sold Out")) {
            holder.storageDataStatus.setBackgroundResource(R.drawable.bg_status_sold);
        } else {
            holder.storageDataStatus.setBackgroundResource(R.drawable.bg_status_aval);
        }
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(storageDataModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storageDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView storageDataIMG,iv_add;

        private TextView storageDataTittle, storageDataPrice, storageDataId, storageDataStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storageDataIMG = itemView.findViewById(R.id.iv_storageDataIMG);
            storageDataTittle = itemView.findViewById(R.id.txt_storageDataTittle);
            storageDataPrice = itemView.findViewById(R.id.txt_storageDataPrice);
            storageDataId = itemView.findViewById(R.id.txt_storageDataId);
            storageDataStatus = itemView.findViewById(R.id.txt_storageDataStatus);
            iv_add =  itemView.findViewById(R.id.iv_Add);
        }
    }
    private void add(StorageDataModel product){
        String id = product.getId();
        if(bill.getBillItems().contains(id)){
            Toast.makeText(context, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
        } else if (product.getStatus().equals("Sold Out")) {
            Toast.makeText(context, "Sản phẩm tạm hết hàng", Toast.LENGTH_SHORT).show();
        } else{
            bill.getInstance().addToBill(product);
            Toast.makeText(context, "Đã thêm sảm phẩm " + product.getTittle()+" vào giỏ hàng!!", Toast.LENGTH_SHORT).show();
        }
    }

}