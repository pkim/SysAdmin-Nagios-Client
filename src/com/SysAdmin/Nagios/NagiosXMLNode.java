package com.SysAdmin.Nagios;

public class NagiosXMLNode 
{
	
	/**
	 * Parent Nodes
	 */
	public static final String NODE_ROOT  		= "nagios_status";
	public static final String NODE_HOSTS 		= "hosts";
	public static final String NODE_HOST  		= "host";
	public static final String NODE_SERVICES 	= "services";
	public static final String NODE_SERVICE		= "service";
	
	
	/**
	 * Host Attributes
	 */
	public static final String NODE_HOST_ATTRIBUTE_HOSTNAME      = "host_name";
	public static final String NODE_HOST_ATTRIBUTE_CURRENT_STATE = "current_state";
	
	
	/**
	 * Service Attributes
	 */
	public static final String NODE_SERVICE_ATTRIBUTE_HOSTNAME  			= "host_name";
	public static final String NODE_SERVICE_ATTRIBUTE_SERVICE_DESCRIPTION  	= "service_description";
	public static final String NODE_SERVICE_ATTRIBUTE_PLUGIN_OUTPUT	      	= "plugin_output";
	public static final String NODE_SERVICE_ATTRIBUTE_CURRENT_STATE			= "current_state";
	
	
	/**
	 * ID of the subnodes of the root node "nagios_status"
	 */
	public static final Integer Node_ID_PROGRAMMSTATUS = 1;
	public static final Integer NODE_ID_HOSTS          = 3;
	public static final Integer NODE_ID_SERVICES 	   = 5;
	
	
	
}
