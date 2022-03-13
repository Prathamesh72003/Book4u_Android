package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubjectActivity extends AppCompatActivity {
    int img[] = {R.drawable.java_book, R.drawable.os_book, R.drawable.os_book, R.drawable.java_book,R.drawable.java_book, R.drawable.os_book, R.drawable.os_book, R.drawable.java_book};
    String heading[] = {"Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II","Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II"};
    String num[] = {"50", "70", "0", "101", "50", "70", "0", "101"};

    SubjectAdapter subjectAdapter;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        recyclerView = (RecyclerView) findViewById(R.id.subjectRecyclerView);
        subjectAdapter = new SubjectAdapter(getApplicationContext(), img, heading, num);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(subjectAdapter);


    }
}