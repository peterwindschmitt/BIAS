package com.bl.bias.objects;

public class MaintenanceWindowAnalysisWindow 
{
	// A window is defined as the time period that exists between two occupancies (after considering blockMustRemainOccupiedBeforeWindow time, blockMustEndInAdvanceOfTrain time, increment and decrement times) 
	// on the same segment of track (aka sub-line).  Occupancy start/end times are preserved primarily for usage in combining potential windows among multiple sublines on onto fewer sublines. 
	private Integer occupancyStartTimeInSeconds;
	private Integer occupancyEndTimeInSeconds;
	private Integer subLineWindowStartTimeInSeconds;
	private Integer subLineWindowFinishTimeInSeconds;
	
	private String subLineId;

	public MaintenanceWindowAnalysisWindow(Integer occupancyStartTimeInSeconds, Integer occupancyEndTimeInSeconds, Integer subLineWindowStartTimeInSeconds, Integer subLineWindowFinishTimeInSeconds, String subLineId) 
	{
		this.occupancyStartTimeInSeconds = occupancyStartTimeInSeconds;
		this.occupancyEndTimeInSeconds = occupancyEndTimeInSeconds;
		this.subLineWindowStartTimeInSeconds = subLineWindowStartTimeInSeconds;
		this.subLineWindowFinishTimeInSeconds = subLineWindowFinishTimeInSeconds;
		this.subLineId = subLineId;
	}

	public Integer getOccupancyStartTimeInSeconds()
	{
		return occupancyStartTimeInSeconds;
	}

	public Integer getOccupancyEndTimeInSeconds() 
	{
		return occupancyEndTimeInSeconds;
	}
	
	public Integer getSubLineWindowStartTimeInSeconds ()
	{
		return subLineWindowStartTimeInSeconds;
	}
	
	public Integer getSubLineWindowFinishTimeInSeconds ()
	{
		return subLineWindowFinishTimeInSeconds;
	}
	
	public String getSubLineId() 
	{
		return subLineId;
	}
	
	public void setSubLineId(String subLineId) 
	{
		this.subLineId = subLineId;
	}
}