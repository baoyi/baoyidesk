package com.inzi123.adapter;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.view.View;

import com.by.codes.adapter.ListAdapter;
import com.inzi123.widget.ResolveInfoApp;

public class ResolveInfoAdapter extends ListAdapter<ResolveInfo> {

	public ResolveInfoAdapter(Context context) {
		super(context);
	}
	@Override
	protected View updateView(int position, View view, ResolveInfo item) {
		ResolveInfoApp app=(ResolveInfoApp)view;
		app.setResolveInfo(item);
		return app;
	}

	@Override
	protected View creatView(int position, ResolveInfo item) {
		ResolveInfoApp app=new ResolveInfoApp(context);
		app.setResolveInfo(item);
		return app;
	}

}
