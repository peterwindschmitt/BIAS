package com.bl.bias.app;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.bl.bias.objects.ComplianceCriteria;

public class BIASJuaComplianceConfigController extends ComplianceCriteria
{
	@FXML private CheckBox trainCountEnabledCheckBox;
	@FXML private CheckBox checkLastAcceptedTrainsFileCheckBox;
	@FXML private CheckBox trainPrioritytEnabledCheckBox;
	@FXML private CheckBox trainMileageEnabledCheckBox;
	@FXML private CheckBox permitsEnabledCheckBox;
	@FXML private CheckBox checkLinearMilesCheckBox;
	@FXML private CheckBox averageSlowOrderSpeedCheckBox;
	@FXML private CheckBox sumOfDurationMilesCheckBox;
	@FXML private CheckBox excludeRestrictionsNearBridgeCheckBox;
	@FXML private CheckBox checkEnabledPermitsOnlyCheckBox;
	@FXML private CheckBox checkStatisticalPeriodOnlyCheckBox;

	@FXML private TextArea brightlineTrainTypeTextArea;
	@FXML private TextArea fecTrainTypeTextArea;
	@FXML private TextArea triRailTrainTypeTextArea;
	@FXML private TextArea brightlineNodesTextArea;
	@FXML private TextArea fecNodesTextArea;
	@FXML private TextArea triRailNodesTextArea;
	@FXML private TextArea bridgeMpsTextArea;

	@FXML private Label trainCountCriteriaLabel1;
	@FXML private Label trainCountCriteriaLabel2;
	@FXML private Label slowOrderCriteriaLabel1;
	@FXML private Label trainPriorityCriteriaLabel1;
	@FXML private Label maxTriRailCountLabel;
	@FXML private Label maxBrightlineCountLabel;
	@FXML private Label maxFecThroughCountLabel;
	@FXML private Label lastTrainsAcceptedFileLocationLabel;
	@FXML private Label lastSlowsAcceptedFileLocationLabel;

	@FXML private Button trainCountUpdateNodesTypesButton;
	@FXML private Button trainCountUpdateLastAcceptedFileButton;
	@FXML private Button trainPriorityUpdateButton;
	@FXML private Button slowOrderUpdateLastAcceptedFileButton;
	@FXML private Button updateBridgeMpsButton;

	private static Boolean checkEnabledCountOfTrains;
	private static Boolean checkLastAcceptedTrainsFile;
	private static Boolean permitsEnabled;
	private static Boolean checkPermitsSumOfTrackMiles;
	private static Boolean checkAverageSlowOrderSpeed;
	private static Boolean checkSumOfDurationMiles;
	private static Boolean checkEnabledPermitsOnly;
	private static Boolean checkStatisticalPeriodOnly;
	private static Boolean excludeRestrictionsNearBridge;

	private static Boolean defaultCheckEnabledCountOfTrains = true;
	private static Boolean defaultCheckLastAcceptedTrainsFile = true;
	private static Boolean defaultPermitsEnabled = true;
	private static Boolean defaultCheckLinearMilesOfSlows = true;
	private static Boolean defaultCheckSlowOrderSpeed = true;
	private static Boolean defaultCheckSumOfDurationMiles = true;
	private static Boolean defaultCheckEnabledPermitsOnly = true;
	private static Boolean defaultCheckStatisticalPeriodOnly = true;
	private static Boolean defaultExcludeRestrictionsNearBridge = true;

	private static String brightlineTrainTypes;
	private static String fecTrainTypes;
	private static String triRailTrainTypes;

	private static String brightlineTrainTypeLabel;
	private static String fecTrainTypeLabel;
	private static String triRailTrainTypeLabel;

	private static String brightlineNodes;
	private static String fecNodes;
	private static String triRailNodes;
	private static String bridgeMps;

	private static String brightlineNodesLabel;
	private static String fecNodesLabel;
	private static String triRailNodesLabel;
	private static String bridgeMpsLabel;

	private static String[] brightlineTrainTypesAsArray;
	private static String[] fecTrainTypesAsArray;
	private static String[] triRailTrainTypesAsArray;

	private static String[] brightlineNodesAsArray;
	private static String[] fecNodesAsArray;
	private static String[] triRailNodesAsArray;
	private static String[] bridgeMpsAsArray;

