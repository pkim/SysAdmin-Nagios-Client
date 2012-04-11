package com.SysAdmin.Nagios.Entity;

import java.util.ArrayList;
import java.util.List;

import com.SysAdmin.Nagios.NagiosServiceState;

public class HostEntity {
	
	public static final Integer ATTRIBUTECOUNT = 3;
	
	private String hostName;	
	private NagiosServiceState currentState;
	private List<ServiceEntity> services;
	
	/**
	 * Empty constructor
	 */
	public HostEntity()
	{
		this.hostName     = new String();
		this.currentState = NagiosServiceState.UNKNOWN;
		this.services     = new ArrayList<ServiceEntity>();
	}
	
	/**
	 * The constructor to create a HostEntity pre-configured with all available properties.
	 * 
	 * @param _hostName 	The name of the host	
	 * @param _currentState The state of the host ( see: http://nagios.sourceforge.net/docs/3_0/pluginapi.html );
	 * @param _services		All services of the host
	 */
	public HostEntity(String _hostName, NagiosServiceState _currentState, List<ServiceEntity> _services)
	{
		super();
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
	
	public NagiosServiceState getCurrentState()
	{
		return currentState;
	}
	
	public void setCurrentState(NagiosServiceState _state)
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


