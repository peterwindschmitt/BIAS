package com.bl.bias.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.util.prefs.Preferences;

public class BIASBridgeClosureAnalysisConfigPageController 
{
	private static Integer lowerMinutes;  
	private static Integer raiseMinutes;  
	private static Integer minimumUpTimeMinutes;  
	private static Integer signalPreferredMinutesInAdvanceOfTrain;

	private static String recurringMarineAccessPeriodStartMinute;
	private static String recurringMarineAccessPeriodStartHour;
	private static String recurringMarineAccessPeriodEndMinute;
	private static String recurringMarineAccessPeriodEndHour;
	private static String incrementCrossing;
	private static String incrementClosure;
	private static String incrementHourlyBucket;
	private static String resultsExclusionPeriod;

	private static String defaultLowerMinutes = "2";  
	private static String defaultRaiseMinutes = "2";  
	private static String defaultMinimumUpTimeMinutes = "5";
	private static String defaultSignalPreferredMinutesInAdvanceOfTrain = "5";
	private static String defaultMarineAcessPeriodStartMinute = ":00";
	private static String defaultMarineAcessPeriodStartHour = "00:00";
	private static String defaultMarineAcessPeriodEndMinute = ":10";
	private static String defaultMarineAcessPeriodEndHour = "23:00";
	private static String defaultIncrementCrossing = "None";
	private static String defaultIncrementClosure = "None";
	private static String defaultIncrementHourlyBucket = "None";
	private static String defaultResultsExclusionPeriod = "1 day";

	private static Boolean includeBridgeLowerTimeInSplits;
	private static Boolean includeBridgeRaiseTimeInSplits;
	private static Boolean recurringMarineAccessPeriodActive;
	private static Boolean computeMarineHighUsagePeriodActive;

	private static Boolean defaultIncludeBridgeLowerTimeInSplits = true;
	private static Boolean defaultIncludeBridgeRaiseTimeInSplits = true;
	private static Boolean defaultRecurringMarineAccessPeriodActive = false;
	private static Boolean defaultComputeMarineHighUsagePeriodActive = false;

	private static ObservableList<String> raiseLowerMinuteValues =  FXCollections.observableArrayList("1", "2", "3", "4", "5");
	private static ObservableList<String> signalPreferredMinutesInAdvanceOfTrainValues =  FXCollections.observableArrayList("0", "5", "6", "7", "8", "9", "10", "11", "12", "13");
	private static ObservableList<String> minimumUpTimeMinuteValues =  FXCollections.observableArrayList("3", "4", "5", "10", "15", "20");
	private static ObservableList<String> marineAccessPeriodStartMinuteValues =  FXCollections.observableArrayList(":00", ":05", ":10", ":15", ":20", ":25", ":30", ":35", ":40", ":45", ":50", ":55");
	private static ObservableList<String> marineAccessPeriodEndMinuteValues =  FXCollections.observableArrayList(":00", ":05", ":10", ":15", ":20", ":25", ":30", ":35", ":40", ":45", ":50", ":55");
	private static ObservableList<String> marineAccessPeriodStartHourValues =  FXCollections.observableArrayList("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
			"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00");
	private static ObservableList<String> marineAccessPeriodEndHourValues =  FXCollections.observableArrayList("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
			"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00");
	private static ObservableList<String> incrementCrossingValues =  FXCollections.observableArrayList("None", "1 min", "5 min");
	private static ObservableList<String> incrementClosureValues =  FXCollections.observableArrayList("None", "1 min", "5 min");
	private static ObservableList<String> incrementHourlyBucketValues =  FXCollections.observableArrayList("None", "1 min", "5 min");
	private static ObservableList<String> exclusionPeriodValues =  FXCollections.observableArrayList("None", "1 day");

	private static Preferences prefs;

	@FXML private ComboBox<String> bridgeLowerCombobox;
	@FXML private ComboBox<String> signalPreferredMinutesInAdvanceOfTrainCombobox;
	@FXML private ComboBox<String> bridgeRaiseCombobox;
	@FXML private ComboBox<String> minimumUpTimeCombobox;
	@FXML private ComboBox<String> recurringMarineAccessPeriodStartMinuteCombobox;
	@FXML private ComboBox<String> recurringMarineAccessPeriodEndMinuteCombobox;
	@FXML private ComboBox<String> recurringMarineAccessPeriodStartHourCombobox;
	@FXML private ComboBox<String> recurringMarineAccessPeriodEndHourCombobox;
	@FXML private ComboBox<String> incrementCrossingCombobox;
	@FXML private ComboBox<String> incrementClosureCombobox;
	@FXML private ComboBox<String> incrementHourlyBucketCombobox;
	@FXML private ComboBox<String> exclusionPeriodCombobox;

