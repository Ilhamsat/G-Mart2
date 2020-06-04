package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Class.StoreActivity;
import com.gmartdev.komsi.g_mart.Model.KiosTerdekatModel;
import com.gmartdev.komsi.g_mart.Model.StoreRecomendationModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreRecommendationAdapter extends RecyclerView.Adapter<StoreRecommendationAdapter.ViewHolderListStore> {

    Context mContext;
    List<KiosTerdekatModel> mData;

    public StoreRecommendationAdapter(Context mContext, List<KiosTerdekatModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolderListStore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_store_recommendation,parent,false);
        ViewHolderListStore viewHolderListStore = new ViewHolderListStore(v);
        return viewHolderListStore;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListStore holder, final int position) {
        holder.storeName.setText(mData.get(position).getNama_kios());
        holder.storeDistance.setText("?");
        holder.ratingKios.setRating(Float.parseFloat(mData.get(position).getRating()));

        holder.storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoreActivity.class);
                intent.putExtra("id_kios", mData.get(position).getId_kios());
                intent.putExtra("nama_kios", mData.get(position).getNama_kios());
                intent.putExtra("alamat_kios", mData.get(position).getAlamat());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolderListStore extends RecyclerView.ViewHolder{

        private TextView storeName;
        private TextView storeDistance;
        private MaterialButton storeButton;
        private RatingBar ratingKios;

        public ViewHolderListStore(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.text_store_name);
            storeDistance = itemView.findViewById(R.id.text_store_location);
            storeButton = itemView.findViewById(R.id.ButtonLihat);
            ratingKios = itemView.findViewById(R.id.ratingKios);
        }
    }
}
