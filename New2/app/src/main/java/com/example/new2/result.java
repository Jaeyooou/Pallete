package com.example.new2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

//그래프
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;


public class result extends AppCompatActivity {
    int[] colorArray;
    private FragmentPagerAdapter fragmentPagerAdapter;
    public int percent[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //인텐트 받기
        Intent intent = getIntent();
        percent = intent.getExtras().getIntArray("percent");

        if(percent[0] != 0 || percent[2] != 0){
            colorArray = new int[]{Color.rgb(250,224,125), Color.rgb(165,146,120)};
        }else{
            colorArray = new int[]{Color.rgb(82,181,222), Color.rgb(200,198,223)};
        }

        //프래그먼트 실행
        ViewPager viewPager = findViewById(R.id.viewPager);
        fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private ArrayList<PieEntry> data1() {
        ArrayList<PieEntry> datavalue = new ArrayList<>();

        if (percent[0] != 0 || percent[2] != 0) {
            datavalue.add(new PieEntry(percent[0], "Spring"));
            datavalue.add(new PieEntry(percent[2], "Autumn"));
        } else {
            datavalue.add(new PieEntry(percent[1], "Summer"));
            datavalue.add(new PieEntry(percent[3], "Winter"));
        }
        return datavalue;
    }
    public void pieChartChange(PieChart pieChart){
        PieDataSet pieDataSet = new PieDataSet(data1(),"Tone");
        pieDataSet.setColors(colorArray);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setDrawEntryLabels(true);
        pieChart.setUsePercentValues(true);
        pieData.setValueTextSize(30);
        pieChart.setCenterText("Personal Color");
        pieChart.setCenterTextSize(25);
        pieChart.setHoleRadius(30);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}