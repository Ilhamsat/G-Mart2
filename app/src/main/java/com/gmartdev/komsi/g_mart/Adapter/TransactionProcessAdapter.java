package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Model.TransactionProcessModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionProcessAdapter extends RecyclerView.Adapter<TransactionProcessAdapter.ViewHolderTransactionProcess> {


    Context mContext;
    List<TransactionProcessModel> mData;

    public TransactionProcessAdapter(Context mContext, List<TransactionProcessModel> mData) {
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTransactionProcess holder, int position) {
        holder.howMany.setText(mData.get(position).getHowMany());
        holder.items.setText(mData.get(position).getItems());
        holder.totalPriceItems.setText(mData.get(position).getTotalPriceItems());
        holder.status.setText(mData.get(position).getStatus());
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
