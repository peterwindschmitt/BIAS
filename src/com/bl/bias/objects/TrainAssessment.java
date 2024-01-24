package com.bl.bias.objects;

import java.util.ArrayList;

public class TrainAssessment
{
	private String trainSymbol;
	private String trainGroupAbbreviation;
	private String trainType;
	private ArrayList<RouteEntry> routeEntries = new ArrayList<RouteEntry>();
	private ArrayList<RecoveryRateAssessment> recoveryRateAssesments = new ArrayList<RecoveryRateAssessment>();
	private Boolean trainHasRecoveryRatesCalculated;
				
	public TrainAssessment(String trainSymbol, String trainGroupAbbreviation, String trainType, ArrayList<RouteEntry> routeEntries)
	{
		this.trainSymbol = trainSymbol;
		this.trainGroupAbbreviation = trainGroupAbbreviation;
		this.trainType = trainType;
		this.routeEntries = routeEntries;
		
		trainHasRecoveryRatesCalculated = false;
	}
	
	public String getTrainSymbol() 
	{
		return trainSymbol;
	}

	public String getTrainGroupAbbreviation() 
	{
		return trainGroupAbbreviation;
	}
	
	public String getTrainType() 
	{
		return trainType;
	}
	
	public ArrayList<RouteEntry> getRouteEntries() 
	{
		return routeEntries;
	}
	
	public ArrayList<RecoveryRateAssessment> getRecoveryRateAssessments() 
	{
		return recoveryRateAssesments;
	}
	
	public void setRecoveryRateAssesment(RecoveryRateAssessment newAssessment) 
	{
		recoveryRateAssesments.add(newAssessment);
	}
	
	public void setTrainHasRecoveryRatesCalculated() 
	{
		trainHasRecoveryRatesCalculated = true;;
	}
	
	public Boolean getTrainHasRecoveryRatesCalculated() 
	{
		return trainHasRecoveryRatesCalculated;
	}
}