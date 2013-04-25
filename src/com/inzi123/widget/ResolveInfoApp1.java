package com.inzi123.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inzi123.baoyidesk.BaoyiApplication;
import com.inzi123.baoyidesk.R;
import com.inzi123.cache.IconCache;

public class ResolveInfoApp1 extends TextView {

	public ResolveInfoApp1(Context context) {
		super(context);
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

		BaoyiApplication application = (BaoyiApplication) getContext()
				.getApplicationContext();
		IconCache iconCache = application.getIconCache();
		setCompoundDrawablesWithIntrinsicBounds(null,
				iconCache.getFullResIcon(resolveInfo), null, null);
		setGravity(Gravity.CENTER);
		// imageView.setImageDrawable(resolveInfo.activityInfo
		// .loadIcon(getContext().getPackageManager()));
		setText(resolveInfo.loadLabel(getContext().getPackageManager()));

		setActivity(new ComponentName(
				resolveInfo.activityInfo.applicationInfo.packageName,
				resolveInfo.activityInfo.name), Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
	}
}
