package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionProcessAdapter extends RecyclerView.Adapter<TransactionProcessAdapter.ViewHolderTransactionProcess> {


    Context mContext;
    List<PesananModel> mData;

    public TransactionProcessAdapter(Context mContext, List<PesananModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public ViewHolderTransactionProcess onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_process,parent,false);
        ViewHolderTransactionProcess viewHolderTransactionProcess = new ViewHolderTransactionProcess(v);
        return viewHolderTransactionProcess;
    }

    public int minus(int position){
        return mData.get(position).getProduk().size()-1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTransactionProcess holder, int position) {
        holder.howMany.setText(String.valueOf(mData.get(position).getProduk().size()));
        if(minus(position) == 0){
            holder.items.setText(mData.get(position).getProduk().get(0).getMerk() + " " + mData.get(position).getProduk().get(0).getNama_produk());
        }else {
            holder.items.setText(mData.get(position).getProduk().get(0).getMerk() + " " + mData.get(position).getProduk().get(0).getNama_produk() + " dan " + minus(position) + " barang lainnya");
        }
        holder.totalPriceItems.setText(mData.get(position).getSubtotal_harga());
        holder.status.setText("Dikemas");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderTransactionProcess extends RecyclerView.ViewHolder {

        private TextView howMany;
        private TextView items;
        private TextView totalPriceItems;
        private TextView status;

        public ViewHolderTransactionProcess(@NonNull View itemView) {
            super(itemView);

            howMany = itemView.findViewById(R.id.textHowManyProcess);
            items = itemView.findViewById(R.id.itemsProcess);
            totalPriceItems = itemView.findViewById(R.id.totalPriceItemsProcess);
            status = itemView.findViewById(R.id.statusProcess);
        }
    }
}
