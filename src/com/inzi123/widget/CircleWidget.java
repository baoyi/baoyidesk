package com.inzi123.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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
		canvas.drawText("滑动3", 0, 20, paint);

		canvas.save();
		canvas.rotate(yellowdegrees, canvas.getWidth() / 2,
				canvas.getWidth() / 2);
		yellow.setBounds(new Rect(0, 0, canvas.getWidth(),
				canvas.getHeight() + 25));
		yellow.draw(canvas);

		canvas.restore();

		canvas.save();
		Path path = new Path();
		path.addArc(new RectF(canvas.getWidth() / 2, canvas.getWidth() / 2,
				canvas.getWidth() + 50, canvas.getWidth() + 50), 180, 180f);
		paint.setColor(Color.RED);
		paint.setTextSize(20);
		canvas.drawTextOnPath("关键词1", path, 0, 0, paint);
		canvas.drawText("滑动2", 0, 0, paint);
		canvas.restore();
	}

	/**
	 * 画最绿色的半圆
	 * 
	 * @param canvas
	 */
	private void drawGreed(Canvas canvas) {
		canvas.save();
		canvas.rotate(greendegrees, canvas.getWidth() / 2,
				canvas.getWidth() / 2);
		greed.setBounds(new Rect(0, 0, canvas.getWidth(),
				canvas.getHeight() + 50));
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
		canvas.rotate(purpledegrees, canvas.getWidth() / 2,
				canvas.getWidth() / 2);
		purple.setBounds(new Rect(0, 0, canvas.getWidth(),
				canvas.getHeight() + 50));
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
				canvas.getHeight() + 50));
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
				.getHeight() + 50));
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
	private int index = 1;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			LogUtil.i("ACTION_DOWN");
			oldxy[0] = event.getX();
			oldxy[1] = event.getY();
			float x = getWidth() / 2 - oldxy[0];
			float y = getHeight() - oldxy[1];
			float mAngle = (float) java.lang.Math.atan2(y, x);
			int radians = calculateRadiansFromAngle(mAngle) - 90;
			LogUtil.i("radians:" + radians);
			if (radians < 30) {
				index = 1;
			} else if (radians >= 30) {
				if (radians < 60) {
					index = 2;
				} else {
					if (radians < 100) {
						index = 3;
					}
				}
			}
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
			int yellowdegreesold = yellowdegrees;
			int greendegreesold = greendegrees;
			int purpledegreesold = purpledegrees;

			if (x3 > 0) {
				if (index == 1) {
					yellowdegrees = yellowdegrees + (int) angleA;
				}
				if (index == 2) {
					greendegrees = greendegrees + (int) angleA;
				}
				if (index == 3) {
					purpledegrees = purpledegrees + (int) angleA;
				}
				if (yellowdegrees > -10) {
					// Toast.makeText(getContext(), "解锁",
					// Toast.LENGTH_SHORT).show();
					yellowdegrees = yellowdegreesold;
					return true;
				}
				if (greendegrees > -10) {
					// Toast.makeText(getContext(), "解锁",
					// Toast.LENGTH_SHORT).show();
					greendegrees = greendegreesold;
					return true;
				}
				if (purpledegrees > -10) {
					// Toast.makeText(getContext(), "解锁",
					// Toast.LENGTH_SHORT).show();
					purpledegrees = purpledegreesold;
					return true;
				}
			} else {
				if (index == 1) {
					yellowdegrees = yellowdegrees - (int) angleA;
				}
				if (index == 2) {
					greendegrees = greendegrees - (int) angleA;
				}
				if (index == 3) {
					purpledegrees = purpledegrees- (int) angleA;
				}
				if (yellowdegrees < -150) {
					yellowdegrees =yellowdegreesold;
					return true;
				}
				if (greendegrees < -120) {
					greendegrees =greendegreesold;
					return true;
				}
				if (purpledegrees < -80) {
					purpledegrees =purpledegreesold;
					return true;
				}
			}
			LogUtil.i("ACTION_MOVE:" + yellowdegrees);
			oldxy[0] = event.getX();
			oldxy[1] = event.getY();
			postInvalidate();

		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			LogUtil.i("ACTION_UP");
			yellowdegrees = -150;
			greendegrees = -120;
			purpledegrees = -80;
			postInvalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			LogUtil.i("ACTION_CANCEL");
		}
		return super.onTouchEvent(event);

	}

	/**
	 * 计算角度
	 * 
	 * @param angle
	 * @return
	 */
	private int calculateRadiansFromAngle(float angle) {
		float unit = (float) (angle / (2 * Math.PI));
		if (unit < 0) {
			unit += 1;
		}
		int radians = (int) ((unit * 360) - ((360 / 4) * 3));
		if (radians < 0)
			radians += 360;
		return radians;
	}

	private Paint paint = new Paint();
	private int yellowdegrees = -150;
	private int greendegrees = -120;
	private int purpledegrees = -80;

}
