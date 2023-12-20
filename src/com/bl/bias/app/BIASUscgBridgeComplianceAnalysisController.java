package com.bl.bias.app;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.UnaryOperator;
import java.util.prefs.Preferences;

import com.bl.bias.analyze.BridgeComplianceAnalysis;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadExcelFileForBridgeCompliance;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.write.WriteBridgeComplianceFiles3;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASUscgBridgeComplianceAnalysisController 
{
	private static String fullyQualifiedPath;

	private static String message = "";
	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;
	
	private static String bridge;

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;

	private static ObservableList<String> columnValues =  FXCollections.observableArrayList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
	private static ObservableList<String> bridgeValues =  FXCollections.observableArrayList("Jupiter Bridge", "New River Bridge", "St Lucie River Bridge");
	
	private static final Integer maxRows = 999;
	private static Integer firstRowOfClosures;
	private static Integer lastRowOfClosures;

	private static SpinnerValueFactory<Integer> firstRowFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxRows, 1);
	private static SpinnerValueFactory<Integer> lastRowFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxRows, maxRows);

	private static String dayColumn;
	private static String lowerColumn;
	private static String raiseColumn;
	private static String tenderColumn;
	private static String dateColumn;
	private static String closingNumberColumn;
	private static String trainTypeColumn;
	private static String notesColumn;

	@FXML private Button selectFileButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label selectProjectFileLabel;
	@FXML private Label fileNameLabel;
	@FXML private Label oneLabel;
	@FXML private Label twoLabel;
	@FXML private Label selectBridgeLabel;
	@FXML private Label threeLabel;
	@FXML private Label firstRowOfBridgeClosuresLabel;
	@FXML private Label dayColumnLabel;
	@FXML private Label lowerColumnLabel;
	@FXML private Label raiseColumnLabel;
	@FXML private Label tenderColumnLabel;
	@FXML private Label dateColumnLabel;
	@FXML private Label closingNumberColumnLabel;
	@FXML private Label trainTypeColumnLabel;
	@FXML private Label notesColumnLabel;
	@FXML private Label lastRowOfBridgeClosuresLabel;
	@FXML private Label selectDataFieldsLabel;

	@FXML private TextArea textArea;

	@FXML private Spinner<Integer> firstRowOfBridgeClosuresSpinner;
	@FXML private Spinner<Integer> lastRowOfBridgeClosuresSpinner;
	@FXML private ComboBox<String> bridgeComboBox;
	@FXML private ComboBox<String> dayColumnComboBox;
	@FXML private ComboBox<String> lowerColumnComboBox;
	@FXML private ComboBox<String> tenderColumnComboBox;
	@FXML private ComboBox<String> raiseColumnComboBox;
	@FXML private ComboBox<String> dateColumnComboBox;
	@FXML private ComboBox<String> closingNumberColumnComboBox;
	@FXML private ComboBox<String> trainTypeColumnComboBox;
	@FXML private ComboBox<String> notesColumnComboBox;

	@FXML private ProgressBar progressBar;

	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");	

		bridgeComboBox.getItems().addAll(bridgeValues);
		dayColumnComboBox.getItems().addAll(columnValues);
		lowerColumnComboBox.getItems().addAll(columnValues);
		tenderColumnComboBox.getItems().addAll(columnValues);
		raiseColumnComboBox.getItems().addAll(columnValues);
		dateColumnComboBox.getItems().addAll(columnValues);
		closingNumberColumnComboBox.getItems().addAll(columnValues);
		trainTypeColumnComboBox.getItems().addAll(columnValues);
		notesColumnComboBox.getItems().addAll(columnValues);
		
		firstRowOfBridgeClosuresSpinner.getEditor().setTextFormatter(new TextFormatter<String>(integerFilter));
		firstRowOfBridgeClosuresSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable,//
					Integer oldValue, Integer newValue) {
				if (newValue == null)
					firstRowOfBridgeClosuresSpinner.getValueFactory().setValue(oldValue);
				else
				{
					if (newValue > maxRows)
						firstRowOfClosures = maxRows;
					else
						firstRowOfClosures = newValue;
				}
			}
		});

		lastRowOfBridgeClosuresSpinner.getEditor().setTextFormatter(new TextFormatter<String>(integerFilter));
		lastRowOfBridgeClosuresSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable,//
					Integer oldValue, Integer newValue) {
				if (newValue == null) 
					lastRowOfBridgeClosuresSpinner.getValueFactory().setValue(oldValue);
				else
				{
					if (newValue > maxRows)
						lastRowOfClosures = maxRows;
					else
						lastRowOfClosures = newValue;
				}
			}
		});

		bridge = bridgeValues.get(0);
		bridgeComboBox.setValue(bridge);
		dayColumnComboBox.setValue("A");
		lowerColumnComboBox.setValue("B");
		raiseColumnComboBox.setValue("C");
		tenderColumnComboBox.setValue("D");
		dateColumnComboBox.setValue("E");
		closingNumberColumnComboBox.setValue("F");
		trainTypeColumnComboBox.setValue("G");
		notesColumnComboBox.setValue("H");
		firstRowOfBridgeClosuresSpinner.setValueFactory(firstRowFactory);
		lastRowOfBridgeClosuresSpinner.setValueFactory(lastRowFactory);
		
		disableDataField2();
		disableDataField3();
	}

	@FXML private void handleSelectFileButton(ActionEvent event) 
	{
		chooseFile();
	}

	@FXML private void handleBridgeComboBox(ActionEvent event)
	{
		bridge = bridgeComboBox.getValue();
	}
	
	@FXML private void handleDayColumnComboBox(ActionEvent event)
	{
		dayColumn = dayColumnComboBox.getValue();
	}
	
	@FXML private void handleLowerColumnComboBox(ActionEvent event)
	{
		lowerColumn = lowerColumnComboBox.getValue();
	}

	@FXML private void handleRaiseColumnComboBox(ActionEvent event)
	{
		raiseColumn = raiseColumnComboBox.getValue();
	}
	
	@FXML private void handleTenderColumnComboBox(ActionEvent event)
	{
		tenderColumn = tenderColumnComboBox.getValue();
	}
	
	@FXML private void handleDateColumnComboBox(ActionEvent event)
	{
		dateColumn = dateColumnComboBox.getValue();
	}
	
	@FXML private void handleClosingNumberColumnComboBox(ActionEvent event)
	{
		closingNumberColumn = closingNumberColumnComboBox.getValue();
	}
	
	@FXML private void handleTrainTypeColumnComboBox(ActionEvent event)
	{
		trainTypeColumn = trainTypeColumnComboBox.getValue();
	}
	
	@FXML private void handleNotesColumnComboBox(ActionEvent event)
	{
		notesColumn = notesColumnComboBox.getValue();
	}

	@FXML private void handleExecuteButton(ActionEvent event) 
	{
		// Get location to save file to if not using system time as file name
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("cg_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("cg_lastDirectorySavedTo", null));
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
						prefs.put("cg_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				message = "\nStarting analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				executeButton.setDisable(true);
				
				disableDataField2();
				disableDataField3();
				
				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				progressBar.setVisible(false);
				setProgressIndicator(0.00);

				executeButton.setVisible(true);
				resetButton.setVisible(false);
				enableDataField1();

				executeButton.setDisable(true);
				disableDataField2();
				disableDataField3();
				
				dayColumnComboBox.setValue("A");
				lowerColumnComboBox.setValue("B");
				raiseColumnComboBox.setValue("C");
				tenderColumnComboBox.setValue("D");
				dateColumnComboBox.setValue("E");
				closingNumberColumnComboBox.setValue("F");
				trainTypeColumnComboBox.setValue("G");
				notesColumnComboBox.setValue("H");
				firstRowOfBridgeClosuresSpinner.getValueFactory().setValue(1);
				lastRowOfBridgeClosuresSpinner.getValueFactory().setValue(maxRows);
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("cg_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("cg_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
					directoryChooser.setInitialDirectory(path.toFile());
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				message = "\nStarting analysis at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("cg_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				disableDataField1();
				disableDataField2();
				disableDataField3();
				
				executeButton.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				progressBar.setVisible(false);
				setProgressIndicator(0.00);

				executeButton.setVisible(true);
				executeButton.setDisable(true);
				resetButton.setVisible(false);
				selectFileButton.setDisable(false);
				fileNameLabel.setText("");
				
				enableDataField1();
				disableDataField2();
				disableDataField3();
				
				dayColumnComboBox.setValue("A");
				lowerColumnComboBox.setValue("B");
				raiseColumnComboBox.setValue("C");
				tenderColumnComboBox.setValue("D");
				dateColumnComboBox.setValue("E");
				closingNumberColumnComboBox.setValue("F");
				trainTypeColumnComboBox.setValue("G");
				notesColumnComboBox.setValue("H");
				firstRowOfBridgeClosuresSpinner.getValueFactory().setValue(1);
				lastRowOfBridgeClosuresSpinner.getValueFactory().setValue(maxRows);
			}	
		}
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		resetMessage();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);

		executeButton.setVisible(true);
		resetButton.setVisible(false);
		selectFileButton.setDisable(false);
		fileNameLabel.setText("");
		
		enableDataField1();
		disableDataField2();
		disableDataField3();
		
		dayColumnComboBox.setValue("A");
		lowerColumnComboBox.setValue("B");
		raiseColumnComboBox.setValue("C");
		tenderColumnComboBox.setValue("D");
		dateColumnComboBox.setValue("E");
		closingNumberColumnComboBox.setValue("F");
		trainTypeColumnComboBox.setValue("G");
		notesColumnComboBox.setValue("H");
		firstRowOfBridgeClosuresSpinner.getValueFactory().setValue(1);
		lastRowOfBridgeClosuresSpinner.getValueFactory().setValue(maxRows);
	}
	
	private void enableDataField1()
	{
		oneLabel.setDisable(false);
		selectProjectFileLabel.setDisable(false);
		fileNameLabel.setDisable(false);
		selectFileButton.setDisable(false);
	}

	private void disableDataField1()
	{
		oneLabel.setDisable(true);
		selectProjectFileLabel.setDisable(true);
		fileNameLabel.setDisable(true);
		selectFileButton.setDisable(true);
	}
	
	private void enableDataField2()
	{
		twoLabel.setDisable(false);
		selectBridgeLabel.setDisable(false);
		bridgeComboBox.setDisable(false);
	}

	private void disableDataField2()
	{
		twoLabel.setDisable(true);
		selectBridgeLabel.setDisable(true);
		bridgeComboBox.setDisable(true);
	}
	
	private void enableDataField3()
	{
		threeLabel.setDisable(false);
		selectDataFieldsLabel.setDisable(false);
		firstRowOfBridgeClosuresLabel.setDisable(false);
		lastRowOfBridgeClosuresLabel.setDisable(false);
		dayColumnLabel.setDisable(false);
		lowerColumnLabel.setDisable(false);
		raiseColumnLabel.setDisable(false);
		firstRowOfBridgeClosuresSpinner.setDisable(false);
		dayColumnComboBox.setDisable(false);
		lowerColumnComboBox.setDisable(false);
		raiseColumnComboBox.setDisable(false);
		tenderColumnComboBox.setDisable(false);
		tenderColumnLabel.setDisable(false);
		dateColumnComboBox.setDisable(false);
		dateColumnLabel.setDisable(false);
		closingNumberColumnComboBox.setDisable(false);
		closingNumberColumnLabel.setDisable(false);
		trainTypeColumnComboBox.setDisable(false);
		trainTypeColumnLabel.setDisable(false);
		notesColumnComboBox.setDisable(false);
		notesColumnLabel.setDisable(false);
		lastRowOfBridgeClosuresSpinner.setDisable(false);
	}
	
	private void disableDataField3()
	{
		threeLabel.setDisable(true);
		selectDataFieldsLabel.setDisable(true);
		firstRowOfBridgeClosuresLabel.setDisable(true);
		lastRowOfBridgeClosuresLabel.setDisable(true);
		dayColumnLabel.setDisable(true);
		lowerColumnLabel.setDisable(true);
		raiseColumnLabel.setDisable(true);
		tenderColumnComboBox.setDisable(true);
		tenderColumnLabel.setDisable(true);
		dateColumnComboBox.setDisable(true);
		dateColumnLabel.setDisable(true);
		closingNumberColumnComboBox.setDisable(true);
		closingNumberColumnLabel.setDisable(true);
		trainTypeColumnComboBox.setDisable(true);
		trainTypeColumnLabel.setDisable(true);
		notesColumnComboBox.setDisable(true);
		notesColumnLabel.setDisable(true);
		firstRowOfBridgeClosuresSpinner.setDisable(true);
		dayColumnComboBox.setDisable(true);
		lowerColumnComboBox.setDisable(true);
		raiseColumnComboBox.setDisable(true);
		lastRowOfBridgeClosuresSpinner.setDisable(true);
	}
	
	private void chooseFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"Excel Bridge Files", "*.XLSX");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// See if last directory is stored
		if ((prefs.get("cg_lastDirectoryForBridgeCompliance", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("cg_lastDirectoryForBridgeCompliance", null));

			if ((path.toFile().exists()) && (path !=null))
				fileChooser.setInitialDirectory(path.toFile());
		}

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) selectFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .XLSX file found
		if (file != null)
		{
			// Reset checkbox status
			executeButton.setDisable(true);

			// Write message
			clearMessage();
			message = "BIAS USCG Bridge Compliance Analysis Module - "+BIASLaunch.getSoftwareVersion()+"\n";

			// Store path for subsequent runs and set labels
			fullyQualifiedPath = file.toString();
			fileNameLabel.setText(fullyQualifiedPath);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("cg_lastDirectoryForBridgeCompliance", file.getParent());

			displayMessage(message);

			try 
			{
				BIASPreprocessClosuresForUscgBridgeAnalysis preprocessData = new BIASPreprocessClosuresForUscgBridgeAnalysis(fullyQualifiedPath);
				if (preprocessData.returnBridgeFileDataLocations().get(0) != null)
				{
					int firstRowIndex = (Integer) preprocessData.returnBridgeFileDataLocations().get(0) + 1;
					firstRowOfBridgeClosuresSpinner.getValueFactory().setValue(firstRowIndex);
				}

				if (preprocessData.returnBridgeFileDataLocations().get(1) != null)
				{
					int lastRowIndex = (Integer) preprocessData.returnBridgeFileDataLocations().get(1) + 1;
					lastRowOfBridgeClosuresSpinner.getValueFactory().setValue(lastRowIndex);
				}

				if (preprocessData.returnBridgeFileDataLocations().get(2) != null)
				{
					dayColumn = (String) preprocessData.returnBridgeFileDataLocations().get(2);
					dayColumnComboBox.setValue(dayColumn);
				}
				
				if (preprocessData.returnBridgeFileDataLocations().get(3) != null)
				{
					lowerColumn = (String) preprocessData.returnBridgeFileDataLocations().get(3);
					lowerColumnComboBox.setValue(lowerColumn);
				}

				if (preprocessData.returnBridgeFileDataLocations().get(4) != null)
				{
					raiseColumn = (String) preprocessData.returnBridgeFileDataLocations().get(4);
					raiseColumnComboBox.setValue(raiseColumn);
				}
				
				if (preprocessData.returnBridgeFileDataLocations().get(5) != null)
				{
					tenderColumn = (String) preprocessData.returnBridgeFileDataLocations().get(5);
					tenderColumnComboBox.setValue(tenderColumn);
				}

				if (preprocessData.returnBridgeFileDataLocations().get(6) != null)
				{
					dateColumn = (String) preprocessData.returnBridgeFileDataLocations().get(6);
					dateColumnComboBox.setValue(dateColumn);
				}
				
				if (preprocessData.returnBridgeFileDataLocations().get(7) != null)
				{
					closingNumberColumn = (String) preprocessData.returnBridgeFileDataLocations().get(7);
					closingNumberColumnComboBox.setValue(closingNumberColumn);
				}
				
				if (preprocessData.returnBridgeFileDataLocations().get(8) != null)
				{
					trainTypeColumn = (String) preprocessData.returnBridgeFileDataLocations().get(8);
					trainTypeColumnComboBox.setValue(trainTypeColumn);
				}
				
				if (preprocessData.returnBridgeFileDataLocations().get(9) != null)
				{
					notesColumn = (String) preprocessData.returnBridgeFileDataLocations().get(9);
					notesColumnComboBox.setValue(notesColumn);
				}
			} 
			catch (Exception e) 
			{
				ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}

			enableDataField2();
			enableDataField3();

			executeButton.setDisable(false);
		}
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

	private void runTask() throws InterruptedException
	{
		ReadExcelFileForBridgeCompliance readData = null;
		BridgeComplianceAnalysis analyzeData = null;
		
		// Read all objects that are required for the bridge analysis
		try 
		{
			readData = new ReadExcelFileForBridgeCompliance(fullyQualifiedPath, firstRowOfClosures, dayColumn, lowerColumn, raiseColumn, tenderColumn, dateColumn, closingNumberColumn,
					trainTypeColumn, notesColumn, lastRowOfClosures);
		} 
		catch (Exception e) 
		{
			displayMessage("\nError in reading spreadsheet");
			continueAnalysis = false;
		}

		message = readData.getResultsMessage();
		displayMessage(message);
		continueAnalysis = readData.getValidFile();
		setProgressIndicator(0.25);

		// Analyze compliance
		if (continueAnalysis)
		{
			analyzeData = new BridgeComplianceAnalysis(readData.getClosures(), BIASUscgBridgeComplianceAnalysisConfigPageController.getMarineAccessPeriods());
			
			message = analyzeData.getResultsMessage();
			displayMessage(message);
			setProgressIndicator(0.75);
			continueAnalysis = analyzeData.getContinueAnalysis();
		}
				
		// Write results to spreadsheet
		if (continueAnalysis)
		{
			if (BIASGeneralConfigController.getUseSerialTimeAsFileName())
			{
				WriteBridgeComplianceFiles3 filesToWrite = new WriteBridgeComplianceFiles3(analyzeData.getClosures(), bridge + readData.getDateSpan(), textArea.getText(), getSaveFileFolderForSerialFileName());
				message = filesToWrite.getResultsMessageWrite3();
			}
			else
			{
				WriteBridgeComplianceFiles3 filesToWrite = new WriteBridgeComplianceFiles3(analyzeData.getClosures(), bridge + readData.getDateSpan(), textArea.getText(), getSaveFileLocationForUserSpecifiedFileName());
				message = filesToWrite.getResultsMessageWrite3();
			}
			
			displayMessage(message);
				
			if (!WriteBridgeComplianceFiles3.getErrorFound())
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
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
				
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

	UnaryOperator<Change> integerFilter = change -> {
		String input = change.getText();
		if (input.matches("[0-9]*")) { 
			return change;
		}
		return null;
	};
}