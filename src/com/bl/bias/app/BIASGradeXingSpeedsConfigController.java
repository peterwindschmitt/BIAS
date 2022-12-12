package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;

public class BIASGradeXingSpeedsConfigController 
{
	private static Preferences prefs;

	private static Integer maxTpcIncrement;
	
	private static Boolean evaluatePassengerGradeCrossingSpeeds;
	private static Boolean evaluateThroughGradeCrossingSpeeds;
	private static Boolean evaluateLocalGradeCrossingSpeeds;
	private static Boolean generateInconsistentNodeNameSheet;

	private static Boolean defaultGenerateInconsistentNodeNameSheet = true;
	
	private static String defaultMaxTpcIncrement = "50";  
	
	private static ObservableList<String> tpcIncrementValues =  FXCollections.observableArrayList("25", "50", "100", "250");

	@FXML private ComboBox<String> maxTpcIncrementComboBox;

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

		maxTpcIncrementComboBox.setItems(tpcIncrementValues);

		// Check for prefs
		prefs = Preferences.userRoot().node("BIAS");

		// See if preference is stored to generate inconsistently named nodes spreadsheet
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

		// See if preference is stored to for max TPC increment
		boolean maxTpcIncrementExists = prefs.get("gx_maxTpcIncrement", null) != null;
		
		if (maxTpcIncrementExists)
		{
			maxTpcIncrementComboBox.getSelectionModel().select(prefs.get("gx_maxTpcIncrement", defaultMaxTpcIncrement));
			maxTpcIncrement = Integer.valueOf(prefs.get("gx_maxTpcIncrement", defaultMaxTpcIncrement));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("gx_maxTpcIncrement", defaultMaxTpcIncrement);
			
			maxTpcIncrementComboBox.getSelectionModel().select(defaultMaxTpcIncrement);
			maxTpcIncrement = Integer.valueOf(defaultMaxTpcIncrement);
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
	
	public static Integer getMaxTpcIncrement()
	{
		return maxTpcIncrement;
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

	@FXML private void handleMaxTpcIncrementComboBox(ActionEvent event) 
	{
		maxTpcIncrement = Integer.valueOf(maxTpcIncrementComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("gx_maxTpcIncrement", maxTpcIncrementComboBox.getValue());
	}
}