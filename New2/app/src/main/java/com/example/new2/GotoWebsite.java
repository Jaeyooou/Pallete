package com.example.new2;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class GotoWebsite extends AppCompatActivity {
    WebView newwebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gotowebsite);

        Intent intent = getIntent();
        String url = intent.getStringExtra("OliveyoungUrl");
        newwebView = (WebView)findViewById(R.id.g_webView);
        newwebView.setWebViewClient(new WebViewClient());
        newwebView.getSettings().setJavaScriptEnabled(true);
        newwebView.loadUrl(url);


    }
}
