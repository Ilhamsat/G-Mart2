package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Model.ItemAtStoreModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAtStoreAdapter extends RecyclerView.Adapter<ItemAtStoreAdapter.ViewHolderListItemAtStore> {

    Context mContext;
    List<ItemAtStoreModel> mData;

    public ItemAtStoreAdapter(Context mContext, List<ItemAtStoreModel> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolderListItemAtStore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_item_at_store,parent,false);
        ViewHolderListItemAtStore viewHolderListItemAtStore = new ViewHolderListItemAtStore(v);
        return viewHolderListItemAtStore;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListItemAtStore holder, int position) {
        holder.itemName.setText(mData.get(position).getItemName());
        holder.itemPrice.setText(mData.get(position).getItemPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderListItemAtStore extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemPrice;

        public ViewHolderListItemAtStore(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}
