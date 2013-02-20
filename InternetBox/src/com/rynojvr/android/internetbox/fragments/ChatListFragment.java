package com.rynojvr.android.internetbox.fragments;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.android.internetbox.ui.adapters.ChatFragmentAdapter;
import com.rynojvr.android.internetbox.ui.adapters.ChatListAdapter;
import com.rynojvr.general.client.internetbox.IBClient;
import com.rynojvr.general.client.internetbox.entities.ChatTopic;

public class ChatListFragment extends SherlockFragment {

	private ListView listView;
	public ChatFragmentAdapter fragmentAdapter;
	private final int OFFSET_FROM_BOTTOM_TO_BEGIN_LOADING_NEXT_BATCH = 5;

	public ChatListFragment(ChatFragmentAdapter adapter) {
		this.fragmentAdapter = adapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View resultView = inflater.inflate(R.layout.fragment_chat_list,
				null);
		listView = (ListView) resultView
				.findViewById(R.id.fragment_chat_list_list_view);
		final ChatListAdapter adapter = new ChatListAdapter(getActivity(),
				fragmentAdapter);
		// adapter.setOnTopicsLoadedListener(new OnTopicsLoadedListener() {
		// @Override
		// public void onTopicsLoaded() {
		//
		// }
		// });
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// Log.d("IB OnScroll", "firstVisibleItem: " + firstVisibleItem
				// +
				// "\nvisibleItemCount: " + visibleItemCount +
				// "\ntotalItemCount: " + totalItemCount + "\n");

				if ((totalItemCount - (firstVisibleItem + visibleItemCount)) < OFFSET_FROM_BOTTOM_TO_BEGIN_LOADING_NEXT_BATCH) {
					adapter.loadMoreTopics();
				}
			}
		});

		return resultView;
	}
}
