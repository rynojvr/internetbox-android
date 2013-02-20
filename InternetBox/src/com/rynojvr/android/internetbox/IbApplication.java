package com.rynojvr.android.internetbox;

import java.util.HashMap;
import java.util.Map;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.rynojvr.general.client.internetbox.IBClient;
public class IbApplication extends Application {
	
	public static final String KEY_LOG = "InternetBox";
	
	private final String KEY_DEFAULT_ACCOUNT = "defaultAccount";
	private final String KEY_USER_DATA_K = "k";
	private final String KEY_USER_DATA_USER = "user";
	private final String KEY_ACCOUNT_TYPE = "com.rynojvr.android.internetbox";
	
	private Account activeAccount;
	private SharedPreferences sharedPrefs;
	private AccountManager accountManager;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		sharedPrefs = getSharedPreferences(KEY_ACCOUNT_TYPE + ".sharedpref", MODE_PRIVATE);
		accountManager = AccountManager.get(this);
	}
	
	public void setDefaultAccount(Account defaultAccount) {
		sharedPrefs.edit()
			.putString(KEY_DEFAULT_ACCOUNT, defaultAccount.name)
			.commit();
		
		boolean foundAccount = false;
		Account[] accounts = accountManager.getAccounts();
		for (int i = 0; i < accounts.length && !foundAccount; i++) {
			if (accounts[i].name.equals(defaultAccount.name)) {
				foundAccount = true;
			}
		}
		
		if (!foundAccount) {
//			accountManager
		}
	}
	
	public boolean hasDefaultAccount() {
		return getDefaultAccount() != null;
	}
	
	public Account getDefaultAccount() {
		String accountName = sharedPrefs.getString(KEY_DEFAULT_ACCOUNT, "");
		Log.d("IB", "default Account name: " + accountName);
		
		// if (!accountName.isEmpty()) {	#Not supported in API 8... 
		return findAccountByUsername(accountName);
	}
	
	public boolean hasActiveAccount() {
		return this.activeAccount != null;
	}
	
	public Account getActiveAccount() {
		return this.activeAccount;
	}
	
	public void setActiveAccount(Account activeAccount) {
		this.activeAccount = activeAccount;
	}
	
	public Account setActiveAccount(String username, String password, Map<String, String> userdata) {
		Account account = findAccountByUsername(username);
		if (account == null) {
			account = new Account(username, KEY_ACCOUNT_TYPE);
			Bundle bundle = new Bundle();
			bundle.putString(KEY_USER_DATA_K, userdata.get(KEY_USER_DATA_K));
			bundle.putString(KEY_USER_DATA_USER, userdata.get(KEY_USER_DATA_USER));
			accountManager.addAccountExplicitly(account, password, bundle);
		} 
		
		this.activeAccount = account;
		return account;
	}
	
	public Map<String, String> getAccountInfo(Account account) {
		Map<String, String> result = new HashMap<String, String>();
		
//		Log.d("IB", "userData[k]: " + accountManager.getUserData(account, "com.rynojvr.android.internetbox"));
		
		result.put(KEY_USER_DATA_K, accountManager.getUserData(account, KEY_USER_DATA_K));
		result.put(KEY_USER_DATA_USER, accountManager.getUserData(account, KEY_USER_DATA_USER));
		return result;
	}
	
	public String getPassword(Account account) {
		return accountManager.getPassword(account);
	}
	
	public boolean signIn(String username, String password) {
		Map<String, String> result = IBClient.signIn(username, password);
		
		if (result == null) {
			return false;
		}
		
		Account account = findAccountByUsername(username);
		if (account == null) {
			account = setActiveAccount(username, password, result);
		} else {
			setActiveAccount(account);
		}
		setDefaultAccount(account);
		
		accountManager.setUserData(account, KEY_USER_DATA_K, result.get(KEY_USER_DATA_K));
		accountManager.setUserData(account, KEY_USER_DATA_USER, result.get(KEY_USER_DATA_USER));
		
		return true;
	}
	
	
	
	
	
	
	private Account findAccountByUsername(String username) {
		Log.d("IB", "findAccountByUsername, username: " + username);
		if (username.length() > 0) {
			for (Account a : accountManager.getAccountsByType(KEY_ACCOUNT_TYPE)) {
				Log.d("IB", "findAccountByUsername, a.name: " + a.name);
				if (a.name.equals(username)) {
					return a;
				}
			}
		}

		return null;
	}
}
