package com.SysAdmin.Filter;

import java.io.Serializable;
import java.util.ArrayList;

import com.SysAdmin.FileHandler;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;


public class FilterList  
{
	private String hostName = new String();
	private String url		= new String();
	
	private ArrayList<Filter> filters;
	private JSONSerializer    mJSONSerializer;
	private JSONDeserializer<FilterList>  mJSONDeserializer;
	
	public FilterList()
	{
		this.filters = new ArrayList<Filter>();
		this.mJSONSerializer   = new JSONSerializer();
		this.mJSONDeserializer = new JSONDeserializer<FilterList>();
	}
	
	public FilterList(ArrayList<Filter> _filters) throws NullPointerException
	{
		super();
		
		try
		{ this.setFilters(_filters); }
		catch(NullPointerException exception)
		{ throw exception; }
	}
	
	public void setFilters(ArrayList<Filter> _filters) throws NullPointerException
	{
		if(_filters == null || _filters.size() == 0)
			throw new NullPointerException("_filters are null");
		
		this.filters = _filters;
	}
	
	public void addFilter(Filter _filter)
	{
		if(_filter == null)
			throw new NullPointerException("_filter is null");
		
		this.filters.add(_filter);
	}
	
	public ArrayList<Filter> getFilters()
	{ return this.filters; }
	
	public Filter getFilter(int _position) throws Exception
	{
		if(_position<0 || _position >= this.filters.size())
			throw new Exception("_position is out of range");
		
		return this.filters.get(_position);
	}
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void serialize(String _path) throws Exception
	{
		String jsonString = this.mJSONSerializer.exclude("mJSONSerializer").exclude("mJSONDeserializer").include("filters").serialize(this);
		
		try 
		{
			FileHandler.write(_path, jsonString);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
	
	public void deserialize(String _path) throws Exception
	{
		String jsonString = new String();
		
		try 
		{
			jsonString = FileHandler.read(_path);
			
			FilterList filterList = this.mJSONDeserializer.use( null, FilterList.class).deserialize(jsonString);
			
			this.hostName = filterList.getHostName();
			this.url      = filterList.url;
			this.filters  = filterList.getFilters();
		} 
		catch (Exception e) 
		{
			throw e;
		}
		
	}


}
