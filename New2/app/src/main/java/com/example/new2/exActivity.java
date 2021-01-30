package com.example.new2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class exActivity extends AppCompatActivity {
    String Board_Title , Board_Contents , Board_Date  , Board_Writer , Board_Password;
    String url = "http://jaeyooou.dothome.co.kr/insert_recode.php";
    String ID ;
    EditText Title ;
    EditText Contents;
    EditText password ;
    RequestQueue requestQueue;
    String TitleHolder , ContentsHolder , DateHolder , WriterHolder , PasswordHolder;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex);

        //이전, 홈버튼
        Button bt_prev = findViewById(R.id.bt_prev);
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_prev=new Intent(getApplicationContext(), CommunityActivity.class);
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

        Intent intent = getIntent();
        ID = intent.getStringExtra("userID");
        Button addbutton = (Button)findViewById(R.id.bt_go);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        progressDialog = new ProgressDialog(exActivity.this);

        Title = (EditText)findViewById(R.id.et_bd_title);
        Contents = (EditText)findViewById(R.id.et_bd_contents);
        password = (EditText)findViewById(R.id.et_bd_password);

        Date time = new Date();
        Board_Date = format.format(time);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("please Wait , We are inserting Your data on db");
                progressDialog.show();
                Board_Title = Title.getText().toString();
                Board_Contents = Contents.getText().toString();
                Board_Writer = ID;
                Board_Password = password.getText().toString();
                progressDialog.dismiss();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(exActivity.this , response , Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(exActivity.this , error.toString() , Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String , String> getParams(){
                        System.out.println("hihihihi");
                        Map<String , String> params = new HashMap<String , String>();
                        params.put("Board_Title" , Board_Title);
                        params.put("Board_Contents" , Board_Contents);
                        params.put("Board_Date" , Board_Date);
                        params.put("Board_Writer" ,Board_Writer );
                        params.put("Board_Password" , Board_Password);
                        System.out.println(params);
                        return params;
                    }
                };
                requestQueue = Volley.newRequestQueue(exActivity.this);
                requestQueue.add(stringRequest);
                Intent intent_exit = new Intent(getApplicationContext(), CommunityActivity.class);
            }
        });
    }

}