package com.rynojvr.android.internetbox.helpers;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticationService extends Service {

	private AccountAuthenticator authenticator;
	
	@Override
	public void onCreate() {
//		authenticator = new AccountAuthenticator(this);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		
		if (authenticator == null)
			authenticator = new AccountAuthenticator(this);
		return authenticator.getIBinder();
	}
}
