package com.rynojvr.android.internetbox.ui.adapters;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.sax.StartElementListener;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.rynojvr.android.internetbox.R;
import com.rynojvr.android.internetbox.models.enums.SlidingMenuOptions;


public class SlidingMenuListAdapter implements ListAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private OnMenuItemClickListener listener;
	
	public SlidingMenuListAdapter(Context context) {
		this.context = context;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return SlidingMenuOptions.values().length;
	}

	@Override
	public Object getItem(int position) {
		return SlidingMenuOptions.values()[position];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final SlidingMenuOptions current = (SlidingMenuOptions)getItem(position);
		
		if (convertView == null) 
			convertView = inflater.inflate(R.layout.sliding_menu_item, null);
		
		ImageView slidingMenuItemImage = (ImageView)convertView.findViewById(R.id.sliding_menu_item_image);
		TextView slidingMenuItemText = (TextView)convertView.findViewById(R.id.sliding_menu_item_text);
		
		if (current.getFragment() != null) {
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					Intent intent = new Intent();
//					intent.setClass(context, current.getIntentClass());
//					context.startActivity(intent);
					
//					fragmentManager.beginTransaction()
//						.replace(R.id.activity_main_fragment_holder, current.getFragment())
//						.commit();
					if (SlidingMenuListAdapter.this.listener != null) {
//						SlidingMenuListAdapter.this.listener.onMenuItemClick(current.getFragment());
						SlidingMenuListAdapter.this.listener.onMenuItemClick(current);
					}
				}
			});
		}
		
		// TODO: Default image for imageViews
//		UrlImageViewHelper.setUrlDrawable(slidingMenuItemImage, current.getImageUrl(), R.drawable.ic_action_search);
		slidingMenuItemImage.setImageResource(current.getImageResourceID());
		slidingMenuItemText.setText(current.getOptionText());
		
		return convertView;
	}
	
	public static interface OnMenuItemClickListener {
//		public void onMenuItemClick(SherlockFragment fragment);
		public void onMenuItemClick(SlidingMenuOptions menuItem);
	}
	
	public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
		this.listener = listener;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}

}
