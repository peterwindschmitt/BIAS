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
	private static String fileAsString;
	private static String fullyQualifiedPath;

	private static String message = "";
	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;

	private static ObservableList<String> columnValues =  FXCollections.observableArrayList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
	
	private static final Integer maxRows = 999;
	private static Integer firstRowOfClosures;
	private static Integer lastRowOfClosures;

	private static SpinnerValueFactory<Integer> firstRowFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxRows, 1);
	private static SpinnerValueFactory<Integer> lastRowFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxRows, maxRows);

	private static String dayColumn;
	private static String lowerColumn;
	private static String raiseColumn;

	@FXML private Button selectFileButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label selectProjectFileLabel;
	@FXML private Label fileNameLabel;
	@FXML private Label oneLabel;
	@FXML private Label twoLabel;
	@FXML private Label firstRowOfBridgeClosuresLabel;
	@FXML private Label lastRowOfBridgeClosuresLabel;
	@FXML private Label dayColumnLabel;
	@FXML private Label lowerColumnLabel;
	@FXML private Label raiseColumnLabel;
	@FXML private Label selectDataFieldsLabel;

	@FXML private TextArea textArea;

	@FXML private Spinner<Integer> firstRowOfBridgeClosuresSpinner;
	@FXML private Spinner<Integer> lastRowOfBridgeClosuresSpinner;
	@FXML private ComboBox<String> dayColumnComboBox;
	@FXML private ComboBox<String> lowerColumnComboBox;
	@FXML private ComboBox<String> raiseColumnComboBox;

	@FXML private ProgressBar progressBar;

	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");	

		dayColumnComboBox.getItems().addAll(columnValues);
		lowerColumnComboBox.getItems().addAll(columnValues);
		raiseColumnComboBox.getItems().addAll(columnValues);
		
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

		dayColumnComboBox.setValue("A");
		lowerColumnComboBox.setValue("B");
		raiseColumnComboBox.setValue("C");
		firstRowOfBridgeClosuresSpinner.setValueFactory(firstRowFactory);
		lastRowOfBridgeClosuresSpinner.setValueFactory(lastRowFactory);

		oneLabel.setDisable(false);
		selectProjectFileLabel.setDisable(false);
		fileNameLabel.setDisable(false);

		twoLabel.setDisable(true);
		selectDataFieldsLabel.setDisable(true);
		firstRowOfBridgeClosuresLabel.setDisable(true);
		lastRowOfBridgeClosuresLabel.setDisable(true);
		dayColumnLabel.setDisable(true);
		lowerColumnLabel.setDisable(true);
		raiseColumnLabel.setDisable(true);
		firstRowOfBridgeClosuresSpinner.setDisable(true);
		lastRowOfBridgeClosuresSpinner.setDisable(true);
		dayColumnComboBox.setDisable(true);
		lowerColumnComboBox.setDisable(true);
		raiseColumnComboBox.setDisable(true);
	}

	@FXML private void handleSelectFileButton(ActionEvent event) 
	{
		chooseFile();
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
				
				twoLabel.setDisable(true);
				selectDataFieldsLabel.setDisable(true);
				firstRowOfBridgeClosuresLabel.setDisable(true);
				lastRowOfBridgeClosuresLabel.setDisable(true);
				dayColumnLabel.setDisable(true);
				lowerColumnLabel.setDisable(true);
				raiseColumnLabel.setDisable(true);
				firstRowOfBridgeClosuresSpinner.setDisable(true);
				lastRowOfBridgeClosuresSpinner.setDisable(true);
				dayColumnComboBox.setDisable(true);
				lowerColumnComboBox.setDisable(true);
				raiseColumnComboBox.setDisable(true);
				
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
				selectFileButton.setDisable(false);
				fileNameLabel.setText("");
				oneLabel.setDisable(false);
				selectProjectFileLabel.setDisable(false);
				fileNameLabel.setDisable(false);

				executeButton.setDisable(true);
				twoLabel.setDisable(true);
				selectDataFieldsLabel.setDisable(true);
				firstRowOfBridgeClosuresLabel.setDisable(true);
				lastRowOfBridgeClosuresLabel.setDisable(true);
				dayColumnLabel.setDisable(true);
				lowerColumnLabel.setDisable(true);
				raiseColumnLabel.setDisable(true);
				firstRowOfBridgeClosuresSpinner.setDisable(true);
				lastRowOfBridgeClosuresSpinner.setDisable(true);
				dayColumnComboBox.setDisable(true);
				lowerColumnComboBox.setDisable(true);
				raiseColumnComboBox.setDisable(true);
				
				dayColumnComboBox.setValue("A");
				lowerColumnComboBox.setValue("B");
				raiseColumnComboBox.setValue("C");
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

				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				executeButton.setDisable(true);

				twoLabel.setDisable(true);
				selectDataFieldsLabel.setDisable(true);
				firstRowOfBridgeClosuresLabel.setDisable(true);
				lastRowOfBridgeClosuresLabel.setDisable(true);
				dayColumnLabel.setDisable(true);
				lowerColumnLabel.setDisable(true);
				raiseColumnLabel.setDisable(true);
				firstRowOfBridgeClosuresSpinner.setDisable(true);
				lastRowOfBridgeClosuresSpinner.setDisable(true);
				dayColumnComboBox.setDisable(true);
				lowerColumnComboBox.setDisable(true);
				raiseColumnComboBox.setDisable(true);
				
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
				oneLabel.setDisable(false);
				selectProjectFileLabel.setDisable(false);
				fileNameLabel.setDisable(false);

				twoLabel.setDisable(true);
				selectDataFieldsLabel.setDisable(true);
				firstRowOfBridgeClosuresLabel.setDisable(true);
				lastRowOfBridgeClosuresLabel.setDisable(true);
				dayColumnLabel.setDisable(true);
				lowerColumnLabel.setDisable(true);
				raiseColumnLabel.setDisable(true);
				firstRowOfBridgeClosuresSpinner.setDisable(true);
				lastRowOfBridgeClosuresSpinner.setDisable(true);
				dayColumnComboBox.setDisable(true);
				lowerColumnComboBox.setDisable(true);
				raiseColumnComboBox.setDisable(true);
				
				dayColumnComboBox.setValue("A");
				lowerColumnComboBox.setValue("B");
				raiseColumnComboBox.setValue("C");
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
		oneLabel.setDisable(false);
		selectProjectFileLabel.setDisable(false);
		fileNameLabel.setDisable(false);

		twoLabel.setDisable(true);
		selectDataFieldsLabel.setDisable(true);
		firstRowOfBridgeClosuresLabel.setDisable(true);
		lastRowOfBridgeClosuresLabel.setDisable(true);
		dayColumnLabel.setDisable(true);
		lowerColumnLabel.setDisable(true);
		raiseColumnLabel.setDisable(true);
		firstRowOfBridgeClosuresSpinner.setDisable(true);
		lastRowOfBridgeClosuresSpinner.setDisable(true);
		dayColumnComboBox.setDisable(true);
		lowerColumnComboBox.setDisable(true);
		raiseColumnComboBox.setDisable(true);
		
		dayColumnComboBox.setValue("A");
		lowerColumnComboBox.setValue("B");
		raiseColumnComboBox.setValue("C");
		firstRowOfBridgeClosuresSpinner.getValueFactory().setValue(1);
		lastRowOfBridgeClosuresSpinner.getValueFactory().setValue(maxRows);
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
			fileAsString = file.getName().toString();
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
			} 
			catch (Exception e) 
			{
				ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}

			twoLabel.setDisable(false);
			selectDataFieldsLabel.setDisable(false);
			firstRowOfBridgeClosuresSpinner.setDisable(false);
			firstRowOfBridgeClosuresLabel.setDisable(false);
			dayColumnComboBox.setDisable(false);
			lowerColumnComboBox.setDisable(false);
			dayColumnLabel.setDisable(false);
			lowerColumnLabel.setDisable(false);
			raiseColumnComboBox.setDisable(false);
			raiseColumnLabel.setDisable(false);
			lastRowOfBridgeClosuresSpinner.setDisable(false);
			lastRowOfBridgeClosuresLabel.setDisable(false);

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
			readData = new ReadExcelFileForBridgeCompliance(fullyQualifiedPath, firstRowOfClosures, dayColumn, lowerColumn, raiseColumn, lastRowOfClosures);
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

		// Analyze closures
		if (continueAnalysis)
		{
			analyzeData = new BridgeComplianceAnalysis();
		}
		else
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
		
		message = analyzeData.getResultsMessage();
		displayMessage(message);
		setProgressIndicator(0.75);

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