package com.bl.bias.app;

import java.util.function.UnaryOperator;
import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;

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

	@FXML private Button generalNextPageButton;
	@FXML private Button generalPreviousPageButton;

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

	public static Boolean getUseRtcFolderForIniFile()
	{
		return useRtcFolderForIniFile;
	}
}