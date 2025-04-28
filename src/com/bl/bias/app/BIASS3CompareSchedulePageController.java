package com.bl.bias.app;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.prefs.Preferences;

import com.bl.bias.analyze.ModifiedOtpAnalysis;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadModifiedOtpFiles;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.write.WriteModifiedOtpFiles2;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
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
	private static String fileAsString;
	private static String fullyQualifiedPath;
	private static String message = "";
	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;

	private static ObservableList<String> validCoreDayList = FXCollections.observableList(new ArrayList<String>());
	
	@FXML private Button executeButton;
	@FXML private Button selectFileButton;
	@FXML private Button resetButton;

	@FXML private Label coreDateStatusMLabel;
	@FXML private Label coreDateStatusTLabel;
	@FXML private Label coreDateStatusWLabel;
	@FXML private Label coreDateStatusRLabel;
	@FXML private Label coreDateStatusFLabel;
	@FXML private Label coreDateStatusSaLabel;
	@FXML private Label coreDateStatusSuLabel;

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
		
		boolean mondayDateExists = prefs.get("s3_mondayCoreDate", null) != null;
		if ((mondayDateExists) && (!prefs.get("s3_mondayCoreDate", "").equals("")))
		{
			validCoreDayList.add("M");
		}
			
		boolean tuesdayDateExists = prefs.get("s3_tuesdayCoreDate", null) != null;
		if ((tuesdayDateExists) && (!prefs.get("s3_tuesdayCoreDate", "").equals("")))
		{
			validCoreDayList.add("T");
		}
		
		boolean wednesdayDateExists = prefs.get("s3_wednesdayCoreDate", null) != null;
		if ((wednesdayDateExists) && (!prefs.get("s3_wednesdayCoreDate", "").equals("")))
		{
			validCoreDayList.add("W");
		}
		
		boolean thursdayDateExists = prefs.get("s3_thursdayCoreDate", null) != null;
		if ((thursdayDateExists) && (!prefs.get("s3_thursdayCoreDate", "").equals("")))
		{
			validCoreDayList.add("R");
		}
		
		boolean fridayDateExists = prefs.get("s3_fridayCoreDate", null) != null;
		if ((fridayDateExists) && (!prefs.get("s3_fridayCoreDate", "").equals("")))
		{
			validCoreDayList.add("F");
		}		
		
		boolean saturdayDateExists = prefs.get("s3_saturdayCoreDate", null) != null;
		if ((saturdayDateExists) && (!prefs.get("s3_saturdayCoreDate", "").equals("")))
		{
			validCoreDayList.add("Sa");
		}
		
		boolean sundayDateExists = prefs.get("s3_sundayCoreDate", null) != null;
		if ((sundayDateExists) && (!prefs.get("s3_sundayCoreDate", "").equals("")))
		{
			validCoreDayList.add("Su");
		}
	}

	@FXML private void handleExecuteButton(ActionEvent event) 
	{
		System.out.println(validCoreDayList.toString());
		/* Get location to save file to if not using system time as file name

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

				message = "\nStarting S3 Core Schedule Analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				//step1Label.setDisable(true);
				//selectFileButton.setDisable(true);
				//selectProjectFileLabel.setDisable(true);
				//executeButton.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				executeButton.setDisable(true);
				executeButton.setVisible(true);
				resetButton.setVisible(false);
				selectFileButton.setDisable(false);
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
				message = "\nStarting Modified OTP Analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("mo_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				selectFileButton.setDisable(true);
				executeButton.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				executeButton.setDisable(true);
				executeButton.setVisible(true);
				resetButton.setVisible(false);
				selectFileButton.setDisable(false);
			}	
		}*/
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		resetMessage();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);

		executeButton.setVisible(true);
		resetButton.setVisible(false);
		selectFileButton.setDisable(false);
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

	private void runTask() throws InterruptedException, IOException
	{
		// Check date/time format, verbose .ROUTE file, output format and ENGLISH input units
		File optionFile = new File(fullyQualifiedPath);
		File optionFileFolder = new File(optionFile.getParent());
		BIASValidateOptionsAndINIFileSchemeA.bIASCheckOptionFiles(optionFileFolder);
		if (BIASValidateOptionsAndINIFileSchemeA.getOptionsFilesFormattedCorrectly())
		{
			message = "\nValidated date/time format, output format and speed/distance \nunits from .OPTION file\n";
		}
		else
		{
			message = "\nInvalid date/time format, output format, speed/distance \nunits, invalid .OPTION file and/or invalid count of .OPTION files\n";
			continueAnalysis = false;
		}
		displayMessage(message);

		if (continueAnalysis)
		{
			// Ensure that there is at least one valid entry from config
			if (BIASModifiedOtpConfigPageController.getSchedulePointEntries().split(",").length > 0) 
			{
				// Read all objects that are required for the modified OTP analysis
				ReadModifiedOtpFiles readData = new ReadModifiedOtpFiles(fullyQualifiedPath);
				message = readData.getResultsMessage();
				displayMessage(message);

				setProgressIndicator(0.20);

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
			}
			else
			{
				displayMessage("\nMust select at least one train, node and departure time to run analysis");
				displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
			}
		}
		else
		{
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
		}

		//  Now reset for next case
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