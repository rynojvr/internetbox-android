package com.rynojvr.android.internetbox.ui.adapters;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rynojvr.android.internetbox.R;
import com.rynojvr.general.client.internetbox.IBClient;
import com.rynojvr.general.client.internetbox.entities.ChatTopic;

public class ChatListAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private ChatFragmentAdapter adapter;
	private List<ChatTopic> topics;
	private OnTopicsLoadedListener listener;
	private GetTopicsTask currentTask;
	private final int TOPICS_PER_REQUEST = 15;
	
	public ChatListAdapter(Context context, /*List<ChatTopic> topics,*/ ChatFragmentAdapter adapter) {
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		this.topics = topics;
		this.topics = new LinkedList<ChatTopic>();
		this.adapter = adapter;
//		ChatTopic topic = new ChatTopic();
//		topic.title = "TBSADFSDF";
//		topics.add(topic);
		
		registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				currentTask = null;
			}
		});
		
		loadMoreTopics();
	}
	
	public void loadMoreTopics() {
		if (currentTask == null) {
			currentTask = new GetTopicsTask();
			currentTask.execute();
		}
	}

	@Override
	public int getCount() {
		return topics.size() + (currentTask == null ? 0 : 1) ;
	}
	
	public void addTopics(List<ChatTopic> topics) {
		notifyDataSetChanged();
		this.topics.addAll(topics);
		notifyDataSetChanged();
		Log.d("IB TOPICS: ", topics.toString());
	}

	@Override
	public Object getItem(int position) {
		return topics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position >= topics.size()) {
			return inflater.inflate(R.layout.loading, null);
		}
		
//		if (convertView == null) 
			convertView = inflater.inflate(R.layout.chat_list_item, null);
		
		final ChatTopic current = (ChatTopic)getItem(position);
		
		TextView title = (TextView)convertView.findViewById(R.id.chat_list_item_text_title);
		title.setText(current.getTitle());
		
		TextView subTitle = (TextView)convertView.findViewById(R.id.chat_list_item_text_subtitle);
		String subTitleText = "Created by " + current.getCreatedBy() + " | " + current.getNumReplies() + " Replies";
		subTitle.setText(subTitleText);
		
		Button newCount = (Button)convertView.findViewById(R.id.chat_list_item_button_new_count);
		newCount.setText("" + current.getNumReplies());
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.loadTopic(current);
			}
		});
		
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return topics.size() == 0;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}
	
	private void dispatchOnTopicsLoaded() {
		if (this.listener != null) {
			this.listener.onTopicsLoaded();
		}
	}
	
	public void setOnTopicsLoadedListener(OnTopicsLoadedListener listener) {
		this.listener = listener;
	}
	
	public static interface OnTopicsLoadedListener {
		public void onTopicsLoaded();
	}
	
	private class GetTopicsTask extends AsyncTask<Void, Void, List<ChatTopic>> {
		@Override
		protected List<ChatTopic> doInBackground(Void... params) {
			List<ChatTopic> result = null;
			while (result == null) {
				try {
					Log.d("IB", "getting topics...");
//					topics = IBClient.getTopChatTopics();
					result = IBClient.getChatTopics(topics.size(), topics.size() + TOPICS_PER_REQUEST);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		@Override
		protected void onPostExecute(List<ChatTopic> result) {
			Log.d("IB", "got topics! :D: " + result);
			addTopics(result);
//			getLayoutInflater().inflate(R.layout.activity_chat, null);
//			setContentView(R.layout.activity_chat);
//			setupSlidingMenu();
		}
	}
}
