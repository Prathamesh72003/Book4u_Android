package com.example.book4u;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SubjectCollectionAdapter extends FragmentPagerAdapter {

    Context context;
    int TotalTabs;

    public SubjectCollectionAdapter(FragmentManager fm, Context context, int TotalTabs) {
        super(fm);
        this.context = context;
        this.TotalTabs = TotalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                SubjectPdfFragment subjectPdfFragment = new SubjectPdfFragment();
                return subjectPdfFragment;
            case 1:
                SubjectBookFragment subjectBookFragment = new SubjectBookFragment();
                return subjectBookFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TotalTabs;
    }

}