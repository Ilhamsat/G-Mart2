package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Class.StoreActivity;
import com.gmartdev.komsi.g_mart.Model.ItemStoreCategoryModel;
import com.gmartdev.komsi.g_mart.Model.ProductCategoryModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemStoreCategoryAdapter extends RecyclerView.Adapter<ItemStoreCategoryAdapter.ViewHolderListItemCategory> implements Filterable {

    Context mContext;
    List<ProductCategoryModel> mData;
    List<ProductCategoryModel> mDataFiltered;

    public ItemStoreCategoryAdapter(Context mContext, List<ProductCategoryModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;
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
        holder.itemMerk.setText(mDataFiltered.get(position).getMerk());
        holder.itemName.setText(mDataFiltered.get(position).getNama_produk());
        holder.itemPrice.setText(mDataFiltered.get(position).getHarga());
        holder.storeName.setText(mDataFiltered.get(position).getNama_kios());
        holder.storeDistance.setText("?");
        holder.buttonSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoreActivity.class);
                intent.putExtra("id_kios", mDataFiltered.get(position).getId_kios());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String key = charSequence.toString();
                if (key.isEmpty()){
                    mDataFiltered = mData;
                }
                else {
                    List<ProductCategoryModel> lstFiltered = new ArrayList<>();
                    for (ProductCategoryModel row : mData){
                        if (row.getMerk().toLowerCase().contains(key.toLowerCase()) || row.getNama_produk().toLowerCase().contains(key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }

                    mDataFiltered = lstFiltered;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                mDataFiltered = (List<ProductCategoryModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };



    }

    public class ViewHolderListItemCategory extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemMerk;
        private TextView itemPrice;
        private TextView storeName;
        private TextView storeDistance;

        private MaterialButton buttonSelected;

        public ViewHolderListItemCategory(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemMerk = itemView.findViewById(R.id.itemMerk);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            storeName = itemView.findViewById(R.id.text_store_item_name);
            storeDistance = itemView.findViewById(R.id.text_store_item_distance);
            buttonSelected = itemView.findViewById(R.id.buttonSelected);

        }
    }
}
