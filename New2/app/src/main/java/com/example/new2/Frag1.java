package com.example.new2;

//프래그먼트
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//그래프
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new2.ui.login.LoginActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Frag1 extends Fragment {
    private View view;
    PieChart pieChart;
    ImageView frag1_iv;

    public static Frag1 newInstance() {
        Frag1 frag1 = new Frag1();
        return frag1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_1, container, false);
        pieChart = view.findViewById(R.id.piechart);
        frag1_iv = view.findViewById(R.id.frag1_iv);
        frag1_iv.setImageResource(R.drawable.your_result);

        ((result)getActivity()).pieChartChange(pieChart);

        return view;
    }
}