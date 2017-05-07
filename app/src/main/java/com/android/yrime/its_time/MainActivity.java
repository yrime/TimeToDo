package com.android.yrime.its_time;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        goToWeb();
    }

    private void goToWeb(){
        WebView mapApiView = (WebView) findViewById(R.id.mapWebView);
        mapApiView.addJavascriptInterface(new myJavaScryptInterfase(), "test");
        mapApiView.getSettings().setJavaScriptEnabled(true);
        InputStream is;
        try{
            is = getAssets().open("yanmap.html");
            byte[] bytebuffer = new byte[is.available()];
            if(is.read(bytebuffer) <= 0){
                throw new IOException("ff");
            }
            is.close();
            String htmlText = new String(bytebuffer);
            mapApiView.loadDataWithBaseURL("http://ru.yandex.api.yandexmapswebviewexample.ymapapp",
                    htmlText, "text/html", "UTF-8", null);
        }catch (IOException e) {
            System.out.println(e);
        }
        mapApiView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }
    private class myJavaScryptInterfase{
        @android.webkit.JavascriptInterface
        public void getGreeting(String s) {
            System.out.println(s);
        }
    }

}

