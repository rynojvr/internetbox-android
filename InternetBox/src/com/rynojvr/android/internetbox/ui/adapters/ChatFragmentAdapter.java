package com.rynojvr.android.internetbox.ui.adapters;

import com.rynojvr.android.internetbox.*;
import com.rynojvr.android.internetbox.fragments.*;
import com.rynojvr.general.client.internetbox.entities.ChatTopic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ChatFragmentAdapter extends FragmentPagerAdapter {
	
	private ChatDetailFooterFragment chatDetailFooterFragment;
	private ChatDetailFragment chatDetailFragment;
	private OnTopicSelectListener listener;
	private boolean switchChatDetailView;
	private FragmentManager fm;

	public ChatFragmentAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
		
		// Remove
//		chatDetailFragment = new ChatDetailFragment();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
//		return super.getPageTitle(position);
		switch (position) {
		case 0:
			return "CHAT List";
		default:
			return chatDetailFragment.getTitle();
		} 
	}

	@Override
	public Fragment getItem(int index) {
//		Fragment fragment = (index == 0) 
//				? new ChatListFragment() 
//				: new ChatDetailFragment();
				
//		if (index == 0) {
//			return new ChatListFragment();
//		} else {
//			return (chatDetailFragment == null) 
//					? new DummySectionFragment()
//					: chatDetailFragment;
//		}
		
		switch (index) {
		case 0:
			return new ChatListFragment(this);
		case 1:
			return chatDetailFragment;
		default:
			return new DummySectionFragment();
		}
	}
	
	@Override
	public int getItemPosition(Object object) {
		if (object instanceof ChatDetailFragment && switchChatDetailView) {
			switchChatDetailView = false;
			Log.d("IB", "new chat Detail");
			return POSITION_NONE;
		}
		return POSITION_UNCHANGED;
	}

	@Override
	public int getCount() {
//		return 2;
		return (chatDetailFragment == null) ? 1 : 2;
	}
	
	public void loadTopic(ChatTopic topic) {
		chatDetailFooterFragment = (ChatDetailFooterFragment) fm.findFragmentById(R.id.frag);
		
		if (chatDetailFooterFragment != null) {
			fm.beginTransaction()
				.remove(chatDetailFooterFragment)
				.commit();
		}
		
		if (chatDetailFragment != null) {
			fm.beginTransaction()
				.remove(chatDetailFragment)
				.commit();
		}
		chatDetailFragment = new ChatDetailFragment(topic);
		
//		chatDetailFooterFragment = (ChatDetailFooterFragment) fm.findFragmentById(R.id.frag);
//		chatDetailFooterFragment.setTopicId(topic.getId());
		
		switchChatDetailView = true;
		notifyDataSetChanged();
		listener.onTopicSelect(topic);
	}
	
	public void setOnTopicSelectListener(OnTopicSelectListener listener) {
		this.listener = listener;
	}
	
	public static interface OnTopicSelectListener {
		public void onTopicSelect(ChatTopic topic);
	}
	
	public static class DummySectionFragment extends Fragment {
		public DummySectionFragment() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText("Hmpf... Shouldn't have gotten here.");
			return textView;
		}
	}
}
