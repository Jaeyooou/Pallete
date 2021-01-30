package com.example.new2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private ArrayList<myItem> mList;
    public RecyclerAdapter(ArrayList<myItem> list){
        this.mList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView_img;
        private TextView textView_title,textView_price,textView_color;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView_img = (ImageView) itemView.findViewById(R.id.i_imageView);
            textView_price = (TextView) itemView.findViewById(R.id.i_textView2);
            textView_title = (TextView) itemView.findViewById(R.id.i_textView1);
            textView_color = (TextView) itemView.findViewById(R.id.i_textView3);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),GotoWebsite.class);
                    intent.putExtra("OliveyoungUrl", mList.get(getAdapterPosition()).getUrl());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.textView_price.setText(String.valueOf(mList.get(position).getprice()));
        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
        holder.textView_color.setText(String.valueOf(mList.get(position).getColor()));
        /*ImageLoadTask task = new ImageLoadTask(mList.get(position).getImg_url(),holder.imageView_img);

        task.execute();*/

        Glide.with(holder.itemView).load(mList.get(position).getImg_url()).override(400,400).into(holder.imageView_img);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



}
