package com.inzi123.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.inzi123.baoyidesk.R;
import com.inzi123.utils.LogUtil;

public class CircleWidget extends View {
	public CircleWidget(Context context) {
		super(context);
		initDraw();
	}

	Drawable halfcircle;
	Drawable yellow;
	Drawable blue;
	Drawable greed;
	Drawable purple;

	public CircleWidget(Context context, AttributeSet attrs) {
		super(context, attrs);

		initDraw();
	}

	private void initDraw() {
		halfcircle = getResources().getDrawable(R.drawable.halfcircle);
		yellow = getResources().getDrawable(R.drawable.yellow);
		blue = getResources().getDrawable(R.drawable.blue);
		greed = getResources().getDrawable(R.drawable.greed);
		purple = getResources().getDrawable(R.drawable.purple);
	}

	public CircleWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initDraw();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		RectF oval = new RectF(0, 0, canvas.getWidth(), canvas.getWidth() * 2);
		// canvas.drawArc(oval, 180, 360, true, paint);
		// canvas.translate(canvas.getWidth()/2.0f, canvas.getHeight()/3.0f);

		drawBlue(canvas);

		drawPurple(canvas);

		drawGreed(canvas);

		drawYellow(canvas);

		drawHalfCircle(canvas);
	}

	/**
	 * 画最黄色的半圆
	 * 
	 * @param canvas
	 */
	private void drawYellow(Canvas canvas) {
		canvas.save();
		canvas.rotate(degrees, canvas.getWidth() / 2, canvas.getWidth() / 2);
		yellow.setBounds(new Rect(0, 0, canvas.getWidth(),
				canvas.getHeight() + 25));
		yellow.draw(canvas);
		canvas.restore();
	}

	/**
	 * 画最绿色的半圆
	 * 
	 * @param canvas
	 */
	private void drawGreed(Canvas canvas) {
		canvas.save();
		canvas.rotate(-120, canvas.getWidth() / 2, canvas.getWidth() / 2);
		greed.setBounds(new Rect(0, 0, canvas.getWidth(),
				canvas.getHeight() + 25));
		greed.draw(canvas);
		canvas.restore();
	}

	/**
	 * 画最紫色的半圆
	 * 
	 * @param canvas
	 */
	private void drawPurple(Canvas canvas) {
		canvas.save();
		canvas.rotate(-80, canvas.getWidth() / 2, canvas.getWidth() / 2);
		purple.setBounds(new Rect(0, 0, canvas.getWidth(),
				canvas.getHeight() + 25));
		purple.draw(canvas);
		canvas.restore();
	}

	/**
	 * 画最蓝色的半圆
	 * 
	 * @param canvas
	 */
	private void drawBlue(Canvas canvas) {
		canvas.save();
		canvas.rotate(-1, canvas.getWidth() / 2, canvas.getWidth() / 2);
		blue.setBounds(new Rect(0, 0, canvas.getWidth(),
				canvas.getHeight() + 25));
		blue.draw(canvas);
		canvas.restore();
	}

	/**
	 * 画最外面的半圆
	 * 
	 * @param canvas
	 */
	private void drawHalfCircle(Canvas canvas) {

		halfcircle.setBounds(new Rect(0, 0, canvas.getWidth(), canvas
				.getHeight() + 25));
		halfcircle.draw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int heigth = width / 2;
		setMeasuredDimension(width, heigth);
	}

	private float[] oldxy = new float[2];

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			LogUtil.i("ACTION_DOWN");
			oldxy[0] = event.getX();
			oldxy[1] = event.getY();
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float x1 = oldxy[0];
			float x2 = event.getX();
			float y1 = oldxy[1];
			float y2 = event.getY();
			float x = getWidth() / 2.0f;
			float y = getHeight();
			double a = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
			double b = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
			double c = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
			double cosA = (b * b + c * c - a * a) / (2 * b * c);
			double arcA = Math.acos(cosA);
			double angleA = arcA * 180 / Math.PI;
			float x3 = x2 - x1;
			if(x3>0){
				degrees = degrees + (int) angleA;
			}else{
				degrees = degrees - (int) angleA;
			}
			LogUtil.i("ACTION_MOVE:" + degrees);
			oldxy[0] = event.getX();
			oldxy[1] = event.getY();
			postInvalidate();

		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			LogUtil.i("ACTION_UP");
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			LogUtil.i("ACTION_CANCEL");
		}
		return super.onTouchEvent(event);

	}

	private Paint paint = new Paint();
	private int degrees = -150;

}
