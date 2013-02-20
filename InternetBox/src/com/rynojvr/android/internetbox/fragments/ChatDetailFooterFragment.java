package com.rynojvr.android.internetbox.fragments;

import java.util.Map;

import android.accounts.Account;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.rynojvr.android.internetbox.IbApplication;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.general.client.internetbox.IBClient;

public class ChatDetailFooterFragment extends SherlockFragment {

	private IbApplication application;
	private int topicId;
	
//	public ChatDetailFooterFragment(int topicId) {
//		this.topicId = topicId;
//	}
	
	public void setTopicId(int topicId) {
		this.topicId = topicId;
		
		Log.d("IB", "Topic ID set: " + topicId);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		application = (IbApplication) getActivity().getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View resultView = inflater.inflate(
				R.layout.fragment_chat_detail_footer, null);

		final EditText editText = (EditText) resultView
				.findViewById(R.id.editText1);
		editText.clearFocus();

		ImageButton imageButton = (ImageButton) resultView
				.findViewById(R.id.imageButton1);
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String message = editText.getText().toString();
				Log.d("IB", "Messge to send: " + message);
				if (message.length() > 0) {
					new SendReplyTask().execute(message);
					editText.setText("");
					editText.clearFocus();
				}
			}
		});

		return resultView;
	}

	private class SendReplyTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			String message = params[0];
			Account currentAccount = application.getActiveAccount();
			Map<String, String> userdata = application.getAccountInfo(currentAccount);
			
			return IBClient.sendReply(topicId, message, userdata);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			Log.d("IB", "replied successfully: " + result);
		}
	}
}
