package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MyUploadsActivity extends AppCompatActivity {

    TabLayout myUploadsTabLayout;
    ViewPager myUploadsViewPager;
    MyUploadsAdapter myUploadsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploads);

        myUploadsTabLayout = (TabLayout) findViewById(R.id.MyUploadsTabLayout);
        myUploadsViewPager = (ViewPager) findViewById(R.id.MyUploadsViewPager);

        myUploadsTabLayout.addTab(myUploadsTabLayout.newTab().setText("Uploaded Pdfs"));
        myUploadsTabLayout.addTab(myUploadsTabLayout.newTab().setText("Uploaded Books"));

        myUploadsTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        myUploadsAdapter = new MyUploadsAdapter(getSupportFragmentManager(),getApplicationContext(), myUploadsTabLayout.getTabCount());
        myUploadsViewPager.setAdapter(myUploadsAdapter);

        myUploadsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(myUploadsTabLayout));

        myUploadsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myUploadsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}