package com.danunaik.nssgit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class recent_activity_webview extends AppCompatActivity {
WebView mywebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_webview);
        mywebview =findViewById(R.id.recent_activities_webviewd);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=getIntent();
                String links=intent.getStringExtra("link");
                mywebview.loadUrl(links);
                WebSettings settings=mywebview.getSettings();
                settings.setJavaScriptEnabled(true);
                mywebview.setWebViewClient(new WebViewClient());
               
            }
        },2000);
    }
}