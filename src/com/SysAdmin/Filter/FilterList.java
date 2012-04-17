package com.SysAdmin.Filter;

import java.io.Serializable;
import java.util.ArrayList;

import com.SysAdmin.FileHandler;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;


public class FilterList  
{
	private ArrayList<Filter> filters;
	private JSONSerializer    mJSONSerializer;
	private JSONDeserializer<ArrayList<Filter>>  mJSONDeserializer;
	
	public FilterList()
	{
		this.filters = new ArrayList<Filter>();
		this.mJSONSerializer   = new JSONSerializer();
		this.mJSONDeserializer = new JSONDeserializer<ArrayList<Filter>>();
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
	
	public void serialize(String _path) throws Exception
	{
		String jsonString = this.mJSONSerializer.serialize(this.filters);
		
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
			
			this.filters = this.mJSONDeserializer.deserialize(jsonString);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		
	}
}
