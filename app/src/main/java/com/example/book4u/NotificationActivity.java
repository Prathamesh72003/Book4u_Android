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
    String description[] = {"hello there how are you doin heyyaaa hola I am good hope you are doing fine and also fuck off",
            "heyyaaa hola I am good hope you are doing fine and also fuck off",
            "This subject teacher is fucking idiot she dont care the students effort👎",
            "Good teacher not that good",
            "hello there how are you doin",
            "heyyaaa hola I am good hope you are doing fine and also fuck off",
            "This subject teacher is fucking idiot she dont care the students effort👎",
            "Good teacher not that good"};
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