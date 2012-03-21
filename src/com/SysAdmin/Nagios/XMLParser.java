package com.SysAdmin.Nagios;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

import com.SysAdmin.AppFacade;
import com.SysAdmin.Entity.HostEntity;
import com.SysAdmin.Entity.NagiosEntity;
import com.SysAdmin.Entity.ServiceEntity;

public class XMLParser {
	

	
	private static NagiosEntity nagiosEntity;
	
	public static NagiosEntity parce(String _xmlFile) throws Exception
	{
		nagiosEntity = new NagiosEntity();
		
		// if parameter object xmlFile is empty
		if(_xmlFile.isEmpty())
			throw new Exception("File is empty");
			
		try{
			// Document XML
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder        builder = factory.newDocumentBuilder();
			Document               dom 	   = builder.parse(new File(_xmlFile));
			
			// XML Nodes
			Element  root  = dom.getDocumentElement();									// XML Root
			NodeList node_root = root.getElementsByTagName(NagiosXMLNode.NODE_ROOT);	// subNodes
			
			Node node_hosts    = node_root.item(NagiosXMLNode.NODE_ID_HOSTS);
			Node node_services = node_root.item(NagiosXMLNode.NODE_ID_SERVICES);
			
			// parse services
			nagiosEntity.setServices(parseServices(node_services));
			
			// parse hosts
			nagiosEntity.setHosts   (parseHosts(node_hosts));
		
		}
		
		catch(Exception e)
		{ throw new RuntimeException(e); }
		
		return nagiosEntity;
	}
	
	private static HostEntity[] parseHosts(Node _nodeHosts)
	{
		NodeList hosts	    = _nodeHosts.getChildNodes();
		Integer  hostsCount = hosts.getLength();
		
		HostEntity[] hostEntities = new HostEntity[hostsCount];
		
		// get sub nodes
		for(int i=0; i < hostsCount; i++)
		{
			hostEntities[i] = parseHost(hosts.item(i));
		}
		
		return hostEntities;
	}
	
	private static HostEntity parseHost(Node _nodeHost)
	{
		HostEntity hostEntity = new HostEntity();
		
		NodeList hostAttributes 	 = _nodeHost.getChildNodes();
		Integer  hostAttributesCount = hostAttributes.getLength();
		
		Integer checkedAttributes = 0;
		
		//
		for( int i=0; i < hostAttributesCount; i++)
		{
			Node   hostAttribute  = hostAttributes.item(i);
			String attributeName  = hostAttribute.getNodeName();
			String attributeValue = hostAttribute.getNodeValue();
			
			// HOSTNAME
			if(attributeName.equalsIgnoreCase(NagiosXMLNode.NODE_HOST_ATTRIBUTE_HOSTNAME))
			{
				if(hostEntity.getHostName().isEmpty())
				{
					hostEntity.setHostName(attributeValue);
					checkedAttributes++;
				}
				
				else
				{
					Log.e(AppFacade.GetTag(), String.format("Duplicated attribute %s while parsing Nagios XML", attributeName));
				}
			}
			
			// 
			else if (attributeName.equalsIgnoreCase(NagiosXMLNode.NODE_HOST_ATTRIBUTE_CURRENT_STATE))
			{
				NagiosServiceState currentState = NagiosServiceState.UNKNOWN;
				
				try
				{
					Integer attributeValue_AsInteger = Integer.parseInt(attributeValue);
					currentState 					 = NagiosServiceState.getEnum(attributeValue_AsInteger);

					hostEntity.setCurrentState(currentState);
					
					checkedAttributes++;
				}
				
				catch(Exception _excpeption)
				{ 
					Log.e(AppFacade.GetTag(), String.format("Coudn't convert %s to an integer while parsing the nagios xml file", attributeValue)); 
				
					// break the for-loop because all needed attributes are dedected
					i = hostAttributesCount;
				}
			
			}
			
			// Check if all needed attributes are parsed
			if ( HostEntity.ATTRIBUTECOUNT >= checkedAttributes)
			{
				// break the for-loop because all needed attributes are dedected
				i = hostAttributesCount;
			}
			
		}
		
		// set services of the host
		ServiceEntity[] services = nagiosEntity.getServices();
		
		for (ServiceEntity serviceEntity: services)
		{
			if(serviceEntity.getHostName() == hostEntity.getHostName())
			{
				hostEntity.AddService(serviceEntity);
			}
		}
		
		
		return hostEntity;
	}
	
	
	private static ServiceEntity[] parseServices(Node _nodeServices)
	{
		NodeList services 	   = _nodeServices.getChildNodes();
		Integer  servicesCount = services.getLength();
		
		ServiceEntity[] serviceEntities = new ServiceEntity[servicesCount];
		
		// get sub nodes
		for(int i=0; i < servicesCount; i++)
		{
			serviceEntities[i] = parseService(services.item(i));
		}
		
		return serviceEntities;
	}
	
