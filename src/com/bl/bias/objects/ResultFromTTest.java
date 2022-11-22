package com.bl.bias.objects;

public class ResultFromTTest 
{
	String line;
	String typeOrGroup;
		
	Double velocityA = null;
	Double velocityB = null;
	Double absVelocityMeanDiff = null;
	Double velocityTValue = null;	
	
	Double trueDelayMinutesPer100TrainMilesA = null;
	Double trueDelayMinutesPer100TrainMilesB = null;
	Double absDelayMinutesPer100TrainMilesMeanDiff = null;
	Double delayMinutesPer100TrainMilesTValue = null;
	
	Double elapsedRunTimePerTrainAsSerialA = null;
	Double elapsedRunTimePerTrainAsSerialB = null;
	Double elapsedRunTimePerTrainAsSerialDiff = null;
	Double elapsedRunTimePerTrainAsSerialTValue = null;
	
	Integer elapsedRunTimePerTrainInSecondsA = null;
	Integer elapsedRunTimePerTrainInSecondsB = null;
	Integer elapsedRunTimePerTrainInSecondsDiff = null;
	Double elapsedRunTimePerTrainInSecondsTValue = null;
	
	String elapsedRunTimePerTrainAsStringA = null;
	String elapsedRunTimePerTrainAsStringB = null;
	String elapsedRunTimePerTrainAsStringDiff = null;
	Double elapsedRunTimePerTrainAsStringTValue = null;
	
	Double otpA = null;
	Double otpB = null;
	Double otpDiff = null;
	Double otpTValue = null;
	
	Double tCritical = null;
	
	Boolean pairedVelocitySignificantWithAlpha = null;
	Boolean pairedDelayMinutesPer100TrainMilesSignificantWithAlpha = null;
	Boolean pairedElapsedRunTimePerTrainAsSerialSignificantWithAlpha = null;
	Boolean pairedElapsedRunTimePerTrainInSecondsSignificantWithAlpha = null;
	Boolean pairedElapsedRunTimePerTrainAsStringSignificantWithAlpha = null;
	Boolean pairedOtpSignificantWithAlpha = null;
	
	public ResultFromTTest(String line, String typeOrGroup)
	{
		setLine(line);
		setTypeOrGroup(typeOrGroup);
	}
	
	private void setLine(String line)
	{
		this.line = line;
	}
	
	private void setTypeOrGroup(String typeOrGroup)
	{
		this.typeOrGroup = typeOrGroup;
	}
	
	public void setVelocityA(Double velocityA)
	{
		this.velocityA = velocityA;
	}
	
	public void setVelocityB(Double velocityB)
	{
		this.velocityB = velocityB;
	}
	
	public void setAbsMeanVelocityDiff(Double absVelocityMeanDiff)
	{
		this.absVelocityMeanDiff = absVelocityMeanDiff;
	}
	
	public void setVelocityTValue(Double velocityTValue)
	{
		this.velocityTValue = velocityTValue;
	}
	
	public void setVelocitySignificant(boolean pairedVelocitySignificantWithAlpha) 
	{
		this.pairedVelocitySignificantWithAlpha = pairedVelocitySignificantWithAlpha;
	}
		
	public void setDelayMinutesPer100TrainMilesA(Double trueDelayMinutesPer100TrainMilesA)
	{
		this.trueDelayMinutesPer100TrainMilesA = trueDelayMinutesPer100TrainMilesA;
	}
	
	public void setDelayMinutesPer100TrainMilesB(Double trueDelayMinutesPer100TrainMilesB)
	{
		this.trueDelayMinutesPer100TrainMilesB = trueDelayMinutesPer100TrainMilesB;
	}
	
	public void setAbsDelayMinutesPer100TrainMilesMeanDiff(Double absDelayMinutesPer100TrainMilesMeanDiff)
	{
		this.absDelayMinutesPer100TrainMilesMeanDiff = absDelayMinutesPer100TrainMilesMeanDiff;
	}
	
	public void setDelayMinutesPer100TrainMilesTValue(Double delayMinutesPer100TrainMilesTValue)
	{
		this.delayMinutesPer100TrainMilesTValue = delayMinutesPer100TrainMilesTValue;
	}
	
	public void setDelayMinutesPer100TrainMilesSignificant(boolean pairedDelayMinutesPer100TrainMilesSignificantWithAlpha) 
	{
		this. pairedDelayMinutesPer100TrainMilesSignificantWithAlpha =  pairedDelayMinutesPer100TrainMilesSignificantWithAlpha;
	}

