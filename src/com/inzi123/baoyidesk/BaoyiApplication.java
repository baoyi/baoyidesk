package com.inzi123.baoyidesk;

import android.app.Application;

import com.inzi123.cache.IconCache;

public class BaoyiApplication extends Application {
	private IconCache iconCache;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		iconCache=new IconCache(this);
	}

	public IconCache getIconCache() {
		return iconCache;
	}
}