	private static String lastAcceptedTrainFileAsString;
	private static String lastAcceptedPermitFileAsString;

	private static Integer maxCharactersNodesField = 80;
	private static Integer maxCharactersTrainTypeField = 100;
	private static Integer maxBrightlineTrainCountPerDayOnAverage = 36;
	private static Integer maxFecThroughTrainCountPerDayOnAverage = 24;
	private static Integer maxTriRailTrainCountPerDayOnAverage = 28;

	private static Preferences prefs;

	@FXML private void initialize() throws IOException
	{
		maxBrightlineCountLabel.setText(maxBrightlineTrainCountPerDayOnAverage.toString());
		maxFecThroughCountLabel.setText(maxFecThroughTrainCountPerDayOnAverage.toString());
		maxTriRailCountLabel.setText(maxTriRailTrainCountPerDayOnAverage.toString());

		// Set up prefs
		prefs = Preferences.userRoot().node("BIAS");

		// See if preference is stored for checking the enabled count of trains
		if (prefs.getBoolean("ju_checkEnabledCountOfTrains", defaultCheckEnabledCountOfTrains))
		{
			checkEnabledCountOfTrains = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkEnabledCountOfTrains", true);
			trainCountEnabledCheckBox.setSelected(true);
		}
		else
		{
			checkEnabledCountOfTrains = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkEnabledCountOfTrains", false);
			trainCountEnabledCheckBox.setSelected(false);
		}

		// See if preference is stored for checking against last accepted file
		if (prefs.getBoolean("ju_checkAgainstLastAcceptedTrainsFile", defaultCheckEnabledCountOfTrains))
		{
			checkLastAcceptedTrainsFile = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedTrainsFile", true);
			checkLastAcceptedTrainsFileCheckBox.setSelected(true);
		}
		else
		{
			checkLastAcceptedTrainsFile = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedTrainsFile", false);
			checkLastAcceptedTrainsFileCheckBox.setSelected(false);
		}

		// See if preferences are stored for Brightline train types
		if ((prefs.get("ju_brightlineTrainTypes", "") != null) && (prefs.get("ju_brightlineTrainTypes", "") != ""))
		{
			brightlineTrainTypes = prefs.get("ju_brightlineTrainTypes", "");
			brightlineTrainTypesAsArray = brightlineTrainTypes.split(",");
			brightlineTrainTypeTextArea.setText(brightlineTrainTypes);
		}

		// See if preferences are stored for FEC train types
		if ((prefs.get("ju_fecTrainTypes", "") != null) && (prefs.get("ju_fecTrainTypes", "") != ""))
		{
			fecTrainTypes = prefs.get("ju_fecTrainTypes", "");
			fecTrainTypesAsArray = fecTrainTypes.split(",");
			fecTrainTypeTextArea.setText(fecTrainTypes);
		}

		// See if preferences are stored for Tri-Rail train types
		if ((prefs.get("ju_triRailTrainTypes", "") != null) && (prefs.get("ju_triRailTrainTypes", "") != ""))
		{
			triRailTrainTypes = prefs.get("ju_triRailTrainTypes", "");
			triRailTrainTypesAsArray = triRailTrainTypes.split(",");
			triRailTrainTypeTextArea.setText(triRailTrainTypes);
		}

		// See if preferences are stored for Brightline nodes
		if ((prefs.get("ju_brightlineNodes", "") != null) && (prefs.get("ju_brightlineNodes", "") != ""))
		{
			brightlineNodes = prefs.get("ju_brightlineNodes", "");
			brightlineNodesAsArray = brightlineNodes.split(",");
			brightlineNodesTextArea.setText(brightlineNodes);
		}

		// See if preferences are stored for FEC nodes
		if ((prefs.get("ju_fecNodes", "") != null) && (prefs.get("ju_fecNodes", "") != ""))
		{
			fecNodes = prefs.get("ju_fecNodes", "");
			fecNodesAsArray = fecNodes.split(",");
			fecNodesTextArea.setText(fecNodes);
		}

		// See if preferences are stored for Tri-Rail nodes
		if ((prefs.get("ju_triRailNodes", "") != null) && (prefs.get("ju_triRailNodes", "") != ""))
		{
			triRailNodes = prefs.get("ju_triRailNodes", "");
			triRailNodesAsArray = triRailNodes.split(",");
			triRailNodesTextArea.setText(triRailNodes);
		}

		// See if preference is stored for checking permits
		if (prefs.getBoolean("ju_permitsEnabled", defaultPermitsEnabled))
		{
			permitsEnabled = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_permitsEnabled", true);
			permitsEnabledCheckBox.setSelected(true);
		}
		else
		{
			permitsEnabled = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_permitsEnabled", false);
			permitsEnabledCheckBox.setSelected(false);
		}

		// See if preferences are stored for checking sum of track miles for permits
		if (prefs.getBoolean("ju_checkPermitsLinearSumOfMiles", defaultCheckLinearMilesOfSlows))
		{
			checkPermitsSumOfTrackMiles = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkPermitsLinearSumOfMiles", true);
			checkLinearMilesCheckBox.setSelected(true);
		}
		else
		{
			checkPermitsSumOfTrackMiles = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkPermitsLinearSumOfMiles", false);
			checkLinearMilesCheckBox.setSelected(false);
		}

		// See if preferences are stored for checking average slow order speeds
		if (prefs.getBoolean("ju_checkSlowOrderSpeeds", defaultCheckSlowOrderSpeed))
		{
			checkAverageSlowOrderSpeed = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkSlowOrderSpeeds", true);
			averageSlowOrderSpeedCheckBox.setSelected(true);
		}
		else
		{
			checkAverageSlowOrderSpeed = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkSlowOrderSpeeds", false);
			averageSlowOrderSpeedCheckBox.setSelected(false);
		}

		// See if preferences are stored for checking sum of duration miles
		if (prefs.getBoolean("ju_checkSumOfDurationMiles", defaultCheckSumOfDurationMiles))
		{
			checkSumOfDurationMiles = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkSumOfDurationMiles", true);
			sumOfDurationMilesCheckBox.setSelected(true);
		}
		else
		{
			checkSumOfDurationMiles = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkSumOfDurationMiles", false);
			sumOfDurationMilesCheckBox.setSelected(false);
		}

		// See if preferences are stored for checking enabled permits only
		if (prefs.getBoolean("ju_checkEnabledPermitsOnly", defaultCheckEnabledPermitsOnly))
		{
			checkEnabledPermitsOnly = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkEnabledPermitsOnly", true);
			checkEnabledPermitsOnlyCheckBox.setSelected(true);
		}
		else
		{
			checkEnabledPermitsOnly = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkEnabledPermitsOnly", false);
			checkEnabledPermitsOnlyCheckBox.setSelected(false);
		}

		// See if preferences are stored for checking permits in statistical period only
		if (prefs.getBoolean("ju_checkStatisticalPeriodPermitsOnly", defaultCheckStatisticalPeriodOnly))
		{
			checkStatisticalPeriodOnly = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkStatisticalPeriodPermitsOnly", true);
			checkStatisticalPeriodOnlyCheckBox.setSelected(true);
		}
		else
		{
			checkStatisticalPeriodOnly = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkStatisticalPeriodPermitsOnly", false);
			checkStatisticalPeriodOnlyCheckBox.setSelected(false);
		}

		// See if preferences are stored for excluding permits which touch a bridge link
		if (prefs.getBoolean("ju_excludeRestrictionsNearBridge", defaultExcludeRestrictionsNearBridge))
		{
			excludeRestrictionsNearBridge = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_excludeRestrictionsNearBridge", true);
			excludeRestrictionsNearBridgeCheckBox.setSelected(true);
		}
		else
		{
			excludeRestrictionsNearBridge = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_excludeRestrictionsNearBridge", false);
			excludeRestrictionsNearBridgeCheckBox.setSelected(false);
		}

		// See if preferences are stored for bridge field MPs
		if ((prefs.get("ju_bridgeMps", "") != null) && (prefs.get("ju_bridgeMps", "") != ""))
		{
			bridgeMps = prefs.get("ju_bridgeMps", "");
			bridgeMpsAsArray = bridgeMps.split(",");
			bridgeMpsTextArea.setText(bridgeMps);
		}

		// See if last accepted train file exists
		if ((prefs.get("ju_lastAcceptedTrainFile", "") != null) && (prefs.get("ju_lastAcceptedTrainFile", "") != ""))
		{
			lastAcceptedTrainFileAsString = prefs.get("ju_lastAcceptedTrainFile", "");;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastAcceptedTrainFile", lastAcceptedTrainFileAsString);
			lastTrainsAcceptedFileLocationLabel.setText(lastAcceptedTrainFileAsString);
		}

		// See if last accepted permit file exists
		if ((prefs.get("ju_lastAcceptedPermitFile", "") != null) && (prefs.get("ju_lastAcceptedPermitFile", "") != ""))
		{
			lastAcceptedPermitFileAsString = prefs.get("ju_lastAcceptedPermitFile", "");;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastAcceptedPermitFile", lastAcceptedPermitFileAsString);
			lastSlowsAcceptedFileLocationLabel.setText(lastAcceptedPermitFileAsString);
		}

		trainCountCriteriaLabel1.setText(ComplianceCriteria.jua_4_2_c()[0]+" "+ComplianceCriteria.jua_4_2_c()[1]+":  "+ComplianceCriteria.jua_4_2_c()[2]);
		trainCountCriteriaLabel2.setText(ComplianceCriteria.trdml_ii_4()[0]+" "+ComplianceCriteria.trdml_ii_4()[1]+":  "+ComplianceCriteria.trdml_ii_4()[2]);
		slowOrderCriteriaLabel1.setText(ComplianceCriteria.hdr_mow_1()[0]);
		trainPriorityCriteriaLabel1.setText(ComplianceCriteria.trdml_ii_3()[0]+" "+ComplianceCriteria.trdml_ii_3()[1]+":  "+ComplianceCriteria.trdml_ii_3()[2]);
	}

