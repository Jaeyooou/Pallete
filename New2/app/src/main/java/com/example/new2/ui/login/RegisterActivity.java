package com.example.new2.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.new2.R;
import com.example.new2.SelectSeason;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_ID , et_Password , et_Name , et_Age;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //이전, 홈버튼
        Button bt_prev = findViewById(R.id.bt_prev);
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_prev=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_prev);
            }
        });

        //아이디값
        et_ID = findViewById(R.id.et_ID);
        et_Password = findViewById(R.id.et_Password);
        et_Name = findViewById(R.id.et_Name);
        et_Age = findViewById(R.id.et_Age);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            //EditText 에 입력된 값을 get 해온다
            public void onClick(View view) {
                String userID = et_ID.getText().toString();
                String userPassword = et_Password.getText().toString();
                String userName = et_Name.getText().toString();
                int userAge = Integer.parseInt(et_Age.getText().toString());
                System.out.println(userID);
                System.out.println(userPassword);
                System.out.println(userName);
                System.out.println(userAge);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {//회원등록에 성공한 경우
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext() , "회원등록에 성공하였습니다" , Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this , LoginActivity.class); // 가입화면에서 -> 로그인으로
                                startActivity(intent);
                            }else{ // 회원동록에 실패한 경우
                                Toast.makeText(getApplicationContext() , "회원등록에 실패하였습니" , Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID , userPassword , userName , userAge , responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}