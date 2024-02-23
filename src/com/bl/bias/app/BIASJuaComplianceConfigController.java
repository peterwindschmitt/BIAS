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
	@FXML private CheckBox checkLastAcceptedOptionsFileCheckBox;
	@FXML private CheckBox permitsEnabledCheckBox;
	@FXML private CheckBox checkLinearMilesCheckBox;
	@FXML private CheckBox averageSlowOrderSpeedCheckBox;
	@FXML private CheckBox sumOfDurationMilesCheckBox;
	@FXML private CheckBox excludeRestrictionsNearBridgeCheckBox;
	@FXML private CheckBox checkEnabledPermitsOnlyCheckBox;
	@FXML private CheckBox checkStatisticalPeriodOnlyCheckBox;
	@FXML private CheckBox analyzeLinksCheckBox;
	@FXML private CheckBox checkTrainPriorityCheckBox;
	@FXML private CheckBox checkRecoveryRatesCheckBox;

	@FXML private TextArea brightlineTrainTypeTextArea;
	@FXML private TextArea fecTrainTypeTextArea;
	@FXML private TextArea triRailTrainTypeTextArea;
	@FXML private TextArea brightlineNodesTextArea;
	@FXML private TextArea fecNodesTextArea;
	@FXML private TextArea triRailNodesTextArea;
	@FXML private TextArea bridgeMpsTextArea;
	@FXML private TextArea tier1PriorityTrainTextArea;
	@FXML private TextArea tier2PriorityTrainTextArea;
	@FXML private TextArea tier3PriorityTrainTextArea;
	@FXML private TextArea tier4PriorityTrainTextArea;

	@FXML private Label trainCountCriteriaLabel1;
	@FXML private Label trainCountCriteriaLabel2;
	@FXML private Label slowOrderCriteriaLabel1;
	@FXML private Label trainPriorityCriteriaLabel1;
	@FXML private Label maxTriRailCountLabel;
	@FXML private Label maxBrightlineCountLabel;
	@FXML private Label maxFecThroughCountLabel;
	@FXML private Label lastOptionsAcceptedFileLocationLabel;
	@FXML private Label lastTrainsAcceptedFileLocationLabel;
	@FXML private Label lastSlowsAcceptedFileLocationLabel;
	@FXML private Label lastAcceptedLinksFileLocationLabel;
	@FXML private Label checkLastAcceptedTrainFileLabel;

	@FXML private Button trainCountUpdateNodesTypesButton;
	@FXML private Button trainCountUpdateLastAcceptedFileButton;
	@FXML private Button trainPriorityUpdateButton;
	@FXML private Button slowOrderUpdateLastAcceptedFileButton;
	@FXML private Button updateBridgeMpsButton;
	@FXML private Button updateLastAcceptedLinksFileButton;
	@FXML private Button updateLastAcceptedPriorityFileButton;

	private static Boolean checkEnabledCountOfTrains;
	private static Boolean checkLastAcceptedOptionsFile;
	private static Boolean checkLastAcceptedTrainsFile;
	private static Boolean permitsEnabled;
	private static Boolean checkPermitsSumOfTrackMiles;
	private static Boolean checkAverageSlowOrderSpeed;
	private static Boolean checkSumOfDurationMiles;
	private static Boolean checkEnabledPermitsOnly;
	private static Boolean checkStatisticalPeriodOnly;
	private static Boolean excludeRestrictionsNearBridge;
	private static Boolean analyzeLinks;
	private static Boolean checkTrainPriority;
	private static Boolean checkRecoveryRates;

	private static Boolean defaultCheckEnabledCountOfTrains = true;
	private static Boolean defaultCheckLastAcceptedTrainsFile = true;
	private static Boolean defaultCheckLastAcceptedOptionsFile = true;
	private static Boolean defaultPermitsEnabled = true;
	private static Boolean defaultCheckLinearMilesOfSlows = true;
	private static Boolean defaultCheckSlowOrderSpeed = true;
	private static Boolean defaultCheckSumOfDurationMiles = true;
	private static Boolean defaultCheckEnabledPermitsOnly = true;
	private static Boolean defaultCheckStatisticalPeriodOnly = true;
	private static Boolean defaultExcludeRestrictionsNearBridge = true;
	private static Boolean defaultAnalyzeLinks = true;
	private static Boolean defaultCheckTrainPriority = true;
	private static Boolean defaultCheckRecoveryRates = true;

	private static String brightlineTrainTypes;
	private static String fecTrainTypes;
	private static String triRailTrainTypes;
	private static String tier1PriorityTrainTypes;
	private static String tier2PriorityTrainTypes;
	private static String tier3PriorityTrainTypes;
	private static String tier4PriorityTrainTypes;

	private static String brightlineTrainTypeLabel;
	private static String fecTrainTypeLabel;
	private static String triRailTrainTypeLabel;
	private static String tier1PriorityTrainLabel;
	private static String tier2PriorityTrainLabel;
	private static String tier3PriorityTrainLabel;
	private static String tier4PriorityTrainLabel;

	private static String brightlineNodes;
	private static String fecNodes;
	private static String triRailNodes;
	private static String bridgeMps;

	private static String brightlineNodesLabel;
	private static String fecNodesLabel;
	private static String triRailNodesLabel;
	private static String bridgeMpsLabel;

	private static String[] brightlineTrainTypesAsArray = new String[0];
	private static String[] fecTrainTypesAsArray = new String[0];
	private static String[] triRailTrainTypesAsArray = new String[0];
	private static String[] tier1TrainTypesAsArray;
	private static String[] tier2TrainTypesAsArray;
	private static String[] tier3TrainTypesAsArray;
	private static String[] tier4TrainTypesAsArray;

	private static String[] brightlineNodesAsArray = new String[0];
	private static String[] fecNodesAsArray = new String[0];
	private static String[] triRailNodesAsArray = new String[0];
	private static String[] bridgeMpsAsArray = new String[0];

	private static String lastAcceptedTrainFileAsString;
	private static String lastAcceptedOptionFileAsString;
	private static String lastAcceptedPermitFileAsString;
	private static String lastAcceptedLinksFileAsString;

	private static Integer maxCharactersNodesField = 80;
	private static Integer maxCharactersTrainTypeField = 180;
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
			checkLastAcceptedTrainsFile = false;
			checkLastAcceptedTrainsFileCheckBox.setSelected(false);
			checkLastAcceptedTrainsFileCheckBox.setDisable(true);
			lastTrainsAcceptedFileLocationLabel.setDisable(true);
			trainCountUpdateLastAcceptedFileButton.setDisable(true);
			checkLastAcceptedTrainFileLabel.setDisable(true);
		}

		// See if preference is stored for checking against last accepted train file
		if (checkEnabledCountOfTrains)
		{
			if (prefs.getBoolean("ju_checkAgainstLastAcceptedTrainsFile", defaultCheckLastAcceptedTrainsFile))
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
		}
		else
		{
			checkLastAcceptedTrainsFile = false;
			checkLastAcceptedTrainsFileCheckBox.setDisable(true);
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedTrainsFile", false);
			checkLastAcceptedTrainsFileCheckBox.setSelected(false);
		}

		// See if preference is stored for checking against last accepted option file
		if (prefs.getBoolean("ju_checkAgainstLastAcceptedOptionsFile", defaultCheckLastAcceptedOptionsFile))
		{
			checkLastAcceptedOptionsFile = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedOptionsFile", true);
			checkLastAcceptedOptionsFileCheckBox.setSelected(true);
		}
		else
		{
			checkLastAcceptedOptionsFile = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedOptionsFile", false);
			checkLastAcceptedOptionsFileCheckBox.setSelected(false);
		}

		// See if preference is stored for checking recovery rates
		if (prefs.getBoolean("ju_checkRecoveryRates", defaultCheckRecoveryRates))
		{
			checkRecoveryRates = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkRecoveryRates", true);
			checkRecoveryRatesCheckBox.setSelected(true);
		}
		else
		{
			checkRecoveryRates = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkRecoveryRates", false);
			checkRecoveryRatesCheckBox.setSelected(false);
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

		// See if preferences are stored for analyzing networks' links
		if (prefs.getBoolean("ju_analyzeLinks", defaultAnalyzeLinks))
		{
			analyzeLinks = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_analyzeLinks", true);
			analyzeLinksCheckBox.setSelected(true);
		}
		else
		{
			analyzeLinks = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_analyzeLinks", false);
			analyzeLinksCheckBox.setSelected(false);
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

		// See if last accepted option file exists
		if ((prefs.get("ju_lastAcceptedOptionFile", "") != null) && (prefs.get("ju_lastAcceptedOptionFile", "") != ""))
		{
			lastAcceptedOptionFileAsString = prefs.get("ju_lastAcceptedOptionFile", "");;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastAcceptedOptionFile", lastAcceptedOptionFileAsString);
			lastOptionsAcceptedFileLocationLabel.setText(lastAcceptedOptionFileAsString);
		}

		// See if last accepted permit file exists
		if ((prefs.get("ju_lastAcceptedPermitFile", "") != null) && (prefs.get("ju_lastAcceptedPermitFile", "") != ""))
		{
			lastAcceptedPermitFileAsString = prefs.get("ju_lastAcceptedPermitFile", "");;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastAcceptedPermitFile", lastAcceptedPermitFileAsString);
			lastSlowsAcceptedFileLocationLabel.setText(lastAcceptedPermitFileAsString);
		}

		// See if last accepted link file exists
		if ((prefs.get("ju_lastAcceptedLinksFile", "") != null) && (prefs.get("ju_lastAcceptedLinksFile", "") != ""))
		{
			lastAcceptedLinksFileAsString = prefs.get("ju_lastAcceptedLinksFile", "");;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastAcceptedLinksFile", lastAcceptedLinksFileAsString);
			lastAcceptedLinksFileLocationLabel.setText(lastAcceptedLinksFileAsString);
		}

		// See if preferences are stored for checking train priority
		if (prefs.getBoolean("ju_checkTrainPriority", defaultCheckTrainPriority))
		{
			checkTrainPriority = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkTrainPriority", true);
			checkTrainPriorityCheckBox.setSelected(true);
		}
		else
		{
			checkTrainPriority = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkTrainPriority", false);
			checkTrainPriorityCheckBox.setSelected(false);
		}

		// See if preferences are stored for Tier 1 train type priorities
		if ((prefs.get("ju_tier1PriorityTrainTypes", "") != null) && (prefs.get("ju_tier1PriorityTrainTypes", "") != ""))
		{
			tier1PriorityTrainTypes = prefs.get("ju_tier1PriorityTrainTypes", "");
			tier1TrainTypesAsArray = tier1PriorityTrainTypes.split(",");
			tier1PriorityTrainTextArea.setText(tier1PriorityTrainTypes);
		}

		// See if preferences are stored for Tier 2 train type priorities
		if ((prefs.get("ju_tier2PriorityTrainTypes", "") != null) && (prefs.get("ju_tier2PriorityTrainTypes", "") != ""))
		{
			tier2PriorityTrainTypes = prefs.get("ju_tier2PriorityTrainTypes", "");
			tier2TrainTypesAsArray = tier2PriorityTrainTypes.split(",");
			tier2PriorityTrainTextArea.setText(tier2PriorityTrainTypes);
		}

		// See if preferences are stored for Tier 3 train type priorities
		if ((prefs.get("ju_tier3PriorityTrainTypes", "") != null) && (prefs.get("ju_tier3PriorityTrainTypes", "") != ""))
		{
			tier3PriorityTrainTypes = prefs.get("ju_tier3PriorityTrainTypes", "");
			tier3TrainTypesAsArray = tier3PriorityTrainTypes.split(",");
			tier3PriorityTrainTextArea.setText(tier3PriorityTrainTypes);
		}

		// See if preferences are stored for Tier 4 train type priorities
		if ((prefs.get("ju_tier4PriorityTrainTypes", "") != null) && (prefs.get("ju_tier4PriorityTrainTypes", "") != ""))
		{
			tier4PriorityTrainTypes = prefs.get("ju_tier4PriorityTrainTypes", "");
			tier4TrainTypesAsArray = tier4PriorityTrainTypes.split(",");
			tier4PriorityTrainTextArea.setText(tier4PriorityTrainTypes);
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

			checkLastAcceptedTrainsFile = false;
			checkLastAcceptedTrainsFileCheckBox.setSelected(false);

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedTrainsFile", false);

			lastTrainsAcceptedFileLocationLabel.setDisable(true);
			trainCountUpdateLastAcceptedFileButton.setDisable(true);
			checkLastAcceptedTrainFileLabel.setDisable(true);
			checkLastAcceptedTrainsFileCheckBox.setDisable(true);
		}
		else
		{
			checkEnabledCountOfTrains = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkEnabledCountOfTrains", true);

			checkLastAcceptedTrainsFileCheckBox.setDisable(false);
			lastTrainsAcceptedFileLocationLabel.setDisable(false);
			trainCountUpdateLastAcceptedFileButton.setDisable(false);
			checkLastAcceptedTrainFileLabel.setDisable(false);
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

	@FXML private void handleTier1PriorityTrainTextArea()
	{
		tier1PriorityTrainTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");
		trainPriorityUpdateButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTier2PriorityTrainTextArea()
	{
		tier2PriorityTrainTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");
		trainPriorityUpdateButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTier3PriorityTrainTextArea()
	{
		tier3PriorityTrainTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");
		trainPriorityUpdateButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTier4PriorityTrainTextArea()
	{
		tier4PriorityTrainTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 10px;");
		trainPriorityUpdateButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrainCountUpdateNodesTypesButton(ActionEvent event)
	{
		// Train types
		brightlineTrainTypeLabel = brightlineTrainTypeTextArea.getText(0, Math.min(brightlineTrainTypeTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
		brightlineTrainTypeTextArea.setText(brightlineTrainTypeLabel);
		brightlineTrainTypesAsArray = brightlineTrainTypeLabel.split(",");

		fecTrainTypeLabel = fecTrainTypeTextArea.getText(0, Math.min(fecTrainTypeTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
		fecTrainTypeTextArea.setText(fecTrainTypeLabel);
		fecTrainTypesAsArray = fecTrainTypeLabel.split(",");

		triRailTrainTypeLabel = triRailTrainTypeTextArea.getText(0, Math.min(triRailTrainTypeTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
		triRailTrainTypeTextArea.setText(triRailTrainTypeLabel);
		triRailTrainTypesAsArray = triRailTrainTypeLabel.split(",");

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

	@FXML private void handleCheckLastAcceptedOptionsFileCheckBox(ActionEvent event)
	{
		if (checkLastAcceptedOptionsFile)
		{
			checkLastAcceptedOptionsFile = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedOptionsFile", false);
		}
		else
		{
			checkLastAcceptedOptionsFile = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedOptionsFile", true);
		}
	}

	@FXML private void handleCheckLastAcceptedTrainsFileCheckBox(ActionEvent event)
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

	@FXML private void handleCheckRecoveryRatesCheckBox(ActionEvent event)
	{
		if (checkRecoveryRates)
		{
			checkRecoveryRates = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkRecoveryRates", false);
		}
		else
		{
			checkRecoveryRates = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkRecoveryRates", true);
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

	@FXML private void handleAnalyzeLinksCheckBox(ActionEvent event)
	{
		if (analyzeLinks)
		{
			analyzeLinks = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_analyzeLinks", false);
		}
		else
		{
			analyzeLinks = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_analyzeLinks", true);
		}
	}

	@FXML private void handleCheckTrainPriorityCheckBox (ActionEvent event)
	{
		if (checkTrainPriority)
		{
			checkTrainPriority = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkTrainPriority", false);
		}
		else
		{
			checkTrainPriority = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkTrainPriority", true);
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

	@FXML private void handleUpdateLastAcceptedLinksFileButton (ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"RTC Link Files", "*.LINK");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) updateLastAcceptedLinksFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .LINK file found
		if (file != null)
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastAcceptedLinksFile", file.toString());

			lastAcceptedLinksFileLocationLabel.setText(file.toString());
			lastAcceptedLinksFileAsString = file.toString();
		}
	}      

	@FXML private void handleTrainPriorityUpdateButton(ActionEvent event)
	{
		// Should be digits, letters, white space, comma, hyphen and underscore only
		if (((tier1PriorityTrainTextArea.getText().trim().equals("")) ||
				(((tier1PriorityTrainTextArea.getText().trim().matches("^[a-zA-Z\\s,0-9_-]*$"))) 
						&& ((!tier1PriorityTrainTextArea.getText().trim().substring(tier1PriorityTrainTextArea.getText().trim().length() - 1).equals(",")))
						&& (!tier1PriorityTrainTextArea.getText().trim().substring(0).equals(","))))

				&& ((tier2PriorityTrainTextArea.getText().trim().equals("")) ||
						(((tier2PriorityTrainTextArea.getText().trim().matches("^[a-zA-Z\\s,0-9_-]*$"))) 
								&& ((!tier2PriorityTrainTextArea.getText().trim().substring(tier2PriorityTrainTextArea.getText().trim().length() - 1).equals(",")))
								&& (!tier2PriorityTrainTextArea.getText().trim().substring(0).equals(","))))

				&& ((tier3PriorityTrainTextArea.getText().trim().equals("")) ||
						(((tier3PriorityTrainTextArea.getText().trim().matches("^[a-zA-Z\\s,0-9_-]*$"))) 
								&& ((!tier3PriorityTrainTextArea.getText().trim().substring(tier3PriorityTrainTextArea.getText().trim().length() - 1).equals(",")))
								&& (!tier3PriorityTrainTextArea.getText().trim().substring(0).equals(","))))

				&& ((tier4PriorityTrainTextArea.getText().trim().equals("")) ||
						(((tier4PriorityTrainTextArea.getText().trim().matches("^[a-zA-Z\\s,0-9_-]*$"))) 
								&& ((!tier4PriorityTrainTextArea.getText().trim().substring(tier4PriorityTrainTextArea.getText().trim().length() - 1).equals(",")))
								&& (!tier4PriorityTrainTextArea.getText().trim().substring(0).equals(",")))))
		{
			tier1PriorityTrainLabel = tier1PriorityTrainTextArea.getText(0, Math.min(tier1PriorityTrainTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
			tier1PriorityTrainTypes = tier1PriorityTrainLabel;
			tier1PriorityTrainTextArea.setText(tier1PriorityTrainLabel);

			tier2PriorityTrainLabel = tier2PriorityTrainTextArea.getText(0, Math.min(tier2PriorityTrainTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
			tier2PriorityTrainTypes = tier2PriorityTrainLabel;
			tier2PriorityTrainTextArea.setText(tier2PriorityTrainLabel);

			tier3PriorityTrainLabel = tier3PriorityTrainTextArea.getText(0, Math.min(tier3PriorityTrainTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
			tier3PriorityTrainTypes = tier3PriorityTrainLabel;
			tier3PriorityTrainTextArea.setText(tier3PriorityTrainLabel);

			tier4PriorityTrainLabel = tier4PriorityTrainTextArea.getText(0, Math.min(tier4PriorityTrainTextArea.getText().length(), maxCharactersTrainTypeField)).trim().toUpperCase();
			tier4PriorityTrainTypes = tier4PriorityTrainLabel;
			tier4PriorityTrainTextArea.setText(tier4PriorityTrainLabel);

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("ju_tier1PriorityTrainTypes", tier1PriorityTrainLabel);	
				prefs.put("ju_tier2PriorityTrainTypes", tier2PriorityTrainLabel);
				prefs.put("ju_tier3PriorityTrainTypes", tier3PriorityTrainLabel);
				prefs.put("ju_tier4PriorityTrainTypes", tier4PriorityTrainLabel);
			}

			tier1PriorityTrainTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 10px;");
			tier2PriorityTrainTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 10px;");
			tier3PriorityTrainTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 10px;");
			tier4PriorityTrainTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 10px;");
			trainPriorityUpdateButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Only A-Z, 0-9, hyphen, underscore and blank spaces are permitted.  Seperate multiple Train Types by ','.  Please try again.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();

			tier1PriorityTrainTextArea.setText(tier1PriorityTrainTypes); 
		}
	}

	@FXML private void handleLastAcceptedPriorityButton(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");
		FileChooser.ExtensionFilter fileExtensions = 
				new FileChooser.ExtensionFilter(
						"RTC Option Files", "*.OPTION");

		fileChooser.getExtensionFilters().add(fileExtensions);		

		// Show the chooser and get the file
		Stage stageForFileChooser = (Stage) updateLastAcceptedPriorityFileButton.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stageForFileChooser);

		// Valid .OPTION file found
		if (file != null)
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("ju_lastAcceptedOptionFile", file.toString());

			lastOptionsAcceptedFileLocationLabel.setText(file.toString());
			lastAcceptedOptionFileAsString = file.toString();
		}
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

	public static Boolean getCheckLastAcceptedOptionsFile()
	{
		return checkLastAcceptedOptionsFile;
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

	public static Boolean getAnalyzeLinks()
	{
		return analyzeLinks;
	}

	public static Boolean getCheckRecoveryRates()
	{
		return checkRecoveryRates;
	}

	public static String getLastAcceptedTrainFileAsString()
	{
		return lastAcceptedTrainFileAsString;
	}

	public static String getLastAcceptedOptionFileAsString()
	{
		return lastAcceptedOptionFileAsString;
	}

	public static String getLastAcceptedPermitFileAsString()
	{
		return lastAcceptedPermitFileAsString;
	}

	public static String getLastAcceptedLinkFileAsString()
	{
		return lastAcceptedLinksFileAsString;
	}

	public static String[] getBridgeMps()
	{
		return bridgeMpsAsArray;
	}

	public static Boolean getCheckTrainPriority()
	{
		return checkTrainPriority;
	}

	public static String[] getTier1TrainTypesAsArray()
	{
		return tier1TrainTypesAsArray;
	}

	public static String[] getTier2TrainTypesAsArray()
	{
		return tier2TrainTypesAsArray;
	}

	public static String[] getTier3TrainTypesAsArray()
	{
		return tier3TrainTypesAsArray;
	}

	public static String[] getTier4TrainTypesAsArray()
	{
		return tier4TrainTypesAsArray;
	}

	public static Boolean getLastAcceptedTrainFileExists() 
	{
		File f = new File(lastAcceptedTrainFileAsString);
		if(f.exists() && !f.isDirectory()) 
			return true;
		else
			return false;
	}

	public static Boolean getLastAcceptedOptionFileExists() 
	{
		File f = new File(lastAcceptedOptionFileAsString);
		if(f.exists() && !f.isDirectory()) 
			return true;
		else
			return false;
	}

	public static Boolean getLastAcceptedLinkFileExists() 
	{
		File f = new File(lastAcceptedLinksFileAsString);
		if(f.exists() && !f.isDirectory()) 
			return true;
		else
			return false;
	}

	public static Boolean getLastAcceptedPermitFileExists() 
	{
		File f = new File(lastAcceptedPermitFileAsString);
		if(f.exists() && !f.isDirectory()) 
			return true;
		else
			return false;
	}
}