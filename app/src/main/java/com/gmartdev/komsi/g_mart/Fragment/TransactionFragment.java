package com.gmartdev.komsi.g_mart.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmartdev.komsi.g_mart.Adapter.TransactionPagerAdapter;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class TransactionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaction, container, false);

        ViewPager2 viewPager2 = v.findViewById(R.id.viewPagerTransaction);
        viewPager2.setAdapter(new TransactionPagerAdapter(getFragmentManager(),getLifecycle(), this));

        TabLayout tabLayout = v.findViewById(R.id.tabTransaction);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: {
                        tab.setText(R.string.basket);
                        tab.setIcon(R.drawable.ic_basket_white);
                        break;
                    }
                    case 1: {
                        tab.setText(R.string.process);
                        tab.setIcon(R.drawable.ic_process_white);
                        break;
                    }
                    case 2: {
                        tab.setText(R.string.history);
                        tab.setIcon(R.drawable.ic_history_white);
                        break;
                    }
                }
            }
        }
        );
        tabLayoutMediator.attach();

        return v;
    }
}
