package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class SubjectPdfActivity extends AppCompatActivity {
    int img[] = {R.drawable.java_book, R.drawable.os_book, R.drawable.os_book, R.drawable.java_book,R.drawable.java_book, R.drawable.os_book, R.drawable.os_book, R.drawable.java_book};
    String heading[] = {"Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II","Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II"};
    String num[] = {"50", "70", "0", "101", "50", "70", "0", "101"};

    SubjectAdapter subjectAdapter;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    TextView subname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_pdf);
        recyclerView = (RecyclerView) findViewById(R.id.subjectPdfRecyclerView);
        subjectAdapter = new SubjectAdapter(getApplicationContext(), img, heading, num);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(subjectAdapter);

        subname = (TextView) findViewById(R.id.subName);
//        subname.setText(getIntent().getStringExtra("subName"));
    }
}