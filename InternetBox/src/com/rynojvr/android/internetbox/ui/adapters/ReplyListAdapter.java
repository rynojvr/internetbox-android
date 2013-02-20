package com.rynojvr.android.internetbox.ui.adapters;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.general.client.internetbox.IBClient;
import com.rynojvr.general.client.internetbox.entities.ChatReply;
import com.rynojvr.general.client.internetbox.entities.ChatTopic;

public class ReplyListAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<ChatReply> replies;
	private ChatTopic topic;
	private final int REPLIES_PER_REQUEST = 25;
	

	public ReplyListAdapter(Context context, final ChatTopic topic /* , List<ChatReply> replies */) {
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.topic = topic;
		this.replies = new LinkedList<ChatReply>();

		new GetRepliesTask().execute();
	}

	@Override
	public int getCount() {
		// +1 for the progress thing.
		return replies.size() + (replies.size() < topic.getNumReplies() ? 1 : 0);
//		return (replies == null ? 1 : replies.size());
	}

	@Override
	public Object getItem(int position) {
		return replies.get(position);
	}

	public void addReplies(List<ChatReply> replies) {
//		if (this.replies == null)
//			replies = new LinkedList<ChatReply>();
//		this.replies = replies;
		this.replies.addAll(replies);
		notifyDataSetChanged();
		Log.d("IB REPLIES: ", replies.toString());
		

		if (this.replies.size() < topic.getNumReplies()) {
			Log.d("IB", "RepliesSize: " + topic.getNumReplies());
			Log.d("IB", "CurrentReplies: " + this.replies.size());
			new GetRepliesTask().execute();
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position >= replies.size()) {
//			ProgressBar progress = new ProgressBar(context);
//			progress.setLayoutParams(new LayoutParams(
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//			progress.setPadding(16, 16, 16, 16);
//			return progress;
			return inflater.inflate(R.layout.loading, null);
		}

//		if (convertView == null)
			convertView = inflater.inflate(
					R.layout.fragment_chat_detail_replies_item, null);

		Log.d("IB convertView", "" + convertView);
		final ChatReply current = (ChatReply) getItem(position);

		TextView createdBy = (TextView) convertView
				.findViewById(R.id.fragment_chat_detail_replies_item_text_created_by);
		createdBy.setText(current.getCreatedBy());

		TextView content = (TextView) convertView
				.findViewById(R.id.fragment_chat_detail_replies_item_text_content);
		content.setText(current.getContent());

		ImageView avatar = (ImageView) convertView
				.findViewById(R.id.fragment_chat_detail_replies_item_image_avatar);
		UrlImageViewHelper
				.setUrlDrawable(
						avatar,
						"http://questions.internetboxpodcast.com/members/avatars/meltinggeode-1354314694.png",
						R.drawable.ic_launcher);

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
		return replies.size() == 0;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	
	private class GetRepliesTask extends AsyncTask<Void, Void, List<ChatReply>> {
		@Override
		protected List<ChatReply> doInBackground(Void... params) {
			List<ChatReply> result = null;
			while (result == null) {
				try {
					Log.d("IB", "getting replies");
//					ChatTopic newTopic = IBClient.getChatTopic(topic.getId());
//					result = newTopic.getReplies();
					result = IBClient.getChatTopicReplies(topic.getId(), replies.size(), replies.size() + REPLIES_PER_REQUEST);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		@Override
		protected void onPostExecute(java.util.List<ChatReply> result) {
			addReplies(result);
		}
	}
}
