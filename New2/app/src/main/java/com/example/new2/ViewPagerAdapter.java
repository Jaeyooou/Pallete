package com.example.new2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //프래그먼트를 보여주는 처리를 구현한 곳
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return Frag1.newInstance();
            case 1:
                return Frag2.newInstance();
            case 2:
                return Frag3.newInstance();
            case 3:
                return Frag4.newInstance();
            case 4:
                return Frag5.newInstance();
            default:
                return null;
        }
    }

    // 프래그먼트 개수
    @Override
    public int getCount() {
        return 5;
    }

    // 상단의 탑 레이아웃 인디케이터 쪽에 텍스를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "결과";
            case 1:
                return "특징";
            case 2:
                return "코디";
            case 3:
                return "연예인";
            case 4:
                return "화장품";
            default:
                return null;
        }
    }
}