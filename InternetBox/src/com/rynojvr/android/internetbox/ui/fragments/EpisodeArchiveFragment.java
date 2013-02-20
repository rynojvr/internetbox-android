package com.rynojvr.android.internetbox.ui.fragments;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.rynojvr.android.internetbox.IbApplication;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.android.internetbox.ui.adapters.EpisodeArchiveAdapter;
import com.rynojvr.general.client.internetbox.IBClient;
import com.rynojvr.general.client.internetbox.entities.Episode;

public class EpisodeArchiveFragment extends SherlockFragment {
	
	// Instance members
	private LayoutInflater inflater;
	
	// Header view and company
	private View headerView;
	
	// Main View and friends
	private View mainView;
	private ProgressBar progressBar;
	private ExpandableListView listView;
	private EpisodeArchiveAdapter episodeArchiveAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.inflater = inflater;
		
		headerView = inflater.inflate(R.layout.fragment_episode_archive_header_view, null);
		populateHeaderView();
		
		mainView = inflater.inflate(R.layout.fragment_episode_archive, null);
		progressBar = (ProgressBar)mainView.findViewById(R.id.fragment_episode_archive_progress_bar);
		listView = (ExpandableListView)mainView.findViewById(R.id.fragment_episode_archive_list_view);
		episodeArchiveAdapter = new EpisodeArchiveAdapter(getActivity());
		populateMainView();
		
		new LoadEpisodesAsyncTask().execute();
		
		return mainView;
	}
	
	private void populateHeaderView() {
		// TODO: Populate the damn header. 
	}
	
	private void populateMainView() {
		listView.addHeaderView(headerView);
		listView.setAdapter(episodeArchiveAdapter);
	}
	
	private class LoadEpisodesAsyncTask extends AsyncTask<Void, Void, List<Episode>> {
		@Override
		protected List<Episode> doInBackground(Void... params) {
			try {
				return IBClient.getEpisodes(0, 10);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(List<Episode> result) {
			progressBar.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			
			episodeArchiveAdapter.add(result);
		}
	}
}
