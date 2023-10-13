package com.daneshnaik.nssgit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class About_redcross extends AppCompatActivity {
WebView redcross_webview;
ProgressBar progressBar_redcross;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_redcross);
        redcross_webview=findViewById(R.id.redcross_webview);
        progressBar_redcross=findViewById(R.id.redcross_progress);
        WebSettings settings=redcross_webview.getSettings();
        settings.setJavaScriptEnabled(true);

       progressBar_redcross.setVisibility(View.VISIBLE);
        redcross_webview.loadUrl("https://nss--nssklsgit20.repl.co/irs");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressBar_redcross.setVisibility(View.INVISIBLE);
            }
        },2000);



    }
}