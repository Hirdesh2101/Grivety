package com.example.grivety;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new news();
                break;

            case 1:
                fragment = new community();
                break;

            case 2:
                fragment = new senior();
                break;

            case 3:
                fragment = new books();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
