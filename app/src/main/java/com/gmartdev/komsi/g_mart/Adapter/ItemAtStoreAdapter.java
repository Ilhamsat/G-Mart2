package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Class.HelperKeranjang;
import com.gmartdev.komsi.g_mart.Class.Nilai;
import com.gmartdev.komsi.g_mart.Model.CartModel;
import com.gmartdev.komsi.g_mart.Model.ProductModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAtStoreAdapter extends RecyclerView.Adapter<ItemAtStoreAdapter.ViewHolderListItemAtStore>{

    private static final String TAG = "ItemAtStoreAdapter";

    Boolean isAdded = false;

    Context mContext;
    List<ProductModel> mData;
//    ArrayList<Integer> jumlahPesan = new ArrayList<Integer>();
    int[] jumlahPesan;

    HelperKeranjang helperKeranjang;

    List<Nilai> mNilai = new ArrayList<>();

    public ItemAtStoreAdapter() {
    }

    public ItemAtStoreAdapter(Context mContext, List<ProductModel> mData, HelperKeranjang helperKeranjang){
        this.mContext = mContext;
        this.mData = mData;
        this.helperKeranjang = helperKeranjang;
    }

//    public ItemAtStoreAdapter(Context mContext, List<ProductModel> mData, HelperKeranjang helperKeranjang){
//        this.mContext = mContext;
//        this.mData = mData;
//        this.helperKeranjang = helperKeranjang;
//    }

    @NonNull
    @Override
    public ViewHolderListItemAtStore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_item_at_store,parent,false);
        ViewHolderListItemAtStore viewHolderListItemAtStore = new ViewHolderListItemAtStore(v);

        jumlahPesan = new int[mData.size()];
        for (int i = 0; i < mData.size(); i++){
            jumlahPesan[i] = 0;
        }


        return viewHolderListItemAtStore;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderListItemAtStore holder, int position) {

        mNilai.add(new Nilai(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan[position]), mData.get(position).getHarga()));

//        holder.itemImageAtStore.setImageResource("");
        Picasso.get().load("http://gmart.vokasidev.com/api/images/produk/" + mData.get(position).getGambar()).into(holder.itemImageAtStore);
//        Picasso.get().load("https://cf.shopee.co.id/file/4127f546911719cb67003644e9895fa9").into(holder.itemImageAtStore);

        holder.itemName.setText(mData.get(position).getMerk() + " " + mData.get(position).getNama_produk());
        holder.itemPrice.setText(mData.get(position).getHarga());

        holder.buttonSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.buttonSelectedItem.setVisibility(View.GONE);
                jumlahPesan[position] = 1;
                holder.numberOrders.setText(String.valueOf(jumlahPesan[position]));
                holder.contentNumber.setVisibility(View.VISIBLE);

//                mNilai.add(new Nilai(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan[position]), mData.get(position).getHarga()));
                Log.d(TAG, "onClick: ItemAtStoreAdapter Barangnya ada " + mData.size());
                Log.d(TAG, "onClick: ItemAtStoreAdapter Add ke " + mNilai.size());
                Log.d(TAG, "onClick: ItemAtStoreAdapter Jumlah Pesan " + jumlahPesan[position]);
                mNilai.get(position).setJumlah_pesan(String.valueOf(jumlahPesan[position]));
                helperKeranjang.setValues(mNilai);
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlahPesan[position] ++;
                isAdded = true;
                if(isAdded && jumlahPesan[position] == 1){
//                    mNilai.add(new Nilai(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan[position]), mData.get(position).getHarga()));
                }else if (isAdded && jumlahPesan[position] > 1) {
                    Log.d(TAG, "onClick: ItemAtStoreAdapter JumlahPesanan" + jumlahPesan[position]);
                    Log.d(TAG, "onClick: ItemAtStoreAdapter JumlahPesanan" + position);
                    //Log.d(TAG, "onClick: ItemAtStoreAdapter JumlahPesanan" + mNilai.get(position).getJumlah_pesan());
                    mNilai.get(position).setJumlah_pesan(String.valueOf(jumlahPesan[position]));
                }

                holder.numberOrders.setText(String.valueOf(jumlahPesan[position]));
//                mNilai.add(new Nilai());
//                helperKeranjang.setValues(mNilai);
//                if (jumlahPesan == 1){
//                    mCart.add(new CartModel(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan), mData.get(position).getHarga()));
//                }else if (jumlahPesan>1){
//                    mCart.remove(position);
//                    mCart.add(new CartModel(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan), mData.get(position).getHarga()));
//                }
            }
        });
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jumlahPesan[position] == 1){
                    jumlahPesan[position] = 0;
                    holder.buttonSelectedItem.setVisibility(View.VISIBLE);
                    holder.contentNumber.setVisibility(View.GONE);
                    mNilai.get(position).setJumlah_pesan(String.valueOf(jumlahPesan[position]));
                } else {
                    jumlahPesan[position] --;
                    Log.d(TAG, "onClick: ItemAtStoreAdapter Reduce JumlahPesanan" + jumlahPesan[position]);
                    mNilai.get(position).setJumlah_pesan(String.valueOf(jumlahPesan[position]));
                    holder.numberOrders.setText(String.valueOf(jumlahPesan[position]));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


//    @Override
//    public List<Nilai> HelperKeranjang(String id_produkkios, String jumlah, String harga) {
//        List<Nilai> nilaiKerenjang = new ArrayList<>();
//        nilaiKerenjang = nilaiKerenjang.add(new Nilai(id_produkkios,jumlah,harga));
//        return nilaiKerenjang;
//    }
//
//    @Override
//    public int Size() {
//        return 0;
//    }

    public class ViewHolderListItemAtStore extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemPrice;
        private MaterialButton buttonSelectedItem;
        private RelativeLayout contentNumber;
        private ImageButton reduce;
        private ImageButton add;
        private TextView numberOrders;
        private ImageView itemImageAtStore;

        public ViewHolderListItemAtStore(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            buttonSelectedItem = itemView.findViewById(R.id.buttonSelectedAtStore);
            contentNumber = itemView.findViewById(R.id.contentNumber);
            reduce = itemView.findViewById(R.id.reduce);
            add = itemView.findViewById(R.id.add);
            numberOrders = itemView.findViewById(R.id.textNumberOrders);
            itemImageAtStore = itemView.findViewById(R.id.itemImageAtStore);

        }
    }
}
