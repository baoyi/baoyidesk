package com.inzi123.baoyidesk;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;

import com.inzi123.adapter.ResolveInfoAdapter;
import com.inzi123.entity.ApplicationInfo;

public class WidgetsActivity extends FragmentActivity {
	GridView gridView;
	private PackageManager mPackageManager;

	public static int[] getMinSpanForWidget(Context context,
			AppWidgetProviderInfo info) {
		return getSpanForWidget(context, info.provider, info.minResizeWidth,
				info.minResizeHeight);
	}

	public static int[] getSpanForWidget(Context context,
			AppWidgetProviderInfo info) {
		return getSpanForWidget(context, info.provider, info.minWidth,
				info.minHeight);
	}

	static int[] getSpanForWidget(Context context, ComponentName component,
			int minWidth, int minHeight) {
		Rect padding = AppWidgetHostView.getDefaultPaddingForWidget(context,
				component, null);
		// We want to account for the extra amount of padding that we are adding
		// to the widget
		// to ensure that it gets the full amount of space that it has requested
		int requiredWidth = minWidth + padding.left + padding.right;
		int requiredHeight = minHeight + padding.top + padding.bottom;
		return rectToCell(context.getResources(), requiredWidth,
				requiredHeight, null);
	}

	public static int[] rectToCell(Resources resources, int width, int height,
			int[] result) {
		// Always assume we're working with the smallest span to make sure we
		// reserve enough space in both orientations.
		int actualWidth = resources
				.getDimensionPixelSize(R.dimen.workspace_cell_width);
		int actualHeight = resources
				.getDimensionPixelSize(R.dimen.workspace_cell_height);
		int smallerSize = Math.min(actualWidth, actualHeight);

		// Always round up to next largest cell
		int spanX = (int) Math.ceil(width / (float) smallerSize);
		int spanY = (int) Math.ceil(height / (float) smallerSize);

		if (result == null) {
			return new int[] { spanX, spanY };
		}
		result[0] = spanX;
		result[1] = spanY;
		return result;
	}

	private static ArrayList<ApplicationInfo> mApplications;
	private ArrayList<Object> mWidgets = new ArrayList<Object>();

	public void onPackagesUpdated() {
		// Get the list of widgets and shortcuts
		mWidgets.clear();
		List<AppWidgetProviderInfo> widgets = AppWidgetManager
				.getInstance(this).getInstalledProviders();
		Intent shortcutsIntent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
		List<ResolveInfo> shortcuts = mPackageManager.queryIntentActivities(
				shortcutsIntent, 0);
		for (AppWidgetProviderInfo widget : widgets) {
			if (widget.minWidth > 0 && widget.minHeight > 0) {
				// Ensure that all widgets we show can be added on a workspace
				// of this size
				int[] spanXY = getSpanForWidget(this, widget);
				int[] minSpanXY = getMinSpanForWidget(this, widget);
				int minSpanX = Math.min(spanXY[0], minSpanXY[0]);
				int minSpanY = Math.min(spanXY[1], minSpanXY[1]);
				if (minSpanX <=5000
						&& minSpanY <= 5000) {
					mWidgets.add(widget);
				} else {
					Log.e(TAG, "Widget " + widget.provider
							+ " can not fit on this device (" + widget.minWidth
							+ ", " + widget.minHeight + ")");
				}
			} else {
				Log.e(TAG, "Widget " + widget.provider
						+ " has invalid dimensions (" + widget.minWidth + ", "
						+ widget.minHeight + ")");
			}
		}
		mWidgets.addAll(shortcuts);
		Collections.sort(mWidgets, new WidgetAndShortcutNameComparator(
				mPackageManager));
	}
    static final String TAG = "AppsCustomizePagedView";

	public static class WidgetAndShortcutNameComparator implements
			Comparator<Object> {
		private Collator mCollator;
		private PackageManager mPackageManager;
		private HashMap<Object, String> mLabelCache;

		public WidgetAndShortcutNameComparator(PackageManager pm) {
			mPackageManager = pm;
			mLabelCache = new HashMap<Object, String>();
			mCollator = Collator.getInstance();
		}

		public final int compare(Object a, Object b) {
			String labelA, labelB;
			if (mLabelCache.containsKey(a)) {
				labelA = mLabelCache.get(a);
			} else {
				labelA = (a instanceof AppWidgetProviderInfo) ? ((AppWidgetProviderInfo) a).label
						: ((ResolveInfo) a).loadLabel(mPackageManager)
								.toString();
				mLabelCache.put(a, labelA);
			}
			if (mLabelCache.containsKey(b)) {
				labelB = mLabelCache.get(b);
			} else {
				labelB = (b instanceof AppWidgetProviderInfo) ? ((AppWidgetProviderInfo) b).label
						: ((ResolveInfo) b).loadLabel(mPackageManager)
								.toString();
				mLabelCache.put(b, labelB);
			}
			return mCollator.compare(labelA, labelB);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPackageManager = getPackageManager();
	}

	ResolveInfoAdapter adapter;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
