package com.example.book4u;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadHolder> {

    View view;
    Context context;
    LayoutInflater layoutInflater;
    int img[];
    String[] pdf_name, total_downloads, date;

    public DownloadAdapter(Context context, int img[] , String pdf_name[], String total_downloads[], String date[]){
        this.context = context;
        this.img = img;
        this.pdf_name = pdf_name;
        this.total_downloads = total_downloads;
        this.date = date;
    }
    @NonNull
    @Override

    public DownloadAdapter.DownloadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.downloadpdf_layout, parent,false);

        return new DownloadHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.DownloadHolder holder, int position) {
        holder.imageView.setImageResource(img[position]);
        holder.pdf_name.setText(pdf_name[position]);
        holder.no_of_downloads.setText(total_downloads[position]);
        holder.date.setText(date[position]);
    }

    @Override
    public int getItemCount() {
        return pdf_name.length;
    }

    public class DownloadHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView pdf_name, no_of_downloads, date;
        public DownloadHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.pdfImg);
            pdf_name = (TextView) itemView.findViewById(R.id.pdf_name);
            no_of_downloads = (TextView) itemView.findViewById(R.id.total_downloads);
            date = (TextView) itemView.findViewById(R.id.downloaded_on);
        }
    }
}
