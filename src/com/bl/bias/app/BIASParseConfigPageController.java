package com.bl.bias.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.Optional;
import java.util.prefs.Preferences;

import com.bl.bias.objects.ParseLocationFormatA;
import com.bl.bias.objects.ParseLocationFormatB;
import com.bl.bias.objects.ParseLocationFormatC;

public class BIASParseConfigPageController 
{
	private static Preferences prefs;

	// Data from .SUMMARY file
	private static String x_trainCat;
	private static String x_trainCount;
	private static String x_trainMiles;
	private static String x_speed;
	private static String x_otp;
	private static String x_elapsedRunTime;
	private static String x_idealRunTime;
	private static String x_rtcVersionFromExtract;
	private static String x_resultsInvalid;

	// Data from .LINK file
	private static String l_linkOriginNode;
	private static String l_linkDestinationNode;
	private static String l_linkClass;
	private static String l_linkDirection;
	private static String l_linkMaxPassengerSpeed;
	private static String l_linkMaxThroughSpeed;
	private static String l_linkMaxLocalSpeed;

	// Data from .NODE file
	private static String n_node;
	private static String n_nodeName;
	private static String n_rtcMarker;
	private static String n_fieldMarker;
	private static String n_switchType;
	private static String n_isASignal;
	private static String n_trackNumber;

	// Data from .SIGNAL file
	private static String s_signalBeginNode;
	private static String s_signalEndNode;
	private static String s_signalType;
	private static String s_signalDirection;
	private static String s_trailingSignalBeginNode;
	private static String s_trailingSignalEndNode;

	// Data from .TRAIN file
	private static String t_trainSymbol;
	private static String t_trainType;
	private static String t_atcEquipped;
	private static String t_ptcEquipped;  

	// Data from .ROUTE file
	private static String r_trainSymbol;
	private static String r_rtcIncrement;
	private static String r_node;
	private static String r_headEndSpeed;
	private static String r_headEndDepartureTime;
	private static String r_tailEndDepartureTime;
	private static String r_cumulativeElapsedTime;
	private static String r_aspect;
	private static String r_direction;

	// Data from .OPTION file
	private static String o_rtcVersion;
	private static String o_units;
	private static String o_summaryReportTimeFormat;
	private static String o_simulationBeginDay;
	private static String o_simulationBeginTime;
	private static String o_simulationDuration; 
	private static String o_warmUpExclusion;
	private static String o_coolDownExclusion;
	private static String o_commaDelimitedRouteFile;
	private static String o_allNodesInRouteReport;
	private static String o_showSeedTrainsInRouteReport;
	private static String o_trainGroupName;
	private static String o_trainGroupAbbreviation;
	private static String o_trainTypeName;
	private static String o_trainTypeGroup;

	// Data from .LINE file
	private static String w_lineName;
	private static String w_col1;
	private static String w_col2;
	private static String w_col3;
	private static String w_col4;
	private static String w_col5;
	private static String w_col6;
	private static String w_col7;
	private static String w_col8;
	private static String w_col9;
	private static String w_col10;
	private static String w_col11;
	private static String w_col12;

	// Data from RTC.INI file
	private static String i_allowAlphaDOW;

	// Data from Radixx Res SSIM (IATA) file
	// For all rows
	private static String z_all_recordType;
	private static String z_all_recordSerialNumber;
	// Type 1 - Header
	private static String z_hdr_titleOfContents;
	private static String z_hdr_dataSetSerialNumber;
	// Type 2 - Carrier
	private static String z_car_timeMode;
	private static String z_car_airlineDesignator;
	private static String z_car_creatorReference;
	private static String z_car_periodOfValidity;
	private static String z_car_creationDate;
	private static String z_car_titleOfData;
	private static String z_car_releaseDate;
	// Type 3 - Flight Leg Record
	private static String z_flr_airlineDesignator;
	private static String z_flr_flightNumber;
	private static String z_flr_itineraryVariationIdentifier;
	private static String z_flr_legSequenceNumber;
	private static String z_flr_serviceType;
	private static String z_flr_periodOfOperation;
	private static String z_flr_dayOfOperation;
	private static String z_flr_departureStation;
	private static String z_flr_passengerSTD;
	private static String z_flr_aircraftSTD;
	private static String z_flr_timeVariationDeparture;
	private static String z_flr_departureTerminal;
	private static String z_flr_arrivalStation;
	private static String z_flr_aircraftSTA;
	private static String z_flr_passengerSTA;
	private static String z_flr_timeVariationArrival;
	private static String z_flr_arrivalTerminal;
	private static String z_flr_aircraftType;
	private static String z_flr_onwardAirlineDesignator;
	private static String z_flr_onwardFlightNumber;
	private static String z_flr_onwardFlightTransitLayover;
	private static String z_flr_aircraftConfiguration;
	private static String z_flr_dateVariation;
	// Type 4 - Segment Data Record
	private static String z_seg_airlineDesignator;
	private static String z_seg_flightNumber;
	private static String z_seg_itineraryVariationNumber;
	private static String z_seg_legSequenceNumber;
	private static String z_seg_serviceType;
	private static String z_seg_boardPointIndicator;
	private static String z_seg_offPointIndicator;
	private static String z_seg_dataElementIdentifier;
	private static String z_seg_segmentBoardPoint;
	private static String z_seg_segmentOffPoint;
	private static String z_seg_data;
	// Type 5 - Trailer Record
	private static String z_trl_airlineDesignator;
	private static String z_trl_releaseDate;
	private static String z_trl_serialNumberCheckReference;
	private static String z_trl_continuationEndCode;

	// Data from Radixx Res SSIM (S3) file
	// For all rows
	private static String y_all_recordType;
	private static String y_all_recordSerialNumber;
	// Type 1 - Header
	private static String y_hdr_titleOfContents;
	// Type 2 - Company/Carrier
	private static String y_com_timeMode;
	private static String y_com_companyCode;
	private static String y_com_description;
	private static String y_com_periodOfValidity;
	private static String y_com_creationDate;
	// Type 3 - Timetable/Flight Leg Record
	private static String y_ttb_companyCode;
	private static String y_ttb_trainNumber;
	private static String y_ttb_itineraryVariationIdentifier;
	private static String y_ttb_legSequenceNumber;
	private static String y_ttb_serviceType;
	private static String y_ttb_periodOfOperation;
	private static String y_ttb_dayOfOperation;
	private static String y_ttb_departureStation;
	private static String y_ttb_passengerSTD;
	private static String y_ttb_trainSTD;
	private static String y_ttb_timeVariationDeparture;
	private static String y_ttb_departureTerminal;
	private static String y_ttb_arrivalStation;
	private static String y_ttb_trainSTA;
	private static String y_ttb_passengerSTA;
	private static String y_ttb_timeVariationArrival;
	private static String y_ttb_arrivalTerminal;
	private static String y_ttb_commercialCategory;
	// Type 4 - Segment Data Record - Not supported
	// Type 5 - Trailer Record
	private static String y_trl_companyCode;
	private static String y_trl_startDate;
	private static String y_trl_serialNumberCheckReference;
	private static String y_trl_continuationEndCode;

	// Data from .TPC file
	private static String p_node;
	private static String p_fieldMarker;
	private static String p_designSpeed;
	private static String p_currentSpeed;

	private static Integer curPage;

	private ObservableList<ParseLocationFormatA> parseData1 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData2 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData3 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData4 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData5 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData6 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData7 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData8 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData9 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData10 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatC> parseData11 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatC> parseData12 = FXCollections.observableArrayList();
	private ObservableList<ParseLocationFormatB> parseData13 = FXCollections.observableArrayList();

	@FXML TableView<ParseLocationFormatA> parseLocationsTable1;  // Summary type/group
	@FXML TableView<ParseLocationFormatB> parseLocationsTable2;  // Summary other
	@FXML TableView<ParseLocationFormatB> parseLocationsTable3;  // Link data
	@FXML TableView<ParseLocationFormatB> parseLocationsTable4;  // Node data
	@FXML TableView<ParseLocationFormatB> parseLocationsTable5;  // Signal data
	@FXML TableView<ParseLocationFormatB> parseLocationsTable6;  // Train data
	@FXML TableView<ParseLocationFormatB> parseLocationsTable7;  // Route data
	@FXML TableView<ParseLocationFormatB> parseLocationsTable8;  // Option data
	@FXML TableView<ParseLocationFormatB> parseLocationsTable9;  // Line data
	@FXML TableView<ParseLocationFormatB> parseLocationsTable10; // Ini data
	@FXML TableView<ParseLocationFormatC> parseLocationsTable11; // Radixx Res SSIM (IATA) data
	@FXML TableView<ParseLocationFormatC> parseLocationsTable12; // Radixx Res SSIM (S3) data
	@FXML TableView<ParseLocationFormatB> parseLocationsTable13; // TPC data

	@FXML private TableColumn<ParseLocationFormatA, String> parameterName1;
	@FXML private TableColumn<ParseLocationFormatA, String> registryKey1;
	@FXML private TableColumn<ParseLocationFormatA, Integer> typeStartColumn1;
	@FXML private TableColumn<ParseLocationFormatA, Integer> typeEndColumn1;
	@FXML private TableColumn<ParseLocationFormatA, Integer> groupStartColumn1;
	@FXML private TableColumn<ParseLocationFormatA, Integer> groupEndColumn1;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName2;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey2;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn2;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn2;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName3;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey3;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn3;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn3;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName4;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey4;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn4;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn4;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName5;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey5;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn5;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn5;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName6;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey6;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn6;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn6;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName7;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey7;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn7;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn7;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName8;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey8;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn8;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn8;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName9;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey9;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn9;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn9;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName10;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey10;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn10;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn10;

	@FXML private TableColumn<ParseLocationFormatC, String> parameterName11;
	@FXML private TableColumn<ParseLocationFormatC, String> displayedName11;
	@FXML private TableColumn<ParseLocationFormatC, String> registryKey11;
	@FXML private TableColumn<ParseLocationFormatC, Integer> startColumn11;
	@FXML private TableColumn<ParseLocationFormatC, Integer> endColumn11;

	@FXML private TableColumn<ParseLocationFormatC, String> parameterName12;
	@FXML private TableColumn<ParseLocationFormatC, String> displayedName12;
	@FXML private TableColumn<ParseLocationFormatC, String> registryKey12;
	@FXML private TableColumn<ParseLocationFormatC, Integer> startColumn12;
	@FXML private TableColumn<ParseLocationFormatC, Integer> endColumn12;

	@FXML private TableColumn<ParseLocationFormatB, String> parameterName13;
	@FXML private TableColumn<ParseLocationFormatB, String> registryKey13;
	@FXML private TableColumn<ParseLocationFormatB, Integer> startColumn13;
	@FXML private TableColumn<ParseLocationFormatB, Integer> endColumn13;

	@FXML private Label parseRangeLabel;

	@FXML private RadioButton viewEntriesOnlyRadioButton;
	@FXML private RadioButton viewAndEditEntriesRadioButton;

	@FXML private Button restoreDefaultsButton;
	@FXML private Button previousPageButton;
	@FXML private Button nextPageButton;

