package com.example.myapplication.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final HashMap<Fragment, Integer> fragments = new HashMap<>();
    private final HashMap<String, Integer> fragmentNames = new HashMap<>();
    private final HashMap<Integer, String> fragmentNumbers = new HashMap<>();


    public SectionsStatePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    private void addFragment(Fragment fragment, String fragmentName) {
        fragmentList.add(fragment);
        fragments.put(fragment, fragmentList.size() - 1);
        fragmentNames.put(fragmentName, fragmentList.size() - 1);
        fragmentNumbers.put(fragmentList.size() - 1, fragmentName);
    }

    public Integer getFragmentNumber(String fragmentName) {
        if (fragmentNames.containsKey(fragmentName)) {
            return fragmentNames.get(fragmentName);
        }
        return null;
    }

    public Integer getFragmentNumber(Fragment fragment) {
        if (fragments.containsKey(fragment)) {
            return fragments.get(fragment);
        }
        return null;
    }

    public String getFragmentName(Integer fragmentNumber) {
        if (fragmentNumbers.containsKey(fragmentNumber)) {
            return fragmentNumbers.get(fragmentNumber);
        }
        return null;
    }
}
