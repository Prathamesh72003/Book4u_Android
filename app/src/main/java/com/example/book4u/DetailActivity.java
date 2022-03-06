package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView pdfDetailImageView;
    TextView pdfDetailName;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        pdfDetailImageView = (ImageView) findViewById(R.id.pdfDetailsImg);
        pdfDetailName = (TextView) findViewById(R.id.title);

        pdfDetailImageView.setImageResource(getIntent().getIntExtra("imgName",0));
        pdfDetailName.setText(getIntent().getStringExtra("pdfName"));

        backFromDetails();
    }

    public void backFromDetails(){

        imageButton = (ImageButton) findViewById(R.id.backBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomTabActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}