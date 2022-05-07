package com.example.book4u;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{

    Context context;
    LayoutInflater layoutInflater;
    View view;
    int Img[];
    String name[];
    String numofpdfp[];
    String subname;

    public SubjectAdapter(Context context, int[] Img, String[] name, String[] numofpdf) {
        this.context = context;
        this.view = view;
        this.Img = Img;
        this.name = name;
        this.numofpdfp = numofpdf;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subject_layout, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img.setImageResource(Img[position]);
        holder.tv.setText(name[position]);
        holder.subtitle.setText(numofpdfp[position]);

//        subname = holder.tv.getText().toString();

        holder.subcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubjectCollectionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("subName", subname);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;
        TextView subtitle;
        CardView subcard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.subjectImg);
            tv = itemView.findViewById(R.id.subName);
            subtitle = itemView.findViewById(R.id.numofpdf);
            subcard = itemView.findViewById(R.id.subCard);
        }
    }
}
