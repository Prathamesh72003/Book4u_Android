package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationActivity extends AppCompatActivity {
    CardView card;
    TextView notificationDesc;
    NotificationAdapter notificationAdapter;
    RecyclerView recyclerView;
    String heading[] = {"Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II","Java Programming", "Operating System", "Advanced Computer Network", "Java Programming II"};

    String description[] = {"New book has been uploaded for Java - Applet",
            "Pdf for Operating System on Deadlock has been recently uploaded.",
            "Don't forget to check new books for ACN",
            "Check out the new pdf for Unit-1 - AWT(Abstract Window Toolkit)",
            "New book has been uploaded for Java - MultiThreading",
            "Pdf for Operating System on Deadlock has been recently uploaded.",
            "Don't forget to check new books for ACN",
            "Check out the new pdf for Unit-1 - AWT(Abstract Window Toolkit)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notificationAdapter = new NotificationAdapter(getApplicationContext(), heading, description);
        recyclerView = (RecyclerView) findViewById(R.id.NotificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(notificationAdapter);

    }


}