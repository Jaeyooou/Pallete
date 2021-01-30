package com.example.new2;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag2 extends Fragment {
    private View view;
    ImageView frag2_iv;

    public static Frag2 newInstance(){
        Frag2 frag2 = new Frag2();
        return frag2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_2,container,false);
        frag2_iv = (ImageView) view.findViewById(R.id.frag2_iv);

        int[] percent = ((result)getActivity()).percent;

        if(percent[0]>=50) {
            frag2_iv.setImageResource(R.drawable.frag2_spring);
        }else if(percent[1]>=50){
            frag2_iv.setImageResource(R.drawable.frag2_summer);
        }else if(percent[2]>=50){
            frag2_iv.setImageResource(R.drawable.frag2_autumn);
        }else{
            frag2_iv.setImageResource(R.drawable.frag2_winter);
        }
        return view;
    }
}
