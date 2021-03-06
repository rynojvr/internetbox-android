package com.rynojvr.android.internetbox.models.enums;

import com.actionbarsherlock.app.SherlockFragment;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.android.internetbox.ui.ChatActivity;
import com.rynojvr.android.internetbox.ui.fragments.EpisodeArchiveFragment;

public enum SlidingMenuOptions {
	News(
		"News", 
		R.drawable.ic_menu_news,
		null
	),
	Rss(
		"Rss", 
		R.drawable.ic_menu_rss,
		null
	),
	EpisodeArchive(
		"Episode Archive", 
		R.drawable.ic_menu_episode_archive,
		new EpisodeArchiveFragment()
	),
	SubmitIntro(
		"Submit an Intro", 
		R.drawable.ic_menu_submit_an_intro,
		null
	),
	AskQuestion(
		"Ask a Question", 
		R.drawable.ic_menu_ask_a_question,
		null
	),
	QuestionArchive(
		"Question Archive", 
		R.drawable.ic_menu_question_archive,
		null
	),
	Chat(
		"CHAT", 
		R.drawable.ic_menu_chat,
//		ChatActivity.class
		null
	),
	Live(
		"Live", 
		R.drawable.ic_menu_live,
		null
	);
	
	private int imageResourceID;
	private String optionText;
//	private Class<?> intentClass;
	private SherlockFragment fragment;
	
	private SlidingMenuOptions(String optionText, int imageResourceID, /*Class<?> intentClass*/ SherlockFragment fragment) {
		this.optionText = optionText;
		this.imageResourceID = imageResourceID;
//		this.intentClass = intentClass;
		this.fragment = fragment;
	}
	
	public String getOptionText() {
		return this.optionText;
	}
	
	public int getImageResourceID() {
		return this.imageResourceID;
	}
	
	public SherlockFragment getFragment() {
		return this.fragment;
	}
	
//	public Class<?> getIntentClass() {
//		return this.intentClass;
//	}
}
