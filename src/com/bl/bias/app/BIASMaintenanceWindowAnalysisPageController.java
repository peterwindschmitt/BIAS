package com.bl.bias.app;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.prefs.Preferences;

import org.controlsfx.control.CheckComboBox;

import com.bl.bias.analyze.MaintenanceWindowAnalysis;
import com.bl.bias.exception.ErrorShutdown;
import com.bl.bias.objects.MaintenanceWindowAnalysisLink;
import com.bl.bias.objects.MaintenanceWindowAnalysisSegment;
import com.bl.bias.read.ReadMaintenanceWindowAnalysisFiles;
import com.bl.bias.tools.ConvertDateTime;
import com.bl.bias.tools.PartitionLineIntoSubLines;
import com.bl.bias.write.WriteMaintenanceWindowFiles5;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox; 
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASMaintenanceWindowAnalysisPageController 
{
	private final Integer maximumLinksPermittedInLine = 500;
	private static String fileAsString;
	private static String fullyQualifiedPath;

	private static ArrayList<String> linesToAnalyze;

	private static ArrayList <MaintenanceWindowAnalysisSegment> segments;

	private static String simulationBeginDay = null;
	private static String simulationBeginTime = null;
	private static String simulationDuration = null;
	private static String warmUpDuration = null;
	private static String coolDownDuration = null;

	private static ArrayList<File> files;
	private static File[] filesAsList;
	private static File directory;
	private static File lastDirectory;
	private static File saveFileLocation;
	private static String saveFileLocationForUserSpecifiedFileName;
	private static String saveFileFolderForSerialFileName;

	private static String message = "";

	private static Preferences prefs;

	private static Boolean continueAnalysis = true;

	BIASPreprocessLinesForMaintenanceWindowAnalysis getPrelimData;

	@FXML private CheckComboBox<String> selectLineCheckComboBox;

	@FXML private CheckBox allLinesCheckBox;

	@FXML private Button selectFileButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label selectProjectFileLabel;
	@FXML private Label fileNameLabel;
	@FXML private Label selectLineLabel;

	@FXML private TextArea textArea;

	@FXML private ProgressBar progressBar;

	@FXML private void initialize() 
	{
		linesToAnalyze = new ArrayList<String>();
		prefs = Preferences.userRoot().node("BIAS");
		selectLineCheckComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) 
			{
				linesToAnalyze.clear();
				linesToAnalyze.addAll(selectLineCheckComboBox.getCheckModel().getCheckedItems());
				if (linesToAnalyze.size() == 0)
				{
					executeButton.setDisable(true);
				}
				else
				{
					executeButton.setDisable(false);
				}
			}
		});
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
			if ((prefs.get("mw_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("mw_lastDirectorySavedTo", null));
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
						prefs.put("mw_lastDirectorySavedTo", file.getParent());
				} 
				catch (Exception e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}

				message = "\nStarting analysis of "+linesToAnalyze.toString()+" line at "+ConvertDateTime.getTimeStamp()+" on "+ConvertDateTime.getDateStamp();
				displayMessage(message);

				progressBar.setVisible(true);
				setProgressIndicator(0.00);
				executeButton.setDisable(true);
				selectFileButton.setDisable(true);
				selectProjectFileLabel.setDisable(true);
				selectLineLabel.setDisable(true);
				allLinesCheckBox.setDisable(true);
				selectLineCheckComboBox.setDisable(true);

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
				selectLineLabel.setDisable(true);
				selectLineCheckComboBox.getItems().removeAll(getPrelimData.returnAllAvailableLines());
				selectLineCheckComboBox.setDisable(true);
				allLinesCheckBox.setDisable(true);
				fileNameLabel.setText("");
			}	
		}
		else
		{
			DirectoryChooser directoryChooser = new DirectoryChooser();

			// See if last location is stored
			if ((prefs.get("mw_lastDirectorySavedTo", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
			{
				Path path = Paths.get(prefs.get("mw_lastDirectorySavedTo", null));
				if ((path.toFile().exists()) && (path !=null))
					directoryChooser.setInitialDirectory(path.toFile());
			}

			directoryChooser.setTitle("Select Folder");

			Stage stageForFolderChooser = (Stage) executeButton.getScene().getWindow();

			File directory = directoryChooser.showDialog(stageForFolderChooser);
			if (directory != null)
			{
				message = "\nStarting analysis of "+linesToAnalyze.toString()+" line(s) at "+ConvertDateTime.getTimeStamp()+" on "+ConvertDateTime.getDateStamp();
				displayMessage(message);

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("mw_lastDirectorySavedTo", directory.toString());

				saveFileFolderForSerialFileName = directory.toString();

				progressBar.setVisible(true);
				setProgressIndicator(0.00);
				executeButton.setDisable(true);
				selectFileButton.setDisable(true);
				selectLineLabel.setDisable(true);
				allLinesCheckBox.setDisable(true);
				selectLineCheckComboBox.setDisable(true);
				selectProjectFileLabel.setDisable(true);

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
				selectLineLabel.setDisable(true);
				selectLineCheckComboBox.getItems().removeAll(getPrelimData.returnAllAvailableLines());
				selectLineCheckComboBox.setDisable(true);
				allLinesCheckBox.setDisable(true);
				fileNameLabel.setText("");
			}	
		}
	}

	@FXML private void handleResetButton(ActionEvent event) 
	{
		resetMessage();
		ReadMaintenanceWindowAnalysisFiles.clearMaintenanceWindowAnalysisSegments();

		progressBar.setVisible(false);
		setProgressIndicator(0.00);
		linesToAnalyze.clear();

		executeButton.setVisible(true);
		resetButton.setVisible(false);
		allLinesCheckBox.setDisable(true);
		allLinesCheckBox.setSelected(false);
		selectFileButton.setDisable(false);
		selectLineLabel.setDisable(true);
		selectLineCheckComboBox.getCheckModel().clearChecks();
		selectLineCheckComboBox.getItems().removeAll(getPrelimData.returnAllAvailableLines());
		selectLineCheckComboBox.setDisable(true);
		allLinesCheckBox.setDisable(true);
		fileNameLabel.setText("");
	}

	@FXML private void handleAllLinesCheckBox(ActionEvent event) 
	{
		if (allLinesCheckBox.isSelected())
		{
			// Fill checkComboBox with all lines starting with MOW
			selectLineCheckComboBox.getCheckModel().clearChecks();
			for (int i = 0; i < getPrelimData.returnAvailableLinesStartingWithMOW().size(); i++)
				selectLineCheckComboBox.getCheckModel().check(getPrelimData.returnAvailableLinesStartingWithMOW().get(i));
			selectLineCheckComboBox.setDisable(true);
		}
		else
		{
			selectLineCheckComboBox.setDisable(false);
		}
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
		if ((prefs.get("mw_lastDirectoryForMaintenanceWindowAnalysis", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("mw_lastDirectoryForMaintenanceWindowAnalysis", null));

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
			Boolean nodeFileFound = false;
			Boolean routeFileFound = false;

			// Reset comobox entries and checkbox status
			selectLineCheckComboBox.getCheckModel().clearChecks();
			selectLineCheckComboBox.getItems().clear();
			selectLineCheckComboBox.setDisable(true);
			executeButton.setDisable(true);
			allLinesCheckBox.setSelected(false);
			allLinesCheckBox.setDisable(true);

			// Write message
			clearMessage();
			message = "BIAS Maintenance Window Analysis Module - "+BIASLaunch.getSoftwareVersion()+"\n";

			// Store path for subsequent runs and set labels
			fileAsString = file.getName().toString();
			fullyQualifiedPath = file.toString();
			fileNameLabel.setText(fullyQualifiedPath);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mw_lastDirectoryForMaintenanceWindowAnalysis", file.getParent());

			// Check that .LINK, .LINE, .NODE and .ROUTE files exist
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

			File nodeFile = new File(file.getParent(), fileAsString.replace(".OPTION", ".NODE"));
			if (nodeFile.exists())
				nodeFileFound = true;
			else
				message += "\n.NODE file is missing!";

			File routeFile = new File(file.getParent(), fileAsString.replace(".OPTION", ".ROUTE"));
			if (routeFile.exists())
				routeFileFound = true;
			else
				message += "\n.ROUTE file is missing!";

			if (linkFileFound && lineFileFound && nodeFileFound && routeFileFound)
			{
				// Required files also found
				selectLineLabel.setDisable(false);
				selectLineCheckComboBox.setDisable(false);
				allLinesCheckBox.setDisable(false);

				// Check .LINE file to generate entries for checkcombobox 
				getPrelimData = new BIASPreprocessLinesForMaintenanceWindowAnalysis(lineFile);
				if (getPrelimData.returnAllAvailableLines().size() > 0)
				{
					selectLineCheckComboBox.getItems().addAll((getPrelimData.returnAllAvailableLines()));

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
					message += "\nSet to perform maintenance window analysis on "+fileAsString.replace(".OPTION", "");
					message += "\nstarting on "+simulationBeginDay;
					message += " at "+simulationBeginTime+" hh:mm";
					message += " for "+simulationDuration+" dd:hh:mm";
					message += " with a warm-up period of "+warmUpDuration+" dd:hh:mm";
					message += " , a cool-down period of "+coolDownDuration+" dd:hh:mm";
					message += " and an exclusion period of "+BIASMaintenanceWindowAnalysisConfigPageController.getResultsExclusionPeriodInHours()+" hh\n";
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
		// Check that .OPTION file has time formatted as DD:HH:MM:SS, no CSV delimiters in .ROUTE file, no seed trains in .ROUTE, all nodes in .ROUTE file (rather than just event and delay)
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
			message = "\nInvalid date/time format, verbose .ROUTE file, output format, speed/distance units, invalid .OPTION file and/or invalid count of .OPTION files\n";
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

		if (continueAnalysis)
		{
			// Read all objects that are required for the maintenance window analysis
			ReadMaintenanceWindowAnalysisFiles readData = new ReadMaintenanceWindowAnalysisFiles(fullyQualifiedPath, linesToAnalyze);
			message = readData.getResultsMessage();
			displayMessage(message+"\n");

			setProgressIndicator(0.15);

			segments = new ArrayList <MaintenanceWindowAnalysisSegment>();
			segments.addAll(readData.getMaintenanceWindowAnalysisSegments());
			for (int i = 0; i < segments.size(); i++)
			{
				// For each segment:

				// Partition the sublines (e.g., tracks) from each line.  To determine a subline all links of category turnout or crossover must be ignored.
				// Each link should be tested to see if it is contiguous with an existing subline.  If yes, assign it to the existing subline.  If no, create
				// a new subline.  

				ArrayList<MaintenanceWindowAnalysisLink> reducedLinksInLine = new ArrayList <MaintenanceWindowAnalysisLink>();

				Integer crossoverLinkCount = 0;
				Integer turnoutLinkCount = 0;

				Iterator<MaintenanceWindowAnalysisLink> itrPartitionLine = segments.get(i).getLinksInLine().iterator();
				while (itrPartitionLine.hasNext())
				{
					MaintenanceWindowAnalysisLink linkToConsider = itrPartitionLine.next();
					if (linkToConsider.getLinkClass().equals("Crossover")) // Ignore link
					{
						crossoverLinkCount++;
					}
					else if (linkToConsider.getLinkClass().equals("Turnout")) // Ignore link
					{
						turnoutLinkCount++;
					}
					else // Add link to hashset
					{
						reducedLinksInLine.add(linkToConsider);
					}
				}

				if (reducedLinksInLine.size() > maximumLinksPermittedInLine)
				{
					displayMessage("Found an unreasonable number ("+reducedLinksInLine.size()+") of eligible links in defined line!\n");
					continueAnalysis = false;
					break;
				}
				else if (reducedLinksInLine.size() == 0)
				{
					displayMessage("Found no links in defined line!\n");
					continueAnalysis = false;
					break;
				}
				else
				{
					// Partition the reducedLinksInLine
					PartitionLineIntoSubLines partition = new PartitionLineIntoSubLines(reducedLinksInLine);

					// Load back to segment object
					segments.get(i).setReducedLinksInLine(reducedLinksInLine);
					segments.get(i).setSubLines(partition.getSubLines());

					if (partition.getSubLines().size() > 2)
					{
						displayMessage("Found more than two sub-lines in defined line!\n");
						continueAnalysis = false;
						break;
					}
					else
					{
						message = "Removed "+crossoverLinkCount+" crossover links, "+turnoutLinkCount+" turnout links ";
						message += "and retained "+reducedLinksInLine.size()+" links in "+segments.get(i).getLineName()+" line\n";
						message += "which were partitioned into "+partition.getSubLines().size()+" sub-lines\n";
						displayMessage(message);

						setProgressIndicator(0.25);
					}
				}
			}
		}
		else
		{
			displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
		}		

		//  Perform Analysis
		if (continueAnalysis)
		{
			MaintenanceWindowAnalysis analyze = new MaintenanceWindowAnalysis(segments);
			displayMessage(analyze.getResultsMessage());
		}

		// Write Results
		if (continueAnalysis)
		{
			writeFiles();
			if (!WriteMaintenanceWindowFiles5.getErrorFound())
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

		// Prepare for next run
		executeButton.setVisible(false);
		resetButton.setVisible(true);
		resetButton.setDisable(false);
	}

	private void writeFiles() 
	{
		WriteMaintenanceWindowFiles5 filesToWrite = new WriteMaintenanceWindowFiles5(textArea.getText(), saveFileLocationForUserSpecifiedFileName);
		displayMessage(filesToWrite.getWriteResultsMessage5());
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
		return linesToAnalyze.toString();
	}

	public static ArrayList<MaintenanceWindowAnalysisSegment> getSegments()
	{
		return segments;
	}
}