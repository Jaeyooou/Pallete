package com.example.new2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectSeason extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //이전, 홈버튼
        Button bt_prev = findViewById(R.id.bt_prev);
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_prev=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_prev);
            }
        });

        Button bt_home = findViewById(R.id.bt_home);
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_home=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_home);
            }
        });

        ImageView imageview1 = findViewById(R.id.imageView3);
        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(), ShoppingActivity2.class);
                startActivity(intent1);
            }
        });


        ImageView imageview2 = findViewById(R.id.imageView4);
        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(getApplicationContext(), ShoppingActivity3.class);
                startActivity(intent2);
            }
        });

        ImageView imageview3 = findViewById(R.id.imageView5);
        imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(getApplicationContext(), ShoppingActivity4.class);
                startActivity(intent3);
            }
        });
        ImageView imageview4 = findViewById(R.id.imageView6);
        imageview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(getApplicationContext(), ShoppingActivity5.class);
                startActivity(intent4);
            }
        });
    }
}
