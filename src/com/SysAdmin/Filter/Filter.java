package com.SysAdmin.Filter;

import com.SysAdmin.Nagios.Entity.ServiceEntity;

public class Filter 
{
	private String hostName;
	private String serviceName;
	
	public Filter()
	{
		this.hostName    = new String();
		this.serviceName = new String();
	}
	
	public Filter(String _hostName, String _serviceName) throws NullPointerException
	{
		try
		{ 
			this.setHostName(_hostName); 
			this.setServiceName(_serviceName);
		}
		
		catch(NullPointerException exception)
		{ throw exception; }
	}
	
	public String getHostName()
	{ return this.hostName; }
	
	public void setHostName(String _hostName)  throws NullPointerException
	{
		if(_hostName == null || _hostName.isEmpty())
			throw new NullPointerException("ServiceEntity is null");
		
		this.hostName = _hostName;
	}
	
	public String getServiceName()
	{ return this.serviceName; }
	
	public void setServiceName(String _serviceName)  throws NullPointerException
	{
		if(_serviceName == null || _serviceName.isEmpty())
			throw new NullPointerException("ServiceEntity is null");
		
		this.serviceName = _serviceName;
	}
	
}
