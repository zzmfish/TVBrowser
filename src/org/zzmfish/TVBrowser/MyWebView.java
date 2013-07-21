package org.zzmfish.TVBrowser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends WebView
{
	private static final String PREF_ZOOM_DENSITY = "zoom_density";
    private static final String TAG = "MyWebView";
    private Point mCursor = new Point(100, 100);
    private Paint mPaint = new Paint();
    private boolean mEnableCursor = false;
    private int mZoomDensity;

    public MyWebView(Context context) {
        this(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    @SuppressLint("SetJavaScriptEnabled")
	public void init() {
    	mPaint.setColor(0xffff0000);
        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(new WebViewClient(){
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		loadUrl(url);
        		return true;
        	}
        });
        loadPrefs();
        setZoomDensity(mZoomDensity);
    }
    
    private void loadPrefs() {
    	loadZoomDensity();
    }
    
    private void loadZoomDensity() {
    	mZoomDensity = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(PREF_ZOOM_DENSITY, 100);
    	if (mZoomDensity == 0)
    		mZoomDensity = 100;
    	Log.d("zhouzm", "loadZoomDensity:" + mZoomDensity);
    }
    
    private void saveZoomDensity() {
    	Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
    	editor.putInt(PREF_ZOOM_DENSITY, mZoomDensity);
    	editor.commit();
    	Log.d("zhouzm", "saveZoomDensity:" + mZoomDensity);
    }
    public void increateZoomDensity() {
    	setZoomDensity((int) (mZoomDensity * 1.25));
    	saveZoomDensity();
    }
    
    public void decreateZoomDensity() {
    	setZoomDensity((int) (mZoomDensity * 0.8));
    	saveZoomDensity();
    }
    
    private void setZoomDensity(int value) {
    	if (value < 10)
    		value = 10;
    	else if (value > 1000)
    		value = 1000;
    	mZoomDensity = value;
    	try {
			Class<?> c = Class.forName("android.webkit.WebView");
			Method methods[] = c.getDeclaredMethods();
	    	for (int i = 0; i < methods.length; i ++) {
	    		Method method = methods[i];
	    		if (method.getName().equals("adjustDefaultZoomDensity")) {
	    			method.setAccessible(true);
	    			try {
						method.invoke(this, value);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			break;
	    		}
	    		
	    	}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mEnableCursor) {
			Log.d(TAG, "MyWebView.onKeyDown");
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if (mCursor.x > 0)
					mCursor.x -= 10;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if (mCursor.x < getWidth() - 1)
					mCursor.x += 10;
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				if (mCursor.y > 0)
					mCursor.y -= 10;
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				if (mCursor.y < this.getHeight() - 1)
					mCursor.y += 10;
				break;
			}
			//Log.d("zhouzm", "x=" + mCursor.x + ", y=" + mCursor.y);
			//loadUrl("javascript:console.log(document.elementFromPoint(" + mCursor.x + ", " + mCursor.y + "))");
			//loadUrl("javascript:console.log(document.elementFromPoint(" + mCursor.x + ", " + mCursor.y + ").focus())");
			loadUrl("javascript:document.elementFromPoint(" + mCursor.x + ", " + mCursor.y + ").focus()");
			invalidate();
			return true;
		}
		else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//Log.d(TAG, "MyWebView.onDraw");
		super.onDraw(canvas);
		if (mEnableCursor) {
			canvas.drawCircle(mCursor.x, mCursor.y, 3, mPaint);
		}
	}
}

