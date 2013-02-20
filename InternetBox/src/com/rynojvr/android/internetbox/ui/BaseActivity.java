package com.rynojvr.android.internetbox.ui;

import com.rynojvr.android.internetbox.R;
import com.rynojvr.android.internetbox.R.layout;
import com.rynojvr.android.internetbox.R.menu;
import com.rynojvr.android.internetbox.ui.adapters.SlidingMenuListAdapter;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

@Deprecated
public abstract class BaseActivity extends SlidingActivity {

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState, int layoutID) {
		super.onCreate(savedInstanceState);
		
		//--------------------------------------------------
		//Update BaseFragmentActivity as well
		//--------------------------------------------------
		 
		setContentView(layoutID);
		setBehindContentView(R.layout.sliding_menu);
		
		
		
		ListView listView = (ListView) findViewById(R.id.sliding_menu_list_view);
//		listView.setAdapter(new SlidingMenuListAdapter(this));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSlidingMenu().setSlidingEnabled(true);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setScrollX(0);
		setSlidingActionBarEnabled(false);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 47, 47)));
		
		
		
		Log.d("[BaseActivity#onCreate]", ""
				+ (findViewById(R.id.sliding_menu_list_view) == null));
		
	}
}
