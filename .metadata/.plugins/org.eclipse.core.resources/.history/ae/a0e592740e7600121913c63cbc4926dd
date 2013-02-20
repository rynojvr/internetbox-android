package com.rynojvr.android.internetbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.rynojvr.android.internetbox.ui.HomeActivity;
import com.rynojvr.android.internetbox.ui.HomePageActivity;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

//public class MainActivity extends SlidingActivity {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setBehindContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_main);
//        
////        getSlidingMenu().setSlidingEnabled(true);
//        setSlidingActionBarEnabled(true);
//        getSlidingMenu().setBehindOffset(R.dimen.slidingmenu_offset);
//        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.activity_main, menu);
////        return true;
////    }
//}

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setClass(this, HomePageActivity.class);
		intent.setClass(this, HomeActivity.class);
		startActivity(intent);

		finish();
	}

}
