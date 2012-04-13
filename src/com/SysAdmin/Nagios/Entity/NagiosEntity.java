package com.SysAdmin.Nagios.Entity;


/**
 * Defines the structure of an Nagios entity.
 * 
 * @author Patrik Kimmeswenger
 * @version 0.6, 13/04/2012
 * @since 0.6
 */
public class NagiosEntity {
	
	private String name;
	private String url;
	private HostEntity[] 	hosts;
	private ServiceEntity[] services;
	
	public NagiosEntity()
	{}
	
	public NagiosEntity(String _name, String _url, HostEntity[] _hostList){
		this.setName(_name);
		this.setUrl(_url);
		this.setHosts(_hostList);
	}
	
	public String getName(){
		return name;
	}
	public void setName(String _name){
		this.name = _name;
	}
	
	public String getUrl(){
		return url;
	}
	public void setUrl(String _url){
		this.url = _url;
	}
	
	public HostEntity[] getHosts(){
		return hosts;
	}
	public void setHosts(HostEntity[] hostEntities){
		this.hosts = hostEntities;
	}

	public ServiceEntity[] getServices() {
		return services;
	}

	public void setServices(ServiceEntity[] services) {
		this.services = services;
	}
	
	
}
