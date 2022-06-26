package com.example.book4u;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MyUploadsPdfAdapter extends RecyclerView.Adapter<MyUploadsPdfAdapter.MyUploadsPdfViewHolder> {

    View view;
    Context context;
    LayoutInflater layoutInflater;

    String id[];
    String[] pdfName;
    String[] imgUrl;
    String[] viewers;
    String[] status;

    public MyUploadsPdfAdapter(Context context, String[] id, String[] pdfName, String[] imgUrl, String[] viewers, String[] status) {
        this.context = context;
        this.id = id;
        this.pdfName = pdfName;
        this.imgUrl = imgUrl;
        this.viewers = viewers;
        this.status = status;
    }

    @NonNull
    @Override
    public MyUploadsPdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.mypdf_layout, parent, false);
        return new MyUploadsPdfViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyUploadsPdfViewHolder holder, int position) {
        Picasso
                .get()
                .load(imgUrl[position])
                .into(holder.imageView);
        holder.textView.setText(pdfName[position]);

        if (status[position].equals("Waiting for approval")){
            holder.pdfStatus.setText("Waiting for approval");
            holder.pdfStatus.setTextColor(R.color.purple_500);
        }else if(status[position].equals("Approved")){
            holder.pdfStatus.setText("Approved");
            holder.pdfStatus.setTextColor(R.color.green);
        }else {
            holder.pdfStatus.setText(status[position]);
            holder.pdfStatus.setTextColor(R.color.red);
        }

        holder.pdfStatus.setText(status[position]);
        holder.pdfViewers.setText(viewers[position]);
    }

    @Override
    public int getItemCount() {
        return imgUrl.length;
    }

    public class MyUploadsPdfViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        TextView pdfStatus;
        TextView pdfViewers;

        public MyUploadsPdfViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.pdfImg);
            textView = (TextView) itemView.findViewById(R.id.Pdf_name);
            pdfStatus = (TextView) itemView.findViewById(R.id.pdfStatus);
            pdfViewers = (TextView) itemView.findViewById(R.id.pdfViewers);
        }
    }
}