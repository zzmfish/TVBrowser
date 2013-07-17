package org.zzmfish.TVBrowser;

import org.zzmfish.TVBrowser.Bookmarks.Bookmark;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("zhouzm", "HomeActivity.onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Bookmarks.getInstance().load(getApplicationContext());
		showBookmarks();
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
		openUrl(url);
	}
	
	public void openUrl(String url) {
		Intent intent = new Intent(this, WebActivity.class);
		intent.putExtra("url", url);
	    startActivity(intent);
	}
	
	private class BookmarksAdapter extends BaseAdapter {
		private Bookmarks mBookmarks = Bookmarks.getInstance();
		
		@Override
		public int getCount() {
			return mBookmarks.count();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			Bookmark bookmark = mBookmarks.get(position);
			TextView textView;
			if (convertView == null)
				textView = new TextView(HomeActivity.this);
			else
				textView = (TextView) convertView;
			textView.setText(bookmark.getName());
			return textView;
		}
		
	}

	public void showBookmarks() {
		GridView gridView = (GridView) findViewById(R.id.bookmarks);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					Bookmark bookmark = Bookmarks.getInstance().get(position);
					openUrl(bookmark.getUrl());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		gridView.setAdapter(new BookmarksAdapter());
	}
	
	public void exit(View view) {
		finish();
	}
}
