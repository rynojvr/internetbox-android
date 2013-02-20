package com.rynojvr.android.internetbox.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.widget.Toast;

import com.rynojvr.android.internetbox.R;

@Deprecated
public class HomePageActivity extends BaseFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_home_page);
//		setContentView(R.layout.activity_home_page);
		
//		AccountManager am = AccountManager.get(this);
//		Account[] accounts = am.getAccountsByType("com.rynojvr.android.internetbox");
		
//		Toast.makeText(this, "Accounts:", Toast.LENGTH_SHORT).show();
//		for (Account a : accounts) {
//			Toast.makeText(this, a.toString(), Toast.LENGTH_SHORT).show();
////			am.s
//		}
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_home_page, menu);
//		return true;
//	}
}