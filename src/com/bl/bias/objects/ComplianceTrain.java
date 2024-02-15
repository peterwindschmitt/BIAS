package com.bl.bias.objects;

import java.util.ArrayList;

public class ComplianceTrain 
{
	private String symbol;
	private String type;
	private String linkedAtOriginTo;
	private String enabled;

	private ArrayList<ComplianceTrainRouteEntry> routeEntries;
	private ArrayList<Integer> daysOfOperationAsInteger;
	
	public ComplianceTrain(String symbol, String type, String linkedAtOriginTo, String enabled, ArrayList<ComplianceTrainRouteEntry> routeEntries, ArrayList<Integer> daysOfOperationAsInteger) 
	{
		this.symbol = symbol.toUpperCase();
		this.type = type.toUpperCase();
		this.linkedAtOriginTo = linkedAtOriginTo.toUpperCase();
		this.enabled = enabled.toUpperCase();
		this.routeEntries = routeEntries;
		this.daysOfOperationAsInteger = daysOfOperationAsInteger;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public String getType() 
	{
		return type;
	}

	public String getLinkedAtOriginTo()
	{
		return linkedAtOriginTo;
	}

	public String getEnabled()
	{
		return enabled;
	}

	public ArrayList<ComplianceTrainRouteEntry> getRouteEntries()
	{
		return routeEntries;
	}

	public ArrayList<Integer> getDaysOfOperationAsInteger()
	{
		return daysOfOperationAsInteger;
	}
}