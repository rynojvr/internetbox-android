package com.rynojvr.android.internetbox.ui;

import com.rynojvr.android.internetbox.R;

import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;
import android.view.Window;

public class AddAccountActivity extends AccountAuthenticatorActivity {

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_add_account);
		
	}
	
}
