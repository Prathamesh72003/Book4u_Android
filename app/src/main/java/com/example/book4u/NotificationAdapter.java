package com.example.book4u;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    View view;
    LayoutInflater layoutInflater;
    String[] title, desc;
    TextView notificationDesc;
    public NotificationAdapter(Context context, String[] title, String[] desc) {
        this.title = title;
        this.desc = desc;
    }
    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.notification_layout, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        holder.notiTitle.setText(title[position]);
        holder.notiDesc.setText(desc[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationDesc = (TextView) v.findViewById(R.id.NotificationDesc);
                int noti =  (notificationDesc.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;

                TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                notificationDesc.setVisibility(noti);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notiTitle, notiDesc;
        CardView cardView;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notiTitle = (TextView) itemView.findViewById(R.id.notificationTitle);
            notiDesc = (TextView) itemView.findViewById(R.id.NotificationDesc);
            cardView = (CardView) itemView.findViewById(R.id.CardviewNotify);
        }
    }
}