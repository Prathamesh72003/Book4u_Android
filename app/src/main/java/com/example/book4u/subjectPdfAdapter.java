package com.example.book4u;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class subjectPdfAdapter extends RecyclerView.Adapter<subjectPdfAdapter.ViewHolder>{

    Context context;
    LayoutInflater layoutInflater;
    View view;
    int Img[];
    String name[];
    String subjectname[];
    String subname;

    public subjectPdfAdapter (Context context, int[] Img, String[] name, String[] subjectname) {
        this.context = context;
        this.view = view;
        this.Img = Img;
        this.name = name;
        this.subjectname = subjectname;
    }

    @NonNull
    @Override
    public subjectPdfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subjectpdf_layout, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull subjectPdfAdapter.ViewHolder holder, int position) {
        holder.img.setImageResource(Img[position]);
        holder.tv.setText(name[position]);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;
        TextView subtitle;
        CardView subPdfcard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.subPdfImg);
            tv = itemView.findViewById(R.id.subPdfName);
            subtitle = itemView.findViewById(R.id.subjectofpdf);
        }
    }
}

