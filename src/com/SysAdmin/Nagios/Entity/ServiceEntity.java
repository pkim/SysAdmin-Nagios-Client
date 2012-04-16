package com.SysAdmin.Nagios.Entity;

import com.SysAdmin.Nagios.NagiosServiceState;

/**
 * Describes the structure of a service entity.
 * 
 * @author Lukas Bernreiter
 * @author Patrik Kimmeswenger
 * @version 0.7, 16/04/2012
 * @since 0.6
 */
public class ServiceEntity {
	
	public static final Integer ATTRIBUTECOUNT = 4;
	
	private String hostName = new String();
	private String serviceDescription = new String();
	private String pluginOutput = new String();
	private NagiosServiceState currentState = null;
	private Boolean mChecked = false;
	
	public ServiceEntity()
	{
		this.hostName 			= new String();
		this.serviceDescription = new String();
		this.pluginOutput 		= new String();
		this.currentState 		= NagiosServiceState.UNKNOWN;
	}
	
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
	
	public Boolean isChecked() {
		return this.mChecked;
	}
	
	public void check(Boolean _checked) {
		this.mChecked = _checked;
	}
}