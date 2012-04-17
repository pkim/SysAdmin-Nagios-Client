package com.SysAdmin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

/**
 * Expandable listView
 * 
 * @author Markus Hinterleitner
 * @author Lukas Bernreiter
 * @version 0.5, 21/03/2012
 * @since 0.3
 */
public class MyExpandableListView implements OnClickListener
{
	
	//ExpandableListAdapter
	private static ExpandableListAdapter mAdapter;

	//ExpandableListView
	private static ExpandableListView expandableListView;

	//Keys for the ExpandableListAdapter
	public static final String GROUPNAME = "GROUPNAME";
	public static final String CHILDNAME = "CHILDNAME";
	
	private static Context context;
	
	private static String[] groupNames;
	private static String[][] childNames;

	public static void createExpandableListView(Context _context,String[] _groupNames,String[][] _childNames,ExpandableListView _expandableListView)
	{
		context=_context;
		
		groupNames=_groupNames;
		childNames=_childNames;
		
		expandableListView=_expandableListView;
		
		createExpandableListView();
	}
	
	private static void createExpandableListView()
	{
	
	 	//GroupData
		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
 
		//ChildData
		List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
	 
		//Group Entry
		Map<String, String> curGroupMap;

		//Create groups for the ExpandableListView
	 
	 	 for(int i=0;i<groupNames.length;i++)
	 	 {
	 		 //Map that contains one group
	 		 curGroupMap= new HashMap<String, String>();
	 		 
	 		 curGroupMap.put(GROUPNAME,groupNames[i]);
	 		 groupData.add(curGroupMap);
	 		 
	 		 
	 		//List that contains all children for one group
			 List<Map<String, String>> children = new ArrayList<Map<String, String>>();
	 		 
		 	 //Map that contains one child
		     Map<String, String> curChildMap;
			 
		     // add childs
			 for (String childName: childNames[i])
			 {
				 curChildMap = new HashMap<String, String>();
		    	 curChildMap.put(CHILDNAME, childName);
		    	 curChildMap.put(GROUPNAME, groupNames[i]);
		    	 children.add(curChildMap);	    	 
			 }
			 
	    	 //Add child map to the list
		     childData.add(children);
		
	 	 }
	 
 /*
		 for (String[] childNameArray: childNames)
		 {
			 
		 	
		 	 //Map that contains one child
		     Map<String, String> curChildMap;
			 
			 for (String childName: childNameArray)
			 {
				 curChildMap = new HashMap<String, String>();
		    	 curChildMap.put(CHILDNAME,childName);
		    	 curChildMap.put(GROUPNAME,groupNames[i]);
		    	 children.add(curChildMap);
			 }
			 
			 //Add child map to the list
		     childData.add(children);
		 }*/
 
		 //Create a new adapter for the expandable listView
		 mAdapter = new SimpleExpandableListAdapter(
	         context,							// context
	         groupData,							// group data
	         R.layout.exp_list_group,			// group layout
	         new String[] {GROUPNAME},			// key to map group content
	         new int[] { R.id.textGroupItem},
	         childData,							// child data
	         R.layout.exp_list_child,			// child layout
	         new String[] {CHILDNAME},			// keys to map child content
	         new int[] {R.id.checkedText_Child}
	         );
		 //Set adapter for the ExpandableListView
		 expandableListView.setAdapter(mAdapter);		
		 expandableListView.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
		 expandableListView.setItemsCanFocus(false);
	}

    public static View getChildView(int groupPosition, int childPosition, View convertView) {

    	String child = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exp_list_child, null);
        }

        CheckedTextView childTextView = (CheckedTextView) convertView.findViewById(R.id.checkedText_Child);

        childTextView.setText(child);

        return convertView;
    }
	
    public static String getChild(int groupPosition, int childPosition) 
    {
        return childNames[groupPosition][childPosition];
    }
    
	public void onClick(View arg0) 
	{
		// TODO Auto-generated method stub
	
	}
}
