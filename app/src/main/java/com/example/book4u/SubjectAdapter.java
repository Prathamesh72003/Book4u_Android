package com.example.book4u;

import android.annotation.SuppressLint;
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

import com.squareup.picasso.Picasso;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{

    Context context;
    LayoutInflater layoutInflater;
    View view;
    String Img[];
    String name[];
    String numofpdfp[];
    String subids[];
    String subname;

    public SubjectAdapter(Context context, String[] Img, String[] name, String[] numofpdf,String[] subids) {
        this.context = context;
        this.view = view;
        this.Img = Img;
        this.name = name;
        this.numofpdfp = numofpdf;
        this.subids = subids;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subject_layout, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso
                .get()
                .load(Img[position])
                .into(holder.img);
        holder.tv.setText(name[position]);
        holder.subtitle.setText("Pdfs: "+numofpdfp[position]);

        subname = holder.tv.getText().toString();

        holder.subcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubPdfActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("subName", name[position]);
                intent.putExtra("subids", subids[position]);
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
