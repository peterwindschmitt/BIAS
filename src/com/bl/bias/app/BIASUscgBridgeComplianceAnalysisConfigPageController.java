package com.bl.bias.app;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.prefs.Preferences;

import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.MarineAccessPeriod;

public class BIASUscgBridgeComplianceAnalysisConfigPageController 
{
	private static Boolean includeSummaryResultsOnSpreadsheet;
	private static Boolean includeViolationsOnClosureSheet;
	private static Boolean includeSummaryResultsOnNotepad;
	private static Boolean includeConfidentialityDisclaimer;
	private static Boolean computeMarineHighUsagePeriodActive;
	private static String inCircuitPermissibleDelay;
	private static String marineAccessPeriodStartHour;
	private static String marineAccessPeriodEndHour;
	private static String maxClosureMinutes;

	private static Boolean defaultIncludeSummaryResultsOnSpreadsheet = true;
	private static Boolean defaultIncludeViolationsOnClosureSheet = true;
	private static Boolean defaultIncludeSummaryResultsOnNotepad = true;
	private static Boolean defaultIncludeConfidentialityDisclaimer = true;
	private static Boolean defaultComputeMarineHighUsagePeriodActive = false;
	private static String defaultInCircuitPermissibleDelay = "0";
	private static String defaultMarineAcessPeriodStartHour = "00:00";
	private static String defaultMarineAcessPeriodEndHour = "00:00";
	private static String defaultMaxClosureMinutes = "60";

	private static Double marineAccessPeriodSpan = 0.0;

	private static Boolean validMarinePeriods = false;

	private static Preferences prefs;

	private static ObservableList<MarineAccessPeriod> marineAccessPeriodsData = FXCollections.observableArrayList();
	private static ObservableList<String> highUsageHourValues =  FXCollections.observableArrayList("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
			"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00");
	private static ObservableList<String> inCircuitPermissibleDelayValues =  FXCollections.observableArrayList("0", "5", "10");
	private static ObservableList<String> maxClosureDurationMinutesValues =  FXCollections.observableArrayList("30", "60", "90", "120");

	@FXML private CheckBox includeSummaryResultsOnSpreadsheetCheckBox;
	@FXML private CheckBox includeViolationsOnClosureSheetCheckBox;
	@FXML private CheckBox includeSummaryResultsOnNotepadCheckBox;
	@FXML private CheckBox includeConfidentialityDisclaimerCheckBox;
	@FXML private CheckBox computeMarineHighUsagePeriodsCheckBox;

	@FXML private ComboBox<String> inCircuitDelayComboBox;
	@FXML private ComboBox<String> startHighUsageHourComboBox;
	@FXML private ComboBox<String> endHighUsageHourComboBox;
	@FXML private ComboBox<String> maxClosureMinutesComboBox;

	@FXML private Label highUsageMarinePeriodSpanLabel;

	@FXML private Button clearRegistryButton;
	@FXML private Button addPeriodButton;
	@FXML private Button deletePeriodButton;
	@FXML private Button saveMarineAccessPeriodsToRegistryButton;
	@FXML private Button saveToFileButton;
	@FXML private Button loadFromFileButton;

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
		marinePeriodStartDouble.setStyle( "-fx-alignment: CENTER;");
		marinePeriodStartTime.setStyle( "-fx-alignment: CENTER;");
		marinePeriodEndDouble.setStyle( "-fx-alignment: CENTER;");
		marinePeriodEndTime.setStyle( "-fx-alignment: CENTER;");

		String marineAccessPeriod = null;

		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");

