package com.bl.bias.objects;

import java.util.ArrayList;

public class ModifiedOtpTrainObjectB 
{
	private String performanceFileName;
	private String trainSymbol;
	private String trainType = "?";
	
	private Double otpThresholdAsDouble = 0.0;
	
	ArrayList<SchedulePointForTrainObjectB> schedulePoints = new ArrayList<SchedulePointForTrainObjectB>();
	ArrayList<ReportingPointForTrainObjectB> reportingPoints = new ArrayList<ReportingPointForTrainObjectB>();

	public ModifiedOtpTrainObjectB(String performanceFileName, String trainSymbol) 
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
	
	public void addSchedulePoint(SchedulePointForTrainObjectB point)
	{
		schedulePoints.add(point);
	}
	
	public ArrayList<SchedulePointForTrainObjectB> getSchedulePoints()
	{
		return schedulePoints;
	}
	
	public void addReportingPoint(ReportingPointForTrainObjectB point)
	{
		reportingPoints.add(point);
	}
	
	public ArrayList<ReportingPointForTrainObjectB> getReportingPoints()
	{
		return reportingPoints;
	}
}