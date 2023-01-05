package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.beans.binding.BooleanBinding;
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
	private static Integer minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds;

	private static Boolean evaluatePassengerGradeCrossingSpeeds;
	private static Boolean evaluateThroughGradeCrossingSpeeds;
	private static Boolean evaluateLocalGradeCrossingSpeeds;
	private static Boolean generateInconsistentNodeNameSheet;
	private static Boolean generateInconsistentMaxSpeedSheet;

	private static Boolean defaultEvaluatePassengerGradeCrossingSpeeds = true;
	private static Boolean defaultEvaluateThroughGradeCrossingSpeeds = true;
	private static Boolean defaultEvaluateLocalGradeCrossingSpeeds = true;
	private static Boolean defaultGenerateInconsistentNodeNameSheet = true;
	private static Boolean defaultGenerateInconsistentMaxSpeedSheet = true;

	private static BooleanBinding disableEvaluatePassengerGradeCrossingSpeeds;
	private static BooleanBinding disableEvaluateThroughGradeCrossingSpeeds;
	private static BooleanBinding disableEvaluateLocalGradeCrossingSpeeds;

	private static String defaultMaxTpcIncrement = "50";  
	private static String defaultMinDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds = "0";

	private static ObservableList<String> tpcIncrementValues =  FXCollections.observableArrayList("25", "50", "100", "250");
	private static ObservableList<String> minDiffMaxDesignVsMinAnticipatedSpeedValues =  FXCollections.observableArrayList("0", "3", "5", "7", "10");

	@FXML private ComboBox<String> maxTpcIncrementComboBox;
	@FXML private ComboBox<String> minDiffMaxDesignVsMinAnticipatedSpeedComboBox;

	@FXML private CheckBox passengerSpeedCheckBox;
	@FXML private CheckBox throughSpeedCheckBox;
	@FXML private CheckBox localSpeedCheckBox;

	@FXML private RadioButton generateInconsistentNodeNameSheetTrueRadioButton;
	@FXML private RadioButton generateInconsistentNodeNameSheetFalseRadioButton;
	@FXML private RadioButton generateInconsistentMaxSpeedSheetTrueRadioButton;
	@FXML private RadioButton generateInconsistentMaxSpeedSheetFalseRadioButton;

	@FXML private void initialize()
	{
		maxTpcIncrementComboBox.setItems(tpcIncrementValues);
		minDiffMaxDesignVsMinAnticipatedSpeedComboBox.setItems(minDiffMaxDesignVsMinAnticipatedSpeedValues);

		// Check for prefs
		prefs = Preferences.userRoot().node("BIAS");

		// See if preference is stored to evaluate Passenger Group crossing speeds
		if (prefs.getBoolean("gx_evaluatePassengerGradeCrossingSpeeds", defaultEvaluatePassengerGradeCrossingSpeeds))
		{
			evaluatePassengerGradeCrossingSpeeds = true;

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("gx_evaluatePassengerGradeCrossingSpeeds", true);
			}

			passengerSpeedCheckBox.setSelected(true);
		}
		else
		{
			evaluatePassengerGradeCrossingSpeeds = false;
			passengerSpeedCheckBox.setSelected(false);
		}

		// See if preference is stored to evaluate Through Group crossing speeds
		if (prefs.getBoolean("gx_evaluateThroughGradeCrossingSpeeds", defaultEvaluateThroughGradeCrossingSpeeds))
		{
			evaluateThroughGradeCrossingSpeeds = true;

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("gx_evaluateThroughGradeCrossingSpeeds", true);
			}

			throughSpeedCheckBox.setSelected(true);
		}
		else
		{
			evaluateThroughGradeCrossingSpeeds = false;
			throughSpeedCheckBox.setSelected(false);
		}

		// See if preference is stored to evaluate Local Group crossing speeds
		if (prefs.getBoolean("gx_evaluateLocalGradeCrossingSpeeds", defaultEvaluateLocalGradeCrossingSpeeds))
		{
			evaluateLocalGradeCrossingSpeeds = true;

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("gx_evaluateLocalGradeCrossingSpeeds", true);
			}

			localSpeedCheckBox.setSelected(true);
		}
		else
		{
			evaluateLocalGradeCrossingSpeeds = false;
			localSpeedCheckBox.setSelected(false);
		}

		// See if preference is stored to generate inconsistently named nodes sheet
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

		// See if preference is stored to generate inconsistent max speed (by direction) sheet
		if (prefs.getBoolean("gx_generateInconsistentMaxSpeedSheet", defaultGenerateInconsistentMaxSpeedSheet))
		{
			generateInconsistentMaxSpeedSheet = true;

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("gx_generateInconsistentMaxSpeedSheet", true);
			}

			generateInconsistentMaxSpeedSheetTrueRadioButton.setSelected(true);
		}
		else
		{
			generateInconsistentMaxSpeedSheet = false;
			generateInconsistentMaxSpeedSheetFalseRadioButton.setSelected(true);
		}

		// See if preference is stored for max TPC increment
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

		// See if preference is stored for min difference between max design speed and min anticipated speed
		boolean minDifferenceBetweenMaxDesignAndMinAnticipatedSpeedsExists = prefs.get("gx_minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds", null) != null;

		if (minDifferenceBetweenMaxDesignAndMinAnticipatedSpeedsExists)
		{
			minDiffMaxDesignVsMinAnticipatedSpeedComboBox.getSelectionModel().select(prefs.get("gx_minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds", defaultMinDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds));
			minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds = Integer.valueOf(prefs.get("gx_minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds", defaultMinDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("gx_minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds", defaultMinDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds);

			minDiffMaxDesignVsMinAnticipatedSpeedComboBox.getSelectionModel().select(defaultMinDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds);
			minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds = Integer.valueOf(defaultMinDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds);
		}

		// Set up Boolean Bindings
		disableEvaluatePassengerGradeCrossingSpeeds = throughSpeedCheckBox.selectedProperty().not().and(localSpeedCheckBox.selectedProperty().not());
		passengerSpeedCheckBox.disableProperty().bind(disableEvaluatePassengerGradeCrossingSpeeds);

		disableEvaluateThroughGradeCrossingSpeeds = passengerSpeedCheckBox.selectedProperty().not().and(localSpeedCheckBox.selectedProperty().not());
		throughSpeedCheckBox.disableProperty().bind(disableEvaluateThroughGradeCrossingSpeeds);

		disableEvaluateLocalGradeCrossingSpeeds = throughSpeedCheckBox.selectedProperty().not().and(passengerSpeedCheckBox.selectedProperty().not());
		localSpeedCheckBox.disableProperty().bind(disableEvaluateLocalGradeCrossingSpeeds);
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

	public static Boolean getGenerateInconsisteneMaxSpeedSheet()
	{
		return generateInconsistentMaxSpeedSheet;
	}

	public static Integer getMaxTpcIncrement()
	{
		return maxTpcIncrement;
	}
	
	public static Integer getMinDiffMaxDesignVsMinAnticipatedSpeed()
	{
		return minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds;
	}

	@FXML private void handlePassengerSpeedCheckBox(ActionEvent event) 
	{
		if (evaluatePassengerGradeCrossingSpeeds)
		{
			evaluatePassengerGradeCrossingSpeeds = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gx_evaluatePassengerGradeCrossingSpeeds", false);
		}
		else
		{
			evaluatePassengerGradeCrossingSpeeds = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gx_evaluatePassengerGradeCrossingSpeeds", true);
		}
	}

	@FXML private void handleThroughSpeedCheckBox(ActionEvent event) 
	{
		if (evaluateThroughGradeCrossingSpeeds)
		{
			evaluateThroughGradeCrossingSpeeds = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gx_evaluateThroughGradeCrossingSpeeds", false);
		}
		else
		{
			evaluateThroughGradeCrossingSpeeds = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gx_evaluateThroughGradeCrossingSpeeds", true);
		}
	}

	@FXML private void handleLocalSpeedCheckBox(ActionEvent event) 
	{
		if (evaluateLocalGradeCrossingSpeeds)
		{
			evaluateLocalGradeCrossingSpeeds = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gx_evaluateLocalGradeCrossingSpeeds", false);
		}
		else
		{
			evaluateLocalGradeCrossingSpeeds = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gx_evaluateLocalGradeCrossingSpeeds", true);
		}
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

	@FXML private void handleGenerateInconsistentMaxSpeedSheetTrueRadioButton(ActionEvent event) 
	{
		generateInconsistentMaxSpeedSheet = true;

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putBoolean("gx_generateInconsistentMaxSpeedSheet", true);
	}

	@FXML private void handleGenerateInconsistentMaxSpeedSheetFalseRadioButton(ActionEvent event) 
	{
		generateInconsistentMaxSpeedSheet = false;

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putBoolean("gx_generateInconsistentMaxSpeedSheet", false);
	}

	@FXML private void handleMaxTpcIncrementComboBox(ActionEvent event) 
	{
		maxTpcIncrement = Integer.valueOf(maxTpcIncrementComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("gx_maxTpcIncrement", maxTpcIncrementComboBox.getValue());
	}

	@FXML private void handleMinDiffMaxDesignVsMinAnticipatedSpeedComboBox(ActionEvent event) 
	{
		minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds = Integer.valueOf(minDiffMaxDesignVsMinAnticipatedSpeedComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("gx_minDifferenceBetweenMaxDesignAndMinAnticipatedSpeeds", minDiffMaxDesignVsMinAnticipatedSpeedComboBox.getValue());
	}
}