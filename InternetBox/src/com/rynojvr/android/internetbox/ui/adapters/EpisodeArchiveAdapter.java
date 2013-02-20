package com.rynojvr.android.internetbox.ui.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.rynojvr.android.internetbox.BuildConfig;
import com.rynojvr.android.internetbox.IbApplication;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.general.client.internetbox.IBClient;
import com.rynojvr.general.client.internetbox.entities.Episode;

public class EpisodeArchiveAdapter extends BaseExpandableListAdapter {
	
	public List<Episode> episodes;
	private LayoutInflater inflater;
	
	public EpisodeArchiveAdapter(Context context) {
		episodes = new ArrayList<Episode>();
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void add(List<Episode> episodes) {
		boolean hasNewEpisodes = false;
		for (Episode episode : episodes) {
			if (!this.episodes.contains(episode)) {
				this.episodes.add(episode);
				hasNewEpisodes = true;
			}
		}
		
		if (hasNewEpisodes) {
			notifyDataSetChanged();
			notifyDataSetInvalidated();
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return episodes.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return episodes.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View result = inflater.inflate(R.layout.fragment_episode_archive_item, null);
		Episode current = (Episode)getGroup(groupPosition);
		
		TextView title = (TextView)result.findViewById(R.id.fragment_episode_archive_item_text_title);
		title.setText("Episode " + current.getId());
		
		return result;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
