package com.rynojvr.android.internetbox.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.android.internetbox.ui.adapters.ReplyListAdapter;
import com.rynojvr.general.client.internetbox.entities.ChatTopic;

public class ChatDetailFragment extends SherlockFragment {
	
	private ChatTopic topic;
	private LayoutInflater inflater;
	
	public ChatDetailFragment(ChatTopic topic) {
		this.topic = topic;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		FragmentManager fm = getFragmentManager();
		ChatDetailFooterFragment chatDetailFooterFragment = (ChatDetailFooterFragment) fm.findFragmentById(R.id.frag);
		chatDetailFooterFragment.setTopicId(topic.getId());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View resultView = inflater.inflate(R.layout.fragment_chat_detail, null);
		ListView listView = (ListView)resultView.findViewById(R.id.fragment_chat_detail_list_replies);
//		loadTopicContent(resultView);
//		loadTopicReplies(resultView);
		
		ReplyListAdapter adapter = new ReplyListAdapter(getActivity(), topic);
		View headerView = getHeaderView(resultView, inflater);
//		View footerView = getFooterView(resultView, inflater);
		listView.addHeaderView(headerView);
//		listView.addFooterView(footerView);
		listView.setAdapter(adapter);
		
//		ChatDetailFooterFragment f = (ChatDetailFooterFragment)getFragmentManager().findFragmentById(R.id.frag);
//		f.setTopicId(topic.getId());
		
		return resultView;
	}
	
	private View getHeaderView(View view, LayoutInflater inflater) {
		View resultView = inflater.inflate(R.layout.fragment_chat_detail_topic, null);
		
		TextView createdBy = (TextView)resultView.findViewById(R.id.fragment_chat_detail_fragment_topic_text_created_by);
//		createdBy.setText("Jason");
		createdBy.setText(topic.getCreatedBy());
		
		TextView title = (TextView)resultView.findViewById(R.id.fragment_chat_detail_fragment_topic_text_title);
//		title.setText("Internet Box App");
		title.setText(topic.getTitle());
		
		TextView content = (TextView)resultView.findViewById(R.id.fragment_chat_detail_fragment_topic_text_content);
//		content.setText("Hey, Mike is there any way a CHAT app could be developed for iPod/iPhone?");
		content.setText(topic.getContent());
		
		ImageView avatar = (ImageView)resultView.findViewById(R.id.fragment_chat_detail_fragment_topic_image_avatar);
		UrlImageViewHelper.setUrlDrawable(avatar, "http://questions.internetboxpodcast.com/members/avatars/jason-1342601464.png", R.drawable.ic_launcher);
		
		return resultView;
	}
	
//	private View getFooterView(View view, LayoutInflater inflater) {
//		View resultView = inflater.inflate(R.layout.fragment_chat_detail_footer, null);
//		
//		return resultView;
//	}

	private void loadTopicContent(View view) {
//		TextView textView = (TextView)view.findViewById(R.id.fragment_chat_detail_text_title);
//		textView.setText("SUPER BLAH");
		getActivity().getSupportFragmentManager()
			.beginTransaction()
//			.replace(R.id.fragment_chat_detail_fragment_topic,  new ChatDetailTopicFragment(topic))
			.commit();
	}
	
	private void loadTopicReplies(View view) {
		getActivity().getSupportFragmentManager()
			.beginTransaction()
//			.replace(R.id.fragment_chat_detail_fragment_replies, new ChatDetailRepliesFragment(topic))
			.commit();
	}
	
	public String getTitle() {
		return topic.getTitle();
	}
}
