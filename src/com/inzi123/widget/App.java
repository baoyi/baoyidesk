package com.inzi123.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inzi123.baoyidesk.BaoyiApplication;
import com.inzi123.baoyidesk.R;
import com.inzi123.cache.IconCache;
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
		BaoyiApplication application=	(BaoyiApplication) getContext().getApplicationContext();
        IconCache iconCache=application.getIconCache();
		//imageView.setsr(iconCache.getIcon(applicationInfo.intent));
		textView.setText(applicationInfo.title);
	}

	private Rect mOldBounds = new Rect();
	private ImageView imageView;
	TextView textView;
}
