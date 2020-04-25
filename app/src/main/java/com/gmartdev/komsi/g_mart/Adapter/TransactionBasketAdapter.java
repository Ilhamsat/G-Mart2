package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Model.TransactionBasketModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionBasketAdapter extends RecyclerView.Adapter<TransactionBasketAdapter.ViewHolderTransactionBasket> {

    Context mContext;
    List<TransactionBasketModel> mData;

    public TransactionBasketAdapter(Context mContext, List<TransactionBasketModel> mData) {
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
        holder.howMany.setText(mData.get(position).getHowMany());
        holder.items.setText(mData.get(position).getItems());
        holder.totalPriceItems.setText(mData.get(position).getTotalPriceItems());
        holder.delivery.setText(mData.get(position).getDelivery());
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

        public ViewHolderTransactionBasket(@NonNull View itemView) {
            super(itemView);

            howMany = itemView.findViewById(R.id.textHowManyBasket);
            items = itemView.findViewById(R.id.itemsBasket);
            totalPriceItems = itemView.findViewById(R.id.totalPriceItemsBasket);
            delivery = itemView.findViewById(R.id.delivery);
        }
    }
}
