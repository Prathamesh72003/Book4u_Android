package com.example.book4u;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class UploadAdapter extends FragmentPagerAdapter {

    Context context;
    int TotalTabs;

    public UploadAdapter(FragmentManager fm,Context context, int TotalTabs) {
        super(fm);

        this.context = context;
        this.TotalTabs = TotalTabs;
    }



    public Fragment getItem(int position){
        switch (position){
            case 0:
                UploadPdfFragment uploadPdfFragment = new UploadPdfFragment();
                return uploadPdfFragment;
            case 1:
                UploadBookFragment uploadBookFragment = new UploadBookFragment();
                return  uploadBookFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TotalTabs;
    }
}
