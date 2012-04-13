package com.SysAdmin.Nagios;

public enum NagiosServiceState {
	
	OK,
	CRITICAL,
	WARNING,
	UNKNOWN;
	
	public static final int VALUE_OK       = 0;
	public static final int VALUE_WARNING  = 1;
	public static final int VALUE_CRITICAL = 2;
	public static final int VALUE_UNKOWN   = 3;
	
	public static final String STRING_OK 	   = "OK";
	public static final String STRING_WARNING  = "WARNING";
	public static final String STRING_CRITICAL = "CRITICAL";
	public static final String STRING_UNKNOWN  = "UNKNOWN";
	
	public Integer value()
	{
		switch(this)
		{
			case OK:		return VALUE_OK;
			case WARNING:	return VALUE_WARNING;
			case CRITICAL:	return VALUE_CRITICAL;
			
			case UNKNOWN:	
			default:		return VALUE_UNKOWN;
		}
	}
	
	public String string()
	{
		switch(this)
		{
			case OK:		return STRING_OK;
			case WARNING:	return STRING_WARNING;
			case CRITICAL:	return STRING_CRITICAL;
			
			case UNKNOWN:	
			default:		return STRING_UNKNOWN;
		}
	}
	
	public static NagiosServiceState getEnum(Integer _attribute)
	{
		switch(_attribute)
		{
			case VALUE_OK:			return NagiosServiceState.OK;
			case VALUE_WARNING:		return NagiosServiceState.WARNING;
			case VALUE_CRITICAL:	return NagiosServiceState.CRITICAL;
			
			case VALUE_UNKOWN:		
			default:				return NagiosServiceState.UNKNOWN;
		}
	
	}
	
}


