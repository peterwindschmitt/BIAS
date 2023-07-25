package com.bl.bias.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
import java.util.prefs.Preferences;

import com.bl.bias.analyze.SSIMComparisonAnalysis;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadRadixxResSSIMFileForComparison;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.write.WriteSSIMComparisonFile;

import javafx.application.Platform;
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

	private static ReadRadixxResSSIMFileForComparison readDataA;
	private static ReadRadixxResSSIMFileForComparison readDataB;
	
	SSIMComparisonAnalysis scheduleComparison;

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

		// Bind Execute Button to B
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
		if ((BIASRadixxResSsimComparisonConfigPageController.getCheckType1Records()) || (BIASRadixxResSsimComparisonConfigPageController.getCheckType2Records()) ||
				(BIASRadixxResSsimComparisonConfigPageController.getCheckType3Records()) || (BIASRadixxResSsimComparisonConfigPageController.getCheckType4Records()) || (BIASRadixxResSsimComparisonConfigPageController.getCheckType5Records())) 
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

					// Bind Execute Button to B
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

					// Bind Execute Button to B
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
		else
		{	
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText(null);
					alert.setContentText("No comparisons are requested.  Select record types to be compared in the module's configuration settings!");

					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));

					alert.showAndWait();
				}
			});
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

		// Bind Execute Button to B
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

				// Enable selections for execute button
				bSelected.set(true);
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
		if ((fileAAsString != null) && (fileBAsString != null))
		{
			clearMessage();
			
			String tempMessageA;
			tempMessageA = "\n\nSet to compare Radixx Res SSIM IATA file "+fileAAsString+" (file A) ";
			String tempMessageB;
			tempMessageB = "\nversus Radixx Res SSIM IATA file "+fileBAsString+" (file B)\n";
			
			message = "BIAS Radixx Res SSIM Comparison Module - "+BIASLaunch.getSoftwareVersion();
			message += tempMessageA;
			message += tempMessageB;
		}

		displayMessage(message);
		
		Boolean error = false;

		// Check if A and B are pointing to the same file
		if (fullyQualifiedPathA.equals(fullyQualifiedPathB))
		{
			error = true;
			displayMessage("\nInput files must be different");
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
		}

		// Read data
		if (error == false)
		{
			if (readData(fullyQualifiedPathA, fullyQualifiedPathB) == true)
			{
				error = true;
				displayMessage("\nError in reading files");
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

		// Analyze data
		if (error == false)
		{
			if(analyzeData() == false)
			{
				//	Write data
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

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error Dialog");
							alert.setHeaderText(null);
							alert.setContentText("There are errors writing the comparison log!  \n\nSee log window.");

							Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
							stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));

							alert.showAndWait();
						}
					});
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
						alert.setContentText("There are errors analyzing the SSIM files!  \n\nSee log window.");

						Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));

						alert.showAndWait();
					}
				});
			}
		}

		//  Now reset for next case
		executeButton.setVisible(false);
		resetButton.setVisible(true);
		resetButton.setDisable(false);   
	}

	private Boolean readData(String fullyQualifiedPathA, String fullyQualifiedPathB)
	{
		readDataA = new ReadRadixxResSSIMFileForComparison();
		Boolean errorReadingA = readDataA.read(fullyQualifiedPathA, "A");
		progressBar.setProgress(.10);

		readDataB = new ReadRadixxResSSIMFileForComparison();
		Boolean errorReadingB = readDataB.read(fullyQualifiedPathB, "B");
		progressBar.setProgress(.20);

		displayMessage(readDataA.getResultsMessage());
		displayMessage(readDataB.getResultsMessage());

		if ((errorReadingA) || (errorReadingB))
			return true;
		else 
			return false;
	}

	private Boolean analyzeData()
	{
		message = "\nStarted analyzing files at "+ConvertDateTime.getTimeStamp()+"\n";
		displayMessage(message);

		scheduleComparison = new SSIMComparisonAnalysis(fileAAsString, fileBAsString, dirAAsString, dirBAsString, readDataA, readDataB, BIASRadixxResSsimComparisonConfigPageController.getCheckType1Records(), BIASRadixxResSsimComparisonConfigPageController.getCheckType2Records(),
				BIASRadixxResSsimComparisonConfigPageController.getCheckType3Records(), BIASRadixxResSsimComparisonConfigPageController.getCheckType4Records(), BIASRadixxResSsimComparisonConfigPageController.getCheckType5Records(), BIASRadixxResSsimComparisonConfigPageController.getType3AllAttributes(), BIASRadixxResSsimComparisonConfigPageController.getType3LimitedAttributes());

		Boolean errorAnalyzing = scheduleComparison.analyze();

		displayMessage(scheduleComparison.getResultsMessage());

		if (!errorAnalyzing)
		{
			progressBar.setProgress(.80);

			message = "\nFinished analyzing files at "+ConvertDateTime.getTimeStamp()+"\n";
			displayMessage(message);
		}
		else 
		{
			progressBar.setProgress(.50);

			message = "\nError encountered analyzing files at "+ConvertDateTime.getTimeStamp()+"\n";
			displayMessage(message);
		}

		return errorAnalyzing;
	}

	private Boolean writeResults()
	{
		message = "\nStarted writing results file at "+ConvertDateTime.getTimeStamp();
		
		displayMessage(message);
		
		Boolean errorWriting = false;

		try
		{
			new WriteSSIMComparisonFile(scheduleComparison.getTextForComparisonFile());
		}
		catch (Exception e)
		{
			errorWriting = true;
		}

		if (!errorWriting)
		{
			progressBar.setProgress(1);

			message = "\nFinished writing results file at "+ConvertDateTime.getTimeStamp()+"\n";
			displayMessage(message);
		}
		else 
		{
			progressBar.setProgress(.80);

			message = "\nError encountered writing results file at "+ConvertDateTime.getTimeStamp()+"\n";
			displayMessage(message);
		}

		return errorWriting;
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