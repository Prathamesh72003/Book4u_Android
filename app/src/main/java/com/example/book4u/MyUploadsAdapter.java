package com.example.book4u;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyUploadsAdapter extends FragmentPagerAdapter {

    Context context;
    int TotalTabs;

    public MyUploadsAdapter(FragmentManager fm, Context context, int TotalTabs) {
        super(fm);
        this.context = context;
        this.TotalTabs = TotalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MyUploadsPdf myUploadsPdfFragment = new MyUploadsPdf();
                return myUploadsPdfFragment;
            case 1:
                MyUploadsBookFragment myUploadsBookFragment = new MyUploadsBookFragment();
                return myUploadsBookFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TotalTabs;
    }

}