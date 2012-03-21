package com.SysAdmin.Nagios.Entity;

import java.util.List;

public class HostEntity {
	
	private String hostName;	
	private Integer currentState;
	private List<ServiceEntity> services;
	
	/**
	 * Empty constructor
	 */
	public HostEntity()
	{}
	
	/**
	 * The constructor to create a HostEntity pre-configured with all available properties.
	 * 
	 * @param _hostName 	The name of the host	
	 * @param _currentState The state of the host
	 * @param _services		All services of the host
	 */
	public HostEntity(String _hostName, Integer _currentState, List<ServiceEntity> _services)
	{
		this.hostName     = _hostName;
		this.currentState = _currentState;
		this.services     = _services;
	}
	
	public void AddService(ServiceEntity _newService)
	{
		this.services.add(_newService);
	}
	
	public String getHostName()
	{
		return hostName;
	}
	
	public void setHostName(String _hostName)
	{
		this.hostName = _hostName;
	}
	
	public Integer getCurrentState()
	{
		return currentState;
	}
	
	public void setCurrentState(Integer _state)
	{
		this.currentState = _state;
	}
	
	public List<ServiceEntity> getServices()
	{
		return services;
	}
	
	public void setServices(List<ServiceEntity> _services)
	{
		this.services = _services;
	}
}


