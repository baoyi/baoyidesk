package com.inzi123.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inzi123.baoyidesk.R;

public class ResolveInfoApp extends LinearLayout {

	public ResolveInfoApp(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.widget_app, this);
		imageView = (ImageView) findViewById(R.id.imageView1);
		textView = (TextView) findViewById(R.id.textView);
	}

	ResolveInfo resolveInfo;
	private Intent intent;

	public void startActivity() {
		getContext().startActivity(intent);
	}

	public final void setActivity(ComponentName className, int launchFlags) {
		intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(className);
		intent.setFlags(launchFlags);
	}

	public ResolveInfo getResolveInfo() {
		return resolveInfo;
	}

	public void setResolveInfo(ResolveInfo resolveInfo) {
		this.resolveInfo = resolveInfo;
		imageView.setImageDrawable(resolveInfo.activityInfo
				.loadIcon(getContext().getPackageManager()));
		textView.setText(resolveInfo
				.loadLabel(getContext().getPackageManager()));
		
		setActivity(new ComponentName(
				resolveInfo.activityInfo.applicationInfo.packageName,
				resolveInfo.activityInfo.name), Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
	}

	private ImageView imageView;
	TextView textView;
}
