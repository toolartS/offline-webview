package com.example.webview;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    private void immersive() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat c =
            new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        c.hide(android.view.WindowInsets.Type.statusBars()
             | android.view.WindowInsets.Type.navigationBars());
        c.setSystemBarsBehavior(
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immersive();
        setContentView(R.layout.activity_main);

        WebView w = findViewById(R.id.webview);
        w.setOverScrollMode(View.OVER_SCROLL_NEVER);
        w.setHorizontalScrollBarEnabled(false);
        w.setVerticalScrollBarEnabled(false);

        WebSettings s = w.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);

        // 🔥 RESPONSIVE NATIVE
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSupportZoom(false);

        // 🔥 REQUIRED for local storage
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(true);

        w.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                // 🔥 Inject CSS FIX (NO TOUCH index.html)
                view.evaluateJavascript(
                  "var s=document.createElement('style');" +
                  "s.innerHTML='html,body{width:100%;height:100%;margin:0;overflow-x:hidden;}';" +
                  "document.head.appendChild(s);",
                  null
                );
            }
        });

        // ✅ LOAD DIRECT
        w.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) immersive();
    }
}
