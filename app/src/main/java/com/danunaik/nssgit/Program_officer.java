package com.danunaik.nssgit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Program_officer extends AppCompatActivity {
CircleImageView rajput_pic;
AppCompatButton know_more_rajput;
WebView about_rajput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_officer);
        know_more_rajput=findViewById(R.id.know_more_rajput);
        about_rajput=findViewById(R.id.webview_rajput_program);
        WebSettings settings=about_rajput.getSettings();
        settings.setJavaScriptEnabled(true);
        know_more_rajput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                about_rajput.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        about_rajput.loadUrl("https://sapphire-lainey-83.tiiny.site");
                    }
                },100);

            }
        });


    }
}