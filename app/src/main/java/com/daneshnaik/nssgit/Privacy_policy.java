package com.daneshnaik.nssgit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class Privacy_policy extends AppCompatActivity {
WebView privacy_policy;
ProgressBar progressBar_poilicy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        privacy_policy=findViewById(R.id.webview_privacy_policy);
        progressBar_poilicy=findViewById(R.id.progressbar_privacy_policy);
        WebSettings settings=privacy_policy.getSettings();
        settings.setJavaScriptEnabled(true);
        final ProgressDialog progressDialog=new ProgressDialog(Privacy_policy.this);
        progressBar_poilicy.setVisibility(View.VISIBLE);
        privacy_policy.loadUrl("https://www.freeprivacypolicy.com/live/b7cd6715-33f8-40e7-93fd-5a6de6bc1ecf");
 new Handler().postDelayed(new Runnable() {
     @Override
     public void run() {

         progressBar_poilicy.setVisibility(View.INVISIBLE);
     }
 },2000);




    }
}