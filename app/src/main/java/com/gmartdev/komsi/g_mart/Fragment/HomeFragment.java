package com.gmartdev.komsi.g_mart.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.StoreRecommendationAdapter;
import com.gmartdev.komsi.g_mart.Class.BasicNeedsCategoryActivity;
import com.gmartdev.komsi.g_mart.Class.BathToolsCategoryActivity;
import com.gmartdev.komsi.g_mart.Class.BeverageCategoryActivity;
import com.gmartdev.komsi.g_mart.Class.CigaretteCategoryActivity;
import com.gmartdev.komsi.g_mart.Class.CleaningToolsCategoryActivity;
import com.gmartdev.komsi.g_mart.Class.FruitVegetablesCategoryActivity;
import com.gmartdev.komsi.g_mart.Class.GallonGasCategoryActivity;
import com.gmartdev.komsi.g_mart.Class.MedicineCategoryActivity;
import com.gmartdev.komsi.g_mart.Class.SnackCategoryActivity;
import com.gmartdev.komsi.g_mart.Model.GetKiosTerdekatModel;
import com.gmartdev.komsi.g_mart.Model.KiosTerdekatModel;
import com.gmartdev.komsi.g_mart.Model.StoreRecomendationModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    String latitude, longitude, latExample, longExample;
    String radius = "1500";
    int PERMISSION_ID = 44;
    float radiusDalamKm = Float.parseFloat(radius) / 1000;

    TextView kiosTerdekatIsEmpty, teksRekomendasiToko;
    private RecyclerView recyclerView;
    ProgressBar progressBar;

    FusedLocationProviderClient mFusedLocationClient;

    List<KiosTerdekatModel> mList = new ArrayList<>();
    Activity context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context=getActivity();

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBarKiosTerdekat);
        kiosTerdekatIsEmpty = (TextView) v.findViewById(R.id.kiosTerdekatIsEmpty);
        teksRekomendasiToko = (TextView) v.findViewById(R.id.teks_rekomendasi_toko);
        teksRekomendasiToko.setText("Pilihan Toko ( dalam jangkauan " + radiusDalamKm + " km )");

        recyclerView = (RecyclerView) v.findViewById(R.id.store_recommendation);
        StoreRecommendationAdapter storeRecommendationAdapter = new StoreRecommendationAdapter(getContext(),mList);
        recyclerView.setAdapter(storeRecommendationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLastLocation();

        callApi();

//        mList = new ArrayList<>();
//        mList.add(new StoreRecomendationModel("Toko Pelangi","1,8"));
//        mList.add(new StoreRecomendationModel("Toko Shamila","1,3"));
//        mList.add(new StoreRecomendationModel("Toko Swalayan Laura","2"));
//        mList.add(new StoreRecomendationModel("Toko Cina","1"));
//        mList.add(new StoreRecomendationModel("Toko Ulin","2"));
//        mList.add(new StoreRecomendationModel("Toko Mama Lia","1,4"));
    }

    @Override
    public void onStart() {
        super.onStart();



        RelativeLayout sembako = (RelativeLayout) context.findViewById(R.id.sembako);
        sembako.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BasicNeedsCategoryActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout makanan = (RelativeLayout) context.findViewById(R.id.makanan);
        makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SnackCategoryActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout minuman = (RelativeLayout) context.findViewById(R.id.minuman);
        minuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BeverageCategoryActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout alat_mandi = (RelativeLayout) context.findViewById(R.id.alat_mandi);
        alat_mandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BathToolsCategoryActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout galon_gas = (RelativeLayout) context.findViewById(R.id.galon_gas);
        galon_gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GallonGasCategoryActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout rokok = (RelativeLayout) context.findViewById(R.id.rokok);
        rokok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CigaretteCategoryActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout obat = (RelativeLayout) context.findViewById(R.id.obat);
        obat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MedicineCategoryActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout sayur_buah = (RelativeLayout) context.findViewById(R.id.sayuran_buah);
        sayur_buah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FruitVegetablesCategoryActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout kebersihan = (RelativeLayout) context.findViewById(R.id.kebersihan);
        kebersihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CleaningToolsCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);

                                    SharedPreferences.Editor editor= sharedPreferences.edit();
                                    editor.putString("latUser", String.valueOf(location.getLatitude()));
                                    editor.putString("longUser", String.valueOf(location.getLongitude()));
                                    editor.commit();

                                    latitude = String.valueOf(location.getLatitude());
                                    longitude = String.valueOf(location.getLongitude());
                                    Log.d(TAG, "onComplete: User Latitude " + latitude);
                                    Log.d(TAG, "onComplete: User Longitude " + longitude);
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(context, "Turn on Location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);

            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("latUser", String.valueOf(mLastLocation.getLatitude()));
            editor.putString("longUser", String.valueOf(mLastLocation.getLongitude()));
            editor.commit();

            latitude = String.valueOf(mLastLocation.getLatitude());
            longitude = String.valueOf(mLastLocation.getLongitude());
            Log.d(TAG, "onComplete: User Latitude " + latitude);
            Log.d(TAG, "onComplete: User Longitude " + longitude);
        }
    };


    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Granted. Start getting the location information
                getLastLocation();
            }
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    private void callApi() {
        radius = "1500";
        latExample = "-7.6687424";
        longExample = "110.6438944";
        Log.d(TAG, "lat: " + latExample);
        Log.d(TAG, "long: " + longExample);
        Log.d(TAG, "radius: " + radius);
        Call<GetKiosTerdekatModel> call = api.kiosTerdekat("-7.6687424", "110.6438944", "1500");
//        Call<GetKiosTerdekatModel> call = api.kiosTerdekat(latitude, longitude, radius);
        call.enqueue(new Callback<GetKiosTerdekatModel>() {
            @Override
            public void onResponse(Call<GetKiosTerdekatModel> call, Response<GetKiosTerdekatModel> response) {
                Log.d(TAG, "onResponse: Kenapa ");

                if (response.body().getData() != null) {
                    kiosTerdekatIsEmpty.setVisibility(View.GONE);
                    List<KiosTerdekatModel> list = response.body().getData();

                    Log.d(TAG, "Code :" + response.body().getData());
                    for (KiosTerdekatModel kiosTerdekatModel : list) {
                        mList.add(new KiosTerdekatModel(kiosTerdekatModel.getId_kios(), kiosTerdekatModel.getNama_kios(), kiosTerdekatModel.getNo_hp(), kiosTerdekatModel.getAlamat(), kiosTerdekatModel.getStatus_buka(), kiosTerdekatModel.getRating(), kiosTerdekatModel.getLongitude(), kiosTerdekatModel.getLatitude()));
                    }
                    Log.d(TAG, "Data " + mList);

                    progressBar.setVisibility(View.GONE);
                    StoreRecommendationAdapter storeRecommendationAdapter = new StoreRecommendationAdapter(getContext(),mList);
                    recyclerView.setAdapter(storeRecommendationAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {
                    kiosTerdekatIsEmpty.setText("Tidak ada kios dalam jangkauan " + radiusDalamKm + " km dari posisi Anda saat ini.");
                    progressBar.setVisibility(View.GONE);
                    kiosTerdekatIsEmpty.setVisibility(View.VISIBLE);
                    Log.d(TAG, "Code KiosTerdekat : " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetKiosTerdekatModel> call, Throwable t) {

            }
        });
    }

}
