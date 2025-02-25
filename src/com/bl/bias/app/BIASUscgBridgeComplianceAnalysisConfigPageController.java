package com.bl.bias.app;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Tab;
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
import javafx.scene.control.TextField;
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
	private static Preferences prefs;

	// Common to both bridges
	private static Boolean includeSummaryResultsOnSpreadsheet;
	private static Boolean includeViolationsOnClosureSheet;
	private static Boolean includeSummaryResultsOnNotepad;
	private static Boolean includeConfidentialityDisclaimer;
	private static Boolean disableCheckingCycleOrder;

	private static Boolean defaultIncludeSummaryResultsOnSpreadsheet = true;
	private static Boolean defaultIncludeViolationsOnClosureSheet = true;
	private static Boolean defaultIncludeSummaryResultsOnNotepad = true;
	private static Boolean defaultIncludeConfidentialityDisclaimer = true;
	private static Boolean defaultComputeMarineHighUsagePeriodActive = false;
	private static Boolean defaultBridgeEnabled = false;
	private static Boolean defaultDisableCheckingCycleOrder = false;
	private static String defaultInCircuitPermissibleDelay = "0";
	private static String defaultMarineAcessPeriodStartHour = "00:00";
	private static String defaultMarineAcessPeriodEndHour = "00:00";
	private static String defaultMaxClosureMinutes = "60";

	private static ObservableList<MarineAccessPeriod> marineAccessPeriodsBridge1 = FXCollections.observableArrayList();
	private static ObservableList<MarineAccessPeriod> marineAccessPeriodsBridge2 = FXCollections.observableArrayList();
	private static ObservableList<String> highUsageHourValues =  FXCollections.observableArrayList("00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
			"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00");
	private static ObservableList<String> inCircuitPermissibleDelayValues =  FXCollections.observableArrayList("0", "5", "10");
	private static ObservableList<String> maxClosureDurationMinutesValues =  FXCollections.observableArrayList("30", "60", "90", "120");

	@FXML private CheckBox includeSummaryResultsOnSpreadsheetCheckBox;
	@FXML private CheckBox includeViolationsOnClosureSheetCheckBox;
	@FXML private CheckBox includeSummaryResultsOnNotepadCheckBox;
	@FXML private CheckBox includeConfidentialityDisclaimerCheckBox;
	@FXML private CheckBox disableCycleOrderCheckBox;
	@FXML private CheckBox applyAbsurdValueCheckBox;
	
	@FXML private ComboBox<Integer> applyAbusrdValueComboBox;
	
	// Bridge 1
	private static String inCircuitPermissibleDelayBridge1;
	private static String marineAccessPeriodStartHourBridge1;
	private static String marineAccessPeriodEndHourBridge1;
	private static String maxClosureMinutesBridge1;
	private static SimpleStringProperty bridge1Name = new SimpleStringProperty();

	private static String defaultBridge1Name = "Bridge 1";

	private static Double marineAccessPeriodSpanBridge1 = 0.0;

	private static SimpleBooleanProperty bridge1Enabled = new SimpleBooleanProperty();
	private static Boolean validMarinePeriodsBridge1 = false;
	private static Boolean computeMarineHighUsagePeriodActiveBridge1;

	@FXML private Button clearRegistryBridge1Button;
	@FXML private Button addPeriodBridge1Button;
	@FXML private Button deletePeriodBridge1Button;
	@FXML private Button saveMarineAccessPeriodsToRegistryBridge1Button;
	@FXML private Button saveToFileBridge1Button;
	@FXML private Button loadFromFileBridge1Button;
	@FXML private Button updateBridgeName1Button;

	@FXML private RadioButton viewEntriesOnlyBridge1RadioButton;
	@FXML private RadioButton viewAndEditEntriesBridge1RadioButton;

	@FXML private TableColumn<MarineAccessPeriod, Double> marinePeriodStartDoubleTable1;
	@FXML private TableColumn<MarineAccessPeriod, String> marinePeriodStartTimeTable1;
	@FXML private TableColumn<MarineAccessPeriod, Double> marinePeriodEndDoubleTable1;
	@FXML private TableColumn<MarineAccessPeriod, String> marinePeriodEndTimeTable1;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> moTable1;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> tuTable1;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> weTable1;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> thTable1;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> frTable1;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> saTable1;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> suTable1;

	@FXML private Tab bridgeTab1;
	@FXML private ComboBox<String> inCircuitDelayBridge1ComboBox;
	@FXML private ComboBox<String> startHighUsageHourBridge1ComboBox;
	@FXML private ComboBox<String> endHighUsageHourBridge1ComboBox;
	@FXML private ComboBox<String> maxClosureMinutesBridge1ComboBox;
	@FXML private TableView<MarineAccessPeriod> marineAccessPeriodsBridge1Table;
	@FXML private Label highUsageMarinePeriodSpanBridge1Label;
	@FXML private CheckBox computeMarineHighUsagePeriods1CheckBox;
	@FXML private CheckBox enableBridge1CheckBox;

	@FXML private TextField bridge1NameTextField;

	// Bridge 2
	private static String inCircuitPermissibleDelayBridge2;
	private static String marineAccessPeriodStartHourBridge2;
	private static String marineAccessPeriodEndHourBridge2;
	private static String maxClosureMinutesBridge2;
	private static SimpleStringProperty bridge2Name = new SimpleStringProperty();

	private static String defaultBridge2Name = "Bridge 2";

	private static Double marineAccessPeriodSpanBridge2 = 0.0;

	private static SimpleBooleanProperty bridge2Enabled = new SimpleBooleanProperty();
	private static Boolean validMarinePeriodsBridge2 = false;
	private static Boolean computeMarineHighUsagePeriodActiveBridge2;

	@FXML private Button clearRegistryBridge2Button;
	@FXML private Button addPeriodBridge2Button;
	@FXML private Button deletePeriodBridge2Button;
	@FXML private Button saveMarineAccessPeriodsToRegistryBridge2Button;
	@FXML private Button saveToFileBridge2Button;
	@FXML private Button loadFromFileBridge2Button;
	@FXML private Button updateBridgeName2Button;

	@FXML private RadioButton viewEntriesOnlyBridge2RadioButton;
	@FXML private RadioButton viewAndEditEntriesBridge2RadioButton;

	@FXML private TableColumn<MarineAccessPeriod, Double> marinePeriodStartDoubleTable2;
	@FXML private TableColumn<MarineAccessPeriod, String> marinePeriodStartTimeTable2;
	@FXML private TableColumn<MarineAccessPeriod, Double> marinePeriodEndDoubleTable2;
	@FXML private TableColumn<MarineAccessPeriod, String> marinePeriodEndTimeTable2;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> moTable2;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> tuTable2;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> weTable2;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> thTable2;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> frTable2;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> saTable2;
	@FXML private TableColumn<MarineAccessPeriod, Boolean> suTable2;

	@FXML private Tab bridgeTab2;
	@FXML private ComboBox<String> inCircuitDelayBridge2ComboBox;
	@FXML private ComboBox<String> startHighUsageHourBridge2ComboBox;
	@FXML private ComboBox<String> endHighUsageHourBridge2ComboBox;
	@FXML private ComboBox<String> maxClosureMinutesBridge2ComboBox;
	@FXML private TableView<MarineAccessPeriod> marineAccessPeriodsBridge2Table;
	@FXML private Label highUsageMarinePeriodSpanBridge2Label;
	@FXML private CheckBox computeMarineHighUsagePeriods2CheckBox;
	@FXML private CheckBox enableBridge2CheckBox;

	@FXML private TextField bridge2NameTextField;

	@FXML private void initialize() 
	{
		// Set tabs to bridge names found in main controller class
		if (BIASUscgBridgeComplianceAnalysisController.getBridgeNames().size() == 1)
		{
			bridgeTab1.setText(BIASUscgBridgeComplianceAnalysisController.getBridgeNames().get(0).getValue());
		}
		else if (BIASUscgBridgeComplianceAnalysisController.getBridgeNames().size() == 2)
		{
			bridgeTab1.setText(BIASUscgBridgeComplianceAnalysisController.getBridgeNames().get(0).getValue());
			bridgeTab2.setText(BIASUscgBridgeComplianceAnalysisController.getBridgeNames().get(1).getValue());
		}

		bridge1Enabled.setValue(false);
		bridge2Enabled.setValue(false);

		marinePeriodStartDoubleTable1.setStyle( "-fx-alignment: CENTER;");
		marinePeriodStartTimeTable1.setStyle( "-fx-alignment: CENTER;");
		marinePeriodEndDoubleTable1.setStyle( "-fx-alignment: CENTER;");
		marinePeriodEndTimeTable1.setStyle( "-fx-alignment: CENTER;");

		marinePeriodStartDoubleTable2.setStyle( "-fx-alignment: CENTER;");
		marinePeriodStartTimeTable2.setStyle( "-fx-alignment: CENTER;");
		marinePeriodEndDoubleTable2.setStyle( "-fx-alignment: CENTER;");
		marinePeriodEndTimeTable2.setStyle( "-fx-alignment: CENTER;");

		String marineAccessPeriodBridge1 = null;
		String marineAccessPeriodBridge2 = null;

		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");

		// Common prefs
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

		// See if cycle #s should be checked for ordering 
		if (prefs.getBoolean("cg_disableCheckingCycleOrdering", defaultDisableCheckingCycleOrder))
		{
			disableCheckingCycleOrder = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_disableCheckingCycleOrdering", true);
			disableCycleOrderCheckBox.setSelected(true);
		}
		else
		{
			disableCheckingCycleOrder = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_disableCheckingCycleOrdering", false);
			disableCycleOrderCheckBox.setSelected(false);
		}

		// See if bridge 1 name is stored
		boolean bridgeName1Exists = ((prefs.get("cg_bridge1Name", null) != null) & (prefs.get("cg_bridge1Name", null) != ""));
		if (bridgeName1Exists)
		{
			bridge1Name.setValue(prefs.get("cg_bridge1Name", defaultBridge1Name));
			bridge1NameTextField.setText(bridge1Name.getValue());
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("cg_bridge1Name", defaultBridge1Name);
			}
			bridge1NameTextField.setText(defaultBridge1Name);
		}

		// See if bridge 2 name is stored
		boolean bridgeName2Exists = ((prefs.get("cg_bridge2Name", null) != null) & (prefs.get("cg_bridge2Name", null) != ""));
		if (bridgeName2Exists)
		{
			bridge2Name.setValue(prefs.get("cg_bridge2Name", defaultBridge2Name));
			bridge2NameTextField.setText(bridge2Name.getValue());
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("cg_bridge2Name", defaultBridge2Name);
			}
			bridge2NameTextField.setText(defaultBridge2Name);
		}

		// See if bridge 1 enable is stored
		if (prefs.getBoolean("cg_bridge1Enabled", defaultBridgeEnabled))
		{
			bridge1Enabled.setValue(true);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_bridge1Enabled", true);
			enableBridge1CheckBox.setSelected(true);
			bridge1NameTextField.setDisable(false);
			updateBridgeName1Button.setDisable(false);
		}
		else
		{
			bridge1Enabled.setValue(false);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_bridge1Enabled", false);
			enableBridge1CheckBox.setSelected(false);
			bridge1NameTextField.setDisable(true);
			updateBridgeName1Button.setDisable(true);
		}

		// See if bridge 2 enable is stored
		if (prefs.getBoolean("cg_bridge2Enabled", defaultBridgeEnabled))
		{
			bridge2Enabled.setValue(true);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_bridge2Enabled", true);
			enableBridge2CheckBox.setSelected(true);
			bridge2NameTextField.setDisable(false);
			updateBridgeName2Button.setDisable(false);
		}
		else
		{
			bridge2Enabled.setValue(false);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_bridge2Enabled", false);
			enableBridge2CheckBox.setSelected(false);
			bridge2NameTextField.setDisable(true);
			updateBridgeName2Button.setDisable(true);
		}

		// See if preference is stored for computing marine high-usage periods
		// Bridge 1
		if (prefs.getBoolean("cg_computeMarineHighUsagePeriodActiveBridge1", defaultComputeMarineHighUsagePeriodActive))
		{
			computeMarineHighUsagePeriodActiveBridge1 = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActiveBridge1", true);
			computeMarineHighUsagePeriods1CheckBox.setSelected(true);

			startHighUsageHourBridge1ComboBox.setDisable(false);
			endHighUsageHourBridge1ComboBox.setDisable(false);
		}
		else
		{
			computeMarineHighUsagePeriodActiveBridge1 = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActiveBridge1", false);
			computeMarineHighUsagePeriods1CheckBox.setSelected(false);

			startHighUsageHourBridge1ComboBox.setDisable(true);
			endHighUsageHourBridge1ComboBox.setDisable(true);
		}

		// Bridge 2
		if (prefs.getBoolean("cg_computeMarineHighUsagePeriodActiveBridge2", defaultComputeMarineHighUsagePeriodActive))
		{
			computeMarineHighUsagePeriodActiveBridge2 = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActiveBridge2", true);
			computeMarineHighUsagePeriods2CheckBox.setSelected(true);

			startHighUsageHourBridge2ComboBox.setDisable(false);
			endHighUsageHourBridge2ComboBox.setDisable(false);
		}
		else
		{
			computeMarineHighUsagePeriodActiveBridge2 = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActiveBridge2", false);
			computeMarineHighUsagePeriods2CheckBox.setSelected(false);

			startHighUsageHourBridge2ComboBox.setDisable(true);
			endHighUsageHourBridge2ComboBox.setDisable(true);
		}

		// See if marine access periods are already defined
		// Bridge 1
		if (prefs.get("cg_marineAccessPeriodsBridge1", null) != null)
		{
			validMarinePeriodsBridge1 = true;

			String[] periods = prefs.get("cg_marineAccessPeriodsBridge1", marineAccessPeriodBridge1).split(":");
			for (int i = 0; i < periods.length; i++)
			{
				String[] values = periods[i].replace("[", "").replace("]", "").split(",");
				marineAccessPeriodsBridge1.add(new MarineAccessPeriod(Double.valueOf(values[0]), 
						Double.valueOf(values[1]), 
						Boolean.valueOf(values[2]), 
						Boolean.valueOf(values[3]),
						Boolean.valueOf(values[4]),
						Boolean.valueOf(values[5]),
						Boolean.valueOf(values[6]),
						Boolean.valueOf(values[7]),
						Boolean.valueOf(values[8])));
			}
			marineAccessPeriodsBridge1Table.setItems(marineAccessPeriodsBridge1);	
		}

		// Bridge 2
		if (prefs.get("cg_marineAccessPeriodsBridge2", null) != null)
		{
			validMarinePeriodsBridge2 = true;

			String[] periods = prefs.get("cg_marineAccessPeriodsBridge2", marineAccessPeriodBridge2).split(":");
			for (int i = 0; i < periods.length; i++)
			{
				String[] values = periods[i].replace("[", "").replace("]", "").split(",");
				marineAccessPeriodsBridge2.add(new MarineAccessPeriod(Double.valueOf(values[0]), 
						Double.valueOf(values[1]), 
						Boolean.valueOf(values[2]), 
						Boolean.valueOf(values[3]),
						Boolean.valueOf(values[4]),
						Boolean.valueOf(values[5]),
						Boolean.valueOf(values[6]),
						Boolean.valueOf(values[7]),
						Boolean.valueOf(values[8])));
			}
			marineAccessPeriodsBridge2Table.setItems(marineAccessPeriodsBridge2);	
		}

		// See if preference is stored for max closure duration bridge 1
		maxClosureMinutesBridge1ComboBox.setItems(maxClosureDurationMinutesValues);

		boolean maxClosureMinutesExistsBridge1 = prefs.get("cg_maxClosureMinutesBridge1", null) != null;
		if (maxClosureMinutesExistsBridge1)
		{
			maxClosureMinutesBridge1ComboBox.getSelectionModel().select(prefs.get("cg_maxClosureMinutesBridge1", defaultMaxClosureMinutes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_maxClosureMinutesBridge1", defaultMaxClosureMinutes);
			maxClosureMinutesBridge1ComboBox.getSelectionModel().select(defaultMaxClosureMinutes);
		}
		maxClosureMinutesBridge1 = prefs.get("cg_maxClosureMinutesBridge1", defaultMaxClosureMinutes);

		// See if preference is stored for max closure duration bridge 2
		maxClosureMinutesBridge2ComboBox.setItems(maxClosureDurationMinutesValues);

		boolean maxClosureMinutesExistsBridge2 = prefs.get("cg_maxClosureMinutesBridge2", null) != null;
		if (maxClosureMinutesExistsBridge2)
		{
			maxClosureMinutesBridge2ComboBox.getSelectionModel().select(prefs.get("cg_maxClosureMinutesBridge2", defaultMaxClosureMinutes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_maxClosureMinutesBridge2", defaultMaxClosureMinutes);
			maxClosureMinutesBridge2ComboBox.getSelectionModel().select(defaultMaxClosureMinutes);
		}
		maxClosureMinutesBridge2 = prefs.get("cg_maxClosureMinutesBridge2", defaultMaxClosureMinutes);

		// Marine periods 
		// Bridge 1
		marinePeriodStartDoubleTable1.setCellValueFactory(new PropertyValueFactory<MarineAccessPeriod, Double>("marinePeriodStartDouble"));
		marinePeriodStartDoubleTable1.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
		marinePeriodStartTimeTable1.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,String>,ObservableValue<String>>(){
					@Override public
					ObservableValue<String> call( CellDataFeatures<MarineAccessPeriod,String> p ){
						return p.getValue().getMarinePeriodStartTime(); }});

		marinePeriodEndDoubleTable1.setCellValueFactory(new PropertyValueFactory<MarineAccessPeriod, Double>("marinePeriodEndDouble"));
		marinePeriodEndDoubleTable1.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
		marinePeriodEndTimeTable1.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,String>,ObservableValue<String>>(){
					@Override public
					ObservableValue<String> call( CellDataFeatures<MarineAccessPeriod,String> p ){
						return p.getValue().getMarinePeriodEndTime(); }});

		moTable1.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getMo(); }});
		moTable1.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		tuTable1.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getTu(); }});
		tuTable1.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		weTable1.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getWe(); }});
		weTable1.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		thTable1.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getTh(); }});
		thTable1.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		frTable1.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getFr(); }});
		frTable1.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		saTable1.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getSa(); }});
		saTable1.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		suTable1.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getSu(); }});
		suTable1.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		marinePeriodStartDoubleTable1.setReorderable(false);
		marinePeriodStartTimeTable1.setReorderable(false);
		marinePeriodEndDoubleTable1.setReorderable(false);
		marinePeriodEndTimeTable1.setReorderable(false);
		moTable1.setReorderable(false);
		tuTable1.setReorderable(false);
		weTable1.setReorderable(false);
		thTable1.setReorderable(false);
		frTable1.setReorderable(false);
		saTable1.setReorderable(false);
		suTable1.setReorderable(false);

		// Bridge 2
		marinePeriodStartDoubleTable2.setCellValueFactory(new PropertyValueFactory<MarineAccessPeriod, Double>("marinePeriodStartDouble"));
		marinePeriodStartDoubleTable2.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
		marinePeriodStartTimeTable2.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,String>,ObservableValue<String>>(){
					@Override public
					ObservableValue<String> call( CellDataFeatures<MarineAccessPeriod,String> p ){
						return p.getValue().getMarinePeriodStartTime(); }});

		marinePeriodEndDoubleTable2.setCellValueFactory(new PropertyValueFactory<MarineAccessPeriod, Double>("marinePeriodEndDouble"));
		marinePeriodEndDoubleTable2.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
		marinePeriodEndTimeTable2.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,String>,ObservableValue<String>>(){
					@Override public
					ObservableValue<String> call( CellDataFeatures<MarineAccessPeriod,String> p ){
						return p.getValue().getMarinePeriodEndTime(); }});

		moTable2.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getMo(); }});
		moTable2.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		tuTable2.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getTu(); }});
		tuTable2.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		weTable2.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getWe(); }});
		weTable2.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		thTable2.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getTh(); }});
		thTable2.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		frTable2.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getFr(); }});
		frTable2.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		saTable2.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getSa(); }});
		saTable2.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		suTable2.setCellValueFactory(
				new Callback<CellDataFeatures<MarineAccessPeriod,Boolean>,ObservableValue<Boolean>>(){
					@Override public
					ObservableValue<Boolean> call( CellDataFeatures<MarineAccessPeriod,Boolean> p ){
						return p.getValue().getSu(); }});
		suTable2.setCellFactory(
				new Callback<TableColumn<MarineAccessPeriod,Boolean>,TableCell<MarineAccessPeriod,Boolean>>(){
					@Override public
					TableCell<MarineAccessPeriod,Boolean> call( TableColumn<MarineAccessPeriod,Boolean> p ){
						return new CheckBoxTableCell<>(); }});	

		marinePeriodStartDoubleTable2.setReorderable(false);
		marinePeriodStartTimeTable2.setReorderable(false);
		marinePeriodEndDoubleTable2.setReorderable(false);
		marinePeriodEndTimeTable2.setReorderable(false);
		moTable2.setReorderable(false);
		tuTable2.setReorderable(false);
		weTable2.setReorderable(false);
		thTable2.setReorderable(false);
		frTable2.setReorderable(false);
		saTable2.setReorderable(false);
		suTable2.setReorderable(false);

		// See if "in-circuit" permissible delay values are stored for bridge 1
		inCircuitDelayBridge1ComboBox.setItems(inCircuitPermissibleDelayValues);

		boolean inCircuitDelayValueExistsBridge1 = prefs.get("cg_inCircuitPermissibleDelayBridge1", null) != null;
		if (inCircuitDelayValueExistsBridge1)
		{
			inCircuitDelayBridge1ComboBox.getSelectionModel().select(prefs.get("cg_inCircuitPermissibleDelayBridge1", defaultInCircuitPermissibleDelay));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_inCircuitPermissibleDelayBridge1", defaultInCircuitPermissibleDelay);
			inCircuitDelayBridge1ComboBox.getSelectionModel().select(defaultInCircuitPermissibleDelay);
		}
		inCircuitPermissibleDelayBridge1 = prefs.get("cg_inCircuitPermissibleDelayBridge1", defaultInCircuitPermissibleDelay);

		// See if "in-circuit" permissible delay values are stored for bridge 2
		inCircuitDelayBridge2ComboBox.setItems(inCircuitPermissibleDelayValues);

		boolean inCircuitDelayValueExistsBridge2 = prefs.get("cg_inCircuitPermissibleDelayBridge2", null) != null;
		if (inCircuitDelayValueExistsBridge2)
		{
			inCircuitDelayBridge2ComboBox.getSelectionModel().select(prefs.get("cg_inCircuitPermissibleDelayBridge2", defaultInCircuitPermissibleDelay));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_inCircuitPermissibleDelayBridge2", defaultInCircuitPermissibleDelay);
			inCircuitDelayBridge2ComboBox.getSelectionModel().select(defaultInCircuitPermissibleDelay);
		}
		inCircuitPermissibleDelayBridge2 = prefs.get("cg_inCircuitPermissibleDelayBridge2", defaultInCircuitPermissibleDelay);

		// See if recurring marine access period start values are stored
		startHighUsageHourBridge1ComboBox.setItems(highUsageHourValues);
		startHighUsageHourBridge2ComboBox.setItems(highUsageHourValues);

		// See if recurring marine access period end values are stored
		endHighUsageHourBridge1ComboBox.setItems(highUsageHourValues);
		endHighUsageHourBridge2ComboBox.setItems(highUsageHourValues);

		// Bridge 1
		boolean marineAccessPeriodStartHourValueExistsBridge1 = prefs.get("cg_marineAccessPeriodStartHourBridge1", null) != null;
		if (marineAccessPeriodStartHourValueExistsBridge1)
		{
			startHighUsageHourBridge1ComboBox.getSelectionModel().select(prefs.get("cg_marineAccessPeriodStartHourBridge1", defaultMarineAcessPeriodStartHour));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_marineAccessPeriodStartHourBridge1", defaultMarineAcessPeriodStartHour);
			startHighUsageHourBridge1ComboBox.getSelectionModel().select(defaultMarineAcessPeriodStartHour);
		}
		marineAccessPeriodStartHourBridge1 = prefs.get("cg_marineAccessPeriodStartHourBridge1", defaultMarineAcessPeriodStartHour);

		boolean marineAccessPeriodEndHourValueExistsBridge1 = prefs.get("cg_marineAccessPeriodEndHourBridge1", null) != null;
		if (marineAccessPeriodEndHourValueExistsBridge1)
		{
			endHighUsageHourBridge1ComboBox.getSelectionModel().select(prefs.get("cg_marineAccessPeriodEndHourBridge1", defaultMarineAcessPeriodEndHour));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_marineAccessPeriodEndHourBridge1", defaultMarineAcessPeriodEndHour);
			endHighUsageHourBridge1ComboBox.getSelectionModel().select(defaultMarineAcessPeriodEndHour);
		}
		marineAccessPeriodEndHourBridge1 = prefs.get("cg_marineAccessPeriodEndHourBridge1", defaultMarineAcessPeriodEndHour);

		figureMarinePeriodSpanBridge1AsDouble(marineAccessPeriodStartHourBridge1, marineAccessPeriodEndHourBridge1);
		highUsageMarinePeriodSpanBridge1Label.setText(getMarinePeriodSpanBridge1AsString());

		// Bridge 2
		boolean marineAccessPeriodStartHourValueExistsBridge2 = prefs.get("cg_marineAccessPeriodStartHourBridge2", null) != null;
		if (marineAccessPeriodStartHourValueExistsBridge2)
		{
			startHighUsageHourBridge2ComboBox.getSelectionModel().select(prefs.get("cg_marineAccessPeriodStartHourBridge2", defaultMarineAcessPeriodStartHour));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_marineAccessPeriodStartHourBridge2", defaultMarineAcessPeriodStartHour);
			startHighUsageHourBridge2ComboBox.getSelectionModel().select(defaultMarineAcessPeriodStartHour);
		}
		marineAccessPeriodStartHourBridge2 = prefs.get("cg_marineAccessPeriodStartHourBridge2", defaultMarineAcessPeriodStartHour);

		boolean marineAccessPeriodEndHourValueExistsBridge2 = prefs.get("cg_marineAccessPeriodEndHourBridge2", null) != null;
		if (marineAccessPeriodEndHourValueExistsBridge2)
		{
			endHighUsageHourBridge2ComboBox.getSelectionModel().select(prefs.get("cg_marineAccessPeriodEndHourBridge2", defaultMarineAcessPeriodEndHour));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_marineAccessPeriodEndHourBridge2", defaultMarineAcessPeriodEndHour);
			endHighUsageHourBridge2ComboBox.getSelectionModel().select(defaultMarineAcessPeriodEndHour);
		}
		marineAccessPeriodEndHourBridge2 = prefs.get("cg_marineAccessPeriodEndHourBridge2", defaultMarineAcessPeriodEndHour);

		figureMarinePeriodSpanBridge2AsDouble(marineAccessPeriodStartHourBridge2, marineAccessPeriodEndHourBridge2);
		highUsageMarinePeriodSpanBridge2Label.setText(getMarinePeriodSpanBridge2AsString());

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

	@FXML private void handleDisableCycleOrderCheckBox(ActionEvent event)
	{
		if (disableCheckingCycleOrder)
		{
			disableCheckingCycleOrder = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_disableCheckingCycleOrdering", false);
		}
		else
		{
			disableCheckingCycleOrder = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_disableCheckingCycleOrdering", true);
		}
	}

	@FXML private void handleViewEntriesOnlyBridge1RadioButton(ActionEvent event) 
	{
		clearRegistryBridge1Button.setDisable(true);
		saveMarineAccessPeriodsToRegistryBridge1Button.setDisable(true);
		addPeriodBridge1Button.setDisable(true);
		deletePeriodBridge1Button.setDisable(true);
		saveToFileBridge1Button.setDisable(true);
		loadFromFileBridge1Button.setDisable(true);

		marineAccessPeriodsBridge1Table.setEditable(false);
		marineAccessPeriodsBridge1Table.refresh();

		marinePeriodStartDoubleTable1.setEditable(false);
		marinePeriodEndDoubleTable1.setEditable(false);

		moTable1.setEditable(false);
		tuTable1.setEditable(false);
		weTable1.setEditable(false);
		thTable1.setEditable(false);
		frTable1.setEditable(false);
		saTable1.setEditable(false);
		suTable1.setEditable(false);
	}

	@FXML private void handleViewEntriesOnlyBridge2RadioButton(ActionEvent event) 
	{
		clearRegistryBridge2Button.setDisable(true);
		saveMarineAccessPeriodsToRegistryBridge2Button.setDisable(true);
		addPeriodBridge2Button.setDisable(true);
		deletePeriodBridge2Button.setDisable(true);
		saveToFileBridge2Button.setDisable(true);
		loadFromFileBridge2Button.setDisable(true);

		marineAccessPeriodsBridge2Table.setEditable(false);
		marineAccessPeriodsBridge2Table.refresh();

		marinePeriodStartDoubleTable2.setEditable(false);
		marinePeriodEndDoubleTable2.setEditable(false);

		moTable2.setEditable(false);
		tuTable2.setEditable(false);
		weTable2.setEditable(false);
		thTable2.setEditable(false);
		frTable2.setEditable(false);
		saTable2.setEditable(false);
		suTable2.setEditable(false);
	}

	@FXML private void handleViewAndEditEntriesBridge1RadioButton(ActionEvent event) 
	{
		addPeriodBridge1Button.setDisable(false);
		loadFromFileBridge1Button.setDisable(false);

		if (validMarinePeriodsBridge1)
		{
			deletePeriodBridge1Button.setDisable(false);

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("false"))
			{
				clearRegistryBridge1Button.setDisable(true);
			}
			else
			{
				clearRegistryBridge1Button.setDisable(false);
			}

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				saveMarineAccessPeriodsToRegistryBridge1Button.setDisable(false);
				saveToFileBridge1Button.setDisable(false);
			}
		}
		else
		{
			clearRegistryBridge1Button.setDisable(true);
			saveMarineAccessPeriodsToRegistryBridge1Button.setDisable(true);
			saveToFileBridge1Button.setDisable(true);
		}

		marineAccessPeriodsBridge1Table.setEditable(true);

		marinePeriodStartDoubleTable1.setEditable(true);
		marinePeriodStartDoubleTable1.setOnEditCommit(new EventHandler<CellEditEvent<MarineAccessPeriod, Double>>() {      
			@Override
			public void handle(CellEditEvent<MarineAccessPeriod, Double> t) {
				if (t.getNewValue() != null)
				{
					int row = t.getTableView().getEditingCell().getRow();
					Double newValue = t.getNewValue();

					if (newValue >= marineAccessPeriodsBridge1.get(row).getMarinePeriodEndDouble())
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
						marineAccessPeriodsBridge1.get(row).setMarinePeriodStartDouble(newValue);
					}
				}

				marineAccessPeriodsBridge1Table.refresh();
			}
		});

		marinePeriodEndDoubleTable1.setEditable(true);
		marinePeriodEndDoubleTable1.setOnEditCommit(new EventHandler<CellEditEvent<MarineAccessPeriod, Double>>() {      
			@Override
			public void handle(CellEditEvent<MarineAccessPeriod, Double> t) {
				if (t.getNewValue() != null)
				{
					int row = t.getTableView().getEditingCell().getRow();
					Double newValue = t.getNewValue();

					if (newValue <= marineAccessPeriodsBridge1.get(row).getMarinePeriodStartDouble())
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
						marineAccessPeriodsBridge1.get(row).setMarinePeriodEndDouble(newValue);
					}
				}

				marineAccessPeriodsBridge1Table.refresh();
			}
		});

		moTable1.setEditable(true);
		tuTable1.setEditable(true);
		weTable1.setEditable(true);
		thTable1.setEditable(true);
		frTable1.setEditable(true);
		saTable1.setEditable(true);
		suTable1.setEditable(true);
	}

	@FXML private void handleViewAndEditEntriesBridge2RadioButton(ActionEvent event) 
	{
		addPeriodBridge2Button.setDisable(false);
		loadFromFileBridge2Button.setDisable(false);

		if (validMarinePeriodsBridge2)
		{
			deletePeriodBridge2Button.setDisable(false);

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("false"))
			{
				clearRegistryBridge2Button.setDisable(true);
			}
			else
			{
				clearRegistryBridge2Button.setDisable(false);
			}

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				saveMarineAccessPeriodsToRegistryBridge2Button.setDisable(false);
				saveToFileBridge2Button.setDisable(false);
			}
		}
		else
		{
			clearRegistryBridge2Button.setDisable(true);
			saveMarineAccessPeriodsToRegistryBridge2Button.setDisable(true);
			saveToFileBridge2Button.setDisable(true);
		}

		marineAccessPeriodsBridge2Table.setEditable(true);

		marinePeriodStartDoubleTable2.setEditable(true);
		marinePeriodStartDoubleTable2.setOnEditCommit(new EventHandler<CellEditEvent<MarineAccessPeriod, Double>>() {      
			@Override
			public void handle(CellEditEvent<MarineAccessPeriod, Double> t) {
				if (t.getNewValue() != null)
				{
					int row = t.getTableView().getEditingCell().getRow();
					Double newValue = t.getNewValue();

					if (newValue >= marineAccessPeriodsBridge2.get(row).getMarinePeriodEndDouble())
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
						marineAccessPeriodsBridge2.get(row).setMarinePeriodStartDouble(newValue);
					}
				}

				marineAccessPeriodsBridge2Table.refresh();
			}
		});

		marinePeriodEndDoubleTable2.setEditable(true);
		marinePeriodEndDoubleTable2.setOnEditCommit(new EventHandler<CellEditEvent<MarineAccessPeriod, Double>>() {      
			@Override
			public void handle(CellEditEvent<MarineAccessPeriod, Double> t) {
				if (t.getNewValue() != null)
				{
					int row = t.getTableView().getEditingCell().getRow();
					Double newValue = t.getNewValue();

					if (newValue <= marineAccessPeriodsBridge2.get(row).getMarinePeriodStartDouble())
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
						marineAccessPeriodsBridge2.get(row).setMarinePeriodEndDouble(newValue);
					}
				}

				marineAccessPeriodsBridge2Table.refresh();
			}
		});

		moTable2.setEditable(true);
		tuTable2.setEditable(true);
		weTable2.setEditable(true);
		thTable2.setEditable(true);
		frTable2.setEditable(true);
		saTable2.setEditable(true);
		suTable2.setEditable(true);
	}

	@FXML private void handleClearRegistryBridge1Button(ActionEvent event)
	{
		if (prefs.get("cg_marineAccessPeriodsBridge1", null) != null)
		{
			prefs.remove("cg_marineAccessPeriodsBridge1");
			clearRegistryBridge1Button.setDisable(true);
			saveMarineAccessPeriodsToRegistryBridge1Button.setDisable(true);

			if (marineAccessPeriodsBridge1Table.getItems().size() == 0)
			{
				deletePeriodBridge1Button.setDisable(true);
			}
		}
	}

	@FXML private void handleAddPeriodBridge1Button(ActionEvent event)
	{
		marineAccessPeriodsBridge1.add(new MarineAccessPeriod(0.00000,0.99999,false,false,false,false,false,false,false));
		marineAccessPeriodsBridge1Table.setItems(marineAccessPeriodsBridge1);
		validMarinePeriodsBridge1 = true;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			saveMarineAccessPeriodsToRegistryBridge1Button.setDisable(false);
		}

		if (marineAccessPeriodsBridge1Table.getItems().size() > 0)
			deletePeriodBridge1Button.setDisable(false);
	}

	@FXML private void handleDeletePeriodBridge1Button(ActionEvent event)
	{	
		MarineAccessPeriod selectedPeriod = marineAccessPeriodsBridge1Table.getSelectionModel().getSelectedItem();
		marineAccessPeriodsBridge1Table.getItems().remove(selectedPeriod);
		if (marineAccessPeriodsBridge1Table.getItems().size() == 0)
		{
			validMarinePeriodsBridge1 = false;
			deletePeriodBridge1Button.setDisable(true);
			saveMarineAccessPeriodsToRegistryBridge1Button.setDisable(true);
		}
	}

	@FXML private void handleSaveToFileBridge1Button(ActionEvent event)
	{	
		savePeriodsToFileBridge1();
	}

	@FXML private void handleLoadFromFileBridge1Button(ActionEvent event) throws FileNotFoundException
	{	
		loadPeriodsFromFileBridge1();
	}

	@FXML private void handleSaveMarineAccessPeriodsToRegistryBridge1Button(ActionEvent event)
	{
		String registryEntry = "";
		for (int i = 0; i < marineAccessPeriodsBridge1.size(); i++)
		{	
			registryEntry+="[";

			registryEntry+=marineAccessPeriodsBridge1.get(i).getMarinePeriodStartDouble()+",";
			registryEntry+=marineAccessPeriodsBridge1.get(i).getMarinePeriodEndDouble()+",";
			registryEntry+=marineAccessPeriodsBridge1.get(i).getMo().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge1.get(i).getTu().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge1.get(i).getWe().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge1.get(i).getTh().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge1.get(i).getFr().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge1.get(i).getSa().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge1.get(i).getSu().getValue();

			if (i == (marineAccessPeriodsBridge1.size() - 1))
				registryEntry+="]";
			else
				registryEntry+="]:";
		}

		validMarinePeriodsBridge1 = true;

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.put("cg_marineAccessPeriodsBridge1", registryEntry);
			clearRegistryBridge1Button.setDisable(false);
		}
	}

	@FXML private void handleClearRegistryBridge2Button(ActionEvent event)
	{
		if (prefs.get("cg_marineAccessPeriodsBridge2", null) != null)
		{
			prefs.remove("cg_marineAccessPeriodsBridge2");
			clearRegistryBridge2Button.setDisable(true);
			saveMarineAccessPeriodsToRegistryBridge2Button.setDisable(true);

			if (marineAccessPeriodsBridge2Table.getItems().size() == 0)
			{
				deletePeriodBridge2Button.setDisable(true);
			}
		}
	}

	@FXML private void handleAddPeriodBridge2Button(ActionEvent event)
	{
		marineAccessPeriodsBridge2.add(new MarineAccessPeriod(0.00000,0.99999,false,false,false,false,false,false,false));
		marineAccessPeriodsBridge2Table.setItems(marineAccessPeriodsBridge2);
		validMarinePeriodsBridge2 = true;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			saveMarineAccessPeriodsToRegistryBridge2Button.setDisable(false);
		}

		if (marineAccessPeriodsBridge2Table.getItems().size() > 0)
			deletePeriodBridge2Button.setDisable(false);
	}

	@FXML private void handleDeletePeriodBridge2Button(ActionEvent event)
	{	
		MarineAccessPeriod selectedPeriod = marineAccessPeriodsBridge2Table.getSelectionModel().getSelectedItem();
		marineAccessPeriodsBridge2Table.getItems().remove(selectedPeriod);
		if (marineAccessPeriodsBridge2Table.getItems().size() == 0)
		{
			validMarinePeriodsBridge2 = false;
			deletePeriodBridge2Button.setDisable(true);
			saveMarineAccessPeriodsToRegistryBridge2Button.setDisable(true);
		}
	}

	@FXML private void handleSaveToFileBridge2Button(ActionEvent event)
	{	
		savePeriodsToFileBridge2();
	}

	@FXML private void handleLoadFromFileBridge2Button(ActionEvent event) throws FileNotFoundException
	{	
		loadPeriodsFromFileBridge2();
	}

	@FXML private void handleSaveMarineAccessPeriodsToRegistryBridge2Button(ActionEvent event)
	{
		String registryEntry = "";
		for (int i = 0; i < marineAccessPeriodsBridge2.size(); i++)
		{	
			registryEntry+="[";

			registryEntry+=marineAccessPeriodsBridge2.get(i).getMarinePeriodStartDouble()+",";
			registryEntry+=marineAccessPeriodsBridge2.get(i).getMarinePeriodEndDouble()+",";
			registryEntry+=marineAccessPeriodsBridge2.get(i).getMo().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge2.get(i).getTu().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge2.get(i).getWe().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge2.get(i).getTh().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge2.get(i).getFr().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge2.get(i).getSa().getValue()+",";
			registryEntry+=marineAccessPeriodsBridge2.get(i).getSu().getValue();

			if (i == (marineAccessPeriodsBridge2.size() - 1))
				registryEntry+="]";
			else
				registryEntry+="]:";
		}

		validMarinePeriodsBridge2 = true;

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.put("cg_marineAccessPeriodsBridge2", registryEntry);
			clearRegistryBridge2Button.setDisable(false);
		}
	}

	@FXML private void handleComputeMarineHighUsagePeriods1CheckBox()
	{
		if (computeMarineHighUsagePeriodActiveBridge1)
		{
			computeMarineHighUsagePeriodActiveBridge1 = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActiveBridge1", false);

			startHighUsageHourBridge1ComboBox.setDisable(true);
			endHighUsageHourBridge1ComboBox.setDisable(true);
		}
		else
		{
			computeMarineHighUsagePeriodActiveBridge1 = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActiveBridge1", true);

			startHighUsageHourBridge1ComboBox.setDisable(false);
			endHighUsageHourBridge1ComboBox.setDisable(false);
		}
	}

	@FXML private void handleComputeMarineHighUsagePeriods2CheckBox()
	{
		if (computeMarineHighUsagePeriodActiveBridge2)
		{
			computeMarineHighUsagePeriodActiveBridge2 = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActiveBridge2", false);

			startHighUsageHourBridge2ComboBox.setDisable(true);
			endHighUsageHourBridge2ComboBox.setDisable(true);
		}
		else
		{
			computeMarineHighUsagePeriodActiveBridge2 = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_computeMarineHighUsagePeriodActiveBridge2", true);

			startHighUsageHourBridge2ComboBox.setDisable(false);
			endHighUsageHourBridge2ComboBox.setDisable(false);
		}
	}

	@FXML private void handleInCircuitDelayBridge1ComboBox(ActionEvent event) 
	{
		inCircuitPermissibleDelayBridge1 = String.valueOf(inCircuitDelayBridge1ComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_inCircuitPermissibleDelayBridge1", inCircuitDelayBridge1ComboBox.getValue());
	}

	@FXML private void handleInCircuitDelayBridge2ComboBox(ActionEvent event) 
	{
		inCircuitPermissibleDelayBridge2 = String.valueOf(inCircuitDelayBridge2ComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_inCircuitPermissibleDelayBridge2", inCircuitDelayBridge2ComboBox.getValue());
	}

	@FXML private void handleStartHighUsageHourBridge1ComboBox(ActionEvent event) 
	{
		marineAccessPeriodStartHourBridge1 = String.valueOf(startHighUsageHourBridge1ComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_marineAccessPeriodStartHourBridge1", startHighUsageHourBridge1ComboBox.getValue());

		// Figure and display marine access period span
		figureMarinePeriodSpanBridge1AsDouble(marineAccessPeriodStartHourBridge1, marineAccessPeriodEndHourBridge1);
		highUsageMarinePeriodSpanBridge1Label.setText(getMarinePeriodSpanBridge1AsString());
	}

	@FXML private void handleStartHighUsageHourBridge2ComboBox(ActionEvent event) 
	{
		marineAccessPeriodStartHourBridge2 = String.valueOf(startHighUsageHourBridge2ComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_marineAccessPeriodStartHourBridge2", startHighUsageHourBridge2ComboBox.getValue());

		// Figure and display marine access period span
		figureMarinePeriodSpanBridge2AsDouble(marineAccessPeriodStartHourBridge2, marineAccessPeriodEndHourBridge2);
		highUsageMarinePeriodSpanBridge2Label.setText(getMarinePeriodSpanBridge2AsString());
	}

	@FXML private void handleEndHighUsageHourBridge1ComboBox(ActionEvent event) 
	{
		marineAccessPeriodEndHourBridge1 = String.valueOf(endHighUsageHourBridge1ComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_marineAccessPeriodEndHourBridge1", endHighUsageHourBridge1ComboBox.getValue());

		// Figure and display marine access period span
		figureMarinePeriodSpanBridge1AsDouble(marineAccessPeriodStartHourBridge1, marineAccessPeriodEndHourBridge1);
		highUsageMarinePeriodSpanBridge1Label.setText(getMarinePeriodSpanBridge1AsString());
	}

	@FXML private void handleEndHighUsageHourBridge2ComboBox(ActionEvent event) 
	{
		marineAccessPeriodEndHourBridge2 = String.valueOf(endHighUsageHourBridge2ComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_marineAccessPeriodEndHourBridge2", endHighUsageHourBridge2ComboBox.getValue());

		// Figure and display marine access period span
		figureMarinePeriodSpanBridge2AsDouble(marineAccessPeriodStartHourBridge2, marineAccessPeriodEndHourBridge2);
		highUsageMarinePeriodSpanBridge2Label.setText(getMarinePeriodSpanBridge2AsString());
	}

	@FXML private void handleMaxClosureMinutesBridge1ComboBox(ActionEvent event) 
	{
		maxClosureMinutesBridge1 = String.valueOf(maxClosureMinutesBridge1ComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_maxClosureMinutesBridge1", maxClosureMinutesBridge1ComboBox.getValue());
	}

	@FXML private void handleMaxClosureMinutesBridge2ComboBox(ActionEvent event) 
	{
		maxClosureMinutesBridge2= String.valueOf(maxClosureMinutesBridge2ComboBox.getValue());
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("cg_maxClosureMinutesBridge2", maxClosureMinutesBridge2ComboBox.getValue());
	}

	@FXML private void handleEnableBridge1CheckBox(ActionEvent event) 
	{
		if (bridge1Enabled.getValue())
		{
			bridge1Enabled.setValue(false);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_bridge1Enabled", false);

			bridge1NameTextField.setDisable(true);
			updateBridgeName1Button.setDisable(true);
		}
		else
		{
			bridge1Enabled.setValue(true);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_bridge1Enabled", true);

			bridge1NameTextField.setDisable(false);
			updateBridgeName1Button.setDisable(false);
		}
	}

	@FXML private void handleEnableBridge2CheckBox(ActionEvent event) 
	{
		if (bridge2Enabled.getValue())
		{
			bridge2Enabled.setValue(false);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_bridge2Enabled", false);

			bridge2NameTextField.setDisable(true);
			updateBridgeName2Button.setDisable(true);
		}
		else
		{
			bridge2Enabled.setValue(true);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("cg_bridge2Enabled", true);

			bridge2NameTextField.setDisable(false);
			updateBridgeName2Button.setDisable(false);
		}
	}

	@FXML private void handleUpdateBridgeName1Button()
	{
		bridge1NameTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		updateBridgeName1Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		if (!bridge1NameTextField.getText().trim().equals(""))
		{
			bridge1Name.setValue(bridge1NameTextField.getText().trim());
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_bridge1Name", bridge1NameTextField.getText().trim());
		}
		else
		{
			bridge1NameTextField.setText(bridge1Name.getValue());
		}
	}

	@FXML private void handleUpdateBridgeName2Button()
	{
		bridge2NameTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		updateBridgeName2Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		if (!bridge2NameTextField.getText().trim().equals(""))
		{
			bridge2Name.setValue(bridge2NameTextField.getText().trim());
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_bridge2Name", bridge2NameTextField.getText().trim());
		}
		else
		{
			bridge2NameTextField.setText(bridge2Name.getValue());
		}
	}

	@FXML private void handleBridge1NameTextField()
	{
		bridge1NameTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateBridgeName1Button.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}


	@FXML private void handleBridge2NameTextField()
	{
		bridge2NameTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateBridgeName2Button.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
	
	public static Boolean getDisableCheckingCycleOrder()
	{
		return disableCheckingCycleOrder;
	}

	public static Boolean getIncludeMarineHighUsagePeriodsBridge1()
	{
		return computeMarineHighUsagePeriodActiveBridge1;
	}

	public static Boolean getIncludeMarineHighUsagePeriodsBridge2()
	{
		return computeMarineHighUsagePeriodActiveBridge2;
	}

	public static String getInCircuitPermissibleDelayBridge1()
	{
		return inCircuitPermissibleDelayBridge1;
	}

	public static String getInCircuitPermissibleDelayBridge2()
	{
		return inCircuitPermissibleDelayBridge2;
	}

	public static String getMarineAccessPeriodStartHourBridge1()
	{
		return marineAccessPeriodStartHourBridge1;
	}

	public static String getMarineAccessPeriodStartHourBridge2()
	{
		return marineAccessPeriodStartHourBridge2;
	}

	public static String getMarineAccessPeriodEndHourBridge1()
	{
		return marineAccessPeriodEndHourBridge1;
	}

	public static String getMarineAccessPeriodEndHourBridge2()
	{
		return marineAccessPeriodEndHourBridge2;
	}

	public static String getMaxClosureMinutesBridge1()
	{
		return maxClosureMinutesBridge1;
	}

	public static String getMaxClosureMinutesBridge2()
	{
		return maxClosureMinutesBridge2;
	}

	public static ObservableList<MarineAccessPeriod> getMarineAccessPeriodsBridge1()
	{
		return marineAccessPeriodsBridge1;
	}

	public static ObservableList<MarineAccessPeriod> getMarineAccessPeriodsBridge2()
	{
		return marineAccessPeriodsBridge2;
	}

	public static SimpleBooleanProperty getBridge1Enabled()
	{
		return bridge1Enabled;
	}

	public static SimpleBooleanProperty getBridge2Enabled()
	{
		return bridge2Enabled;
	}

	public static SimpleStringProperty getBridge1Name()
	{
		return bridge1Name;
	}

	public static SimpleStringProperty getBridge2Name()
	{
		return bridge2Name;
	}

	private static Double figureMarinePeriodSpanBridge1AsDouble(String marineAccessPeriodStartHour, String marineAccessPeriodEndHour)
	{
		marineAccessPeriodStartHour = marineAccessPeriodStartHour.replace(":00", "").strip();
		marineAccessPeriodEndHour = marineAccessPeriodEndHour.replace(":00", "").strip();
		marineAccessPeriodSpanBridge1 = Double.valueOf(marineAccessPeriodEndHour) - Double.valueOf(marineAccessPeriodStartHour);
		if (marineAccessPeriodSpanBridge1 == 0.0)
			marineAccessPeriodSpanBridge1 = 24.0;
		else if (marineAccessPeriodSpanBridge1 < 0)
			marineAccessPeriodSpanBridge1 = marineAccessPeriodSpanBridge1 + 24.0;
		return marineAccessPeriodSpanBridge1;
	}

	private static Double figureMarinePeriodSpanBridge2AsDouble(String marineAccessPeriodStartHour, String marineAccessPeriodEndHour)
	{
		marineAccessPeriodStartHour = marineAccessPeriodStartHour.replace(":00", "").strip();
		marineAccessPeriodEndHour = marineAccessPeriodEndHour.replace(":00", "").strip();
		marineAccessPeriodSpanBridge2 = Double.valueOf(marineAccessPeriodEndHour) - Double.valueOf(marineAccessPeriodStartHour);
		if (marineAccessPeriodSpanBridge2 == 0.0)
			marineAccessPeriodSpanBridge2 = 24.0;
		else if (marineAccessPeriodSpanBridge2 < 0)
			marineAccessPeriodSpanBridge2 = marineAccessPeriodSpanBridge2 + 24.0;
		return marineAccessPeriodSpanBridge2;
	}

	public static Double getMarineAccessPeriodSpanBridge1AsDouble()
	{
		return marineAccessPeriodSpanBridge1;
	}

	public static Double getMarineAccessPeriodSpanBridge2AsDouble()
	{
		return marineAccessPeriodSpanBridge2;
	}

	private static String getMarinePeriodSpanBridge1AsString()
	{
		String marineAccessPeriodSpanAsString = String.valueOf(marineAccessPeriodSpanBridge1).replace(".0","");

		return marineAccessPeriodSpanAsString;
	}

	private static String getMarinePeriodSpanBridge2AsString()
	{
		String marineAccessPeriodSpanAsString = String.valueOf(marineAccessPeriodSpanBridge2).replace(".0","");

		return marineAccessPeriodSpanAsString;
	}

	public static Integer getMarinePeriodsPerWeekBridge1AsInteger()
	{
		int marineAccessPeriodsPerWeekBridge1AsInteger = 0;
		for (int i = 0; i < marineAccessPeriodsBridge1.size(); i++)
		{
			if (marineAccessPeriodsBridge1.get(i).getMo().getValue())
				marineAccessPeriodsPerWeekBridge1AsInteger++;
			if (marineAccessPeriodsBridge1.get(i).getTu().getValue())
				marineAccessPeriodsPerWeekBridge1AsInteger++;
			if (marineAccessPeriodsBridge1.get(i).getWe().getValue())
				marineAccessPeriodsPerWeekBridge1AsInteger++;
			if (marineAccessPeriodsBridge1.get(i).getTh().getValue())
				marineAccessPeriodsPerWeekBridge1AsInteger++;
			if (marineAccessPeriodsBridge1.get(i).getFr().getValue())
				marineAccessPeriodsPerWeekBridge1AsInteger++;
			if (marineAccessPeriodsBridge1.get(i).getSa().getValue())
				marineAccessPeriodsPerWeekBridge1AsInteger++;
			if (marineAccessPeriodsBridge1.get(i).getSu().getValue())
				marineAccessPeriodsPerWeekBridge1AsInteger++;
		}

		return marineAccessPeriodsPerWeekBridge1AsInteger;
	}

	public static Integer getMarinePeriodsPerWeekBridge2AsInteger()
	{
		int marineAccessPeriodsPerWeekBridge2AsInteger = 0;
		for (int i = 0; i < marineAccessPeriodsBridge2.size(); i++)
		{
			if (marineAccessPeriodsBridge2.get(i).getMo().getValue())
				marineAccessPeriodsPerWeekBridge2AsInteger++;
			if (marineAccessPeriodsBridge2.get(i).getTu().getValue())
				marineAccessPeriodsPerWeekBridge2AsInteger++;
			if (marineAccessPeriodsBridge2.get(i).getWe().getValue())
				marineAccessPeriodsPerWeekBridge2AsInteger++;
			if (marineAccessPeriodsBridge2.get(i).getTh().getValue())
				marineAccessPeriodsPerWeekBridge2AsInteger++;
			if (marineAccessPeriodsBridge2.get(i).getFr().getValue())
				marineAccessPeriodsPerWeekBridge2AsInteger++;
			if (marineAccessPeriodsBridge2.get(i).getSa().getValue())
				marineAccessPeriodsPerWeekBridge2AsInteger++;
			if (marineAccessPeriodsBridge2.get(i).getSu().getValue())
				marineAccessPeriodsPerWeekBridge2AsInteger++;
		}

		return marineAccessPeriodsPerWeekBridge2AsInteger;
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

	void savePeriodsToFileBridge1()
	{
		// Get location to save file to if not using system time as file name
		FileChooser fileChooser = new FileChooser();
		Stage stageForFolderChooser = (Stage) saveToFileBridge1Button.getScene().getWindow();
		fileChooser.setTitle("Select Location to Save Results");
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("Marine Access Periods (.mrp)", "*.mrp");
		fileChooser.getExtensionFilters().add(fileExtensions);

		File file = fileChooser.showSaveDialog(stageForFolderChooser);

		if (file != null) 
		{
			try 
			{
				String fileEntry = "";
				for (int i = 0; i < marineAccessPeriodsBridge1.size(); i++)
				{	
					fileEntry+="[";

					fileEntry+=marineAccessPeriodsBridge1.get(i).getMarinePeriodStartDouble()+",";
					fileEntry+=marineAccessPeriodsBridge1.get(i).getMarinePeriodEndDouble()+",";
					fileEntry+=marineAccessPeriodsBridge1.get(i).getMo().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge1.get(i).getTu().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge1.get(i).getWe().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge1.get(i).getTh().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge1.get(i).getFr().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge1.get(i).getSa().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge1.get(i).getSu().getValue();

					if (i == (marineAccessPeriodsBridge1.size() - 1))
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

	void savePeriodsToFileBridge2()
	{
		// Get location to save file to if not using system time as file name
		FileChooser fileChooser = new FileChooser();
		Stage stageForFolderChooser = (Stage) saveToFileBridge1Button.getScene().getWindow();
		fileChooser.setTitle("Select Location to Save Results");
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("Marine Access Periods (.mrp)", "*.mrp");
		fileChooser.getExtensionFilters().add(fileExtensions);

		File file = fileChooser.showSaveDialog(stageForFolderChooser);

		if (file != null) 
		{
			try 
			{
				String fileEntry = "";
				for (int i = 0; i < marineAccessPeriodsBridge2.size(); i++)
				{	
					fileEntry+="[";

					fileEntry+=marineAccessPeriodsBridge2.get(i).getMarinePeriodStartDouble()+",";
					fileEntry+=marineAccessPeriodsBridge2.get(i).getMarinePeriodEndDouble()+",";
					fileEntry+=marineAccessPeriodsBridge2.get(i).getMo().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge2.get(i).getTu().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge2.get(i).getWe().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge2.get(i).getTh().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge2.get(i).getFr().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge2.get(i).getSa().getValue()+",";
					fileEntry+=marineAccessPeriodsBridge2.get(i).getSu().getValue();

					if (i == (marineAccessPeriodsBridge2.size() - 1))
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

	void loadPeriodsFromFileBridge1() throws FileNotFoundException
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("Marine Access Periods (.mrp)", "*.mrp");
		fileChooser.getExtensionFilters().add(fileExtensions);	

		Stage stageForFileChooser = (Stage) loadFromFileBridge1Button.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		if (file != null)
		{
			marineAccessPeriodsBridge1.clear();

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				String[] periods = lineFromFile.split(":");
				for (int i = 0; i < periods.length; i++)
				{
					String[] values = periods[i].replace("[", "").replace("]", "").split(",");
					marineAccessPeriodsBridge1.add(new MarineAccessPeriod(Double.valueOf(values[0]), 
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

			if (marineAccessPeriodsBridge1.size() > 0)
			{
				marineAccessPeriodsBridge1Table.setItems(marineAccessPeriodsBridge1);	
				saveToFileBridge1Button.setDisable(false);
				saveMarineAccessPeriodsToRegistryBridge1Button.setDisable(false);
				deletePeriodBridge1Button.setDisable(false); 
			}
		}
	}

	void loadPeriodsFromFileBridge2() throws FileNotFoundException
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("Marine Access Periods (.mrp)", "*.mrp");
		fileChooser.getExtensionFilters().add(fileExtensions);	

		Stage stageForFileChooser = (Stage) loadFromFileBridge2Button.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		if (file != null)
		{
			marineAccessPeriodsBridge2.clear();

			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) 
			{
				String lineFromFile = scanner.nextLine();
				String[] periods = lineFromFile.split(":");
				for (int i = 0; i < periods.length; i++)
				{
					String[] values = periods[i].replace("[", "").replace("]", "").split(",");
					marineAccessPeriodsBridge2.add(new MarineAccessPeriod(Double.valueOf(values[0]), 
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

			if (marineAccessPeriodsBridge2.size() > 0)
			{
				marineAccessPeriodsBridge2Table.setItems(marineAccessPeriodsBridge2);	
				saveToFileBridge2Button.setDisable(false);
				saveMarineAccessPeriodsToRegistryBridge2Button.setDisable(false);
				deletePeriodBridge2Button.setDisable(false); 
			}
		}
	}
}