package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Class.DetailPesananKeranjangActivity;
import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.Model.ProductDetailPesananModel;
import com.gmartdev.komsi.g_mart.Model.TransactionBasketModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionBasketAdapter extends RecyclerView.Adapter<TransactionBasketAdapter.ViewHolderTransactionBasket> {

    Context mContext;
    List<PesananModel> mData;

    public TransactionBasketAdapter(Context mContext, List<PesananModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolderTransactionBasket onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_basket,parent,false);
        ViewHolderTransactionBasket viewHolderTransactionBasket = new ViewHolderTransactionBasket(v);
        return viewHolderTransactionBasket;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTransactionBasket holder, int position) {
        holder.howMany.setText(mData.get(position).getStatus());
        holder.items.setText(mData.get(position).getId_order());
        holder.totalPriceItems.setText(mData.get(position).getSubtotal_harga());
        holder.delivery.setText(mData.get(position).getMetode_kirim());
        holder.contentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailPesananKeranjangActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderTransactionBasket extends RecyclerView.ViewHolder {

        private TextView howMany;
        private TextView items;
        private TextView totalPriceItems;
        private TextView delivery;
        private RelativeLayout contentBasket;

        public ViewHolderTransactionBasket(@NonNull View itemView) {
            super(itemView);

            howMany = itemView.findViewById(R.id.textHowManyBasket);
            items = itemView.findViewById(R.id.itemsBasket);
            totalPriceItems = itemView.findViewById(R.id.totalPriceItemsBasket);
            delivery = itemView.findViewById(R.id.delivery);
            contentBasket = itemView.findViewById(R.id.contentBasket);
        }
    }
}
