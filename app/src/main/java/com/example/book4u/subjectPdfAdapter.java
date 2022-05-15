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

public class subjectPdfAdapter extends RecyclerView.Adapter<subjectPdfAdapter.ViewHolder>{

    Context context;
    LayoutInflater layoutInflater;
    View view;
    String[] Img;
    String name[];
    String subjectname[];
    String subname;
    String[] pdf_id;

    public subjectPdfAdapter (Context context, String[] Img, String[] name, String[] subjectname, String[] pdf_id) {
        this.context = context;
        this.view = view;
        this.Img = Img;
        this.name = name;
        this.subjectname = subjectname;
        this.pdf_id = pdf_id;
    }

    @NonNull
    @Override
    public subjectPdfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subjectpdf_layout, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull subjectPdfAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso
                .get()
                .load(Img[position])
                .into(holder.img);
        holder.tv.setText(name[position]);
        holder.subtitle.setText("Subject "+subjectname[position]);

        holder.subPdfcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pdfId", pdf_id[position]);
                intent.putExtra("imgName", Img[position]);
                intent.putExtra("pdfName", name[position]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        CardView subPdfcard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.subPdfImg);
            tv = itemView.findViewById(R.id.subPdfName);
            subtitle = itemView.findViewById(R.id.subjectofpdf);
            subPdfcard = itemView.findViewById(R.id.subPdfCard);
        }
    }
}

