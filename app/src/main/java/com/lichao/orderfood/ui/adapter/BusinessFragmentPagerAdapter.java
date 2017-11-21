package com.lichao.orderfood.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lichao.orderfood.presenter.net.bean.Seller;
import com.lichao.orderfood.ui.fragment.BaseFragment;
import com.lichao.orderfood.ui.fragment.GoodsFragment;
import com.lichao.orderfood.ui.fragment.SellerFragment;
import com.lichao.orderfood.ui.fragment.SuggestFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-17.
 */

public class BusinessFragmentPagerAdapter extends FragmentPagerAdapter {
    private Seller seller;
    private String[] mStringArry;
    private ArrayList<Fragment> fragmentList;

    public BusinessFragmentPagerAdapter(FragmentManager fm, String[] stringArry, Seller seller) {
        super(fm);
        this.mStringArry = stringArry;
        this.seller = seller;
        fragmentList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment baseFragment = null;
        switch (position) {
            case 0:
                baseFragment = new GoodsFragment();
                break;
            case 1:
                baseFragment = new SuggestFragment();
                break;
            case 2:
                baseFragment = new SellerFragment();
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("seller",seller);
        baseFragment.setArguments(bundle);

        if (!fragmentList.contains(baseFragment)) {
            fragmentList.add(baseFragment);
        }
        return baseFragment;
    }

    @Override
    public int getCount() {
        return mStringArry.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStringArry[position];
    }

    public ArrayList<Fragment> getFragmentList() {
        return fragmentList;
    }
}
