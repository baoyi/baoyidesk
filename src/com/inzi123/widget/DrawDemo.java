package com.inzi123.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DrawDemo extends View {

	private Paint paint = new Paint();

	public DrawDemo(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int heigth = width / 2;
		setMeasuredDimension(width, heigth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		RectF oval = new RectF(0, 0, canvas.getWidth(), canvas.getWidth());
		canvas.drawArc(oval, 45, 180, true, paint);
		paint.setColor(Color.YELLOW);
		Path path=new Path();
		path.addArc(new RectF(50, 50, canvas.getWidth()+50, canvas.getWidth()+50), 180, 180);
		canvas.drawTextOnPath("hello im ada haha come on baby", path, 0, 0, paint);
		canvas.save();
		canvas.rotate(90,canvas.getWidth()/2,canvas.getWidth()/2);
		paint.setColor(Color.RED);
		Path path1=new Path();
		path.addArc(new RectF(50, 50, canvas.getWidth()+50, canvas.getWidth()+50), 180, 180);
		canvas.drawTextOnPath("hello im ada haha come on baby", path1, 0, 0, paint);
		canvas.restore();
		
	}

}
