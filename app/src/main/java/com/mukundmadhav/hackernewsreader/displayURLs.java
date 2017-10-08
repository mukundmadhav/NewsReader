package com.mukundmadhav.hackernewsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class displayURLs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_urls);

        WebView webView = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        Log.i("Got:",intent.getStringExtra("url"));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(intent.getStringExtra("url"));

    }
}
