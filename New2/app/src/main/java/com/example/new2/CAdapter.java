package com.example.new2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CAdapter extends RecyclerView.Adapter<CAdapter.ViewHolder>{
    private ArrayList<Citem> mList;
    public CAdapter(ArrayList<Citem> list){
        this.mList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView_number,textView_title,textView_person,textView_cnum,textView_date;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView_number = (TextView) itemView.findViewById(R.id.c_textView1);
            textView_title = (TextView) itemView.findViewById(R.id.c_textView2);
            textView_person = (TextView) itemView.findViewById(R.id.c_textView3);
            textView_cnum = (TextView) itemView.findViewById(R.id.c_textView4);
            textView_date = (TextView) itemView.findViewById(R.id.c_textView5);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), SeePost.class);
                    intent.putExtra("number", Integer.toString(mList.get(getAdapterPosition()).Getnumber()));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView_title.setText(String.valueOf(mList.get(position).Gettitle()));
        holder.textView_number.setText(Integer.toString(Integer.valueOf(mList.get(position).Getnumber())));

        holder.textView_person.setText(String.valueOf(mList.get(position).Getperson()));
        holder.textView_cnum.setText(Integer.toString(Integer.valueOf(mList.get(position).Getcnum())));
        holder.textView_date.setText(String.valueOf(mList.get(position).Getdate()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



}