	public void setElapsedRunTimePerTrainAsSerialA(Double elapsedRunTimePerTrainAsSerialA)
	{
		this.elapsedRunTimePerTrainAsSerialA = elapsedRunTimePerTrainAsSerialA;
	}
	
	public void setElapsedRunTimePerTrainAsSerialB(Double elapsedRunTimePerTrainAsSerialB)
	{
		this.elapsedRunTimePerTrainAsSerialB = elapsedRunTimePerTrainAsSerialB;
	}
	
	public void setElapsedRunTimePerTrainAsSerialDiff(Double elapsedRunTimePerTrainAsSerialDiff)
	{
		this.elapsedRunTimePerTrainAsSerialDiff = elapsedRunTimePerTrainAsSerialDiff;
	}
	
	public void setElapsedRunTimeAsSerialTValue(Double elapsedRunTimePerTrainAsSerialTValue)
	{
		this.elapsedRunTimePerTrainAsSerialTValue = elapsedRunTimePerTrainAsSerialTValue;
	}
		
	public void setElapsedRunTimeAsSerialSignificant(Boolean pairedElapsedRunTimePerTrainAsSerialSignificantWithAlpha)
	{
		this.pairedElapsedRunTimePerTrainAsSerialSignificantWithAlpha = pairedElapsedRunTimePerTrainAsSerialSignificantWithAlpha;
	}
		
	public void setElapsedRunTimePerTrainInSecondsA(Double elapsedRunTimePerTrainInSecondsA)
	{
		this.elapsedRunTimePerTrainInSecondsA = elapsedRunTimePerTrainInSecondsA.intValue();
	}
	
	public void setElapsedRunTimePerTrainInSecondsB(Double elapsedRunTimePerTrainInSecondsB)
	{
		this.elapsedRunTimePerTrainInSecondsB = elapsedRunTimePerTrainInSecondsB.intValue();
	}
	
	public void setElapsedRunTimePerTrainInSecondsDiff(Double elapsedRunTimePerTrainInSecondsDiff)
	{
		this.elapsedRunTimePerTrainInSecondsDiff = elapsedRunTimePerTrainInSecondsDiff.intValue();
	}
	
	public void setElapsedRunTimeInSecondsTValue(Double elapsedRunTimePerTrainInSecondsTValue)
	{
		this.elapsedRunTimePerTrainInSecondsTValue = elapsedRunTimePerTrainInSecondsTValue;
	}
		
	public void setElapsedRunTimeInSecondsSignificant(Boolean pairedElapsedRunTimePerTrainInSecondsSignificantWithAlpha)
	{
		this.pairedElapsedRunTimePerTrainInSecondsSignificantWithAlpha = pairedElapsedRunTimePerTrainInSecondsSignificantWithAlpha;
	}	
	
	public void setElapsedRunTimePerTrainAsStringA(String elapsedRunTimePerTrainAsStringA)
	{
		this.elapsedRunTimePerTrainAsStringA = elapsedRunTimePerTrainAsStringA;
	}
	
	public void setElapsedRunTimePerTrainAsStringB(String elapsedRunTimePerTrainAsStringB)
	{
		this.elapsedRunTimePerTrainAsStringB = elapsedRunTimePerTrainAsStringB;
	}
	
	public void setElapsedRunTimePerTrainAsStringDiff(String elapsedRunTimePerTrainAsStringDiff)
	{
		this.elapsedRunTimePerTrainAsStringDiff = elapsedRunTimePerTrainAsStringDiff;
	}
	
	public void setElapsedRunTimeAsStringTValue(Double elapsedRunTimePerTrainAsStringTValue)
	{
		this.elapsedRunTimePerTrainAsStringTValue = elapsedRunTimePerTrainAsStringTValue;
	}
		
	public void setElapsedRunTimeAsStringSignificant(Boolean pairedElapsedRunTimePerTrainAsStringSignificantWithAlpha)
	{
		this.pairedElapsedRunTimePerTrainAsStringSignificantWithAlpha = pairedElapsedRunTimePerTrainAsStringSignificantWithAlpha;
	}	
	
	public void setOtpA(Double otpA)
	{
		this.otpA = otpA;
	}
	
	public void setOtpB(Double otpB)
	{
		this.otpB = otpB;
	}
	
	public void setOtpDiff(Double otpDiff)
	{
		this.otpDiff = otpDiff;
	}
	
	public void setOtpTValue(Double otpTValue)
	{
		this.otpTValue = otpTValue;
	}
		
