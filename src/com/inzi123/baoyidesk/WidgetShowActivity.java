package com.inzi123.baoyidesk;

import java.util.List;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WidgetShowActivity extends Activity {
	private List<AppWidgetProviderInfo> widgetList;
	private AppWidgetManager appWidgetManager;
	public static final int APPWIDGET_HOST_ID = 1024;
	LinearLayout linearlayout;
	int id;
	private Object options;
	private static final int REQUEST_BIND_APPWIDGET = 11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widget_show);
		appWidgetManager = AppWidgetManager.getInstance(this);
		widgetList = appWidgetManager.getInstalledProviders();
		host = new AppWidgetHost(this, APPWIDGET_HOST_ID);
		host.startListening();
		linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
		aa();
		aa();
		aa();

	}

	private void aa() {
		id = host.allocateAppWidgetId();
		AppWidgetProviderInfo appWidgetProviderInfo = widgetList.get(1);
		boolean success = false;
		success = appWidgetManager.bindAppWidgetIdIfAllowed(id,
				appWidgetProviderInfo.provider);
		if (success) {
			AppWidgetHostView hostView = host.createView(
					WidgetShowActivity.this, id, appWidgetProviderInfo);
			linearlayout.addView(hostView);
		} else {
			Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_BIND);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER,
					appWidgetProviderInfo.provider);
			// TODO: we need to make sure that this accounts for the options
			// bundle.
			// intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_OPTIONS,
			// options);
			startActivityForResult(intent, REQUEST_BIND_APPWIDGET);
		}
	}

	AppWidgetHost host;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 直接返回，没有选择任何一项 ，例如按Back键
		if (resultCode == RESULT_CANCELED)
			return;

		switch (requestCode) {
		case MY_REQUEST_APPWIDGET:
			Log.i(TAG, "MY_REQUEST_APPWIDGET intent info is -----> " + data);
			int appWidgetId = data.getIntExtra(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);

			Log.i(TAG, "MY_REQUEST_APPWIDGET : appWidgetId is ----> "
					+ appWidgetId);

			// 得到的为有效的id
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				// 查询指定appWidgetId的 AppWidgetProviderInfo对象 ，
				// 即在xml文件配置的<appwidget-provider />节点信息
				AppWidgetProviderInfo appWidgetProviderInfo = appWidgetManager
						.getAppWidgetInfo(appWidgetId);

				// 如果配置了configure属性 ， 即android:configure = ""
				// ，需要再次启动该configure指定的类文件,通常为一个Activity
				if (appWidgetProviderInfo.configure != null) {

					Log.i(TAG,
							"The AppWidgetProviderInfo configure info -----> "
									+ appWidgetProviderInfo.configure);

					// 配置此Action
					Intent intent = new Intent(
							AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
					intent.setComponent(appWidgetProviderInfo.configure);
					intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
							appWidgetId);

					startActivityForResult(intent, MY_CREATE_APPWIDGET);
				} else
					// 直接创建一个AppWidget
					onActivityResult(MY_CREATE_APPWIDGET, RESULT_OK, data); // 参数不同，简单回调而已
			}
			break;
		case MY_CREATE_APPWIDGET:
			completeAddAppWidget(data);
			break;
		}

	}

	private static String TAG = "AddAppWidget";
	private static final int MY_REQUEST_APPWIDGET = 1;
	private static final int MY_CREATE_APPWIDGET = 2;

	// 向当前视图添加一个用户选择的
	private void completeAddAppWidget(Intent data) {
		Bundle extra = data.getExtras();
		int appWidgetId = extra.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		// 等同于上面的获取方式
		Log.i(TAG, "completeAddAppWidget : appWidgetId is ----> " + appWidgetId);
		if (appWidgetId == -1) {
			Toast.makeText(WidgetShowActivity.this, "添加窗口小部件有误",
					Toast.LENGTH_SHORT);
			return;
		}
		AppWidgetProviderInfo appWidgetProviderInfo = appWidgetManager
				.getAppWidgetInfo(appWidgetId);
		AppWidgetHostView hostView = host.createView(WidgetShowActivity.this,
				appWidgetId, appWidgetProviderInfo);
		// linearLayout.addView(hostView) ;
		Rect padding = hostView.getDefaultPaddingForWidget(this,
				appWidgetProviderInfo.provider, null);
		int widget_minWidht = appWidgetProviderInfo.minWidth + padding.left
				+ padding.right;
		int widget_minHeight = appWidgetProviderInfo.minHeight + padding.top
				+ padding.bottom;
		// 设置长宽 appWidgetProviderInfo 对象的 minWidth 和 minHeight 属性
		LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
				widget_minWidht, widget_minHeight);
		// 添加至LinearLayout父视图中
		linearlayout.addView(hostView);
	}

}
