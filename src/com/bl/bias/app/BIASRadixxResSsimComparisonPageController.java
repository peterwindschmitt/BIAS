package com.bl.bias.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import com.bl.bias.exception.ErrorShutdown;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.event.ActionEvent;

public class BIASRadixxResSsimComparisonPageController 
{
	@FXML private Label selectA;
	@FXML private Label selectB;
	@FXML private Label fileANameLabel;
	@FXML private Label fileBNameLabel;

	@FXML private Button selectFileAButton;
	@FXML private Button selectFileBButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private ProgressBar progressBar;

	@FXML private TextArea textArea;

	private static Preferences prefs;

	private static String fileAAsString;
	private static String fileBAsString;
	private static String dirAAsString;
	private static String dirBAsString;
	private static String fullyQualifiedPathA;
	private static String fullyQualifiedPathB;

	private static File saveFileLocation;
	private static File saveDirectoryLocation;
	private static File directory;

	private static String message = "";

	private static SimpleBooleanProperty aSelected;
	private static SimpleBooleanProperty bSelected;

	private static BooleanBinding disableSelectFileB;
	private static BooleanBinding disableExecuteButton;

	@FXML private void initialize() 
	{
		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");

		// Bind B to A
		aSelected = new SimpleBooleanProperty();
		aSelected.set(false);
		disableSelectFileB = aSelected.not();
		selectB.disableProperty().bind(disableSelectFileB);
		selectFileBButton.disableProperty().bind(disableSelectFileB);

		// Bind Checkboxes to B
		bSelected = new SimpleBooleanProperty();
		bSelected.set(false);
		disableExecuteButton = bSelected.not();
		executeButton.disableProperty().bind(disableExecuteButton);
	}

	@FXML public void handleSelectFileAButton(ActionEvent event) 
	{
		chooseFile("sm_lastDirectoryReadFromA");
	}

	@FXML public void handleSelectFileBButton(ActionEvent event) 
	{
		chooseFile("sm_lastDirectoryReadFromB");
	}

