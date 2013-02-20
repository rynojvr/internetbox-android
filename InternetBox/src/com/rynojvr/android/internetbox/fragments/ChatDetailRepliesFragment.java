package com.rynojvr.android.internetbox.fragments;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockFragment;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.android.internetbox.ui.adapters.ReplyListAdapter;
import com.rynojvr.general.client.internetbox.IBClient;
import com.rynojvr.general.client.internetbox.entities.ChatReply;
import com.rynojvr.general.client.internetbox.entities.ChatTopic;

public class ChatDetailRepliesFragment extends SherlockFragment {
	
	private ListView listView;
	private ChatTopic topic;
	
	public ChatDetailRepliesFragment(ChatTopic topic) {
		this.topic = topic;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);
		final View resultView = inflater.inflate(R.layout.fragment_chat_detail_replies, null);
		listView = (ListView) resultView.findViewById(R.id.fragment_chat_detail_replies_list_view);
		
		new AsyncTask<Void, Void, List<ChatReply>>() {

			@Override
			protected List<ChatReply> doInBackground(Void... params) {
				List<ChatReply> result = null;
				while (result == null) {
					try {
						Log.d("IB", "getting replies");
						ChatTopic newTopic = IBClient.getChatTopic(topic.getId());
						result = newTopic.getReplies();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return result;
			}
			
			protected void onPostExecute(java.util.List<ChatReply> result) {
				ProgressBar progress = (ProgressBar)resultView.findViewById(R.id.fragment_chat_detail_replies_progress_bar);
				progress.setVisibility(View.GONE);
//				ReplyListAdapter adapter = new ReplyListAdapter(getActivity(), result);
//				listView.setAdapter(adapter);
//				listView.setLayoutParams(
//						new LayoutParams(
//								LayoutParams.MATCH_PARENT, 
//								LayoutParams.MATCH_PARENT));
				listView.invalidate();
			}
		}.execute();
		return resultView;
	}
	
}
