package com.bl.bias.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;

import java.util.prefs.Preferences;

import com.bl.bias.objects.MarineAccessPeriod;

public class BIASUscgBridgeComplianceAnalysisConfigPageController 
{
	private static Boolean includeSummaryResultsOnSpreadsheet;
	private static Boolean includeSummaryResultsOnNotepad;
	private static Boolean includeConfidentialityDisclaimer;

	private static Boolean defaultIncludeSummaryResultsOnSpreadsheet = true;
	private static Boolean defaultIncludeSummaryResultsOnNotepad = true;
	private static Boolean defaultIncludeConfidentialityDisclaimer = true;

	private static Preferences prefs;
	
	private ObservableList<MarineAccessPeriod> marineAccessPeriodsData = FXCollections.observableArrayList();
		
	private static int periodsCounter = 0;

	@FXML private CheckBox includeSummaryResultsOnSpreadsheetCheckBox;
	@FXML private CheckBox includeSummaryResultsOnNotepadCheckBox;
	@FXML private CheckBox includeConfidentialityDisclaimerCheckBox;
	
	@FXML private Button clearAllMarineAccessPeriodsButton;
	@FXML private Button addPeriodButton;
	@FXML private Button deletePeriodButton;
	
	@FXML private RadioButton viewEntriesOnlyRadioButton;
	@FXML private RadioButton viewAndEditEntriesRadioButton;
	
	@FXML private TableView<MarineAccessPeriod> marineAccessPeriodsTable;
	
	@FXML private TableColumn<MarineAccessPeriod, Double> marinePeriodStartDouble;
	@FXML private TableColumn<MarineAccessPeriod, String> marinePeriodStartTime;
	@FXML private TableColumn<MarineAccessPeriod, Double> marinePeriodEndDouble;
	@FXML private TableColumn<MarineAccessPeriod, String> marinePeriodEndTime;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> mo;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> tu;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> we;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> th;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> fr;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> sa;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> su;
	
	@FXML private void initialize() 
	{
		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");

		// See if including summary results on spreadsheet is stored
		if (prefs.getBoolean("cg_includeSummaryResultsOnSpreadsheet", defaultIncludeSummaryResultsOnSpreadsheet))
		{
			includeSummaryResultsOnSpreadsheet = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeSummaryResultsOnSpreadsheet", true);
			includeSummaryResultsOnSpreadsheetCheckBox.setSelected(true);
		}
		else
		{
			includeSummaryResultsOnSpreadsheet = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeSummaryResultsOnSpreadsheet", false);
			includeSummaryResultsOnSpreadsheetCheckBox.setSelected(false);
		}

		// See if including summary results on generated Notepad doc is stored
		if (prefs.getBoolean("cg_includeSummaryResultsOnNotepad", defaultIncludeSummaryResultsOnNotepad))
		{
			includeSummaryResultsOnNotepad = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeSummaryResultsOnNotepad", true);
			includeSummaryResultsOnNotepadCheckBox.setSelected(true);
		}
		else
		{
			includeSummaryResultsOnNotepad = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeSummaryResultsOnNotepad", false);
			includeSummaryResultsOnNotepadCheckBox.setSelected(false);
		}

		// See if including legal disclaimer is stored
		if (prefs.getBoolean("cg_includeConfidentialityDisclaimer", defaultIncludeConfidentialityDisclaimer))
		{
			includeConfidentialityDisclaimer = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeConfidentialityDisclaimer", true);
			includeConfidentialityDisclaimerCheckBox.setSelected(true);
		}
		else
		{
			includeConfidentialityDisclaimer = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeConfidentialityDisclaimer", false);
			includeConfidentialityDisclaimerCheckBox.setSelected(false);
		}
		
		marinePeriodStartDouble.setSortable(false);
		//marinePeriodStartTime.setSortable(false);
		marinePeriodEndDouble.setSortable(false);
		//marinePeriodEndTime.setSortable(false);
		mo.setSortable(false);
		tu.setSortable(false);
		we.setSortable(false);
		th.setSortable(false);
		fr.setSortable(false);
		sa.setSortable(false);
		su.setSortable(false);
		
		marinePeriodStartDouble.setReorderable(false);
		//marinePeriodStartTime.setReorderable(false);
		marinePeriodEndDouble.setReorderable(false);
		//marinePeriodEndTime.setReorderable(false);
		mo.setReorderable(false);
		tu.setReorderable(false);
		we.setReorderable(false);
		th.setReorderable(false);
		fr.setReorderable(false);
		sa.setReorderable(false);
		su.setReorderable(false);
		
		marineAccessPeriodsData.addAll(new MarineAccessPeriod(0.25, 0.50 , true, true, true, true, true, true, true)
				);
		
		marineAccessPeriodsTable.setItems(marineAccessPeriodsData);
	}

	@FXML private void handleIncludeSummaryResultsOnSpreadsheetCheckBox(ActionEvent event)
	{
		if (includeSummaryResultsOnSpreadsheet)
		{
			includeSummaryResultsOnSpreadsheet = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeSummaryResultsOnSpreadsheet", false);
		}
		else
		{
			includeSummaryResultsOnSpreadsheet = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeSummaryResultsOnSpreadsheet", true);
		}
	}

	@FXML private void handleIncludeSummaryResultsOnNotepadCheckBox(ActionEvent event)
	{
		if (includeSummaryResultsOnNotepad)
		{
			includeSummaryResultsOnNotepad = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeSummaryResultsOnNotepad", false);
		}
		else
		{
			includeSummaryResultsOnNotepad = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeSummaryResultsOnNotepad", true);
		}
	}

	@FXML private void handleIncludeConfidentialityDisclaimerCheckBox(ActionEvent event)
	{
		if (includeConfidentialityDisclaimer)
		{
			includeConfidentialityDisclaimer = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeConfidentialityDisclaimer", false);
		}
		else
		{
			includeConfidentialityDisclaimer = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeConfidentialityDisclaimer", true);
		}
	}
	
	@FXML private void handleViewEntriesOnlyRadioButton(ActionEvent event) 
	{
		
	}
	
	@FXML private void handleViewAndEditEntriesRadioButton(ActionEvent event) 
	{
		
	}
	
	@FXML private void handleClearAllMarineAccessPeriodsButton(ActionEvent event)
	{
		
	}
	
	@FXML private void handleAddPeriodButton(ActionEvent event)
	{
		
	}
	
	@FXML private void handleDeletePeriodsButton(ActionEvent event)
	{
		
	}
	
	public static Boolean getIncludeSummaryResultsOnSpreadsheet()
	{
		return includeSummaryResultsOnSpreadsheet;
	}

	public static Boolean getIncludeSummaryResultsOnNotepad()
	{
		return includeSummaryResultsOnNotepad;
	}

	public static Boolean getIncludeConfidentialityDisclaimer()
	{
		return includeConfidentialityDisclaimer;
	}
}