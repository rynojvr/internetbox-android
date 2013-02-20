package com.rynojvr.android.internetbox.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.general.client.internetbox.entities.ChatTopic;

public class ChatDetailTopicFragment extends SherlockFragment {
	
	private ChatTopic topic;
	
	public ChatDetailTopicFragment(ChatTopic topic) {
		this.topic = topic;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
}
