package com.gmartdev.komsi.g_mart.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.gmartdev.komsi.g_mart.Class.EditProfileActivity;
import com.gmartdev.komsi.g_mart.Class.PostSplashScreen;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    TextView phoneNumber, address, name, email, headerName, headerEmail;


    Activity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getActivity();

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        drawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) v.findViewById(R.id.navigation_view_profile);
        View headerView = navigationView.getHeaderView(0);

        headerName = headerView.findViewById(R.id.headerName);
        headerEmail = headerView.findViewById(R.id.headerEmail);

        phoneNumber = (TextView) v.findViewById(R.id.profile_number_phone);
        address = (TextView) v.findViewById(R.id.profile_address);
        name = (TextView) v.findViewById(R.id.profile_name);
        email = (TextView) v.findViewById(R.id.profile_email);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);


        headerName.setText(sharedPreferences.getString("nama",null));
        headerEmail.setText(sharedPreferences.getString("email",null));
        phoneNumber.setText(sharedPreferences.getString("no_hp",null));
        address.setText(sharedPreferences.getString("alamat",null));
        name.setText(sharedPreferences.getString("nama",null));
        email.setText(sharedPreferences.getString("email",null));

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(this);

        return v;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.edit_profile:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.share:
                break;
            case R.id.logout:
                Intent intent1 = new Intent(getActivity(), PostSplashScreen.class);
                startActivity(intent1);
                break;
        }

        return true;
    }



}
