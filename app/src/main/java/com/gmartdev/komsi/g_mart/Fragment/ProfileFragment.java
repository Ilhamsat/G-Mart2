package com.gmartdev.komsi.g_mart.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.gmartdev.komsi.g_mart.Class.EditProfileActivity;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Activity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getActivity();

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        drawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) v.findViewById(R.id.navigation_view_profile);

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
        }

        return true;
    }

}
