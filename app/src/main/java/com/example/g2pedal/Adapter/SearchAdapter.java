package com.example.g2pedal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g2pedal.Model.SearchModel;
import com.example.g2pedal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.viewHolder> {

    private Context context;
    private List<SearchModel> searchModels;

    public SearchAdapter(Context context, List<SearchModel> searchModels) {
        this.context = context;
        this.searchModels = searchModels;
    }

    @NonNull
    @Override
    public SearchAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_search,parent,false);
        return new SearchAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.viewHolder holder, int position) {
        SearchModel searchModel = searchModels.get(position);
        Picasso.get().load(searchModel.getProductIMG()).into(holder.searchIMG);

        holder.searchId.setText("ID: "+searchModel.getId());
        holder.searchTittle.setText("Tên: "+searchModel.getTittle());
        holder.searchCategory.setText("Loại: "+searchModel.getCategory());
        holder.searchPrice.setText("Giá: "+searchModel.getPrice()+" Dollar");
        holder.searchColor.setText("Màu: "+searchModel.getColor());
        holder.searchBrand.setText("Hãng: "+searchModel.getBrand());
        holder.searchDayIn.setText("Ngày nhập: "+searchModel.getDayIn());
        holder.searchQty.setText("Số lượng: "+searchModel.getDayIn());

    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private ImageView searchIMG;
        private TextView searchId, searchTittle, searchCategory,
                searchBrand,searchColor, searchQty,
                searchDayIn,searchPrice;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            searchIMG = itemView.findViewById(R.id.iv_SearchIMG);
            searchId = itemView.findViewById(R.id.txt_SearchId);
            searchTittle = itemView.findViewById(R.id.txt_SearchTittle);
            searchCategory = itemView.findViewById(R.id.txt_SearchCategory);
            searchBrand = itemView.findViewById(R.id.txt_SearchBrand);
            searchColor = itemView.findViewById(R.id.txt_SearchColor);
            searchQty = itemView.findViewById(R.id.txt_SearchQty);
            searchDayIn = itemView.findViewById(R.id.txt_SearchDayIn);
            searchPrice = itemView.findViewById(R.id.txt_SearchPrice);

        }
    }
}