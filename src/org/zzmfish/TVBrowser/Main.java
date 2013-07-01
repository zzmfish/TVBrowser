package org.zzmfish.TVBrowser;

import android.app.Activity;
import android.os.Bundle;

public class Main extends Activity
{
    MyWebView webview;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        webview = (MyWebView)findViewById(R.id.webview);
        webview.loadUrl("http://www.baidu.com");
    }
}
