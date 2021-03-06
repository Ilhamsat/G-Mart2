package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TransactionProcessAdapter extends RecyclerView.Adapter<TransactionProcessAdapter.ViewHolderTransactionProcess> {

    private static final String TAG = "TransaksiProsesDiproses";

    Context mContext;
    List<PesananModel> mData;

    String contohNomorWA = "89651508930";

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

    private boolean appInstalledOrNot(String url){
        PackageManager packageManager = mContext.getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
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


        holder.hubungiKiosByWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean installed = appInstalledOrNot("com.whatsapp");

                if (installed){
                    Log.d(TAG, "onClick: NoHP Pemilik Kios FUCK" + mData.get(position).getNo_hp());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+contohNomorWA));
                    String no_hp = mData.get(position).getNo_hp();
                    String no_hpfix;
                    String[] parts = no_hp.split("\\+62");
                    no_hpfix = parts[1];
//                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+ no_hpfix));
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+ mData.get(position).getNo_hp()));
                    mContext.startActivity(intent);
                }else {
                    Toast.makeText(mContext, "Tidak ada aplikasi WhatsApp di Perangkat Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        private MaterialButton hubungiKiosByWA;

        public ViewHolderTransactionProcess(@NonNull View itemView) {
            super(itemView);

            howMany = itemView.findViewById(R.id.textHowManyProcess);
            items = itemView.findViewById(R.id.itemsProcess);
            totalPriceItems = itemView.findViewById(R.id.totalPriceItemsProcess);
            status = itemView.findViewById(R.id.statusProcess);
            hubungiKiosByWA = itemView.findViewById(R.id.hubungiKiosByWA);
        }
    }
}
