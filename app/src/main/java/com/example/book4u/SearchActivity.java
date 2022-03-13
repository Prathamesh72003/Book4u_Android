package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    searchAdapter searchAdapter;
    TextView searchText;

    int[] pdfImg = {R.drawable.java_book, R.drawable.c_book, R.drawable.os_book, R.drawable.c_book, R.drawable.os_book};

    String[] pdfName = {
            "Applets",
            "Programming in C",
            "Basics of OS",
            "Applets",
            "Programming in C",
            "Basics of OS"
    };

    String[] subject = {
            "Java",
            "C",
            "Operating System",
            "Java",
            "C",
            "Operating System"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        searchAdapter = new searchAdapter(getApplicationContext(), pdfImg, pdfName, subject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(searchAdapter);

        searchText = (TextView) findViewById(R.id.searchTerm);
        searchText.setText(getIntent().getStringExtra("searchTerm"));

    }
}