	@FXML private void handleTrainCountEnabledCheckBox(ActionEvent event)
	{
		if (checkEnabledCountOfTrains)
		{
			checkEnabledCountOfTrains = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkEnabledCountOfTrains", false);
		}
		else
		{
			checkEnabledCountOfTrains = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkEnabledCountOfTrains", true);
		}
	}

	@FXML private void handleBrightlineTrainTypeTextArea()
	{
		brightlineTrainTypeTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		trainCountUpdateNodesTypesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleFecTrainTypeTextArea()
	{
		fecTrainTypeTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		trainCountUpdateNodesTypesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTriRailTrainTypeTextArea()
	{
		triRailTrainTypeTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		trainCountUpdateNodesTypesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleBrightlineNodesTextArea()
	{
		brightlineNodesTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		trainCountUpdateNodesTypesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleFecNodesTextArea()
	{
		fecNodesTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		trainCountUpdateNodesTypesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTriRailNodesTextArea()
	{
		triRailNodesTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		trainCountUpdateNodesTypesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleBridgeMpsTextArea()
	{
		bridgeMpsTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateBridgeMpsButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrainCountUpdateNodesTypesButton(ActionEvent event)
	{
		// Train types
		brightlineTrainTypeLabel = brightlineTrainTypeTextArea.getText(0, Math.min(brightlineTrainTypeTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
		brightlineTrainTypeTextArea.setText(brightlineTrainTypeLabel);

		fecTrainTypeLabel = fecTrainTypeTextArea.getText(0, Math.min(fecTrainTypeTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
		fecTrainTypeTextArea.setText(fecTrainTypeLabel);

		triRailTrainTypeLabel = triRailTrainTypeTextArea.getText(0, Math.min(triRailTrainTypeTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
		triRailTrainTypeTextArea.setText(triRailTrainTypeLabel);

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("ju_brightlineTrainTypes", brightlineTrainTypeLabel);	

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("ju_fecTrainTypes", fecTrainTypeLabel);	

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("ju_triRailTrainTypes", triRailTrainTypeLabel);	

		brightlineTrainTypeTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		fecTrainTypeTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		triRailTrainTypeTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		trainCountUpdateNodesTypesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		// Train nodes
		brightlineNodesLabel = brightlineNodesTextArea.getText(0, Math.min(brightlineNodesTextArea.getText().length(), maxCharactersNodesField)).trim().toUpperCase();
		brightlineNodesTextArea.setText(brightlineNodesLabel);
		brightlineNodesAsArray = brightlineNodesLabel.split(",");

		fecNodesLabel = fecNodesTextArea.getText(0, Math.min(fecNodesTextArea.getText().length(), maxCharactersNodesField)).trim().toUpperCase();
		fecNodesTextArea.setText(fecNodesLabel);
		fecNodesAsArray = fecNodesLabel.split(",");

		triRailNodesLabel = triRailNodesTextArea.getText(0, Math.min(triRailNodesTextArea.getText().length(), maxCharactersNodesField)).trim().toUpperCase();
		triRailNodesTextArea.setText(triRailNodesLabel);
		triRailNodesAsArray = triRailNodesLabel.split(",");
		
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("ju_brightlineNodes", brightlineNodesLabel);	

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("ju_fecNodes", fecNodesLabel);	

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("ju_triRailNodes", triRailNodesLabel);	

		brightlineTrainTypeTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		fecTrainTypeTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		triRailTrainTypeTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		brightlineNodesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		fecNodesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		triRailNodesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		trainCountUpdateNodesTypesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
	}

	@FXML private void handleTrainPriorityEnabledCheckBox(ActionEvent event)
	{

	}

	@FXML private void handleTrainMileageEnabledCheckBox(ActionEvent event)
	{

	}

	@FXML private void handleUpdateBridgeMpsButton(ActionEvent event)
	{
		// Should be digits, comma and decimal only
		if ((bridgeMpsTextArea.getText().trim().equals("")) ||
		(((bridgeMpsTextArea.getText().trim().matches("^[0-9]{0,4}\\.?[0-9]{0,3}(,\\s?[0-9]{0,4}\\.?[0-9]{0,3})*$"))) 
				&& ((!bridgeMpsTextArea.getText().trim().substring(bridgeMpsTextArea.getText().trim().length() - 1).equals(".")))
				&& ((!bridgeMpsTextArea.getText().trim().substring(bridgeMpsTextArea.getText().trim().length() - 1).equals(",")))
				&& (!bridgeMpsTextArea.getText().trim().substring(0).equals(","))
				&& (!bridgeMpsTextArea.getText().trim().substring(0).equals("."))))
		{
			bridgeMpsLabel = bridgeMpsTextArea.getText(0, Math.min(bridgeMpsTextArea.getText().length(), maxCharactersNodesField)).trim().toUpperCase().replace(" ", "");
			bridgeMps = bridgeMpsLabel;
			bridgeMpsTextArea.setText(bridgeMpsLabel);
			

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_bridgeMps", bridgeMpsLabel);	
			

			bridgeMpsTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			updateBridgeMpsButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Only digits 0-9 and '.' are permitted!  Seperate multiple Field MPs by ','.  Please try again.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();
			
			bridgeMpsTextArea.setText(bridgeMps); 
		}
	}

	@FXML private void handleCheckLastAcceptedTrainsFileCheckBoxx(ActionEvent event)
	{
		if (checkLastAcceptedTrainsFile)
		{
			checkLastAcceptedTrainsFile = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedTrainsFile", false);
		}
		else
		{
			checkLastAcceptedTrainsFile = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedTrainsFile", true);
		}
	}

	@FXML private void handlePermitsEnabledCheckBox(ActionEvent event)
	{
		if (permitsEnabled)
		{
			permitsEnabled = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_permitsEnabled", false);
		}
		else
		{
			permitsEnabled = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_permitsEnabled", true);
		}
	}

	@FXML private void handleCheckLinearMilesCheckBox(ActionEvent event)
	{
		if (checkPermitsSumOfTrackMiles)
		{
			checkPermitsSumOfTrackMiles = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkPermitsLinearSumOfMiles", false);
		}
		else
		{
			checkPermitsSumOfTrackMiles = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkPermitsLinearSumOfMiles", true);
		}
	}

	@FXML private void handleAverageSlowOrderSpeedCheckBox(ActionEvent event)
	{
		if (checkAverageSlowOrderSpeed)
		{
			checkAverageSlowOrderSpeed = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkSlowOrderSpeeds", false);
		}
		else
		{
			checkAverageSlowOrderSpeed = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkSlowOrderSpeeds", true);
		}
	}

	@FXML private void handleSumOfDurationMilesCheckBox(ActionEvent event)
	{
		if (checkSumOfDurationMiles)
		{
			checkSumOfDurationMiles = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkSumOfDurationMiles", false);
		}
		else
		{
			checkSumOfDurationMiles = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkSumOfDurationMiles", true);
		}
	}

	@FXML private void handleExcludeRestrictionsNearBridgeCheckBox (ActionEvent event)
	{
		if (excludeRestrictionsNearBridge)
		{
			excludeRestrictionsNearBridge = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_excludeRestrictionsNearBridge", false);
		}
		else
		{
			excludeRestrictionsNearBridge = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_excludeRestrictionsNearBridge", true);
		}
	}

	@FXML private void handleCheckEnabledPermitsOnlyCheckBox (ActionEvent event)
	{
		if (checkEnabledPermitsOnly)
		{
			checkEnabledPermitsOnly = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkEnabledPermitsOnly", false);
		}
		else
		{
			checkEnabledPermitsOnly = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkEnabledPermitsOnly", true);
		}
	}

	@FXML private void handleCheckStatisticalPeriodOnlyCheckBox (ActionEvent event)
	{
		if (checkStatisticalPeriodOnly)
		{
			checkStatisticalPeriodOnly = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkStatisticalPeriodPermitsOnly", false);
		}
		else
		{
			checkStatisticalPeriodOnly = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkStatisticalPeriodPermitsOnly", true);
		}
	}

	@FXML private void handleSlowOrderUpdateLastAcceptedFileButton (ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"RTC Permit Files", "*.PERMIT");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) slowOrderUpdateLastAcceptedFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .PERMIT file found
		if (file != null)
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastAcceptedPermitFile", file.toString());

			lastSlowsAcceptedFileLocationLabel.setText(file.toString());
			lastAcceptedPermitFileAsString = file.toString();
		}
	}           

	@FXML private void handleTrainPriorityUpdateButton(ActionEvent event)
	{

	}

	@FXML private void handleTrainCountUpdateLastAcceptedFileButton(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"RTC Train Files", "*.TRAIN");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) trainCountUpdateLastAcceptedFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .TRAIN file found
		if (file != null)
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastAcceptedTrainFile", file.toString());

			lastTrainsAcceptedFileLocationLabel.setText(file.toString());
			lastAcceptedTrainFileAsString = file.toString();
		}
	}
	
	public static Boolean getCheckLastAcceptedTrainsFile()
	{
		return checkLastAcceptedTrainsFile;
	}

	public static String[] getBrightlineTrainTypes()
	{
		return brightlineTrainTypesAsArray;
	}

	public static String[] getFecTrainTypes()
	{
		return fecTrainTypesAsArray;
	}

	public static String[] getTriRailTrainTypes()
	{
		return triRailTrainTypesAsArray;
	}

	public static String[] getBrightlineNodes()
	{
		return brightlineNodesAsArray;
	}

	public static String[] getFecNodes()
	{
		return fecNodesAsArray;
	}

	public static String[] getTriRailNodes()
	{
		return triRailNodesAsArray;
	}

	public static Integer getDailyBrightlinePermitted()
	{
		return maxBrightlineTrainCountPerDayOnAverage;
	}

	public static Integer getDailyFecPermitted()
	{
		return maxFecThroughTrainCountPerDayOnAverage;
	}

	public static Integer getDailyTriRailPermitted()
	{
		return maxTriRailTrainCountPerDayOnAverage;
	}

	public static Boolean getCheckEnabledCountOfTrains()
	{
		return checkEnabledCountOfTrains;
	}

	public static Boolean getCheckPermitsEnabled()
	{
		return permitsEnabled;
	}

	public static Boolean getCheckPermitsSumOfTrackMiles()
	{
		return checkPermitsSumOfTrackMiles;
	}

	public static Boolean getAverageSlowOrderSpeed()
	{
		return checkAverageSlowOrderSpeed;
	}

	public static Boolean getSumOfDurationMiles()
	{
		return checkSumOfDurationMiles;
	}

	public static Boolean getCheckEnabledPermitsOnly()
	{
		return checkEnabledPermitsOnly;
	}

	public static Boolean getCheckStatisticalPeriodOnly()
	{
		return checkStatisticalPeriodOnly;
	}

	public static Boolean getExcludePermitsNearBridge()
	{
		return excludeRestrictionsNearBridge;
	}

	public static String getLastAcceptedTrainFileAsString()
	{
		return lastAcceptedTrainFileAsString;
	}
	
	public static String getLastAcceptedPermitFileAsString()
	{
		return lastAcceptedPermitFileAsString;
	}

	public static String[] getBridgeMps()
	{
		return bridgeMpsAsArray;
	}
}