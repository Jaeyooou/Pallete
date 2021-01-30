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

public class Frag4 extends Fragment {
    private View view;
    ImageView frag4_iv;

    public static Frag4 newInstance(){
        Frag4 frag4 = new Frag4();
        return frag4;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_4,container,false);

        ImageView frag4_iv;
        frag4_iv = (ImageView) view.findViewById(R.id.frag4_iv);

        int[] percent = ((result)getActivity()).percent;

        if(percent[0]>=50) {
            frag4_iv.setImageResource(R.drawable.frag4_spring);
        }else if(percent[1]>=50){
            frag4_iv.setImageResource(R.drawable.frag4_summer);
        }else if(percent[2]>=50){
            frag4_iv.setImageResource(R.drawable.frag4_autumn);
        }else {
            frag4_iv.setImageResource(R.drawable.frag4_winter);
        }
        return view;
    }
}
