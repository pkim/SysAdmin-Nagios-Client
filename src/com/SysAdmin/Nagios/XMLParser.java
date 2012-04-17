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
import com.SysAdmin.Nagios.Entity.HostEntity;
import com.SysAdmin.Nagios.Entity.NagiosEntity;
import com.SysAdmin.Nagios.Entity.ServiceEntity;

public class XMLParser {
	

	
	private static NagiosEntity nagiosEntity;
	
	public static NagiosEntity parse(String _xmlFile) throws Exception
	{
		nagiosEntity = new NagiosEntity();
		
		// if parameter object xmlFile is empty
		if(_xmlFile.isEmpty())
			throw new Exception("File is empty");
			
		try{
			// Document XML
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder        builder = factory.newDocumentBuilder();
			Document               doc 	   = builder.parse(new File(_xmlFile));
			
			doc.getDocumentElement().normalize();
			
			// XML Nodes
			Element  root  	   = doc.getDocumentElement();								// XML Root
			//NodeList node_root = root.getElementsByTagName(NagiosXMLNode.NODE_ROOT);	// subNodes
			NodeList nodeList_hosts    = doc.getElementsByTagName(NagiosXMLNode.NODE_HOST);
			NodeList nodeList_services = doc.getElementsByTagName(NagiosXMLNode.NODE_SERVICE);
			
			//Node node_hosts    = node_root.item(NagiosXMLNode.NODE_ID_HOSTS);
			//Node node_services = node_root.item(NagiosXMLNode.NODE_ID_SERVICES);
			
			// parse services
			nagiosEntity.setServices(parseServices(nodeList_services));
			
			// parse hosts
			nagiosEntity.setHosts   (parseHosts(nodeList_hosts));
		
		}
		
		catch(Exception e)
		{ throw new RuntimeException(e); }
		
		return nagiosEntity;
	}
	
	private static HostEntity[] parseHosts(NodeList _nodeList_Hosts)
	{
		if(_nodeList_Hosts == null)
			throw new NullPointerException("The value of _nodeList_Services has been null");
		
		Integer  hostsCount = _nodeList_Hosts.getLength();
		
		HostEntity[] hostEntities = new HostEntity[hostsCount];
		
		// get sub nodes
		for(int i=0; i < hostsCount; i++)
		{
			Node node_host  = _nodeList_Hosts.item(i);
			hostEntities[i] = parseHost(node_host);
		}
		
		return hostEntities;
	}
	
	
	private static HostEntity parseHost(Node _nodeHost)
	{
		HostEntity hostEntity = new HostEntity();
		
		NodeList hostAttributes 	 = _nodeHost.getChildNodes();
		Integer  hostAttributesCount = hostAttributes.getLength();
		
		Integer checkedAttributes = 0;
		
		for( int i=1; i < hostAttributesCount; i+=2)
		{
			Node   hostAttribute  = hostAttributes.item(i);
			String attributeName  = hostAttribute.getNodeName();
			String attributeValue = hostAttribute.getTextContent();
			
			// HOSTNAME
			if(attributeName.equalsIgnoreCase(NagiosXMLNode.NODE_HOST_ATTRIBUTE_HOSTNAME))
			{
				String hostName = hostEntity.getHostName();
				
				if(hostName != null && hostName.isEmpty())
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
			if ( HostEntity.ATTRIBUTECOUNT <= checkedAttributes)
			{
				// break the for-loop because all needed attributes are dedected
				i = hostAttributesCount;
			}
			
		}
		
		// set services of the host
		ServiceEntity[] services = nagiosEntity.getServices();
		
		for (ServiceEntity serviceEntity: services)
		{
			if(serviceEntity.getHostName().equals(hostEntity.getHostName()))
			{
				hostEntity.AddService(serviceEntity);
			}
		}
		
		return hostEntity;
	}
	
	
	private static ServiceEntity[] parseServices(NodeList _nodeList_Services)
	{
		
		if(_nodeList_Services == null)
			throw new NullPointerException("The value of _nodeList_Services has been null");
		
		Integer  servicesCount = _nodeList_Services.getLength();
		
		ServiceEntity[] serviceEntities = new ServiceEntity[servicesCount];
		
		for(int i=0; i < servicesCount; i++)
		{
			Node node_Service = _nodeList_Services.item(i);
			
			serviceEntities[i] = parseService(node_Service);
		}
		
		/*
		// get sub nodes
		for(int i=0; i < servicesCount; i++)
		{
			serviceEntities[i] = parseService(services.item(i));
		}
		*/
		return serviceEntities;
	}
	
	private static ServiceEntity parseService(Node _nodeService)
	{
		ServiceEntity serviceEntity = new ServiceEntity();
		
		NodeList serviceAttributes 	    = _nodeService.getChildNodes();
		Integer  serviceAttributesCount = serviceAttributes.getLength();
		
		Integer checkedAttributes = 0;
		
		//
		for( int i=1; i < serviceAttributesCount; i+=2)
		{
			Node   serviceAttribute  = serviceAttributes.item(i);
			String attributeName  	 = serviceAttribute.getNodeName();
			String attributeValue 	 = serviceAttribute.getTextContent();//serviceAttribute.getNodeValue();
			
			// HOSTNAME
			if(attributeName.equalsIgnoreCase(NagiosXMLNode.NODE_SERVICE_ATTRIBUTE_HOSTNAME))
			{
				String hostNameOfService = serviceEntity.getHostName();
				
				if(		hostNameOfService != null
					&&  hostNameOfService.isEmpty() 
					&& 	attributeValue != null)
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
					i = serviceAttributesCount;
				}
				
			}
			 
			// Check if all needed attributes are parsed
			// -1 because the services of the host will be setted later
			if ( ServiceEntity.ATTRIBUTECOUNT <= checkedAttributes)
			{
				// break the for-loop because all needed attributes are dedected
				i = serviceAttributesCount;
			}
			
		}
		
		return serviceEntity;
	}
}
