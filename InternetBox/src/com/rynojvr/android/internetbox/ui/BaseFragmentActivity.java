package com.rynojvr.android.internetbox.ui;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.HttpConnection.Response;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rynojvr.android.internetbox.IbApplication;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.android.internetbox.ui.adapters.SlidingMenuListAdapter;
import com.rynojvr.general.client.internetbox.IBClient;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

@Deprecated
public class BaseFragmentActivity extends SlidingFragmentActivity {
	
	private final String ERROR_LOGIN_FAILED = "Error: Login Failed";
	
//	private ListView headerView;
	private View headerView;
	private IbApplication application;
	private LayoutInflater inflater;
	
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState, int layoutID) {
		super.onCreate(savedInstanceState);
		
//		AccountManager am = AccountManager.get(this);
//		for (Account a : am.getAccountsByType("com.rynojvr.android.internetbox")) {
//			am.removeAccount(a, null, null);
//		}
			
		
		//--------------------------------------------------
		//Update BaseActivity as well
		//--------------------------------------------------
		 
		setContentView(layoutID);
		application = (IbApplication)getApplication();
		
		inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		View slidingMenu = inflater.inflate(R.layout.sliding_menu, null);
		setBehindContentView(slidingMenu);
		
		renderSlidingMenu();
		
//		headerView = new ListView(this);
		
		getSlidingMenu().setSlidingEnabled(true);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setScrollX(0);
		setSlidingActionBarEnabled(false);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 47, 47)));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Log.d("[BaseActivity#onCreate]", ""
				+ (findViewById(R.id.sliding_menu_account_fragment) == null));
		
		FragmentManager fm = getFragmentManager();
//		fm.beginTransaction()
//			.add(arg0, arg1)
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Crouton.cancelAllCroutons();
	}
	
	private void renderSlidingMenu() {
		
		
		Account current = null;
		if (application.hasActiveAccount()) {
			current = application.getActiveAccount();
		} else if (application.hasDefaultAccount()) {
			current = application.getDefaultAccount();
		}
		
		Log.d("IB", "BFA#renderSlidinngMenu : current: " + current);
		if (current != null) {
			Log.d("IB", "Checking logged in: " + current.type);
			new CheckLoggedInTask().execute(current);
		}
		
		renderAccoountPane();
	}
	
	private void renderAccoountPane() {
		ListView listView = (ListView) findViewById(R.id.sliding_menu_list_view);
		listView.removeHeaderView(headerView);
		
		Log.d("IB", "application.hasActiveAccount(): " + application.hasActiveAccount());
		if (application.hasActiveAccount()) {
			Account account = application.getActiveAccount();
			headerView = inflater.inflate(R.layout.fragment_account_info, null);
		} else {
			headerView = inflater.inflate(R.layout.fragment_log_in, null);
			
			final EditText editTextUsername = (EditText)headerView.findViewById(R.id.fragment_log_in_edit_text_username);
			final EditText editTextPassword = (EditText)headerView.findViewById(R.id.fragment_log_in_edit_text_password);
			Button loginButton = (Button)headerView.findViewById(R.id.fragment_log_in_button_sign_in);
			loginButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new LogInAsyncTask().execute(
						editTextUsername.getText().toString(),
						editTextPassword.getText().toString()
					);
				}
			});
		}
		
		listView.addHeaderView(headerView);
//		listView.setAdapter(new SlidingMenuListAdapter(this));
	}
	
	private class CheckLoggedInTask extends AsyncTask<Account, Void, Boolean> {
		
		private Account current;
		
		@Override
		protected Boolean doInBackground(Account... params) {
			current = params[0];
			
			
			Boolean result = IBClient.isLoggedIn(application.getAccountInfo(current));
//			Log.d("IB", "Checking if logged in: " + result);
			return result;
		}
		
		@Override
		protected void onPostExecute(Boolean isSignedIn) {
			Log.d("IB", "Checking if logged in: " + isSignedIn);
			if (!isSignedIn) {
				String username = current.name;
				String password = application.getPassword(current);
				// return application.signIn(username, password);
				new LogInAsyncTask().execute(username, password);
			} else {
				application.setActiveAccount(current);
				renderAccoountPane();
			}
		}
	}
	
	private class LogInAsyncTask extends AsyncTask<String, Void, Boolean> {

		private String username;
		private String password;
		
//		@Override
		protected Boolean doInBackground(String... params) {
			if (params.length < 2) {
				Log.d("IB", "Didn't get username and password");
				Toast.makeText(BaseFragmentActivity.this, "Didn't get username and password", Toast.LENGTH_LONG).show();
				return null;
			}
			username = params[0];
			password = params[1];
			
			return application.signIn(username, password);
		}
		
		@Override
		protected void onPostExecute(Boolean logInSuccessful) {
			if (logInSuccessful) {
				
			} else {
				Crouton.makeText(BaseFragmentActivity.this, ERROR_LOGIN_FAILED, Style.ALERT).show();
//				Crouton.
			}
			
			renderAccoountPane();
		}
		
//		private boolean isAuthenticated(Map<String, String> result) {
//			// User is authenticated if the response has the 'k' and 'user' cookies
//			return result.containsKey("k") && result.containsKey("user");
//		}
	}
}
