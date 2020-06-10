package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Class.StoreActivity;
import com.gmartdev.komsi.g_mart.Model.GetDistanceModel;
import com.gmartdev.komsi.g_mart.Model.ItemStoreCategoryModel;
import com.gmartdev.komsi.g_mart.Model.ProductCategoryModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemStoreCategoryAdapter extends RecyclerView.Adapter<ItemStoreCategoryAdapter.ViewHolderListItemCategory> implements Filterable {

    private static final String TAG = "ItemStoreCatAdapter";

    Context mContext;
    List<ProductCategoryModel> mData;
    List<ProductCategoryModel> mDataFiltered;

    String latUser, longUser;
    Double latEx = -7.6687424;
    Double longEx = 110.6438944;

    Retrofit retrofitJarak = new Retrofit.Builder()
            .baseUrl("https://milhamrk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API apiJarak = retrofitJarak.create(API.class);

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
        holder.ratingItem.setRating(Float.parseFloat(mData.get(position).getRating()));
        Picasso.get().load("http://gmart.vokasidev.com/api/images/produk/" + mData.get(position).getGambar()).into(holder.itemImage);

        //set up distance
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserData", mContext.MODE_PRIVATE);
        latUser = sharedPreferences.getString("latUser", null);
        longUser = sharedPreferences.getString("longUser", null);
        Call<GetDistanceModel> call = apiJarak.hitungJarak(Double.parseDouble(latUser), Double.parseDouble(longUser), Double.parseDouble(mData.get(position).getLatitude()), Double.parseDouble(mData.get(position).getLongitude()));
        call.enqueue(new Callback<GetDistanceModel>() {
            @Override
            public void onResponse(Call<GetDistanceModel> call, Response<GetDistanceModel> response) {
                if(response.isSuccessful()){
                    Double a;
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    a = response.body().getJarak();
                    holder.storeDistance.setText(String.valueOf(decimalFormat.format(a)));
                    Log.d(TAG, "onResponse: " + a);
                }
            }
            @Override
            public void onFailure(Call<GetDistanceModel> call, Throwable t) {
            }
        });


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
        private RatingBar ratingItem;
        private ImageView itemImage;
        private MaterialButton buttonSelected;

        public ViewHolderListItemCategory(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemMerk = itemView.findViewById(R.id.itemMerk);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            storeName = itemView.findViewById(R.id.text_store_item_name);
            storeDistance = itemView.findViewById(R.id.text_store_item_distance);
            buttonSelected = itemView.findViewById(R.id.buttonSelected);
            ratingItem = itemView.findViewById(R.id.ratingItem);
            itemImage = itemView.findViewById(R.id.itemImage);

        }
    }
}
