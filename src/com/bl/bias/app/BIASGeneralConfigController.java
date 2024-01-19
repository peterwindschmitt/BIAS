package com.bl.bias.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import com.bl.bias.exception.ErrorShutdown;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BIASGeneralConfigController
{	
	private static Preferences prefs;

	private static Boolean useLastDirectory;
	private static Boolean useSerialTimeForFileName;
	private static Boolean useRtcFolderForIniFile;

	@FXML private Label ioSectionLabel;

	@FXML private CheckBox useLastDirectoryCheckbox;
	@FXML private CheckBox useSerialTimeForFileNameCheckbox;
	@FXML private CheckBox useRtcFolderForIniFileCheckbox;

	@FXML private Button updateInputFilesButton;
	@FXML private Button clearRegistryButton;

	@FXML private GridPane generalConfigGridPanePage1;
	@FXML private GridPane generalConfigGridPanePage2;

	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");

		// See if preference is stored for using last directory
		if (prefs.getBoolean("gc_useLastDirectory", true))
		{
			useLastDirectory = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gc_useLastDirectory", true);
			useLastDirectoryCheckbox.setSelected(true);
		}
		else
		{
			useLastDirectory = false;
			useLastDirectoryCheckbox.setSelected(false);
		}

		// See if preference is stored for using serial time file names or user-provided file names
		if (prefs.getBoolean("gc_useSerialTimeForFileName", true))
		{
			useSerialTimeForFileName = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gc_useSerialTimeForFileName", true);
			useSerialTimeForFileNameCheckbox.setSelected(true);
		}
		else
		{
			useSerialTimeForFileName = false;
			useSerialTimeForFileNameCheckbox.setSelected(false);
		}

		// See if preference is stored for using C:\RTC for .ini file
		if (prefs.getBoolean("gc_useRtcFolderForIniFile", true))
		{
			useRtcFolderForIniFile = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gc_useRtcFolderForIniFile", true);
			useRtcFolderForIniFileCheckbox.setSelected(true);
		}
		else
		{
			useRtcFolderForIniFile = false;
			useRtcFolderForIniFileCheckbox.setSelected(false);
		}
	};

	@FXML private void handleUseLastDirectoryCheckbox()
	{
		if (useLastDirectory)
		{
			useLastDirectory = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gc_useLastDirectory", false);
		}
		else
		{
			useLastDirectory = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gc_useLastDirectory", true);
		}
	}

	public static Boolean getUseLastDirectory()
	{
		return useLastDirectory;
	}

	@FXML private void handleUseSerialTimeForFileNameCheckbox()
	{
		if (useSerialTimeForFileName)
		{
			useSerialTimeForFileName = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gc_useSerialTimeForFileName", false);
		}
		else
		{
			useSerialTimeForFileName = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gc_useSerialTimeForFileName", true);
		}
	}

	public static Boolean getUseSerialTimeAsFileName()
	{
		return useSerialTimeForFileName;
	}

	UnaryOperator<Change> integerFilter3digits = change -> {
		String newText = change.getControlNewText();
		if (newText.matches("([0-9]{0,3})")) { 
			return change;
		}
		return null;
	};

	@FXML private void handleUseRtcFolderForIniFileCheckbox()
	{
		if (useRtcFolderForIniFile)
		{
			useRtcFolderForIniFile = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gc_useRtcFolderForIniFile", false);
		}
		else
		{
			useRtcFolderForIniFile = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("gc_useRtcFolderForIniFile", true);
		}
	}

	@FXML private void handleUpdateInputFilesButton(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"RTC .OPTION File", "*.OPTION");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) updateInputFilesButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .OPTION file found
		if (file != null)
		{
			String newOptionFile = "";

			Boolean check1Complete = false;
			Boolean check2Complete = false;
			Boolean check3Complete = false;
			Boolean check4Complete = false;
			Boolean check5Complete = false;

			Scanner scanner = null;

			try 
			{
				// For each line
				scanner = new Scanner(file);

				while (scanner.hasNextLine())  
				{
					String lineFromFile = scanner.nextLine();
					if (lineFromFile.contains("Summary report time format: ")) // Check 1
					{
						check1Complete = true;
						// "Summary report time format: DD:HH:MM:SS"
						String[] newLineName = lineFromFile.split(":");
						if (!newLineName[1].trim().equals("DD:HH:MM:SS"))
						{
							newOptionFile += lineFromFile.substring(0, Integer.valueOf(BIASParseConfigPageController.o_getSummaryReportTimeFormat()[0]))+"DD:HH:MM:SS\n";
							continue;
						}
					}
					else if (lineFromFile.contains("Units: "))  // Check 2
					{
						check2Complete = true;
						// "Units: ENGLISH"  Line 15
						String[] newLineName = lineFromFile.split(":");
						if (!newLineName[1].trim().equals("ENGLISH"))
						{
							newOptionFile += lineFromFile.substring(0, Integer.valueOf(BIASParseConfigPageController.o_getUnits()[0]))+"ENGLISH\n";
							continue;
						}
					}
					else if (lineFromFile.contains("Train route report in CSV format: ")) //Check 3  
					{
						check3Complete = true;
						// "Train route report in CSV format: NO"  Line 2275
						String[] newLineName = lineFromFile.split(":");
						if (!newLineName[1].trim().equals("NO"))
						{
							newOptionFile += lineFromFile.substring(0, Integer.valueOf(BIASParseConfigPageController.o_getCommaDelimited()[0]))+"NO \n";
							continue;
						}
					}
					else if (lineFromFile.contains("All nodes (vs event only) in route report: "))  // Check 4
					{
						check4Complete = true;
						// "All nodes (vs event only) in route report: YES"  Line 2274
						String[] newLineName = lineFromFile.split(":");
						if (!newLineName[1].trim().equals("YES"))
						{
							newOptionFile += lineFromFile.substring(0, Integer.valueOf(BIASParseConfigPageController.o_getAllNodesInRouteReport()[0]))+"YES\n";
							continue;
						}
					}
					else if (lineFromFile.contains("Show seed trains in route report: "))  // Check 5 -- Needed for recovery time analysis
					{
						check5Complete = true;
						// "Show seed trains in route report: YES"  Line 2272
						String[] newLineName = lineFromFile.split(":");
						if (!newLineName[1].trim().equals("YES"))
						{
							newOptionFile += lineFromFile.substring(0, Integer.valueOf(BIASParseConfigPageController.o_getShowSeedTrainsInRouteReport()[0]))+"YES\n";
							continue;
						}
					}
					newOptionFile += lineFromFile + "\n";
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

			if (check1Complete && (check2Complete) && (check3Complete) && (check4Complete) && (check5Complete))
			{
				try 
				{
					// Delete old .OPTION file
					File f= new File(file.toString());    
					f.delete();     

					// Write new .OPTION file
					FileWriter fileWriter;		
					fileWriter = new FileWriter(file);
					fileWriter.write(newOptionFile);
					fileWriter.close();	
				} 
				catch (IOException e) 
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName()+".  Unable to write new .OPTION file!");
				}

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Success!");
				alert.setHeaderText(null);
				alert.setContentText(file.getName()+" was updated.  Please rerun RTC case with new .OPTION file.");	
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
				alert.show();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Not all parameters in "+file.getName()+" were updated!!! Reverting to old .OPTION file.");	
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
				alert.show();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("File was NOT updated.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
			alert.show();
		}
	}

	@FXML private void handleClearRegistryButton(ActionEvent event) throws BackingStoreException
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("All saved BIAS elements in the System Registry will be removed!  BIAS must then be restarted.  Are you sure?");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			prefs.removeNode();
		}	
	}

	public static Boolean getUseRtcFolderForIniFile()
	{
		return useRtcFolderForIniFile;
	}
}