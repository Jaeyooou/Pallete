package com.example.new2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag5 extends Fragment {
    private View view;
    ImageView frag5_iv;
    Button store_bt;
    Button home_bt;

    public static Frag5 newInstance(){
        Frag5 frag5 = new Frag5();
        return frag5;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_5,container,false);

        ImageView frag5_iv;
        Button store_bt;
        frag5_iv = (ImageView) view.findViewById(R.id.frag5_iv);
        store_bt = (Button) view.findViewById(R.id.store_bt);
        home_bt = (Button) view.findViewById(R.id.home_bt);

        store_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(),SelectSeason.class);
                startActivity(intent1);
            }
        });
        home_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(),MainActivity.class);
                startActivity(intent2);
            }
        });
        int[] percent = ((result)getActivity()).percent;

        if(percent[0]>=50) {
            frag5_iv.setImageResource(R.drawable.frag5_spring);
        }else if(percent[1]>=50){
            frag5_iv.setImageResource(R.drawable.frag5_summer);
        }else if(percent[2]>=50){
            frag5_iv.setImageResource(R.drawable.frag5_autumn);
        }else {
            frag5_iv.setImageResource(R.drawable.frag5_winter);
        }
        return view;
    }
}
