package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class onboardMain extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout linearLayout;
    TextView textView;
    Button b1;

    SliderAdapter sliderAdapter;

    public TextView[] pageIndicator;

    private int btn_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_main);


        viewPager = (ViewPager) findViewById(R.id.slideViewPager);
        linearLayout = (LinearLayout) findViewById(R.id.pageIndicator);
        textView = (TextView) findViewById(R.id.skip);
        b1 = (Button) findViewById(R.id.button);

        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);
        addPagination(0);
        viewPager.addOnPageChangeListener(viewListener);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addPagination(int pos){
        pageIndicator = new TextView[3];
        linearLayout.removeAllViews();
        for (int i = 0; i < pageIndicator.length; i++) {
            pageIndicator[i] = new TextView(this);
            pageIndicator[i].setText(Html.fromHtml("&#8226&#160"));
            pageIndicator[i].setTextSize(35);
            pageIndicator[i].setTextColor(getResources().getColor(R.color.white));

            linearLayout.addView(pageIndicator[i]);
        }
        if (pageIndicator.length > 0){
            pageIndicator[pos].setTextColor(getResources().getColor(R.color.black));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addPagination(position);
            btn_no = position;

            if(position == pageIndicator.length - 1){
                b1.setEnabled(true);
                b1.setText("GET STARTED");
                b1.setVisibility(View.VISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void loginActivity(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}