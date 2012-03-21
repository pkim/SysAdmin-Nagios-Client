package test;

import htl.ybbs.test.MyExpandableListView;

import com.SysAdmin.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import com.SysAdmin.*;

public class TestActivity extends Activity
{
	private ExpandableListView expandableListView;
	private String groupNames[];
	private String childNames[][];
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.expandableListView=(ExpandableListView)findViewById(R.id.myExpandableListView);
	
		for(int i=0;i<5;i++)
		{
			groupNames[i]="Group "+i;
		}
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<3;j++)
			{
				childNames[i][j]="Child "+i+"/"+j;
			}
		}
		
		MyExpandableListView myExpandableListView=new MyExpandableListView(expandableListView,this,groupNames,childNames);
		
		
		this.setContentView(R.layout.my_expandable_list_view);
	}
	
}
