package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

public class BIASGradeXingSpeedsConfigController 
{
	private static Preferences prefs;

	private static Boolean evaluatePassengerGradeCrossingSpeeds;
	private static Boolean evaluateThroughGradeCrossingSpeeds;
	private static Boolean evaluateLocalGradeCrossingSpeeds;
	private static Boolean generateInconsistentNodeNameSheet;
	
	private static Boolean defaultGenerateInconsistentNodeNameSheet = true;
	
	
	@FXML private CheckBox passengerSpeedCheckBox;
	@FXML private CheckBox throughSpeedCheckBox;
	@FXML private CheckBox localSpeedCheckBox;
	
	@FXML private RadioButton generateInconsistentNodeNameSheetTrueRadioButton;
	@FXML private RadioButton generateInconsistentNodeNameSheetFalseRadioButton;
	
	@FXML private void initialize()
	{
		// Below is hard-coded to only compute all trains in the .TPC file for the time being
		evaluatePassengerGradeCrossingSpeeds = true;
		evaluateThroughGradeCrossingSpeeds = true;
		evaluateLocalGradeCrossingSpeeds = true;
		
		// Check for 
		prefs = Preferences.userRoot().node("BIAS");

		// See if preference is stored generate inconsistently named nodes spreadsheet
		if (prefs.getBoolean("gx_generateInconsistentNodeNameSheet", defaultGenerateInconsistentNodeNameSheet))
		{
			generateInconsistentNodeNameSheet = true;
			
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("gx_generateInconsistentNodeNameSheet", true);
			}
			
			generateInconsistentNodeNameSheetTrueRadioButton.setSelected(true);
		}
		else
		{
			generateInconsistentNodeNameSheet = false;
			generateInconsistentNodeNameSheetFalseRadioButton.setSelected(true);
		}
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
	
	public static Boolean getGenerateInconsisteneNodeNameSheet()
	{
		return generateInconsistentNodeNameSheet;
	}
	
	@FXML private void handleGenerateInconsistentNodeNameSheetTrueRadioButton(ActionEvent event) 
	{
		generateInconsistentNodeNameSheet = true;
		
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gx_generateInconsistentNodeNameSheet", true);
	}
	
	@FXML private void handleGenerateInconsistentNodeNameSheetFalseRadioButton(ActionEvent event) 
	{
		generateInconsistentNodeNameSheet = false;
		
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putBoolean("gx_generateInconsistentNodeNameSheet", false);
	}
}