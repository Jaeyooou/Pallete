package com.example.new2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SeePost extends AppCompatActivity {
    String url;
    RequestQueue queue;
    JsonObjectRequest jsonObjectRequest;
    String Title,Contents,Date,Person;
    int Cnum,TNumber;
    private TextView textView_number,textView_title,textView_person,textView_cnum,textView_date,textView_contents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seepost);

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

        textView_number = (TextView) findViewById(R.id.s_textView1);
        textView_title = (TextView) findViewById(R.id.s_textView2);
        textView_person = (TextView) findViewById(R.id.s_textView3);
        //textView_cnum = (TextView) findViewById(R.id.s_textView4);
        textView_date = (TextView) findViewById(R.id.s_textView5);
        textView_contents = (TextView) findViewById(R.id.s_textView6);

        Intent intent = getIntent();
        String postnum = intent.getStringExtra("number");
        final Integer Postnum = Integer.parseInt(postnum);
        url = "http://jaeyooou.dothome.co.kr/Get_Board.php"; // json 저장 된 url
        queue = Volley.newRequestQueue(this); // volley 로 통신 할 큐 생성
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("result"); // result 라는 이름의 json 받기

                    Title = new String();
                    Contents = new String();
                    Date = new String();
                    Person = new String();

                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject result = jsonArray.getJSONObject(i);
                        if(Postnum==result.getInt("Board_Num"))
                        {
                            Title = result.getString("Board_Title");
                            Contents = result.getString("Board_Contents");
                            Date = result.getString("Board_Date");
                            Cnum  = result.getInt("Board_hit");
                            Person = result.getString("Board_Writer");
                            //textView_cnum.setText(Integer.toString(Cnum));
                            textView_person.setText(Person);
                            textView_date.setText(Date);
                            textView_contents.setText(Contents);
                            textView_title.setText(Title);
                            textView_number.setText(Integer.toString(Postnum));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(jsonObjectRequest);

    }
}
