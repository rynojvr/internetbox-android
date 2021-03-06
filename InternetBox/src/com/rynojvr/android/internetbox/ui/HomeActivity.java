package com.rynojvr.android.internetbox.ui;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.rynojvr.android.internetbox.IbApplication;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.android.internetbox.models.enums.SlidingMenuOptions;
import com.rynojvr.android.internetbox.ui.adapters.SlidingMenuListAdapter;
import com.rynojvr.general.client.internetbox.IBClient;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class HomeActivity extends SlidingFragmentActivity {	
	
	private final String ERROR_LOGIN_FAILED = "Error: Login Failed";
	
	private View headerView;
	private IbApplication application;
	private LayoutInflater inflater;
	private SlidingMenuListAdapter slidingMenuListAdapter;
	
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		slidingMenuListAdapter = new SlidingMenuListAdapter(this);
		slidingMenuListAdapter.setOnMenuItemClickListener(new SlidingMenuListAdapter.OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(SlidingMenuOptions menuItem) {
				SherlockFragment fragment = menuItem.getFragment();
				Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_fragment_holder);
				if (!fragment.equals(currentFragment)) {
					getSupportFragmentManager().beginTransaction()
						.replace(R.id.activity_main_fragment_holder, fragment)
						.addToBackStack(null)
						.commit();
				}
				getSlidingMenu().showContent();
			}
		});
		
		setContentView(R.layout.activity_home);
		application = (IbApplication)getApplication();
		
		inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		View slidingMenu = inflater.inflate(R.layout.sliding_menu, null);
		setBehindContentView(slidingMenu);
		
		renderSlidingMenu();
		
		getSlidingMenu().setSlidingEnabled(true);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setScrollX(0);
		setSlidingActionBarEnabled(false);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 47, 47)));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
		
		if (current != null) {
			Log.d("IB", "Checking logged in: " + current.type);
			new CheckLoggedInTask().execute(current);
		}
		
		renderAccoountPane();
	}
	
	private void renderAccoountPane() {
		ListView listView = (ListView) findViewById(R.id.sliding_menu_list_view);
		listView.removeHeaderView(headerView);
		
		if (application.hasActiveAccount()) {
			Account account = application.getActiveAccount();
			headerView = inflater.inflate(R.layout.fragment_account_info, null);
			//TODO: Do something with the damn account
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
		listView.setAdapter(slidingMenuListAdapter);
	}
	
	private class CheckLoggedInTask extends AsyncTask<Account, Void, Boolean> {
		private Account current;
		
		@Override
		protected Boolean doInBackground(Account... params) {
			current = params[0];
			return IBClient.isLoggedIn(application.getAccountInfo(current));
		}
		
		@Override
		protected void onPostExecute(Boolean isSignedIn) {
			 if (isSignedIn) {
				application.setActiveAccount(current);
				renderAccoountPane();
			} else {
				String username = current.name;
				String password = application.getPassword(current);
				new LogInAsyncTask().execute(username, password);
			}
		}
	}
	
	private class LogInAsyncTask extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			String username = params[0];
			String password = params[1];
			return application.signIn(username, password);
		}
		
		@Override
		protected void onPostExecute(Boolean logInSuccessful) {
			if (logInSuccessful) {
				// TODO: Something was supposed to go here 
			} else {
				Crouton.makeText(HomeActivity.this, ERROR_LOGIN_FAILED, Style.ALERT).show();
			}
			renderAccoountPane();
		}
	}
}
