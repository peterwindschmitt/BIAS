package com.bl.bias.app;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class BIASGradeXingSpeedsConfigController 
{
	private static Boolean evaluatePassengerGradeCrossingSpeeds;
	private static Boolean evaluateThroughGradeCrossingSpeeds;
	private static Boolean evaluateLocalGradeCrossingSpeeds;
	
	@FXML private CheckBox passengerSpeedCheckBox;
	@FXML private CheckBox throughSpeedCheckBox;
	@FXML private CheckBox localSpeedCheckBox;
	
	@FXML private void initialize()
	{
		// Below is hard-coded to only compute all trains in the .TPC file for the time being
		evaluatePassengerGradeCrossingSpeeds = true;
		evaluateThroughGradeCrossingSpeeds = true;
		evaluateLocalGradeCrossingSpeeds = true;
	}
	
	public static Boolean getEvaluatePassengerSpeeds()
	{
		return evaluatePassengerGradeCrossingSpeeds;
	}
	
	public static Boolean getEvaluateThroughSpeeds()
	{
		return evaluateThroughGradeCrossingSpeeds;
	}
	
	public static Boolean getEvaluateLocalSpeeds()
	{
		return evaluateLocalGradeCrossingSpeeds;
	}
}