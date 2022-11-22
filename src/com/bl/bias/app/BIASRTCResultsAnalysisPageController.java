package com.bl.bias.app;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadRTCResultsAnalysisGroupFiles;
import com.bl.bias.read.ReadRTCResultsAnalysisTypeFiles;
import com.bl.bias.write.WriteExtractedFiles5;
import com.bl.bias.analyze.RTCResultsAnalysisCleanFiles;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BIASRTCResultsAnalysisPageController 
{
	private static ArrayList<File> files;
	private static File[] filesAsList;
	private static File directory;
	private static File lastDirectory;
	private static File saveFileLocation;
	private static String message = "";

	private static Stage extractConfigWindow;

	private static Preferences prefs;

	private static BooleanBinding filesToExecuteForBB;
	private static BooleanBinding disableExecuteButton;
	private static BooleanBinding disableAssignOptionsButton;

	private static IntegerProperty validFilesAvailable;

	@FXML private Button assignOptionsButton;
	@FXML private Button selectFolderButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private ProgressBar progressBar;

	@FXML private Label selectProjectFolderLabel;
	@FXML private Label folderNameLabel;
	@FXML private Label assignOptionsLabel;
	@FXML private Label messageLabel;

	@FXML private TextArea textArea;

	@FXML private void initialize()
	{
		validFilesAvailable = new SimpleIntegerProperty();
		filesToExecuteForBB = validFilesAvailable.greaterThan(0);

		disableAssignOptionsButton = filesToExecuteForBB.not();
		assignOptionsButton.disableProperty().bind(disableAssignOptionsButton);
		assignOptionsLabel.disableProperty().bind(disableAssignOptionsButton);

		disableExecuteButton = filesToExecuteForBB.not();  // disable execute button if there are no eligible files        
		executeButton.disableProperty().bind(disableExecuteButton);

		prefs = Preferences.userRoot().node("BIAS");
	}

	@FXML private void handleExecuteButton(ActionEvent event) 
	{
		validFilesAvailable.set(0);
		selectProjectFolderLabel.disableProperty().unbind();
		selectProjectFolderLabel.setDisable(true);
		assignOptionsLabel.disableProperty().unbind();
		assignOptionsLabel.setDisable(true);
		assignOptionsButton.disableProperty().unbind();
		assignOptionsButton.setDisable(true);
		selectFolderButton.setDisable(true);
		progressBar.setVisible(true);

		// Get location to save file to if not using system time as file name
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("ra_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("ra_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
				{
					fileChooser.setInitialDirectory(path.toFile());
				}
			}

			Stage stageForFolderChooser = (Stage) assignOptionsButton.getScene().getWindow();
			File file = null;

			file = fileChooser.showSaveDialog(stageForFolderChooser);

			if (file != null) 
			{
				try 
				{
					saveFileLocation = file;
					if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
						prefs.put("ra_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				files.clear();
				ReadRTCResultsAnalysisGroupFiles.clearGroupFiles();
				ReadRTCResultsAnalysisTypeFiles.clearTypeFiles();

				resetMessage();

				validFilesAvailable.set(0);

				executeButton.setVisible(true);
				resetButton.setVisible(false);
				progressBar.setVisible(false);
				selectFolderButton.setDisable(false);
				selectProjectFolderLabel.setDisable(false);
				folderNameLabel.setText("");
			}	
		}
		else
			startTask();
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		files.clear();
		ReadRTCResultsAnalysisGroupFiles.clearGroupFiles();
		ReadRTCResultsAnalysisTypeFiles.clearTypeFiles();

		resetMessage();

		validFilesAvailable.set(0);

		executeButton.setVisible(true);
		resetButton.setVisible(false);
		progressBar.setVisible(false);
		selectFolderButton.setDisable(false);
		selectProjectFolderLabel.setDisable(false);
		folderNameLabel.setText("");
	}

	@FXML private void handleAssignOptionsButton(ActionEvent event) throws IOException 
	{
		chooseExtractOptions();
	}

	@FXML private void handleSelectFolderButton(ActionEvent event) 
	{
		chooseFolder();
	}

	private void setProgressIndicator(double value)
	{
		progressBar.setProgress(value);
	}

	private void chooseFolder()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();

		// See if last location is stored
		if ((prefs.get("ra_lastDirectoryReadFromExtract", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("ra_lastDirectoryReadFromExtract", null));
			if ((path.toFile().exists()) && (path !=null))
				directoryChooser.setInitialDirectory(path.toFile());
		}

		directoryChooser.setTitle("Select Folder");

		Stage stageForFolderChooser = (Stage) assignOptionsButton.getScene().getWindow();

		directory = directoryChooser.showDialog(stageForFolderChooser);
		if (directory != null)
		{
			// Clear out previous parse options here
			BIASRTCResultsAnalysisOptionsWindowController.resetParametersForNewFile();

			// Determine number of files to review
			filesAsList = directory.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File directory, String name) {
					return name.toLowerCase().endsWith(".summary");
				}
			});

			files = new ArrayList<File>();
			for (int i = 0; i < filesAsList.length; i++) 
			{
				files.add(filesAsList[i]);
			}

			folderNameLabel.setText(directory.toString()+" contains "+String.valueOf(files.size())+" .SUMMARY files to review");
			if (files.size() == 0)
			{
				files = null;
				validFilesAvailable.set(0);
				resetMessage();
				disableExecuteButton = filesToExecuteForBB.not();
				disableAssignOptionsButton = filesToExecuteForBB.not();

				assignOptionsButton.disableProperty().unbind();
				assignOptionsButton.setDisable(true);
				assignOptionsLabel.disableProperty().unbind();
				assignOptionsLabel.setDisable(true);
			}
			else
			{
				validFilesAvailable.set(files.size());
				clearMessage();
				message = "BIASLaunch RTC Results Analysis Module - "+BIASLaunch.getSoftwareVersion();
				message += "\n\nSet to review "+String.valueOf(files.size())+" .SUMMARY files from "+directory.toString()+"\n";
				displayMessage(message);
				lastDirectory = directory;
				BIASCustomAssignmentsWindowController.resetCustomAssignments();

				assignOptionsButton.disableProperty().unbind();
				assignOptionsButton.setDisable(false);
				assignOptionsLabel.disableProperty().unbind();
				assignOptionsLabel.setDisable(false);
			}

			// Store path for subsequent runs
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ra_lastDirectoryReadFromExtract", directory.toString());
		}
		else
		{
			directory = lastDirectory;
		}
	}

	private void chooseExtractOptions() throws IOException
	{
		Parent root2 = FXMLLoader.load(getClass().getResource("BIASRTCResultsAnalysisOptionsWindow.fxml"));
		Scene secondScene = new Scene(root2, 600, 625);
		extractConfigWindow = new Stage();
		extractConfigWindow.setResizable(false);
		extractConfigWindow.setTitle("RTC Results Analysis Options");
		extractConfigWindow.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
		extractConfigWindow.setScene(secondScene);
		extractConfigWindow.initModality(Modality.APPLICATION_MODAL);
		extractConfigWindow.show();
		extractConfigWindow.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) 
			{
				BIASRTCResultsAnalysisOptionsWindowController.revertToLastParseSettings();
				extractConfigWindow.close();
			}
		});
	}

	public static int getEligibleFileCount()
	{
		return files.size();
	}

	public static ArrayList<File> getFiles()
	{
		return files;
	}

	static void closeTheExtractConfigWindow()
	{
		extractConfigWindow.close();
	}

	private void extractByTrainType()
	{
		ReadRTCResultsAnalysisTypeFiles extractByType = new ReadRTCResultsAnalysisTypeFiles(BIASRTCResultsAnalysisOptionsWindowController.getEntireNetwork(), BIASRTCResultsAnalysisOptionsWindowController.getSelectedLines(), BIASRTCResultsAnalysisOptionsWindowController.getSelectedSubdivisions(), BIASRTCResultsAnalysisOptionsWindowController.getListOfLines(), BIASRTCResultsAnalysisOptionsWindowController.getListOfSubdivisions(), files);
		displayMessage(extractByType.getResultsMessage());
	}

	private void extractByTrainGroup()
	{
		ReadRTCResultsAnalysisGroupFiles extractByGroup = new ReadRTCResultsAnalysisGroupFiles(BIASRTCResultsAnalysisOptionsWindowController.getEntireNetwork(), BIASRTCResultsAnalysisOptionsWindowController.getSelectedLines(), BIASRTCResultsAnalysisOptionsWindowController.getSelectedSubdivisions(), BIASRTCResultsAnalysisOptionsWindowController.getListOfLines(), BIASRTCResultsAnalysisOptionsWindowController.getListOfSubdivisions(), files);
		displayMessage(extractByGroup.getResultsMessage());
	}

	private void writeFiles() 
	{
		WriteExtractedFiles5 filesToWrite7 = new WriteExtractedFiles5(directory, textArea.getText(), BIASRTCResultsAnalysisOptionsWindowController.getEntireNetwork(), BIASRTCResultsAnalysisOptionsWindowController.getSelectedLines(), BIASRTCResultsAnalysisOptionsWindowController.getTrainCount(),
				BIASRTCResultsAnalysisOptionsWindowController.getVelocity(), BIASRTCResultsAnalysisOptionsWindowController.getTrainMiles(), BIASRTCResultsAnalysisOptionsWindowController.getElapsedRunTime(), BIASRTCResultsAnalysisOptionsWindowController.getElapsedRunTimePerTrain(), BIASRTCResultsAnalysisOptionsWindowController.getIdealRunTime(), BIASRTCResultsAnalysisOptionsWindowController.getTrueDelay(), 
				BIASRTCResultsAnalysisOptionsWindowController.getDelayMinutesPer100TrainMiles(), BIASRTCResultsAnalysisOptionsWindowController.getDelayMinutesPerTrain(), BIASRTCResultsAnalysisOptionsWindowController.getOtp(), BIASRTCResultsAnalysisConfigPageController.getGenerateRawData(), BIASRTCResultsAnalysisConfigPageController.getSummaryResults(), 
				BIASRTCResultsAnalysisConfigPageController.getGenerateGraphs(), BIASRTCResultsAnalysisConfigPageController.getOutputAsString(), BIASRTCResultsAnalysisConfigPageController.getOutputAsSeconds(), BIASRTCResultsAnalysisConfigPageController.getOutputAsSerial());

		displayMessage(filesToWrite7.getResultsWriteMessage7());
	}

	private void startTask()
	{

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

	private void runTask()
	{
		// Clean files -- call from here
		RTCResultsAnalysisCleanFiles cleanedFiles = new RTCResultsAnalysisCleanFiles(BIASRTCResultsAnalysisConfigPageController.getMoveFailedFiles(), BIASRTCResultsAnalysisConfigPageController.getPrependFailedFiles(), BIASRTCResultsAnalysisConfigPageController.getPurgeFailedFiles(), files);

		message = cleanedFiles.getResultsMessage();
		displayMessage(message);

		files.clear();
		files.addAll(cleanedFiles.getfilesToKeepForParsing());

		setProgressIndicator(0.10);

		// Check input files formatting
		Boolean formattedCorrectly = true;
		try
		{
			// Check that .OPTION file has time formatted as DD:HH:MM:SS, no CSV delimiters in .ROUTE file and ENGLISH input units
			BIASValidateOptionsAndINIFile.bIASCheckOptionFiles(directory);
			if (BIASValidateOptionsAndINIFile.getOptionsFilesFormattedCorrectly())
			{
				message = "\nValidated date/time format, output format and speed/distance units from .OPTION file\n";
				displayMessage(message);
			}
			else
			{
				message = "\nInvalid date/time format, output format, speed/distance units, invalid .OPTION file and/or invalid count of .OPTION files\n";
				displayMessage(message);
				formattedCorrectly = false;
			}

			// Check that not using alpha DOW in RTC.INI
			BIASValidateOptionsAndINIFile.bIASCheckINIFile(directory);
			if (BIASValidateOptionsAndINIFile.getINIFileFormattedCorrectly())
			{
				message = "Validated DOW output format in .INI file\n";
				displayMessage(message);
			}
			else
			{
				message = "Invalid DOW output format in .INI file and/or invalid count of .INI files\n";
				displayMessage(message);
				formattedCorrectly = false;
			}

			if ((getEligibleFileCount() > 0) && (formattedCorrectly))
			{
				// Modify size of files ArrayList if limiting results to first x files
				if (BIASRTCResultsAnalysisOptionsWindowController.getConsiderFirstXFiles())
				{
					// For each file
					for (int i = files.size(); i > 0; i--)     
					{
						if (i > BIASRTCResultsAnalysisOptionsWindowController.getFilesToConsiderCount())
						{
							files.remove(files.size() - i);
						}
					}
				}

				// Perform parse
				if (BIASRTCResultsAnalysisOptionsWindowController.getTrainType())
				{
					extractByTrainType();
					if (BIASRTCResultsAnalysisOptionsWindowController.getTrainGroup() == false)
					{
						setProgressIndicator(0.50);
					}
					else
					{
						setProgressIndicator(0.60);
					}
				}

				if (BIASRTCResultsAnalysisOptionsWindowController.getTrainGroup())
				{
					extractByTrainGroup();
					if (BIASRTCResultsAnalysisOptionsWindowController.getTrainType() == false)
					{
						setProgressIndicator(0.60);
					}
					else
					{
						setProgressIndicator(0.70);
					}
				}

				writeFiles();
				if (!WriteExtractedFiles5.getErrorFound())
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

		catch(Exception e)
		{
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
			ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
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

	public static File getSaveFileLocation()
	{
		if (!saveFileLocation.toString().toLowerCase().endsWith(".xlsx"))
		{
			String saveFileLocationAsString = saveFileLocation.toString();
			saveFileLocationAsString += ".xlsx";
			saveFileLocation = new File(saveFileLocationAsString);
		}

		return saveFileLocation;
	}
}