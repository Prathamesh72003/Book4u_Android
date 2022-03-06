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

import java.util.ArrayList;

public class TrendingPDFAdapter extends RecyclerView.Adapter<TrendingPDFAdapter.RecycleViewHolder> {

      Context context;
//    String data[];
//    int img[];

    ArrayList<TrendingPDFModel> pdfData;
    public TrendingPDFAdapter( Context context, ArrayList<TrendingPDFModel> pdfData)
    {
        this.context = context;
        this.pdfData = pdfData;
    }

    LayoutInflater layoutInflater;
    OnClickListenerInterface onClickListenerInterface;



    @NonNull


    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.trendingpdf_layout, parent,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {

            final TrendingPDFModel tempModel = pdfData.get(position);

            holder.textView.setText(pdfData.get(position).getPdfName());
            holder.imageView.setImageResource(pdfData.get(position).getImgName());

        holder.pdfCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("imgName", tempModel.getImgName());
                intent.putExtra("pdfName", tempModel.getPdfName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfData.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;
        CardView pdfCV;

         public RecycleViewHolder(@NonNull View itemView) {
             super(itemView);
             imageView = (ImageView) itemView.findViewById(R.id.pdfImg);
             textView = (TextView) itemView.findViewById(R.id.pdfName);
             pdfCV = (CardView) itemView.findViewById(R.id.pdfCardview);
         }

        @Override
        public void onClick(View view) {
            onClickListenerInterface.listener(view, getAdapterPosition());
        }
    }

     public interface OnClickListenerInterface{
        void listener(View v, int position);
     }
}