	@FXML private RadioButton recurringMarineAccessPeriodFalseRadioButton;
	@FXML private RadioButton recurringMarineAccessPeriodTrueRadioButton;

	@FXML private Label recurringMarineAccessPeriodDurationLabel;

	@FXML private CheckBox computeMarineHighUsagePeriodCheckBox;
	@FXML private CheckBox bridgeLowerTimeIncludedInSplitCheckBox;
	@FXML private CheckBox bridgeRaiseTimeIncludedInSplitCheckBox;
	
	@FXML private void initialize() 
	{
		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");

		// See if bridge lower time value is stored
		bridgeLowerCombobox.setItems(raiseLowerMinuteValues);

		boolean lowerBridgeMinutesExists = prefs.get("bc_bridgeLowerMinutes", null) != null;
		if (lowerBridgeMinutesExists)
		{
			bridgeLowerCombobox.getSelectionModel().select(prefs.get("bc_bridgeLowerMinutes", defaultLowerMinutes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_bridgeLowerMinutes", defaultLowerMinutes);
			bridgeLowerCombobox.getSelectionModel().select(getDefaultLowerMinutes());
		}
		lowerMinutes = Integer.valueOf(prefs.get("bc_bridgeLowerMinutes", defaultLowerMinutes));

		// See if including bridge lowering time in splits is stored
		if (prefs.getBoolean("bc_includeBridgeLowerTimeInSplits", defaultIncludeBridgeLowerTimeInSplits))
		{
			includeBridgeLowerTimeInSplits = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_includeBridgeLowerTimeInSplits", true);
			bridgeLowerTimeIncludedInSplitCheckBox.setSelected(true);
		}
		else
		{
			includeBridgeLowerTimeInSplits = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_includeBridgeLowerTimeInSplits", false);
			bridgeLowerTimeIncludedInSplitCheckBox.setSelected(false);
		}

		// See if bridge raise time value is stored
		bridgeRaiseCombobox.setItems(raiseLowerMinuteValues);

		boolean raiseBridgeMinutesExists = prefs.get("bc_bridgeRaiseMinutes", null) != null;
		if (raiseBridgeMinutesExists)
		{
			bridgeRaiseCombobox.getSelectionModel().select(prefs.get("bc_bridgeRaiseMinutes", defaultRaiseMinutes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_bridgeRaiseMinutes", defaultRaiseMinutes);
			bridgeRaiseCombobox.getSelectionModel().select(getDefaultRaiseMinutes());
		}
		raiseMinutes = Integer.valueOf(prefs.get("bc_bridgeRaiseMinutes", defaultRaiseMinutes));

		// See if including bridge raising time in splits is stored
		if (prefs.getBoolean("bc_includeBridgeRaiseTimeInSplits", defaultIncludeBridgeRaiseTimeInSplits))
		{
			includeBridgeRaiseTimeInSplits = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_includeBridgeRaiseTimeInSplits", true);
			bridgeRaiseTimeIncludedInSplitCheckBox.setSelected(true);
		}
		else
		{
			includeBridgeRaiseTimeInSplits = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_includeBridgeRaiseTimeInSplits", false);
			bridgeRaiseTimeIncludedInSplitCheckBox.setSelected(false);
		}

		// See if preferred minutes in front of train (for best signal) time value is stored
		signalPreferredMinutesInAdvanceOfTrainCombobox.setItems(signalPreferredMinutesInAdvanceOfTrainValues);

		boolean minimumMinutesInAdvanceOfTrainExists = prefs.get("bc_signalPreferredMinutesInAdvanceOfTrain", null) != null;
		if (minimumMinutesInAdvanceOfTrainExists)
		{
			signalPreferredMinutesInAdvanceOfTrainCombobox.getSelectionModel().select(prefs.get("bc_signalPreferredMinutesInAdvanceOfTrain", defaultSignalPreferredMinutesInAdvanceOfTrain));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_signalPreferredMinutesInAdvanceOfTrain", defaultSignalPreferredMinutesInAdvanceOfTrain);
			signalPreferredMinutesInAdvanceOfTrainCombobox.getSelectionModel().select(getDefaultMinimumMinutesInAdvanceOfTrainValue());
		}
		signalPreferredMinutesInAdvanceOfTrain = Integer.valueOf(prefs.get("bc_signalPreferredMinutesInAdvanceOfTrain", defaultSignalPreferredMinutesInAdvanceOfTrain));

		// See if bridge minimum up time value is stored
		minimumUpTimeCombobox.setItems(minimumUpTimeMinuteValues);

		boolean minimumUpTimeMinutesExists = prefs.get("bc_minimumUpTimeMinutes", null) != null;
		if (minimumUpTimeMinutesExists)
		{
			minimumUpTimeCombobox.getSelectionModel().select(prefs.get("bc_minimumUpTimeMinutes", defaultMinimumUpTimeMinutes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_minimumUpTimeMinutes", defaultMinimumUpTimeMinutes);
			minimumUpTimeCombobox.getSelectionModel().select(getDefaultMinimumUpTimeMinutes());
		}
		minimumUpTimeMinutes = Integer.valueOf(prefs.get("bc_minimumUpTimeMinutes", defaultMinimumUpTimeMinutes));

		// See if preference is stored for recurring marine access periods
		if (prefs.getBoolean("bc_recurringMarineAccessPeriodActive", defaultRecurringMarineAccessPeriodActive))
		{
			recurringMarineAccessPeriodActive = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_recurringMarineAccessPeriodActive", true);
			recurringMarineAccessPeriodTrueRadioButton.setSelected(true);
			recurringMarineAccessPeriodStartMinuteCombobox.setDisable(false);
			recurringMarineAccessPeriodEndMinuteCombobox.setDisable(false);
			recurringMarineAccessPeriodStartHourCombobox.setDisable(false);
			recurringMarineAccessPeriodEndHourCombobox.setDisable(false);

			computeMarineHighUsagePeriodCheckBox.setDisable(false);
		}
		else
		{
			recurringMarineAccessPeriodActive = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_recurringMarineAccessPeriodActive", false);
			recurringMarineAccessPeriodFalseRadioButton.setSelected(true);
			recurringMarineAccessPeriodStartMinuteCombobox.setDisable(true);
			recurringMarineAccessPeriodEndMinuteCombobox.setDisable(true);
			recurringMarineAccessPeriodStartHourCombobox.setDisable(true);
			recurringMarineAccessPeriodEndHourCombobox.setDisable(true);

			computeMarineHighUsagePeriodCheckBox.setDisable(true);
			computeMarineHighUsagePeriodActive = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_computeMarineHighUsagePeriodActive", false);
			computeMarineHighUsagePeriodCheckBox.setSelected(false);
		}

		// See if recurring marine access period start values are stored
		recurringMarineAccessPeriodStartMinuteCombobox.setItems(marineAccessPeriodStartMinuteValues);
		recurringMarineAccessPeriodStartHourCombobox.setItems(marineAccessPeriodStartHourValues);

		boolean marineAccessPeriodStartMinuteValueExists = prefs.get("bc_recurringMarineAccessPeriodStartMinute", null) != null;
		if (marineAccessPeriodStartMinuteValueExists)
		{
			recurringMarineAccessPeriodStartMinuteCombobox.getSelectionModel().select(prefs.get("bc_recurringMarineAccessPeriodStartMinute", defaultMarineAcessPeriodStartMinute));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_recurringMarineAccessPeriodStartMinute", defaultMarineAcessPeriodStartMinute);
			recurringMarineAccessPeriodStartMinuteCombobox.getSelectionModel().select(getDefaultMarineAccessPeriodStartMinute());
		}
		recurringMarineAccessPeriodStartMinute = prefs.get("bc_recurringMarineAccessPeriodStartMinute", defaultMarineAcessPeriodStartMinute);

		boolean marineAccessPeriodStartHourValueExists = prefs.get("bc_recurringMarineAccessPeriodStartHour", null) != null;
		if (marineAccessPeriodStartHourValueExists)
		{
			recurringMarineAccessPeriodStartHourCombobox.getSelectionModel().select(prefs.get("bc_recurringMarineAccessPeriodStartHour", defaultMarineAcessPeriodStartHour));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_recurringMarineAccessPeriodStartHour", defaultMarineAcessPeriodStartHour);
			recurringMarineAccessPeriodStartHourCombobox.getSelectionModel().select(getDefaultMarineAccessPeriodStartHour());
		}
		recurringMarineAccessPeriodStartHour = prefs.get("bc_recurringMarineAccessPeriodStartHour", defaultMarineAcessPeriodStartHour);

		// See if recurring marine access period end values are stored
		recurringMarineAccessPeriodEndMinuteCombobox.setItems(marineAccessPeriodEndMinuteValues);
		recurringMarineAccessPeriodEndHourCombobox.setItems(marineAccessPeriodEndHourValues);

		boolean marineAccessPeriodEndValueExists = prefs.get("bc_recurringMarineAccessPeriodEndMinute", null) != null;
		if (marineAccessPeriodEndValueExists)
		{
			recurringMarineAccessPeriodEndMinuteCombobox.getSelectionModel().select(prefs.get("bc_recurringMarineAccessPeriodEndMinute", defaultMarineAcessPeriodEndMinute));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_recurringMarineAccessPeriodEndMinute", defaultMarineAcessPeriodEndMinute);
			recurringMarineAccessPeriodEndMinuteCombobox.getSelectionModel().select(getDefaultMarineAccessPeriodEndMinute());
		}
		recurringMarineAccessPeriodEndMinute = prefs.get("bc_recurringMarineAccessPeriodEndMinute", defaultMarineAcessPeriodEndMinute);

		boolean marineAccessPeriodEndHourValueExists = prefs.get("bc_recurringMarineAccessPeriodEndHour", null) != null;
		if (marineAccessPeriodEndHourValueExists)
		{
			recurringMarineAccessPeriodEndHourCombobox.getSelectionModel().select(prefs.get("bc_recurringMarineAccessPeriodEndHour", defaultMarineAcessPeriodEndHour));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_recurringMarineAccessPeriodEndHour", defaultMarineAcessPeriodEndHour);
			recurringMarineAccessPeriodEndHourCombobox.getSelectionModel().select(getDefaultMarineAccessPeriodEndHour());
		}
		recurringMarineAccessPeriodEndHour = prefs.get("bc_recurringMarineAccessPeriodEndHour", defaultMarineAcessPeriodEndHour);

		// See if preference is stored for computing marine high-usage periods
		if (prefs.getBoolean("bc_computeMarineHighUsagePeriodActive", defaultComputeMarineHighUsagePeriodActive))
		{
			computeMarineHighUsagePeriodActive = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_computeMarineHighUsagePeriodActive", true);
			computeMarineHighUsagePeriodCheckBox.setSelected(true);
		}
		else
		{
			computeMarineHighUsagePeriodActive = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_computeMarineHighUsagePeriodActive", false);
			computeMarineHighUsagePeriodCheckBox.setSelected(false);
		}

		// See if increment crossing value is stored
		incrementCrossingCombobox.setItems(incrementCrossingValues);

		boolean incrementCrossingExists = prefs.get("bc_incrementCrossing", null) != null;
		if (incrementCrossingExists)
		{
			incrementCrossingCombobox.getSelectionModel().select(prefs.get("bc_incrementCrossing", defaultIncrementCrossing));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_incrementCrossing", defaultIncrementCrossing);
			incrementCrossingCombobox.getSelectionModel().select(getDefaultIncrementCrossing());
		}
		incrementCrossing = prefs.get("bc_incrementCrossing", defaultIncrementCrossing);

		// See if increment closure is stored
		incrementClosureCombobox.setItems(incrementClosureValues);

		boolean incrementClosureExists = prefs.get("bc_incrementClosure", null) != null;
		if (incrementClosureExists)
		{
			incrementClosureCombobox.getSelectionModel().select(prefs.get("bc_incrementClosure", defaultIncrementClosure));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_incrementClosure", defaultIncrementClosure);
			incrementClosureCombobox.getSelectionModel().select(getDefaultIncrementClosure());
		}
		incrementClosure = prefs.get("bc_incrementClosure", defaultIncrementClosure);

		// See if increment hourly bucket is stored
		incrementHourlyBucketCombobox.setItems(incrementHourlyBucketValues);

		boolean incrementHourlyBucketExists = prefs.get("bc_incrementHourlyBucket", null) != null;
		if (incrementHourlyBucketExists)
		{
			incrementHourlyBucketCombobox.getSelectionModel().select(prefs.get("bc_incrementHourlyBucket", defaultIncrementHourlyBucket));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_incrementHourlyBucket", defaultIncrementHourlyBucket);
			incrementHourlyBucketCombobox.getSelectionModel().select(getDefaultIncrementHourlyBucket());
		}
		incrementHourlyBucket = prefs.get("bc_incrementHourlyBucket", defaultIncrementHourlyBucket);

		// See if exclusion period value is stored
		exclusionPeriodCombobox.setItems(exclusionPeriodValues);

		boolean exclusionPeriodExists = prefs.get("bc_exclusionPeriod", null) != null;
		if (exclusionPeriodExists)
		{
			exclusionPeriodCombobox.getSelectionModel().select(prefs.get("bc_exclusionPeriod", defaultResultsExclusionPeriod));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("bc_exclusionPeriod", defaultResultsExclusionPeriod);
			exclusionPeriodCombobox.getSelectionModel().select(getDefaultResultsExclusionPeriod());
		}
		resultsExclusionPeriod = prefs.get("bc_exclusionPeriod", defaultResultsExclusionPeriod);

		// Figure and display marine access period duration
		recurringMarineAccessPeriodDurationLabel.setText(figureMarinePeriodDuration(recurringMarineAccessPeriodStartMinute, recurringMarineAccessPeriodEndMinute));
	}

	@FXML private void handleBridgeLowerCombobox(ActionEvent event) 
	{
		lowerMinutes = Integer.valueOf(bridgeLowerCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_bridgeLowerMinutes", bridgeLowerCombobox.getValue());
	}

	@FXML private void handleBridgeLowerTimeIncludedInSplitCheckBox(ActionEvent event)
	{
		if (includeBridgeLowerTimeInSplits)
		{
			includeBridgeLowerTimeInSplits = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_includeBridgeLowerTimeInSplits", false);
		}
		else
		{
			includeBridgeLowerTimeInSplits = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_includeBridgeLowerTimeInSplits", true);
		}
	}

	@FXML private void handleBridgeRaiseCombobox(ActionEvent event) 
	{
		raiseMinutes = Integer.valueOf(bridgeRaiseCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_bridgeRaiseMinutes", bridgeRaiseCombobox.getValue());
	}

	@FXML private void handleBridgeRaiseTimeIncludedInSplitCheckBox(ActionEvent event)
	{
		if (includeBridgeRaiseTimeInSplits)
		{
			includeBridgeRaiseTimeInSplits = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_includeBridgeRaiseTimeInSplits", false);
		}
		else
		{
			includeBridgeRaiseTimeInSplits = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_includeBridgeRaiseTimeInSplits", true);
		}
	}

	@FXML private void handleMinimumUpTimeCombobox(ActionEvent event) 
	{
		minimumUpTimeMinutes = Integer.valueOf(minimumUpTimeCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_minimumUpTimeMinutes", minimumUpTimeCombobox.getValue());
	}

	@FXML private void handleRecurringMarineAccessPeriodFalseRadioButton(ActionEvent event) 
	{
		recurringMarineAccessPeriodActive = false;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putBoolean("bc_recurringMarineAccessPeriodActive", false);

		recurringMarineAccessPeriodFalseRadioButton.setSelected(true);
		recurringMarineAccessPeriodStartMinuteCombobox.setDisable(true);
		recurringMarineAccessPeriodEndMinuteCombobox.setDisable(true);
		recurringMarineAccessPeriodStartHourCombobox.setDisable(true);
		recurringMarineAccessPeriodEndHourCombobox.setDisable(true);

		computeMarineHighUsagePeriodCheckBox.setDisable(true);
		computeMarineHighUsagePeriodActive = false;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putBoolean("bc_computeMarineHighUsagePeriodActive", false);
		computeMarineHighUsagePeriodCheckBox.setSelected(false);
	}

	@FXML private void handleRecurringMarineAccessPeriodTrueRadioButton(ActionEvent event) 
	{
		recurringMarineAccessPeriodActive = true;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putBoolean("bc_recurringMarineAccessPeriodActive", true);

		recurringMarineAccessPeriodTrueRadioButton.setSelected(true);
		recurringMarineAccessPeriodStartMinuteCombobox.setDisable(false);
		recurringMarineAccessPeriodEndMinuteCombobox.setDisable(false);
		recurringMarineAccessPeriodStartHourCombobox.setDisable(false);
		recurringMarineAccessPeriodEndHourCombobox.setDisable(false);

		computeMarineHighUsagePeriodCheckBox.setDisable(false);
	}

	@FXML private void handleSignalPreferredMinutesInAdvanceOfTrainCombobox(ActionEvent event) 
	{
		signalPreferredMinutesInAdvanceOfTrain = Integer.valueOf(signalPreferredMinutesInAdvanceOfTrainCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_signalPreferredMinutesInAdvanceOfTrain", signalPreferredMinutesInAdvanceOfTrainCombobox.getValue());
	}

	@FXML private void handleRecurringMarineAccessPeriodStartMinuteCombobox(ActionEvent event) 
	{
		recurringMarineAccessPeriodStartMinute = String.valueOf(recurringMarineAccessPeriodStartMinuteCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_recurringMarineAccessPeriodStartMinute", recurringMarineAccessPeriodStartMinuteCombobox.getValue());

		// Figure and display marine access period duration
		recurringMarineAccessPeriodDurationLabel.setText(figureMarinePeriodDuration(recurringMarineAccessPeriodStartMinute, recurringMarineAccessPeriodEndMinute));
	}

	@FXML private void handleRecurringMarineAccessPeriodEndMinuteCombobox(ActionEvent event) 
	{
		recurringMarineAccessPeriodEndMinute = String.valueOf(recurringMarineAccessPeriodEndMinuteCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_recurringMarineAccessPeriodEndMinute", recurringMarineAccessPeriodEndMinuteCombobox.getValue());

		// Figure and display marine access period duration
		recurringMarineAccessPeriodDurationLabel.setText(figureMarinePeriodDuration(recurringMarineAccessPeriodStartMinute, recurringMarineAccessPeriodEndMinute));
	}

	@FXML private void handleRecurringMarineAccessPeriodStartHourCombobox(ActionEvent event) 
	{
		recurringMarineAccessPeriodStartHour = String.valueOf(recurringMarineAccessPeriodStartHourCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_recurringMarineAccessPeriodStartHour", recurringMarineAccessPeriodStartHourCombobox.getValue());
	}

	@FXML private void handleRecurringMarineAccessPeriodEndHourCombobox(ActionEvent event) 
	{
		recurringMarineAccessPeriodEndHour = String.valueOf(recurringMarineAccessPeriodEndHourCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_recurringMarineAccessPeriodEndHour", recurringMarineAccessPeriodEndHourCombobox.getValue());
	}

	@FXML private void handleComputeMarineHighUsagePeriodCheckBox(ActionEvent event)
	{
		if (computeMarineHighUsagePeriodActive)
		{
			computeMarineHighUsagePeriodActive = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_computeMarineHighUsagePeriodActive", false);
		}
		else
		{
			computeMarineHighUsagePeriodActive = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("bc_computeMarineHighUsagePeriodActive", true);
		}
	}

	@FXML private void handleIncrementCrossingCombobox(ActionEvent event) 
	{
		incrementCrossing = String.valueOf(incrementCrossingCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_incrementCrossing", incrementCrossingCombobox.getValue());
	}

	@FXML private void handleIncrementClosureCombobox(ActionEvent event) 
	{
		incrementClosure = String.valueOf(incrementClosureCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_incrementClosure", incrementClosureCombobox.getValue());
	}

	@FXML private void handleIncrementHourlyBucketCombobox(ActionEvent event) 
	{
		incrementHourlyBucket = String.valueOf(incrementHourlyBucketCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_incrementHourlyBucket", incrementHourlyBucketCombobox.getValue());
	}

	@FXML private void handleExclusionPeriodCombobox(ActionEvent event) 
	{
		resultsExclusionPeriod = String.valueOf(exclusionPeriodCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("bc_exclusionPeriod", exclusionPeriodCombobox.getValue());
	}

	private static String getDefaultLowerMinutes()
	{
		return defaultLowerMinutes;
	}

	private static String getDefaultRaiseMinutes()
	{
		return defaultRaiseMinutes;
	}

	private static String getDefaultMinimumUpTimeMinutes()
	{
		return defaultMinimumUpTimeMinutes;
	}

	private static String getDefaultMarineAccessPeriodStartMinute()
	{
		return defaultMarineAcessPeriodStartMinute;
	}

	private static String getDefaultMarineAccessPeriodStartHour()
	{
		return defaultMarineAcessPeriodStartHour;
	}

	private static String getDefaultMarineAccessPeriodEndMinute()
	{
		return defaultMarineAcessPeriodEndMinute;
	}

	private static String getDefaultMarineAccessPeriodEndHour()
	{
		return defaultMarineAcessPeriodEndHour;
	}

	private static String getDefaultMinimumMinutesInAdvanceOfTrainValue()
	{
		return defaultSignalPreferredMinutesInAdvanceOfTrain;
	}

	private static String getDefaultResultsExclusionPeriod()
	{
		return defaultResultsExclusionPeriod;
	}

	private static String getDefaultIncrementCrossing()
	{
		return defaultIncrementCrossing;
	}

	private static String getDefaultIncrementClosure()
	{
		return defaultIncrementClosure;
	}

	private static String getDefaultIncrementHourlyBucket()
	{
		return defaultIncrementHourlyBucket;
	}

	public static Integer getLowerMinutes()
	{
		return lowerMinutes;
	}

	public static boolean getIncludeBridgeLowerTimeInClosureTime() 
	{
		return includeBridgeLowerTimeInSplits;
	}

	public static Integer getRaiseMinutes()
	{
		return raiseMinutes;
	}

	public static boolean getIncludeBridgeRaiseTimeInClosureTime() 
	{
		return includeBridgeRaiseTimeInSplits;
	}

	public static Integer getMinimumUpTimeMinutes()
	{
		return minimumUpTimeMinutes;
	}

	public static Boolean getRecurringMarineAccessPeriodActive()
	{
		return recurringMarineAccessPeriodActive;
	}

	public static String getRecurringMarineAccessPeriodStartMinute()
	{
		return recurringMarineAccessPeriodStartMinute;
	}

	public static String getRecurringMarineAccessPeriodStartHour()
	{
		return recurringMarineAccessPeriodStartHour;
	}

	public static String getRecurringMarineAccessPeriodEndMinute()
	{
		return recurringMarineAccessPeriodEndMinute;
	}

	public static String getRecurringMarineAccessPeriodEndHour()
	{
		return recurringMarineAccessPeriodEndHour;
	}

	public static Integer getSignalPreferredMinutesInAdvanceOfTrain()
	{
		return signalPreferredMinutesInAdvanceOfTrain;
	}

	public static boolean getComputeMarineHighUsagePeriodActive() 
	{
		return computeMarineHighUsagePeriodActive;
	}

	public static String getIncrementCrossing()
	{
		return incrementCrossing;
	}

	public static String getIncrementClosure()
	{
		return incrementClosure;
	}

	public static String getIncrementHourlyBucket()
	{
		return incrementHourlyBucket;
	}

	public static Integer getResultsExclusionPeriodInHours()
	{
		Integer hoursToReturn = null;
		if (resultsExclusionPeriod.equals("None"))
			hoursToReturn = 0;
		else if (resultsExclusionPeriod.equals("1 day"))
			hoursToReturn = 24;	
		return hoursToReturn;
	} 

	private static String figureMarinePeriodDuration(String marineAccessPeriodStartMinute, String marineAccessPeriodEndMinute)
	{
		marineAccessPeriodStartMinute = marineAccessPeriodStartMinute.replace(":", "").strip();
		marineAccessPeriodEndMinute = marineAccessPeriodEndMinute.replace(":", "").strip();
		int marineAccessPeriodDuration = Integer.valueOf(marineAccessPeriodEndMinute) - Integer.valueOf(marineAccessPeriodStartMinute);
		if (marineAccessPeriodDuration == 0)
			marineAccessPeriodDuration = 60;
		else if (marineAccessPeriodDuration < 0)
			marineAccessPeriodDuration = marineAccessPeriodDuration + 60;
		String marineAccessPeriodDurationAsString = String.valueOf(marineAccessPeriodDuration);
		return marineAccessPeriodDurationAsString;
	}
}