package com.SysAdmin.Entity;

import com.SysAdmin.Nagios.NagiosServiceState;

public class ServiceEntity {
	
	public static final Integer ATTRIBUTECOUNT = 4;
	
	private String hostName;
	private String serviceDescription;
	private String pluginOutput;
	private NagiosServiceState currentState;
	
	public ServiceEntity()
	{}
	
	public ServiceEntity(String _hostName, String _serviceDescription, String _pluginOutput, NagiosServiceState _state)
	{
		this.setHostName(_hostName);
		this.setServiceDescription(_serviceDescription);
		this.setPluginOutput(_pluginOutput);
		this.setCurrentState(_state);
	}
	
	public String getHostName(){
		return hostName;
	}
	
	public void setHostName(String _hostName){
		this.hostName = _hostName;
	}
	
	public String getServiceDescription(){
		return serviceDescription;
	}
	
	public void setServiceDescription(String _serviceDescription){
		this.serviceDescription = _serviceDescription;
	}
	
	public String getPluginOutput(){
		return pluginOutput;
	}
	
	public void setPluginOutput(String _pluginOutput){
		this.pluginOutput = _pluginOutput;
	}
	
	public NagiosServiceState getCurrentState(){
		return currentState;
	}
	
	public void setCurrentState(NagiosServiceState _state){
		this.currentState = _state;
	}
}