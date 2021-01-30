package com.example.new2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag3 extends Fragment {
    private View view;
    ImageView frag3_iv;

    public static Frag3 newInstance(){
        Frag3 frag3 = new Frag3();
        return frag3;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_3,container,false);

        frag3_iv = (ImageView) view.findViewById(R.id.frag3_iv);
        int[] percent = ((result)getActivity()).percent;

        if(percent[0]>=50) {
            frag3_iv.setImageResource(R.drawable.frag3_spring);
        }else if(percent[1]>=50){
            frag3_iv.setImageResource(R.drawable.frag3_summer);
        }else if(percent[2]>=50){
            frag3_iv.setImageResource(R.drawable.frag3_autumn);
        }else{
            frag3_iv.setImageResource(R.drawable.frag3_winter);
        }
        return view;
    }
}
