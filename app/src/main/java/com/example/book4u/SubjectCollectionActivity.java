package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SubjectCollectionActivity extends AppCompatActivity {

    TabLayout subCollectionTabLayout;
    ViewPager subCollectionViewPager;
    SubjectCollectionAdapter subjectCollectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_collection);

        subCollectionTabLayout = (TabLayout) findViewById(R.id.SubjectCollectionTabLayout);
        subCollectionViewPager = (ViewPager) findViewById(R.id.SubjectCollectionViewPager);

        subCollectionTabLayout.addTab(subCollectionTabLayout.newTab().setText("PDF"));
        subCollectionTabLayout.addTab(subCollectionTabLayout.newTab().setText("Books"));

        subCollectionTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        subjectCollectionAdapter = new SubjectCollectionAdapter(getSupportFragmentManager(),getApplicationContext(), subCollectionTabLayout.getTabCount());
        subCollectionViewPager.setAdapter(subjectCollectionAdapter);

        subCollectionViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(subCollectionTabLayout));

        subCollectionTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                subCollectionViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}