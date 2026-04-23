package com.bl.bias.app;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import com.bl.bias.analyze.S3CompareScheduleAnalysisCoreVsPlan;
import com.bl.bias.analyze.S3CompareScheduleAnalysisPlanVsPlan;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadS3CompareScheduleFilesCoreVsPlan;
import com.bl.bias.read.ReadS3CompareScheduleFilesPlanVsPlan;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.write.WriteS3CompareScheduleFilesCoreVsPlan2;
import com.bl.bias.write.WriteS3CompareScheduleFilesPlanVsPlan2;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BIASS3CompareSchedulePageController 
{
	private static String message = "";
	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;

	private static BooleanBinding disableApiConnectionsAndExecuteButton;
	private static SimpleBooleanProperty startDateSelectedBP;
	private static SimpleBooleanProperty endDateSelectedBP;
	private static SimpleBooleanProperty firstDateSelectedBP;
	private static SimpleBooleanProperty secondDateSelectedBP;
	private static SimpleBooleanProperty coreVsPlanBP;
	private static SimpleBooleanProperty planVsPlanBP;


	private static LocalDate startDate;
	private static LocalDate endDate;
	private static LocalDate scheduleDateA;
	private static LocalDate scheduleDateB;

	private static ObservableList<String> validCoreDayList = FXCollections.observableList(new ArrayList<String>());
	private static ObservableList<String> validPlanDayListCoreVsPlan = FXCollections.observableList(new ArrayList<String>());
	private static ObservableList<String> validPlanDayListPlanVsPlan = FXCollections.observableList(new ArrayList<String>());

	private static ObservableValue<String> con1NameAsObservable;
	private static ObservableValue<String> con2NameAsObservable;

	@FXML private Tab coreVsPlanTab;
	@FXML private Tab planVsPlanTab;

	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label coreDatesLabel;
	@FXML private Label coreDateStatusMLabel;
	@FXML private Label coreDateStatusTLabel;
	@FXML private Label coreDateStatusWLabel;
	@FXML private Label coreDateStatusRLabel;
	@FXML private Label coreDateStatusFLabel;
	@FXML private Label coreDateStatusSaLabel;
	@FXML private Label coreDateStatusSuLabel;
	@FXML private Label planDatesLabel;
	@FXML private Label planDateStatusMLabel;
	@FXML private Label planDateStatusTLabel;
	@FXML private Label planDateStatusWLabel;
	@FXML private Label planDateStatusRLabel;
	@FXML private Label planDateStatusFLabel;
	@FXML private Label planDateStatusSaLabel;
	@FXML private Label planDateStatusSuLabel;
	@FXML private Label planVsCoreStep1TextLabel;
	@FXML private Label planVsCoreStep2TextLabel;
	@FXML private Label step3TextLabel;
	@FXML private Label step1LabelPlanVsCore;
	@FXML private Label step2LabelPlanVsCore;
	@FXML private Label step1LabelPlanVsPlan;
	@FXML private Label step2LabelPlanVsPlan;
	@FXML private Label step3Label;
	@FXML private Label con1Label;
	@FXML private Label con2Label;
	@FXML private Label planVsPlanStep1TextLabel;
	@FXML private Label planVsPlanStep2TextLabel;

	@FXML private DatePicker planVsCoreStartDatePicker;
	@FXML private DatePicker planVsCoreEndDatePicker;
	@FXML private DatePicker planVsPlanDatePicker1;
	@FXML private DatePicker planVsPlanDatePicker2;

	@FXML private RadioButton con1RadioButton;
	@FXML private RadioButton con2RadioButton;

	@FXML private TextArea textArea;

	@FXML private ProgressBar progressBar;

	public BIASS3CompareSchedulePageController()
	{
		prefs = Preferences.userRoot().node("BIAS");

		con1NameAsObservable = BIASS3CompareScheduleConfigPageController.getProfileName1AsObservable();
		con2NameAsObservable = BIASS3CompareScheduleConfigPageController.getProfileName2AsObservable();	
	}

	@FXML private void initialize()
	{
		coreVsPlanBP = new SimpleBooleanProperty();
		planVsPlanBP = new SimpleBooleanProperty();

		planDateStatusMLabel.setStyle("-fx-text-fill: red");
		planDateStatusTLabel.setStyle("-fx-text-fill: red");
		planDateStatusWLabel.setStyle("-fx-text-fill: red");
		planDateStatusRLabel.setStyle("-fx-text-fill: red");
		planDateStatusFLabel.setStyle("-fx-text-fill: red");
		planDateStatusSaLabel.setStyle("-fx-text-fill: red");
		planDateStatusSuLabel.setStyle("-fx-text-fill: red");

		coreVsPlanBP.setValue(true);

		coreVsPlanTab.setOnSelectionChanged(_ -> {
			if (coreVsPlanTab.isSelected()) 
			{ 
				coreVsPlanBP.setValue(true);
				planVsPlanBP.setValue(false);

				coreDatesLabel.setVisible(true);
				coreDateStatusMLabel.setVisible(true);
				coreDateStatusTLabel.setVisible(true);
				coreDateStatusWLabel.setVisible(true);
				coreDateStatusRLabel.setVisible(true);
				coreDateStatusFLabel.setVisible(true);
				coreDateStatusSaLabel.setVisible(true);
				coreDateStatusSuLabel.setVisible(true); 

				updatePlanDaysForCoreVsPlan();
			}
		});

		planVsPlanTab.setOnSelectionChanged(_ -> {
			if (planVsPlanTab.isSelected()) 
			{ 
				coreVsPlanBP.setValue(false);
				planVsPlanBP.setValue(true);

				coreDatesLabel.setVisible(false);
				coreDateStatusMLabel.setVisible(false);
				coreDateStatusTLabel.setVisible(false);
				coreDateStatusWLabel.setVisible(false);
				coreDateStatusRLabel.setVisible(false);
				coreDateStatusFLabel.setVisible(false);
				coreDateStatusSaLabel.setVisible(false);
				coreDateStatusSuLabel.setVisible(false);

				updatePlanDaysForPlanVsPlan();
			}
		});

		startDateSelectedBP = new SimpleBooleanProperty();
		endDateSelectedBP = new SimpleBooleanProperty();

		firstDateSelectedBP = new SimpleBooleanProperty();
		secondDateSelectedBP = new SimpleBooleanProperty();

		planVsCoreStartDatePicker.setDayCellFactory(getFutureDatesOnlyFactory(true));
		planVsCoreStartDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				if (planVsCoreStartDatePicker.getValue() != null)
				{
					startDate = planVsCoreStartDatePicker.getValue();
					startDateSelectedBP.set(true);
				}
				else
				{
					startDateSelectedBP.set(false);
					startDate = null;
				}

				updatePlanDaysForCoreVsPlan();
			}
		});

		planVsCoreEndDatePicker.setDayCellFactory(getFutureDatesOnlyFactory(true));
		planVsCoreEndDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				if (planVsCoreEndDatePicker.getValue() != null)
				{
					endDate = planVsCoreEndDatePicker.getValue();
					endDateSelectedBP.set(true);
				}
				else
				{
					endDateSelectedBP.set(false);
					endDate = null;
				}

				updatePlanDaysForCoreVsPlan();
			}
		});

		planVsPlanDatePicker1.setDayCellFactory(getFutureDatesOnlyFactory(true));
		planVsPlanDatePicker1.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				if (planVsPlanDatePicker1.getValue() != null)
				{
					scheduleDateA = planVsPlanDatePicker1.getValue();
					firstDateSelectedBP.set(true);
				}
				else
				{
					firstDateSelectedBP.set(false);
					scheduleDateA = null;
				}
				updatePlanDaysForPlanVsPlan();
			}
		});

		planVsPlanDatePicker2.setDayCellFactory(getFutureDatesOnlyFactory(true));
		planVsPlanDatePicker2.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				if (planVsPlanDatePicker2.getValue() != null)
				{
					scheduleDateB = planVsPlanDatePicker2.getValue();
					secondDateSelectedBP.set(true);
				}
				else
				{
					secondDateSelectedBP.set(false);
					scheduleDateB = null;
				}

				updatePlanDaysForPlanVsPlan();
			}
		});

		startDateSelectedBP.addListener((_, _, newValue) -> {
			if (newValue.equals(true)) 
			{
				planVsCoreEndDatePicker.setDisable(false);
				planVsCoreStep2TextLabel.setDisable(false);
				step2LabelPlanVsCore.setDisable(false);
			}
			else
			{
				planVsCoreEndDatePicker.setDisable(true);
				planVsCoreEndDatePicker.setValue(null);
				planVsCoreStep2TextLabel.setDisable(true);
				step2LabelPlanVsCore.setDisable(true);
			}
		});

		endDateSelectedBP.addListener((_, _, newValue) -> {
			if (newValue.equals(true)) 
			{
				con1Label.disableProperty().unbind();
				con2Label.disableProperty().unbind();
				con1Label.setDisable(false);
				con2Label.setDisable(false);
				con1Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con2Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
			}
			else
			{
				con1Label.disableProperty().unbind();
				con2Label.disableProperty().unbind();
				con1Label.setDisable(true);
				con2Label.setDisable(true);
				con1Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con2Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
			}
		});

		firstDateSelectedBP.addListener((_, _, newValue) -> {
			if (newValue.equals(true)) 
			{
				planVsPlanDatePicker2.setDisable(false);
				planVsPlanStep2TextLabel.setDisable(false);
				step2LabelPlanVsPlan.setDisable(false);
			}
			else
			{
				planVsPlanDatePicker2.setDisable(true);
				planVsPlanDatePicker2.setValue(null);
				planVsPlanStep2TextLabel.setDisable(true);
				step2LabelPlanVsPlan.setDisable(true);
			}
		});

		secondDateSelectedBP.addListener((_, _, newValue) -> {
			if (newValue.equals(true)) 
			{
				con1Label.disableProperty().unbind();
				con2Label.disableProperty().unbind();
				con1Label.setDisable(false);
				con2Label.setDisable(false);
				con1Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con2Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
			}
			else
			{
				con1Label.disableProperty().unbind();
				con2Label.disableProperty().unbind();
				con1Label.setDisable(true);
				con2Label.setDisable(true);
				con1Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con2Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
			}
		});

		validCoreDayList.addListener(new ListChangeListener<String>() {
			@Override
			//onChanged method
			public void onChanged(ListChangeListener.Change c) {

				if (validCoreDayList.contains("M"))
				{
					coreDateStatusMLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					coreDateStatusMLabel.setStyle("-fx-text-fill: red");
				}

				if (validCoreDayList.contains("T"))
				{
					coreDateStatusTLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					coreDateStatusTLabel.setStyle("-fx-text-fill: red");
				}

				if (validCoreDayList.contains("W"))
				{
					coreDateStatusWLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					coreDateStatusWLabel.setStyle("-fx-text-fill: red");
				}

				if (validCoreDayList.contains("R"))
				{
					coreDateStatusRLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					coreDateStatusRLabel.setStyle("-fx-text-fill: red");
				}

				if (validCoreDayList.contains("F"))
				{
					coreDateStatusFLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					coreDateStatusFLabel.setStyle("-fx-text-fill: red");
				}

				if (validCoreDayList.contains("Sa"))
				{
					coreDateStatusSaLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					coreDateStatusSaLabel.setStyle("-fx-text-fill: red");
				}

				if (validCoreDayList.contains("Su"))
				{
					coreDateStatusSuLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					coreDateStatusSuLabel.setStyle("-fx-text-fill: red");
				}
			}
		});

		validPlanDayListCoreVsPlan.addListener(new ListChangeListener<String>() {
			@Override
			//onChanged method
			public void onChanged(ListChangeListener.Change c) {
				if (validPlanDayListCoreVsPlan.contains("M"))
				{
					planDateStatusMLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					planDateStatusMLabel.setStyle("-fx-text-fill: red");
				}

				if (validPlanDayListCoreVsPlan.contains("T"))
				{
					planDateStatusTLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					planDateStatusTLabel.setStyle("-fx-text-fill: red");
				}

				if (validPlanDayListCoreVsPlan.contains("W"))
				{
					planDateStatusWLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					planDateStatusWLabel.setStyle("-fx-text-fill: red");
				}

				if (validPlanDayListCoreVsPlan.contains("R"))
				{
					planDateStatusRLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					planDateStatusRLabel.setStyle("-fx-text-fill: red");
				}

				if (validPlanDayListCoreVsPlan.contains("F"))
				{
					planDateStatusFLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					planDateStatusFLabel.setStyle("-fx-text-fill: red");
				}

				if (validPlanDayListCoreVsPlan.contains("Sa"))
				{
					planDateStatusSaLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					planDateStatusSaLabel.setStyle("-fx-text-fill: red");
				}

				if (validPlanDayListCoreVsPlan.contains("Su"))
				{
					planDateStatusSuLabel.setStyle("-fx-text-fill: green");
				}
				else
				{
					planDateStatusSuLabel.setStyle("-fx-text-fill: red");
				}
			}
		});

		// Update API connection 1 labels
		if (con1NameAsObservable.getValue().equals(""))
			con1Label.setText("N/A");
		else
			con1Label.setText(con1NameAsObservable.getValue());

		con1NameAsObservable.addListener((_, _, newValue) -> {
			if (newValue.equals(""))
				con1Label.setText("N/A");
			else
				con1Label.setText(newValue);
		});	

		// Update API connection 2 labels
		if (con2NameAsObservable.getValue().equals("")) 
			con2Label.setText("N/A");
		else
			con2Label.setText(con2NameAsObservable.getValue());

		con2NameAsObservable.addListener((_, _, newValue) -> {
			if (newValue.equals(""))
				con2Label.setText("N/A");
			else
				con2Label.setText(newValue);
		});	

		// disable execute button and API radio buttons if 
		// CONDITION A:  1) there isn't a start or 2) an there isn't an end date or 3) if all plan DOW are not present in core assignments or 4) on core vs plan tab
		// CONDITION B:  1) there isn't a first date or 2) there isn't a second date or 3) on plan vs plan tab
		disableApiConnectionsAndExecuteButton = (planVsPlanBP.or(startDateSelectedBP.not().or(endDateSelectedBP.not()))).and((coreVsPlanBP.or(firstDateSelectedBP.not().or(secondDateSelectedBP.not()))));
		executeButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		con1RadioButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		con1Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		con2RadioButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		con2Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		step3TextLabel.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		step3Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);

		boolean mondayDateExists = Boolean.getBoolean(prefs.get("s3_mondayCoreDate", null));
		if ((mondayDateExists) && (!prefs.get("s3_mondayCoreDate", "").equals("M")))
		{
			validCoreDayList.add("M");
		}

		boolean tuesdayDateExists = Boolean.getBoolean(prefs.get("s3_tuesdayCoreDate", null));
		if ((tuesdayDateExists) && (!prefs.get("s3_tuesdayCoreDate", "").equals("T")))
		{
			validCoreDayList.add("T");
		}

		boolean wednesdayDateExists = Boolean.getBoolean(prefs.get("s3_wednesdayCoreDate", null));
		if ((wednesdayDateExists) && (!prefs.get("s3_wednesdayCoreDate", "").equals("W")))
		{
			validCoreDayList.add("W");
		}

		boolean thursdayDateExists = Boolean.getBoolean(prefs.get("s3_thursdayCoreDate", null));
		if ((thursdayDateExists) && (!prefs.get("s3_thursdayCoreDate", "").equals("R")))
		{
			validCoreDayList.add("R");
		}

		boolean fridayDateExists = Boolean.getBoolean(prefs.get("s3_fridayCoreDate", null));
		if ((fridayDateExists) && (!prefs.get("s3_fridayCoreDate", "").equals("F")))
		{
			validCoreDayList.add("F");
		}		

		boolean saturdayDateExists = Boolean.getBoolean(prefs.get("s3_saturdayCoreDate", null));
		if ((saturdayDateExists) && (!prefs.get("s3_saturdayCoreDate", "").equals("Sa")))
		{
			validCoreDayList.add("Sa");
		}

		boolean sundayDateExists = Boolean.getBoolean(prefs.get("s3_sundayCoreDate", null));
		if ((sundayDateExists) && (!prefs.get("s3_sundayCoreDate", "").equals("Su")))
		{
			validCoreDayList.add("Su");
		}

		if (validCoreDayList.contains("M"))
		{
			coreDateStatusMLabel.setStyle("-fx-text-fill: green");
		}
		else
		{
			coreDateStatusMLabel.setStyle("-fx-text-fill: red");
		}

		if (validCoreDayList.contains("T"))
		{
			coreDateStatusTLabel.setStyle("-fx-text-fill: green");
		}
		else
		{
			coreDateStatusTLabel.setStyle("-fx-text-fill: red");
		}

		if (validCoreDayList.contains("W"))
		{
			coreDateStatusWLabel.setStyle("-fx-text-fill: green");
		}
		else
		{
			coreDateStatusWLabel.setStyle("-fx-text-fill: red");
		}

		if (validCoreDayList.contains("R"))
		{
			coreDateStatusRLabel.setStyle("-fx-text-fill: green");
		}
		else
		{
			coreDateStatusRLabel.setStyle("-fx-text-fill: red");
		}

		if (validCoreDayList.contains("F"))
		{
			coreDateStatusFLabel.setStyle("-fx-text-fill: green");
		}
		else
		{
			coreDateStatusFLabel.setStyle("-fx-text-fill: red");
		}

		if (validCoreDayList.contains("Sa"))
		{
			coreDateStatusSaLabel.setStyle("-fx-text-fill: green");
		}
		else
		{
			coreDateStatusSaLabel.setStyle("-fx-text-fill: red");
		}

		if (validCoreDayList.contains("Su"))
		{
			coreDateStatusSuLabel.setStyle("-fx-text-fill: green");
		}
		else
		{
			coreDateStatusSuLabel.setStyle("-fx-text-fill: red");
		}
	}

	@FXML private void handleExecuteButton(ActionEvent event) 
	{
		// Get location to save file to if not using system time as file name
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("s3_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("s3_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
				{
					fileChooser.setInitialDirectory(path.toFile());
				}
			}

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File file = null;

			file = fileChooser.showSaveDialog(stageForFolderChooser);

			if (file != null) 
			{
				clearMessage();
				try 
				{
					saveFileLocationForUserSpecifiedFileName = file.toString();
					if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
						prefs.put("s3_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				if (coreVsPlanBP.getValue())
					message = "Starting S3 Core vs Plan Schedule Analysis at "+ConvertDateTime.getTimeStamp()+"\n";
				else
					message = "Starting S3 Plan vs Plan Schedule Analysis at "+ConvertDateTime.getTimeStamp()+"\n";
				displayMessage(message);

				// Unbind buttons
				executeButton.disableProperty().unbind();
				con1RadioButton.disableProperty().unbind();
				con2RadioButton.disableProperty().unbind();
				con1Label.disableProperty().unbind();
				con2Label.disableProperty().unbind();
				step3Label.disableProperty().unbind();
				step3TextLabel.disableProperty().unbind();

				// Disable controls
				coreVsPlanTab.setDisable(true);
				planVsPlanTab.setDisable(true);
				step1LabelPlanVsCore.setDisable(true);
				planVsCoreStep1TextLabel.setDisable(true);
				step2LabelPlanVsCore.setDisable(true);
				planVsCoreStep2TextLabel.setDisable(true);
				planVsCoreStartDatePicker.setDisable(true);
				planVsCoreEndDatePicker.setDisable(true);
				step3Label.setDisable(true);
				step3TextLabel.setDisable(true);
				con1RadioButton.setDisable(true);
				con2RadioButton.setDisable(true);
				con1Label.setDisable(true);
				con2Label.setDisable(true);
				coreDatesLabel.setDisable(true);
				planDatesLabel.setDisable(true);
				coreDateStatusMLabel.setDisable(true);
				coreDateStatusTLabel.setDisable(true);
				coreDateStatusWLabel.setDisable(true);
				coreDateStatusRLabel.setDisable(true);
				coreDateStatusFLabel.setDisable(true);
				coreDateStatusSaLabel.setDisable(true);
				coreDateStatusSuLabel.setDisable(true);
				planDateStatusMLabel.setDisable(true);
				planDateStatusTLabel.setDisable(true);
				planDateStatusWLabel.setDisable(true);
				planDateStatusRLabel.setDisable(true);
				planDateStatusFLabel.setDisable(true);
				planDateStatusSaLabel.setDisable(true);
				planDateStatusSuLabel.setDisable(true);
				executeButton.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				// Rebind buttons
				executeButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con1RadioButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con2RadioButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con1Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con2Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				step3Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				step3TextLabel.disableProperty().bind(disableApiConnectionsAndExecuteButton);

				// Enable controls
				coreVsPlanTab.setDisable(false);
				planVsPlanTab.setDisable(false);
				step1LabelPlanVsCore.setDisable(false);
				planVsCoreStep1TextLabel.setDisable(false);
				step2LabelPlanVsCore.setDisable(false);
				planVsCoreStep2TextLabel.setDisable(false);
				planVsCoreStartDatePicker.setDisable(false);
				planVsCoreEndDatePicker.setDisable(false);
				coreDatesLabel.setDisable(false);
				planDatesLabel.setDisable(false);
				coreDateStatusMLabel.setDisable(false);
				coreDateStatusTLabel.setDisable(false);
				coreDateStatusWLabel.setDisable(false);
				coreDateStatusRLabel.setDisable(false);
				coreDateStatusFLabel.setDisable(false);
				coreDateStatusSaLabel.setDisable(false);
				coreDateStatusSuLabel.setDisable(false);
				planDateStatusMLabel.setDisable(false);
				planDateStatusTLabel.setDisable(false);
				planDateStatusWLabel.setDisable(false);
				planDateStatusRLabel.setDisable(false);
				planDateStatusFLabel.setDisable(false);
				planDateStatusSaLabel.setDisable(false);
				planDateStatusSuLabel.setDisable(false);
				planVsCoreStartDatePicker.setValue(null);
				planVsCoreEndDatePicker.setValue(null);

				executeButton.setVisible(true);
				resetButton.setVisible(false);
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("s3_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("s3_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
					directoryChooser.setInitialDirectory(path.toFile());
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				clearMessage();
				if (coreVsPlanBP.getValue())
					message = "Starting S3 Core vs Plan Schedule Analysis at "+ConvertDateTime.getTimeStamp()+"\n";
				else
					message = "Starting S3 Plan vs Plan Schedule Analysis at "+ConvertDateTime.getTimeStamp()+"\n";
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("s3_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				// Unbind buttons
				executeButton.disableProperty().unbind();
				con1RadioButton.disableProperty().unbind();
				con2RadioButton.disableProperty().unbind();
				con1Label.disableProperty().unbind();
				con2Label.disableProperty().unbind();
				step3Label.disableProperty().unbind();
				step3TextLabel.disableProperty().unbind();

				// Disable controls
				coreVsPlanTab.setDisable(true);
				planVsPlanTab.setDisable(true);
				step1LabelPlanVsCore.setDisable(true);
				planVsCoreStep1TextLabel.setDisable(true);
				step2LabelPlanVsCore.setDisable(true);
				planVsCoreStep2TextLabel.setDisable(true);
				planVsCoreStartDatePicker.setDisable(true);
				planVsCoreEndDatePicker.setDisable(true);
				step3Label.setDisable(true);
				step3TextLabel.setDisable(true);
				con1Label.setDisable(true);
				con2Label.setDisable(true);
				con1RadioButton.setDisable(true);
				con2RadioButton.setDisable(true);
				coreDatesLabel.setDisable(true);
				planDatesLabel.setDisable(true);
				coreDateStatusMLabel.setDisable(true);
				coreDateStatusTLabel.setDisable(true);
				coreDateStatusWLabel.setDisable(true);
				coreDateStatusRLabel.setDisable(true);
				coreDateStatusFLabel.setDisable(true);
				coreDateStatusSaLabel.setDisable(true);
				coreDateStatusSuLabel.setDisable(true);
				planDateStatusMLabel.setDisable(true);
				planDateStatusTLabel.setDisable(true);
				planDateStatusWLabel.setDisable(true);
				planDateStatusRLabel.setDisable(true);
				planDateStatusFLabel.setDisable(true);
				planDateStatusSaLabel.setDisable(true);
				planDateStatusSuLabel.setDisable(true);
				executeButton.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				// Rebind buttons
				executeButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con1RadioButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con2RadioButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con1Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				con2Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				step3Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
				step3TextLabel.disableProperty().bind(disableApiConnectionsAndExecuteButton);

				// Enable controls
				coreVsPlanTab.setDisable(false);
				planVsPlanTab.setDisable(false);
				step1LabelPlanVsCore.setDisable(false);
				planVsCoreStep1TextLabel.setDisable(false);
				step2LabelPlanVsCore.setDisable(false);
				planVsCoreStep2TextLabel.setDisable(false);
				planVsCoreStartDatePicker.setDisable(false);
				planVsCoreEndDatePicker.setDisable(false);
				coreDatesLabel.setDisable(false);
				planDatesLabel.setDisable(false);
				coreDateStatusMLabel.setDisable(false);
				coreDateStatusTLabel.setDisable(false);
				coreDateStatusWLabel.setDisable(false);
				coreDateStatusRLabel.setDisable(false);
				coreDateStatusFLabel.setDisable(false);
				coreDateStatusSaLabel.setDisable(false);
				coreDateStatusSuLabel.setDisable(false);
				planDateStatusMLabel.setDisable(false);
				planDateStatusTLabel.setDisable(false);
				planDateStatusWLabel.setDisable(false);
				planDateStatusRLabel.setDisable(false);
				planDateStatusFLabel.setDisable(false);
				planDateStatusSaLabel.setDisable(false);
				planDateStatusSuLabel.setDisable(false);
				planVsCoreStartDatePicker.setValue(null);
				planVsCoreEndDatePicker.setValue(null);

				executeButton.setVisible(true);
				resetButton.setVisible(false);
			}	
		}
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		resetMessage();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);

		// Enable controls
		coreVsPlanTab.setDisable(false);
		planVsPlanTab.setDisable(false);
		step1LabelPlanVsCore.setDisable(false);
		planVsCoreStep1TextLabel.setDisable(false);
		step2LabelPlanVsCore.setDisable(false);
		planVsCoreStep2TextLabel.setDisable(false);
		planVsCoreStartDatePicker.setDisable(false);
		planVsCoreEndDatePicker.setDisable(false);	
		step3Label.setDisable(false);
		step3TextLabel.setDisable(false);
		con1Label.setDisable(false);
		con2Label.setDisable(false);
		con1RadioButton.setDisable(false);
		con2RadioButton.setDisable(false);
		coreDatesLabel.setDisable(false);
		planDatesLabel.setDisable(false);
		coreDateStatusMLabel.setDisable(false);
		coreDateStatusTLabel.setDisable(false);
		coreDateStatusWLabel.setDisable(false);
		coreDateStatusRLabel.setDisable(false);
		coreDateStatusFLabel.setDisable(false);
		coreDateStatusSaLabel.setDisable(false);
		coreDateStatusSuLabel.setDisable(false);
		planDateStatusMLabel.setDisable(false);
		planDateStatusTLabel.setDisable(false);
		planDateStatusWLabel.setDisable(false);
		planDateStatusRLabel.setDisable(false);
		planDateStatusFLabel.setDisable(false);
		planDateStatusSaLabel.setDisable(false);
		planDateStatusSuLabel.setDisable(false);
		planVsCoreStartDatePicker.setValue(null);
		planVsCoreEndDatePicker.setValue(null);
		planVsPlanDatePicker1.setValue(null);
		planVsPlanDatePicker2.setValue(null);

		// Rebind buttons
		executeButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		con1RadioButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		con2RadioButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		con1Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		con2Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		step3Label.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		step3TextLabel.disableProperty().bind(disableApiConnectionsAndExecuteButton);

		executeButton.setVisible(true);
		resetButton.setVisible(false);
	}     	

	private void startTask()
	{
		progressBar.setVisible(true);

		Runnable task = new Runnable()
		{
			@Override
			public void run() 
			{
				try
				{
					runTask();
				}
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}
			}
		};

		Thread backgroundThread = new Thread(task);
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}

	private void runTask() throws Exception
	{
		continueAnalysis = true;

		if (coreVsPlanBP.getValue())
		{
			// Check date range is ordered properly
			if (startDate.isAfter(endDate))
			{
				continueAnalysis = false;
				displayMessage("\nSelected Start Date is after End Date.");
			}

			// Check that selected date range is not in the past
			if ((startDate.isBefore(LocalDate.now())) || (endDate.isBefore(LocalDate.now())))
			{
				continueAnalysis = false;
				displayMessage("\nSelected Start and/or End Date are in the past.");
			}

			// Check that planned DOWs intersects with core DOWs
			if (((validPlanDayListCoreVsPlan.contains("M")) && (!validCoreDayList.contains("M"))) 
					|| ((validPlanDayListCoreVsPlan.contains("T")) && (!validCoreDayList.contains("T"))) 
					|| ((validPlanDayListCoreVsPlan.contains("W")) && (!validCoreDayList.contains("W")))
					|| ((validPlanDayListCoreVsPlan.contains("R")) && (!validCoreDayList.contains("R")))
					|| ((validPlanDayListCoreVsPlan.contains("F")) && (!validCoreDayList.contains("F")))
					|| ((validPlanDayListCoreVsPlan.contains("Sa")) && (!validCoreDayList.contains("Sa")))
					|| ((validPlanDayListCoreVsPlan.contains("Su")) && (!validCoreDayList.contains("Su"))))
			{
				continueAnalysis = false;
				displayMessage("\nPlanned day-of-weeks must intersect with Core day-of-weeks.");
			}

			// Check that core dates are not null
			if  ((coreVsPlanBP.getValue()) 
					&& (validPlanDayListCoreVsPlan.contains("M"))
					&& (BIASS3CompareScheduleConfigPageController.getMondayCoreDate() == null))	
			{
				continueAnalysis = false;
				displayMessage("\nMonday's Core Date is null.");
			}

			if  ((coreVsPlanBP.getValue()) 
					&& (validPlanDayListCoreVsPlan.contains("T"))
					&& (BIASS3CompareScheduleConfigPageController.getTuesdayCoreDate() == null))
			{
				continueAnalysis = false;
				displayMessage("\nTuesday's Core Date is null.");
			}

			if  ((coreVsPlanBP.getValue())
					&& (validPlanDayListCoreVsPlan.contains("W"))
					&& (BIASS3CompareScheduleConfigPageController.getWednesdayCoreDate() == null))
			{
				continueAnalysis = false;
				displayMessage("\nWednesday's Core Date is null.");
			}

			if  ((coreVsPlanBP.getValue()) 
					&& (validPlanDayListCoreVsPlan.contains("R"))
					&& (BIASS3CompareScheduleConfigPageController.getThursdayCoreDate() == null))
			{
				continueAnalysis = false;
				displayMessage("\nThursday's Core Date is null.");
			}

			if  ((coreVsPlanBP.getValue()) 
					&& (validPlanDayListCoreVsPlan.contains("F"))
					&& (BIASS3CompareScheduleConfigPageController.getFridayCoreDate() == null))
			{
				continueAnalysis = false;
				displayMessage("\nFriday's Core Date is null.");
			}

			if  ((coreVsPlanBP.getValue()) 
					&& (validPlanDayListCoreVsPlan.contains("Sa"))
					&& (BIASS3CompareScheduleConfigPageController.getSaturdayCoreDate() == null))
			{
				continueAnalysis = false;
				displayMessage("\nSaturday's Core Date is null.");
			}

			if  ((coreVsPlanBP.getValue()) 
					&& (validPlanDayListCoreVsPlan.contains("Su"))
					&& (BIASS3CompareScheduleConfigPageController.getSundayCoreDate() == null))
			{
				continueAnalysis = false;
				displayMessage("\nSunday's Core Date is null.");
			}

			// Check that required core dates are not in the past
			if (continueAnalysis) 
			{
				if  ((coreVsPlanBP.getValue()) 
						&& (validPlanDayListCoreVsPlan.contains("M"))
						&& (BIASS3CompareScheduleConfigPageController.getMondayCoreDate().isBefore(LocalDate.now())))
				{
					continueAnalysis = false;
					displayMessage("\nMonday's Core Date is in the past.");
				}

				if  ((coreVsPlanBP.getValue()) 
						&& (validPlanDayListCoreVsPlan.contains("T"))
						&& (BIASS3CompareScheduleConfigPageController.getTuesdayCoreDate().isBefore(LocalDate.now())))
				{
					continueAnalysis = false;
					displayMessage("\nTuesday's Core Date is in the past.");
				}

				if  ((coreVsPlanBP.getValue()) 
						&& (validPlanDayListCoreVsPlan.contains("W"))
						&& (BIASS3CompareScheduleConfigPageController.getWednesdayCoreDate().isBefore(LocalDate.now())))
				{
					continueAnalysis = false;
					displayMessage("\nWednesday's Core Date is in the past.");
				}

				if  ((coreVsPlanBP.getValue()) 
						&& (validPlanDayListCoreVsPlan.contains("R"))
						&& (BIASS3CompareScheduleConfigPageController.getThursdayCoreDate().isBefore(LocalDate.now())))
				{
					continueAnalysis = false;
					displayMessage("\nThursday's Core Date is in the past.");
				}

				if  ((coreVsPlanBP.getValue()) 
						&& (validPlanDayListCoreVsPlan.contains("F"))
						&& (BIASS3CompareScheduleConfigPageController.getFridayCoreDate().isBefore(LocalDate.now())))
				{
					continueAnalysis = false;
					displayMessage("\nFriday's Core Date is in the past.");
				}

				if  ((coreVsPlanBP.getValue()) 
						&& (validPlanDayListCoreVsPlan.contains("Sa"))
						&& (BIASS3CompareScheduleConfigPageController.getSaturdayCoreDate().isBefore(LocalDate.now())))
				{
					continueAnalysis = false;
					displayMessage("\nSaturday's Core Date is in the past.");
				}

				if  ((coreVsPlanBP.getValue()) 
						&& (validPlanDayListCoreVsPlan.contains("Su"))
						&& (BIASS3CompareScheduleConfigPageController.getSundayCoreDate().isBefore(LocalDate.now())))
				{
					continueAnalysis = false;
					displayMessage("\nSunday's Core Date is in the past.");
				}
			}
		}
		else
		{
			// Check if dates are the same
			if (scheduleDateA.isEqual(scheduleDateB))
			{
				continueAnalysis = false;
				displayMessage("\nSelected dates are identical.");
			}
		}

		if (!continueAnalysis)
			displayMessage("\n\n*** PROCESSING NOT COMPLETE!!! ***");

		if (continueAnalysis)
		{
			// Read all objects that are required for the analysis
			// Core vs Plan
			if (coreVsPlanBP.getValue())
			{
				ReadS3CompareScheduleFilesCoreVsPlan readDataCoreVsPlan = null;
				if (con1RadioButton.isSelected())
					readDataCoreVsPlan = new ReadS3CompareScheduleFilesCoreVsPlan(con1NameAsObservable.getValue().toString(), BIASS3CompareScheduleConfigPageController.getUri1(), BIASS3CompareScheduleConfigPageController.getClientId1(), 
							BIASS3CompareScheduleConfigPageController.getClientSecret1(), BIASS3CompareScheduleConfigPageController.getUserName1(), BIASS3CompareScheduleConfigPageController.getPassword1(), 
							BIASS3CompareScheduleConfigPageController.getGrantType1(), BIASS3CompareScheduleConfigPageController.getCode1(), startDate, endDate,
							BIASS3CompareScheduleConfigPageController.getMondayCoreDate(), BIASS3CompareScheduleConfigPageController.getTuesdayCoreDate(), BIASS3CompareScheduleConfigPageController.getWednesdayCoreDate(),
							BIASS3CompareScheduleConfigPageController.getThursdayCoreDate(), BIASS3CompareScheduleConfigPageController.getFridayCoreDate(), BIASS3CompareScheduleConfigPageController.getSaturdayCoreDate(),
							BIASS3CompareScheduleConfigPageController.getSundayCoreDate(), BIASS3CompareScheduleConfigPageController.getShowDetailsForRetimedTrains());
				else
					readDataCoreVsPlan = new ReadS3CompareScheduleFilesCoreVsPlan(con2NameAsObservable.getValue().toString(), BIASS3CompareScheduleConfigPageController.getUri2(), BIASS3CompareScheduleConfigPageController.getClientId2(), 
							BIASS3CompareScheduleConfigPageController.getClientSecret2(), BIASS3CompareScheduleConfigPageController.getUserName2(), BIASS3CompareScheduleConfigPageController.getPassword2(), 
							BIASS3CompareScheduleConfigPageController.getGrantType2(), BIASS3CompareScheduleConfigPageController.getCode2(), startDate, endDate,
							BIASS3CompareScheduleConfigPageController.getMondayCoreDate(), BIASS3CompareScheduleConfigPageController.getTuesdayCoreDate(), BIASS3CompareScheduleConfigPageController.getWednesdayCoreDate(),
							BIASS3CompareScheduleConfigPageController.getThursdayCoreDate(), BIASS3CompareScheduleConfigPageController.getFridayCoreDate(), BIASS3CompareScheduleConfigPageController.getSaturdayCoreDate(),
							BIASS3CompareScheduleConfigPageController.getSundayCoreDate(), BIASS3CompareScheduleConfigPageController.getShowDetailsForRetimedTrains());
				message = readDataCoreVsPlan.getResultsMessage();
				displayMessage(message);

				if (readDataCoreVsPlan.getValidFile())
				{
					setProgressIndicator(0.40);

					// Analyze trains
					S3CompareScheduleAnalysisCoreVsPlan analyze = new S3CompareScheduleAnalysisCoreVsPlan(startDate, endDate, readDataCoreVsPlan.getCoreDatesData(), readDataCoreVsPlan.getAnalyzedDatesData());
					message = analyze.getResultsMessage();
					displayMessage(message);

					setProgressIndicator(0.80);

					// Write results to spreadsheet
					WriteS3CompareScheduleFilesCoreVsPlan2 writeFiles = new WriteS3CompareScheduleFilesCoreVsPlan2(con1RadioButton.isSelected(), con2RadioButton.isSelected(), textArea.getText().toString(), startDate, endDate, analyze.getTrainsInAnalyzedDayButNotCoreDay(), analyze.getTrainsInCoreDayButNotAnalyzedDay(), analyze.getTrainsWithDifferentParameters(), readDataCoreVsPlan.getShowDetailsForRetimedTrains(), readDataCoreVsPlan.getCoreDatesData(), readDataCoreVsPlan.getAnalyzedDatesData());
					message = writeFiles.getResultsMessage2();
					displayMessage(message);

					if (!WriteS3CompareScheduleFilesCoreVsPlan2.getErrorFound())
					{
						setProgressIndicator(1.0);
						displayMessage("\n*** PROCESSING COMPLETE ***");
					}
					else
					{
						displayMessage("\nError in writing files");
						displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
					}
				}
				else
				{
					displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
				}
			}
			else
			{
				//Plan vs Plan
				ReadS3CompareScheduleFilesPlanVsPlan readDataPlanVsPlan = null;
				if (con1RadioButton.isSelected())
					readDataPlanVsPlan = new ReadS3CompareScheduleFilesPlanVsPlan(con1NameAsObservable.getValue().toString(), BIASS3CompareScheduleConfigPageController.getUri1(), BIASS3CompareScheduleConfigPageController.getClientId1(), 
							BIASS3CompareScheduleConfigPageController.getClientSecret1(), BIASS3CompareScheduleConfigPageController.getUserName1(), BIASS3CompareScheduleConfigPageController.getPassword1(), 
							BIASS3CompareScheduleConfigPageController.getGrantType1(), BIASS3CompareScheduleConfigPageController.getCode1(), scheduleDateA, scheduleDateB,
							BIASS3CompareScheduleConfigPageController.getShowDetailsForRetimedTrains());
				else
					readDataPlanVsPlan = new ReadS3CompareScheduleFilesPlanVsPlan(con2NameAsObservable.getValue().toString(), BIASS3CompareScheduleConfigPageController.getUri2(), BIASS3CompareScheduleConfigPageController.getClientId2(), 
							BIASS3CompareScheduleConfigPageController.getClientSecret2(), BIASS3CompareScheduleConfigPageController.getUserName2(), BIASS3CompareScheduleConfigPageController.getPassword2(), 
							BIASS3CompareScheduleConfigPageController.getGrantType2(), BIASS3CompareScheduleConfigPageController.getCode2(), scheduleDateA, scheduleDateB,
							BIASS3CompareScheduleConfigPageController.getShowDetailsForRetimedTrains());
				message = readDataPlanVsPlan.getResultsMessage();
				displayMessage(message);

				if (readDataPlanVsPlan.getValidFile())
				{
					setProgressIndicator(0.40);

					// Analyze trains
					S3CompareScheduleAnalysisPlanVsPlan analyze = new S3CompareScheduleAnalysisPlanVsPlan(scheduleDateA, scheduleDateB, readDataPlanVsPlan.getAnalyzedDatesData());
					message = analyze.getResultsMessage();
					displayMessage(message);

					setProgressIndicator(0.80);

					// Write results to spreadsheet
					WriteS3CompareScheduleFilesPlanVsPlan2 writeFiles = new WriteS3CompareScheduleFilesPlanVsPlan2(con1RadioButton.isSelected(), con2RadioButton.isSelected(), textArea.getText().toString(), scheduleDateA, scheduleDateB, analyze.getTrainsInScheduleDateAButNotScheduleDateB(), analyze.getTrainsInScheduleDateBButNotScheduleDateA(), analyze.getTrainsWithDifferentParameters(), readDataPlanVsPlan.getShowDetailsForRetimedTrains(), readDataPlanVsPlan.getAnalyzedDatesData());
					message = writeFiles.getResultsMessage2();
					displayMessage(message);

					if (!WriteS3CompareScheduleFilesPlanVsPlan2.getErrorFound())
					{
						setProgressIndicator(1.0);
						displayMessage("\n*** PROCESSING COMPLETE ***");
					}
					else
					{
						displayMessage("\nError in writing files");
						displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
					}
				}
				else
				{
					displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
				}
			}
		}

		// Now reset for next case
		// Rebind buttons
		executeButton.disableProperty().bind(disableApiConnectionsAndExecuteButton);
		executeButton.setVisible(false);
		resetButton.setVisible(true);
		resetButton.setDisable(false);
	}

	private void resetMessage()
	{
		message="";
		textArea.setText("Select processing options...");
	}

	private void clearMessage()
	{
		message="";
		textArea.setText("");
	}

	private void displayMessage(String message)
	{
		Platform.runLater(() -> {
			textArea.appendText(message);
		});
	}

	private void setProgressIndicator(double value)
	{
		progressBar.setProgress(value);
	}

	public static String getSaveFileLocationForUserSpecifiedFileName()
	{
		if (!saveFileLocationForUserSpecifiedFileName.toLowerCase().endsWith(".xlsx"))
		{
			saveFileLocationForUserSpecifiedFileName += ".xlsx";
		}

		return saveFileLocationForUserSpecifiedFileName;
	}

	public static String getSaveFileFolderForSerialFileName()
	{
		return saveFileFolderForSerialFileName;
	}

	public void alertOfConfigChange(ObservableList<String> validCoreDayListFromConfig)
	{
		validCoreDayList.clear();
		validCoreDayList.addAll(validCoreDayListFromConfig);
	}

	private void updatePlanDaysForCoreVsPlan()
	{
		validPlanDayListCoreVsPlan.clear();

		planDateStatusMLabel.setStyle("-fx-text-fill: red");
		planDateStatusTLabel.setStyle("-fx-text-fill: red");
		planDateStatusWLabel.setStyle("-fx-text-fill: red");
		planDateStatusRLabel.setStyle("-fx-text-fill: red");
		planDateStatusFLabel.setStyle("-fx-text-fill: red");
		planDateStatusSaLabel.setStyle("-fx-text-fill: red");
		planDateStatusSuLabel.setStyle("-fx-text-fill: red");

		if (((startDate != null) && (endDate != null)) && (!endDate.isBefore(startDate)))
		{
			for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) 
			{
				if(date.getDayOfWeek().toString().equals("SUNDAY"))
					validPlanDayListCoreVsPlan.add("Su");
				else if(date.getDayOfWeek().toString().equals("MONDAY"))
					validPlanDayListCoreVsPlan.add("M");
				else if(date.getDayOfWeek().toString().equals("TUESDAY"))
					validPlanDayListCoreVsPlan.add("T");
				else if(date.getDayOfWeek().toString().equals("WEDNESDAY"))
					validPlanDayListCoreVsPlan.add("W");
				else if(date.getDayOfWeek().toString().equals("THURSDAY"))
					validPlanDayListCoreVsPlan.add("R");
				else if(date.getDayOfWeek().toString().equals("FRIDAY"))
					validPlanDayListCoreVsPlan.add("F");
				else if(date.getDayOfWeek().toString().equals("SATURDAY"))
					validPlanDayListCoreVsPlan.add("Sa");
			}

			if (validPlanDayListCoreVsPlan.contains("Su"))
				planDateStatusSuLabel.setStyle("-fx-text-fill: green");
			else
				planDateStatusSuLabel.setStyle("-fx-text-fill: red");

			if (validPlanDayListCoreVsPlan.contains("M"))
				planDateStatusMLabel.setStyle("-fx-text-fill: green");
			else
				planDateStatusMLabel.setStyle("-fx-text-fill: red");

			if (validPlanDayListCoreVsPlan.contains("T"))
				planDateStatusTLabel.setStyle("-fx-text-fill: green");
			else
				planDateStatusTLabel.setStyle("-fx-text-fill: red");

			if (validPlanDayListCoreVsPlan.contains("W"))
				planDateStatusWLabel.setStyle("-fx-text-fill: green");
			else
				planDateStatusWLabel.setStyle("-fx-text-fill: red");

			if (validPlanDayListCoreVsPlan.contains("R"))
				planDateStatusRLabel.setStyle("-fx-text-fill: green");
			else
				planDateStatusRLabel.setStyle("-fx-text-fill: red");

			if (validPlanDayListCoreVsPlan.contains("F"))
				planDateStatusFLabel.setStyle("-fx-text-fill: green");
			else
				planDateStatusFLabel.setStyle("-fx-text-fill: red");

			if (validPlanDayListCoreVsPlan.contains("Sa"))
				planDateStatusSaLabel.setStyle("-fx-text-fill: green");
			else
				planDateStatusSaLabel.setStyle("-fx-text-fill: red");
		}
	}

	private void updatePlanDaysForPlanVsPlan()
	{
		validPlanDayListPlanVsPlan.clear();

		planDateStatusMLabel.setStyle("-fx-text-fill: red");
		planDateStatusTLabel.setStyle("-fx-text-fill: red");
		planDateStatusWLabel.setStyle("-fx-text-fill: red");
		planDateStatusRLabel.setStyle("-fx-text-fill: red");
		planDateStatusFLabel.setStyle("-fx-text-fill: red");
		planDateStatusSaLabel.setStyle("-fx-text-fill: red");
		planDateStatusSuLabel.setStyle("-fx-text-fill: red");

		if (scheduleDateA != null)
		{
			if(scheduleDateA.getDayOfWeek().toString().equals("SUNDAY"))
				validPlanDayListPlanVsPlan.add("Su");
			else if(scheduleDateA.getDayOfWeek().toString().equals("MONDAY"))
				validPlanDayListPlanVsPlan.add("M");
			else if(scheduleDateA.getDayOfWeek().toString().equals("TUESDAY"))
				validPlanDayListPlanVsPlan.add("T");
			else if(scheduleDateA.getDayOfWeek().toString().equals("WEDNESDAY"))
				validPlanDayListPlanVsPlan.add("W");
			else if(scheduleDateA.getDayOfWeek().toString().equals("THURSDAY"))
				validPlanDayListPlanVsPlan.add("R");
			else if(scheduleDateA.getDayOfWeek().toString().equals("FRIDAY"))
				validPlanDayListPlanVsPlan.add("F");
			else if(scheduleDateA.getDayOfWeek().toString().equals("SATURDAY"))
				validPlanDayListPlanVsPlan.add("Sa");
		}

		if (scheduleDateB != null)
		{
			if(scheduleDateB.getDayOfWeek().toString().equals("SUNDAY"))
				validPlanDayListPlanVsPlan.add("Su");
			else if(scheduleDateB.getDayOfWeek().toString().equals("MONDAY"))
				validPlanDayListPlanVsPlan.add("M");
			else if(scheduleDateB.getDayOfWeek().toString().equals("TUESDAY"))
				validPlanDayListPlanVsPlan.add("T");
			else if(scheduleDateB.getDayOfWeek().toString().equals("WEDNESDAY"))
				validPlanDayListPlanVsPlan.add("W");
			else if(scheduleDateB.getDayOfWeek().toString().equals("THURSDAY"))
				validPlanDayListPlanVsPlan.add("R");
			else if(scheduleDateB.getDayOfWeek().toString().equals("FRIDAY"))
				validPlanDayListPlanVsPlan.add("F");
			else if(scheduleDateB.getDayOfWeek().toString().equals("SATURDAY"))
				validPlanDayListPlanVsPlan.add("Sa");
		}

		if (validPlanDayListPlanVsPlan.contains("Su"))
			planDateStatusSuLabel.setStyle("-fx-text-fill: green");
		else
			planDateStatusSuLabel.setStyle("-fx-text-fill: red");

		if (validPlanDayListPlanVsPlan.contains("M"))
			planDateStatusMLabel.setStyle("-fx-text-fill: green");
		else
			planDateStatusMLabel.setStyle("-fx-text-fill: red");

		if (validPlanDayListPlanVsPlan.contains("T"))
			planDateStatusTLabel.setStyle("-fx-text-fill: green");
		else
			planDateStatusTLabel.setStyle("-fx-text-fill: red");

		if (validPlanDayListPlanVsPlan.contains("W"))
			planDateStatusWLabel.setStyle("-fx-text-fill: green");
		else
			planDateStatusWLabel.setStyle("-fx-text-fill: red");

		if (validPlanDayListPlanVsPlan.contains("R"))
			planDateStatusRLabel.setStyle("-fx-text-fill: green");
		else
			planDateStatusRLabel.setStyle("-fx-text-fill: red");

		if (validPlanDayListPlanVsPlan.contains("F"))
			planDateStatusFLabel.setStyle("-fx-text-fill: green");
		else
			planDateStatusFLabel.setStyle("-fx-text-fill: red");

		if (validPlanDayListPlanVsPlan.contains("Sa"))
			planDateStatusSaLabel.setStyle("-fx-text-fill: green");
		else
			planDateStatusSaLabel.setStyle("-fx-text-fill: red");
	}

	private Callback<DatePicker, DateCell> getFutureDatesOnlyFactory(boolean includeToday) {
		return datePicker -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);

				LocalDate today = LocalDate.now();
				LocalDate minDate = includeToday ? today : today.plusDays(1);

				// Disable past dates
				if (item.isBefore(minDate)) {
					setDisable(true);
					setStyle("-fx-background-color: #dddddd;"); // Grey out
				}
			}
		};
	}
}