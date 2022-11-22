package com.bl.bias.app;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.util.prefs.Preferences;

public class BIASMaintenanceWindowAnalysisConfigPageController 
{
	private static Integer blockUnoccupiedInAdvanceOfWindowMinutes;  
	private static Integer windowMustEndMinutesInAdvanceOfTrain;
	private static String minimumWindowDuration;  
	private static Integer incrementMinutes;
	private static Integer decrementMinutes;
	private static String resultsExclusionPeriod;
	private static Boolean generateTraversals;
	private static Boolean generateOccupancies;
	private static Boolean generateObservedWindows;
	private static Boolean generateProposedWindows;

	private static String defaultBlockUnoccupiedInAdvanceOfWindowMinutes = "1"; 
	private static String defaultWindowMustEndMinutesInAdvanceOfTrain = "15";  
	private static String defaultMinimumWindowDuration = "8 hours";
	private static String defaultIncrementMinutes = "5";
	private static String defaultDecrementMinutes = "5";
	private static String defaultResultsExclusionPeriod = "1 day";
	private static Boolean defaultGenerateTraversals = true;
	private static Boolean defaultGenerateOccupancies = true;
	private static Boolean defaultGenerateObservedWindows = true;
	private static Boolean defaultGenerateProposedWindows = true;
	
	private static BooleanBinding disableTraversalsCheckbox;
	private static BooleanBinding disableOccuppanciesCheckbox;
	private static BooleanBinding disableObservedWindowsCheckbox;
	private static BooleanBinding disableProposedWindowsCheckbox;
	
	private static ObservableList<String> blockUnoccupiedInAdvanceOfWindowValues =  FXCollections.observableArrayList("1", "5", "10", "15");
	private static ObservableList<String> windowMustEndMinutesInAdvanceOfTrainComboboxValues =  FXCollections.observableArrayList("1", "5", "10", "15");
	private static ObservableList<String> minimumWindowDurationValues =  FXCollections.observableArrayList("30 min", "1 hour", "2 hours", "3 hours", "4 hours", "8 hours");
	private static ObservableList<String> incrementMinutesValues =  FXCollections.observableArrayList("1", "5", "10");
	private static ObservableList<String> decrementMinutesValues =  FXCollections.observableArrayList("1", "5", "10");
	private static ObservableList<String> exclusionPeriodValues =  FXCollections.observableArrayList("None", "1 day");

	private static Preferences prefs;

	@FXML private ComboBox<String> blockUnoccupiedInAdvanceOfWindowCombobox;
	@FXML private ComboBox<String> windowMustEndMinutesInAdvanceOfTrainCombobox;
	@FXML private ComboBox<String> minimumWindowDurationCombobox;
	@FXML private ComboBox<String> incrementWindowStartCombobox;
	@FXML private ComboBox<String> decrementWindowEndCombobox;
	@FXML private ComboBox<String> exclusionPeriodCombobox;

	@FXML private CheckBox traversalsCheckbox;
	@FXML private CheckBox occupanciesCheckbox;
	@FXML private CheckBox observedWindowsCheckbox;
	@FXML private CheckBox proposedWindowsCheckbox;

