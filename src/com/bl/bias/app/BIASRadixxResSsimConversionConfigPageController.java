package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class BIASRadixxResSsimConversionConfigPageController
{	
	private static Preferences prefs;

	private static Boolean checkStasEqual;
	private static Boolean checkStdsEqualOrLaterThanStas;
	
	private static Boolean defaultCheckStasEqual = true;
	private static Boolean defaultCheckStdsEqualOrLaterThanStas = true;
	
	@FXML private CheckBox checkStasEqualCheckBox;
	@FXML private CheckBox checkStdsEqualOrLaterThanStasCheckBox;


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

		// See if preference is stored for checking if Passenger STD is at the same time or after Passenger STA and that Train STD is at the same time or after Train STA
		if (prefs.getBoolean("rs_checkStdsEqualOrLaterThanStas", defaultCheckStdsEqualOrLaterThanStas))
		{
			checkStdsEqualOrLaterThanStas = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_checkStdsEqualOrLaterThanStas", true);
			checkStdsEqualOrLaterThanStasCheckBox.setSelected(true);
		}
		else
		{
			checkStdsEqualOrLaterThanStas = false;
			checkStdsEqualOrLaterThanStasCheckBox.setSelected(false);
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

	@FXML private void handleCheckStdsEqualOrLaterThanStasCheckBox()
	{
		if (checkStdsEqualOrLaterThanStas)
		{
			checkStdsEqualOrLaterThanStas = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_checkStdsEqualOrLaterThanStas", false);
		}
		else
		{
			checkStdsEqualOrLaterThanStas = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_checkStdsEqualOrLaterThanStas", true);
		}
	}

	public static Boolean getCheckStdsAtSameTimeOrLaterThanStas()
	{
		return checkStdsEqualOrLaterThanStas;
	}
}