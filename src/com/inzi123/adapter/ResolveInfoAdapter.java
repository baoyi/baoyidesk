package com.inzi123.adapter;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.view.View;

import com.by.codes.adapter.ListAdapter;
import com.inzi123.widget.ResolveInfoApp1;

public class ResolveInfoAdapter extends ListAdapter<ResolveInfo> {

	public ResolveInfoAdapter(Context context) {
		super(context);
	}
	@Override
	protected View updateView(int position, View view, ResolveInfo item) {
		ResolveInfoApp1 app=(ResolveInfoApp1)view;
		app.setResolveInfo(item);
		return app;
	}

	@Override
	protected View creatView(int position, ResolveInfo item) {
		ResolveInfoApp1 app=new ResolveInfoApp1(context);
		app.setResolveInfo(item);
		return app;
	}

}
