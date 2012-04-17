package com.SysAdmin.EventListener;

import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.SysAdmin.Activity.FilterActivity;

/**
 * Contains and handles every event of the filter activity.
 * 
 * @author Lukas Bernreiter
 * @version 0.7, 22/02/2012
 * @since 0.2
 */
public class EventListener_Filter implements OnChildClickListener
{
	private FilterActivity mFilter = null;
	
	public EventListener_Filter(FilterActivity _filter)
	{
		this.mFilter = _filter;		
	}
	
	public void setEvents()
	{
		this.mFilter.getExpListView().setOnChildClickListener(this);
	}

	public boolean onChildClick(ExpandableListView _expListView, View _view, int _group, int _child, long _id)
	{
		CheckedTextView ckTextView = (CheckedTextView)_view;
		
		ckTextView.setChecked(!ckTextView.isChecked());
		
		int position = this.getPosition(_group, _child);
		
		_expListView.setItemChecked(position, ckTextView.isChecked());
		
		return true;
	}
	
	private int getPosition(int _group, int _child)
	{
		return this.mFilter.getExpListView().getFlatListPosition(ExpandableListView.getPackedPositionForChild(_group, _child));
	}

}
