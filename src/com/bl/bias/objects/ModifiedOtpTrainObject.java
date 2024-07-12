package com.bl.bias.objects;

import java.util.ArrayList;

public class ModifiedOtpTrainObject 
{
	private String performanceFileName;
	private String trainSymbol;
	private String trainType = "?";
	
	private Double otpThresholdAsDouble = 0.0;
	
	ArrayList<SchedulePointForTrainObject> schedulePoints = new ArrayList<SchedulePointForTrainObject>();
	ArrayList<ReportingPointForTrainObject> reportingPoints = new ArrayList<ReportingPointForTrainObject>();

	public ModifiedOtpTrainObject(String performanceFileName, String trainSymbol) 
	{
		this.performanceFileName = performanceFileName;
		this.trainSymbol = trainSymbol;
	}

	public String getPerformanceFileName()
	{
		return performanceFileName;
	}
	
	public String getTrainSymbol()
	{
		return trainSymbol;
	}

	public void setOtpThresholdAsDouble(Double otpThresholdAsDouble)
	{
		this.otpThresholdAsDouble = otpThresholdAsDouble;
	}

	public Double getOtpThresholdAsDouble()
	{
		return otpThresholdAsDouble;
	}

	public void setTrainType(String trainType)
	{
		this.trainType = trainType;
	}

	public String getTrainType()
	{
		return trainType;
	}
	
	public void addSchedulePoint(SchedulePointForTrainObject point)
	{
		schedulePoints.add(point);
	}
	
	public ArrayList<SchedulePointForTrainObject> getSchedulePoints()
	{
		return schedulePoints;
	}
	
	public void addReportingPoint(ReportingPointForTrainObject point)
	{
		reportingPoints.add(point);
	}
	
	public ArrayList<ReportingPointForTrainObject> getReportingPoints()
	{
		return reportingPoints;
	}
}