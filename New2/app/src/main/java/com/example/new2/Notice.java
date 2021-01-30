package com.example.new2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Notice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        //이전, 홈버튼
        Button bt_exit = findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_exit=new Intent(getApplicationContext(), ilhwan_test.class);
                startActivity(intent_exit);
            }
        });
    }
}