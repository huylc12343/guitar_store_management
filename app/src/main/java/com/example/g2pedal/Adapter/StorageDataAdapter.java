package com.example.g2pedal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g2pedal.Model.StorageDataModel;
import com.example.g2pedal.R;

import java.util.List;

public class StorageDataAdapter extends RecyclerView.Adapter<StorageDataAdapter.viewHolder> {
    Context context;
    private List<StorageDataModel> storageDataModels;
    private TextView txtStatus;

    public StorageDataAdapter(Context context, List<StorageDataModel> storageDataModels) {
        this.context = context;
        this.storageDataModels = storageDataModels;
    }

    @NonNull
    @Override
    public StorageDataAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_storage_data,parent,false);
        return new StorageDataAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageDataAdapter.viewHolder holder, int position) {
        final StorageDataModel storageDataModel = storageDataModels.get(position);
        holder.storageDataIMG.setImageURI(storageDataModel.getDataIMG());
        holder.storageDataTittle.setText(""+storageDataModel.getTittle());
        holder.storageDataPrice.setText(""+storageDataModel.getPrice()+"VNĐ");
        holder.storageDataId.setText("ID: "+storageDataModel.getId());
        holder.storageDataStatus.setText(""+storageDataModel.getStatus());



        if(storageDataModel.getStatus().equals("Hết hàng")){
            holder.storageDataStatus.setBackgroundResource(R.drawable.bg_status_sold);
        }else{
            holder.storageDataStatus.setBackgroundResource(R.drawable.bg_status_aval);

        }
    }

    @Override
    public int getItemCount() {
        return storageDataModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private ImageView storageDataIMG;
        private TextView storageDataTittle,storageDataPrice,storageDataId,storageDataStatus;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            storageDataIMG = itemView.findViewById(R.id.iv_storageDataIMG);
            storageDataTittle = itemView.findViewById(R.id.txt_storageDataTittle);
            storageDataPrice = itemView.findViewById(R.id.txt_storageDataPrice);
            storageDataId = itemView.findViewById(R.id.txt_storageDataId);
            storageDataStatus = itemView.findViewById(R.id.txt_storageDataStatus);
        }
    }
}
