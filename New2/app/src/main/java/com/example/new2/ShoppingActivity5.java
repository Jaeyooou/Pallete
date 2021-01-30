package com.example.new2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ShoppingActivity5 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<myItem> list = new ArrayList();
    JsonObjectRequest jsonObjectRequest;
    String[] Get_Url;
    String[] Get_N;
    String[] Get_Color;
    String url;
    RequestQueue queue;

    Integer Category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping5);

        //이전, 홈버튼
        Button bt_prev = findViewById(R.id.bt_prev);
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_prev=new Intent(getApplicationContext(), SelectSeason.class);
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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        url = "http://jaeyooou.dothome.co.kr/Get_url_Winter.php"; // json 저장 된 url
        queue = Volley.newRequestQueue(this); // volley 로 통신 할 큐 생성
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("result"); // result 라는 이름의 json 받기
                    Get_Url = new String[jsonArray.length()];
                    Get_N = new String[jsonArray.length()];
                    Get_Color = new String[jsonArray.length()];
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject result = jsonArray.getJSONObject(i);
                        Get_Url[i] = result.getString("Url");
                        Get_N[i] = result.getString("Category");
                        Get_Color[i] = result.getString("Color");

                    }
                    //new Description().execute();

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
        final Button button1 = findViewById(R.id.btn1);
        final Button button2 = findViewById(R.id.btn2);
        final Button button3 = findViewById(R.id.btn3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                button1.setSelected(true);
                button2.setSelected(false);
                button3.setSelected(false);

                Category=1;
                list.clear();
                new Description().execute();
            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setSelected(false);
                button2.setSelected(true);
                button3.setSelected(false);


                Category=2;
                list.clear();
                new Description().execute();
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setSelected(false);
                button2.setSelected(false);
                button3.setSelected(true);

                Category=3;
                list.clear();
                new Description().execute();
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
            progressDialog = new ProgressDialog(ShoppingActivity5.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                if(Category==1)
                {
                    for(int i=0;i<Get_Url.length;i++)
                    {
                        if(!Get_N[i].equals("Lip")) continue;
                        String webUrl = Get_Url[i];
                        Document doc = Jsoup.connect(webUrl).get();
                        Elements con = doc.select("div[class=prd_img]");
                        String imgUrl = con.select("img[id=mainImg]").attr("src");

                        Elements textcon = doc.select("div[class=prd_info]");
                        String textUrl = textcon.select("p[class=prd_name]").text();

                        Elements textcon_2=doc.select("div[class=price]");
                        String textUrl_2 = textcon_2.select("span[class=price-2] strong").text();
                        String textUrl_2_1=textcon_2.select("span[class=price-2] span").text();

                        list.add(new myItem(textUrl,textUrl_2+textUrl_2_1,imgUrl,webUrl,Get_Color[i]));

                    }
                }
                else if(Category==2)
                {
                    for(int i=0;i<Get_Url.length;i++)
                    {
                        if(!Get_N[i].equals("Shadow")) continue;
                        String webUrl = Get_Url[i];
                        Document doc = Jsoup.connect(webUrl).get();
                        Elements con = doc.select("div[class=prd_img]");
                        String imgUrl = con.select("img[id=mainImg]").attr("src");

                        Elements textcon = doc.select("div[class=prd_info]");
                        String textUrl = textcon.select("p[class=prd_name]").text();

                        Elements textcon_2=doc.select("div[class=price]");
                        String textUrl_2 = textcon_2.select("span[class=price-2] strong").text();
                        String textUrl_2_1=textcon_2.select("span[class=price-2] span").text();

                        list.add(new myItem(textUrl,textUrl_2+textUrl_2_1,imgUrl,webUrl,Get_Color[i]));

                    }
                }
                else if(Category==3)
                {
                    for(int i=0;i<Get_Url.length;i++)
                    {
                        if(!Get_N[i].equals("Blusher")) continue;
                        String webUrl = Get_Url[i];
                        Document doc = Jsoup.connect(webUrl).get();
                        Elements con = doc.select("div[class=prd_img]");
                        String imgUrl = con.select("img[id=mainImg]").attr("src");

                        Elements textcon = doc.select("div[class=prd_info]");
                        String textUrl = textcon.select("p[class=prd_name]").text();

                        Elements textcon_2=doc.select("div[class=price]");
                        String textUrl_2 = textcon_2.select("span[class=price-2] strong").text();
                        String textUrl_2_1=textcon_2.select("span[class=price-2] span").text();

                        list.add(new myItem(textUrl,textUrl_2+textUrl_2_1,imgUrl,webUrl,Get_Color[i]));

                    }
                }


               /*String webUrl = "https://www.oliveyoung.co.kr/store/goods/getGoodsDetail.do?goodsNo=A000000123331&dispCatNo=1000001000200010004";
                Document doc = Jsoup.connect("https://www.oliveyoung.co.kr/store/goods/getGoodsDetail.do?goodsNo=A000000123331&dispCatNo=1000001000200010004").get();
                Elements con = doc.select("div[class=prd_img]");
                String imgUrl = con.select("img[id=mainImg]").attr("src");

                Elements textcon = doc.select("div[class=prd_info]");
                String textUrl = textcon.select("p[class=prd_name]").text();

                Elements textcon_2=doc.select("div[class=price]");
                String textUrl_2 = textcon_2.select("span[class=price-2] strong").text();
                String textUrl_2_1=textcon_2.select("span[class=price-2] span").text();

                list.add(new myItem(textUrl,textUrl_2+textUrl_2_1,imgUrl,webUrl));

                String webUrl2 = "https://www.oliveyoung.co.kr/store/goods/getGoodsDetail.do?goodsNo=A000000002345&dispCatNo=1000001000800040003&trackingCd=Search_Result";
                Document doc2 = Jsoup.connect("https://www.oliveyoung.co.kr/store/goods/getGoodsDetail.do?goodsNo=A000000002345&dispCatNo=1000001000800040003&trackingCd=Search_Result").get();
                Elements con2 = doc2.select("div[class=prd_img]");
                String imgUrl2 = con2.select("img[id=mainImg]").attr("src");

                Elements textcon2 = doc2.select("div[class=prd_info]");
                String textUrl2 = textcon2.select("p[class=prd_name]").text();

                Elements textcon2_2=doc2.select("div[class=price]");
                String textUrl2_2 = textcon2_2.select("span[class=price-2] strong").text();
                String textUrl2_2_1=textcon2_2.select("span[class=price-2] span").text();

                list.add(new myItem(textUrl2,textUrl2_2+textUrl2_2_1,imgUrl2,webUrl2));

                String webUrl3 = "https://www.oliveyoung.co.kr/store/goods/getGoodsDetail.do?goodsNo=A000000012239&dispCatNo=1000001000200010005&trackingCd=Search_Result";
                Document doc3 = Jsoup.connect("https://www.oliveyoung.co.kr/store/goods/getGoodsDetail.do?goodsNo=A000000012239&dispCatNo=1000001000200010005&trackingCd=Search_Result").get();
                Elements con3 = doc3.select("div[class=prd_img]");
                String imgUrl3 = con3.select("img[id=mainImg]").attr("src");

                Elements textcon3 = doc3.select("div[class=prd_info]");
                String textUrl3 = textcon3.select("p[class=prd_name]").text();

                Elements textcon3_2=doc3.select("div[class=price]");
                String textUrl3_2 = textcon3_2.select("span[class=price-2] strong").text();
                String textUrl3_2_1=textcon3_2.select("span[class=price-2] span").text();

                list.add(new myItem(textUrl3,textUrl3_2+textUrl3_2_1,imgUrl3,webUrl3));*/


            }

            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            RecyclerAdapter myAdapter = new RecyclerAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendImageRequest();
                new AsyncTask(){
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try{
                            Document doc = Jsoup.connect("https://www.oliveyoung.co.kr/store/goods/getGoodsDetail.do?goodsNo=A000000123331&dispCatNo=1000001000200010004").get();
                            Elements con = doc.select("div[class=prd_img]");
                            String imgUrl = con.select("img[id=mainImg]").attr("src");
                            ImageLoadTask task = new ImageLoadTask(imgUrl, imageView);
                            task.execute();
                            Elements textcon = doc.select("div[class=prd_info]");
                            String textUrl = textcon.select("p[class=prd_name]").text();
                            text = text+textUrl;
                            Elements textcon2=doc.select("div[class=price]");
                            String textUrl2 = textcon2.select("span[class=price-2] strong").text();
                            String textUrl2_1=textcon2.select("span[class=price-2] span").text();
                            text2= text2+textUrl2+textUrl2_1;
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                        return null;
                    }

                }.execute();
                textView.setText(text);
                textView2.setText(text2);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask(){
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try{
                            Document doc_2 = Jsoup.connect("https://www.oliveyoung.co.kr/store/goods/getGoodsDetail.do?goodsNo=A000000123331&dispCatNo=1000001000200010004").get();
                            Elements con_2 = doc_2.select("div[class=contEditer]");
                            String imgUrl_2 = con_2.select("img alt").attr("src");
                            ImageLoadTask task_2 = new ImageLoadTask(imgUrl_2, imageView2);
                            task_2.execute();

                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                        return null;
                    }

                }.execute();
            }
        });*/
    /*public void sendImageRequest() {
        String url = "https://image.oliveyoung.co.kr/uploads/images/editor/QuickUpload/SYS/image/20200722113236/qcna_20200722113236.jpg";
        ImageLoadTask task = new ImageLoadTask(url, imageView);
        task.execute();
        String url2 = "https://image.oliveyoung.co.kr/uploads/images/editor/QuickUpload/C17757/image/20200629150909/qc17_20200629150909.jpg";
        ImageLoadTask task2 = new ImageLoadTask(url2, imageView2);
        task2.execute();
        String url3 = "https://image.oliveyoung.co.kr/uploads/images/editor/QuickUpload/C17757/image/20200629150921/qc17_20200629150921.jpg";
        ImageLoadTask task3 = new ImageLoadTask(url3, imageView3);
        task3.execute();
    }*/
}
