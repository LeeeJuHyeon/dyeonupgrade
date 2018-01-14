package dgu.dyeon;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    private WebView webview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        startActivity(new Intent(this, SplashActivity.class));
        
        webview = (WebView) findViewById(R.id.fullscreen_content);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setDisplayZoomControls(false);

        webview.setHorizontalScrollBarEnabled(false); //가로 스크롤
        webview.setVerticalScrollBarEnabled(false); //세로 스크롤
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://dyeon.net/");
        webview.setWebViewClient(new BlogWebViewClient());

        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                webview.reload();
                refreshLayout.setRefreshing(false);
            }
        });


        ImageButton b_back = (ImageButton) findViewById(R.id.back);
        ImageButton b_forwd = (ImageButton) findViewById(R.id.forward);
        ImageButton b_rload= (ImageButton) findViewById(R.id.reload);
        ImageButton b_home = (ImageButton) findViewById(R.id.home);
        ImageButton b_mypage = (ImageButton) findViewById(R.id.mypage);
        ImageButton b_time = (ImageButton) findViewById(R.id.timetable);

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webview.canGoBack())
                    webview.goBack();
            }
        });

        b_forwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webview.canGoForward())
                    webview.goForward();
            }
        });

        b_rload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.reload();
                webview.scrollTo(0,0);
            }
        });

        b_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.loadUrl("https://dyeon.net/");
                webview.scrollTo(0,0);
            }
        });

        b_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.loadUrl("https://dyeon.net/messages");
                webview.scrollTo(0,0);
            }
        });

        b_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.loadUrl("https://dyeon.net/timetable");
                webview.scrollTo(0,0);
            }
        });

    }

    private SwipeRefreshLayout refreshLayout;

    private class BlogWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    long tempTime;long intervalTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String home = "https://dyeon.net/";
        boolean b = home.equals(webview.getOriginalUrl());

        if(webview.canGoBack() && !b){
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed(){
            tempTime = System.currentTimeMillis();
            intervalTime = tempTime - backPressedTime;

            if(0<=intervalTime && FINISH_INTERVAL_TIME>=intervalTime){
                super.onBackPressed();
            }else{
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "한 번 더 '뒤로' 버튼을 누르면 종료되디연", Toast.LENGTH_SHORT).show();
            }

    }
}
