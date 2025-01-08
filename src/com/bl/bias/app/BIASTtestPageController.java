package com.bl.bias.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.prefs.Preferences;

import com.bl.bias.analyze.RTCResultsAnalysisAnalyzeTTestByGroup;
import com.bl.bias.analyze.RTCResultsAnalysisAnalyzeTTestByType;
import com.bl.bias.analyze.TTestValidateTTest;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.SampleForTTest;
import com.bl.bias.read.ReadTTestFiles;
import com.bl.bias.write.WriteTTestFiles1;
import com.bl.bias.write.WriteTTestFiles2;
import com.bl.bias.write.WriteTTestFiles3;
import com.bl.bias.write.WriteTTestFiles4;
import com.bl.bias.write.WriteTTestFiles5;
import com.bl.bias.write.WriteTTestFiles6;
import com.bl.bias.write.WriteTTestFiles7;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.event.ActionEvent;

public class BIASTtestPageController 
{
	@FXML private Label selectA;
	@FXML private Label selectB;
	@FXML private Label fileANameLabel;
	@FXML private Label fileBNameLabel;
	@FXML private Label selectParametersLabel;
	@FXML private Label step1Label;
	@FXML private Label step2Label;
	@FXML private Label step3Label;

	@FXML private Button selectFileAButton;
	@FXML private Button selectFileBButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private CheckBox velocityCheckbox;
	@FXML private CheckBox trueDelayMinutesPer100TrainMilesCheckbox;
	@FXML private CheckBox otpCheckbox;
	@FXML private CheckBox elapsedRunTimePerTrainCheckbox;

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
	private static SimpleBooleanProperty velocitySelected;
	private static SimpleBooleanProperty trueDelayMinutesPer100TrainMilesSelected;
	private static SimpleBooleanProperty elapsedRunTimePerTrainSelected;
	private static SimpleBooleanProperty otpSelected;

	private static BooleanBinding disableSelectFileB;
	private static BooleanBinding disableCheckBoxes;
	private static BooleanBinding disableExecuteButton;

	private static Boolean requestGenerateVelocity = false;
	private static Boolean requestGenerateTrueDelayMinutesPer100TrainMiles = false;
	private static Boolean requestGenerateElapsedRunTimePerTrain = false;
	private static Boolean requestGenerateOtp = false;
	private static Boolean requestGenerateType = false;
	private static Boolean requestGenerateGroup = false;
	
	private ArrayList<SampleForTTest> byTypeDataA;
	private ArrayList<SampleForTTest> byGroupDataA;
	private ArrayList<SampleForTTest> byTypeDataB;
	private ArrayList<SampleForTTest> byGroupDataB;

	private HashSet<String> lines;
	private HashSet<String> categoriesForTypes;
	private HashSet<String> categoriesForGroups;

	private int fileACount;
	private int fileBCount;

	private static double criticalT;
	private static double levelOfSignificance;
	
	private static int tTestColumn1ColorIndex;
	private static int tTestColumn2ColorIndex;
	private static int tTestColumn3ColorIndex;
	private static int tTestColumn4ColorIndex;
	private static int tTestColumn5ColorIndex;
	
	@FXML private void initialize() 
	{
		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");

		// Bind B to A
		aSelected = new SimpleBooleanProperty();
		aSelected.set(false);
		disableSelectFileB = aSelected.not();
		step2Label.disableProperty().bind(disableSelectFileB);
		selectB.disableProperty().bind(disableSelectFileB);
		selectFileBButton.disableProperty().bind(disableSelectFileB);

		// Bind Checkboxes to B
		bSelected = new SimpleBooleanProperty();
		bSelected.set(false);
		disableCheckBoxes = bSelected.not();
		selectParametersLabel.disableProperty().bind(disableCheckBoxes);
		step3Label.disableProperty().bind(disableCheckBoxes);
		velocityCheckbox.disableProperty().bind(disableCheckBoxes);
		trueDelayMinutesPer100TrainMilesCheckbox.disableProperty().bind(disableCheckBoxes);
		otpCheckbox.disableProperty().bind(disableCheckBoxes);
		elapsedRunTimePerTrainCheckbox.disableProperty().bind(disableCheckBoxes);

		// Bind Execute to B and Checkboxes
		velocitySelected = new SimpleBooleanProperty();
		velocitySelected.set(true);
		trueDelayMinutesPer100TrainMilesSelected = new SimpleBooleanProperty();
		trueDelayMinutesPer100TrainMilesSelected.set(true);
		elapsedRunTimePerTrainSelected = new SimpleBooleanProperty();
		elapsedRunTimePerTrainSelected.set(true);
		otpSelected = new SimpleBooleanProperty();
		otpSelected.set(true);
	
		disableExecuteButton = bSelected.not().or(velocitySelected.not().and(trueDelayMinutesPer100TrainMilesSelected.not().and(elapsedRunTimePerTrainSelected.not().and(otpSelected.not()))));
		executeButton.disableProperty().bind(disableExecuteButton);
	}

