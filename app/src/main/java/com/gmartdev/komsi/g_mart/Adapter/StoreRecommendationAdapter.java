package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Class.StoreActivity;
import com.gmartdev.komsi.g_mart.Model.GetDistanceModel;
import com.gmartdev.komsi.g_mart.Model.GetKiosTerdekatModel;
import com.gmartdev.komsi.g_mart.Model.KiosTerdekatModel;
import com.gmartdev.komsi.g_mart.Model.StoreRecomendationModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StoreRecommendationAdapter extends RecyclerView.Adapter<StoreRecommendationAdapter.ViewHolderListStore> {

    private static final String TAG = "StoreRecAdapter";

    Context mContext;
    List<KiosTerdekatModel> mData;

    String latUser, longUser;
    Double distance;

    Double latEx = -7.6687424;
    Double longEx = 110.6438944;

    Retrofit retrofitJarak = new Retrofit.Builder()
            .baseUrl("https://milhamrk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API apiJarak = retrofitJarak.create(API.class);

    public StoreRecommendationAdapter(Context mContext, List<KiosTerdekatModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolderListStore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_store_recommendation,parent,false);
        ViewHolderListStore viewHolderListStore = new ViewHolderListStore(v);
        return viewHolderListStore;
    }

    private void hitungJarak(Double latKios, Double longKios){
        final Double[] Distance = new Double[1];
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserData", mContext.MODE_PRIVATE);
        latUser = sharedPreferences.getString("latUser", null);
        longUser = sharedPreferences.getString("longUser", null);
        latEx = -7.6687424;
        longEx = 110.6438944;
        Call<GetDistanceModel> call = apiJarak.hitungJarak(-7.775009991025893, 110.37115654019851, -7.7751898, 110.33623);
//        Call<GetDistanceModel> call = apiJarak.hitungJarak(latEx, longEx, latKios, longKios);
//        Call<GetDistanceModel> call = apiJarak.hitungJarak(latUser, longUser, latKios, longKios);
        call.enqueue(new Callback<GetDistanceModel>() {
            @Override
            public void onResponse(Call<GetDistanceModel> call, Response<GetDistanceModel> response) {

                if(response.isSuccessful()){
                    Double a;
                    Distance[0] = response.body().getJarak();
                    a = response.body().getJarak();
                    Log.d(TAG, "onResponse: " + a);
                    Log.d(TAG, "onResponse: " + Distance[0]);
                }
            }

            @Override
            public void onFailure(Call<GetDistanceModel> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListStore holder, final int position) {
        holder.storeName.setText(mData.get(position).getNama_kios());
        holder.ratingKios.setRating(Float.parseFloat(mData.get(position).getRating()));

//        holder.storeDistance.setText(String.valueOf(hitungJarak(Double.parseDouble(mData.get(position).getLatitude()), Double.parseDouble(mData.get(position).getLatitude()))));

//        Call<GetDistanceModel> call = apiJarak.hitungJarak(-7.775009991025893, 110.37115654019851, -7.7751898, 110.33623);
        Call<GetDistanceModel> call = apiJarak.hitungJarak(latEx, longEx, Double.parseDouble(mData.get(position).getLatitude()), Double.parseDouble(mData.get(position).getLongitude()));
//        Call<GetDistanceModel> call = apiJarak.hitungJarak(latUser, longUser, latKios, longKios);
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

        holder.storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StoreActivity.class);
                intent.putExtra("id_kios", mData.get(position).getId_kios());
                intent.putExtra("nama_kios", mData.get(position).getNama_kios());
                intent.putExtra("alamat_kios", mData.get(position).getAlamat());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolderListStore extends RecyclerView.ViewHolder{

        private TextView storeName;
        private TextView storeDistance;
        private MaterialButton storeButton;
        private RatingBar ratingKios;

        public ViewHolderListStore(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.text_store_name);
            storeDistance = itemView.findViewById(R.id.text_store_location);
            storeButton = itemView.findViewById(R.id.ButtonLihat);
            ratingKios = itemView.findViewById(R.id.ratingKios);
        }
    }
}
