package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Model.ItemStoreCategoryModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemStoreCategoryAdapter extends RecyclerView.Adapter<ItemStoreCategoryAdapter.ViewHolderListItemCategory> {

    Context mContext;
    List<ItemStoreCategoryModel> mData;

    public ItemStoreCategoryAdapter(Context mContext, List<ItemStoreCategoryModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolderListItemCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_item_store,parent,false);
        ViewHolderListItemCategory viewHolderListItemCategory = new ViewHolderListItemCategory(v);
        return viewHolderListItemCategory;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListItemCategory holder, int position) {
        holder.itemName.setText(mData.get(position).getItemName());
        holder.itemPrice.setText(mData.get(position).getItemPrice());
        holder.storeName.setText(mData.get(position).getStoreName());
        holder.storeDistance.setText(mData.get(position).getStoreDistance());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderListItemCategory extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemPrice;
        private TextView storeName;
        private TextView storeDistance;

        public ViewHolderListItemCategory(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            storeName = itemView.findViewById(R.id.text_store_item_name);
            storeDistance = itemView.findViewById(R.id.text_store_item_distance);
        }
    }
}