	@FXML public void handleSelectFileAButton(ActionEvent event) 
	{
		chooseFile("tt_lastDirectoryReadFromA");
	}

	@FXML public void handleSelectFileBButton(ActionEvent event) 
	{
		chooseFile("tt_lastDirectoryReadFromB");
	}

	@FXML public void handleVelocityCheckbox() 
	{
		if (velocitySelected.get() == true)
			velocitySelected.set(false);
		else
			velocitySelected.set(true);
	}

	@FXML public void handleTrueDelayMinutesPer100TrainMilesCheckbox() 
	{
		if (trueDelayMinutesPer100TrainMilesSelected.get() == true)
			trueDelayMinutesPer100TrainMilesSelected.set(false);
		else
			trueDelayMinutesPer100TrainMilesSelected.set(true);
	}

	@FXML public void handleOtpCheckbox()
	{
		if (otpSelected.get() == true)
			otpSelected.set(false);
		else
			otpSelected.set(true);
	}

	@FXML public void handleElapsedRunTimePerTrainCheckbox()
	{
		if (elapsedRunTimePerTrainSelected.get() == true)
			elapsedRunTimePerTrainSelected.set(false);
		else
			elapsedRunTimePerTrainSelected.set(true);
	}

	@FXML public void handleExecuteButton()
	{
		step1Label.setDisable(true);
		velocityCheckbox.disableProperty().unbind();
		velocityCheckbox.setDisable(true);
		trueDelayMinutesPer100TrainMilesCheckbox.disableProperty().unbind();
		trueDelayMinutesPer100TrainMilesCheckbox.setDisable(true);
		elapsedRunTimePerTrainCheckbox.disableProperty().unbind();
		elapsedRunTimePerTrainCheckbox.setDisable(true);
		otpCheckbox.disableProperty().unbind();
		otpCheckbox.setDisable(true);
		selectFileAButton.disableProperty().unbind();
		selectFileAButton.setDisable(true);
		selectFileBButton.disableProperty().unbind();
		selectFileBButton.setDisable(true);

		step2Label.disableProperty().unbind();
		step2Label.setDisable(true);
		selectA.disableProperty().unbind();
		selectA.setDisable(true);
		step3Label.disableProperty().unbind();
		step3Label.setDisable(true);
		selectB.disableProperty().unbind();
		selectB.setDisable(true);
		selectParametersLabel.disableProperty().unbind();
		selectParametersLabel.setDisable(true);

		progressBar.setVisible(true);
		executeButton.disableProperty().unbind();
		executeButton.setDisable(true);

		// Set LOS
		if ((prefs.getInt("tt_alphaLosIndex", -1)) < 0)
			prefs.putInt("tt_alphaLosIndex", BIASTtestConfigPageController.getDefaultAlphaIndex());
		Integer losIndex = prefs.getInt("tt_alphaLosIndex", BIASTtestConfigPageController.getDefaultAlphaIndex());
		String losRaw = BIASTtestConfigPageController.getLosValues().get(losIndex);
		levelOfSignificance = Double.parseDouble(losRaw.substring(0, 4));
		
		// Set column colors
		tTestColumn1ColorIndex = prefs.getInt("tt_tTestColumn1ColorIndex", BIASTtestConfigPageController.getDefaultTTestColumnColorIndex());
		tTestColumn2ColorIndex = prefs.getInt("tt_tTestColumn2ColorIndex", BIASTtestConfigPageController.getDefaultTTestColumnColorIndex());
		tTestColumn3ColorIndex = prefs.getInt("tt_tTestColumn3ColorIndex", BIASTtestConfigPageController.getDefaultTTestColumnColorIndex());
		tTestColumn4ColorIndex = prefs.getInt("tt_tTestColumn4ColorIndex", BIASTtestConfigPageController.getDefaultTTestColumnColorIndex());
		tTestColumn5ColorIndex = prefs.getInt("tt_tTestColumn5ColorIndex", BIASTtestConfigPageController.getDefaultTTestColumnColorIndex());

		// User specified
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{	
			// Get location to save file to
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("tt_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("tt_lastDirectorySavedTo", null));
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
						prefs.put("tt_lastDirectorySavedTo", file.getParent());
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
				step2Label.disableProperty().bind(disableSelectFileB);
				
				// Bind Checkboxes to B
				bSelected = new SimpleBooleanProperty();
				bSelected.set(false);
				disableCheckBoxes = bSelected.not();
				selectParametersLabel.disableProperty().bind(disableCheckBoxes);
				velocityCheckbox.disableProperty().bind(disableCheckBoxes);
				trueDelayMinutesPer100TrainMilesCheckbox.disableProperty().bind(disableCheckBoxes);
				otpCheckbox.disableProperty().bind(disableCheckBoxes);
				elapsedRunTimePerTrainCheckbox.disableProperty().bind(disableCheckBoxes);
				step3Label.disableProperty().bind(disableCheckBoxes);
				
				// Bind Execute to B and Checkboxes
				velocitySelected = new SimpleBooleanProperty();
				velocitySelected.set(true);
				trueDelayMinutesPer100TrainMilesSelected = new SimpleBooleanProperty();
				trueDelayMinutesPer100TrainMilesSelected.set(true);
				elapsedRunTimePerTrainSelected = new SimpleBooleanProperty();
				elapsedRunTimePerTrainSelected.set(true);
				otpSelected = new SimpleBooleanProperty();
				otpSelected.set(true);
				
				disableExecuteButton = bSelected.not().or(velocitySelected.not().and(trueDelayMinutesPer100TrainMilesSelected.not().and(elapsedRunTimePerTrainSelected.not().and(otpSelected.not()))));
				executeButton.disableProperty().bind(disableExecuteButton);

				// Reset parameters to allow A to be again selected
				selectA.setDisable(false);
				selectFileAButton.setDisable(false);		

				fileANameLabel.setText("");
				fileBNameLabel.setText("");

				velocitySelected.set(true);
				velocityCheckbox.setSelected(true);
				trueDelayMinutesPer100TrainMilesSelected.set(true);
				trueDelayMinutesPer100TrainMilesCheckbox.setSelected(true);
				elapsedRunTimePerTrainSelected.set(true);
				elapsedRunTimePerTrainCheckbox.setSelected(true);
				otpSelected.set(true);
				otpCheckbox.setSelected(true);

				// Reset for next run
				RTCResultsAnalysisAnalyzeTTestByGroup.resetResults();
				RTCResultsAnalysisAnalyzeTTestByType.resetResults();
				requestGenerateVelocity = false;
				requestGenerateTrueDelayMinutesPer100TrainMiles = false;
				requestGenerateElapsedRunTimePerTrain = false;
				requestGenerateOtp = false;
				requestGenerateType = false;
				requestGenerateGroup = false;
				fileAAsString = null;
				fileBAsString = null;
			}
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("tt_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("tt_lastDirectorySavedTo", null));
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
						prefs.put("tt_lastDirectorySavedTo", directory.toString());
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
				step2Label.disableProperty().bind(disableSelectFileB);

				// Bind Checkboxes to B
				bSelected = new SimpleBooleanProperty();
				bSelected.set(false);
				disableCheckBoxes = bSelected.not();
				selectParametersLabel.disableProperty().bind(disableCheckBoxes);
				velocityCheckbox.disableProperty().bind(disableCheckBoxes);
				trueDelayMinutesPer100TrainMilesCheckbox.disableProperty().bind(disableCheckBoxes);
				otpCheckbox.disableProperty().bind(disableCheckBoxes);
				elapsedRunTimePerTrainCheckbox.disableProperty().bind(disableCheckBoxes);
				step3Label.disableProperty().bind(disableCheckBoxes);

				// Bind Execute to B and Checkboxes
				velocitySelected = new SimpleBooleanProperty();
				velocitySelected.set(true);
				trueDelayMinutesPer100TrainMilesSelected = new SimpleBooleanProperty();
				trueDelayMinutesPer100TrainMilesSelected.set(true);
				elapsedRunTimePerTrainSelected = new SimpleBooleanProperty();
				elapsedRunTimePerTrainSelected.set(true);
				otpSelected = new SimpleBooleanProperty();
				otpSelected.set(true);

				disableExecuteButton = bSelected.not().or(velocitySelected.not().and(trueDelayMinutesPer100TrainMilesSelected.not().and(elapsedRunTimePerTrainSelected.not().and(otpSelected.not()))));
				executeButton.disableProperty().bind(disableExecuteButton);

				// Reset parameters to allow A to be again selected
				selectA.setDisable(false);
				selectFileAButton.setDisable(false);		

				fileANameLabel.setText("");
				fileBNameLabel.setText("");

				velocitySelected.set(true);
				velocityCheckbox.setSelected(true);
				trueDelayMinutesPer100TrainMilesSelected.set(true);
				trueDelayMinutesPer100TrainMilesCheckbox.setSelected(true);
				elapsedRunTimePerTrainSelected.set(true);
				elapsedRunTimePerTrainCheckbox.setSelected(true);
				otpSelected.set(true);
				otpCheckbox.setSelected(true);

				// Reset for next run
				RTCResultsAnalysisAnalyzeTTestByGroup.resetResults();
				RTCResultsAnalysisAnalyzeTTestByType.resetResults();
				requestGenerateVelocity = false;
				requestGenerateTrueDelayMinutesPer100TrainMiles = false;
				requestGenerateElapsedRunTimePerTrain = false;
				requestGenerateOtp = false;
				requestGenerateType = false;
				requestGenerateGroup = false;
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

		step1Label.setDisable(false);
		
		// Reset bindings and properties
		// Bind B to A
		aSelected = new SimpleBooleanProperty();
		aSelected.set(false);
		disableSelectFileB = aSelected.not();
		selectB.disableProperty().bind(disableSelectFileB);
		selectFileBButton.disableProperty().bind(disableSelectFileB);
		step2Label.disableProperty().bind(disableSelectFileB);

		// Bind Checkboxes to B
		bSelected = new SimpleBooleanProperty();
		bSelected.set(false);
		disableCheckBoxes = bSelected.not();
		selectParametersLabel.disableProperty().bind(disableCheckBoxes);
		velocityCheckbox.disableProperty().bind(disableCheckBoxes);
		trueDelayMinutesPer100TrainMilesCheckbox.disableProperty().bind(disableCheckBoxes);
		otpCheckbox.disableProperty().bind(disableCheckBoxes);
		elapsedRunTimePerTrainCheckbox.disableProperty().bind(disableCheckBoxes);
		step3Label.disableProperty().bind(disableCheckBoxes);

		// Bind Execute to B and Checkboxes
		velocitySelected = new SimpleBooleanProperty();
		velocitySelected.set(true);
		trueDelayMinutesPer100TrainMilesSelected = new SimpleBooleanProperty();
		trueDelayMinutesPer100TrainMilesSelected.set(true);
		elapsedRunTimePerTrainSelected = new SimpleBooleanProperty();
		elapsedRunTimePerTrainSelected.set(true);
		otpSelected = new SimpleBooleanProperty();
		otpSelected.set(true);

		disableExecuteButton = bSelected.not().or(velocitySelected.not().and(trueDelayMinutesPer100TrainMilesSelected.not().and(elapsedRunTimePerTrainSelected.not().and(otpSelected.not()))));
		executeButton.disableProperty().bind(disableExecuteButton);

		// Reset parameters to allow A to be again selected
		selectA.setDisable(false);
		selectFileAButton.setDisable(false);		

		fileANameLabel.setText("");
		fileBNameLabel.setText("");

		velocitySelected.set(true);
		velocityCheckbox.setSelected(true);
		trueDelayMinutesPer100TrainMilesSelected.set(true);
		trueDelayMinutesPer100TrainMilesCheckbox.setSelected(true);
		elapsedRunTimePerTrainSelected.set(true);
		elapsedRunTimePerTrainCheckbox.setSelected(true);
		otpSelected.set(true);
		otpCheckbox.setSelected(true);

		// Reset for next run
		RTCResultsAnalysisAnalyzeTTestByGroup.resetResults();
		RTCResultsAnalysisAnalyzeTTestByType.resetResults();
		requestGenerateVelocity = false;
		requestGenerateTrueDelayMinutesPer100TrainMiles = false;
		requestGenerateElapsedRunTimePerTrain = false;
		requestGenerateOtp = false;
		requestGenerateType = false;
		requestGenerateGroup = false;
		fileAAsString = null;
		fileBAsString = null;
	}

	private void chooseFile(String directory)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"Excel Files", "*.xlsx");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// See if last directory is stored
		if ((prefs.get(directory, null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			if (directory.equals("tt_lastDirectoryReadFromA"))
			{
				dirAAsString = prefs.get(directory, null);
				if (dirAAsString != null) 
				{
					Path path = Paths.get(prefs.get("tt_lastDirectoryReadFromA", null));
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
					Path path = Paths.get(prefs.get("tt_lastDirectoryReadFromB", null));
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
			message = "BIAS T-test Analysis Module - "+BIASLaunch.getSoftwareVersion();
			
			if (directory.contentEquals("tt_lastDirectoryReadFromA"))
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
				String tempMessageA = "\n\nSet to perform t-test analysis of results set from "+fileAAsString;
				String tempMessageB = "\nversus results set from "+fileBAsString+"\n";

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
		Boolean error = false;
		// Validate files
		if ((validateFiles(fullyQualifiedPathA, fullyQualifiedPathB, velocitySelected, trueDelayMinutesPer100TrainMilesSelected, elapsedRunTimePerTrainSelected, otpSelected)) == false)
		{
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
							alert.setContentText("There are errors in the t-test analysis!  \n\nSee log window.");

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
		}
		else
		{
			error = true;
			displayMessage("\nError in validating files");
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
		}

		//  Now reset for next case
		executeButton.setVisible(false);
		resetButton.setVisible(true);
		resetButton.setDisable(false);   
		TTestValidateTTest.resetGenerateValidation();
	}

	private Boolean validateFiles(String fullyQualifiedPathA, String fullyQualifiedPathB, SimpleBooleanProperty velocitySelected, SimpleBooleanProperty trueDelayMinutesPer100TrainMilesSelected, SimpleBooleanProperty elapsedRunTimePerTrainSelected, SimpleBooleanProperty otpSelected)
	{
		TTestValidateTTest validate = new TTestValidateTTest();
		Boolean error = TTestValidateTTest.validate(fullyQualifiedPathA, fullyQualifiedPathB, velocitySelected, trueDelayMinutesPer100TrainMilesSelected, elapsedRunTimePerTrainSelected, otpSelected);
		displayMessage(validate.getResultsMessage());
		if (!error)
		{
			requestGenerateVelocity = TTestValidateTTest.getGenerateVelocity();
			requestGenerateTrueDelayMinutesPer100TrainMiles = TTestValidateTTest.getGenerateTrueDelayMinutesPer100TrainMiles();

			if (TTestValidateTTest.getGenerateElapsedRunTimePerTrainInSeconds() == true)
			{
				requestGenerateElapsedRunTimePerTrain = true;
			}
			else if (TTestValidateTTest.getGenerateElapsedRunTimePerTrainAsSerial() == true)
			{
				requestGenerateElapsedRunTimePerTrain = true;
			}
			else if (TTestValidateTTest.getGenerateElapsedRunTimePerTrainAsString() == true)
			{
				requestGenerateElapsedRunTimePerTrain = true;
			}

			requestGenerateOtp = TTestValidateTTest.getGenerateOtp();
			requestGenerateType = TTestValidateTTest.getGenerateType();
			requestGenerateGroup = TTestValidateTTest.getGenerateGroup();
			
			progressBar.setProgress(.10);
		}
		return error;
	}

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

	public static double getCriticalT()
	{		
		return criticalT;
	}

	public static double getLOS()
	{		
		return levelOfSignificance;
	}
	
	public static int getTTestColumn1ColorIndex()
	{		
		return tTestColumn1ColorIndex;
	}
	
	public static int getTTestColumn2ColorIndex()
	{		
		return tTestColumn2ColorIndex;
	}
	
	public static int getTTestColumn3ColorIndex()
	{		
		return tTestColumn3ColorIndex;
	}
	
	public static int getTTestColumn4ColorIndex()
	{		
		return tTestColumn4ColorIndex;
	}
	
	public static int getTTestColumn5ColorIndex()
	{		
		return tTestColumn5ColorIndex;
	}

	public static void setCriticalT(double criticalTin)
	{		
		criticalT = criticalTin;
	}
}