package com.example.webview;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

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
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);

        // 🚨 LOAD SHELL, NOT INDEX
        w.loadUrl("file:///android_asset/shell.html");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) immersive();
    }
}