		// See if marine access periods are already defined
		if (prefs.get("cg_marineAccessPeriods", null) != null)
		{
			validMarinePeriods = true;

			String[] periods = prefs.get("cg_marineAccessPeriods", marineAccessPeriod).split(":");
			for (int i = 0; i < periods.length; i++)
			{
				String[] values = periods[i].replace("[", "").replace("]", "").split(",");
				marineAccessPeriodsData.add(new MarineAccessPeriod(Double.valueOf(values[0]), 
						Double.valueOf(values[1]), 
						Boolean.valueOf(values[2]), 
						Boolean.valueOf(values[3]),
						Boolean.valueOf(values[4]),
						Boolean.valueOf(values[5]),
						Boolean.valueOf(values[6]),
						Boolean.valueOf(values[7]),
						Boolean.valueOf(values[8])));
			}
			marineAccessPeriodsTable.setItems(marineAccessPeriodsData);	
		}

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

		// See if including violations on closure sheet is stored
		if (prefs.getBoolean("cg_includeViolationsOnClosureSheet", defaultIncludeViolationsOnClosureSheet))
		{
			includeViolationsOnClosureSheet = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeViolationsOnClosureSheet", true);
			includeViolationsOnClosureSheetCheckBox.setSelected(true);
		}
		else
		{
			includeViolationsOnClosureSheet = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeViolationsOnClosureSheet", false);
			includeViolationsOnClosureSheetCheckBox.setSelected(false);
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

		// See if preference is stored for computing marine high-usage periods
		if (prefs.getBoolean("cg_computeMarineHighUsagePeriodActive", defaultComputeMarineHighUsagePeriodActive))
		{
			computeMarineHighUsagePeriodActive = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActive", true);
			computeMarineHighUsagePeriodsCheckBox.setSelected(true);

			startHighUsageHourComboBox.setDisable(false);
			endHighUsageHourComboBox.setDisable(false);
		}
		else
		{
			computeMarineHighUsagePeriodActive = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActive", false);
			computeMarineHighUsagePeriodsCheckBox.setSelected(false);

			startHighUsageHourComboBox.setDisable(true);
			endHighUsageHourComboBox.setDisable(true);
		}

		// See if preference is stored for max closure duration
		maxClosureMinutesComboBox.setItems(maxClosureDurationMinutesValues);

		boolean maxClosureMinutesExists = prefs.get("cg_maxClosureMinutes", null) != null;
		if (maxClosureMinutesExists)
		{
			maxClosureMinutesComboBox.getSelectionModel().select(prefs.get("cg_maxClosureMinutes", defaultMaxClosureMinutes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_maxClosureMinutes", defaultMaxClosureMinutes);
			maxClosureMinutesComboBox.getSelectionModel().select(defaultMaxClosureMinutes);
		}
		maxClosureMinutes = prefs.get("cg_maxClosureMinutes", defaultMaxClosureMinutes);

		marinePeriodStartDouble.setCellValueFactory(new PropertyValueFactory<MarineAccessPeriod, Double>("marinePeriodStartDouble"));
		marinePeriodStartDouble.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
		marinePeriodStartTime.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,String>,ObservableValue<String>>(){
					@Override public
					ObservableValue<String> call( CellDataFeatures<MarineAccessPeriod,String> p ){
						return p.getValue().getMarinePeriodStartTime(); }});

		marinePeriodEndDouble.setCellValueFactory(new PropertyValueFactory<MarineAccessPeriod, Double>("marinePeriodEndDouble"));
		marinePeriodEndDouble.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
		marinePeriodEndTime.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,String>,ObservableValue<String>>(){
					@Override public
					ObservableValue<String> call( CellDataFeatures<MarineAccessPeriod,String> p ){
						return p.getValue().getMarinePeriodEndTime(); }});

		mo.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getMo(); }});
		mo.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		tu.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getTu(); }});
		tu.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		we.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getWe(); }});
		we.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		th.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getTh(); }});
		th.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		fr.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getFr(); }});
		fr.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		sa.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getSa(); }});
		sa.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		su.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getSu(); }});
		su.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		marinePeriodStartDouble.setReorderable(false);
		marinePeriodStartTime.setReorderable(false);
		marinePeriodEndDouble.setReorderable(false);
		marinePeriodEndTime.setReorderable(false);
		mo.setReorderable(false);
		tu.setReorderable(false);
		we.setReorderable(false);
		th.setReorderable(false);
		fr.setReorderable(false);
		sa.setReorderable(false);
		su.setReorderable(false);

		// See if "in-circuit" permissible delay values are stored
		inCircuitDelayComboBox.setItems(inCircuitPermissibleDelayValues);

		boolean inCircuitDelayValueExists = prefs.get("cg_inCircuitPermissibleDelay", null) != null;
		if (inCircuitDelayValueExists)
		{
			inCircuitDelayComboBox.getSelectionModel().select(prefs.get("cg_inCircuitPermissibleDelay", defaultInCircuitPermissibleDelay));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_inCircuitPermissibleDelay", defaultInCircuitPermissibleDelay);
			inCircuitDelayComboBox.getSelectionModel().select(defaultInCircuitPermissibleDelay);
		}
		inCircuitPermissibleDelay = prefs.get("cg_inCircuitPermissibleDelay", defaultInCircuitPermissibleDelay);

		// See if recurring marine access period start values are stored
		startHighUsageHourComboBox.setItems(highUsageHourValues);

		boolean marineAccessPeriodStartHourValueExists = prefs.get("cg_marineAccessPeriodStartHour", null) != null;
		if (marineAccessPeriodStartHourValueExists)
		{
			startHighUsageHourComboBox.getSelectionModel().select(prefs.get("cg_marineAccessPeriodStartHour", defaultMarineAcessPeriodStartHour));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_marineAccessPeriodStartHour", defaultMarineAcessPeriodStartHour);
			startHighUsageHourComboBox.getSelectionModel().select(defaultMarineAcessPeriodStartHour);
		}
		marineAccessPeriodStartHour = prefs.get("cg_marineAccessPeriodStartHour", defaultMarineAcessPeriodStartHour);

		// See if recurring marine access period start values are stored
		endHighUsageHourComboBox.setItems(highUsageHourValues);

		boolean marineAccessPeriodEndHourValueExists = prefs.get("cg_marineAccessPeriodEndHour", null) != null;
		if (marineAccessPeriodEndHourValueExists)
		{
			endHighUsageHourComboBox.getSelectionModel().select(prefs.get("cg_marineAccessPeriodEndHour", defaultMarineAcessPeriodEndHour));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_marineAccessPeriodEndHour", defaultMarineAcessPeriodEndHour);
			endHighUsageHourComboBox.getSelectionModel().select(defaultMarineAcessPeriodEndHour);
		}
		marineAccessPeriodEndHour = prefs.get("cg_marineAccessPeriodEndHour", defaultMarineAcessPeriodEndHour);

		figureMarinePeriodSpanAsDouble(marineAccessPeriodStartHour, marineAccessPeriodEndHour);
		highUsageMarinePeriodSpanLabel.setText(getMarinePeriodSpanAsString());
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

	@FXML private void handleIncludeViolationsOnClosureSheetCheckBox(ActionEvent event)
	{
		if (includeViolationsOnClosureSheet)
		{
			includeViolationsOnClosureSheet = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeViolationsOnClosureSheet", false);
		}
		else
		{
			includeViolationsOnClosureSheet = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_includeViolationsOnClosureSheet", true);
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
		clearRegistryButton.setDisable(true);
		saveMarineAccessPeriodsToRegistryButton.setDisable(true);
		addPeriodButton.setDisable(true);
		deletePeriodButton.setDisable(true);
		saveToFileButton.setDisable(true);
		loadFromFileButton.setDisable(true);

		marineAccessPeriodsTable.setEditable(false);
		marineAccessPeriodsTable.refresh();

		marinePeriodStartDouble.setEditable(false);
		marinePeriodEndDouble.setEditable(false);

		mo.setEditable(false);
		tu.setEditable(false);
		we.setEditable(false);
		th.setEditable(false);
		fr.setEditable(false);
		sa.setEditable(false);
		su.setEditable(false);
	}

	@FXML private void handleViewAndEditEntriesRadioButton(ActionEvent event) 
	{
		addPeriodButton.setDisable(false);
		loadFromFileButton.setDisable(false);

		if (validMarinePeriods)
		{
			deletePeriodButton.setDisable(false);

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("false"))
			{
				clearRegistryButton.setDisable(true);
			}
			else
			{
				clearRegistryButton.setDisable(false);
			}

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				saveMarineAccessPeriodsToRegistryButton.setDisable(false);
				saveToFileButton.setDisable(false);
			}
		}
		else
		{
			clearRegistryButton.setDisable(true);
			saveMarineAccessPeriodsToRegistryButton.setDisable(true);
			saveToFileButton.setDisable(true);
		}

		marineAccessPeriodsTable.setEditable(true);

		marinePeriodStartDouble.setEditable(true);
		marinePeriodStartDouble.setOnEditCommit(new EventHandler<CellEditEvent<MarineAccessPeriod, Double>>() {      
			@Override
			public void handle(CellEditEvent<MarineAccessPeriod, Double> t) {
				if (t.getNewValue() != null)
				{
					int row = t.getTableView().getEditingCell().getRow();
					Double newValue = t.getNewValue();

					if (newValue >= marineAccessPeriodsData.get(row).getMarinePeriodEndDouble())
					{
						// Alert
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("The marine period start time is later than the end time.");	
						Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
						alert.show();
					}
					else
					{
						// Update in data
						marineAccessPeriodsData.get(row).setMarinePeriodStartDouble(newValue);
					}
				}

				marineAccessPeriodsTable.refresh();
			}
		});

		marinePeriodEndDouble.setEditable(true);
		marinePeriodEndDouble.setOnEditCommit(new EventHandler<CellEditEvent<MarineAccessPeriod, Double>>() {      
			@Override
			public void handle(CellEditEvent<MarineAccessPeriod, Double> t) {
				if (t.getNewValue() != null)
				{
					int row = t.getTableView().getEditingCell().getRow();
					Double newValue = t.getNewValue();

					if (newValue <= marineAccessPeriodsData.get(row).getMarinePeriodStartDouble())
					{
						// Alert
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText(null);
						alert.setContentText("The marine period start time is later than the end time.");	
						Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
						alert.show();
					}
					else
					{
						// Update in data
						marineAccessPeriodsData.get(row).setMarinePeriodEndDouble(newValue);
					}
				}

				marineAccessPeriodsTable.refresh();
			}
		});

		mo.setEditable(true);
		tu.setEditable(true);
		we.setEditable(true);
		th.setEditable(true);
		fr.setEditable(true);
		sa.setEditable(true);
		su.setEditable(true);
	}

	@FXML private void handleClearRegistryButton(ActionEvent event)
	{
		if (prefs.get("cg_marineAccessPeriods", null) != null)
		{
			prefs.remove("cg_marineAccessPeriods");
			clearRegistryButton.setDisable(true);
			saveMarineAccessPeriodsToRegistryButton.setDisable(true);

			if (marineAccessPeriodsTable.getItems().size() == 0)
			{
				deletePeriodButton.setDisable(true);
			}
		}
	}

	@FXML private void handleAddPeriodButton(ActionEvent event)
	{
		marineAccessPeriodsData.add(new MarineAccessPeriod(0.00000,0.99999,false,false,false,false,false,false,false));
		marineAccessPeriodsTable.setItems(marineAccessPeriodsData);
		validMarinePeriods = true;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			saveMarineAccessPeriodsToRegistryButton.setDisable(false);
		}

		if (marineAccessPeriodsTable.getItems().size() > 0)
			deletePeriodButton.setDisable(false);
	}

	@FXML private void handleDeletePeriodButton(ActionEvent event)
	{	
		MarineAccessPeriod selectedPeriod = marineAccessPeriodsTable.getSelectionModel().getSelectedItem();
		marineAccessPeriodsTable.getItems().remove(selectedPeriod);
		if (marineAccessPeriodsTable.getItems().size() == 0)
		{
			validMarinePeriods = false;
			deletePeriodButton.setDisable(true);
			saveMarineAccessPeriodsToRegistryButton.setDisable(true);
		}
	}

	@FXML private void handleSaveToFileButton(ActionEvent event)
	{	
		savePeriodsToFile();
	}

	@FXML private void handleLoadFromFileButton(ActionEvent event) throws FileNotFoundException
	{	
		loadPeriodsFromFile();
	}

	@FXML private void handleSaveMarineAccessPeriodsToRegistryButton(ActionEvent event)
	{
		String registryEntry = "";
		for (int i = 0; i < marineAccessPeriodsData.size(); i++)
		{	
			registryEntry+="[";

			registryEntry+=marineAccessPeriodsData.get(i).getMarinePeriodStartDouble()+",";
			registryEntry+=marineAccessPeriodsData.get(i).getMarinePeriodEndDouble()+",";
			registryEntry+=marineAccessPeriodsData.get(i).getMo().getValue()+",";
			registryEntry+=marineAccessPeriodsData.get(i).getTu().getValue()+",";
			registryEntry+=marineAccessPeriodsData.get(i).getWe().getValue()+",";
			registryEntry+=marineAccessPeriodsData.get(i).getTh().getValue()+",";
			registryEntry+=marineAccessPeriodsData.get(i).getFr().getValue()+",";
			registryEntry+=marineAccessPeriodsData.get(i).getSa().getValue()+",";
			registryEntry+=marineAccessPeriodsData.get(i).getSu().getValue();

			if (i == (marineAccessPeriodsData.size() - 1))
				registryEntry+="]";
			else
				registryEntry+="]:";
		}

		validMarinePeriods = true;

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.put("cg_marineAccessPeriods", registryEntry);
			clearRegistryButton.setDisable(false);
		}
	}


	@FXML private void handleComputeMarineHighUsagePeriodsCheckBox()
	{
		if (computeMarineHighUsagePeriodActive)
		{
			computeMarineHighUsagePeriodActive = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActive", false);

			startHighUsageHourComboBox.setDisable(true);
			endHighUsageHourComboBox.setDisable(true);
		}
		else
		{
			computeMarineHighUsagePeriodActive = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActive", true);

			startHighUsageHourComboBox.setDisable(false);
			endHighUsageHourComboBox.setDisable(false);
		}
	}

	@FXML private void handleInCircuitDelayComboBox(ActionEvent event) 
	{
		inCircuitPermissibleDelay = String.valueOf(inCircuitDelayComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_inCircuitPermissibleDelay", inCircuitDelayComboBox.getValue());
	}

	@FXML private void handleStartHighUsageHourComboBox(ActionEvent event) 
	{
		marineAccessPeriodStartHour = String.valueOf(startHighUsageHourComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_marineAccessPeriodStartHour", startHighUsageHourComboBox.getValue());

		// Figure and display marine access period span
		figureMarinePeriodSpanAsDouble(marineAccessPeriodStartHour, marineAccessPeriodEndHour);
		highUsageMarinePeriodSpanLabel.setText(getMarinePeriodSpanAsString());
	}

	@FXML private void handleEndHighUsageHourComboBox(ActionEvent event) 
	{
		marineAccessPeriodEndHour = String.valueOf(endHighUsageHourComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_marineAccessPeriodEndHour", endHighUsageHourComboBox.getValue());

		// Figure and display marine access period span
		figureMarinePeriodSpanAsDouble(marineAccessPeriodStartHour, marineAccessPeriodEndHour);
		highUsageMarinePeriodSpanLabel.setText(getMarinePeriodSpanAsString());
	}

	@FXML private void handleMaxClosureMinutesComboBox(ActionEvent event) 
	{
		maxClosureMinutes = String.valueOf(maxClosureMinutesComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_maxClosureMinutes", maxClosureMinutesComboBox.getValue());
	}

	public static Boolean getIncludeSummaryResultsOnSpreadsheet()
	{
		return includeSummaryResultsOnSpreadsheet;
	}

	public static Boolean getIncludeSummaryResultsOnNotepad()
	{
		return includeSummaryResultsOnNotepad;
	}

	public static Boolean getIncludeViolationsOnClosuresSheet()
	{
		return includeViolationsOnClosureSheet;
	}

	public static Boolean getIncludeConfidentialityDisclaimer()
	{
		return includeConfidentialityDisclaimer;
	}

	public static Boolean getIncludeMarineHighUsagePeriods()
	{
		return computeMarineHighUsagePeriodActive;
	}

	public static String getInCircuitPermissibleDelay()
	{
		return inCircuitPermissibleDelay;
	}

	public static String getMarineAccessPeriodStartHour()
	{
		return marineAccessPeriodStartHour;
	}

	public static String getMarineAccessPeriodEndHour()
	{
		return marineAccessPeriodEndHour;
	}

	public static String getMaxClosureMinutes()
	{
		return maxClosureMinutes;
	}

	public static ObservableList<MarineAccessPeriod> getMarineAccessPeriods()
	{
		return marineAccessPeriodsData;
	}

	private static Double figureMarinePeriodSpanAsDouble(String marineAccessPeriodStartHour, String marineAccessPeriodEndHour)
	{
		marineAccessPeriodStartHour = marineAccessPeriodStartHour.replace(":00", "").strip();
		marineAccessPeriodEndHour = marineAccessPeriodEndHour.replace(":00", "").strip();
		marineAccessPeriodSpan = Double.valueOf(marineAccessPeriodEndHour) - Double.valueOf(marineAccessPeriodStartHour);
		if (marineAccessPeriodSpan == 0.0)
			marineAccessPeriodSpan = 24.0;
		else if (marineAccessPeriodSpan < 0)
			marineAccessPeriodSpan = marineAccessPeriodSpan + 24.0;
		return marineAccessPeriodSpan;
	}

	public static Double getMarineAccessPeriodSpanAsDouble()
	{
		return marineAccessPeriodSpan;
	}

	private static String getMarinePeriodSpanAsString()
	{
		String marineAccessPeriodSpanAsString = String.valueOf(marineAccessPeriodSpan).replace(".0","");

		return marineAccessPeriodSpanAsString;
	}

	public static Integer getMarinePeriodsPerWeekAsInteger()
	{
		int marineAccessPeriodsPerWeekAsInteger = 0;
		for (int i = 0; i < marineAccessPeriodsData.size(); i++)
		{
			if (marineAccessPeriodsData.get(i).getMo().getValue())
				marineAccessPeriodsPerWeekAsInteger++;
			if (marineAccessPeriodsData.get(i).getTu().getValue())
				marineAccessPeriodsPerWeekAsInteger++;
			if (marineAccessPeriodsData.get(i).getWe().getValue())
				marineAccessPeriodsPerWeekAsInteger++;
			if (marineAccessPeriodsData.get(i).getTh().getValue())
				marineAccessPeriodsPerWeekAsInteger++;
			if (marineAccessPeriodsData.get(i).getFr().getValue())
				marineAccessPeriodsPerWeekAsInteger++;
			if (marineAccessPeriodsData.get(i).getSa().getValue())
				marineAccessPeriodsPerWeekAsInteger++;
			if (marineAccessPeriodsData.get(i).getSu().getValue())
				marineAccessPeriodsPerWeekAsInteger++;
		}

		return marineAccessPeriodsPerWeekAsInteger;
	}

	private static class CustomDoubleStringConverter extends DoubleStringConverter 
	{
		private final DoubleStringConverter converter = new DoubleStringConverter();

		@Override
		public Double fromString(String string) {
			try 
			{    
				Double doubleFromString = converter.fromString(string);
				if (doubleFromString == null)
				{
					// Alert
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Value is null.");	
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
					alert.show();

					return null;
				}
				else if ((doubleFromString < 0) || (doubleFromString >= 1))
				{
					// Alert
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Value is outside of valid range.");	
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
					alert.show();

					return null;
				}
				return doubleFromString;
			} 
			catch (NumberFormatException e) 
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Value must be Double.");	
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
				alert.show();
			}
			return null;
		}
	}

	void savePeriodsToFile()
	{
		// Get location to save file to if not using system time as file name
		FileChooser fileChooser = new FileChooser();
		Stage stageForFolderChooser = (Stage) saveToFileButton.getScene().getWindow();
		fileChooser.setTitle("Select Location to Save Results");
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("Marine Access Periods (.mrp)", "*.mrp");
		fileChooser.getExtensionFilters().add(fileExtensions);

		File file = fileChooser.showSaveDialog(stageForFolderChooser);

		if (file != null) 
		{
			try 
			{
				String fileEntry = "";
				for (int i = 0; i < marineAccessPeriodsData.size(); i++)
				{	
					fileEntry+="[";

					fileEntry+=marineAccessPeriodsData.get(i).getMarinePeriodStartDouble()+",";
					fileEntry+=marineAccessPeriodsData.get(i).getMarinePeriodEndDouble()+",";
					fileEntry+=marineAccessPeriodsData.get(i).getMo().getValue()+",";
					fileEntry+=marineAccessPeriodsData.get(i).getTu().getValue()+",";
					fileEntry+=marineAccessPeriodsData.get(i).getWe().getValue()+",";
					fileEntry+=marineAccessPeriodsData.get(i).getTh().getValue()+",";
					fileEntry+=marineAccessPeriodsData.get(i).getFr().getValue()+",";
					fileEntry+=marineAccessPeriodsData.get(i).getSa().getValue()+",";
					fileEntry+=marineAccessPeriodsData.get(i).getSu().getValue();

					if (i == (marineAccessPeriodsData.size() - 1))
						fileEntry+="]";
					else
						fileEntry+="]:";
				}

				try 
				{
					FileWriter fileWriter = new FileWriter(file);
					fileWriter.write(fileEntry);
					fileWriter.close();
				} 
				catch (IOException e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}	
			} 
			catch (Exception e) 
			{
				ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}
		}
	}

	void loadPeriodsFromFile() throws FileNotFoundException
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("Marine Access Periods (.mrp)", "*.mrp");
		fileChooser.getExtensionFilters().add(fileExtensions);	

		Stage stageForFileChooser = (Stage) loadFromFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		if (file != null)
		{
			marineAccessPeriodsData.clear();
			
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				String[] periods = lineFromFile.split(":");
				for (int i = 0; i < periods.length; i++)
				{
					String[] values = periods[i].replace("[", "").replace("]", "").split(",");
					marineAccessPeriodsData.add(new MarineAccessPeriod(Double.valueOf(values[0]), 
							Double.valueOf(values[1]), 
							Boolean.valueOf(values[2]), 
							Boolean.valueOf(values[3]),
							Boolean.valueOf(values[4]),
							Boolean.valueOf(values[5]),
							Boolean.valueOf(values[6]),
							Boolean.valueOf(values[7]),
							Boolean.valueOf(values[8])));
				}
			}
			scanner.close();

			if (marineAccessPeriodsData.size() > 0)
			{
				marineAccessPeriodsTable.setItems(marineAccessPeriodsData);	
				saveToFileButton.setDisable(false);
				saveMarineAccessPeriodsToRegistryButton.setDisable(false);
				deletePeriodButton.setDisable(false); 
			}
		}
	}
}