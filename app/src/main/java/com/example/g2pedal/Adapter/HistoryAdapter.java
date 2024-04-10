package com.example.g2pedal.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g2pedal.DTO.BillDTO;
import com.example.g2pedal.Model.HistoryModel;
import com.example.g2pedal.R;


import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {
    private Context context;
    private List<HistoryModel> historyModels;

    public HistoryAdapter(Context context, List<HistoryModel> historyModels) {
        this.context = context;
        this.historyModels = historyModels;
    }
    public void setHistoryModels(List<HistoryModel> historyModels) {
        this.historyModels = historyModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history,parent,false);
        return new HistoryAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.viewHolder holder, int position) {
        HistoryModel historyModel = historyModels.get(position);

        holder.historyId.setText("ID hóa đơn: "+historyModel.getHistoryId());
        holder.historyDate.setText("Ngày bán: "+historyModel.getDate());
        holder.historyPaid.setText("Tổng tiền: "+ historyModel.getTotalPay()+" Dollar");
        List<String> productIds = historyModel.getProductIds();
        String productIdsString = TextUtils.join(", ", productIds);
        holder.historyItemId.setText("ID sản phẩm đã bán: "+productIdsString);
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView historyId, historyDate,historyPaid, historyItemId;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            historyId = itemView.findViewById(R.id.txt_HistoryId);
            historyDate = itemView.findViewById(R.id.txt_HistoryDate);
            historyPaid = itemView.findViewById(R.id.txt_HistoryPaid);
            historyItemId = itemView.findViewById(R.id.txt_HistoryItem);
        }
    }
}
