package org.zzmfish.TVBrowser;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("zhouzm", "HomeActivity.onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
