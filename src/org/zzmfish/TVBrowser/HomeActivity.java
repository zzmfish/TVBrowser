package org.zzmfish.TVBrowser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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

	public void openUrl(View view) {
		EditText editText = (EditText) findViewById(R.id.url);
		String url = editText.getText().toString();
		Intent intent = new Intent(this, Main.class);
		intent.putExtra("url", url);
	    startActivity(intent);
	}
}
