package org.zzmfish.TVBrowser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
		case R.id.menu_home:
			startActivity(new Intent(this, HomeActivity.class));
			return true;
		case R.id.menu_add_to_home:
			Log.d("zhouzm", "add_to_home");
		    DialogFragment newFragment = new EditBookmarkDialog();
		    newFragment.show(getFragmentManager(), "missiles");
			return true;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
		
	}
}

class EditBookmarkDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.edit_bookmark, null))
               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            	   @Override
                   public void onClick(DialogInterface dialog, int id) {
                   }
               });      
        return builder.create();
    }
}

