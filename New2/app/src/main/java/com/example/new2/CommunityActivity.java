package com.example.new2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Citem> list = new ArrayList();
    JsonObjectRequest jsonObjectRequest;
    String[] Title,Contents,Date,Person;
    Integer[] Cnum,TNumber;

    String userID;
    String url;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        //인텐트 받아오기
        Intent gIntent = getIntent();
        userID = gIntent.getStringExtra("userID");

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

        Button button = findViewById(R.id.c_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), exActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.c_recyclerView);
        url = "http://jaeyooou.dothome.co.kr/Get_Board.php"; // json 저장 된 url
        queue = Volley.newRequestQueue(this); // volley 로 통신 할 큐 생성
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("result"); // result 라는 이름의 json 받기

                    Title = new String[jsonArray.length()];
                    Contents = new String[jsonArray.length()];
                    Date = new String[jsonArray.length()];
                    Cnum = new Integer[jsonArray.length()];
                    TNumber = new Integer[jsonArray.length()];
                    Person = new String[jsonArray.length()];

                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject result = jsonArray.getJSONObject(i);

                        Title[i] = result.getString("Board_Title");
                        Contents[i] = result.getString("Board_Contents");
                        Date[i] = result.getString("Board_Date");
                        Cnum[i]  = result.getInt("Board_hit");
                        Person[i] = result.getString("Board_Writer");
                        TNumber[i] = result.getInt("Board_Num");


                    }
                    new Description().execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if(lastPosition == totalCount){
                    //아이템 추가 ! 입맛에 맞게 설정하시면됩니다.
                }
            }
        });




        queue.add(jsonObjectRequest);


    }
    private class Description extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(CommunityActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }
        @Override
        protected Void doInBackground(Void... voids) {

            for(int i=0;i<Title.length;i++){
                list.add(new Citem(TNumber[i],Title[i],Person[i],Cnum[i],Date[i]));
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            CAdapter myAdapter = new CAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }
}