	@FXML public void handleExecuteButton()
	{
		selectFileAButton.disableProperty().unbind();
		selectFileAButton.setDisable(true);
		selectFileBButton.disableProperty().unbind();
		selectFileBButton.setDisable(true);

		selectA.disableProperty().unbind();
		selectA.setDisable(true);
		selectB.disableProperty().unbind();
		selectB.setDisable(true);

		progressBar.setVisible(true);
		executeButton.disableProperty().unbind();
		executeButton.setDisable(true);

		// User specified
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{	
			// Get location to save file to
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("sm_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("sm_lastDirectorySavedTo", null));
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
					saveFileLocation = file;
					if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
						prefs.put("sm_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				startTask();
			}
			else
			{
				//  User did not commit so reset
				resetMessage();

				executeButton.setVisible(true);
				resetButton.setVisible(false);
				progressBar.setProgress(0);
				progressBar.setVisible(false);

				// Reset bindings and properties
				// Bind B to A
				aSelected = new SimpleBooleanProperty();
				aSelected.set(false);
				disableSelectFileB = aSelected.not();
				selectB.disableProperty().bind(disableSelectFileB);
				selectFileBButton.disableProperty().bind(disableSelectFileB);

				// Bind Checkboxes to B
				bSelected = new SimpleBooleanProperty();
				bSelected.set(false);

				disableExecuteButton = bSelected.not();
				executeButton.disableProperty().bind(disableExecuteButton);

				// Reset parameters to allow A to be again selected
				selectA.setDisable(false);
				selectFileAButton.setDisable(false);		

				fileANameLabel.setText("");
				fileBNameLabel.setText("");

				// Reset for next run
				fileAAsString = null;
				fileBAsString = null;
			}
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("sm_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("sm_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
				{
					directoryChooser.setInitialDirectory(path.toFile());
				}
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				try 
				{
					saveDirectoryLocation = directory;
					if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
						prefs.put("sm_lastDirectorySavedTo", directory.toString());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				startTask();
			}
			else
			{
				//  User did not commit so reset
				resetMessage();

				executeButton.setVisible(true);
				resetButton.setVisible(false);
				progressBar.setProgress(0);
				progressBar.setVisible(false);

				// Reset bindings and properties
				// Bind B to A
				aSelected = new SimpleBooleanProperty();
				aSelected.set(false);
				disableSelectFileB = aSelected.not();
				selectB.disableProperty().bind(disableSelectFileB);
				selectFileBButton.disableProperty().bind(disableSelectFileB);

				// Bind Checkboxes to B
				bSelected = new SimpleBooleanProperty();
				bSelected.set(false);

				disableExecuteButton = bSelected.not();
				executeButton.disableProperty().bind(disableExecuteButton);

				// Reset parameters to allow A to be again selected
				selectA.setDisable(false);
				selectFileAButton.setDisable(false);		

				fileANameLabel.setText("");
				fileBNameLabel.setText("");

				// Reset for next run
				fileAAsString = null;
				fileBAsString = null;
			}
		}
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		resetMessage();

		executeButton.setVisible(true);
		resetButton.setVisible(false);
		progressBar.setProgress(0);
		progressBar.setVisible(false);

		// Reset bindings and properties
		// Bind B to A
		aSelected = new SimpleBooleanProperty();
		aSelected.set(false);
		disableSelectFileB = aSelected.not();
		selectB.disableProperty().bind(disableSelectFileB);
		selectFileBButton.disableProperty().bind(disableSelectFileB);

		// Bind Checkboxes to B
		bSelected = new SimpleBooleanProperty();
		bSelected.set(false);

		disableExecuteButton = bSelected.not();
		executeButton.disableProperty().bind(disableExecuteButton);

		// Reset parameters to allow A to be again selected
		selectA.setDisable(false);
		selectFileAButton.setDisable(false);		

		fileANameLabel.setText("");
		fileBNameLabel.setText("");

		// Reset for next run
		fileAAsString = null;
		fileBAsString = null;
	}

	private void chooseFile(String directory)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"SSIM Files", "*.txt");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// See if last directory is stored
		if ((prefs.get(directory, null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			if (directory.equals("sm_lastDirectoryReadFromA"))
			{
				dirAAsString = prefs.get(directory, null);
				if (dirAAsString != null) 
				{
					Path path = Paths.get(prefs.get("sm_lastDirectoryReadFromA", null));
					if ((path.toFile().exists()) && (path !=null))
					{
						fileChooser.setInitialDirectory(path.toFile());
						if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
							prefs.put(directory, path.toString());
					}
				}
			}
			else
			{
				dirBAsString = prefs.get(directory, null);
				if (dirBAsString != null) 
				{
					Path path = Paths.get(prefs.get("sm_lastDirectoryReadFromB", null));
					if ((path.toFile().exists()) && (path !=null))
					{
						fileChooser.setInitialDirectory(path.toFile());
						if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
							prefs.put(directory, path.toString());
					}
				}
			}
		}

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) selectFileAButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Store path for subsequent runs and set labels
		if (file != null)
		{
			// Write message
			clearMessage();
			message = "BIAS Radixx Res SSIM Comparison Module - "+BIASLaunch.getSoftwareVersion();

			if (directory.contentEquals("sm_lastDirectoryReadFromA"))
			{
				fileAAsString = file.getName().toString();
				fullyQualifiedPathA = file.toString();
				fileANameLabel.setText(fullyQualifiedPathA);
				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put(directory, file.getParent());

				// Enable selections for File B
				aSelected.set(true);
			}
			else
			{
				fileBAsString = file.getName().toString();
				fullyQualifiedPathB = file.toString();
				fileBNameLabel.setText(fullyQualifiedPathB);
				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put(directory, file.getParent());

				// Enable selections for checkboxes and execute button
				bSelected.set(true);
			}

			if ((fileAAsString != null) && (fileBAsString != null))
			{
				String tempMessageA = "\n\nSet to compare Radixx Res SSIM files from "+fileAAsString;
				String tempMessageB = "\nversus "+fileBAsString+"\n";

				message += tempMessageA;
				message += tempMessageB;
			}

			displayMessage(message);
		}
	}

	public void setprogressBar(double value)
	{
		progressBar.setProgress(value);
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
		/*Boolean error = false;
		// Read data
		if ((readData(fullyQualifiedPathA, fullyQualifiedPathB)) == false)
		{
			// Analyze data
			if(analyzeData() == false)
			{
				// Write data
				if(writeResults() == false)
				{
					// At this point process is complete
					displayMessage("\n*** PROCESSING COMPLETE ***");
				}
				else
				{
					error = true;
					displayMessage("\nError in writing files");
					displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
				}
			}
			else 
			{
				displayMessage("\nError in analyzing files");
				displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText(null);
						alert.setContentText("There are errors in the SSIM files!  \n\nSee log window.");

						Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));

						alert.showAndWait();
					}
				});
			}
		}
		else
		{
			error = true;
			displayMessage("\nError in reading files");
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
		}
		*/
		//  Now reset for next case
		executeButton.setVisible(false);
		resetButton.setVisible(true);
		resetButton.setDisable(false);   
	}

	/*
	private Boolean readData(String fullyQualifiedPathA, String fullyQualifiedPathB)
	{
		ReadTTestFiles readData = new ReadTTestFiles();
		Boolean error = readData.read(fullyQualifiedPathA, fullyQualifiedPathB, requestGenerateVelocity, requestGenerateTrueDelayMinutesPer100TrainMiles, requestGenerateElapsedRunTimePerTrain, requestGenerateOtp, requestGenerateType, requestGenerateGroup);
		if (requestGenerateType)
		{
			byTypeDataA = readData.getTypeDataA();
			byTypeDataB = readData.getTypeDataB();
		}
		if (requestGenerateGroup) 
		{
			byGroupDataA = readData.getGroupDataA();
			byGroupDataB = readData.getGroupDataB();
		}

		lines = readData.getLines();
		categoriesForTypes = readData.getCategoriesForTypes();
		categoriesForGroups = readData.getCategoriesForGroups();

		fileACount = readData.getFilesASize();
		fileBCount = readData.getFilesBSize();

		displayMessage(readData.getResultsMessage());
		if (!error)
			progressBar.setProgress(.40);
		return error;
	}

	private Boolean analyzeData()
	{
		LocalTime startAnalyzingFilesTime = LocalTime.now();
		message = "\nStarted analyzing files at "+startAnalyzingFilesTime+"\n";
		displayMessage(message);

		Boolean error = false;
		if ((requestGenerateGroup) && (!error))
		{
			criticalT = Double.MAX_VALUE;
			RTCResultsAnalysisAnalyzeTTestByGroup analyzeDataByGroup = new RTCResultsAnalysisAnalyzeTTestByGroup(fileACount, fileBCount, lines, categoriesForGroups, byGroupDataA, byGroupDataB, requestGenerateVelocity, requestGenerateTrueDelayMinutesPer100TrainMiles, requestGenerateElapsedRunTimePerTrain, requestGenerateOtp);
			Boolean errorGroup = analyzeDataByGroup.analyzeByGroup();
			displayMessage(analyzeDataByGroup.getResultsMessage());
			if (!errorGroup)
				progressBar.setProgress(.50);
			else 
				error = true;
		}

		if ((requestGenerateType) && (!error))
		{
			criticalT = Double.MAX_VALUE;
			RTCResultsAnalysisAnalyzeTTestByType analyzeDataByType = new RTCResultsAnalysisAnalyzeTTestByType(fileACount, fileBCount, lines, categoriesForTypes, byTypeDataA, byTypeDataB, requestGenerateVelocity, requestGenerateTrueDelayMinutesPer100TrainMiles, requestGenerateElapsedRunTimePerTrain, requestGenerateOtp);
			Boolean errorType = analyzeDataByType.analyzeByType();
			displayMessage(analyzeDataByType.getResultsMessage());
			if (!errorType)
				progressBar.setProgress(.50);
			else
				error = true;
		}

		LocalTime endAnalyzeFilesTime = LocalTime.now();
		message = "Finished analyzing files at "+endAnalyzeFilesTime+"\n";
		displayMessage(message);

		return error;
	}

	private Boolean writeResults()
	{
		Boolean error = false;

		try
		{
			new WriteTTestFiles1(textArea.getText(), RTCResultsAnalysisAnalyzeTTestByType.getTypeTTestResults(), RTCResultsAnalysisAnalyzeTTestByGroup.getGroupTTestResults());
			new WriteTTestFiles2(textArea.getText(), RTCResultsAnalysisAnalyzeTTestByType.getTypeTTestResults(), RTCResultsAnalysisAnalyzeTTestByGroup.getGroupTTestResults());
			new WriteTTestFiles3(textArea.getText(), RTCResultsAnalysisAnalyzeTTestByType.getTypeTTestResults(), RTCResultsAnalysisAnalyzeTTestByGroup.getGroupTTestResults());
			new WriteTTestFiles4(textArea.getText(), RTCResultsAnalysisAnalyzeTTestByType.getTypeTTestResults(), RTCResultsAnalysisAnalyzeTTestByGroup.getGroupTTestResults());
			new WriteTTestFiles5(textArea.getText(), RTCResultsAnalysisAnalyzeTTestByType.getTypeTTestResults(), RTCResultsAnalysisAnalyzeTTestByGroup.getGroupTTestResults());			
			new WriteTTestFiles6(textArea.getText(), RTCResultsAnalysisAnalyzeTTestByType.getTypeTTestResults(), RTCResultsAnalysisAnalyzeTTestByGroup.getGroupTTestResults());			

			int totalResultsToWrite = WriteTTestFiles1.getResultsSetSize1() + WriteTTestFiles2.getResultsSetSize2() + WriteTTestFiles3.getResultsSetSize3() + WriteTTestFiles4.getResultsSetSize4() + WriteTTestFiles5.getResultsSetSize5() + WriteTTestFiles6.getResultsSetSize6();
			if (totalResultsToWrite > 0)
			{
				WriteTTestFiles7 writeTTestSpreadsheet7 = new WriteTTestFiles7(textArea.getText());
				message = writeTTestSpreadsheet7.getResultsWriteMessage7();
				error = writeTTestSpreadsheet7.getErrorFound();
			}
			else
			{
				message = "\nAfter peforming t-tests, no valid results to write were found";
			}
		}
		catch (Exception e)
		{
			error = true;
		}

		if (!error)
			displayMessage(message);

		progressBar.setProgress(1);

		return error;
	}
	*/

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
		if (!saveFileLocation.toString().toLowerCase().endsWith(".txt"))
		{
			String saveFileLocationAsString = saveFileLocation.toString();
			saveFileLocationAsString += ".txt";
			saveFileLocation = new File(saveFileLocationAsString);
		}

		return saveFileLocation;
	}

	public static File getSaveDirectoryLocation()
	{		
		return saveDirectoryLocation;
	}

	public static String getFileAAsString()
	{		
		return fileAAsString;
	}

	public static String getFileBAsString()
	{		
		return fileBAsString;
	}
}