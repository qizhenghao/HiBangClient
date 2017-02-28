package cn.hibang.liuzhiwei.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
/**
 * 具有弹性效果的ListView。主要是实现父类dispatchTouchEvent方法和OnGestureListener中onScroll方法。
 * @author E
 */
public class PullDownListview extends ListView implements OnGestureListener{
	
	private Context context = null;
	private boolean outBound = false;
	private int distance;
	private int firstOut;
	
	public  PullDownListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public PullDownListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	public PullDownListview(Context context) {
		super(context);
		this.context = context;
	}
	
	GestureDetector lisGestureDetector = new GestureDetector(context, this);
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int act = event.getAction();
		if ((act == MotionEvent.ACTION_UP || act == MotionEvent.ACTION_CANCEL)
		&& outBound) {
		outBound = false;
		// scroll back
		}
		if (!lisGestureDetector.onTouchEvent(event)) {
			outBound = false;
		} else {
			outBound = true;
		}
		Rect rect = new Rect();  
        getLocalVisibleRect(rect);  
        TranslateAnimation am = new TranslateAnimation( 0, 0, -rect.top, 0);  
        am.setDuration(300);  
        startAnimation(am);  
        scrollTo(0, 0);
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		int firstPos = getFirstVisiblePosition();
		int lastPos = getLastVisiblePosition();
		int itemCount = getCount();
		// outbound Top
		if (outBound && firstPos != 0 && lastPos != (itemCount - 1)) {
		scrollTo(0, 0);
		return false;
		}
		View firstView = getChildAt(firstPos);
		if (!outBound)
		firstOut = (int) e2.getRawY();
		if (firstView != null&& (outBound || (firstPos == 0
		   && firstView.getTop() == 0 && distanceY < 0))) {
		// Record the length of each slide
		distance = firstOut - (int) e2.getRawY();
		scrollTo(0, distance / 2);
		return true;
		}
		// outbound Bottom
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}
}