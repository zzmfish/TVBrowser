package org.zzmfish.TVBrowser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends WebView
{
    private static final String TAG = "MyWebView";
    private Point mCursor = new Point(100, 100);
    private Paint mPaint = new Paint();
    private boolean mEnableCursor = false;

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
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (canGoBack())
				goBack();
			else
				getContext().startActivity(new Intent(this.getContext(), HomeActivity.class));
			return true;
		}
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

