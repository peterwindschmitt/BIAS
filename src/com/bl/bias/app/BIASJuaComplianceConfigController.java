package com.bl.bias.app;

import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import com.bl.bias.objects.ComplianceCriteria;

public class BIASJuaComplianceConfigController extends ComplianceCriteria
{
	@FXML private CheckBox trainCountEnabledCheckBox;
	@FXML private CheckBox lastAcceptedFileEnabledCheckBox;
	@FXML private CheckBox trainPrioritytEnabledCheckBox;
	@FXML private CheckBox trainMileageEnabledCheckBox;
	@FXML private CheckBox permitsEnabledCheckBox;
	@FXML private CheckBox checkQuantityOfPermitsCheckBox;
	@FXML private CheckBox checkLinearMilesCheckBox;
	@FXML private CheckBox sumOfSpeedsCheckBox;
	@FXML private CheckBox sumOfDurationsCheckBox;
	@FXML private CheckBox excludeRestrictionsNearBridgeCheckBox;

	@FXML private TextArea brightlineTrainTypeTextArea;
	@FXML private TextArea fecTrainTypeTextArea;
	@FXML private TextArea triRailTrainTypeTextArea;
	@FXML private TextArea brightlineNodesTextArea;
	@FXML private TextArea fecNodesTextArea;
	@FXML private TextArea triRailNodesTextArea;

	@FXML private Label trainCountCriteriaLabel1;
	@FXML private Label trainCountCriteriaLabel2;
	@FXML private Label slowOrderCriteriaLabel1;
	@FXML private Label trainPriorityCriteriaLabel1;
	@FXML private Label lastTrainsAcceptedFileLocationLabel;
	@FXML private Label maxTriRailCountLabel;
	@FXML private Label maxBrightlineCountLabel;
	@FXML private Label maxFecThroughCountLabel;
	@FXML private Label lastSlowsAcceptedFileLocationLabel;

	@FXML private Button trainCountUpdateNodesTypesButton;
	@FXML private Button trainCountUpdateLastAcceptedFileButton;
	@FXML private Button trainPriorityUpdateButton;
	@FXML private Button slowOrderUpdateLastAcceptedFileButton;

	private static Boolean checkEnabledCountOfTrains;
	private static Boolean checkLastAcceptedTrainsFile;
	private static Boolean permitsEnabled;
	private static Boolean checkPermitCount;

	private static Boolean defaultCheckEnabledCountOfTrains = true;
	private static Boolean defaultCheckLastAcceptedTrainsFile = true;
	private static Boolean defaultPermitsEnabled = true;
	private static Boolean defaultCheckPermitCount = true;

	private static String brightlineTrainTypes;
	private static String fecTrainTypes;
	private static String triRailTrainTypes;

	private static String brightlineTrainTypeLabel;
	private static String fecTrainTypeLabel;
	private static String triRailTrainTypeLabel;

	private static String brightlineNodes;
	private static String fecNodes;
	private static String triRailNodes;

	private static String brightlineNodesLabel;
	private static String fecNodesLabel;
	private static String triRailNodesLabel;

	private static String[] brightlineTrainTypesAsArray;
	private static String[] fecTrainTypesAsArray;
	private static String[] triRailTrainTypesAsArray;

	private static String[] brightlineNodesAsArray;
	private static String[] fecNodesAsArray;
	private static String[] triRailNodesAsArray;

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
			lastAcceptedFileEnabledCheckBox.setSelected(true);
		}
		else
		{
			checkLastAcceptedTrainsFile = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkAgainstLastAcceptedTrainsFile", false);
			lastAcceptedFileEnabledCheckBox.setSelected(false);
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

		// See if preference is stored for checking permits
		if (prefs.getBoolean("ju_checkPermitCount", defaultCheckPermitCount))
		{
			checkPermitCount = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkPermitCount", true);
			checkQuantityOfPermitsCheckBox.setSelected(true);
		}
		else
		{
			checkPermitCount = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkPermitCount", false);
			checkQuantityOfPermitsCheckBox.setSelected(false);
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

		fecNodesLabel = fecNodesTextArea.getText(0, Math.min(fecNodesTextArea.getText().length(), maxCharactersNodesField)).trim().toUpperCase();
		fecNodesTextArea.setText(fecNodesLabel);

		triRailNodesLabel = triRailNodesTextArea.getText(0, Math.min(triRailNodesTextArea.getText().length(), maxCharactersNodesField)).trim().toUpperCase();
		triRailNodesTextArea.setText(triRailNodesLabel);

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

	@FXML private void handleLastAcceptedFileEnabledCheckBox(ActionEvent event)
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

	@FXML private void handleCheckQuantityOfPermitsCheckBox(ActionEvent event)
	{
		if (checkPermitCount)
		{
			checkPermitCount = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkPermitCount", false);
		}
		else
		{
			checkPermitCount = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ju_checkPermitCount", true);
		}
	}

	@FXML private void handleCheckLinearMilesCheckBox(ActionEvent event)
	{

	}

	@FXML private void handleSumOfSpeedsCheckBox(ActionEvent event)
	{

	}

	@FXML private void handleSumOfDurationsCheckBox(ActionEvent event)
	{

	}

	@FXML private void handleExcludeRestrictionsNearBridgeCheckBox (ActionEvent event)
	{

	}

	@FXML private void handleSlowOrderUpdateLastAcceptedFileButton (ActionEvent event)
	{

	}

	@FXML private void handleTrainPriorityUpdateButton(ActionEvent event)
	{

	}

	@FXML private void handleTrainCountUpdateLastAcceptedFileButton(ActionEvent event)
	{

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

	public static Boolean getCheckPermitsEnabled()
	{
		return permitsEnabled;
	}
	
	public static Boolean getCheckPermitCount()
	{
		return checkPermitCount;
	}
}