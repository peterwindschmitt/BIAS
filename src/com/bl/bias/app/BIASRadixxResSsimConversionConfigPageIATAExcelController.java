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

public class BIASRadixxResSsimConversionConfigPageIATAExcelController
{	
	private static Preferences prefs;

	private static Boolean checkStasEqual;
	private static Boolean enforceTrainsInOrder;
	private static Boolean enforceValidityStartDate;
	private static String permittedLocationCodes;
	private static String permittedTraversalTimes;

	private static Boolean defaultCheckStasEqual = true;
	private static Boolean defaultEnforceTrainsInOrder = true;
	private static Boolean defaultEnforceValidityStartDate = true;
	private static String defaultPermittedLocationCodes = "WPT,RRN,FBT,AVE,EKW"; 
	private static String defaultPermittedTraversalTimes = "14,16,17,22,27,30,35,40";

	@FXML private CheckBox checkStasEqualCheckBox;
	@FXML private CheckBox enforceTrainNumbersInOrderCheckBox;
	@FXML private CheckBox enforceValidityStartDateCheckBox;

	@FXML private Button updateLocationCodesButton;
	@FXML private Button useLastSavedLocationCodesButton;
	@FXML private Button updateTraversalTimesButton;
	@FXML private Button useLastTraversalTimesButton;

	@FXML private TextField permissibleLocationCodesTextField;
	@FXML private TextField permissibleTraversalTimesTextField;

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

		// See if preference is stored for checking if trains are in order
		if (prefs.getBoolean("rs_enforceTrainsInOrder", defaultEnforceTrainsInOrder))
		{
			enforceTrainsInOrder = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_enforceTrainsInOrder", true);
			enforceTrainNumbersInOrderCheckBox.setSelected(true);
		}
		else
		{
			enforceTrainsInOrder = false;
			enforceTrainNumbersInOrderCheckBox.setSelected(false);
		}

		// See if preference is stored for enforcing SSIM validity start date
		if (prefs.getBoolean("rs_enforceValidityStartDate", defaultEnforceValidityStartDate))
		{
			enforceValidityStartDate = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_enforceValidityStartDate", true);
			enforceValidityStartDateCheckBox.setSelected(true);
		}
		else
		{
			enforceValidityStartDate = false;
			enforceValidityStartDateCheckBox.setSelected(false);
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

		// See if traversal times are stored
		boolean permittedTraversalTimesExist = prefs.get("rs_permittedTraversalTimes", null) != null;
		if (permittedTraversalTimesExist)
		{
			permissibleTraversalTimesTextField.setText(prefs.get("rs_permittedTraversalTimes", defaultPermittedTraversalTimes));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("rs_permittedTraversalTimes", defaultPermittedTraversalTimes);
			permissibleTraversalTimesTextField.setText(prefs.get("rs_permittedTraversalTimes", defaultPermittedTraversalTimes));
		}
		permittedTraversalTimes = prefs.get("rs_permittedTraversalTimes", defaultPermittedTraversalTimes);
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

	@FXML private void handleEnforceTrainNumbersInOrderCheckBox()
	{
		if (enforceTrainsInOrder)
		{
			enforceTrainsInOrder = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_enforceTrainsInOrder", false);
		}
		else
		{
			enforceTrainsInOrder = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_enforceTrainsInOrder", true);
		}
	}

	@FXML private void handleEnforceValidityStartDateCheckBox()
	{
		if (enforceValidityStartDate)
		{
			enforceValidityStartDate = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_enforceValidityStartDate", false);
		}
		else
		{
			enforceValidityStartDate = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("rs_enforceValidityStartDate", true);
		}
	}

	public static Boolean getCheckStasEqual()
	{
		return checkStasEqual;
	}

	public static Boolean getEnforceTrainsInOrder()
	{
		return enforceTrainsInOrder;
	}
	
	public static Boolean getEnforceValidityStartDate()
	{
		return enforceValidityStartDate;
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

	@FXML private void handleTextChangedPermissibleLocationsTextField()
	{
		permissibleLocationCodesTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedPermissibleTraversalTimesTextField()
	{
		permissibleTraversalTimesTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleUpdateTraversalTimesButton()
	{
		validateTraversalTimes();
	}

	@FXML private void handleUseLastSavedTraversalTimesButton()
	{
		permissibleTraversalTimesTextField.setText(permittedTraversalTimes);
		permissibleTraversalTimesTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
	}

	public static String getPermittedLocationCodes()
	{
		return permittedLocationCodes;
	}

	public static String getPermittedTraversalTimes()
	{
		return permittedTraversalTimes;
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
			if (inputtedLocationList.get(i) == "")
				continue;

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

	private void validateTraversalTimes()
	{
		Boolean traversalTimesCorrectlyFormatted = true;

		Integer validTraversalTimesCount = 0;

		String textToValidate = permissibleTraversalTimesTextField.getText();
		String formattedTraversalTimesList = "";
		List<String> inputtedTraversalTimesList = Arrays.asList(textToValidate.split(",+\\s*"));

		for (int i=0; i < inputtedTraversalTimesList.size(); i++)
		{
			if (inputtedTraversalTimesList.get(i) == "")
				continue;

			char[] traversalTime = inputtedTraversalTimesList.get(i).toCharArray();

			for (int j = 0; j < traversalTime.length; j++)
			{

				if(!Character.isDigit(traversalTime[j]))
				{
					traversalTimesCorrectlyFormatted = false;
					break;
				}				
			}

			if ((traversalTimesCorrectlyFormatted == true) && (traversalTime.length > 0))
			{
				validTraversalTimesCount++;
				if (validTraversalTimesCount == 1)
				{
					formattedTraversalTimesList = Integer.valueOf(inputtedTraversalTimesList.get(i).trim()).toString();
				}
				else
				{	
					formattedTraversalTimesList += ","+Integer.valueOf(inputtedTraversalTimesList.get(i).trim()).toString();
				}
			}
			else
				break;
		}

		if (traversalTimesCorrectlyFormatted == false) 
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("At least one traversal time is not formatted properly!  Please try again.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();
		}
		else if (validTraversalTimesCount == 0)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("At least one traversal time must be specified!  Please try again.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();
		}
		else
		{
			permittedTraversalTimes = formattedTraversalTimesList;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("rs_permittedTraversalTimes", formattedTraversalTimesList);

			permissibleTraversalTimesTextField.setText(formattedTraversalTimesList);
			permissibleTraversalTimesTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
	}
}