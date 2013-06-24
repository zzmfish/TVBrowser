package org.zzmfish.TVBrowser;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Main extends Activity
{
    WebView webview;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        webview = (WebView)findViewById(R.id.webview);
        webview.loadUrl("http://www.baidu.com");
    }
}
