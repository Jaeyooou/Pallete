package com.example.new2;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new2.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    public static Context context_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this;

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        Button bt_logout = findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_logout=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_logout);
            }
        });

        Button button1 = findViewById(R.id.bt_test);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(), Notice.class);
                startActivity(intent1);
            }
        });


        Button button2 = findViewById(R.id.bt_shop);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(getApplicationContext(), SelectSeason.class);
                startActivity(intent2);
            }
        });

        Button button3 = findViewById(R.id.bt_community);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(getApplicationContext(), CommunityActivity.class);
                intent3.putExtra("userID",userID);
                startActivity(intent3);
            }
        });
    }

    void show(){ // 입력 가능한 팝업 띄우기
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("본인확인");
        builder.setMessage("비밀번호를 입력해주세요");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),edittext.getText().toString() , Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }


}