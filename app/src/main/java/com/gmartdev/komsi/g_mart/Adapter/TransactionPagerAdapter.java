package com.gmartdev.komsi.g_mart.Adapter;

import com.gmartdev.komsi.g_mart.Fragment.TransactionBasketFragment;
import com.gmartdev.komsi.g_mart.Fragment.TransactionFragment;
import com.gmartdev.komsi.g_mart.Fragment.TransactionHistoryFragment;
import com.gmartdev.komsi.g_mart.Fragment.TransactionProcessFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TransactionPagerAdapter extends FragmentStateAdapter {

    private TransactionFragment mContext;

    public TransactionPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, TransactionFragment c) {
        super(fragmentManager, lifecycle);
        mContext = c;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TransactionBasketFragment();
            case 1:
                return new TransactionProcessFragment();
            default:
                return new TransactionHistoryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
