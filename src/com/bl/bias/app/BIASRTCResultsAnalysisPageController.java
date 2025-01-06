package com.bl.bias.app;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadRTCResultsAnalysisGroupFiles;
import com.bl.bias.read.ReadRTCResultsAnalysisTypeFiles;
import com.bl.bias.write.WriteExtractedFiles5;
import com.bl.bias.analyze.RTCResultsAnalysisCleanFiles;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
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
	private static File saveDirectoryLocation;
	private static String message = "";

	private static Stage extractConfigWindow;

	private static Preferences prefs;

	private static BooleanBinding filesToExecuteForBB;
	private static BooleanBinding disableExecuteButton;
	private static BooleanBinding disableAssignOptionsButton;
	private static SimpleBooleanProperty executionInProgress = new SimpleBooleanProperty();
	private static SimpleBooleanProperty validCustomCategory1FromConfigExists = new SimpleBooleanProperty();
	private static SimpleBooleanProperty validCustomCategory2FromConfigExists= new SimpleBooleanProperty();

	private static Boolean doNotUseCustomAssignments = true;
	private static Boolean useCustomAssignmentsFromModuleConfig = false;
	private static Boolean useCustomAssignmentsFromOptions = false;

	private static IntegerProperty validFilesAvailable;

	private static String customAssignmentsFromConfigCategory1Name;
	private static String customAssignmentsFromConfigCategory1Types;
	private static String customAssignmentsFromConfigCategory2Name;
	private static String customAssignmentsFromConfigCategory2Types;

	private static SimpleStringProperty typesAffectedConfig = new SimpleStringProperty();

	@FXML private Button assignOptionsButton;
	@FXML private Button selectFolderButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private RadioButton doNotUseCustomAssignmentsRadioButton;
	@FXML private RadioButton useModuleConfigCustomAssignmentsRadioButton;
	@FXML private RadioButton useOptionsCustomAssignmentsRadioButton;

	@FXML private ProgressBar progressBar;

	@FXML private Label selectProjectFolderLabel;
	@FXML private Label folderNameLabel;
	@FXML private Label assignOptionsLabel;
	@FXML private Label messageLabel;
	@FXML private Label typesAffected1Label;
	@FXML private Label showTheTypes1Label;

	@FXML private TextArea textArea;

	public BIASRTCResultsAnalysisPageController()
	{
		prefs = Preferences.userRoot().node("BIAS");

		validCustomCategory1FromConfigExists.bind(BIASRTCResultsAnalysisConfigPageController.getValidCustomAssignment1Exists());
		validCustomCategory2FromConfigExists.bind(BIASRTCResultsAnalysisConfigPageController.getValidCustomAssignment2Exists());
	}

	@FXML private void initialize()
	{
		executionInProgress.setValue(false);

		typesAffectedConfig.addListener((observable, oldValue, newValue) -> {
			showTheTypes1Label.setText(newValue);
			if (newValue.equals("N/A"))
			{
				doNotUseCustomAssignments = true;
				useCustomAssignmentsFromModuleConfig = false;
				useCustomAssignmentsFromOptions = false;

				doNotUseCustomAssignmentsRadioButton.setSelected(true);
			}
		});

		changeMadeToCustomTypesInConfig();

		// Establish Boolean Bindings
		validFilesAvailable = new SimpleIntegerProperty();
		filesToExecuteForBB = validFilesAvailable.greaterThan(0);
		disableAssignOptionsButton = filesToExecuteForBB.not();
		assignOptionsButton.disableProperty().bind(disableAssignOptionsButton);
		assignOptionsLabel.disableProperty().bind(disableAssignOptionsButton);
		doNotUseCustomAssignmentsRadioButton.disableProperty().bind(disableAssignOptionsButton);

		if ((validCustomCategory1FromConfigExists.get()) || (validCustomCategory2FromConfigExists.get()))
		{
			useModuleConfigCustomAssignmentsRadioButton.disableProperty().bind(disableAssignOptionsButton);
			typesAffected1Label.disableProperty().bind(disableAssignOptionsButton);
			showTheTypes1Label.disableProperty().bind(disableAssignOptionsButton);

		}
		else 
		{
			useModuleConfigCustomAssignmentsRadioButton.setDisable(true);
			typesAffected1Label.setDisable(true);
			showTheTypes1Label.setDisable(true);
		}

		useOptionsCustomAssignmentsRadioButton.disableProperty().bind(BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments().not());
		useOptionsCustomAssignmentsRadioButton.disableProperty().addListener((obs, wasDisabled, isNowDisabled) -> {
			if ((wasDisabled == false) && (isNowDisabled == true) && (executionInProgress.get() == false) && (useOptionsCustomAssignmentsRadioButton.isSelected()))
			{
				doNotUseCustomAssignmentsRadioButton.setSelected(true);
			}
		});

		useModuleConfigCustomAssignmentsRadioButton.disableProperty().bind(disableAssignOptionsButton.or(validCustomCategory1FromConfigExists.not().and(validCustomCategory2FromConfigExists.not())));
		typesAffected1Label.disableProperty().bind(disableAssignOptionsButton.or(validCustomCategory1FromConfigExists.not().and(validCustomCategory2FromConfigExists.not())));
		showTheTypes1Label.disableProperty().bind(disableAssignOptionsButton.or(validCustomCategory1FromConfigExists.not().and(validCustomCategory2FromConfigExists.not())));

		disableExecuteButton = filesToExecuteForBB.not();  // disable execute button if there are no eligible files        
		executeButton.disableProperty().bind(disableExecuteButton);
	}

	@FXML private void handleDoNotUseCustomAssignmentsRadioButton(ActionEvent event)
	{
		doNotUseCustomAssignments = true;
		useCustomAssignmentsFromModuleConfig = false;
		useCustomAssignmentsFromOptions = false;
	}

	@FXML private void handleUseModuleConfigCustomAssignmentsRadioButton(ActionEvent event)
	{
		doNotUseCustomAssignments = false;
		useCustomAssignmentsFromModuleConfig = true;
		useCustomAssignmentsFromOptions = false;
	}

	@FXML private void handleUseOptionsCustomAssignmentsRadioButton(ActionEvent event)
	{
		doNotUseCustomAssignments = false;
		useCustomAssignmentsFromModuleConfig = false;
		useCustomAssignmentsFromOptions = true;
	}

	@FXML private void handleExecuteButton(ActionEvent event) 
	{
		executionInProgress.setValue(true);
		validFilesAvailable.set(0);
		selectProjectFolderLabel.disableProperty().unbind();
		selectProjectFolderLabel.setDisable(true);
		assignOptionsLabel.disableProperty().unbind();
		assignOptionsLabel.setDisable(true);
		assignOptionsButton.disableProperty().unbind();
		assignOptionsButton.setDisable(true);
		selectFolderButton.setDisable(true);

		// Unbind radio buttons
		doNotUseCustomAssignmentsRadioButton.disableProperty().unbind();
		doNotUseCustomAssignmentsRadioButton.selectedProperty().unbind();
		doNotUseCustomAssignmentsRadioButton.setDisable(true);

		// Below lines are forcing early selection of do not use custom assignments radio button
		useOptionsCustomAssignmentsRadioButton.disableProperty().unbind();
		useOptionsCustomAssignmentsRadioButton.setDisable(true);

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
			File file = fileChooser.showSaveDialog(stageForFolderChooser);

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
				
				// Rebind radio buttons
				doNotUseCustomAssignmentsRadioButton.disableProperty().bind(disableAssignOptionsButton);
				useOptionsCustomAssignmentsRadioButton.disableProperty().bind(BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments().not());

				doNotUseCustomAssignments = true;
				useCustomAssignmentsFromModuleConfig = false;
				useCustomAssignmentsFromOptions = false;
				doNotUseCustomAssignmentsRadioButton.setSelected(true);

				executeButton.setVisible(true);
				resetButton.setVisible(false);
				progressBar.setVisible(false);
				selectFolderButton.setDisable(false);
				selectProjectFolderLabel.setDisable(false);
				folderNameLabel.setText("");
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			
			// Check if previous location is available
			if ((prefs.get("ra_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("ra_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
				{
					directoryChooser.setInitialDirectory(path.toFile());
				}
			}
			
			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) assignOptionsButton.getScene().getWindow();
			saveDirectoryLocation = directoryChooser.showDialog(stageForFolderChooser);
			
			if (saveDirectoryLocation != null) 
			{
				try 
				{
					if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
						prefs.put("ra_lastDirectorySavedTo", saveDirectoryLocation.toString());
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
				
				// Rebind radio buttons
				doNotUseCustomAssignmentsRadioButton.disableProperty().bind(disableAssignOptionsButton);
				useOptionsCustomAssignmentsRadioButton.disableProperty().bind(BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments().not());

				doNotUseCustomAssignments = true;
				useCustomAssignmentsFromModuleConfig = false;
				useCustomAssignmentsFromOptions = false;
				doNotUseCustomAssignmentsRadioButton.setSelected(true);

				executeButton.setVisible(true);
				resetButton.setVisible(false);
				progressBar.setVisible(false);
				selectFolderButton.setDisable(false);
				selectProjectFolderLabel.setDisable(false);
				folderNameLabel.setText("");
			}
		}
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		files.clear();
		ReadRTCResultsAnalysisGroupFiles.clearGroupFiles();
		ReadRTCResultsAnalysisTypeFiles.clearTypeFiles();
		BIASRTCResultsAnalysisOptionsWindowController.setGetCustomAssignmentsToFalse();

		resetMessage();

		validFilesAvailable.set(0);

		executeButton.setVisible(true);
		resetButton.setVisible(false);
		progressBar.setVisible(false);
		selectFolderButton.setDisable(false);
		selectProjectFolderLabel.setDisable(false);
		folderNameLabel.setText("");

		// Rebind radio buttons
		doNotUseCustomAssignmentsRadioButton.disableProperty().bind(disableAssignOptionsButton);
		useOptionsCustomAssignmentsRadioButton.disableProperty().bind(BIASRTCResultsAnalysisOptionsWindowController.getCustomAssignments().not());

		doNotUseCustomAssignments = true;
		useCustomAssignmentsFromModuleConfig = false;
		useCustomAssignmentsFromOptions = false;
		doNotUseCustomAssignmentsRadioButton.setSelected(true);

		executionInProgress.setValue(false);
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
				message = "BIAS RTC Results Analysis Module - "+BIASLaunch.getSoftwareVersion();
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
		WriteExtractedFiles5 filesToWrite5 = new WriteExtractedFiles5(textArea.getText(), BIASRTCResultsAnalysisOptionsWindowController.getEntireNetwork(), BIASRTCResultsAnalysisOptionsWindowController.getSelectedLines(), BIASRTCResultsAnalysisOptionsWindowController.getTrainCount(),
				BIASRTCResultsAnalysisOptionsWindowController.getVelocity(), BIASRTCResultsAnalysisOptionsWindowController.getTrainMiles(), BIASRTCResultsAnalysisOptionsWindowController.getElapsedRunTime(), BIASRTCResultsAnalysisOptionsWindowController.getElapsedRunTimePerTrain(), BIASRTCResultsAnalysisOptionsWindowController.getIdealRunTime(), BIASRTCResultsAnalysisOptionsWindowController.getTrueDelay(), 
				BIASRTCResultsAnalysisOptionsWindowController.getDelayMinutesPer100TrainMiles(), BIASRTCResultsAnalysisOptionsWindowController.getDelayMinutesPerTrain(), BIASRTCResultsAnalysisOptionsWindowController.getOtp(), BIASRTCResultsAnalysisConfigPageController.getGenerateRawData(), BIASRTCResultsAnalysisConfigPageController.getSummaryResults(), 
				BIASRTCResultsAnalysisConfigPageController.getGenerateGraphs(), BIASRTCResultsAnalysisConfigPageController.getOutputAsString(), BIASRTCResultsAnalysisConfigPageController.getOutputAsSeconds(), BIASRTCResultsAnalysisConfigPageController.getOutputAsSerial());

		displayMessage(filesToWrite5.getResultsWriteMessage5());
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

		// Check input files formatting and ATC/PTC compliance
		Boolean formattedCorrectly = true;
		try
		{
			message = "\n";

			// Check that .TRAIN files shows all trains PTC-equipped/ATC-equipped if specified in module config file
			BIASValidateOptionsAndINIFileSchemeA.bIASCheckTrainFile(directory);
			if ((BIASValidateOptionsAndINIFileSchemeA.getTrainsEquippedCorrectly()) && ((BIASRTCResultsAnalysisConfigPageController.getCheckForPtcEquipped()) || (BIASRTCResultsAnalysisConfigPageController.getCheckForAtcEquipped()))) 
			{
				message += "Validated PTC/ATC compliance in .TRAIN file\n";
			}
			else if ((!BIASValidateOptionsAndINIFileSchemeA.getTrainsEquippedCorrectly()) && ((BIASRTCResultsAnalysisConfigPageController.getCheckForPtcEquipped()) || (BIASRTCResultsAnalysisConfigPageController.getCheckForAtcEquipped()))) 
			{
				message += "Invalid PTC/ATC compliance in .TRAIN file and/or invalid count of .TRAIN files\n";
				formattedCorrectly = false;
			}

			// Check that .OPTION file has time formatted as DD:HH:MM:SS, no CSV delimiters in .ROUTE file and ENGLISH input units
			BIASValidateOptionsAndINIFileSchemeA.bIASCheckOptionFiles(directory);
			if (BIASValidateOptionsAndINIFileSchemeA.getOptionsFilesFormattedCorrectly())
			{
				message += "Validated date/time format, output format and speed/distance units from .OPTION file\n";
			}
			else
			{
				message += "Invalid date/time format, output format, speed/distance units, invalid .OPTION file and/or invalid count of .OPTION files\n";
				formattedCorrectly = false;
			}

			// Check that not using alpha DOW in RTC.INI
			BIASValidateOptionsAndINIFileSchemeA.bIASCheckINIFile(directory);
			if (BIASValidateOptionsAndINIFileSchemeA.getINIFileFormattedCorrectly())
			{
				message += "Validated DOW output format in .INI file\n";
			}
			else
			{
				message += "Invalid DOW output format in .INI file and/or invalid count of .INI files\n";
				formattedCorrectly = false;
			}

			displayMessage(message);


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

	public static File getSaveDirectoryLocation()
	{		
		return saveDirectoryLocation;
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

	public static String getCustomAssignmentsCategory1Name()
	{
		String category1NameToReturn = "";
		if (useCustomAssignmentsFromModuleConfig)
		{
			category1NameToReturn = customAssignmentsFromConfigCategory1Name;
		}
		else if (useCustomAssignmentsFromOptions)
		{
			category1NameToReturn = BIASCustomAssignmentsWindowController.returnCustomCategory1();
		}
		return category1NameToReturn;
	}

	public static List<String> getCustomAssignmentsCategory1Types()
	{
		List <String> customAssignmentsCategory1TypesAsArrayList = new ArrayList<>();

		if (useCustomAssignmentsFromModuleConfig)
		{
			String[] customAssignmentsFromConfigCategory1TypesAsArray = customAssignmentsFromConfigCategory1Types.split(",");
			customAssignmentsCategory1TypesAsArrayList = Arrays.asList(customAssignmentsFromConfigCategory1TypesAsArray);
		}
		else if (useCustomAssignmentsFromOptions)
		{
			customAssignmentsCategory1TypesAsArrayList =  BIASCustomAssignmentsWindowController.returnCustomCategoryTypes1();
		}

		return customAssignmentsCategory1TypesAsArrayList;
	}

	public static String getCustomAssignmentsCategory2Name()
	{
		String category2NameToReturn = "";
		if (useCustomAssignmentsFromModuleConfig)
		{
			category2NameToReturn = customAssignmentsFromConfigCategory2Name;
		}
		else if (useCustomAssignmentsFromOptions)
		{
			category2NameToReturn = BIASCustomAssignmentsWindowController.returnCustomCategory2();
		}
		return category2NameToReturn;
	}

	public static List<String> getCustomAssignmentsCategory2Types()
	{
		List <String> customAssignmentsCategory2TypesAsArrayList = new ArrayList<>();

		if (useCustomAssignmentsFromModuleConfig)
		{
			String[] customAssignmentsFromConfigCategory2TypesAsArray = customAssignmentsFromConfigCategory2Types.split(",");
			customAssignmentsCategory2TypesAsArrayList = Arrays.asList(customAssignmentsFromConfigCategory2TypesAsArray);
		}
		else if (useCustomAssignmentsFromOptions)
		{
			customAssignmentsCategory2TypesAsArrayList =  BIASCustomAssignmentsWindowController.returnCustomCategoryTypes2();
		}

		return customAssignmentsCategory2TypesAsArrayList;
	}

	public static void changeMadeToCustomTypesInConfig()
	{
		customAssignmentsFromConfigCategory1Name = BIASRTCResultsAnalysisConfigPageController.getUserCategory1Name().getValue();
		customAssignmentsFromConfigCategory1Types = BIASRTCResultsAnalysisConfigPageController.getUserCategory1Types().getValue();
		customAssignmentsFromConfigCategory2Name = BIASRTCResultsAnalysisConfigPageController.getUserCategory2Name().getValue();
		customAssignmentsFromConfigCategory2Types = BIASRTCResultsAnalysisConfigPageController.getUserCategory2Types().getValue();

		// Check to see if there's any Custom Assignments in Config
		typesAffectedConfig.setValue("N/A");
		if (((!customAssignmentsFromConfigCategory1Name.trim().isBlank()) && (!customAssignmentsFromConfigCategory1Types.trim().isBlank()))  &&
				((customAssignmentsFromConfigCategory2Name.trim().isBlank()) || (customAssignmentsFromConfigCategory2Types.trim().isBlank())))
		{
			typesAffectedConfig.setValue(customAssignmentsFromConfigCategory1Name.trim() +": "+customAssignmentsFromConfigCategory1Types.trim()+"\n"); 
		}
		else if (((!customAssignmentsFromConfigCategory2Name.trim().isBlank()) && (!customAssignmentsFromConfigCategory2Types.trim().isBlank())) && 
				((customAssignmentsFromConfigCategory1Name.trim().isBlank()) || (customAssignmentsFromConfigCategory1Types.trim().isBlank())))
		{
			typesAffectedConfig.setValue(customAssignmentsFromConfigCategory2Name.trim() +": "+customAssignmentsFromConfigCategory2Types.trim()+"\n"); 
		}
		else if (((!customAssignmentsFromConfigCategory2Name.trim().isBlank()) && (!customAssignmentsFromConfigCategory2Types.trim().isBlank())) && 
				((!customAssignmentsFromConfigCategory1Name.trim().isBlank()) && (!customAssignmentsFromConfigCategory1Types.trim().isBlank())))
		{
			typesAffectedConfig.setValue(customAssignmentsFromConfigCategory1Name.trim() +": "+customAssignmentsFromConfigCategory1Types.trim()+"\n" + 
					customAssignmentsFromConfigCategory2Name.trim() +": "+customAssignmentsFromConfigCategory2Types.trim()+"\n"); 
		}
	}

	public static Boolean getUseCustomAssignmentsFromModuleConfig()
	{
		return useCustomAssignmentsFromModuleConfig;
	}

	public static Boolean getUseCustomAssignmentsFromOptions()
	{
		return useCustomAssignmentsFromOptions;
	}

	public static Boolean getDoNotUseCustomAssignments()
	{
		return doNotUseCustomAssignments;
	}
}