	public void setOtpSignificant(Boolean pairedOtpSignificantWithAlpha)
	{
		this.pairedOtpSignificantWithAlpha = pairedOtpSignificantWithAlpha;
	}	
		
	public void setTCritical(Double tCritical) 
	{
		this.tCritical = tCritical;
	}
	
	public String getLine()
	{
		return line;
	}
	
	public String getTypeOrGroup()
	{
		return typeOrGroup;
	}
	
	public Double getVelocityA()
	{
		return velocityA;
	}
	
	public Double getVelocityB()
	{
		return velocityB;
	}
	
	public Double getAbsMeanVelocityDiff()
	{
		return absVelocityMeanDiff;
	}
	
	public Double getVelocityTValue()
	{	
		return velocityTValue;
	}
	
	public Double getMeanDelayMinutesPer100TrainMilesMeanDiff()
	{
		return absDelayMinutesPer100TrainMilesMeanDiff;
	}
	
	public Double getElapsedRunTimePerTrainAsSerialA()
	{
		return elapsedRunTimePerTrainAsSerialA;
	}
	
	public Double getElapsedRunTimePerTrainAsSerialB()
	{
		return elapsedRunTimePerTrainAsSerialB;
	}
	
	public Double getElapsedRunTimePerTrainAsSerialDiff()
	{
		return elapsedRunTimePerTrainAsSerialDiff;
	}
	
	public Double getElapsedRunTimePerTrainAsSerialTValue()
	{	
		return elapsedRunTimePerTrainAsSerialTValue;
	}
	
	public Integer getElapsedRunTimePerTrainInSecondsA()
	{
		return elapsedRunTimePerTrainInSecondsA;
	}
	
	public Integer getElapsedRunTimePerTrainInSecondsB()
	{
		return elapsedRunTimePerTrainInSecondsB;
	}
	
	public Integer getElapsedRunTimePerTrainInSecondsDiff()
	{
		return elapsedRunTimePerTrainInSecondsDiff;
	}
	
	public Double getElapsedRunTimePerTrainInSecondsTValue()
	{	
		return elapsedRunTimePerTrainInSecondsTValue;
	}
	
	public String getElapsedRunTimePerTrainAsStringA()
	{
		return elapsedRunTimePerTrainAsStringA;
	}
	
	public String getElapsedRunTimePerTrainAsStringB()
	{
		return elapsedRunTimePerTrainAsStringB;
	}
	
	public String getElapsedRunTimePerTrainAsStringDiff()
	{
		return elapsedRunTimePerTrainAsStringDiff;
	}
	
	public Double getElapsedRunTimePerTrainAsStringTValue()
	{	
		return elapsedRunTimePerTrainAsStringTValue;
	}
		
	public Double getOtpA()
	{
		return otpA;
	}
	
	public Double getOtpB()
	{
		return otpB;
	}
	
	public Double getOtpDiff()
	{
		return otpDiff;
	}
	
	public Double getOtpTValue()
	{
		return otpTValue;
	}
	
	public Double getTCritical() 
	{
		return tCritical;
	}

	public boolean getVelocitySignificant()
	{
		return pairedVelocitySignificantWithAlpha;		
	}
	
	public Double getDelayMinutesPer100TrainMilesA()
	{
		return trueDelayMinutesPer100TrainMilesA;
	}
	
	public Double getDelayMinutesPer100TrainMilesB()
	{
		return trueDelayMinutesPer100TrainMilesB;
	}
	
	public Double getDelayMinutesPer100TrainMilesTValue()
	{
		return delayMinutesPer100TrainMilesTValue;
	}
	
	public boolean getDelayMinutesPer100TrainMilesSignificant() 
	{
		return pairedDelayMinutesPer100TrainMilesSignificantWithAlpha;
	}
	
	public boolean getElapsedRunTimePerTrainAsSerialSignificant() 
	{
		return pairedElapsedRunTimePerTrainAsSerialSignificantWithAlpha;
	}
	
	public boolean getElapsedRunTimePerTrainInSecondsSignificant() 
	{
		return pairedElapsedRunTimePerTrainInSecondsSignificantWithAlpha;
	}
	
	public boolean getElapsedRunTimePerTrainAsStringSignificant() 
	{
		return pairedElapsedRunTimePerTrainAsStringSignificantWithAlpha;
	}
	
	public boolean getOtpSignificant() 
	{
		return pairedOtpSignificantWithAlpha;
	}
}