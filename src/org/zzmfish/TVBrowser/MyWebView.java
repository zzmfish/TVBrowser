package org.zzmfish.TVBrowser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;

public class MyWebView extends WebView
{
    private static final String TAG = "MyWebView";
    private Point mCursor = new Point(100, 100);

    public MyWebView(Context context) {
        this(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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
		Log.d("zhouzm", "x=" + mCursor.x + ", y=" + mCursor.y);
		boolean result = super.onKeyDown(keyCode, event);
		this.invalidate();
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "MyWebView.onDraw");
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(0xffff0000);
		canvas.drawCircle(mCursor.x, mCursor.y, 3, paint);
	}


}

