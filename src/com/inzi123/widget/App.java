package com.inzi123.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inzi123.baoyidesk.R;
import com.inzi123.entity.ApplicationInfo;

public class App extends LinearLayout {

	public App(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.widget_app, this);
		imageView = (ImageView) findViewById(R.id.imageView1);
		textView = (TextView) findViewById(R.id.textView);
	}

	ApplicationInfo applicationInfo;

	public void startActivity() {
		getContext().startActivity(applicationInfo.intent);
	}

	public ApplicationInfo getApplicationInfo() {
		return applicationInfo;
	}

	public void setApplicationInfo(ApplicationInfo applicationInfo) {
		this.applicationInfo = applicationInfo;

		Drawable icon = applicationInfo.icon;
		if (!applicationInfo.filtered) {
			// final Resources resources = getContext().getResources();
			int width = 42;// (int)
							// resources.getDimension(android.R.dimen.app_icon_size);
			int height = 42;// (int)
							// resources.getDimension(android.R.dimen.app_icon_size);

			final int iconWidth = icon.getIntrinsicWidth();
			final int iconHeight = icon.getIntrinsicHeight();

			if (icon instanceof PaintDrawable) {
				PaintDrawable painter = (PaintDrawable) icon;
				painter.setIntrinsicWidth(width);
				painter.setIntrinsicHeight(height);
			}

			if (width > 0 && height > 0
					&& (width < iconWidth || height < iconHeight)) {
				final float ratio = (float) iconWidth / iconHeight;

				if (iconWidth > iconHeight) {
					height = (int) (width / ratio);
				} else if (iconHeight > iconWidth) {
					width = (int) (height * ratio);
				}

				final Bitmap.Config c = icon.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
						: Bitmap.Config.RGB_565;
				final Bitmap thumb = Bitmap.createBitmap(width, height, c);
				final Canvas canvas = new Canvas(thumb);
				canvas.setDrawFilter(new PaintFlagsDrawFilter(
						Paint.DITHER_FLAG, 0));
				// Copy the old bounds to restore them later
				// If we were to do oldBounds = icon.getBounds(),
				// the call to setBounds() that follows would
				// change the same instance and we would lose the
				// old bounds
				mOldBounds.set(icon.getBounds());
				icon.setBounds(0, 0, width, height);
				icon.draw(canvas);
				icon.setBounds(mOldBounds);
				icon = applicationInfo.icon = new BitmapDrawable(thumb);
				applicationInfo.filtered = true;
			}
		}
		imageView.setImageDrawable(icon);
		textView.setText(applicationInfo.title);
	}

	private Rect mOldBounds = new Rect();
	private ImageView imageView;
	TextView textView;
}
