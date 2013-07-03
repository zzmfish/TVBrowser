package org.zzmfish.TVBrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class WebActivity extends Activity
{
    MyWebView webview;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        
        String url = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	url = getIntent().getExtras().getString("url");
        }
        if (url == null)
        	url = "about:blank";

        webview = (MyWebView)findViewById(R.id.webview);
        webview.init();
        webview.loadUrl(url);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			startActivity(new Intent(this, HomeActivity.class));
			return true;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
		
	}
    
    
}
