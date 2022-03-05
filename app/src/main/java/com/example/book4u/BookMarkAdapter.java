package com.example.book4u;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.BookMarkHolder> {
    View view;
    LayoutInflater layoutInflater;
    Context context;
    int img[] ;
    String heading[];
    String totalReaders[];
    String description[];

        public BookMarkAdapter(Context context, int img[], String heading[], String[] totalReaders,  String[] description) {
            this.context = context;
            this.img = img;
            this.heading = heading;
            this.totalReaders = totalReaders;
            this.description = description;
        }
        @NonNull
        @Override
        public BookMarkAdapter.BookMarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.bookmarkpdf_layout, parent, false);
            return new BookMarkHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookMarkAdapter.BookMarkHolder holder, int position) {
            holder.BookmarkImg.setImageResource(img[position]);
            holder.pdfName.setText(heading[position]);
            holder.totalReaders.setText(totalReaders[position]);
            holder.description.setText(description[position]);
        }

        @Override
        public int getItemCount() {
            return heading.length;
        }


        public class BookMarkHolder extends RecyclerView.ViewHolder {
            ImageView BookmarkImg;
            TextView pdfName;
            TextView totalReaders;
            TextView description;

            public BookMarkHolder(@NonNull View itemView) {
                super(itemView);
                BookmarkImg = (ImageView) itemView.findViewById(R.id.pdfImg);
                pdfName = (TextView) itemView.findViewById(R.id.Pdf_name);
                totalReaders = (TextView) itemView.findViewById(R.id.total_readers);
                description = (TextView) itemView.findViewById(R.id.description);
            }

        }
}
