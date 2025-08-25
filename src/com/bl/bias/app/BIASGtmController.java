package com.bl.bias.app;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.prefs.Preferences;

import com.bl.bias.analyze.GtmAnalysis;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.read.ReadGtmAnalysisFiles;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.write.WriteGTMFiles2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASGtmController 
{
	private static String fileAsString;
	private static String fullyQualifiedPath;

	private static String lineToAnalyze;

	private static String message = "";
	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;

	private static String simulationBeginDay = null;
	private static String simulationBeginTime = null;
	private static String simulationDuration = null;
	private static String warmUpDuration = null;
	private static String coolDownDuration = null;

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;

	BIASPreprocessLinesForGtmAnalysis getPrelimData;

	@FXML private ComboBox<String> selectLineComboBox;

	@FXML private Button selectFileButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label step1Label;
	@FXML private Label step2Label;
	@FXML private Label selectProjectFileLabel;
	@FXML private Label fileNameLabel;
	@FXML private Label selectLineLabel;

	@FXML private TextArea textArea;

	@FXML private ProgressBar progressBar;

	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");	
	}

	@FXML private void handleSelectFileButton(ActionEvent event) 
	{
		chooseFile();
	}

	@FXML private void handleExecuteButton(ActionEvent event) 
	{
		// Get location to save file to if not using system time as file name
		if (!BIASGeneralConfigController.getUseSerialTimeAsFileName())
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select Location to Save Results");

			// Check if previous location is available
			if ((prefs.get("gt_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("gt_lastDirectorySavedTo", null));
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
						prefs.put("gt_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				message = "\nStarting analysis of "+lineToAnalyze+" line at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				step1Label.setDisable(true);
				step2Label.setDisable(true);
				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				executeButton.setDisable(true);
				selectLineComboBox.setDisable(true);
				selectLineLabel.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				step1Label.setDisable(false);
				step2Label.setDisable(true);
				executeButton.setDisable(true);
				executeButton.setVisible(true);
				resetButton.setVisible(false);
				selectFileButton.setDisable(false);
				selectLineLabel.setDisable(true);
				selectLineComboBox.getItems().removeAll(getPrelimData.returnAvailableLines());
				selectLineComboBox.setDisable(true);
				fileNameLabel.setText("");
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("gt_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("gt_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
					directoryChooser.setInitialDirectory(path.toFile());
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				message = "\nStarting analysis of "+lineToAnalyze+" line at "+ConvertDateTime.getTimeStamp();
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("gt_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				step1Label.setDisable(true);
				step2Label.setDisable(true);
				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				executeButton.setDisable(true);
				selectLineComboBox.setDisable(true);
				selectLineLabel.setDisable(true);

				continueAnalysis = true;

				startTask();
			}
			else
			{
				//  Did not commit file to save so reset
				resetMessage();

				step1Label.setDisable(false);
				step2Label.setDisable(true);
				executeButton.setDisable(true);
				executeButton.setVisible(true);
				resetButton.setVisible(false);
				selectFileButton.setDisable(false);
				selectLineLabel.setDisable(true);
				selectLineComboBox.getItems().removeAll(getPrelimData.returnAvailableLines());
				selectLineComboBox.setDisable(true);
				fileNameLabel.setText("");
			}	
		}
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		resetMessage();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);

		step1Label.setDisable(false);
		step2Label.setDisable(true);
		selectProjectFileLabel.setDisable(false);
		executeButton.setVisible(true);
		resetButton.setVisible(false);
		selectFileButton.setDisable(false);
		selectLineLabel.setDisable(true);
		selectLineComboBox.getItems().removeAll(getPrelimData.returnAvailableLines());
		selectLineComboBox.setDisable(true);
		fileNameLabel.setText("");
	}

	@FXML private void handleSelectLineComboBox(ActionEvent event) 
	{
		lineToAnalyze = selectLineComboBox.getValue();
	}

	private void chooseFile()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"RTC Option Files", "*.OPTION");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// See if last directory is stored
		if ((prefs.get("gt_lastDirectoryForGtmAnalysis", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("gt_lastDirectoryForGtmAnalysis", null));

			if ((path.toFile().exists()) && (path !=null))
				fileChooser.setInitialDirectory(path.toFile());
		}

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) selectFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .OPTION file found
		if (file != null)
		{
			Boolean linkFileFound = false;
			Boolean lineFileFound = false;
			Boolean routeFileFound = false;

			// Reset comobox entries and checkbox status
			selectLineComboBox.getItems().clear();
			selectLineComboBox.setDisable(true);
			selectLineLabel.setDisable(true);
			executeButton.setDisable(true);

			// Write message
			clearMessage();
			message = "BIAS GTM Analysis Module - "+BIASLaunch.getSoftwareVersion()+"\n";

			// Store path for subsequent runs and set labels
			fileAsString = file.getName().toString();
			fullyQualifiedPath = file.toString();
			fileNameLabel.setText(fullyQualifiedPath);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("gt_lastDirectoryForGtmAnalysis", file.getParent());

			// Check that .LINK, .LINE, and .ROUTE files exist
			File linkFile = new File(file.getParent(), fileAsString.replace(".OPTION", ".LINK"));
			if (linkFile.exists())
				linkFileFound = true;
			else
				message += "\n.LINK file is missing!";

			File lineFile = new File(file.getParent(), fileAsString.replace(".OPTION", ".LINE"));
			if (lineFile.exists())
				lineFileFound = true;
			else
				message += "\n.LINE file is missing!";

			File routeFile = new File(file.getParent(), fileAsString.replace(".OPTION", ".ROUTE"));
			if (routeFile.exists())
				routeFileFound = true;
			else
				message += "\n.ROUTE file is missing!";

			if (linkFileFound && lineFileFound && routeFileFound)
			{
				// Reset
				simulationBeginDay = null;
				simulationBeginTime = null;
				simulationDuration = null;
				warmUpDuration = null;
				coolDownDuration = null;
				
				// Required files also found
				selectLineLabel.setDisable(false);
				selectLineComboBox.setDisable(false);
				step2Label.setDisable(false);

				// Check .LINE file to generate entries for combobox 
				getPrelimData = new BIASPreprocessLinesForGtmAnalysis(lineFile);
				if (getPrelimData.returnAvailableLines().size() > 0)
				{
					selectLineComboBox.getItems().addAll((getPrelimData.returnAvailableLines()));
					selectLineComboBox.getSelectionModel().select(0);

					// Gather data from .OPTION file to display prior to executing analysis
					Scanner scanner = null;

					String targetSequence0 = "Simulation begin day:";
					String targetSequence1 = "Simulation begin time (HH:MM):";
					String targetSequence2 = "Simulation duration (DD:HH:MM):";
					String targetSequence3 = "Warm-up statistical exclusion (DD:HH:MM):";
					String targetSequence4 = "Cool-down statistical exclusion (DD:HH:MM):";

					String lineFromFile;

					// Perform parse
					try 
					{
						scanner = new Scanner(file);

						while ((scanner.hasNextLine()) && ((simulationBeginDay == null) || (simulationBeginTime == null) || (simulationDuration == null) || (warmUpDuration == null) || (coolDownDuration == null)))
						{
							lineFromFile = scanner.nextLine();

							if (lineFromFile.contains(targetSequence0))
							{
								simulationBeginDay = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginDay()[1])).trim();
							}
							else if (lineFromFile.contains(targetSequence1))
							{
								simulationBeginTime = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationBeginTime()[1])).trim();
							}
							else if (lineFromFile.contains(targetSequence2))
							{
								simulationDuration = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[0]), Integer.valueOf(BIASParseConfigPageController.o_getSimulationDuration()[1])).trim();
							}
							else if (lineFromFile.contains(targetSequence3))
							{
								warmUpDuration = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getWarmUpExclusion()[1])).trim();
							}
							else if (lineFromFile.contains(targetSequence4))
							{
								coolDownDuration = lineFromFile.substring(Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[0]), Integer.valueOf(BIASParseConfigPageController.o_getCoolDownExclusion()[1])).trim();
							}
						}
					}
					catch (Exception e) 
					{
						ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
					}
					finally
					{
						scanner.close();
					}

					//  Ready to execute
					message += "\nSet to compute GTMs on "+fileAsString.replace(".OPTION", "");
					message += "\nstarting on "+simulationBeginDay;
					message += " at "+simulationBeginTime+" hh:mm";
					message += " for "+simulationDuration+" dd:hh:mm";
					message += " with a warm-up period of "+warmUpDuration+" dd:hh:mm";
					message += " and a cool-down period of "+coolDownDuration+" dd:hh:mm\n";
					
					executeButton.setDisable(false);
				}
				else
					message += "\n\nUnable to perform analysis due to no line definition(s)";	
			}
			else
				message += "\n\nUnable to perform analysis due to missing file(s)";

			displayMessage(message);
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
		// Check that .OPTION file has time formatted as DD:HH:MM:SS, no CSV delimiters in .ROUTE file, all nodes in .ROUTE file (rather than just event and delay)
		// and ENGLISH input units
		File optionFile = new File(fullyQualifiedPath);
		File optionFileFolder = new File(optionFile.getParent());
		BIASValidateOptionsAndINIFileSchemeA.bIASCheckOptionFiles(optionFileFolder);
		if (BIASValidateOptionsAndINIFileSchemeA.getOptionsFilesFormattedCorrectly())
		{
			message = "\nValidated date/time format, verbose .ROUTE file, output format and speed/distance units from .OPTION file\n";
			displayMessage(message);
		}
		else
		{
			message += "\nInvalid date/time format, verbose .ROUTE file, output format, speed/distance units, invalid .OPTION file and/or invalid count of .OPTION files\n";
			displayMessage(message);
			continueAnalysis = false;
		}

		// Check that not using alpha DOW in RTC.INI
		File iniFolder;
		if (BIASGeneralConfigController.getUseRtcFolderForIniFile())
			iniFolder = new File("C:\\RTC");
		else
			iniFolder = new File(optionFile.getParent());

		BIASValidateOptionsAndINIFileSchemeA.bIASCheckINIFile(iniFolder);
		if (BIASValidateOptionsAndINIFileSchemeA.getINIFileFormattedCorrectly())
		{
			message = "Validated DOW output format in .INI file\n";
			displayMessage(message);
		}
		else
		{
			message = "Invalid DOW output format in .INI file and/or invalid count of .INI files\n";
			displayMessage(message);
			continueAnalysis = false;
		}

		setProgressIndicator(0.05);

		if (continueAnalysis)
		{
			// Read all objects that are required for the GTM analysis
			ReadGtmAnalysisFiles readData = new ReadGtmAnalysisFiles(fullyQualifiedPath, lineToAnalyze);
			message = readData.getResultsMessage();
			displayMessage(message);

			setProgressIndicator(0.25);
			
			if (continueAnalysis)
			{
				GtmAnalysis gtmAnalysis = new GtmAnalysis(readData.returnNodesFromLineFile(), readData.returnLinksFromLinkFile(), readData.returnTraversalsFromRouteFile());
				message = gtmAnalysis.getResultsMessage();

				displayMessage(message);

				// Write results to spreadsheet
				writeFiles();
				if (!WriteGTMFiles2.getErrorFound())
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
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
		}

		//  Now reset for next case
		executeButton.setVisible(false);
		resetButton.setVisible(true);
		resetButton.setDisable(false);
	}

	private void writeFiles() 
	{
		WriteGTMFiles2 filesToWrite = new WriteGTMFiles2(textArea.getText(), fileAsString.replace(".OPTION", ""));
		displayMessage(filesToWrite.getResultsMessage2());
	}

	public static String getSimulationBeginDay()
	{
		return simulationBeginDay;
	}

	public static String getSimulationBeginTime()
	{
		return simulationBeginTime;
	}

	public static String getWarmUpDuration()
	{
		return warmUpDuration;
	}

	public static String getSimulationDuration()
	{
		return simulationDuration;
	}

	public static String getCoolDownDuration()
	{
		return coolDownDuration;
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

	public static String getAnalyzedLine()
	{
		return lineToAnalyze;
	}
}