	@FXML private void initialize() 
	{
		curPage = 1;
		previousPageButton.setDisable(true);

		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");
		getParseParameters();

		// Initiailize for page 1
		parseRangeLabel.setText("Parameters parsed from .SUMMARY files:"); 

		parseLocationsTable1.setVisible(true);
		parseLocationsTable2.setVisible(true);
		parseLocationsTable3.setVisible(false);
		parseLocationsTable4.setVisible(false);
		parseLocationsTable5.setVisible(false);
		parseLocationsTable6.setVisible(false);
		parseLocationsTable7.setVisible(false);
		parseLocationsTable8.setVisible(false);
		parseLocationsTable9.setVisible(false);
		parseLocationsTable10.setVisible(false);
		parseLocationsTable11.setVisible(false);
		parseLocationsTable12.setVisible(false);
		parseLocationsTable13.setVisible(false);

		// Table 1 - Extract
		parameterName1.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatA, String>("parameterName"));
		registryKey1.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatA, String>("registryKey"));
		typeStartColumn1.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatA, Integer>("typeStartColumn"));
		typeEndColumn1.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatA, Integer>("typeEndColumn"));
		groupStartColumn1.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatA, Integer>("groupStartColumn"));
		groupEndColumn1.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatA, Integer>("groupEndColumn"));

		parameterName1.setSortable(false);
		registryKey1.setSortable(false);
		typeStartColumn1.setSortable(false);
		typeEndColumn1.setSortable(false);
		groupStartColumn1.setSortable(false);
		groupEndColumn1.setSortable(false);

		parameterName1.getStyleClass().add("header-style");
		registryKey1.getStyleClass().add("header-style");
		typeStartColumn1.setStyle( "-fx-alignment: CENTER;");
		typeEndColumn1.setStyle( "-fx-alignment: CENTER;");
		groupStartColumn1.setStyle( "-fx-alignment: CENTER;");
		groupEndColumn1.setStyle( "-fx-alignment: CENTER;");

		parseData1.addAll(new ParseLocationFormatA("Train Category", "x_trainCat", Integer.valueOf(x_getTrainCat()[0]), Integer.valueOf(x_getTrainCat()[1]), Integer.valueOf(x_getTrainCat()[2]), Integer.valueOf(x_getTrainCat()[3])),
				new ParseLocationFormatA("Train Count", "x_trainCount", Integer.valueOf(x_getTrainCount()[0]), Integer.valueOf(x_getTrainCount()[1]), Integer.valueOf(x_getTrainCount()[2]), Integer.valueOf(x_getTrainCount()[3])),
				new ParseLocationFormatA("Train Miles", "x_trainMiles", Integer.valueOf(x_getTrainMiles()[0]), Integer.valueOf(x_getTrainMiles()[1]), Integer.valueOf(x_getTrainMiles()[2]), Integer.valueOf(x_getTrainMiles()[3])),
				new ParseLocationFormatA("Velocity", "x_speed", Integer.valueOf(x_getSpeed()[0]), Integer.valueOf(x_getSpeed()[1]), Integer.valueOf(x_getSpeed()[2]), Integer.valueOf(x_getSpeed()[3])),
				new ParseLocationFormatA("OTP", "x_otp", Integer.valueOf(x_getOTP()[0]), Integer.valueOf(x_getOTP()[1]), Integer.valueOf(x_getOTP()[2]), Integer.valueOf(x_getOTP()[3])),
				new ParseLocationFormatA("Elapsed Run Time", "x_elapsedRunTime", Integer.valueOf(x_getElapsedRunTime()[0]), Integer.valueOf(x_getElapsedRunTime()[1]), Integer.valueOf(x_getElapsedRunTime()[2]), Integer.valueOf(x_getElapsedRunTime()[3])),
				new ParseLocationFormatA("Ideal Run Time", "x_idealRunTime", Integer.valueOf(x_getIdealRunTime()[0]), Integer.valueOf(x_getIdealRunTime()[1]), Integer.valueOf(x_getIdealRunTime()[2]), Integer.valueOf(x_getIdealRunTime()[3]))
				);

		parseLocationsTable1.setItems(parseData1);

		parameterName1.setReorderable(false);
		registryKey1.setReorderable(false);
		typeEndColumn1.setReorderable(false);
		typeStartColumn1.setReorderable(false);
		groupStartColumn1.setReorderable(false);
		groupEndColumn1.setReorderable(false);

		// Table 2 - Extract
		parameterName2.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey2.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn2.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn2.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName2.setSortable(false);
		registryKey2.setSortable(false);
		startColumn2.setSortable(false);
		endColumn2.setSortable(false);

		parameterName2.getStyleClass().add("header-style");
		registryKey2.getStyleClass().add("header-style");
		startColumn2.setStyle( "-fx-alignment: CENTER;");
		endColumn2.setStyle( "-fx-alignment: CENTER;");

		parseData2.addAll(new ParseLocationFormatB("RTC Version", "x_rtcVersion", Integer.valueOf(BIASParseConfigPageController.x_getRtcVersionExtract()[0]), Integer.valueOf(BIASParseConfigPageController.x_getRtcVersionExtract()[1])),
				new ParseLocationFormatB("Results Invalid", "x_resultsInvalid", Integer.valueOf(BIASParseConfigPageController.x_getInvalidResults()[0]), Integer.valueOf(BIASParseConfigPageController.x_getInvalidResults()[1]))
				);

		parseLocationsTable2.setItems(parseData2);

		parameterName2.setReorderable(false);
		registryKey2.setReorderable(false);
		startColumn2.setReorderable(false);
		endColumn2.setReorderable(false);

		// Table 3 - Links
		parameterName3.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey3.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn3.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn3.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName3.setSortable(false);
		registryKey3.setSortable(false);
		startColumn3.setSortable(false);
		endColumn3.setSortable(false);

		parameterName3.getStyleClass().add("header-style");
		registryKey3.getStyleClass().add("header-style");
		startColumn3.setStyle( "-fx-alignment: CENTER;");
		endColumn3.setStyle( "-fx-alignment: CENTER;");

		parseData3.addAll(new ParseLocationFormatB("Link Origin Node", "l_linkOriginNode", Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[1])),
				new ParseLocationFormatB("Link Destination Node", "l_linkDestinationNode", Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[1])),
				new ParseLocationFormatB("Link Class", "l_linkClass", Integer.valueOf(BIASParseConfigPageController.l_getLinkClass()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkClass()[1])),
				new ParseLocationFormatB("Link Direction", "l_linkDirection", Integer.valueOf(BIASParseConfigPageController.l_getLinkDirection()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDirection()[1])),
				new ParseLocationFormatB("Link Max Passenger Speed", "l_linkMaxPassengerSpeed", Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxPassengerSpeed()[0]), Integer.valueOf(l_getLinkMaxPassengerSpeed()[1])),
				new ParseLocationFormatB("Link Max Through Speed", "l_linkMaxThroughSpeed", Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxThroughSpeed()[0]), Integer.valueOf(l_getLinkMaxThroughSpeed()[1])),
				new ParseLocationFormatB("Link Max Local Speed", "l_linkMaxLocalSpeed", Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxLocalSpeed()[0]), Integer.valueOf(l_getLinkMaxLocalSpeed()[1]))
				);

		parseLocationsTable3.setItems(parseData3);

		parameterName3.setReorderable(false);
		registryKey3.setReorderable(false);
		startColumn3.setReorderable(false);
		endColumn3.setReorderable(false);

		// Table 4 - Nodes
		parameterName4.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey4.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn4.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn4.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName4.setSortable(false);
		registryKey4.setSortable(false);
		startColumn4.setSortable(false);
		endColumn4.setSortable(false);

		parameterName4.getStyleClass().add("header-style");
		registryKey4.getStyleClass().add("header-style");
		startColumn4.setStyle( "-fx-alignment: CENTER;");
		endColumn4.setStyle( "-fx-alignment: CENTER;");

		parseData4.addAll(new ParseLocationFormatB("Node", "n_node", Integer.valueOf(BIASParseConfigPageController.n_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.n_getNode()[1])),
				new ParseLocationFormatB("Node Name", "n_nodeName", Integer.valueOf(BIASParseConfigPageController.n_getNodeName()[0]), Integer.valueOf(BIASParseConfigPageController.n_getNodeName()[1])),
				new ParseLocationFormatB("RTC Marker", "n_rtcMarker", Integer.valueOf(BIASParseConfigPageController.n_getRtcMarker()[0]), Integer.valueOf(BIASParseConfigPageController.n_getRtcMarker()[1])),
				new ParseLocationFormatB("Field Marker", "n_fieldMarker", Integer.valueOf(BIASParseConfigPageController.n_getFieldMarker()[0]), Integer.valueOf(BIASParseConfigPageController.n_getFieldMarker()[1])),
				new ParseLocationFormatB("Switch Type", "n_switchType", Integer.valueOf(BIASParseConfigPageController.n_getSwitchType()[0]), Integer.valueOf(BIASParseConfigPageController.n_getSwitchType()[1])),
				new ParseLocationFormatB("Signal", "n_isASignal", Integer.valueOf(BIASParseConfigPageController.n_getIsASignal()[0]), Integer.valueOf(BIASParseConfigPageController.n_getIsASignal()[1])),
				new ParseLocationFormatB("Track Number", "n_trackNumber", Integer.valueOf(BIASParseConfigPageController.n_getTrackNumber()[0]), Integer.valueOf(BIASParseConfigPageController.n_getTrackNumber()[1]))
				);

		parseLocationsTable4.setItems(parseData4);

		parameterName4.setReorderable(false);
		registryKey4.setReorderable(false);
		startColumn4.setReorderable(false);
		endColumn4.setReorderable(false);

		// Table 5 - Signals
		parameterName5.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey5.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn5.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn5.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName5.setSortable(false);
		registryKey5.setSortable(false);
		startColumn5.setSortable(false);
		endColumn5.setSortable(false);

		parameterName5.getStyleClass().add("header-style");
		registryKey5.getStyleClass().add("header-style");
		startColumn5.setStyle( "-fx-alignment: CENTER;");
		endColumn5.setStyle( "-fx-alignment: CENTER;");

		parseData5.addAll(new ParseLocationFormatB("Begin Node", "s_signalBeginNode", Integer.valueOf(BIASParseConfigPageController.s_getSignalBeginNode()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalBeginNode()[1])),
				new ParseLocationFormatB("End Node", "s_signalEndNode", Integer.valueOf(BIASParseConfigPageController.s_getSignalEndNode()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalEndNode()[1])),
				new ParseLocationFormatB("Signal Type", "s_signalType", Integer.valueOf(BIASParseConfigPageController.s_getSignalType()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalType()[1])),
				new ParseLocationFormatB("Signal Direction", "s_signalDirection", Integer.valueOf(BIASParseConfigPageController.s_getSignalDirection()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalDirection()[1])),
				new ParseLocationFormatB("Trailing Signal Begin", "s_trailingSignalBeginNode", Integer.valueOf(BIASParseConfigPageController.s_getTrailingSignalBeginNode()[0]), Integer.valueOf(BIASParseConfigPageController.s_getTrailingSignalBeginNode()[1])),
				new ParseLocationFormatB("Trailing Signal End", "s_trailingSignalEndNode", Integer.valueOf(BIASParseConfigPageController.s_getTrailingSignalEndNode()[0]), Integer.valueOf(BIASParseConfigPageController.s_getTrailingSignalEndNode()[1]))
				);

		parseLocationsTable5.setItems(parseData5);

		parameterName5.setReorderable(false);
		registryKey5.setReorderable(false);
		startColumn5.setReorderable(false);
		endColumn5.setReorderable(false);

		// Table 6 - Trains
		parameterName6.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey6.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn6.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn6.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName6.setSortable(false);
		registryKey6.setSortable(false);
		startColumn6.setSortable(false);
		endColumn6.setSortable(false);

		parameterName6.getStyleClass().add("header-style");
		registryKey6.getStyleClass().add("header-style");
		startColumn6.setStyle( "-fx-alignment: CENTER;");
		endColumn6.setStyle( "-fx-alignment: CENTER;");

		parseData6.addAll(new ParseLocationFormatB("Train Symbol", "t_trainSymbol", Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[1])),
				new ParseLocationFormatB("Train Type", "t_trainType", Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[1])),
				new ParseLocationFormatB("ATC Equipped", "t_atcEquipped", Integer.valueOf(BIASParseConfigPageController.t_getAtcEquipped()[0]), Integer.valueOf(BIASParseConfigPageController.t_getAtcEquipped()[1])),
				new ParseLocationFormatB("PTC Equpped", "t_ptcEquipped", Integer.valueOf(BIASParseConfigPageController.t_getPtcEquipped()[0]), Integer.valueOf(BIASParseConfigPageController.t_getPtcEquipped()[1]))
				);

		parseLocationsTable6.setItems(parseData6);

		parameterName6.setReorderable(false);
		registryKey6.setReorderable(false);
		startColumn6.setReorderable(false);
		endColumn6.setReorderable(false);

		// Table 7 - Routes
		parameterName7.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey7.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn7.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn7.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName7.setSortable(false);
		registryKey7.setSortable(false);
		startColumn7.setSortable(false);
		endColumn7.setSortable(false);

		parameterName7.getStyleClass().add("header-style");
		registryKey7.getStyleClass().add("header-style");
		startColumn7.setStyle( "-fx-alignment: CENTER;");
		endColumn7.setStyle( "-fx-alignment: CENTER;");

		parseData7.addAll(new ParseLocationFormatB("Train Symbol", "r_trainSymbol", Integer.valueOf(BIASParseConfigPageController.r_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTrainSymbol()[1])),
				new ParseLocationFormatB("RTC Increment", "r_rtcIncrement", Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[0]), Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[1])),
				new ParseLocationFormatB("Node", "r_node", Integer.valueOf(BIASParseConfigPageController.r_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.r_getNode()[1])),
				new ParseLocationFormatB("Head End Speed", "r_headEndSpeed", Integer.valueOf(BIASParseConfigPageController.r_getHeadEndSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndSpeed()[1])),
				new ParseLocationFormatB("Head End Arrival Time", "r_headEndDepartureTime", Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[1])),
				new ParseLocationFormatB("Tail End Departure Time", "r_tailEndDepartureTime", Integer.valueOf(BIASParseConfigPageController.r_getTailEndDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTailEndDepartureTime()[1])),
				new ParseLocationFormatB("Cumulative Elapsed", "r_cumulativeElapsedTime", Integer.valueOf(BIASParseConfigPageController.r_getCumulativeElapsedTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getCumulativeElapsedTime()[1])),
				new ParseLocationFormatB("Aspect", "r_aspect", Integer.valueOf(BIASParseConfigPageController.r_getAspect()[0]), Integer.valueOf(BIASParseConfigPageController.r_getAspect()[1])),
				new ParseLocationFormatB("Direction", "r_direction", Integer.valueOf(BIASParseConfigPageController.r_getDirection()[0]), Integer.valueOf(BIASParseConfigPageController.r_getDirection()[1]))
				);

		parseLocationsTable7.setItems(parseData7);

		parameterName7.setReorderable(false);
		registryKey7.setReorderable(false);
		startColumn7.setReorderable(false);
		endColumn7.setReorderable(false);

		// Table 8 - Options
		parameterName8.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey8.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn8.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn8.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName8.setSortable(false);
		registryKey8.setSortable(false);
		startColumn8.setSortable(false);
		endColumn8.setSortable(false);

		parameterName8.getStyleClass().add("header-style");
		registryKey8.getStyleClass().add("header-style");
		startColumn8.setStyle( "-fx-alignment: CENTER;");
		endColumn8.setStyle( "-fx-alignment: CENTER;");

		parseData8.addAll(new ParseLocationFormatB("RTC Version", "o_rtcVersion", Integer.valueOf(BIASParseConfigPageController.o_getRtcVersion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getRtcVersion()[1])),
				new ParseLocationFormatB("Units", "o_units", Integer.valueOf(BIASParseConfigPageController.o_getUnits()[0]), Integer.valueOf(BIASParseConfigPageController.o_getUnits()[1])),
				new ParseLocationFormatB("Summary Report Time Format", "o_summaryReportTimeFormat", Integer.valueOf(BIASParseConfigPageController.o_getSummaryReportTimeFormat()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSummaryReportTimeFormat()[1])),
				new ParseLocationFormatB("Simulation Begin Day", "o_simulationBeginDay", Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[1])),
				new ParseLocationFormatB("Simulation Begin Time", "o_simulationBeginTime", Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[1])),
				new ParseLocationFormatB("Simulation Duration", "o_simulationDuration", Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[1])),
				new ParseLocationFormatB("Warm-up Exclusion", "o_warmUpExclusion", Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[1])),
				new ParseLocationFormatB("Cool-down Exclusion", "o_coolDownExclusion", Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[1])),
				new ParseLocationFormatB("Route Is Comma Delimited", "o_commaDelimitedRouteFile", Integer.valueOf(BIASParseConfigPageController.o_getCommaDelimited()[0]), Integer.valueOf(BIASParseConfigPageController.o_getCommaDelimited()[1])),
				new ParseLocationFormatB("All Nodes in Route Report", "o_allNodesInRouteReport", Integer.valueOf(BIASParseConfigPageController.o_getAllNodesInRouteReport()[0]), Integer.valueOf(BIASParseConfigPageController.o_getAllNodesInRouteReport()[1])),
				new ParseLocationFormatB("Show Seed Trains in Route Report", "o_showSeedTrainsInRouteReport", Integer.valueOf(BIASParseConfigPageController.o_getShowSeedTrainsInRouteReport()[0]), Integer.valueOf(BIASParseConfigPageController.o_getShowSeedTrainsInRouteReport()[1])),
				new ParseLocationFormatB("Train Group Abbreviation", "o_trainGroupAbbreviation", Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupAbbreviation()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupAbbreviation()[1])),
				new ParseLocationFormatB("Train Type Name", "o_trainTypeName", Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[1])),
				new ParseLocationFormatB("Train Type Group", "o_trainTypeGroup", Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeGroup()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeGroup()[1]))
				);

		parseLocationsTable8.setItems(parseData8);

		parameterName8.setReorderable(false);
		registryKey8.setReorderable(false);
		startColumn8.setReorderable(false);
		endColumn8.setReorderable(false);

		// Table 9 - Line
		parameterName9.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey9.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn9.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn9.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName9.setSortable(false);
		registryKey9.setSortable(false);
		startColumn9.setSortable(false);
		endColumn9.setSortable(false);

		parameterName9.getStyleClass().add("header-style");
		registryKey9.getStyleClass().add("header-style");
		startColumn9.setStyle( "-fx-alignment: CENTER;");
		endColumn9.setStyle( "-fx-alignment: CENTER;");

		parseData9.addAll(new ParseLocationFormatB("Line Name", "w_lineName", Integer.valueOf(BIASParseConfigPageController.w_getLineName()[0]), Integer.valueOf(BIASParseConfigPageController.w_getLineName()[1])),
				new ParseLocationFormatB("1st Column Entry", "w_col1", Integer.valueOf(BIASParseConfigPageController.w_getCol1()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol1()[1])),
				new ParseLocationFormatB("2nd Column Entry", "w_col2", Integer.valueOf(BIASParseConfigPageController.w_getCol2()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol2()[1])),
				new ParseLocationFormatB("3rd Column Entry", "w_col3", Integer.valueOf(BIASParseConfigPageController.w_getCol3()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol3()[1])),
				new ParseLocationFormatB("4th Column Entry", "w_col4", Integer.valueOf(BIASParseConfigPageController.w_getCol4()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol4()[1])),
				new ParseLocationFormatB("5th Column Entry", "w_col5", Integer.valueOf(BIASParseConfigPageController.w_getCol5()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol5()[1])),
				new ParseLocationFormatB("6th Column Entry", "w_col6", Integer.valueOf(BIASParseConfigPageController.w_getCol6()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol6()[1])),
				new ParseLocationFormatB("7th Column Entry", "w_col7", Integer.valueOf(BIASParseConfigPageController.w_getCol7()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol7()[1])),
				new ParseLocationFormatB("8th Column Entry", "w_col8", Integer.valueOf(BIASParseConfigPageController.w_getCol8()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol8()[1])),
				new ParseLocationFormatB("9th Column Entry", "w_col9", Integer.valueOf(BIASParseConfigPageController.w_getCol9()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol9()[1])),
				new ParseLocationFormatB("10th Column Entry", "w_col10", Integer.valueOf(BIASParseConfigPageController.w_getCol10()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol10()[1])),
				new ParseLocationFormatB("11th Column Entry", "w_col11", Integer.valueOf(BIASParseConfigPageController.w_getCol11()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol11()[1])),
				new ParseLocationFormatB("12th Column Entry", "w_col12", Integer.valueOf(BIASParseConfigPageController.w_getCol12()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol12()[1]))
				);
		parseLocationsTable9.setItems(parseData9);

		parameterName9.setReorderable(false);
		registryKey9.setReorderable(false);
		startColumn9.setReorderable(false);
		endColumn9.setReorderable(false);

		// Table 10 - Ini config
		parameterName10.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey10.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn10.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn10.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName10.setSortable(false);
		registryKey10.setSortable(false);
		startColumn10.setSortable(false);
		endColumn10.setSortable(false);

		parameterName10.getStyleClass().add("header-style");
		registryKey10.getStyleClass().add("header-style");
		startColumn10.setStyle( "-fx-alignment: CENTER;");
		endColumn10.setStyle( "-fx-alignment: CENTER;");

		parseData10.addAll(new ParseLocationFormatB("Allow Alpha DOW", "i_allowAlphaDOW", Integer.valueOf(BIASParseConfigPageController.i_getAllowAlphaDOW()[0]), Integer.valueOf(BIASParseConfigPageController.i_getAllowAlphaDOW()[1]))
				);
		parseLocationsTable10.setItems(parseData10);

		parameterName10.setReorderable(false);
		registryKey10.setReorderable(false);
		startColumn10.setReorderable(false);
		endColumn10.setReorderable(false);

		// Table 11 - Radixx Res SSSIM (IATA)
		parameterName11.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, String>("parameterName"));
		displayedName11.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, String>("displayedName"));
		registryKey11.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, String>("registryKey"));
		startColumn11.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, Integer>("startColumn"));
		endColumn11.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, Integer>("endColumn"));

		parameterName11.setSortable(false);
		displayedName11.setSortable(false);
		registryKey11.setSortable(false);
		startColumn11.setSortable(false);
		endColumn11.setSortable(false);

		parameterName11.getStyleClass().add("header-style");
		displayedName11.getStyleClass().add("header-style");
		registryKey11.getStyleClass().add("header-style");
		startColumn11.setStyle( "-fx-alignment: CENTER;");
		endColumn11.setStyle( "-fx-alignment: CENTER;");

		parseData11.addAll(new ParseLocationFormatC("All Rows - Record Type", "", "z_all_recordType", Integer.valueOf(BIASParseConfigPageController.z_getAll_recordType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordType()[1])),
				new ParseLocationFormatC("All Rows - Record Serial Number", "", "z_all_recordSerialNumber", Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[1])),
				new ParseLocationFormatC("Header - Title of Contents", "", "z_hdr_titleOfContents", Integer.valueOf(BIASParseConfigPageController.z_getHdr_titleOfContents()[0]), Integer.valueOf(BIASParseConfigPageController.z_getHdr_titleOfContents()[1])),
				new ParseLocationFormatC("Header - Data Set Serial Number", "", "z_hdr_dataSetSerialNumber", Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[1])),
				new ParseLocationFormatC("Carrier - Time Mode", "", "z_car_timeMode", Integer.valueOf(BIASParseConfigPageController.z_getCar_timeMode()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_timeMode()[1])),
				new ParseLocationFormatC("Carrier - Airline Designator", "Railroad ID", "z_car_airlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getCar_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_airlineDesignator()[1])),
				new ParseLocationFormatC("Carrier - Creator Reference", "", "z_car_creatorReference", Integer.valueOf(BIASParseConfigPageController.z_getCar_creatorReference()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_creatorReference()[1])),
				new ParseLocationFormatC("Carrier - Period of Validity", "", "z_car_periodOfValidity", Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[1])),
				new ParseLocationFormatC("Carrier - Creation Date", "", "z_car_creationDate", Integer.valueOf(BIASParseConfigPageController.z_getCar_creationDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_creationDate()[1])),
				new ParseLocationFormatC("Carrier - Title of Data", "", "z_car_titleOfData", Integer.valueOf(BIASParseConfigPageController.z_getCar_titleOfData()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_titleOfData()[1])),
				new ParseLocationFormatC("Carrier - Release Date", "", "z_car_releaseDate", Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[1])),
				new ParseLocationFormatC("Flight Leg - Airline Designator", "Railroad ID", "z_flr_airlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getFlr_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_airlineDesignator()[1])),
				new ParseLocationFormatC("Flight Leg - Flight Number", "Train Number", "z_flr_flightNumber", Integer.valueOf(BIASParseConfigPageController.z_getFlr_flightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_flightNumber()[1])),
				new ParseLocationFormatC("Flight Leg - Itinerary Variation Identifier", "", "z_flr_itineraryVariationIdentifier", Integer.valueOf(BIASParseConfigPageController.z_getFlr_itineraryVariationIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_itineraryVariationIdentifier()[1])),
				new ParseLocationFormatC("Flight Leg - Leg Sequence Number", "", "z_flr_legSequenceNumber", Integer.valueOf(BIASParseConfigPageController.z_getFlr_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_legSequenceNumber()[1])),
				new ParseLocationFormatC("Flight Leg - Service Type", "", "z_flr_serviceType", Integer.valueOf(BIASParseConfigPageController.z_getFlr_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_serviceType()[1])),
				new ParseLocationFormatC("Flight Leg - Period of Operation", "", "z_flr_periodOfOperation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[1])),
				new ParseLocationFormatC("Flight Leg - Day of Operation", "", "z_flr_dayOfOperation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[1])),
				new ParseLocationFormatC("Flight Leg - Departure Station", "", "z_flr_departureStation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureStation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureStation()[1])),
				new ParseLocationFormatC("Flight Leg - Passenger STD", "", "z_flr_passengerSTD", Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTD()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTD()[1])),
				new ParseLocationFormatC("Flight Leg - Aircraft STD", "Train STD", "z_flr_aircraftSTD", Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTD()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTD()[1])),
				new ParseLocationFormatC("Flight Leg - Time Variation Departure", "", "z_flr_timeVariationDeparture", Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationDeparture()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationDeparture()[1])),
				new ParseLocationFormatC("Flight Leg - Departure Terminal", "", "z_flr_departureTerminal", Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureTerminal()[1])),
				new ParseLocationFormatC("Flight Leg - Arrival Station", "", "z_flr_arrivalStation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalStation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalStation()[1])),
				new ParseLocationFormatC("Flight Leg - Aircraft STA", "Train STA", "z_flr_aircraftSTA", Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTA()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTA()[1])),
				new ParseLocationFormatC("Flight Leg - Passenger STA", "", "z_flr_passengerSTA", Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTA()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTA()[1])),
				new ParseLocationFormatC("Flight Leg - Time Variation Arrival", "", "z_flr_timeVariationArrival", Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationArrival()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationArrival()[1])),
				new ParseLocationFormatC("Flight Leg - Arrival Terminal", "", "z_flr_arrivalTerminal", Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalTerminal()[1])),
				new ParseLocationFormatC("Flight Leg - Aircraft Type", "Train Type", "z_flr_aircraftType", Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftType()[1])),
				new ParseLocationFormatC("Flight Leg - Onward Airline Designator", "Onward Railroad ID", "z_flr_onwardAirlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[1])),
				new ParseLocationFormatC("Flight Leg - Onward Flight Number", "Onward Train Number", "z_flr_onwardFlightNumber", Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[1])),
				new ParseLocationFormatC("Flight Leg - Onward Flight Transit Layover", "Onward Train Transit Layover", "z_flr_onwardFlightTransitLayover", Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[1])),
				new ParseLocationFormatC("Flight Leg - Aircraft Configuration", "Train Configuration", "z_flr_aircraftConfiguration", Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftConfiguration()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftConfiguration()[1])),
				new ParseLocationFormatC("Flight Leg - Date Variation", "", "z_flr_dateVariation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[1])),
				new ParseLocationFormatC("Segment - Airline Designator", "Railroad ID", "z_seg_airlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getSeg_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_airlineDesignator()[1])),
				new ParseLocationFormatC("Segment - Flight Number", "Train Number", "z_seg_flightNumber", Integer.valueOf(BIASParseConfigPageController.z_getSeg_flightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_flightNumber()[1])),
				new ParseLocationFormatC("Segment - Itinerary Variation Number", "", "z_seg_itineraryVariationNumber", Integer.valueOf(BIASParseConfigPageController.z_getSeg_itineraryVariationNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_itineraryVariationNumber()[1])),
				new ParseLocationFormatC("Segment - Leg Sequence Number", "", "z_seg_legSequenceNumber", Integer.valueOf(BIASParseConfigPageController.z_getSeg_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_legSequenceNumber()[1])),
				new ParseLocationFormatC("Segment - Service Type", "", "z_seg_serviceType", Integer.valueOf(BIASParseConfigPageController.z_getSeg_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_serviceType()[1])),
				new ParseLocationFormatC("Segment - Board Point Indicator", "", "z_seg_boardPointIndicator", Integer.valueOf(BIASParseConfigPageController.z_getSeg_boardPointIndicator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_boardPointIndicator()[1])),
				new ParseLocationFormatC("Segment - Off Point Indicator", "", "z_seg_offPointIndicator", Integer.valueOf(BIASParseConfigPageController.z_getSeg_offPointIndicator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_offPointIndicator()[1])),
				new ParseLocationFormatC("Segment - Data Element Identifier", "", "z_seg_dataElementIdentifier", Integer.valueOf(BIASParseConfigPageController.z_getSeg_dataElementIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_dataElementIdentifier()[1])),
				new ParseLocationFormatC("Segment - Segment Board Point", "", "z_seg_segmentBoardPoint", Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentBoardPoint()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentBoardPoint()[1])),
				new ParseLocationFormatC("Segment - Segment Off Point", "", "z_seg_segmentOffPoint", Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentOffPoint()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentOffPoint()[1])),
				new ParseLocationFormatC("Segment - Data", "", "z_seg_data", Integer.valueOf(BIASParseConfigPageController.z_getSeg_data()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_data()[1])),
				new ParseLocationFormatC("Trailer - Airline Designator", "Railroad ID", "z_trl_airlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[1])),
				new ParseLocationFormatC("Trailer - Release Date", "", "z_trl_releaseDate", Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[1])),
				new ParseLocationFormatC("Trailer - Serial Number Check Reference", "", "z_trl_serialNumberCheckReference", Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[1])),
				new ParseLocationFormatC("Trailer - Continuation End Code", "", "z_trl_continuationEndCode", Integer.valueOf(BIASParseConfigPageController.z_getTrl_continuationEndCode()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_continuationEndCode()[1]))
				);
		parseLocationsTable11.setItems(parseData11);

		parameterName11.setReorderable(false);
		displayedName11.setReorderable(false);
		registryKey11.setReorderable(false);
		startColumn11.setReorderable(false);
		endColumn11.setReorderable(false);

		// Table 12 - Radixx Res SSSIM (S3)
		parameterName12.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, String>("parameterName"));
		displayedName12.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, String>("displayedName"));
		registryKey12.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, String>("registryKey"));
		startColumn12.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, Integer>("startColumn"));
		endColumn12.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatC, Integer>("endColumn"));

		parameterName12.setSortable(false);
		displayedName12.setSortable(false);
		registryKey12.setSortable(false);
		startColumn12.setSortable(false);
		endColumn12.setSortable(false);

		parameterName12.getStyleClass().add("header-style");
		displayedName12.getStyleClass().add("header-style");
		registryKey12.getStyleClass().add("header-style");
		startColumn12.setStyle( "-fx-alignment: CENTER;");
		endColumn12.setStyle( "-fx-alignment: CENTER;");

		parseData12.addAll(new ParseLocationFormatC("All Rows - Record Type", "", "y_all_recordType", Integer.valueOf(BIASParseConfigPageController.y_getAll_recordType()[0]), Integer.valueOf(BIASParseConfigPageController.y_getAll_recordType()[1])),
				new ParseLocationFormatC("All Rows - Record Serial Number", "", "y_all_recordSerialNumber", Integer.valueOf(BIASParseConfigPageController.y_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getAll_recordSerialNumber()[1])),
				new ParseLocationFormatC("Header - Title of Contents", "", "y_hdr_titleOfContents", Integer.valueOf(BIASParseConfigPageController.y_getHdr_titleOfContents()[0]), Integer.valueOf(BIASParseConfigPageController.y_getHdr_titleOfContents()[1])),
				new ParseLocationFormatC("Company - Time Mode", "", "y_com_timeMode", Integer.valueOf(BIASParseConfigPageController.y_getCom_timeMode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_timeMode()[1])),
				new ParseLocationFormatC("Company - Company Code", "", "y_com_companyCode", Integer.valueOf(BIASParseConfigPageController.y_getCom_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_companyCode()[1])),
				new ParseLocationFormatC("Company - Description", "", "y_com_description", Integer.valueOf(BIASParseConfigPageController.y_getCom_description()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_description()[1])),
				new ParseLocationFormatC("Company - Period of Validity", "", "y_com_periodOfValidity", Integer.valueOf(BIASParseConfigPageController.y_getCom_periodOfValidity()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_periodOfValidity()[1])),
				new ParseLocationFormatC("Company - Creation Date", "", "y_com_creationDate", Integer.valueOf(BIASParseConfigPageController.y_getCom_creationDate()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_creationDate()[1])),
				new ParseLocationFormatC("Timetable - Company Code", "", "y_ttb_companyCode", Integer.valueOf(BIASParseConfigPageController.y_getTtb_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_companyCode()[1])),
				new ParseLocationFormatC("Timetable - Train Number", "", "y_ttb_trainNumber", Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainNumber()[1])),
				new ParseLocationFormatC("Timetable - Itinerary Variation Identifier", "", "y_ttb_itineraryVariationIdentifier", Integer.valueOf(BIASParseConfigPageController.y_getTtb_itineraryVariationIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_itineraryVariationIdentifier()[1])),
				new ParseLocationFormatC("Timetable - Leg Sequence Number", "", "y_ttb_legSequenceNumber", Integer.valueOf(BIASParseConfigPageController.y_getTtb_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_legSequenceNumber()[1])),
				new ParseLocationFormatC("Timetable - Commercial Category", "", "y_ttb_commercialCategory", Integer.valueOf(BIASParseConfigPageController.y_getTtb_commercialCategory()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_commercialCategory()[1])),
				new ParseLocationFormatC("Timetable - Period of Operation", "", "y_ttb_periodOfOperation", Integer.valueOf(BIASParseConfigPageController.y_getTtb_periodOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_periodOfOperation()[1])),
				new ParseLocationFormatC("Timetable - Day of Operation", "", "y_ttb_dayOfOperation", Integer.valueOf(BIASParseConfigPageController.y_getTtb_dayOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_dayOfOperation()[1])),
				new ParseLocationFormatC("Timetable - Departure Station", "", "y_ttb_departureStation", Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureStation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureStation()[1])),
				new ParseLocationFormatC("Timetable - Passenger STD", "", "y_ttb_passengerSTD", Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTD()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTD()[1])),
				new ParseLocationFormatC("Timetable - Train STD", "", "y_ttb_trainSTD", Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTD()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTD()[1])),
				new ParseLocationFormatC("Timetable - Time Variation Departure", "", "y_ttb_timeVariationDeparture", Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationDeparture()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationDeparture()[1])),
				new ParseLocationFormatC("Timetable - Departure Terminal", "", "y_ttb_departureTerminal", Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureTerminal()[1])),
				new ParseLocationFormatC("Timetable - Arrival Station", "", "y_ttb_arrivalStation", Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalStation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalStation()[1])),
				new ParseLocationFormatC("Timetable - Train STA", "", "y_ttb_trainSTA", Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTA()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTA()[1])),
				new ParseLocationFormatC("Timetable - Passenger STA", "", "y_ttb_passengerSTA", Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTA()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTA()[1])),
				new ParseLocationFormatC("Timetable - Time Variation Arrival", "", "y_ttb_timeVariationArrival", Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationArrival()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationArrival()[1])),
				new ParseLocationFormatC("Timetable - Arrival Terminal", "", "y_ttb_arrivalTerminal", Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalTerminal()[1])),
				new ParseLocationFormatC("Timetable - Service Type", "", "y_ttb_serviceType", Integer.valueOf(BIASParseConfigPageController.y_getTtb_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_serviceType()[1])),
				new ParseLocationFormatC("Trailer - Company Code", "", "y_trl_companyCode", Integer.valueOf(BIASParseConfigPageController.y_getTrl_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_companyCode()[1])),
				new ParseLocationFormatC("Trailer - Start Date", "", "y_trl_startDate", Integer.valueOf(BIASParseConfigPageController.y_getTrl_startDate()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_startDate()[1])),
				new ParseLocationFormatC("Trailer - Serial Number Check Reference", "", "y_trl_serialNumberCheckReference", Integer.valueOf(BIASParseConfigPageController.y_getTrl_serialNumberCheckReference()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_serialNumberCheckReference()[1])),
				new ParseLocationFormatC("Trailer - Continuation End Code", "", "y_trl_continuationEndCode", Integer.valueOf(BIASParseConfigPageController.y_getTrl_continuationEndCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_continuationEndCode()[1]))
				);
		parseLocationsTable12.setItems(parseData12);

		parameterName12.setReorderable(false);
		displayedName12.setReorderable(false);
		registryKey12.setReorderable(false);
		startColumn12.setReorderable(false);
		endColumn12.setReorderable(false);

		// Table 13 - TPC
		parameterName13.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("parameterName"));
		registryKey13.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, String>("registryKey"));
		startColumn13.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("startColumn"));
		endColumn13.setCellValueFactory(new PropertyValueFactory<ParseLocationFormatB, Integer>("endColumn"));

		parameterName13.setSortable(false);
		registryKey13.setSortable(false);
		startColumn13.setSortable(false);
		endColumn13.setSortable(false);

		parameterName13.getStyleClass().add("header-style");
		registryKey13.getStyleClass().add("header-style");
		startColumn13.setStyle( "-fx-alignment: CENTER;");
		endColumn13.setStyle( "-fx-alignment: CENTER;");

		parseData13.addAll(new ParseLocationFormatB("Node", "p_node", Integer.valueOf(BIASParseConfigPageController.p_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.p_getNode()[1])),
				new ParseLocationFormatB("Field Marker", "p_fieldMarker", Integer.valueOf(BIASParseConfigPageController.p_getFieldMarker()[0]), Integer.valueOf(BIASParseConfigPageController.p_getFieldMarker()[1])),
				new ParseLocationFormatB("Design Speed", "p_designSpeed", Integer.valueOf(BIASParseConfigPageController.p_getDesignSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.p_getDesignSpeed()[1])),
				new ParseLocationFormatB("Current Speed", "p_currentSpeed", Integer.valueOf(BIASParseConfigPageController.p_getCurrentSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.p_getCurrentSpeed()[1]))
				);

		parseLocationsTable13.setItems(parseData13);

		parameterName13.setReorderable(false);
		registryKey13.setReorderable(false);
		startColumn13.setReorderable(false);
		endColumn13.setReorderable(false);
	}

	@FXML private void handleRestoreDefaultsButton(ActionEvent event) 
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("All parsing parameters WILL BE RESET to factory defaults!");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			// Remove parameters
			// Table 1
			prefs.remove("x_trainCat");
			prefs.remove("x_trainCount");
			prefs.remove("x_trainMiles");
			prefs.remove("x_speed");
			prefs.remove("x_otp");
			prefs.remove("x_elapsedRunTime");
			prefs.remove("x_idealRunTime");

			// Table 2
			prefs.remove("x_rtcVersion");
			prefs.remove("x_resultsInvalid");

			// Table 3
			prefs.remove("l_linkOriginNode");
			prefs.remove("l_linkDestinationNode");
			prefs.remove("l_linkClass");
			prefs.remove("l_linkDirection");
			prefs.remove("l_linkMaxPassengerSpeed");
			prefs.remove("l_linkMaxThroughSpeed");
			prefs.remove("l_linkMaxLocalSpeed");

			// Table 4
			prefs.remove("n_node");
			prefs.remove("n_nodeName");
			prefs.remove("n_rtcMarker");
			prefs.remove("n_fieldMarker");
			prefs.remove("n_switchType");
			prefs.remove("n_isASignal");
			prefs.remove("n_trackNumber");

			// Table 5
			prefs.remove("s_signalBeginNode");
			prefs.remove("s_signalEndNode");
			prefs.remove("s_signalType");
			prefs.remove("s_signalDirection");
			prefs.remove("s_trailingSignalBeginNode");
			prefs.remove("s_trailingSignalEndNode");

			// Table 6
			prefs.remove("t_trainSymbol");
			prefs.remove("t_trainType");
			prefs.remove("t_atcEquipped");
			prefs.remove("t_ptcEquipped");

			// Table 7
			prefs.remove("r_trainSymbol");
			prefs.remove("r_rtcIncrement");
			prefs.remove("r_node");
			prefs.remove("r_headEndSpeed");
			prefs.remove("r_headEndDepartureTime");
			prefs.remove("r_tailEndDepartureTime");
			prefs.remove("r_cumulativeElapsedTime");
			prefs.remove("r_aspect");
			prefs.remove("r_direction");

			// Table 8
			prefs.remove("o_rtcVersion");
			prefs.remove("o_units");
			prefs.remove("o_summaryReportTimeFormat");
			prefs.remove("o_simulationBeginDay");
			prefs.remove("o_simulationBeginTime");
			prefs.remove("o_simulationDuration");
			prefs.remove("o_warmUpExclusion");
			prefs.remove("o_coolDownExclusion");
			prefs.remove("o_commaDelimitedRouteFile");
			prefs.remove("o_allNodesInRouteReport");
			prefs.remove("o_showSeedTrainsInRouteReport");
			prefs.remove("o_trainGroupName");
			prefs.remove("o_trainGroupAbbreviation");
			prefs.remove("o_trainTypeName");
			prefs.remove("o_trainTypeGroup");

			// Table 9
			prefs.remove("w_lineName");
			prefs.remove("w_col1");
			prefs.remove("w_col2");
			prefs.remove("w_col3");
			prefs.remove("w_col4");
			prefs.remove("w_col5");
			prefs.remove("w_col6");
			prefs.remove("w_col7");
			prefs.remove("w_col8");
			prefs.remove("w_col9");
			prefs.remove("w_col10");
			prefs.remove("w_col11");
			prefs.remove("w_col12");

			// Table 10
			prefs.remove("i_allowAlphaDOW");

			// Table 11
			prefs.remove("z_all_recordType");
			prefs.remove("z_all_recordSerialNumber");
			prefs.remove("z_hdr_titleOfContents");
			prefs.remove("z_hdr_dataSetSerialNumber");
			prefs.remove("z_car_timeMode");
			prefs.remove("z_car_airlineDesignator");
			prefs.remove("z_car_creatorReference");
			prefs.remove("z_car_periodOfValidity");
			prefs.remove("z_car_creationDate");
			prefs.remove("z_car_titleOfData");
			prefs.remove("z_car_releaseDate");
			prefs.remove("z_flr_airlineDesignator");
			prefs.remove("z_flr_flightNumber");
			prefs.remove("z_flr_itineraryVariationIdentifier");
			prefs.remove("z_flr_legSequenceNumber");
			prefs.remove("z_flr_serviceType");
			prefs.remove("z_flr_periodOfOperation");
			prefs.remove("z_flr_dayOfOperation");
			prefs.remove("z_flr_departureStation");
			prefs.remove("z_flr_passengerSTD");
			prefs.remove("z_flr_aircraftSTD");
			prefs.remove("z_flr_timeVariationDeparture");
			prefs.remove("z_flr_departureTerminal");
			prefs.remove("z_flr_arrivalStation");
			prefs.remove("z_flr_aircraftSTA");
			prefs.remove("z_flr_passengerSTA");
			prefs.remove("z_flr_timeVariationArrival");
			prefs.remove("z_flr_arrivalTerminal");
			prefs.remove("z_flr_aircraftType");
			prefs.remove("z_flr_onwardAirlineDesignator");
			prefs.remove("z_flr_onwardFlightNumber");
			prefs.remove("z_flr_onwardFlightTransitLayover");
			prefs.remove("z_flr_aircraftConfiguration");
			prefs.remove("z_flr_dateVariation");
			prefs.remove("z_seg_airlineDesignator");
			prefs.remove("z_seg_flightNumber");
			prefs.remove("z_seg_itineraryVariationNumber");
			prefs.remove("z_seg_legSequenceNumber");
			prefs.remove("z_seg_serviceType");
			prefs.remove("z_seg_boardPointIndicator");
			prefs.remove("z_seg_offPointIndicator");
			prefs.remove("z_seg_dataElementIdentifier");
			prefs.remove("z_seg_segmentBoardPoint");
			prefs.remove("z_seg_segmentOffPoint");
			prefs.remove("z_seg_data");
			prefs.remove("z_trl_airlineDesignator");
			prefs.remove("z_trl_releaseDate");
			prefs.remove("z_trl_serialNumberCheckReference");
			prefs.remove("z_trl_continuationEndCode");

			// Table 12
			prefs.remove("y_all_recordType");
			prefs.remove("y_all_recordSerialNumber");
			prefs.remove("y_hdr_titleOfContents");
			prefs.remove("y_com_timeMode");
			prefs.remove("y_com_companyCode");
			prefs.remove("y_com_description");
			prefs.remove("y_com_periodOfValidity");
			prefs.remove("y_com_creationDate");
			prefs.remove("y_ttb_companyCode");
			prefs.remove("y_ttb_trainNumber");
			prefs.remove("y_ttb_itineraryVariationIdentifier");
			prefs.remove("y_ttb_legSequenceNumber");
			prefs.remove("y_ttb_commercialCategory");
			prefs.remove("y_ttb_periodOfOperation");
			prefs.remove("y_ttb_dayOfOperation");
			prefs.remove("y_ttb_departureStation");
			prefs.remove("y_ttb_passengerSTD");
			prefs.remove("y_ttb_trainSTD");
			prefs.remove("y_ttb_timeVariationDeparture");
			prefs.remove("y_ttb_departureTerminal");
			prefs.remove("y_ttb_arrivalStation");
			prefs.remove("y_ttb_trainSTA");
			prefs.remove("y_ttb_passengerSTA");
			prefs.remove("y_ttb_timeVariationArrival");
			prefs.remove("y_ttb_arrivalTerminal");
			prefs.remove("y_ttb_serviceType");
			prefs.remove("y_trl_companyCode");
			prefs.remove("y_trl_startDate");
			prefs.remove("y_trl_serialNumberCheckReference");
			prefs.remove("y_trl_continuationEndCode");

			// Table 13
			prefs.remove("p_node");
			prefs.remove("p_fieldMarker");
			prefs.remove("p_designSpeed");
			prefs.remove("p_currentSpeed");

			// Add parameters
			getParseParameters();

			// Set viewOnly radio button and disable editing
			viewEntriesOnlyRadioButton.setSelected(true);
			parseLocationsTable1.setEditable(false);
			parseLocationsTable2.setEditable(false);
			parseLocationsTable3.setEditable(false);
			parseLocationsTable4.setEditable(false);
			parseLocationsTable5.setEditable(false);
			parseLocationsTable6.setEditable(false);
			parseLocationsTable7.setEditable(false);
			parseLocationsTable8.setEditable(false);
			parseLocationsTable9.setEditable(false);
			parseLocationsTable10.setEditable(false);
			parseLocationsTable11.setEditable(false);
			parseLocationsTable12.setEditable(false);
			parseLocationsTable13.setEditable(false);

			// Refresh parseData from registry for table 1 values
			parseData1.clear();
			parseData1.addAll(new ParseLocationFormatA("Train Category", "x_trainCat", Integer.valueOf(x_getTrainCat()[0]), Integer.valueOf(x_getTrainCat()[1]), Integer.valueOf(x_getTrainCat()[2]), Integer.valueOf(x_getTrainCat()[3])),
					new ParseLocationFormatA("Train Count", "x_trainCount", Integer.valueOf(x_getTrainCount()[0]), Integer.valueOf(x_getTrainCount()[1]), Integer.valueOf(x_getTrainCount()[2]), Integer.valueOf(x_getTrainCount()[3])),
					new ParseLocationFormatA("Train Miles", "x_trainMiles", Integer.valueOf(x_getTrainMiles()[0]), Integer.valueOf(x_getTrainMiles()[1]), Integer.valueOf(x_getTrainMiles()[2]), Integer.valueOf(x_getTrainMiles()[3])),
					new ParseLocationFormatA("Velocity", "x_speed", Integer.valueOf(x_getSpeed()[0]), Integer.valueOf(x_getSpeed()[1]), Integer.valueOf(x_getSpeed()[2]), Integer.valueOf(x_getSpeed()[3])),
					new ParseLocationFormatA("OTP", "x_otp", Integer.valueOf(x_getOTP()[0]), Integer.valueOf(x_getOTP()[1]), Integer.valueOf(x_getOTP()[2]), Integer.valueOf(x_getOTP()[3])),
					new ParseLocationFormatA("Elapsed Run Time", "x_elapsedRunTime", Integer.valueOf(x_getElapsedRunTime()[0]), Integer.valueOf(x_getElapsedRunTime()[1]), Integer.valueOf(x_getElapsedRunTime()[2]), Integer.valueOf(x_getElapsedRunTime()[3])),
					new ParseLocationFormatA("Ideal Run Time", "x_idealRunTime", Integer.valueOf(x_getIdealRunTime()[0]), Integer.valueOf(x_getIdealRunTime()[1]), Integer.valueOf(x_getIdealRunTime()[2]), Integer.valueOf(x_getIdealRunTime()[3]))
					);

			typeStartColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			typeEndColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			groupStartColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			groupEndColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 2 values
			parseData2.clear();
			parseData2.addAll(new ParseLocationFormatB("RTC Version", "x_rtcVersion", Integer.valueOf(BIASParseConfigPageController.x_getRtcVersionExtract()[0]), Integer.valueOf(BIASParseConfigPageController.x_getRtcVersionExtract()[1])),
					new ParseLocationFormatB("Results Invalid", "x_resultsInvalid", Integer.valueOf(BIASParseConfigPageController.x_getInvalidResults()[0]), Integer.valueOf(BIASParseConfigPageController.x_getInvalidResults()[1]))
					);

			startColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 3 values
			parseData3.clear();
			parseData3.addAll(new ParseLocationFormatB("Link Origin Node", "l_linkOriginNode", Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkOriginNode()[1])),
					new ParseLocationFormatB("Link Destination Node", "l_linkDestinationNode", Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDestinationNode()[1])),
					new ParseLocationFormatB("Link Class", "l_linkClass", Integer.valueOf(BIASParseConfigPageController.l_getLinkClass()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkClass()[1])),
					new ParseLocationFormatB("Link Direction", "l_linkDirection", Integer.valueOf(BIASParseConfigPageController.l_getLinkDirection()[0]), Integer.valueOf(BIASParseConfigPageController.l_getLinkDirection()[1])),
					new ParseLocationFormatB("Link Max Passenger Speed", "l_linkMaxPassengerSpeed", Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxPassengerSpeed()[0]), Integer.valueOf(l_getLinkMaxPassengerSpeed()[1])),
					new ParseLocationFormatB("Link Max Through Speed", "l_linkMaxThroughSpeed", Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxThroughSpeed()[0]), Integer.valueOf(l_getLinkMaxThroughSpeed()[1])),
					new ParseLocationFormatB("Link Max Local Speed", "l_linkMaxLocalSpeed", Integer.valueOf(BIASParseConfigPageController.l_getLinkMaxLocalSpeed()[0]), Integer.valueOf(l_getLinkMaxLocalSpeed()[1]))
					);

			startColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 4 values
			parseData4.clear();
			parseData4.addAll(new ParseLocationFormatB("Node", "n_node", Integer.valueOf(BIASParseConfigPageController.n_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.n_getNode()[1])),
					new ParseLocationFormatB("Node Name", "n_nodeName", Integer.valueOf(BIASParseConfigPageController.n_getNodeName()[0]), Integer.valueOf(BIASParseConfigPageController.n_getNodeName()[1])),
					new ParseLocationFormatB("RTC Marker", "n_rtcMarker", Integer.valueOf(BIASParseConfigPageController.n_getRtcMarker()[0]), Integer.valueOf(BIASParseConfigPageController.n_getRtcMarker()[1])),
					new ParseLocationFormatB("Field Marker", "n_fieldMarker", Integer.valueOf(BIASParseConfigPageController.n_getFieldMarker()[0]), Integer.valueOf(BIASParseConfigPageController.n_getFieldMarker()[1])),
					new ParseLocationFormatB("Switch Type", "n_switchType", Integer.valueOf(BIASParseConfigPageController.n_getSwitchType()[0]), Integer.valueOf(BIASParseConfigPageController.n_getSwitchType()[1])),
					new ParseLocationFormatB("Is a Signal", "n_isASignal", Integer.valueOf(BIASParseConfigPageController.n_getIsASignal()[0]), Integer.valueOf(BIASParseConfigPageController.n_getIsASignal()[1])),
					new ParseLocationFormatB("Track Number", "n_trackNumber", Integer.valueOf(BIASParseConfigPageController.n_getTrackNumber()[0]), Integer.valueOf(BIASParseConfigPageController.n_getTrackNumber()[1]))
					);

			startColumn4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 5 values
			parseData5.clear();
			parseData5.addAll(new ParseLocationFormatB("Begin Node", "s_signalBeginNode", Integer.valueOf(BIASParseConfigPageController.s_getSignalBeginNode()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalBeginNode()[1])),
					new ParseLocationFormatB("End Node", "s_signalEndNode", Integer.valueOf(BIASParseConfigPageController.s_getSignalEndNode()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalEndNode()[1])),
					new ParseLocationFormatB("Signal Type", "s_signalType", Integer.valueOf(BIASParseConfigPageController.s_getSignalType()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalType()[1])),
					new ParseLocationFormatB("Signal Direction", "s_signalDirection", Integer.valueOf(BIASParseConfigPageController.s_getSignalDirection()[0]), Integer.valueOf(BIASParseConfigPageController.s_getSignalDirection()[1])),
					new ParseLocationFormatB("Trailing Signal Begin", "s_trailingSignalBeginNode", Integer.valueOf(BIASParseConfigPageController.s_getTrailingSignalBeginNode()[0]), Integer.valueOf(BIASParseConfigPageController.s_getTrailingSignalBeginNode()[1])),
					new ParseLocationFormatB("Trailing Signal End", "s_trailingSignalEndNode", Integer.valueOf(BIASParseConfigPageController.s_getTrailingSignalEndNode()[0]), Integer.valueOf(BIASParseConfigPageController.s_getTrailingSignalEndNode()[1]))
					);

			startColumn5.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn5.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 6 values
			parseData6.clear();
			parseData6.addAll(new ParseLocationFormatB("Train Symbol", "t_trainSymbol", Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainSymbol()[1])),
					new ParseLocationFormatB("Train Type", "t_trainType", Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[0]), Integer.valueOf(BIASParseConfigPageController.t_getTrainType()[1])),
					new ParseLocationFormatB("ATC Equipped", "t_atcEquipped", Integer.valueOf(BIASParseConfigPageController.t_getAtcEquipped()[0]), Integer.valueOf(BIASParseConfigPageController.t_getAtcEquipped()[1])),
					new ParseLocationFormatB("PTC Equpped", "t_ptcEquipped", Integer.valueOf(BIASParseConfigPageController.t_getPtcEquipped()[0]), Integer.valueOf(BIASParseConfigPageController.t_getPtcEquipped()[1]))
					);

			startColumn6.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn6.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 7 values
			parseData7.clear();
			parseData7.addAll(new ParseLocationFormatB("Train Symbol", "r_trainSymbol", Integer.valueOf(BIASParseConfigPageController.r_getTrainSymbol()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTrainSymbol()[1])),
					new ParseLocationFormatB("RTC Increment", "r_rtcIncrement", Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[0]), Integer.valueOf(BIASParseConfigPageController.r_getRtcIncrement()[1])),
					new ParseLocationFormatB("Node", "r_node", Integer.valueOf(BIASParseConfigPageController.r_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.r_getNode()[1])),
					new ParseLocationFormatB("Head End Speed", "r_headEndSpeed", Integer.valueOf(BIASParseConfigPageController.r_getHeadEndSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndSpeed()[1])),
					new ParseLocationFormatB("Head End Arrival Time", "r_headEndDepartureTime", Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getHeadEndArrivalTime()[1])),
					new ParseLocationFormatB("Tail End Departure Time", "r_tailEndDepartureTime", Integer.valueOf(BIASParseConfigPageController.r_getTailEndDepartureTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getTailEndDepartureTime()[1])),
					new ParseLocationFormatB("Cumulative Elapsed", "r_cumulativeElapsedTime", Integer.valueOf(BIASParseConfigPageController.r_getCumulativeElapsedTime()[0]), Integer.valueOf(BIASParseConfigPageController.r_getCumulativeElapsedTime()[1])),
					new ParseLocationFormatB("Aspect", "r_aspect", Integer.valueOf(BIASParseConfigPageController.r_getAspect()[0]), Integer.valueOf(BIASParseConfigPageController.r_getAspect()[1])),
					new ParseLocationFormatB("Direction", "r_direction", Integer.valueOf(BIASParseConfigPageController.r_getDirection()[0]), Integer.valueOf(BIASParseConfigPageController.r_getDirection()[1]))
					);

			startColumn7.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn7.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 8 values
			parseData8.clear();
			parseData8.addAll(new ParseLocationFormatB("RTC Version", "o_rtcVersion", Integer.valueOf(BIASParseConfigPageController.o_getRtcVersion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getRtcVersion()[1])),
					new ParseLocationFormatB("Units", "o_units", Integer.valueOf(BIASParseConfigPageController.o_getUnits()[0]), Integer.valueOf(BIASParseConfigPageController.o_getUnits()[1])),
					new ParseLocationFormatB("Summary Report Time Format", "o_summaryReportTimeFormat", Integer.valueOf(BIASParseConfigPageController.o_getSummaryReportTimeFormat()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSummaryReportTimeFormat()[1])),
					new ParseLocationFormatB("Simulation Begin Day", "o_simulationBeginDay", Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[1])),
					new ParseLocationFormatB("Simulation Begin Time", "o_simulationBeginTime", Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[1])),
					new ParseLocationFormatB("Simulation Duration", "o_simulationDuration", Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[1])),
					new ParseLocationFormatB("Warm-up Exclusion", "o_warmUpExclusion", Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[1])),
					new ParseLocationFormatB("Cool-down Exclusion", "o_coolDownExclusion", Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[1])),
					new ParseLocationFormatB("Route Is Comma Delimited", "o_commaDelimitedRouteFile", Integer.valueOf(BIASParseConfigPageController.o_getCommaDelimited()[0]), Integer.valueOf(BIASParseConfigPageController.o_getCommaDelimited()[1])),
					new ParseLocationFormatB("All Nodes in Route Report", "o_allNodesInRouteReport", Integer.valueOf(BIASParseConfigPageController.o_getAllNodesInRouteReport()[0]), Integer.valueOf(BIASParseConfigPageController.o_getAllNodesInRouteReport()[1])),
					new ParseLocationFormatB("Show Seed Trains in Route Report", "o_showSeedTrainsInRouteReport", Integer.valueOf(BIASParseConfigPageController.o_getShowSeedTrainsInRouteReport()[0]), Integer.valueOf(BIASParseConfigPageController.o_getShowSeedTrainsInRouteReport()[1])),
					new ParseLocationFormatB("Train Group Name", "o_trainGroupName", Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupName()[1])),
					new ParseLocationFormatB("Train Group Abbreviation", "o_trainGroupAbbreviation", Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupAbbreviation()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainGroupAbbreviation()[1])),
					new ParseLocationFormatB("Train Type Name", "o_trainTypeName", Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeName()[1])),
					new ParseLocationFormatB("Train Type Group", "o_trainTypeGroup", Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeGroup()[0]), Integer.valueOf(BIASParseConfigPageController.o_getTrainTypeGroup()[1]))
					);

			startColumn8.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn8.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 9 values
			parseData9.clear();
			parseData9.addAll(new ParseLocationFormatB("Line Name", "w_lineName", Integer.valueOf(BIASParseConfigPageController.w_getLineName()[0]), Integer.valueOf(BIASParseConfigPageController.w_getLineName()[1])),
					new ParseLocationFormatB("1st Column Entry", "w_col1", Integer.valueOf(BIASParseConfigPageController.w_getCol1()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol1()[1])),
					new ParseLocationFormatB("2nd Column Entry", "w_col2", Integer.valueOf(BIASParseConfigPageController.w_getCol2()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol2()[1])),
					new ParseLocationFormatB("3rd Column Entry", "w_col3", Integer.valueOf(BIASParseConfigPageController.w_getCol3()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol3()[1])),
					new ParseLocationFormatB("4th Column Entry", "w_col4", Integer.valueOf(BIASParseConfigPageController.w_getCol4()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol4()[1])),
					new ParseLocationFormatB("5th Column Entry", "w_col5", Integer.valueOf(BIASParseConfigPageController.w_getCol5()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol5()[1])),
					new ParseLocationFormatB("6th Column Entry", "w_col6", Integer.valueOf(BIASParseConfigPageController.w_getCol6()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol6()[1])),
					new ParseLocationFormatB("7th Column Entry", "w_col7", Integer.valueOf(BIASParseConfigPageController.w_getCol7()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol7()[1])),
					new ParseLocationFormatB("8th Column Entry", "w_col8", Integer.valueOf(BIASParseConfigPageController.w_getCol8()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol8()[1])),
					new ParseLocationFormatB("9th Column Entry", "w_col9", Integer.valueOf(BIASParseConfigPageController.w_getCol9()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol9()[1])),
					new ParseLocationFormatB("10th Column Entry", "w_col10", Integer.valueOf(BIASParseConfigPageController.w_getCol10()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol10()[1])),
					new ParseLocationFormatB("11th Column Entry", "w_col11", Integer.valueOf(BIASParseConfigPageController.w_getCol11()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol11()[1])),
					new ParseLocationFormatB("12th Column Entry", "w_col12", Integer.valueOf(BIASParseConfigPageController.w_getCol12()[0]), Integer.valueOf(BIASParseConfigPageController.w_getCol12()[1]))
					);
			startColumn9.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn9.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 10 values
			parseData10.clear();
			parseData10.addAll(new ParseLocationFormatB("Allow Alpha DOW", "i_allowAlphaDOW", Integer.valueOf(BIASParseConfigPageController.i_getAllowAlphaDOW()[0]), Integer.valueOf(BIASParseConfigPageController.i_getAllowAlphaDOW()[1]))
					);
			startColumn10.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn10.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 11 values
			parseData11.clear();
			parseData11.addAll(new ParseLocationFormatC("All Rows - Record Type", "", "z_all_recordType", Integer.valueOf(BIASParseConfigPageController.z_getAll_recordType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordType()[1])),
					new ParseLocationFormatC("All Rows - Record Serial Number", "", "z_all_recordSerialNumber", Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getAll_recordSerialNumber()[1])),
					new ParseLocationFormatC("Header - Title of Contents", "", "z_hdr_titleOfContents", Integer.valueOf(BIASParseConfigPageController.z_getHdr_titleOfContents()[0]), Integer.valueOf(BIASParseConfigPageController.z_getHdr_titleOfContents()[1])),
					new ParseLocationFormatC("Header - Data Set Serial Number", "", "z_hdr_dataSetSerialNumber", Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getHdr_dataSetSerialNumber()[1])),
					new ParseLocationFormatC("Carrier - Time Mode", "", "z_car_timeMode", Integer.valueOf(BIASParseConfigPageController.z_getCar_timeMode()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_timeMode()[1])),
					new ParseLocationFormatC("Carrier - Airline Designator", "Railroad ID", "z_car_airlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getCar_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_airlineDesignator()[1])),
					new ParseLocationFormatC("Carrier - Creator Reference", "", "z_car_creatorReference", Integer.valueOf(BIASParseConfigPageController.z_getCar_creatorReference()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_creatorReference()[1])),
					new ParseLocationFormatC("Carrier - Period of Validity", "", "z_car_periodOfValidity", Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_periodOfValidity()[1])),
					new ParseLocationFormatC("Carrier - Creation Date", "", "z_car_creationDate", Integer.valueOf(BIASParseConfigPageController.z_getCar_creationDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_creationDate()[1])),
					new ParseLocationFormatC("Carrier - Title of Data", "", "z_car_titleOfData", Integer.valueOf(BIASParseConfigPageController.z_getCar_titleOfData()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_titleOfData()[1])),
					new ParseLocationFormatC("Carrier - Release Date", "", "z_car_releaseDate", Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getCar_releaseDate()[1])),
					new ParseLocationFormatC("Flight Leg - Airline Designator", "Railroad ID", "z_flr_airlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getFlr_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_airlineDesignator()[1])),
					new ParseLocationFormatC("Flight Leg - Flight Number", "Train Number", "z_flr_flightNumber", Integer.valueOf(BIASParseConfigPageController.z_getFlr_flightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_flightNumber()[1])),
					new ParseLocationFormatC("Flight Leg - Itinerary Variation Identifier", "", "z_flr_itineraryVariationIdentifier", Integer.valueOf(BIASParseConfigPageController.z_getFlr_itineraryVariationIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_itineraryVariationIdentifier()[1])),
					new ParseLocationFormatC("Flight Leg - Leg Sequence Number", "", "z_flr_legSequenceNumber", Integer.valueOf(BIASParseConfigPageController.z_getFlr_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_legSequenceNumber()[1])),
					new ParseLocationFormatC("Flight Leg - Service Type", "", "z_flr_serviceType", Integer.valueOf(BIASParseConfigPageController.z_getFlr_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_serviceType()[1])),
					new ParseLocationFormatC("Flight Leg - Period of Operation", "", "z_flr_periodOfOperation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_periodOfOperation()[1])),
					new ParseLocationFormatC("Flight Leg - Day of Operation", "", "z_flr_dayOfOperation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_dayOfOperation()[1])),
					new ParseLocationFormatC("Flight Leg - Departure Station", "", "z_flr_departureStation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureStation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureStation()[1])),
					new ParseLocationFormatC("Flight Leg - Passenger STD", "", "z_flr_passengerSTD", Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTD()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTD()[1])),
					new ParseLocationFormatC("Flight Leg - Aircraft STD", "Train STD", "z_flr_aircraftSTD", Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTD()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTD()[1])),
					new ParseLocationFormatC("Flight Leg - Time Variation Departure", "", "z_flr_timeVariationDeparture", Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationDeparture()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationDeparture()[1])),
					new ParseLocationFormatC("Flight Leg - Departure Terminal", "", "z_flr_departureTerminal", Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_departureTerminal()[1])),
					new ParseLocationFormatC("Flight Leg - Arrival Station", "", "z_flr_arrivalStation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalStation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalStation()[1])),
					new ParseLocationFormatC("Flight Leg - Aircraft STA", "Train STA", "z_flr_aircraftSTA", Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTA()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftSTA()[1])),
					new ParseLocationFormatC("Flight Leg - Passenger STA", "", "z_flr_passengerSTA", Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTA()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_passengerSTA()[1])),
					new ParseLocationFormatC("Flight Leg - Time Variation Arrival", "", "z_flr_timeVariationArrival", Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationArrival()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_timeVariationArrival()[1])),
					new ParseLocationFormatC("Flight Leg - Arrival Terminal", "", "z_flr_arrivalTerminal", Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_arrivalTerminal()[1])),
					new ParseLocationFormatC("Flight Leg - Aircraft Type", "Train Type", "z_flr_aircraftType", Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftType()[1])),
					new ParseLocationFormatC("Flight Leg - Onward Airline Designator", "Onward Railroad ID", "z_flr_onwardAirlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardAirlineDesignator()[1])),
					new ParseLocationFormatC("Flight Leg - Onward Flight Number", "Onward Train Number", "z_flr_onwardFlightNumber", Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightNumber()[1])),
					new ParseLocationFormatC("Flight Leg - Onward Flight Transit Layover", "Onward Train Transit Layover", "z_flr_onwardFlightTransitLayover", Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_onwardFlightTransitLayover()[1])),
					new ParseLocationFormatC("Flight Leg - Aircraft Configuration", "Train Configuration", "z_flr_aircraftConfiguration", Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftConfiguration()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_aircraftConfiguration()[1])),
					new ParseLocationFormatC("Flight Leg - Date Variation", "", "z_flr_dateVariation", Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[0]), Integer.valueOf(BIASParseConfigPageController.z_getFlr_dateVariation()[1])),
					new ParseLocationFormatC("Segment - Airline Designator", "Railroad ID", "z_seg_airlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getSeg_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_airlineDesignator()[1])),
					new ParseLocationFormatC("Segment - Flight Number", "Train Number", "z_seg_flightNumber", Integer.valueOf(BIASParseConfigPageController.z_getSeg_flightNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_flightNumber()[1])),
					new ParseLocationFormatC("Segment - Itinerary Variation Number", "", "z_seg_itineraryVariationNumber", Integer.valueOf(BIASParseConfigPageController.z_getSeg_itineraryVariationNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_itineraryVariationNumber()[1])),
					new ParseLocationFormatC("Segment - Leg Sequence Number", "", "z_seg_legSequenceNumber", Integer.valueOf(BIASParseConfigPageController.z_getSeg_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_legSequenceNumber()[1])),
					new ParseLocationFormatC("Segment - Service Type", "", "z_seg_serviceType", Integer.valueOf(BIASParseConfigPageController.z_getSeg_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_serviceType()[1])),
					new ParseLocationFormatC("Segment - Board Point Indicator", "", "z_seg_boardPointIndicator", Integer.valueOf(BIASParseConfigPageController.z_getSeg_boardPointIndicator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_boardPointIndicator()[1])),
					new ParseLocationFormatC("Segment - Off Point Indicator", "", "z_seg_offPointIndicator", Integer.valueOf(BIASParseConfigPageController.z_getSeg_offPointIndicator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_offPointIndicator()[1])),
					new ParseLocationFormatC("Segment - Data Element Identifier", "", "z_seg_dataElementIdentifier", Integer.valueOf(BIASParseConfigPageController.z_getSeg_dataElementIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_dataElementIdentifier()[1])),
					new ParseLocationFormatC("Segment - Segment Board Point", "", "z_seg_segmentBoardPoint", Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentBoardPoint()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentBoardPoint()[1])),
					new ParseLocationFormatC("Segment - Segment Off Point", "", "z_seg_segmentOffPoint", Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentOffPoint()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_segmentOffPoint()[1])),
					new ParseLocationFormatC("Segment - Data", "", "z_seg_data", Integer.valueOf(BIASParseConfigPageController.z_getSeg_data()[0]), Integer.valueOf(BIASParseConfigPageController.z_getSeg_data()[1])),
					new ParseLocationFormatC("Trailer - Airline Designator", "Railroad ID", "z_trl_airlineDesignator", Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_airlineDesignator()[1])),
					new ParseLocationFormatC("Trailer - Release Date", "", "z_trl_releaseDate", Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_releaseDate()[1])),
					new ParseLocationFormatC("Trailer - Serial Number Check Reference", "", "z_trl_serialNumberCheckReference", Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_serialNumberCheckReference()[1])),
					new ParseLocationFormatC("Trailer - Continuation End Code", "", "z_trl_continuationEndCode", Integer.valueOf(BIASParseConfigPageController.z_getTrl_continuationEndCode()[0]), Integer.valueOf(BIASParseConfigPageController.z_getTrl_continuationEndCode()[1]))
					);
			startColumn11.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn11.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 12 values
			parseData12.clear();
			parseData12.addAll(new ParseLocationFormatC("All Rows - Record Type", "", "y_all_recordType", Integer.valueOf(BIASParseConfigPageController.y_getAll_recordType()[0]), Integer.valueOf(BIASParseConfigPageController.y_getAll_recordType()[1])),
					new ParseLocationFormatC("All Rows - Record Serial Number", "", "y_all_recordSerialNumber", Integer.valueOf(BIASParseConfigPageController.y_getAll_recordSerialNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getAll_recordSerialNumber()[1])),
					new ParseLocationFormatC("Header - Title of Contents", "", "y_hdr_titleOfContents", Integer.valueOf(BIASParseConfigPageController.y_getHdr_titleOfContents()[0]), Integer.valueOf(BIASParseConfigPageController.y_getHdr_titleOfContents()[1])),
					new ParseLocationFormatC("Company - Time Mode", "", "y_com_timeMode", Integer.valueOf(BIASParseConfigPageController.y_getCom_timeMode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_timeMode()[1])),
					new ParseLocationFormatC("Company - Company Code", "", "y_com_companyCode", Integer.valueOf(BIASParseConfigPageController.y_getCom_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_companyCode()[1])),
					new ParseLocationFormatC("Company - Description", "", "y_com_description", Integer.valueOf(BIASParseConfigPageController.y_getCom_description()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_description()[1])),
					new ParseLocationFormatC("Company - Period of Validity", "", "y_com_periodOfValidity", Integer.valueOf(BIASParseConfigPageController.y_getCom_periodOfValidity()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_periodOfValidity()[1])),
					new ParseLocationFormatC("Company - Creation Date", "", "y_com_creationDate", Integer.valueOf(BIASParseConfigPageController.y_getCom_creationDate()[0]), Integer.valueOf(BIASParseConfigPageController.y_getCom_creationDate()[1])),
					new ParseLocationFormatC("Timetable - Company Code", "", "y_ttb_companyCode", Integer.valueOf(BIASParseConfigPageController.y_getTtb_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_companyCode()[1])),
					new ParseLocationFormatC("Timetable - Train Number", "", "y_ttb_trainNumber", Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainNumber()[1])),
					new ParseLocationFormatC("Timetable - Itinerary Variation Identifier", "", "y_ttb_itineraryVariationIdentifier", Integer.valueOf(BIASParseConfigPageController.y_getTtb_itineraryVariationIdentifier()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_itineraryVariationIdentifier()[1])),
					new ParseLocationFormatC("Timetable - Leg Sequence Number", "", "y_ttb_legSequenceNumber", Integer.valueOf(BIASParseConfigPageController.y_getTtb_legSequenceNumber()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_legSequenceNumber()[1])),
					new ParseLocationFormatC("Timetable - Commercial Category", "", "y_ttb_commercialCategory", Integer.valueOf(BIASParseConfigPageController.y_getTtb_commercialCategory()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_commercialCategory()[1])),
					new ParseLocationFormatC("Timetable - Period of Operation", "", "y_ttb_periodOfOperation", Integer.valueOf(BIASParseConfigPageController.y_getTtb_periodOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_periodOfOperation()[1])),
					new ParseLocationFormatC("Timetable - Day of Operation", "", "y_ttb_dayOfOperation", Integer.valueOf(BIASParseConfigPageController.y_getTtb_dayOfOperation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_dayOfOperation()[1])),
					new ParseLocationFormatC("Timetable - Departure Station", "", "y_ttb_departureStation", Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureStation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureStation()[1])),
					new ParseLocationFormatC("Timetable - Passenger STD", "", "y_ttb_passengerSTD", Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTD()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTD()[1])),
					new ParseLocationFormatC("Timetable - Train STD", "", "y_ttb_trainSTD", Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTD()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTD()[1])),
					new ParseLocationFormatC("Timetable - Time Variation Departure", "", "y_ttb_timeVariationDeparture", Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationDeparture()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationDeparture()[1])),
					new ParseLocationFormatC("Timetable - Departure Terminal", "", "y_ttb_departureTerminal", Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_departureTerminal()[1])),
					new ParseLocationFormatC("Timetable - Arrival Station", "", "y_ttb_arrivalStation", Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalStation()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalStation()[1])),
					new ParseLocationFormatC("Timetable - Train STA", "", "y_ttb_trainSTA", Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTA()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_trainSTA()[1])),
					new ParseLocationFormatC("Timetable - Passenger STA", "", "y_ttb_passengerSTA", Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTA()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_passengerSTA()[1])),
					new ParseLocationFormatC("Timetable - Time Variation Arrival", "", "y_ttb_timeVariationArrival", Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationArrival()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_timeVariationArrival()[1])),
					new ParseLocationFormatC("Timetable - Arrival Terminal", "", "y_ttb_arrivalTerminal", Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalTerminal()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_arrivalTerminal()[1])),
					new ParseLocationFormatC("Timetable - Service Type", "", "y_ttb_serviceType", Integer.valueOf(BIASParseConfigPageController.y_getTtb_serviceType()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTtb_serviceType()[1])),
					new ParseLocationFormatC("Trailer - Company Code", "", "y_trl_companyCode", Integer.valueOf(BIASParseConfigPageController.y_getTrl_companyCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_companyCode()[1])),
					new ParseLocationFormatC("Trailer - Start Date", "", "y_trl_startDate", Integer.valueOf(BIASParseConfigPageController.y_getTrl_startDate()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_startDate()[1])),
					new ParseLocationFormatC("Trailer - Serial Number Check Reference", "", "y_trl_serialNumberCheckReference", Integer.valueOf(BIASParseConfigPageController.y_getTrl_serialNumberCheckReference()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_serialNumberCheckReference()[1])),
					new ParseLocationFormatC("Trailer - Continuation End Code", "", "y_trl_continuationEndCode", Integer.valueOf(BIASParseConfigPageController.y_getTrl_continuationEndCode()[0]), Integer.valueOf(BIASParseConfigPageController.y_getTrl_continuationEndCode()[1]))
					);
			startColumn12.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn12.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

			// Refresh parseData from registry for table 13 values
			parseData13.clear();
			parseData13.addAll(new ParseLocationFormatB("Node", "p_node", Integer.valueOf(BIASParseConfigPageController.p_getNode()[0]), Integer.valueOf(BIASParseConfigPageController.p_getNode()[1])),
					new ParseLocationFormatB("Field Marker", "p_fieldMarker", Integer.valueOf(BIASParseConfigPageController.p_getFieldMarker()[0]), Integer.valueOf(BIASParseConfigPageController.p_getFieldMarker()[1])),
					new ParseLocationFormatB("Design Speed", "p_designSpeed", Integer.valueOf(BIASParseConfigPageController.p_getDesignSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.p_getDesignSpeed()[1])),
					new ParseLocationFormatB("Current Speed", "p_currentSpeed", Integer.valueOf(BIASParseConfigPageController.p_getCurrentSpeed()[0]), Integer.valueOf(BIASParseConfigPageController.p_getCurrentSpeed()[1]))
					);

			startColumn13.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			endColumn13.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		} 
	}

	@FXML private void handleViewEntriesOnlyRadioButton(ActionEvent event) 
	{
		parseLocationsTable1.setEditable(false);
		parseLocationsTable2.setEditable(false);
		parseLocationsTable3.setEditable(false);
		parseLocationsTable4.setEditable(false);
		parseLocationsTable5.setEditable(false);
		parseLocationsTable6.setEditable(false);
		parseLocationsTable7.setEditable(false);
		parseLocationsTable8.setEditable(false);
		parseLocationsTable9.setEditable(false);
		parseLocationsTable10.setEditable(false);
		parseLocationsTable11.setEditable(false);
		parseLocationsTable12.setEditable(false);
		parseLocationsTable13.setEditable(false);

		typeStartColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		typeEndColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		groupStartColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		groupEndColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn5.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn5.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn6.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn6.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn7.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn7.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn8.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn8.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn9.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn9.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn10.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn10.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn11.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn11.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn12.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn12.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		startColumn13.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		endColumn13.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
	}

	@FXML private void handleViewAndEditEntriesRadioButton(ActionEvent event) 
	{
		parseLocationsTable1.setEditable(true);
		parseLocationsTable2.setEditable(true);
		parseLocationsTable3.setEditable(true);
		parseLocationsTable4.setEditable(true);
		parseLocationsTable5.setEditable(true);
		parseLocationsTable6.setEditable(true);
		parseLocationsTable7.setEditable(true);
		parseLocationsTable8.setEditable(true);
		parseLocationsTable9.setEditable(true);
		parseLocationsTable10.setEditable(true);
		parseLocationsTable11.setEditable(true);
		parseLocationsTable12.setEditable(true);
		parseLocationsTable13.setEditable(true);

		// Table 1 - typeStartColumn
		typeStartColumn1.setEditable(true);
		typeStartColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		typeStartColumn1.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatA, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatA, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable1.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1]+","+oldValues[2]+","+oldValues[3];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in typeStartColumn which will then update GUI (as it is observable)
					parseData1.get(row).setTypeStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData1.get(row).setTypeStartColumn(t.getOldValue());
					parseLocationsTable1.getColumns().get(row).setVisible(false);
					parseLocationsTable1.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 1 - typeEndColumn
		typeEndColumn1.setEditable(true);
		typeEndColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		typeEndColumn1.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatA, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatA, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable1.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue()+","+oldValues[2]+","+oldValues[3];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in typeEndColumn which will then update GUI (as it is observable)
					parseData1.get(row).setTypeEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData1.get(row).setTypeEndColumn(t.getOldValue());
					parseLocationsTable1.getColumns().get(row).setVisible(false);
					parseLocationsTable1.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 1 - groupStartColumn
		groupStartColumn1.setEditable(true);
		groupStartColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		groupStartColumn1.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatA, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatA, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable1.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+oldValues[1]+","+t.getNewValue()+","+oldValues[3];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in groupStartColumn which will then update GUI (as it is observable)
					parseData1.get(row).setGroupStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData1.get(row).setGroupStartColumn(t.getOldValue());
					parseLocationsTable1.getColumns().get(row).setVisible(false);
					parseLocationsTable1.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 1 - groupEndColumn
		groupEndColumn1.setEditable(true);
		groupEndColumn1.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		groupEndColumn1.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatA, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatA, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable1.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+oldValues[1]+","+oldValues[2]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in groupStartColumn which will then update GUI (as it is observable)
					parseData1.get(row).setGroupEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData1.get(row).setGroupEndColumn(t.getOldValue());
					parseLocationsTable1.getColumns().get(row).setVisible(false);
					parseLocationsTable1.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 2 - startColumn
		startColumn2.setEditable(true);
		startColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn2.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable2.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData2.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData2.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable2.getColumns().get(row).setVisible(false);
					parseLocationsTable2.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 2 - endColumn
		endColumn2.setEditable(true);
		endColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn2.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable2.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData2.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData2.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable2.getColumns().get(row).setVisible(false);
					parseLocationsTable2.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 3 - startColumn
		startColumn3.setEditable(true);
		startColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn3.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable3.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData3.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData3.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable3.getColumns().get(row).setVisible(false);
					parseLocationsTable3.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 3 - endColumn
		endColumn3.setEditable(true);
		endColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn3.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable3.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData3.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData3.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable3.getColumns().get(row).setVisible(false);
					parseLocationsTable3.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 4 - startColumn
		startColumn4.setEditable(true);
		startColumn4.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn4.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable4.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData4.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData4.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable4.getColumns().get(row).setVisible(false);
					parseLocationsTable4.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 4 - endColumn
		endColumn4.setEditable(true);
		endColumn4.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn4.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable4.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData4.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData4.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable4.getColumns().get(row).setVisible(false);
					parseLocationsTable4.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 5 - startColumn
		startColumn5.setEditable(true);
		startColumn5.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn5.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable5.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData5.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData5.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable5.getColumns().get(row).setVisible(false);
					parseLocationsTable5.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 5 - endColumn
		endColumn5.setEditable(true);
		endColumn5.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn5.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable5.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData5.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData5.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable5.getColumns().get(row).setVisible(false);
					parseLocationsTable5.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 6 - startColumn
		startColumn6.setEditable(true);
		startColumn6.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn6.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable6.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData6.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData6.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable6.getColumns().get(row).setVisible(false);
					parseLocationsTable6.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 6 - endColumn
		endColumn6.setEditable(true);
		endColumn6.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn6.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable6.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData6.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData6.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable6.getColumns().get(row).setVisible(false);
					parseLocationsTable6.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 7 - startColumn
		startColumn7.setEditable(true);
		startColumn7.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn7.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable7.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData7.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData7.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable7.getColumns().get(row).setVisible(false);
					parseLocationsTable7.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 7 - endColumn
		endColumn7.setEditable(true);
		endColumn7.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn7.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable7.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData7.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData7.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable7.getColumns().get(row).setVisible(false);
					parseLocationsTable7.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 8 - startColumn
		startColumn8.setEditable(true);
		startColumn8.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn8.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable8.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData8.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData8.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable8.getColumns().get(row).setVisible(false);
					parseLocationsTable8.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 8 - endColumn
		endColumn8.setEditable(true);
		endColumn8.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn8.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable8.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData8.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData8.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable8.getColumns().get(row).setVisible(false);
					parseLocationsTable8.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 9 - startColumn
		startColumn9.setEditable(true);
		startColumn9.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn9.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable9.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData9.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData9.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable9.getColumns().get(row).setVisible(false);
					parseLocationsTable9.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 9 - endColumn
		endColumn9.setEditable(true);
		endColumn9.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn9.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable9.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData8.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData9.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable9.getColumns().get(row).setVisible(false);
					parseLocationsTable9.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 10 - startColumn
		startColumn10.setEditable(true);
		startColumn10.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn10.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable10.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData10.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData10.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable10.getColumns().get(row).setVisible(false);
					parseLocationsTable10.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 10 - endColumn
		endColumn10.setEditable(true);
		endColumn10.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn10.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable10.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData10.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData10.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable10.getColumns().get(row).setVisible(false);
					parseLocationsTable10.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 11 - startColumn
		startColumn11.setEditable(true);
		startColumn11.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn11.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatC, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatC, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable11.getColumns().get(2).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData11.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData11.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable11.getColumns().get(row).setVisible(false);
					parseLocationsTable11.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 11 - endColumn
		endColumn11.setEditable(true);
		endColumn11.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn11.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatC, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatC, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable11.getColumns().get(2).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData11.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData11.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable11.getColumns().get(row).setVisible(false);
					parseLocationsTable11.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 12 - startColumn
		startColumn12.setEditable(true);
		startColumn12.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn12.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatC, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatC, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable12.getColumns().get(2).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData12.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData12.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable12.getColumns().get(row).setVisible(false);
					parseLocationsTable12.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 12 - endColumn
		endColumn12.setEditable(true);
		endColumn12.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn12.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatC, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatC, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable12.getColumns().get(2).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData12.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData12.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable12.getColumns().get(row).setVisible(false);
					parseLocationsTable12.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 13 - startColumn
		startColumn13.setEditable(true);
		startColumn13.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		startColumn13.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable13.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = t.getNewValue()+","+oldValues[1];

					// Update in registry
					prefs.put(key, newValue);

					// Update value in startColumn which will then update GUI (as it is observable)
					parseData13.get(row).setStartColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData13.get(row).setStartColumn(t.getOldValue());
					parseLocationsTable13.getColumns().get(row).setVisible(false);
					parseLocationsTable13.getColumns().get(row).setVisible(true);
				}
			}
		});

		// Table 13 - endColumn
		endColumn13.setEditable(true);
		endColumn13.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
		endColumn13.setOnEditCommit(new EventHandler<CellEditEvent<ParseLocationFormatB, Integer>>() {      
			@Override
			public void handle(CellEditEvent<ParseLocationFormatB, Integer> t) {
				if (t.getNewValue() != -1)
				{
					int row = t.getTableView().getEditingCell().getRow();
					String key = parseLocationsTable13.getColumns().get(1).getCellObservableValue(row).getValue().toString(); 
					String[] oldValues = prefs.get(key, null).split(",");
					String newValue = oldValues[0]+","+t.getNewValue();

					// Update in registry
					prefs.put(key, newValue);

					// Update value in endColumn which will then update GUI (as it is observable)
					parseData13.get(row).setEndColumn(t.getNewValue());
				}
				else
				{
					int row = t.getTableView().getEditingCell().getRow();

					parseData13.get(row).setEndColumn(t.getOldValue());
					parseLocationsTable13.getColumns().get(row).setVisible(false);
					parseLocationsTable13.getColumns().get(row).setVisible(true);
				}
			}
		});
	}

	@FXML private void handlePreviousPageButton(ActionEvent event) 
	{
		if (curPage == 1)
		{
			// do nothing
		}
		else if (curPage == 2) // Going to extract (p 1)
		{
			curPage--;
			previousPageButton.setDisable(true);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .SUMMARY files:");
			parseLocationsTable1.setVisible(true);
			parseLocationsTable2.setVisible(true);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable1.requestFocus();
		}
		else if (curPage == 3) // Going to links (p 2)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .LINK file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(true);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable3.requestFocus();
		}
		else if (curPage == 4) // Going to nodes (p 3)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .NODE file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(true);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable4.requestFocus();
		}
		else if (curPage == 5) // Going to signals (p 4)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .SIGNAL file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(true);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable5.requestFocus();
		}
		else if (curPage == 6) // Going to trains (p 5)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .TRAIN file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(true);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable6.requestFocus();
		}
		else if (curPage == 7) // Going to route (p 6)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .ROUTE file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(true);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable7.requestFocus();
		}
		else if (curPage == 8) // Going to option (p 7)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .OPTION file (scroll down for complete list):");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(true);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable8.requestFocus();
		}
		else if (curPage == 9) // Going to option (p 8)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .LINE file (scroll down for complete list):");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(true);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable9.requestFocus();
		}
		else if (curPage == 10) // Going to option (p 9)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from RTC.INI file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(true);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable10.requestFocus();
		}
		else if (curPage == 11) // Going to SSIM (p 10)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from Radixx Res SSIM (IATA) file (scroll down for complete list):");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(true);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable11.requestFocus();
		}
		else if (curPage == 12) // Going to SSIM (p 11)
		{
			curPage--;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from Radixx Res SSIM (S3) file (scroll down for complete list):");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(true);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable12.requestFocus();
		}
		else
		{
			curPage--;
			nextPageButton.setDisable(false);
		}
	}

	@FXML private void handleNextPageButton(ActionEvent event) 
	{
		if (curPage == 12)  // Highest possible page # here
		{
			// do nothing
		}
		else if (curPage == 1)  // Going to links (p 2)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .LINK file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(true);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable3.requestFocus();
		}
		else if (curPage == 2)  // Going to nodes (p 3)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .NODE file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(true);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable4.requestFocus();
		}
		else if (curPage == 3)  // Going to signals (p 4)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .SIGNAL file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(true);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable5.requestFocus();
		}
		else if (curPage == 4)  // Going to trains (p 5)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .TRAIN file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(true);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable6.requestFocus();
		}
		else if (curPage == 5)  // Going to routes (p 6)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .ROUTE file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(true);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable7.requestFocus();
		}
		else if (curPage == 6)  // Going to options (p 7)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .OPTION file (scroll down for complete list):");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(true);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable8.requestFocus();
		}
		else if (curPage == 7)  // Going to line (p 8)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from .LINE file (scroll down for complete list):");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(true);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable9.requestFocus();
		}
		else if (curPage == 8)  // Going to ini (p 9)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from RTC.INI file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(true);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable10.requestFocus();
		}
		else if (curPage == 9)  // Going to Radixx Res SSIM (p 10)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from Radixx Res SSIM (IATA) file (scroll down for complete list):");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(true);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable11.requestFocus();
		}
		else if (curPage == 10)  // Going to Radixx Res SSIM (p 11)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(false);

			parseRangeLabel.setText("Parameters parsed from Radixx Res SSIM (S3) file (scroll down for complete list):");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(true);
			parseLocationsTable13.setVisible(false);

			parseLocationsTable12.requestFocus();
		}
		else if (curPage == 11)  // Going to TPC (p 12)
		{
			curPage++;
			previousPageButton.setDisable(false);
			nextPageButton.setDisable(true);

			parseRangeLabel.setText("Parameters parsed from .TPC file:");
			parseLocationsTable1.setVisible(false);
			parseLocationsTable2.setVisible(false);
			parseLocationsTable3.setVisible(false);
			parseLocationsTable4.setVisible(false);
			parseLocationsTable5.setVisible(false);
			parseLocationsTable6.setVisible(false);
			parseLocationsTable7.setVisible(false);
			parseLocationsTable8.setVisible(false);
			parseLocationsTable9.setVisible(false);
			parseLocationsTable10.setVisible(false);
			parseLocationsTable11.setVisible(false);
			parseLocationsTable12.setVisible(false);
			parseLocationsTable13.setVisible(true);

			parseLocationsTable13.requestFocus();
		}
	}

	private void getParseParameters()
	{
		// Default values
		// Table 1
		// Train categories 
		if (prefs.get("x_trainCat", null) == null)
		{
			// Write value for subsequent runs
			x_trainCat = "5,25,3,15";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("x_trainCat", x_trainCat);
		}

		// Train count
		if (prefs.get("x_trainCount", null) == null)
		{
			// Write value for subsequent runs
			x_trainCount = "27,31,18,22";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("x_trainCount", x_trainCount);
		}

		// Train miles
		if (prefs.get("x_trainMiles", null) == null)
		{
			// Write value for subsequent runs
			x_trainMiles = "146,154,137,145";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("x_trainMiles", x_trainMiles);
		}

		// Speed
		if (prefs.get("x_speed", null) == null)
		{
			// Write value for subsequent runs
			x_speed = "35,42,25,32";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("x_speed", x_speed);
		}

		// OTP
		if (prefs.get("x_otp", null) == null)
		{
			// Write value for subsequent runs
			x_otp = "169,175,169,175";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("x_otp", x_otp);
		}

		// Elapsed run time
		if (prefs.get("x_elapsedRunTime", null) == null)
		{
			// Write value for subsequent runs
			x_elapsedRunTime = "130,142,121,133";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("x_elapsedRunTime", x_elapsedRunTime);
		}

		// Ideal run time
		if (prefs.get("x_idealRunTime", null) == null)
		{
			// Write value for subsequent runs
			x_idealRunTime = "104,116,95,107";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("x_idealRunTime", x_idealRunTime);
		} 

		// Table 2
		// RTC version from extract
		if (prefs.get("x_rtcVersionFromExtract", null) == null)
		{
			// Write value for subsequent runs
			x_rtcVersionFromExtract = "10,75";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("x_rtcVersionFromExtract", x_rtcVersionFromExtract);
		}

		// Invalid results
		if (prefs.get("x_resultsInvalid", null) == null)
		{
			// Write value for subsequent runs
			x_resultsInvalid = "0,179";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("x_resultsInvalid", x_resultsInvalid);
		}  

		// Table 3
		// Link origin node
		if (prefs.get("l_linkOriginNode", null) == null)
		{
			// Write value for subsequent runs
			l_linkOriginNode = "2,14";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("l_linkOriginNode", l_linkOriginNode);
		}

		// Link destination node
		if (prefs.get("l_linkDestinationNode", null) == null)
		{
			// Write value for subsequent runs
			l_linkDestinationNode = "17,29";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("l_linkDestinationNode", l_linkDestinationNode);
		}

		// Link class
		if (prefs.get("l_linkClass", null) == null)
		{
			// Write value for subsequent runs
			l_linkClass = "48,68";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("l_linkClass", l_linkClass);
		}

		// Link direction
		if (prefs.get("l_linkDirection", null) == null)
		{
			// Write value for subsequent runs
			l_linkDirection = "36,37";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("l_linkDirection", l_linkDirection);
		}

		// Link max passenger speed
		if (prefs.get("l_linkMaxPassengerSpeed", null) == null)
		{
			// Write value for subsequent runs
			l_linkMaxPassengerSpeed = "129,132";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("l_linkMaxPassengerSpeed", l_linkMaxPassengerSpeed);
		}

		// Link max through speed
		if (prefs.get("l_linkMaxThroughSpeed", null) == null)
		{
			// Write value for subsequent runs
			l_linkMaxThroughSpeed = "133,136";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("l_linkMaxThroughSpeed", l_linkMaxThroughSpeed);
		}

		// Link max local speed
		if (prefs.get("l_linkMaxLocalSpeed", null) == null)
		{
			// Write value for subsequent runs
			l_linkMaxLocalSpeed = "137,140";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("l_linkMaxLocalSpeed", l_linkMaxLocalSpeed);
		}

		// Table 4
		// Node
		if (prefs.get("n_node", null) == null)
		{
			// Write value for subsequent runs
			n_node = "2,14";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("n_node", n_node);
		}

		// Node name
		if (prefs.get("n_nodeName", null) == null)
		{
			// Write value for subsequent runs
			n_nodeName = "16,32";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("n_nodeName", n_nodeName);
		}

		// RTC Marker
		if (prefs.get("n_rtcMarker", null) == null)
		{
			// Write value for subsequent runs
			n_rtcMarker = "69,78";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("n_rtcMarker", n_rtcMarker);
		}

		// Field Marker
		if (prefs.get("n_fieldMarker", null) == null)
		{
			// Write value for subsequent runs
			n_fieldMarker = "80,89";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("n_fieldMarker", n_fieldMarker);
		}

		// Switch Type
		if (prefs.get("n_switchType", null) == null)
		{
			// Write value for subsequent runs
			n_switchType = "135,140";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("n_switchType", n_switchType);
		}

		// Is A Signal
		if (prefs.get("n_isASignal", null) == null)
		{
			// Write value for subsequent runs
			n_isASignal = "207,208";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("n_isASignal", n_isASignal);
		}

		// Track Number
		if (prefs.get("n_trackNumber", null) == null)
		{
			// Write value for subsequent runs
			n_trackNumber = "696,701";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("n_trackNumber", n_trackNumber);
		}

		// Table 5
		// Begin Node
		if (prefs.get("s_signalBeginNode", null) == null)
		{
			// Write value for subsequent runs
			s_signalBeginNode = "32,51";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s_signalBeginNode", s_signalBeginNode);
		}

		// End Node
		if (prefs.get("s_signalEndNode", null) == null)
		{
			// Write value for subsequent runs
			s_signalEndNode = "114,135";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s_signalEndNode", s_signalEndNode);
		}

		// Signal Type
		if (prefs.get("s_signalType", null) == null)
		{
			// Write value for subsequent runs
			s_signalType = "32,49";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s_signalType", s_signalType);
		}

		// Signal Direction
		if (prefs.get("s_signalDirection", null) == null)
		{
			// Write value for subsequent runs
			s_signalDirection = "32,49";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s_signalDirection", s_signalDirection);
		}

		// Trailing Signal Begin Node
		if (prefs.get("s_trailingSignalBeginNode", null) == null)
		{
			// Write value for subsequent runs
			s_trailingSignalBeginNode = "16,35";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s_trailingSignalBeginNode", s_trailingSignalBeginNode);
		}

		// Trailing Signal End Node
		if (prefs.get("s_trailingSignalEndNode", null) == null)
		{
			// Write value for subsequent runs
			s_trailingSignalEndNode = "94,113";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s_trailingSignalEndNode", s_trailingSignalEndNode);
		}

		// Table 6
		// Train Symbol
		if (prefs.get("t_trainSymbol", null) == null)
		{
			// Write value for subsequent runs
			t_trainSymbol = "31,52";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("t_trainSymbol", t_trainSymbol);
		}

		// Train Type
		if (prefs.get("t_trainType", null) == null)
		{
			// Write value for subsequent runs
			t_trainType = "31,77";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("t_trainType", t_trainType);
		}

		// ATC Equipped
		if (prefs.get("t_atcEquipped", null) == null)
		{
			// Write value for subsequent runs
			t_atcEquipped = "47,50";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("t_atcEquipped", t_atcEquipped);
		}

		// Signal PTC Equipped
		if (prefs.get("t_ptcEquipped", null) == null)
		{
			// Write value for subsequent runs
			t_ptcEquipped = "31,34";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("t_ptcEquipped", t_ptcEquipped);
		}

		// Table 7
		// Train Symbol
		if (prefs.get("r_trainSymbol", null) == null)
		{
			// Write value for subsequent runs
			r_trainSymbol = "269,290";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("r_trainSymbol", r_trainSymbol);
		}

		// RTC Increment
		if (prefs.get("r_rtcIncrement", null) == null)
		{
			// Write value for subsequent runs
			r_rtcIncrement = "2,6";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("r_rtcIncrement", r_rtcIncrement);
		}

		// Node
		if (prefs.get("r_node", null) == null)
		{
			// Write value for subsequent runs
			r_node = "15,28";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("r_node", r_node);
		}

		// Head End Speed
		if (prefs.get("r_headEndSpeed", null) == null)
		{
			// Write value for subsequent runs
			r_headEndSpeed = "44,51";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("r_headEndSpeed", r_headEndSpeed);
		}

		// Head End Arrival Time
		if (prefs.get("r_headEndDepartureTime", null) == null)
		{
			// Write value for subsequent runs
			r_headEndDepartureTime = "71,82";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("r_headEndDepartureTime", r_headEndDepartureTime);
		}

		// Tail End Departure Time
		if (prefs.get("r_tailEndDepartureTime", null) == null)
		{
			// Write value for subsequent runs
			r_tailEndDepartureTime = "84,95";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("r_tailEndDepartureTime", r_tailEndDepartureTime);
		}

		// Cumulative Elapsed
		if (prefs.get("r_cumulativeElapsedTime", null) == null)
		{
			// Write value for subsequent runs
			r_cumulativeElapsedTime = "168,179";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("r_cumulativeElapsedTime", r_cumulativeElapsedTime);
		}

		// Aspect
		if (prefs.get("r_aspect", null) == null)
		{
			// Write value for subsequent runs
			r_aspect = "340,360";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("r_aspect", r_aspect);
		}

		// Direction
		if (prefs.get("r_direction", null) == null)
		{
			// Write value for subsequent runs
			r_direction = "450,459";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("r_direction", r_direction);
		}

		// Table 8
		// RTC Version
		if (prefs.get("o_rtcVersion", null) == null)
		{
			// Write value for subsequent runs
			o_rtcVersion = "46,60";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_rtcVersion", o_rtcVersion);
		}

		// Units of measurement
		if (prefs.get("o_units", null) == null)
		{
			// Write value for subsequent runs
			o_units = "46,53";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_units", o_units);
		}

		// Summary report time format
		if (prefs.get("o_summaryReportTimeFormat", null) == null)
		{
			// Write value for subsequent runs
			o_summaryReportTimeFormat = "46,56";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_summaryReportTimeFormat", o_summaryReportTimeFormat);
		}

		// Simulation begin day
		if (prefs.get("o_simulationBeginDay", null) == null)
		{
			// Write value for subsequent runs
			o_simulationBeginDay = "46,55";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_simulationBeginDay", o_simulationBeginDay);
		}

		// Simulation begin time
		if (prefs.get("o_simulationBeginTime", null) == null)
		{
			// Write value for subsequent runs
			o_simulationBeginTime = "46,54";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_simulationBeginTime", o_simulationBeginTime);
		}

		// Simulation duration
		if (prefs.get("o_simulationDuration", null) == null)
		{
			// Write value for subsequent runs
			o_simulationDuration = "46,54";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_simulationDuration", o_simulationDuration);
		}

		// Warm-up exclusion
		if (prefs.get("o_warmUpExclusion", null) == null)
		{
			// Write value for subsequent runs
			o_warmUpExclusion = "46,54";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_warmUpExclusion", o_warmUpExclusion);
		}

		// Cool-down exclusion
		if (prefs.get("o_coolDownExclusion", null) == null)
		{
			// Write value for subsequent runs
			o_coolDownExclusion = "46,54";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_coolDownExclusion", o_coolDownExclusion);
		}

		// Comma delimited route file
		if (prefs.get("o_commaDelimitedRouteFile", null) == null)
		{
			// Write value for subsequent runs
			o_commaDelimitedRouteFile = "46,49";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_commaDelimitedRouteFile", o_commaDelimitedRouteFile);
		}

		// Show all nodes in route report
		if (prefs.get("o_showAllNodesInRouteReport", null) == null)
		{
			// Write value for subsequent runs
			o_allNodesInRouteReport = "46,49";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_allNodesInRouteReport", o_allNodesInRouteReport);
		}

		// Show seed trains in route report
		if (prefs.get("o_showSeedTrainsInRouteReport", null) == null)
		{
			// Write value for subsequent runs
			o_showSeedTrainsInRouteReport = "46,49";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_showSeedTrainsInRouteReport", o_showSeedTrainsInRouteReport);
		}

		// Train group name
		if (prefs.get("o_trainGroupName", null) == null)
		{
			// Write value for subsequent runs
			o_trainGroupName = "8,25";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_trainGroupName", o_trainGroupName);
		}

		// Train group abbreviation
		if (prefs.get("o_trainGroupAbbreviation", null) == null)
		{
			// Write value for subsequent runs
			o_trainGroupAbbreviation = "30,33";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_trainGroupAbbreviation", o_trainGroupAbbreviation);
		}

		// Train type name
		if (prefs.get("o_trainTypeName", null) == null)
		{
			// Write value for subsequent runs
			o_trainTypeName = "8,30";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_trainTypeName", o_trainTypeName);
		}

		// Train type group
		if (prefs.get("o_trainTypeGroup", null) == null)
		{
			// Write value for subsequent runs
			o_trainTypeGroup = "38,42";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("o_trainTypeGroup", o_trainTypeGroup);
		}

		// Table 9
		// Line name
		if (prefs.get("w_lineName", null) == null)
		{
			// Write value for subsequent runs
			w_lineName = "12,55";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_lineName", w_lineName);
		}

		// Column 1 line entry
		if (prefs.get("w_col1", null) == null)
		{
			// Write value for subsequent runs
			w_col1 = "1,14";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col1", w_col1);
		}

		// Column 2 line entry
		if (prefs.get("w_col2", null) == null)
		{
			// Write value for subsequent runs
			w_col2 = "15,28";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col2", w_col2);
		}

		// Column 3 line entry
		if (prefs.get("w_col3", null) == null)
		{
			// Write value for subsequent runs
			w_col3 = "29,42";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col3", w_col3);
		}

		// Column 4 line entry
		if (prefs.get("w_col4", null) == null)
		{
			// Write value for subsequent runs
			w_col4 = "43,56";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col4", w_col4);
		}

		// Column 5 line entry
		if (prefs.get("w_col5", null) == null)
		{
			// Write value for subsequent runs
			w_col5 = "57,70";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col5", w_col5);
		}

		// Column 6 line entry
		if (prefs.get("w_col6", null) == null)
		{
			// Write value for subsequent runs
			w_col6 = "71,84";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col6", w_col6);
		}

		// Column 7 line entry
		if (prefs.get("w_col7", null) == null)
		{
			// Write value for subsequent runs
			w_col7 = "85,98";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col7", w_col7);
		}

		// Column 8 line entry
		if (prefs.get("w_col8", null) == null)
		{
			// Write value for subsequent runs
			w_col8 = "99,112";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col8", w_col8);
		}

		// Column 9 line entry
		if (prefs.get("w_col9", null) == null)
		{
			// Write value for subsequent runs
			w_col9 = "113,126";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col9", w_col9);
		}

		// Column 10 line entry
		if (prefs.get("w_col10", null) == null)
		{
			// Write value for subsequent runs
			w_col10 = "127,140";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col10", w_col10);
		}

		// Column 11 line entry
		if (prefs.get("w_col11", null) == null)
		{
			// Write value for subsequent runs
			w_col11 = "141,154";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col11", w_col11);
		}

		// Column 12 line entry
		if (prefs.get("w_col12", null) == null)
		{
			// Write value for subsequent runs
			w_col12 = "155,168";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("w_col12", w_col12);
		}

		// Table 10
		// Allow alpha DOW
		if (prefs.get("i_allowAlphaDOW", null) == null)
		{
			// Write value for subsequent runs
			i_allowAlphaDOW = "28,31";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("i_allowAlphaDOW", i_allowAlphaDOW);
		}

		// Table 11
		// All rows - record type
		if (prefs.get("z_all_recordType", null) == null)
		{
			// Write value for subsequent runs
			z_all_recordType = "0,1";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_all_recordType", z_all_recordType);
		}

		// All rows - record serial number
		if (prefs.get("z_all_recordSerialNumber", null) == null)
		{
			// Write value for subsequent runs
			z_all_recordSerialNumber = "194,200";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_all_recordSerialNumber", z_all_recordSerialNumber);
		}

		// Header row - title of contents
		if (prefs.get("z_hdr_titleOfContents", null) == null)
		{
			// Write value for subsequent runs
			z_hdr_titleOfContents = "1,35";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_hdr_titleOfContents", z_hdr_titleOfContents);
		}

		// Header row - data set serial number
		if (prefs.get("z_hdr_dataSetSerialNumber", null) == null)
		{
			// Write value for subsequent runs
			z_hdr_dataSetSerialNumber = "191,194";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_hdr_dataSetSerialNumber", z_hdr_dataSetSerialNumber);
		}

		// Carrier record - time mode
		if (prefs.get("z_car_timeMode", null) == null)
		{
			// Write value for subsequent runs
			z_car_timeMode = "1,2";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_car_timeMode", z_car_timeMode);
		}

		// Carrier record - airline designator
		if (prefs.get("z_car_airlineDesignator", null) == null)
		{
			// Write value for subsequent runs
			z_car_airlineDesignator = "2,5";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_car_airlineDesignator", z_car_airlineDesignator);
		}

		// Carrier record - creator reference
		if (prefs.get("z_car_creatorReference", null) == null)
		{
			// Write value for subsequent runs
			z_car_creatorReference = "72,107";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_car_creatorReference", z_car_creatorReference);
		}

		// Carrier record - period of validity
		if (prefs.get("z_car_periodOfValidity", null) == null)
		{
			// Write value for subsequent runs
			z_car_periodOfValidity = "14,29";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_car_periodOfValidity", z_car_periodOfValidity);
		}

		// Carrier record - creation date
		if (prefs.get("z_car_creationDate", null) == null)
		{
			// Write value for subsequent runs
			z_car_creationDate = "28,35";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_car_creationDate", z_car_creationDate);
		}

		// Carrier record - title of data
		if (prefs.get("z_car_titleOfData", null) == null)
		{
			// Write value for subsequent runs
			z_car_titleOfData = "35,64";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_car_titleOfData", z_car_titleOfData);
		}

		// Carrier record - release date
		if (prefs.get("z_car_releaseDate", null) == null)
		{
			// Write value for subsequent runs
			z_car_releaseDate = "64,71";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_car_releaseDate", z_car_releaseDate);
		}

		// Flight record - airline designator
		if (prefs.get("z_flr_airlineDesignator", null) == null)
		{
			// Write value for subsequent runs
			z_flr_airlineDesignator = "2,5";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_airlineDesignator", z_flr_airlineDesignator);
		}

		// Flight record - flight number
		if (prefs.get("z_flr_flightNumber", null) == null)
		{
			// Write value for subsequent runs
			z_flr_flightNumber = "5,9";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_flightNumber", z_flr_flightNumber);
		}

		// Flight record - itinerary variation identifier
		if (prefs.get("z_flr_itineraryVariationIdentifier", null) == null)
		{
			// Write value for subsequent runs
			z_flr_itineraryVariationIdentifier = "9,11";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_itineraryVariationIdentifier", z_flr_itineraryVariationIdentifier);
		}

		// Flight record - leg sequence number
		if (prefs.get("z_flr_legSequenceNumber", null) == null)
		{
			// Write value for subsequent runs
			z_flr_legSequenceNumber = "11,13";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_legSequenceNumber", z_flr_legSequenceNumber);
		}

		// Flight record - service type
		if (prefs.get("z_flr_serviceType", null) == null)
		{
			// Write value for subsequent runs
			z_flr_serviceType = "13,14";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_serviceType", z_flr_serviceType);
		}

		// Flight record - period of operation
		if (prefs.get("z_flr_periodOfOperation", null) == null)
		{
			// Write value for subsequent runs
			z_flr_periodOfOperation = "14,28";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_periodOfOperation", z_flr_periodOfOperation);
		}

		// Flight record - day of operation
		if (prefs.get("z_flr_dayOfOperation", null) == null)
		{
			// Write value for subsequent runs
			z_flr_dayOfOperation = "28,35";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_dayOfOperation", z_flr_dayOfOperation);
		}

		// Flight record - departure station
		if (prefs.get("z_flr_departureStation", null) == null)
		{
			// Write value for subsequent runs
			z_flr_departureStation = "36,39";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_departureStation", z_flr_departureStation);
		}

		// Flight record - passenger STD
		if (prefs.get("z_flr_passengerSTD", null) == null)
		{
			// Write value for subsequent runs
			z_flr_passengerSTD = "39,43";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_passengerSTD", z_flr_passengerSTD);
		}

		// Flight record - aircraft STD
		if (prefs.get("z_flr_aircraftSTD", null) == null)
		{
			// Write value for subsequent runs
			z_flr_aircraftSTD = "43,47";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_aircraftSTD", z_flr_aircraftSTD);
		}

		// Flight record - time variation departure
		if (prefs.get("z_flr_timeVariationDeparture", null) == null)
		{
			// Write value for subsequent runs
			z_flr_timeVariationDeparture = "47,52";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_timeVariationDeparture", z_flr_timeVariationDeparture);
		}

		// Flight record - departure terminal
		if (prefs.get("z_flr_departureTerminal", null) == null)
		{
			// Write value for subsequent runs
			z_flr_departureTerminal = "52,54";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_departureTerminal", z_flr_departureTerminal);
		}

		// Flight record - arrival station
		if (prefs.get("z_flr_arrivalStation", null) == null)
		{
			// Write value for subsequent runs
			z_flr_arrivalStation = "54,57";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_arrivalStation", z_flr_arrivalStation);
		}

		// Flight record - aircraft STA
		if (prefs.get("z_flr_aircraftSTA", null) == null)
		{
			// Write value for subsequent runs
			z_flr_aircraftSTA = "57,61";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_aircraftSTA", z_flr_aircraftSTA);
		}

		// Flight record - passenger STA
		if (prefs.get("z_flr_passengerSTA", null) == null)
		{
			// Write value for subsequent runs
			z_flr_passengerSTA = "61,65";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_passengerSTA", z_flr_passengerSTA);
		}

		// Flight record - time variation arrival
		if (prefs.get("z_flr_timeVariationArrival", null) == null)
		{
			// Write value for subsequent runs
			z_flr_timeVariationArrival = "65,70";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_timeVariationArrival", z_flr_timeVariationArrival);
		}

		// Flight record - arrival terminal
		if (prefs.get("z_flr_arrivalTerminal", null) == null)
		{
			// Write value for subsequent runs
			z_flr_arrivalTerminal = "70,72";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_arrivalTerminal", z_flr_arrivalTerminal);
		}

		// Flight record - aircraft type
		if (prefs.get("z_flr_aircraftType", null) == null)
		{
			// Write value for subsequent runs
			z_flr_aircraftType = "72,75";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_aircraftType", z_flr_aircraftType);
		}

		// Flight record - onward airline designator
		if (prefs.get("z_flr_onwardAirlineDesignator", null) == null)
		{
			// Write value for subsequent runs
			z_flr_onwardAirlineDesignator = "137,140";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_onwardAirlineDesignator", z_flr_onwardAirlineDesignator);
		}

		// Flight record - onward flight number
		if (prefs.get("z_flr_onwardFlightNumber", null) == null)
		{
			// Write value for subsequent runs
			z_flr_onwardFlightNumber = "140,144";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_onwardFlightNumber", z_flr_onwardFlightNumber);
		}

		// Flight record - onward flight transit layover
		if (prefs.get("z_flr_onwardFlightTransitLayover", null) == null)
		{
			// Write value for subsequent runs
			z_flr_onwardFlightTransitLayover = "147,148";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_onwardFlightTransitLayover", z_flr_onwardFlightTransitLayover);
		}

		// Flight record - aircraft configuration
		if (prefs.get("z_flr_aircraftConfiguration", null) == null)
		{
			// Write value for subsequent runs
			z_flr_aircraftConfiguration = "172,192";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_aircraftConfiguration", z_flr_aircraftConfiguration);
		}

		// Flight record - date variation
		if (prefs.get("z_flr_dateVariation", null) == null)
		{
			// Write value for subsequent runs
			z_flr_dateVariation = "192,194";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_flr_dateVariation", z_flr_dateVariation);
		}

		// Segment record - airline designator
		if (prefs.get("z_seg_airlineDesignator", null) == null)
		{
			// Write value for subsequent runs
			z_seg_airlineDesignator = "2,5";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_airlineDesignator", z_seg_airlineDesignator);
		}

		// Segment record - flight number
		if (prefs.get("z_seg_flightNumber", null) == null)
		{
			// Write value for subsequent runs
			z_seg_flightNumber = "5,9";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_flightNumber", z_seg_flightNumber);
		}

		// Segment record - itinerary variation number
		if (prefs.get("z_seg_itineraryVariationNumber", null) == null)
		{
			// Write value for subsequent runs
			z_seg_itineraryVariationNumber = "9,11";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_itineraryVariationNumber", z_seg_itineraryVariationNumber);
		}

		// Segment record - leg sequence number
		if (prefs.get("z_seg_legSequenceNumber", null) == null)
		{
			// Write value for subsequent runs
			z_seg_legSequenceNumber = "11,13";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_legSequenceNumber", z_seg_legSequenceNumber);
		}

		// Segment record - service type
		if (prefs.get("z_seg_serviceType", null) == null)
		{
			// Write value for subsequent runs
			z_seg_serviceType = "13,14";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_serviceType", z_seg_serviceType);
		}

		// Segment record - board point indicator
		if (prefs.get("z_seg_boardPointIndicator", null) == null)
		{
			// Write value for subsequent runs
			z_seg_boardPointIndicator = "28,29";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_boardPointIndicator", z_seg_boardPointIndicator);
		}

		// Segment record - off point indicator
		if (prefs.get("z_seg_offPointIndicator", null) == null)
		{
			// Write value for subsequent runs
			z_seg_offPointIndicator = "29,30";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_offPointIndicator", z_seg_offPointIndicator);
		}

		// Segment record - data element identifier
		if (prefs.get("z_seg_dataElementIdentifier", null) == null)
		{
			// Write value for subsequent runs
			z_seg_dataElementIdentifier = "30,33";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_dataElementIdentifier", z_seg_dataElementIdentifier);
		}

		// Segment record - segment board point
		if (prefs.get("z_seg_segmentBoardPoint", null) == null)
		{
			// Write value for subsequent runs
			z_seg_segmentBoardPoint = "33,36";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_segmentBoardPoint", z_seg_segmentBoardPoint);
		}

		// Segment record - segment off point
		if (prefs.get("z_seg_segmentOffPoint", null) == null)
		{
			// Write value for subsequent runs
			z_seg_segmentOffPoint = "36,39";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_segmentOffPoint", z_seg_segmentOffPoint);
		}

		// Segment record - data
		if (prefs.get("z_seg_data", null) == null)
		{
			// Write value for subsequent runs
			z_seg_data = "39,194";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_seg_data", z_seg_data);
		}

		// Trailer record - airline designator
		if (prefs.get("z_trl_airlineDesignator", null) == null)
		{
			// Write value for subsequent runs
			z_trl_airlineDesignator = "2,5";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_trl_airlineDesignator", z_trl_airlineDesignator);
		}

		// Trailer record - release date
		if (prefs.get("z_trl_releaseDate", null) == null)
		{
			// Write value for subsequent runs
			z_trl_releaseDate = "5,12";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_trl_releaseDate", z_trl_releaseDate);
		}

		// Trailer record - serial number check reference
		if (prefs.get("z_trl_serialNumberCheckReference", null) == null)
		{
			// Write value for subsequent runs
			z_trl_serialNumberCheckReference = "187,193";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_trl_serialNumberCheckReference", z_trl_serialNumberCheckReference);
		}

		// Trailer record - continuation end code
		if (prefs.get("z_trl_continuationEndCode", null) == null)
		{
			// Write value for subsequent runs
			z_trl_continuationEndCode = "193,194";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("z_trl_continuationEndCode", z_trl_continuationEndCode);
		}

		// Table 12
		// All rows - record type
		if (prefs.get("y_all_recordType", null) == null)
		{
			// Write value for subsequent runs
			y_all_recordType = "0,1";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_all_recordType", y_all_recordType);
		}

		// All rows - record serial number
		if (prefs.get("y_all_recordSerialNumber", null) == null)
		{
			// Write value for subsequent runs
			y_all_recordSerialNumber = "208,216";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_all_recordSerialNumber", y_all_recordSerialNumber);
		}

		// Header Record - title of contents
		if (prefs.get("y_hdr_titleOfContents", null) == null)
		{
			// Write value for subsequent runs
			y_hdr_titleOfContents = "1,40";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_hdr_titleOfContents", y_hdr_titleOfContents);
		}

		// Company Record - time mode
		if (prefs.get("y_com_timeMode", null) == null)
		{
			// Write value for subsequent runs
			y_com_timeMode = "1,2";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_com_timeMode", y_com_timeMode);
		}

		// Company Record - company code
		if (prefs.get("y_com_companyCode", null) == null)
		{
			// Write value for subsequent runs
			y_com_companyCode = "2,4";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_com_companyCode", y_com_companyCode);
		}

		// Company Record - description
		if (prefs.get("y_com_description", null) == null)
		{
			// Write value for subsequent runs
			y_com_description = "35,62";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_com_description", y_com_description);
		}

		// Company Record - period of validity
		if (prefs.get("y_com_periodOfValidity", null) == null)
		{
			// Write value for subsequent runs
			y_com_periodOfValidity = "14,28";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_com_periodOfValidity", y_com_periodOfValidity);
		}

		// Company Record - creation date
		if (prefs.get("y_com_creationDate", null) == null)
		{
			// Write value for subsequent runs
			y_com_creationDate = "28,35";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_com_creationDate", y_com_creationDate);
		}

		// Timetable Record - company code
		if (prefs.get("y_ttb_companyCode", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_companyCode = "2,4";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_companyCode", y_ttb_companyCode);
		}

		// Timetable Record - train number
		if (prefs.get("y_ttb_trainNumber", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_trainNumber = "148,154";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_trainNumber", y_ttb_trainNumber);
		}

		// Timetable Record - itinerary variation identifier
		if (prefs.get("y_ttb_itineraryVariationIdentifier", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_itineraryVariationIdentifier = "11,13";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_itineraryVariationIdentifier", y_ttb_itineraryVariationIdentifier);
		}

		// Timetable Record - leg sequence Number
		if (prefs.get("y_ttb_legSequenceNumber", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_legSequenceNumber = "13,15";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_legSequenceNumber", y_ttb_legSequenceNumber);
		}

		// Timetable Record - commercial category
		if (prefs.get("y_ttb_commercialCategory", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_commercialCategory = "15,16";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_commercialCategory", y_ttb_commercialCategory);
		}

		// Timetable Record - period of operation
		if (prefs.get("y_ttb_periodOfOperation", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_periodOfOperation = "16,30";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_periodOfOperation", y_ttb_periodOfOperation);
		}

		// Timetable Record - day of operation
		if (prefs.get("y_ttb_dayOfOperation", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_dayOfOperation = "30,37";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_dayOfOperation", y_ttb_dayOfOperation);
		}

		// Timetable Record - departure station
		if (prefs.get("y_ttb_departureStation", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_departureStation = "38,43";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_departureStation", y_ttb_departureStation);
		}

		// Timetable - passenger STD
		if (prefs.get("y_ttb_passengerSTD", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_passengerSTD = "43,47";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_passengerSTD", y_ttb_passengerSTD);
		}

		// Timetable - train STD
		if (prefs.get("y_ttb_trainSTD", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_trainSTD = "47,51";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_trainSTD", y_ttb_trainSTD);
		}

		// Timetable - time variation departure
		if (prefs.get("y_ttb_timeVariationDeparture", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_timeVariationDeparture = "51,56";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_timeVariationDeparture", y_ttb_timeVariationDeparture);
		}

		// Timetable - departure terminal
		if (prefs.get("y_ttb_departureTerminal", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_departureTerminal = "56,58";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_departureTerminal", y_ttb_departureTerminal);
		}

		// Timetable Record - arrival station
		if (prefs.get("y_ttb_arrivalStation", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_arrivalStation = "58,63";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_arrivalStation", y_ttb_arrivalStation);
		}

		// Timetable - passenger STA
		if (prefs.get("y_ttb_passengerSTA", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_passengerSTA = "63,67";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_passengerSTA", y_ttb_passengerSTA);
		}

		// Timetable - train STA
		if (prefs.get("y_ttb_trainSTA", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_trainSTA = "67,71";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_trainSTA", y_ttb_trainSTA);
		}

		// Timetable - time variation arrival
		if (prefs.get("y_ttb_timeVariationArrival", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_timeVariationArrival = "71,76";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_timeVariationArrival", y_ttb_timeVariationArrival);
		}

		// Timetable - arrival terminal
		if (prefs.get("y_ttb_arrivalTerminal", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_arrivalTerminal = "76,78";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_arrivalTerminal", y_ttb_arrivalTerminal);
		}

		// Timetable Record - service type
		if (prefs.get("y_ttb_serviceType", null) == null)
		{
			// Write value for subsequent runs
			y_ttb_serviceType = "78,81";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_ttb_serviceType", y_ttb_serviceType);
		}

		// Trailer record - companyCode
		if (prefs.get("y_trl_companyCode", null) == null)
		{
			// Write value for subsequent runs
			y_trl_companyCode = "2,4";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_trl_companyCode", y_trl_companyCode);
		}

		// Trailer record - start date
		if (prefs.get("y_trl_startDate", null) == null)
		{
			// Write value for subsequent runs
			y_trl_startDate = "5,12";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_trl_startDate", y_trl_startDate);
		}

		// Trailer record - serial number check reference
		if (prefs.get("y_trl_serialNumberCheckReference", null) == null)
		{
			// Write value for subsequent runs
			y_trl_serialNumberCheckReference = "183,191";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_trl_serialNumberCheckReference", y_trl_serialNumberCheckReference);
		}

		// Trailer record - continuation end code
		if (prefs.get("y_trl_continuationEndCode", null) == null)
		{
			// Write value for subsequent runs
			y_trl_continuationEndCode = "191,192";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("y_trl_continuationEndCode", y_trl_continuationEndCode);
		}

		// Table 13
		// Node (route)
		if (prefs.get("p_node", null) == null)
		{
			// Write value for subsequent runs
			p_node = "1,15";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("p_node", p_node);
		}

		// Field marker
		if (prefs.get("p_fieldMarker", null) == null)
		{
			// Write value for subsequent runs
			p_fieldMarker = "235,244";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("p_fieldMarker", p_fieldMarker);
		}

		// Design speed
		if (prefs.get("p_designSpeed", null) == null)
		{
			// Write value for subsequent runs
			p_designSpeed = "127,134";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("p_designSpeed", p_designSpeed);
		}

		// Current speed
		if (prefs.get("p_currentSpeed", null) == null)
		{
			// Write value for subsequent runs
			p_currentSpeed = "165,172";
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("p_currentSpeed", p_currentSpeed);
		}
	}

	public static String[] x_getTrainCat()
	{
		String[] values = prefs.get("x_trainCat", x_trainCat).split(",");
		return values;
	}

	public static String[] x_getTrainCount()
	{
		String[] values = prefs.get("x_trainCount", x_trainCount).split(",");
		return values;
	}

	public static String[] x_getTrainMiles()
	{
		String[] values = prefs.get("x_trainMiles", x_trainMiles).split(",");
		return values;
	}

	public static String[] x_getSpeed()
	{
		String[] values = prefs.get("x_speed", x_speed).split(",");
		return values;
	}

	public static String[] x_getOTP()
	{
		String[] values = prefs.get("x_otp", x_otp).split(",");
		return values;
	}

	public static String[] x_getElapsedRunTime()
	{
		String[] values = prefs.get("x_elapsedRunTime", x_elapsedRunTime).split(",");
		return values;
	}

	public static String[] x_getIdealRunTime()
	{
		String[] values = prefs.get("x_idealRunTime", x_idealRunTime).split(",");
		return values;
	}

	public static String[] x_getRtcVersionExtract()
	{
		String[] values = prefs.get("x_rtcVersionFromExtract", x_rtcVersionFromExtract).split(",");
		return values;
	}

	public static String[] x_getInvalidResults()
	{
		String[] values = prefs.get("x_resultsInvalid", x_resultsInvalid).split(",");
		return values;
	}

	public static String[] l_getLinkOriginNode()
	{
		String[] values = prefs.get("l_linkOriginNode", l_linkOriginNode).split(",");
		return values;
	}

	public static String[] l_getLinkDestinationNode()
	{
		String[] values = prefs.get("l_linkDestinationNode", l_linkDestinationNode).split(",");
		return values;
	}

	public static String[] l_getLinkClass()
	{
		String[] values = prefs.get("l_linkClass", l_linkClass).split(",");
		return values;
	}

	public static String[] l_getLinkDirection()
	{
		String[] values = prefs.get("l_linkDirection", l_linkDirection).split(",");
		return values;
	}

	public static String[] l_getLinkMaxPassengerSpeed()
	{
		String[] values = prefs.get("l_linkMaxPassengerSpeed", l_linkMaxPassengerSpeed).split(",");
		return values;
	}

	public static String[] l_getLinkMaxThroughSpeed()
	{
		String[] values = prefs.get("l_linkMaxThroughSpeed", l_linkMaxThroughSpeed).split(",");
		return values;
	}

	public static String[] l_getLinkMaxLocalSpeed()
	{
		String[] values = prefs.get("l_linkMaxLocalSpeed", l_linkMaxLocalSpeed).split(",");
		return values;
	}

	public static String[] n_getNode()
	{
		String[] values = prefs.get("n_node", n_node).split(",");
		return values;
	}

	public static String[] n_getNodeName()
	{
		String[] values = prefs.get("n_nodeName", n_nodeName).split(",");
		return values;
	}

	public static String[] n_getRtcMarker()
	{
		String[] values = prefs.get("n_rtcMarker", n_rtcMarker).split(",");
		return values;
	}

	public static String[] n_getFieldMarker()
	{
		String[] values = prefs.get("n_fieldMarker", n_fieldMarker).split(",");
		return values;
	}

	public static String[] n_getSwitchType()
	{
		String[] values = prefs.get("n_switchType", n_switchType).split(",");
		return values;
	}

	public static String[] n_getIsASignal()
	{
		String[] values = prefs.get("n_isASignal", n_isASignal).split(",");
		return values;
	}

	public static String[] n_getTrackNumber()
	{
		String[] values = prefs.get("n_trackNumber", n_trackNumber).split(",");
		return values;
	}

	public static String[] s_getSignalBeginNode()
	{
		String[] values = prefs.get("s_signalBeginNode", s_signalBeginNode).split(",");
		return values;
	}

	public static String[] s_getSignalEndNode()
	{
		String[] values = prefs.get("s_signalEndNode", s_signalEndNode).split(",");
		return values;
	}

	public static String[] s_getSignalType()
	{
		String[] values = prefs.get("s_signalType", s_signalType).split(",");
		return values;
	}

	public static String[] s_getSignalDirection()
	{
		String[] values = prefs.get("s_signalDirection", s_signalDirection).split(",");
		return values;
	}

	public static String[] s_getTrailingSignalBeginNode()
	{
		String[] values = prefs.get("s_trailingSignalBeginNode", s_trailingSignalBeginNode).split(",");
		return values;
	}

	public static String[] s_getTrailingSignalEndNode()
	{
		String[] values = prefs.get("s_trailingSignalEndNode", s_trailingSignalEndNode).split(",");
		return values;
	}

	public static String[] t_getTrainSymbol()
	{
		String[] values = prefs.get("t_trainSymbol", t_trainSymbol).split(",");
		return values;
	}

	public static String[] t_getTrainType()
	{
		String[] values = prefs.get("t_trainType", t_trainType).split(",");
		return values;
	}

	public static String[] t_getAtcEquipped()
	{
		String[] values = prefs.get("t_atcEquipped", t_atcEquipped).split(",");
		return values;
	}

	public static String[] t_getPtcEquipped()
	{
		String[] values = prefs.get("t_ptcEquipped", t_ptcEquipped).split(",");
		return values;
	}

	public static String[] r_getTrainSymbol()
	{
		String[] values = prefs.get("r_trainSymbol", r_trainSymbol).split(",");
		return values;
	}

	public static String[] r_getRtcIncrement()
	{
		String[] values = prefs.get("r_rtcIncrement", r_rtcIncrement).split(",");
		return values;
	}

	public static String[] r_getNode()
	{
		String[] values = prefs.get("r_node", r_node).split(",");
		return values;
	}

	public static String[] r_getHeadEndSpeed()
	{
		String[] values = prefs.get("r_headEndSpeed", r_headEndSpeed).split(",");
		return values;
	}

	public static String[] r_getHeadEndArrivalTime()
	{
		String[] values = prefs.get("r_headEndDepartureTime", r_headEndDepartureTime).split(",");
		return values;
	}

	public static String[] r_getTailEndDepartureTime()
	{
		String[] values = prefs.get("r_tailEndDepartureTime", r_tailEndDepartureTime).split(",");
		return values;
	}

	public static String[] r_getCumulativeElapsedTime()
	{
		String[] values = prefs.get("r_cumulativeElapsedTime", r_cumulativeElapsedTime).split(",");
		return values;
	}

	public static String[] r_getAspect()
	{
		String[] values = prefs.get("r_aspect", r_aspect).split(",");
		return values;
	}

	public static String[] r_getDirection()
	{
		String[] values = prefs.get("r_direction", r_direction).split(",");
		return values;
	}

	public static String[] o_getRtcVersion()
	{
		String[] values = prefs.get("o_rtcVersion", o_rtcVersion).split(",");
		return values;
	}

	public static String[] o_getUnits()
	{
		String[] values = prefs.get("o_units", o_units).split(",");
		return values;
	}

	public static String[] o_getSummaryReportTimeFormat()
	{
		String[] values = prefs.get("o_summaryReportTimeFormat", o_summaryReportTimeFormat).split(",");
		return values;
	}

	public static String[] o_getSimulationBeginDay()
	{
		String[] values = prefs.get("o_simulationBeginDay", o_simulationBeginDay).split(",");
		return values;
	}

	public static String[] o_getSimulationBeginTime()
	{
		String[] values = prefs.get("o_simulationBeginTime", o_simulationBeginTime).split(",");
		return values;
	}

	public static String[] o_getSimulationDuration()
	{
		String[] values = prefs.get("o_simulationDuration", o_simulationDuration).split(",");
		return values;
	}

	public static String[] o_getWarmUpExclusion()
	{
		String[] values = prefs.get("o_warmUpExclusion", o_warmUpExclusion).split(",");
		return values;
	}

	public static String[] o_getCoolDownExclusion()
	{
		String[] values = prefs.get("o_coolDownExclusion", o_coolDownExclusion).split(",");
		return values;
	}

	public static String[] o_getCommaDelimited()
	{
		String[] values = prefs.get("o_commaDelimitedRouteFile", o_commaDelimitedRouteFile).split(",");
		return values;
	}
	
	public static String[] o_getAllNodesInRouteReport()
	{
		String[] values = prefs.get("o_allNodesInRouteReport", o_allNodesInRouteReport).split(",");
		return values;
	}
	
	public static String[] o_getShowSeedTrainsInRouteReport()
	{
		String[] values = prefs.get("o_showSeedTrainsInRouteReport", o_showSeedTrainsInRouteReport).split(",");
		return values;
	}
	
	public static String[] o_getTrainGroupName()
	{
		String[] values = prefs.get("o_trainGroupName", o_trainGroupName).split(",");
		return values;
	}

	public static String[] o_getTrainGroupAbbreviation()
	{
		String[] values = prefs.get("o_trainGroupAbbreviation", o_trainGroupAbbreviation).split(",");
		return values;
	}

	public static String[] o_getTrainTypeName()
	{
		String[] values = prefs.get("o_trainTypeName", o_trainTypeName).split(",");
		return values;
	}

	public static String[] o_getTrainTypeGroup()
	{
		String[] values = prefs.get("o_trainTypeGroup", o_trainTypeGroup).split(",");
		return values;
	}

	public static String[] w_getLineName()
	{
		String[] values = prefs.get("w_lineName", w_lineName).split(",");
		return values;
	}

	public static String[] w_getCol1()
	{
		String[] values = prefs.get("w_col1", w_col1).split(",");
		return values;
	}

	public static String[] w_getCol2()
	{
		String[] values = prefs.get("w_col2", w_col2).split(",");
		return values;
	}

	public static String[] w_getCol3()
	{
		String[] values = prefs.get("w_col3", w_col3).split(",");
		return values;
	}

	public static String[] w_getCol4()
	{
		String[] values = prefs.get("w_col4", w_col4).split(",");
		return values;
	}

	public static String[] w_getCol5()
	{
		String[] values = prefs.get("w_col5", w_col5).split(",");
		return values;
	}

	public static String[] w_getCol6()
	{
		String[] values = prefs.get("w_col6", w_col6).split(",");
		return values;
	}

	public static String[] w_getCol7()
	{
		String[] values = prefs.get("w_col7", w_col7).split(",");
		return values;
	}

	public static String[] w_getCol8()
	{
		String[] values = prefs.get("w_col8", w_col8).split(",");
		return values;
	}

	public static String[] w_getCol9()
	{
		String[] values = prefs.get("w_col9", w_col9).split(",");
		return values;
	}

	public static String[] w_getCol10()
	{
		String[] values = prefs.get("w_col10", w_col10).split(",");
		return values;
	}

	public static String[] w_getCol11()
	{
		String[] values = prefs.get("w_col11", w_col11).split(",");
		return values;
	}

	public static String[] w_getCol12()
	{
		String[] values = prefs.get("w_col12", w_col12).split(",");
		return values;
	}

	public static String[] i_getAllowAlphaDOW()
	{
		String[] values = prefs.get("i_allowAlphaDOW", i_allowAlphaDOW).split(",");
		return values;
	}

	public static String[] z_getAll_recordType()
	{
		String[] values = prefs.get("z_all_recordType", z_all_recordType).split(",");
		return values;
	}

	public static String[] z_getAll_recordSerialNumber()
	{
		String[] values = prefs.get("z_all_recordSerialNumber", z_all_recordSerialNumber).split(",");
		return values;
	}

	public static String[] z_getHdr_titleOfContents()
	{
		String[] values = prefs.get("z_hdr_titleOfContents", z_hdr_titleOfContents).split(",");
		return values;
	}

	public static String[] z_getHdr_dataSetSerialNumber()
	{
		String[] values = prefs.get("z_hdr_dataSetSerialNumber", z_hdr_dataSetSerialNumber).split(",");
		return values;
	}

	public static String[] z_getCar_timeMode()
	{
		String[] values = prefs.get("z_car_timeMode", z_car_timeMode).split(",");
		return values;
	}

	public static String[] z_getCar_airlineDesignator()
	{
		String[] values = prefs.get("z_car_airlineDesignator", z_car_airlineDesignator).split(",");
		return values;
	}

	public static String[] z_getCar_creatorReference()
	{
		String[] values = prefs.get("z_car_creatorReference", z_car_creatorReference).split(",");
		return values;
	}

	public static String[] z_getCar_periodOfValidity()
	{
		String[] values = prefs.get("z_car_periodOfValidity", z_car_periodOfValidity).split(",");
		return values;
	}

	public static String[] z_getCar_creationDate()
	{
		String[] values = prefs.get("z_car_creationDate", z_car_creationDate).split(",");
		return values;
	}

	public static String[] z_getCar_titleOfData()
	{
		String[] values = prefs.get("z_car_titleOfData", z_car_titleOfData).split(",");
		return values;
	}

	public static String[] z_getCar_releaseDate()
	{
		String[] values = prefs.get("z_car_releaseDate", z_car_releaseDate).split(",");
		return values;
	}

	public static String[] z_getFlr_airlineDesignator()
	{
		String[] values = prefs.get("z_flr_airlineDesignator", z_flr_airlineDesignator).split(",");
		return values;
	}

	public static String[] z_getFlr_flightNumber()
	{
		String[] values = prefs.get("z_flr_flightNumber", z_flr_flightNumber).split(",");
		return values;
	}

	public static String[] z_getFlr_itineraryVariationIdentifier()
	{
		String[] values = prefs.get("z_flr_itineraryVariationIdentifier", z_flr_itineraryVariationIdentifier).split(",");
		return values;
	}

	public static String[] z_getFlr_legSequenceNumber()
	{
		String[] values = prefs.get("z_flr_legSequenceNumber", z_flr_legSequenceNumber).split(",");
		return values;
	}

	public static String[] z_getFlr_serviceType()
	{
		String[] values = prefs.get("z_flr_serviceType", z_flr_serviceType).split(",");
		return values;
	}

	public static String[] z_getFlr_periodOfOperation()
	{
		String[] values = prefs.get("z_flr_periodOfOperation", z_flr_periodOfOperation).split(",");
		return values;
	}

	public static String[] z_getFlr_dayOfOperation()
	{
		String[] values = prefs.get("z_flr_dayOfOperation", z_flr_dayOfOperation).split(",");
		return values;
	}

	public static String[] z_getFlr_departureStation()
	{
		String[] values = prefs.get("z_flr_departureStation", z_flr_departureStation).split(",");
		return values;
	}

	public static String[] z_getFlr_passengerSTD()
	{
		String[] values = prefs.get("z_flr_passengerSTD", z_flr_passengerSTD).split(",");
		return values;
	}

	public static String[] z_getFlr_aircraftSTD()
	{
		String[] values = prefs.get("z_flr_aircraftSTD", z_flr_aircraftSTD).split(",");
		return values;
	}

	public static String[] z_getFlr_timeVariationDeparture()
	{
		String[] values = prefs.get("z_flr_timeVariationDeparture", z_flr_timeVariationDeparture).split(",");
		return values;
	}

	public static String[] z_getFlr_departureTerminal()
	{
		String[] values = prefs.get("z_flr_departureTerminal", z_flr_departureTerminal).split(",");
		return values;
	}

	public static String[] z_getFlr_arrivalStation()
	{
		String[] values = prefs.get("z_flr_arrivalStation", z_flr_arrivalStation).split(",");
		return values;
	}

	public static String[] z_getFlr_aircraftSTA()
	{
		String[] values = prefs.get("z_flr_aircraftSTA", z_flr_aircraftSTA).split(",");
		return values;
	}

	public static String[] z_getFlr_passengerSTA()
	{
		String[] values = prefs.get("z_flr_passengerSTA", z_flr_passengerSTA).split(",");
		return values;
	}

	public static String[] z_getFlr_timeVariationArrival()
	{
		String[] values = prefs.get("z_flr_timeVariationArrival", z_flr_timeVariationArrival).split(",");
		return values;
	}

	public static String[] z_getFlr_arrivalTerminal()
	{
		String[] values = prefs.get("z_flr_arrivalTerminal", z_flr_arrivalTerminal).split(",");
		return values;
	}

	public static String[] z_getFlr_aircraftType()
	{
		String[] values = prefs.get("z_flr_aircraftType", z_flr_aircraftType).split(",");
		return values;
	}

	public static String[] z_getFlr_onwardAirlineDesignator()
	{
		String[] values = prefs.get("z_flr_onwardAirlineDesignator", z_flr_onwardAirlineDesignator).split(",");
		return values;
	}

	public static String[] z_getFlr_onwardFlightNumber()
	{
		String[] values = prefs.get("z_flr_onwardFlightNumber", z_flr_onwardFlightNumber).split(",");
		return values;
	}

	public static String[] z_getFlr_onwardFlightTransitLayover()
	{
		String[] values = prefs.get("z_flr_onwardFlightTransitLayover", z_flr_onwardFlightTransitLayover).split(",");
		return values;
	}

	public static String[] z_getFlr_aircraftConfiguration()
	{
		String[] values = prefs.get("z_flr_aircraftConfiguration", z_flr_aircraftConfiguration).split(",");
		return values;
	}

	public static String[] z_getFlr_dateVariation()
	{
		String[] values = prefs.get("z_flr_dateVariation", z_flr_dateVariation).split(",");
		return values;
	}

	public static String[] z_getSeg_airlineDesignator()
	{
		String[] values = prefs.get("z_seg_airlineDesignator", z_seg_airlineDesignator).split(",");
		return values;
	}

	public static String[] z_getSeg_flightNumber()
	{
		String[] values = prefs.get("z_seg_flightNumber", z_seg_flightNumber).split(",");
		return values;
	}

	public static String[] z_getSeg_itineraryVariationNumber()
	{
		String[] values = prefs.get("z_seg_itineraryVariationNumber", z_seg_itineraryVariationNumber).split(",");
		return values;
	}

	public static String[] z_getSeg_legSequenceNumber()
	{
		String[] values = prefs.get("z_seg_legSequenceNumber", z_seg_legSequenceNumber).split(",");
		return values;
	}

	public static String[] z_getSeg_serviceType()
	{
		String[] values = prefs.get("z_seg_serviceType", z_seg_serviceType).split(",");
		return values;
	}

	public static String[] z_getSeg_boardPointIndicator()
	{
		String[] values = prefs.get("z_seg_boardPointIndicator", z_seg_boardPointIndicator).split(",");
		return values;
	}

	public static String[] z_getSeg_offPointIndicator()
	{
		String[] values = prefs.get("z_seg_offPointIndicator", z_seg_offPointIndicator).split(",");
		return values;
	}

	public static String[] z_getSeg_dataElementIdentifier()
	{
		String[] values = prefs.get("z_seg_dataElementIdentifier", z_seg_dataElementIdentifier).split(",");
		return values;
	}

	public static String[] z_getSeg_segmentBoardPoint()
	{
		String[] values = prefs.get("z_seg_segmentBoardPoint", z_seg_segmentBoardPoint).split(",");
		return values;
	}

	public static String[] z_getSeg_segmentOffPoint()
	{
		String[] values = prefs.get("z_seg_segmentOffPoint", z_seg_segmentOffPoint).split(",");
		return values;
	}

	public static String[] z_getSeg_data()
	{
		String[] values = prefs.get("z_seg_data", z_seg_data).split(",");
		return values;
	}

	public static String[] z_getTrl_airlineDesignator()
	{
		String[] values = prefs.get("z_trl_airlineDesignator", z_trl_airlineDesignator).split(",");
		return values;
	}

	public static String[] z_getTrl_releaseDate()
	{
		String[] values = prefs.get("z_trl_releaseDate", z_trl_releaseDate).split(",");
		return values;
	}

	public static String[] z_getTrl_serialNumberCheckReference()
	{
		String[] values = prefs.get("z_trl_serialNumberCheckReference", z_trl_serialNumberCheckReference).split(",");
		return values;
	}

	public static String[] z_getTrl_continuationEndCode()
	{
		String[] values = prefs.get("z_trl_continuationEndCode", z_trl_continuationEndCode).split(",");
		return values;
	}

	public static String[] y_getAll_recordType()
	{
		String[] values = prefs.get("y_all_recordType", y_all_recordType).split(",");
		return values;
	}

	public static String[] y_getAll_recordSerialNumber()
	{
		String[] values = prefs.get("y_all_recordSerialNumber", y_all_recordSerialNumber).split(",");
		return values;
	}

	public static String[] y_getHdr_titleOfContents() 
	{
		String[] values = prefs.get("y_hdr_titleOfContents", y_hdr_titleOfContents).split(",");
		return values;
	}

	public static String[] y_getCom_timeMode() 
	{
		String[] values = prefs.get("y_com_timeMode", y_com_timeMode).split(",");
		return values;
	}

	public static String[] y_getCom_creationDate() 
	{
		String[] values = prefs.get("y_com_creationDate", y_com_creationDate).split(",");
		return values;
	}

	public static String[] y_getCom_periodOfValidity() 
	{
		String[] values = prefs.get("y_com_periodOfValidity", y_com_periodOfValidity).split(",");
		return values;
	}

	public static String[] y_getCom_description() 
	{
		String[] values = prefs.get("y_com_description", y_com_description).split(",");
		return values;
	}

	public static String[] y_getCom_companyCode() 
	{
		String[] values = prefs.get("y_com_companyCode", y_com_companyCode).split(",");
		return values;
	}

	public static String[] y_getTtb_companyCode() 
	{
		String[] values = prefs.get("y_ttb_companyCode", y_ttb_companyCode).split(",");
		return values;
	}

	public static String[] y_getTtb_trainNumber() 
	{
		String[] values = prefs.get("y_ttb_trainNumber", y_ttb_trainNumber).split(",");
		return values;
	}

	public static String[] y_getTtb_itineraryVariationIdentifier()
	{
		String[] values = prefs.get("y_ttb_itineraryVariationIdentifier", y_ttb_itineraryVariationIdentifier).split(",");
		return values;
	}

	public static String[] y_getTtb_legSequenceNumber() 
	{
		String[] values = prefs.get("y_ttb_legSequenceNumber", y_ttb_legSequenceNumber).split(",");
		return values;
	}

	public static String[] y_getTtb_commercialCategory() 
	{
		String[] values = prefs.get("y_ttb_commercialCategory", y_ttb_commercialCategory).split(",");
		return values;
	}

	public static String[] y_getTtb_periodOfOperation() 
	{
		String[] values = prefs.get("y_ttb_periodOfOperation", y_ttb_periodOfOperation).split(",");
		return values;
	}

	public static String[] y_getTtb_dayOfOperation() 
	{
		String[] values = prefs.get("y_ttb_dayOfOperation", y_ttb_dayOfOperation).split(",");
		return values;
	}


	public static String[] y_getTtb_departureStation() 
	{
		String[] values = prefs.get("y_ttb_departureStation", y_ttb_departureStation).split(",");
		return values;
	}

	public static String[] y_getTtb_passengerSTD() 
	{
		String[] values = prefs.get("y_ttb_passengerSTD", y_ttb_passengerSTD).split(",");
		return values;
	}

	public static String[] y_getTtb_trainSTD() 
	{
		String[] values = prefs.get("y_ttb_trainSTD", y_ttb_trainSTD).split(",");
		return values;
	}

	public static String[] y_getTtb_timeVariationDeparture() 
	{
		String[] values = prefs.get("y_ttb_timeVariationDeparture", y_ttb_timeVariationDeparture).split(",");
		return values;
	}

	public static String[] y_getTtb_departureTerminal() 
	{
		String[] values = prefs.get("y_ttb_departureTerminal", y_ttb_departureTerminal).split(",");
		return values;
	}

	public static String[] y_getTtb_arrivalStation() 
	{
		String[] values = prefs.get("y_ttb_arrivalStation", y_ttb_arrivalStation).split(",");
		return values;
	}

	public static String[] y_getTtb_passengerSTA() 
	{
		String[] values = prefs.get("y_ttb_passengerSTA", y_ttb_passengerSTA).split(",");
		return values;
	}

	public static String[] y_getTtb_trainSTA() 
	{
		String[] values = prefs.get("y_ttb_trainSTA", y_ttb_trainSTA).split(",");
		return values;
	}

	public static String[] y_getTtb_timeVariationArrival() 
	{
		String[] values = prefs.get("y_ttb_timeVariationArrival", y_ttb_timeVariationArrival).split(",");
		return values;
	}

	public static String[] y_getTtb_arrivalTerminal() 
	{
		String[] values = prefs.get("y_ttb_arrivalTerminal", y_ttb_arrivalTerminal).split(",");
		return values;
	}

	public static String[] y_getTtb_serviceType() 
	{
		String[] values = prefs.get("y_ttb_serviceType", y_ttb_serviceType).split(",");
		return values;
	}

	public static String[] y_getTrl_companyCode()
	{
		String[] values = prefs.get("y_trl_companyCode", y_trl_companyCode).split(",");
		return values;
	}

	public static String[] y_getTrl_startDate()
	{
		String[] values = prefs.get("y_trl_startDate", y_trl_startDate).split(",");
		return values;
	}

	public static String[] y_getTrl_serialNumberCheckReference()
	{
		String[] values = prefs.get("y_trl_serialNumberCheckReference", y_trl_serialNumberCheckReference).split(",");
		return values;
	}

	public static String[] y_getTrl_continuationEndCode()
	{
		String[] values = prefs.get("y_trl_continuationEndCode", y_trl_continuationEndCode).split(",");
		return values;
	}

	public static String[] p_getNode()
	{
		String[] values = prefs.get("p_node", "p_node").split(",");
		return values;
	}

	public static String[] p_getFieldMarker()
	{
		String[] values = prefs.get("p_fieldMarker", "p_fieldMarker").split(",");
		return values;
	}

	public static String[] p_getDesignSpeed()
	{
		String[] values = prefs.get("p_designSpeed", "p_designSpeed").split(",");
		return values;
	}

	public static String[] p_getCurrentSpeed()
	{
		String[] values = prefs.get("p_currentSpeed", "p_currentSpeed").split(",");
		return values;
	}

	private static class CustomIntegerStringConverter extends IntegerStringConverter 
	{
		private final IntegerStringConverter converter = new IntegerStringConverter();

		@Override
		public Integer fromString(String string) {
			try 
			{    
				Integer integerFromString = converter.fromString(string);
				if (integerFromString == null)
				{
					// Alert
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Value is null.");	
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
					alert.show();

					return -1;
				}
				else if ((integerFromString < 1) || (integerFromString > 999))
				{
					// Alert
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Value is outside of valid range.");	
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
					alert.show();

					return -1;
				}
				return integerFromString;
			} 
			catch (NumberFormatException e) 
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Value must be integer.");	
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
				alert.show();
			}
			return -1;
		}
	}
}