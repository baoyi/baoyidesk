package com.inzi123.baoyidesk;

import java.util.List;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadWidgetActivity extends Activity {

	private GridView widgetGv;
	private List<AppWidgetProviderInfo> widgetList; 
	private LayoutInflater ll;
	AppWidgetManager awm;
	PackageManager pm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pm=getPackageManager();
		ll=LayoutInflater.from(this);
		 awm= AppWidgetManager.getInstance(this);
		widgetList=awm.getInstalledProviders();
		widgetGv=(GridView) findViewById(R.id.widgetGv);
		widgetGv.setAdapter(new WidgetAdapter());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class WidgetAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			if(widgetList!=null){
				return widgetList.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if(widgetList!=null){
				return widgetList.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		ViewHolder holder;
		@Override
		public View getView(int position, View cv, ViewGroup parent) {
			if(cv==null){
				cv=ll.inflate(R.layout.item_widget, null);
				holder=new ViewHolder();
				holder.name=(TextView) cv.findViewById(R.id.name);
				holder.icon=(ImageView) cv.findViewById(R.id.icon);
				cv.setTag(holder);
			}else{
				holder=(ViewHolder) cv.getTag();
			}
			AppWidgetProviderInfo info=widgetList.get(position);
			holder.name.setText(info.label);
			Drawable a=null;;
			try {
				if(pm!=null&&info.configure!=null){
					a = pm.getActivityIcon(info.configure);
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			if(a!=null){
				holder.icon.setImageDrawable(a);
			}
//			holder.icon.setImageDrawable(getResources().getDrawable(info.icon));
			return cv;
		}
		class ViewHolder {
			TextView name;
			ImageView icon;
		}
		
	}
}
