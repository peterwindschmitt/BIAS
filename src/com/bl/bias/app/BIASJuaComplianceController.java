package com.bl.bias.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import com.bl.bias.read.ReadJuaComplianceFiles;
import com.bl.bias.analyze.AnalyzeJuaComplianceFiles;
import com.bl.bias.exception.ErrorShutdown;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASJuaComplianceController 
{
	@FXML private Button fileLocationButton;
	@FXML private Button executeButton;
	@FXML private Button resetButton;

	@FXML private Label fileNameLabel;

	@FXML private TextArea textArea;

	@FXML private ProgressBar progressBar;

	private static Preferences prefs;

	private static String fileAsString;
	private static String fullyQualifiedPath;
	private static String message = "";

	@FXML private void initialize() throws IOException
	{
		prefs = Preferences.userRoot().node("BIAS");	
	}

	
	@FXML private void handleExecuteButton(ActionEvent event) throws IOException 
	{
		startTask();
	}

	@FXML private void handleResetButton(ActionEvent event) throws IOException 
	{

	}

	@FXML private void handleFileLocationButton(ActionEvent event) throws IOException 
	{
		chooseFile();
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
		// Read all objects that are required for the recovery rate analysis
		message = "\nFor case "+fileAsString.replace(".OPTION", "")+":";
		displayMessage(message);
		ReadJuaComplianceFiles readData = new ReadJuaComplianceFiles(fullyQualifiedPath);
		message = readData.getResultsMessage();
		displayMessage(message);
				
		if (readData.getFormattedCorrectly())
		{
			setProgressIndicator(0.33);
				
			if (ReadJuaComplianceFiles.getTrainsToAnalyzeForCompliance().size() > 0)
			{
				//  Perform Analysis
				AnalyzeJuaComplianceFiles analyzeData = new AnalyzeJuaComplianceFiles();
				message = analyzeData.getResultsMessage();
				displayMessage(message);
	
				setProgressIndicator(0.66);
	
				// Write Results
				/*writeFiles();
				if (!WriteGradeXingFiles4.getErrorFound())
				{
					setProgressIndicator(1.0);
					displayMessage("\n*** PROCESSING COMPLETE ***");
				}
				else
				{
					displayMessage("\nError in writing files");
					displayMessage("\n*** PROCESSING NOT COMPLETE!!! ***");
				}*/
			}
			else
			{
				message += "\nUnable to perform analysis due to no trains in read in";
				displayMessage("*** PROCESSING NOT COMPLETE!!! ***");
			}
		}
		else
		{
			displayMessage("*** PROCESSING NOT COMPLETE!!! ***");
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
		if ((prefs.get("ju_lastDirectoryForJuaCompliance", null) != null) && (BIASGeneralConfigController.getUseLastDirectory()))
		{
			Path path = Paths.get(prefs.get("ju_lastDirectoryForJuaCompliance", null));

			if ((path.toFile().exists()) && (path !=null))
				fileChooser.setInitialDirectory(path.toFile());
		}

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) fileLocationButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .OPTION file found
		if (file != null)
		{
			Boolean trainFileFound = false;

			executeButton.setDisable(true);

			// Write message
			clearMessage();
			message = "BIAS JUA Compliance Module - "+BIASLaunch.getSoftwareVersion()+"\n";

			// Store path for subsequent runs and set labels
			fileAsString = file.getName().toString();
			fullyQualifiedPath = file.toString();
			fileNameLabel.setText(fullyQualifiedPath);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastDirectoryForJuaCompliance", file.getParent());

			// Check that .TRAIN file exists
			File trainFile = new File(file.getParent(), fileAsString.replace(".OPTION", ".TRAIN"));
			if (trainFile.exists())
				trainFileFound = true;
			else
				message += "\n.TRAIN file is missing!";

			if (trainFileFound)
			{
				executeButton.setDisable(false);
			}
			else
				message += "\n\nUnable to perform analysis due to missing file(s)";

			displayMessage(message);
		}
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
}