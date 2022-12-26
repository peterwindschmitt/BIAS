package com.bl.bias.app;

import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BIASRadixxResSsimConversionConfigPageController
{	
	private static Preferences prefs;

	private static Boolean checkStasEqual;
	private static Boolean checkStasEqualOrLaterThanStds;
	private static String permittedLocationCodes;

	private static Boolean defaultCheckStasEqual = true;
	private static Boolean defaultCheckStasEqualOrLaterThanStds = true;
	private static String defaultPermittedLocationCodes = "WPT,RRN,FBT,AVE,EKW"; 

	@FXML private CheckBox checkStasEqualCheckBox;
	@FXML private CheckBox checkStasEqualOrLaterThanStdsCheckBox;

	@FXML private Button updateLocationCodesButton;
	@FXML private Button useLastSavedLocationCodesButton;

	@FXML private TextField permissibleLocationCodesTextField;

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

		// See if location codes are stored
		boolean permittedLocationCodesExists = prefs.get("rs_permittedLocationCodes", null) != null;
		if (permittedLocationCodesExists)
		{
			permissibleLocationCodesTextField.setText(prefs.get("rs_permittedLocationCodes", defaultPermittedLocationCodes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("rs_permittedLocationCodes", defaultPermittedLocationCodes);
			permissibleLocationCodesTextField.setText(prefs.get("rs_permittedLocationCodes", defaultPermittedLocationCodes));
		}
		permittedLocationCodes = prefs.get("rs_permittedLocationCodes", defaultPermittedLocationCodes);
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

	@FXML private void handleUpdateLocationCodesButton()
	{
		validateLocationCodes();
	}
	
	@FXML private void handleUseLastSavedLocationCodesButton()
	{
		permissibleLocationCodesTextField.setText(permittedLocationCodes);
		permissibleLocationCodesTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedTextField()
	{
		permissibleLocationCodesTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}
	
	public static String getPermittedLocationCodes()
	{
		return permittedLocationCodes;
	}

	private void validateLocationCodes()
	{
		Boolean locationsCorrectlyFormatted = true;

		Integer validLocationCount = 0;

		String textToValidate = permissibleLocationCodesTextField.getText();
		String formattedLocationList = "";

		List<String> inputtedLocationList = Arrays.asList(textToValidate.split("\\s*,\\s*"));

		for (int i=0; i < inputtedLocationList.size(); i++)
		{
			if (inputtedLocationList.get(i).length() != 3)
			{
				locationsCorrectlyFormatted = false;
				break;
			}
			else
			{
				validLocationCount++;
				if (validLocationCount == 1)
				{
					formattedLocationList += inputtedLocationList.get(i).trim().toUpperCase();
				}
				else
				{	
					formattedLocationList += ","+inputtedLocationList.get(i).trim().toUpperCase();
				}
			}
		}

		if (locationsCorrectlyFormatted == false)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("At least one location code is not formatted properly!  Please try again.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();
		}
		else
		{
			permittedLocationCodes = formattedLocationList;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("rs_permittedLocationCodes", formattedLocationList);
			
			permissibleLocationCodesTextField.setText(formattedLocationList);
			permissibleLocationCodesTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
	}
}