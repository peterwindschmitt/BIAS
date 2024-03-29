package com.bl.bias.app;

import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

public class BIASRadixxResSsimComparisonConfigPageController 
{
	private static Preferences prefs;

	@FXML private CheckBox type1CheckBox;
	@FXML private CheckBox type2CheckBox;
	@FXML private CheckBox type3CheckBox;
	@FXML private CheckBox type4CheckBox;
	@FXML private CheckBox type5CheckBox;

	@FXML private RadioButton type3AllAttributesRadioButton;
	@FXML private RadioButton type3LimitedAttributesRadioButton;

	private static Boolean checkType1Records;
	private static Boolean checkType2Records;
	private static Boolean checkType3Records;
	private static Boolean checkType4Records;
	private static Boolean checkType5Records;

	private static Boolean checkType3AllAttributes;
	private static Boolean checkType3LimitedAttributes;

	private static Boolean defaultCheckType1Records = true;
	private static Boolean defaultCheckType2Records = true;
	private static Boolean defaultCheckType3Records = true;
	private static Boolean defaultCheckType4Records = true;
	private static Boolean defaultCheckType5Records = true;

	private static Boolean defaultCheckType3AllAttributes = false;
	private static Boolean defaultCheckType3LimitedAttributes = true;

	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");

		// See if preference is stored for comparing Type 1 records in SSIM files
		if (prefs.getBoolean("rc_checkType1Records", defaultCheckType1Records))
		{
			checkType1Records = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType1Records", true);
			type1CheckBox.setSelected(true);
		}
		else
		{
			checkType1Records = false;
			type1CheckBox.setSelected(false);
		}

		// See if preference is stored for comparing Type 2 records in SSIM files
		if (prefs.getBoolean("rc_checkType2Records", defaultCheckType2Records))
		{
			checkType2Records = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType2Records", true);
			type2CheckBox.setSelected(true);
		}
		else
		{
			checkType2Records = false;
			type2CheckBox.setSelected(false);
		}

		// See if preference is stored for comparing Type 3 records in SSIM files
		if (prefs.getBoolean("rc_checkType3Records", defaultCheckType3Records))
		{
			checkType3Records = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType3Records", true);
			type3CheckBox.setSelected(true);

			type4CheckBox.setDisable(false);
		}
		else
		{
			checkType3Records = false;
			type3CheckBox.setSelected(false);

			type4CheckBox.setSelected(false);
			type4CheckBox.setDisable(true);
		}

		// See if preference is stored for comparing Type 4 records in SSIM files
		if (prefs.getBoolean("rc_checkType4Records", defaultCheckType4Records))
		{
			if (checkType3Records)
			{
				checkType4Records = true;
				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.putBoolean("rc_checkType4Records", true);
				type4CheckBox.setSelected(true);
			}
		}
		else
		{
			checkType4Records = false;
			type4CheckBox.setSelected(false);
		}

		// See if preference is stored for comparing Type 5 records in SSIM files
		if (prefs.getBoolean("rc_checkType5Records", defaultCheckType5Records))
		{
			checkType5Records = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType5Records", true);
			type5CheckBox.setSelected(true);
		}
		else
		{
			checkType5Records = false;
			type5CheckBox.setSelected(false);
		}

		// See if preference is stored for comparing Type 3 records (full or limited attributes) in SSIM files
		if (prefs.getBoolean("rc_checkType3RecordsAllAttributes", defaultCheckType3AllAttributes))
		{
			checkType3AllAttributes = true;
			checkType3LimitedAttributes = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType3RecordsAllAttributes", true);
			type3AllAttributesRadioButton.setSelected(true);
		}
		else
		{
			checkType3AllAttributes = false;
			checkType3LimitedAttributes = true;
			type3LimitedAttributesRadioButton.setSelected(true);
		}
	}

	@FXML private void handleType1CheckBox(ActionEvent event) throws IOException
	{
		if (checkType1Records)
		{
			checkType1Records = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType1Records", false);
		}
		else
		{
			checkType1Records = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType1Records", true);
		}
	}

	@FXML private void handleType2CheckBox(ActionEvent event) throws IOException
	{
		if (checkType2Records)
		{
			checkType2Records = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType2Records", false);
		}
		else
		{
			checkType2Records = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType2Records", true);
		}
	}

	@FXML private void handleType3CheckBox(ActionEvent event) throws IOException
	{
		if (checkType3Records)
		{
			checkType3Records = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType3Records", false);
			checkType4Records = false;

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType4Records", false);
			type4CheckBox.setSelected(false);
			type4CheckBox.setDisable(true);
		}
		else
		{
			checkType3Records = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType3Records", true);

			type4CheckBox.setDisable(false);
		}
	}

	@FXML private void handleType4CheckBox(ActionEvent event) throws IOException
	{
		if (checkType4Records)
		{
			checkType4Records = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType4Records", false);
		}
		else
		{
			checkType4Records = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType4Records", true);
		}
	}

	@FXML private void handleType5CheckBox(ActionEvent event) throws IOException
	{
		if (checkType5Records)
		{
			checkType5Records = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType5Records", false);
		}
		else
		{
			checkType5Records = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rc_checkType5Records", true);
		}
	}

	@FXML private void handleType3AllAttributesRadioButton(ActionEvent event) throws IOException
	{
		// Currently not enabled
	}

	@FXML private void handleType3LimitedAttributesRadioButton(ActionEvent event) throws IOException
	{
		// Enabled by default in constructor
	}

	public static Boolean getCheckType1Records()
	{
		return checkType1Records;
	}

	public static Boolean getCheckType2Records()
	{
		return checkType2Records;
	}

	public static Boolean getCheckType3Records()
	{
		return checkType3Records;
	}

	public static Boolean getCheckType4Records()
	{
		return checkType4Records;
	}

	public static Boolean getCheckType5Records()
	{
		return checkType5Records;
	}

	public static Boolean getType3LimitedAttributes()
	{
		return checkType3LimitedAttributes;
	}

	public static Boolean getType3AllAttributes()
	{
		return checkType3AllAttributes;
	}
}