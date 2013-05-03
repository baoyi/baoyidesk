package com.inzi123.baoyidesk;

import java.util.List;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

public class WidgetShowActivity extends Activity {
	private List<AppWidgetProviderInfo> widgetList;
	private AppWidgetManager awm;
	public static final int APPWIDGET_HOST_ID = 1024;
	LinearLayout linearlayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widget_show);
		awm = AppWidgetManager.getInstance(this);
		widgetList = awm.getInstalledProviders();
		AppWidgetProviderInfo info = widgetList.get(0);
		int[] ids = awm.getAppWidgetIds(info.provider);

		host = new AppWidgetHost(this, APPWIDGET_HOST_ID);
		host.startListening();
		AppWidgetHostView view = host.createView(this, ids[0], info);
		linearlayout=(LinearLayout) findViewById(R.id.linearlayout);
		linearlayout.addView(view);
	}

	AppWidgetHost host;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.widget_show, menu);
		return true;
	}

}
