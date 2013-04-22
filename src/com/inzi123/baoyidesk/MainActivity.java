package com.inzi123.baoyidesk;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.inzi123.adapter.ResolveInfoAdapter;
import com.inzi123.entity.ApplicationInfo;
import com.inzi123.widget.App;
import com.inzi123.widget.ResolveInfoApp;

public class MainActivity extends FragmentActivity {
	GridView gridView;
	
    private static ArrayList<ApplicationInfo> mApplications;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter=new ResolveInfoAdapter(this);
        //getLoaderManager().initLoader(LOADER, null, this);
		//查找系统的所有应用
        final PackageManager manager = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
        //Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
         for (ResolveInfo resolveInfo : apps) {
        	 adapter.add(resolveInfo);
		}
        gridView=(GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg1 instanceof ResolveInfoApp){
					ResolveInfoApp	apps =(ResolveInfoApp)arg1;
					apps.startActivity();
				}
			}
		});
        gridView.setAdapter(adapter);
	}
	
	ResolveInfoAdapter adapter;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