	@FXML private void initialize() 
	{
		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");

		// See if time that the block must be unoccupied in advance of window value is stored
		blockUnoccupiedInAdvanceOfWindowCombobox.setItems(blockUnoccupiedInAdvanceOfWindowValues);

		boolean blockUnoccupiedInAdvanceOfWindowExists = prefs.get("mw_blockUnoccupiedInAdvanceOfWindowMinutes", null) != null;
		if (blockUnoccupiedInAdvanceOfWindowExists)
		{
			blockUnoccupiedInAdvanceOfWindowCombobox.getSelectionModel().select(prefs.get("mw_blockUnoccupiedInAdvanceOfWindowMinutes", defaultBlockUnoccupiedInAdvanceOfWindowMinutes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mw_blockUnoccupiedInAdvanceOfWindowMinutes", defaultBlockUnoccupiedInAdvanceOfWindowMinutes);
			blockUnoccupiedInAdvanceOfWindowCombobox.getSelectionModel().select(getDefaultBlockUnoccupiedInAdvanceOfWindowMinutes());
		}
		blockUnoccupiedInAdvanceOfWindowMinutes = Integer.valueOf(prefs.get("mw_blockUnoccupiedInAdvanceOfWindowMinutes", defaultBlockUnoccupiedInAdvanceOfWindowMinutes));

		// See if the minutes that the block ends in advance of next train is stored
		windowMustEndMinutesInAdvanceOfTrainCombobox.setItems(windowMustEndMinutesInAdvanceOfTrainComboboxValues);

		boolean windowMustEndMinutesInAdvanceOfTrainExists = prefs.get("mw_windowMustEndMinutesInAdvanceOfTrain", null) != null;
		if (windowMustEndMinutesInAdvanceOfTrainExists)
		{
			windowMustEndMinutesInAdvanceOfTrainCombobox.getSelectionModel().select(prefs.get("mw_windowMustEndMinutesInAdvanceOfTrain", defaultWindowMustEndMinutesInAdvanceOfTrain));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mw_windowMustEndMinutesInAdvanceOfTrain", defaultWindowMustEndMinutesInAdvanceOfTrain);
			windowMustEndMinutesInAdvanceOfTrainCombobox.getSelectionModel().select(getDefaultWindowMustEndMinutesInAdvanceOfTrain());
		}
		windowMustEndMinutesInAdvanceOfTrain = Integer.valueOf(prefs.get("mw_windowMustEndMinutesInAdvanceOfTrain", defaultWindowMustEndMinutesInAdvanceOfTrain));

		// See if minimum window duration value is stored
		minimumWindowDurationCombobox.setItems(minimumWindowDurationValues);

		boolean minimumWindowDurationExists = prefs.get("mw_minimumWindowDuration", null) != null;
		if (minimumWindowDurationExists)
		{
			minimumWindowDurationCombobox.getSelectionModel().select(prefs.get("mw_minimumWindowDuration", defaultMinimumWindowDuration));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mw_minimumWindowDuration", defaultMinimumWindowDuration);
			minimumWindowDurationCombobox.getSelectionModel().select(getDefaultMinimumWindowDuration());
		}
		minimumWindowDuration = String.valueOf(prefs.get("mw_minimumWindowDuration", defaultMinimumWindowDuration));

		// See if window start increment value is stored
		incrementWindowStartCombobox.setItems(incrementMinutesValues);

		boolean incrementMinutesExists = prefs.get("mw_incrementMinutes", null) != null;
		if (incrementMinutesExists)
		{
			incrementWindowStartCombobox.getSelectionModel().select(prefs.get("mw_incrementMinutes", defaultIncrementMinutes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mw_incrementMinutes", defaultIncrementMinutes);
			incrementWindowStartCombobox.getSelectionModel().select(getDefaultIncrementMinutes());
		}
		incrementMinutes = Integer.valueOf(prefs.get("mw_incrementMinutes", defaultIncrementMinutes));

		// See if window end decrement value is stored
		decrementWindowEndCombobox.setItems(decrementMinutesValues);

		boolean decrementMinuteExists = prefs.get("mw_decrementMinutes", null) != null;
		if (decrementMinuteExists)
		{
			decrementWindowEndCombobox.getSelectionModel().select(prefs.get("mw_decrementMinutes", defaultDecrementMinutes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mw_decrementMinutes", defaultDecrementMinutes);
			decrementWindowEndCombobox.getSelectionModel().select(getDefaultDecrementMinutes());
		}
		decrementMinutes = Integer.valueOf(prefs.get("mw_decrementMinutes", defaultDecrementMinutes));

		// See if exclusion period value is stored
		exclusionPeriodCombobox.setItems(exclusionPeriodValues);

		boolean exclusionPeriodExists = prefs.get("mw_exclusionPeriod", null) != null;
		if (exclusionPeriodExists)
		{
			exclusionPeriodCombobox.getSelectionModel().select(prefs.get("mw_exclusionPeriod", defaultResultsExclusionPeriod));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mw_exclusionPeriod", defaultResultsExclusionPeriod);
			exclusionPeriodCombobox.getSelectionModel().select(getDefaultResultsExclusionPeriod());
		}
		resultsExclusionPeriod = prefs.get("mw_exclusionPeriod", defaultResultsExclusionPeriod);

		// See if preference is stored for generating train traversals
		if (prefs.getBoolean("mw_generateTraversals", defaultGenerateTraversals))
		{
			generateTraversals = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateTraversals", true);
			traversalsCheckbox.setSelected(true);
		}
		else
		{
			generateTraversals = false;
			traversalsCheckbox.setSelected(false);
		}

		// See if preference is stored for generating occupancies
		if (prefs.getBoolean("mw_generateOccupancies", defaultGenerateOccupancies))
		{
			generateOccupancies = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateOccupancies", true);
			occupanciesCheckbox.setSelected(true);
		}
		else
		{
			generateOccupancies = false;
			occupanciesCheckbox.setSelected(false);
		}

		// See if preference is stored for generating observed windows
		if (prefs.getBoolean("mw_generateObservedWindows", defaultGenerateObservedWindows))
		{
			generateObservedWindows = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateObservedWindows", true);
			observedWindowsCheckbox.setSelected(true);
		}
		else
		{
			generateObservedWindows = false;
			observedWindowsCheckbox.setSelected(false);
		}
		
		// See if preference is stored for generating proposed windows
		if (prefs.getBoolean("mw_generateProposedWindows", defaultGenerateProposedWindows))
		{
			generateProposedWindows = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateProposedWindows", true);
			proposedWindowsCheckbox.setSelected(true);
		}
		else
		{
			generateProposedWindows = false;
			proposedWindowsCheckbox.setSelected(false);
		}
		
		// Set up Boolean Bindings
        disableTraversalsCheckbox = (occupanciesCheckbox.selectedProperty().not().and(observedWindowsCheckbox.selectedProperty().not().and(proposedWindowsCheckbox.selectedProperty().not())));
		traversalsCheckbox.disableProperty().bind(disableTraversalsCheckbox);
		
		disableOccuppanciesCheckbox = (traversalsCheckbox.selectedProperty().not().and(observedWindowsCheckbox.selectedProperty().not().and(proposedWindowsCheckbox.selectedProperty().not())));
		occupanciesCheckbox.disableProperty().bind(disableOccuppanciesCheckbox);
		
		disableObservedWindowsCheckbox = (traversalsCheckbox.selectedProperty().not().and(occupanciesCheckbox.selectedProperty().not().and(proposedWindowsCheckbox.selectedProperty().not())));
		observedWindowsCheckbox.disableProperty().bind(disableObservedWindowsCheckbox);
		
		disableProposedWindowsCheckbox = (traversalsCheckbox.selectedProperty().not().and(occupanciesCheckbox.selectedProperty().not().and(observedWindowsCheckbox.selectedProperty().not())));
		proposedWindowsCheckbox.disableProperty().bind(disableProposedWindowsCheckbox);
	} 

	@FXML private void handleBlockUnoccupiedInAdvanceOfWindowCombobox(ActionEvent event) 
	{
		blockUnoccupiedInAdvanceOfWindowMinutes = Integer.valueOf(blockUnoccupiedInAdvanceOfWindowCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("mw_blockUnoccupiedInAdvanceOfWindowMinutes", blockUnoccupiedInAdvanceOfWindowCombobox.getValue());
	}

	@FXML private void handleWindowMustEndMinutesInAdvanceOfTrainCombobox(ActionEvent event) 
	{
		windowMustEndMinutesInAdvanceOfTrain = Integer.valueOf(windowMustEndMinutesInAdvanceOfTrainCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("mw_windowMustEndMinutesInAdvanceOfTrain", windowMustEndMinutesInAdvanceOfTrainCombobox.getValue());
	}

	@FXML private void handleMinimumWindowDurationCombobox(ActionEvent event) 
	{
		minimumWindowDuration= String.valueOf(minimumWindowDurationCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("mw_minimumWindowDuration", minimumWindowDurationCombobox.getValue());
	}

	@FXML private void handleDecrementWindowEndCombobox(ActionEvent event) 
	{
		decrementMinutes = Integer.valueOf(decrementWindowEndCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("mw_decrementMinutes", decrementWindowEndCombobox.getValue());
	}

	@FXML private void handleIncrementWindowStartCombobox(ActionEvent event) 
	{
		incrementMinutes = Integer.valueOf(incrementWindowStartCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("mw_incrementMinutes", incrementWindowStartCombobox.getValue());
	}

	@FXML private void handleExclusionPeriodCombobox(ActionEvent event) 
	{
		resultsExclusionPeriod = String.valueOf(exclusionPeriodCombobox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("mw_exclusionPeriod", exclusionPeriodCombobox.getValue());
	}

	@FXML private void handleTraversalsCheckbox(ActionEvent event)
	{
		if (generateTraversals)
		{
			generateTraversals = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateTraversals", false);
		}
		else
		{
			generateTraversals = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateTraversals", true);
		}
	}

	@FXML private void handleOccupanciesCheckbox(ActionEvent event)
	{
		if (generateOccupancies)
		{
			generateOccupancies = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateOccupancies", false);
		}
		else
		{
			generateOccupancies = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateOccupancies", true);
		}
	}

	@FXML private void handleObservedWindowsCheckbox(ActionEvent event)
	{
		if (generateObservedWindows)
		{
			generateObservedWindows = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateObservedWindows", false);
		}
		else
		{
			generateObservedWindows = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateObservedWindows", true);
		}
	}

	@FXML private void handleProposedWindowsCheckbox(ActionEvent event)
	{
		if (generateProposedWindows)
		{
			generateProposedWindows = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateProposedWindows", false);
		}
		else
		{
			generateProposedWindows = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mw_generateProposedWindows", true);
		}
	}

	private static String getDefaultBlockUnoccupiedInAdvanceOfWindowMinutes()
	{
		return defaultBlockUnoccupiedInAdvanceOfWindowMinutes;
	}

	private static String getDefaultWindowMustEndMinutesInAdvanceOfTrain()
	{
		return defaultWindowMustEndMinutesInAdvanceOfTrain;
	}

	private static String getDefaultMinimumWindowDuration()
	{
		return defaultMinimumWindowDuration;
	}

	private static String getDefaultResultsExclusionPeriod()
	{
		return defaultResultsExclusionPeriod;
	}

	private static String getDefaultIncrementMinutes()
	{
		return defaultIncrementMinutes;
	}

	private static String getDefaultDecrementMinutes()
	{
		return defaultDecrementMinutes;
	}

	public static Integer getBlockUnoccupiedInAdvanceOfWindowMinutes()
	{
		return blockUnoccupiedInAdvanceOfWindowMinutes;
	}

	public static Integer getWindowMustEndMinutesInAdvanceOfTrain()
	{
		return windowMustEndMinutesInAdvanceOfTrain;
	}

	public static Integer getMinimumWindowDurationMinutes()
	{
		Integer minimumWindowDurationMinutes = null;
		if (minimumWindowDuration.equals("30 min"))
			minimumWindowDurationMinutes = 30;
		else if (minimumWindowDuration.equals("1 hour"))
			minimumWindowDurationMinutes = 60;	
		else if (minimumWindowDuration.equals("2 hours"))
			minimumWindowDurationMinutes = 120;	
		else if (minimumWindowDuration.equals("3 hours"))
			minimumWindowDurationMinutes = 180;	
		else if (minimumWindowDuration.equals("4 hours"))
			minimumWindowDurationMinutes = 240;	
		else if (minimumWindowDuration.equals("8 hours"))
			minimumWindowDurationMinutes = 480;	
		return minimumWindowDurationMinutes;
	}

	public static String getMinimumWindowDurationAsString()
	{
		String durationAsString = null;
		if (minimumWindowDuration.equals("30 min"))
			durationAsString = "30m";
		else if (minimumWindowDuration.equals("1 hour"))
			durationAsString = "1h";
		else if (minimumWindowDuration.equals("2 hours"))
			durationAsString = "2h";
		else if (minimumWindowDuration.equals("3 hours"))
			durationAsString = "3h";
		else if (minimumWindowDuration.equals("4 hours"))
			durationAsString = "4h";
		else if (minimumWindowDuration.equals("8 hours"))
			durationAsString = "8h";
		return durationAsString;
	}

	public static Integer getIncrementMinutes()
	{
		return incrementMinutes;
	}

	public static Integer getDecrementMinutes()
	{
		return decrementMinutes;
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

	public static Boolean getGenerateTraversals()
	{
		return generateTraversals;
	}

	public static Boolean getGenerateOccupancies()
	{
		return generateOccupancies;
	}

	public static Boolean getObservedWindows()
	{
		return generateObservedWindows;
	}

	public static Boolean getProposedWindows()
	{
		return generateProposedWindows;
	}
}