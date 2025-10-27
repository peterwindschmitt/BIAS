package com.bl.bias.app;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadS3CompareScheduleFiles;
import com.bl.bias.tools.ConvertDateTime;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASS3CompareSchedulePageController 
{
	private static String message = "";
	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;

	private static BooleanBinding disableExecuteButton;
	private static BooleanBinding coreDayCountBP;
	private static SimpleIntegerProperty coreDayCountSelectedIP;
	private static SimpleBooleanProperty startDateSelectedBP;
	private static SimpleBooleanProperty endDateSelectedBP;

	private static LocalDate startDate;
	private static LocalDate endDate;

	private static ObservableList<String> validCoreDayList = FXCollections.observableList(new ArrayList<String>());

	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label coreDateLabel;
	@FXML private Label coreDateStatusMLabel;
	@FXML private Label coreDateStatusTLabel;
	@FXML private Label coreDateStatusWLabel;
	@FXML private Label coreDateStatusRLabel;
	@FXML private Label coreDateStatusFLabel;
	@FXML private Label coreDateStatusSaLabel;
	@FXML private Label coreDateStatusSuLabel;
	@FXML private Label step1TextLabel;
	@FXML private Label step2TextLabel;
	@FXML private Label step3TextLabel;
	@FXML private Label step1Label;
	@FXML private Label step2Label;
	@FXML private Label step3Label;

	@FXML private DatePicker startDatePicker;
	@FXML private DatePicker endDatePicker;

	@FXML private TextArea textArea;

	@FXML private ProgressBar progressBar;

	public BIASS3CompareSchedulePageController()
	{
		prefs = Preferences.userRoot().node("BIAS");	
	}

	@FXML private void initialize()
	{
		startDateSelectedBP = new SimpleBooleanProperty();
		endDateSelectedBP = new SimpleBooleanProperty();

		startDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				if (startDatePicker.getValue() != null)
				{
					startDate = startDatePicker.getValue();
					startDateSelectedBP.set(true);
				}
				else
				{
					startDateSelectedBP.set(false);
				}
			}
		});

		endDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				if (endDatePicker.getValue() != null)
				{
					endDate = endDatePicker.getValue();
					endDateSelectedBP.set(true);
				}
				else
				{
					endDateSelectedBP.set(false);
				}
			}
		});

		startDateSelectedBP.addListener((observable, oldValue, newValue) -> {
			if (newValue.equals(true)) 
			{
				endDatePicker.setDisable(false);
				step2TextLabel.setDisable(false);
				step2Label.setDisable(false);
			}
			else
			{
				endDatePicker.setDisable(true);
				endDatePicker.setValue(null);
				step2TextLabel.setDisable(true);
				step2Label.setDisable(true);
			}
		});

		validCoreDayList.addListener(new ListChangeListener<String>() {
			@Override
			//onChanged method
			public void onChanged(ListChangeListener.Change c) {
				coreDayCountSelectedIP.set(validCoreDayList.size());

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

		coreDayCountSelectedIP = new SimpleIntegerProperty();
		coreDayCountBP = coreDayCountSelectedIP.isEqualTo(7);

		// disable execute button if there isn't a start and end date and if all DOW are not present in core assignments
		disableExecuteButton = startDateSelectedBP.not().or(endDateSelectedBP.not()).or(coreDayCountBP.not());          
		executeButton.disableProperty().bind(disableExecuteButton);

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

		coreDayCountSelectedIP.set(validCoreDayList.size());
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

				message = "Starting S3 Core Schedule Analysis at "+ConvertDateTime.getTimeStamp()+"\n";
				displayMessage(message);

				// Unbind buttons
				executeButton.disableProperty().unbind();

				// Disable controls
				step1Label.setDisable(true);
				step1TextLabel.setDisable(true);
				step2Label.setDisable(true);
				step2TextLabel.setDisable(true);
				startDatePicker.setDisable(true);
				endDatePicker.setDisable(true);
				coreDateLabel.setDisable(true);
				coreDateStatusMLabel.setDisable(true);
				coreDateStatusTLabel.setDisable(true);
				coreDateStatusWLabel.setDisable(true);
				coreDateStatusRLabel.setDisable(true);
				coreDateStatusFLabel.setDisable(true);
				coreDateStatusSaLabel.setDisable(true);
				coreDateStatusSuLabel.setDisable(true);
				executeButton.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				// Rebind buttons
				executeButton.disableProperty().bind(disableExecuteButton);

				// Enable controls
				step1Label.setDisable(false);
				step1TextLabel.setDisable(false);
				step2Label.setDisable(false);
				step2TextLabel.setDisable(false);
				startDatePicker.setDisable(false);
				endDatePicker.setDisable(false);
				coreDateLabel.setDisable(false);
				coreDateStatusMLabel.setDisable(false);
				coreDateStatusTLabel.setDisable(false);
				coreDateStatusWLabel.setDisable(false);
				coreDateStatusRLabel.setDisable(false);
				coreDateStatusFLabel.setDisable(false);
				coreDateStatusSaLabel.setDisable(false);
				coreDateStatusSuLabel.setDisable(false);
				startDatePicker.setValue(null);
				endDatePicker.setValue(null);

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
				message = "Starting S3 Core Schedule Analysis at "+ConvertDateTime.getTimeStamp()+"\n";
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("s3_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				// Unbind buttons
				executeButton.disableProperty().unbind();

				// Disable controls
				step1Label.setDisable(true);
				step1TextLabel.setDisable(true);
				step2Label.setDisable(true);
				step2TextLabel.setDisable(true);
				startDatePicker.setDisable(true);
				endDatePicker.setDisable(true);
				coreDateLabel.setDisable(true);
				coreDateStatusMLabel.setDisable(true);
				coreDateStatusTLabel.setDisable(true);
				coreDateStatusWLabel.setDisable(true);
				coreDateStatusRLabel.setDisable(true);
				coreDateStatusFLabel.setDisable(true);
				coreDateStatusSaLabel.setDisable(true);
				coreDateStatusSuLabel.setDisable(true);
				executeButton.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				// Rebind buttons
				executeButton.disableProperty().bind(disableExecuteButton);

				// Enable controls
				step1Label.setDisable(false);
				step1TextLabel.setDisable(false);
				step2Label.setDisable(false);
				step2TextLabel.setDisable(false);
				startDatePicker.setDisable(false);
				endDatePicker.setDisable(false);
				coreDateLabel.setDisable(false);
				coreDateStatusMLabel.setDisable(false);
				coreDateStatusTLabel.setDisable(false);
				coreDateStatusWLabel.setDisable(false);
				coreDateStatusRLabel.setDisable(false);
				coreDateStatusFLabel.setDisable(false);
				coreDateStatusSaLabel.setDisable(false);
				coreDateStatusSuLabel.setDisable(false);
				startDatePicker.setValue(null);
				endDatePicker.setValue(null);

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

		// Rebind buttons
		executeButton.disableProperty().bind(disableExecuteButton);

		// Enable controls
		step1Label.setDisable(false);
		step1TextLabel.setDisable(false);
		step2Label.setDisable(false);
		step2TextLabel.setDisable(false);
		startDatePicker.setDisable(false);
		endDatePicker.setDisable(false);
		coreDateLabel.setDisable(false);
		coreDateStatusMLabel.setDisable(false);
		coreDateStatusTLabel.setDisable(false);
		coreDateStatusWLabel.setDisable(false);
		coreDateStatusRLabel.setDisable(false);
		coreDateStatusFLabel.setDisable(false);
		coreDateStatusSaLabel.setDisable(false);
		coreDateStatusSuLabel.setDisable(false);
		startDatePicker.setValue(null);
		endDatePicker.setValue(null);

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
		// Check date range is ordered properly
		if (startDate.isAfter(endDate))
		{
			continueAnalysis = false;
			displayMessage("\nSelected Start Date is after End Date.");
			displayMessage("\n\n*** PROCESSING NOT COMPLETE!!! ***");
		}

		if (continueAnalysis)
		{
			// Read all objects that are required for the modified OTP analysis
			ReadS3CompareScheduleFiles readData = new ReadS3CompareScheduleFiles(BIASS3CompareScheduleConfigPageController.getUri1(), 
					BIASS3CompareScheduleConfigPageController.getHost1(), BIASS3CompareScheduleConfigPageController.getKey1());
			message = readData.getResultsMessage();
			displayMessage(message);

			setProgressIndicator(0.20);
			/*
				if (ReadModifiedOtpFiles.getEnabledTrainsFromTrainFile().size() > 0)
				{
					// Analyze trains' modified OTP
					ModifiedOtpAnalysis analyze = new ModifiedOtpAnalysis();
					message = analyze.getResultsMessage();
					displayMessage(message);

					setProgressIndicator(0.80);

					// Write results to spreadsheet
					WriteModifiedOtpFiles2 writeFiles = new WriteModifiedOtpFiles2(textArea.getText().toString(), fileAsString);
					message = WriteModifiedOtpFiles2.getResultsMessage2();
					displayMessage(message);

					if (!WriteModifiedOtpFiles2.getErrorFound())
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
					displayMessage("\nNo qualifying run-time trains were found to compare schedule points against.");
					displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
				}
			*/
		}

		// Now reset for next case
		// Rebind buttons
		executeButton.disableProperty().bind(disableExecuteButton);

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
		textArea.appendText(message);
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
}