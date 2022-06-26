package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class BottomTabActivity extends AppCompatActivity {
    MeowBottomNavigation meowBottomNavigation;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String SHARED_ID = "local_userid";
    public static final String SHARED_DEPARTMENT = "local_depid";
    SharedPreferences sharedpreferences;
    String userid,dep;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);



        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(SHARED_ID, null);
        dep = sharedpreferences.getString(SHARED_DEPARTMENT, null);
        meowBottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomTab);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_bbookmark));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_add));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_dw));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_profile));

        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment;
                fragment = null;

                switch (item.getId()){
                    case 1:
                        fragment = new HomeFragment(userid,dep);
                        break;

                    case 2:
                        fragment = new BookmarkFragment(userid);
                    break;

                    case 3:
                        fragment = new UploadPdfFrag();
                    break;

                    case 4:
                        fragment = new DownloadFragment();
                    break;

                    case 5:
                        fragment = new ProfileFragment();
                    break;
                }
                loadFragment(fragment);
            }
        });

        meowBottomNavigation.show(1,true);

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(), "you clicked "+ item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(), "you reclicked "+ item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
}