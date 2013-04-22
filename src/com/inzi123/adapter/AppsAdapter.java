package com.inzi123.adapter;

import android.content.Context;
import android.view.View;

import com.by.codes.adapter.ItemListHolderAdapter;
import com.inzi123.entity.ApplicationInfo;
import com.inzi123.widget.App;

public class AppsAdapter extends ItemListHolderAdapter<ApplicationInfo> {

	public AppsAdapter(Context context) {
		super(context);
	}
	@Override
	protected View updateView(int position, View view, ApplicationInfo item) {
		App app=(App)view;
		app.setApplicationInfo(item);
		return app;
	}

	@Override
	protected View creatView(int position, ApplicationInfo item) {
		App app=new App(context);
		app.setApplicationInfo(item);
		return app;
	}

}