	private static ServiceEntity parseService(Node _nodeService)
	{
		ServiceEntity serviceEntity = new ServiceEntity();
		
		NodeList hostAttributes 	 = _nodeService.getChildNodes();
		Integer  hostAttributesCount = hostAttributes.getLength();
		
		Integer checkedAttributes = 0;
		
		//
		for( int i=0; i < hostAttributesCount; i++)
		{
			Node   serviceAttribute  = hostAttributes.item(i);
			String attributeName  = serviceAttribute.getNodeName();
			String attributeValue = serviceAttribute.getNodeValue();
			
			// HOSTNAME
			if(attributeName.equalsIgnoreCase(NagiosXMLNode.NODE_SERVICE_ATTRIBUTE_HOSTNAME))
			{
				if(serviceEntity.getHostName().isEmpty())
				{
					serviceEntity.setHostName(attributeValue);
					checkedAttributes++;
				}
				
				else
				{ Log.e(AppFacade.GetTag(), String.format("Duplicated attribute %s while parsing Nagios XML", attributeName)); }
			}
			
			// Service Description
			else if (attributeName.equalsIgnoreCase(NagiosXMLNode.NODE_SERVICE_ATTRIBUTE_SERVICE_DESCRIPTION))
			{
				serviceEntity.setServiceDescription(attributeValue);
				
				checkedAttributes++;
			}
			
			// Service PluginOutput
			else if (attributeName.equalsIgnoreCase(NagiosXMLNode.NODE_SERVICE_ATTRIBUTE_PLUGIN_OUTPUT))
			{
				serviceEntity.setPluginOutput(attributeValue);
				
				checkedAttributes++;
			}
			
			// CURRENT STATE
			else if (attributeName.equalsIgnoreCase(NagiosXMLNode.NODE_SERVICE_ATTRIBUTE_CURRENT_STATE))
			{
				NagiosServiceState currentState = NagiosServiceState.UNKNOWN;
				
				try
				{
					Integer attributeValue_AsInteger = Integer.parseInt(attributeValue);
					currentState = NagiosServiceState.getEnum(attributeValue_AsInteger);
					
					serviceEntity.setCurrentState(currentState);
					checkedAttributes++;
				}
				
				catch(Exception _excpeption)
				{ 
					Log.e(AppFacade.GetTag(), String.format("Coudn't convert %s to an integer while parsing the nagios xml file", attributeValue)); 
					
					// break the for-loop because all needed attributes are dedected
					i = hostAttributesCount;
				}
				
			}
			 
			// Check if all needed attributes are parsed
			// -1 because the services of the host will be setted later
			if ( ServiceEntity.ATTRIBUTECOUNT-1>= checkedAttributes)
			{
				// break the for-loop because all needed attributes are dedected
				i = hostAttributesCount;
			}
			
		}
		
		return serviceEntity;
	}
}
