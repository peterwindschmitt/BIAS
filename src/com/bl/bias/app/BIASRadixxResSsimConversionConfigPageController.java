package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class BIASRadixxResSsimConversionConfigPageController
{	
	private static Preferences prefs;

	private static Boolean checkStasEqual;
	private static Boolean checkStasEqualOrLaterThanStds;
	
	private static Boolean defaultCheckStasEqual = true;
	private static Boolean defaultCheckStasEqualOrLaterThanStds = true;
	
	@FXML private CheckBox checkStasEqualCheckBox;
	@FXML private CheckBox checkStasEqualOrLaterThanStdsCheckBox;


	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");
		
		// See if preference is stored for checking if Passenger and Train STAs are equal
		if (prefs.getBoolean("rs_checkStasEqual", defaultCheckStasEqual))
		{
			checkStasEqual = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_checkStasEqual", true);
			checkStasEqualCheckBox.setSelected(true);
		}
		else
		{
			checkStasEqual = false;
			checkStasEqualCheckBox.setSelected(false);
		}

		// See if preference is stored for checking if Passenger STA is at the same time or after Passenger STD and that Train STA is at the same time or after Train STD
		if (prefs.getBoolean("rs_checkStasEqualOrLaterThanStds", defaultCheckStasEqualOrLaterThanStds))
		{
			checkStasEqualOrLaterThanStds = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_checkStasEqualOrLaterThanStds", true);
			checkStasEqualOrLaterThanStdsCheckBox.setSelected(true);
		}
		else
		{
			checkStasEqualOrLaterThanStds = false;
			checkStasEqualOrLaterThanStdsCheckBox.setSelected(false);
		}
	};

	@FXML private void handleCheckStasEqualCheckBox()
	{
		if (checkStasEqual)
		{
			checkStasEqual = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_checkStasEqual", false);
		}
		else
		{
			checkStasEqual = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_checkStasEqual", true);
		}
	}

	public static Boolean getCheckStasEqual()
	{
		return checkStasEqual;
	}

	@FXML private void handleCheckStasEqualOrLaterThanStdsCheckBox()
	{
		if (checkStasEqualOrLaterThanStds)
		{
			checkStasEqualOrLaterThanStds = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_checkStasEqualOrLaterThanStds", false);
		}
		else
		{
			checkStasEqualOrLaterThanStds = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_checkStasEqualOrLaterThanStds", true);
		}
	}

	public static Boolean getCheckStasAtSameTimeOrLaterThanStds()
	{
		return checkStasEqualOrLaterThanStds;
	}
}