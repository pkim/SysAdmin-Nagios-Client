package com.SysAdmin.EventListener;


// android
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
// com.SysAdmin
import com.SysAdmin.R;
import com.SysAdmin.Activity.ConclusionActivity;

/**
 * Contains and handles every event of the conclusion activity.
 * 
 * @author Lukas Bernreiter
 * @version 0.5, 28/03/2012
 * @since 0.2
 */
public class EventListener_Conclusion implements MultiChoiceModeListener, OnItemClickListener
{
	// Objects
	private ConclusionActivity mConclusion = null;
	
	// Constructor
	public EventListener_Conclusion(ConclusionActivity _conclusion)
	{
		this.mConclusion = _conclusion;
	}
	
	public void setEvents()
	{
		this.mConclusion.getListView().setMultiChoiceModeListener(this);
	}

	/** Callback method to be invoked when an item in this AdapterView has been clicked */
	public void onItemClick(AdapterView<?> _adapter, View _view, int position, long _id) {
		
	}
	
	/** Called to report a user click on a action button */
	public boolean onActionItemClicked(ActionMode _mode, MenuItem _item) 
	{
		switch(_item.getItemId())
		{
			case R.id.menuItem_Delete:
				this.deleteSelectedItems();
				_mode.finish();
				break;
				
			default:
				return true;
		}
		
		return true;
	}

	/** Called when a action mode is first created.
	 *  The menu supplied will be used to generate action buttons for the action mode. */
	public boolean onCreateActionMode(ActionMode _mode, Menu _menu) 
	{
		// Inflate menu
		MenuInflater inflater = _mode.getMenuInflater();
		inflater.inflate(R.menu.cab_conclusion, _menu);
		
		// Set title
		_mode.setTitle("Select Action");
		this.setCabSubTitle(_mode);
		
		return true;
	}

	/** Called when an action mode is about to be exited or destroyed. */
	public void onDestroyActionMode(ActionMode arg0) {	}

	/** Called to refresh an action mode's action menu whenever it is invalidated. */
	public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) 
	{
		return true;
	}

	/** Called when an item checked state is changed. */
	public void onItemCheckedStateChanged(ActionMode _mode, int _position, long _id, boolean _checked)
	{
		this.setCabSubTitle(_mode);
	}
	
	/**
	 * Updates the action bar
	 * @param _mode The current action mode.
	 */
	private void setCabSubTitle(ActionMode _mode)
	{
		Integer count = this.mConclusion.getListView().getCheckedItemCount();
		
		_mode.setSubtitle(count.toString() + " selected");
	}
	
	private void deleteSelectedItems()
	{
		// TODO: delete selected items
	}
}
