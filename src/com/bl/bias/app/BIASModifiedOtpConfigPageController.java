package com.bl.bias.app;

import java.util.prefs.Preferences;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class BIASModifiedOtpConfigPageController
{
	private static ObservableList<String> permissibleMintuesOfDelayValues =  FXCollections.observableArrayList("0", "1", "5", "10", "15");

	private static Boolean a_exceptTrainsBasedOnExternalSchedule; 
	private static Boolean b_exceptTrainsBasedOnRunTimeStatus; 
	private static Boolean c_exceptTrainsBasedOnExternalAndRunTimeStatus;
	private static Boolean d_doNotExceptTrains; 

	private static Boolean useMethodology1;
	private static Boolean useMethodology2;

	private static Boolean useOtpThresholds;

	private static Boolean suppressTrainsAndResultsWithNoEligibleReportings;
	private static String permissibleMinutesOfDelayOptionA; 
	private static String permissibleMinutesOfDelayOptionB; 

	private static Boolean a_defaultExceptTrainsBasedOnExternalSchedule = false; 
	private static Boolean b_defaultExceptTrainsBasedOnRunTimeStatus = false; 
	private static Boolean c_defaultExceptTrainsBasedOnExternalAndRunTimeStatus = false;
	private static Boolean d_defaultDoNotExceptTrains = true;

	private static Boolean defaultUseMethodology1 = true;
	private static Boolean defaultUseMethodology2 = false;

	private static Boolean defaultUseOtpThresholds = true;

	private static Boolean defaultSuppressTrainsAndResultsWithNoEligibleReportings = true;
	private static String defaultPermissibleMinutesOfDelayOptionA = "10"; 
	private static String defaultPermissibleMinutesOfDelayOptionB = "10"; 

	private static String schedulePointEntries = ""; 
	private static String actualPointEntries = "";

	final Pattern trainSymbolPattern = Pattern.compile("^[a-z-_A-Z0-9]+$");
	final Pattern timePattern = Pattern.compile("^(?:\\d|[01]\\d|2[0-3]):[0-5]\\d$");
	final Pattern nodePattern = Pattern.compile("^[a-z-_A-Z0-9.]+$");

	private static Preferences prefs;

	@FXML private Label train1Label;
	@FXML private Label node1Label;
	@FXML private Label departureTime1Label;
	@FXML private Label train2Label;
	@FXML private Label node2Label;
	@FXML private Label departureTime2Label;
	@FXML private Label train3Label;
	@FXML private Label node3Label;
	@FXML private Label departureTime3Label;

	@FXML private Label originMeasuringPointCol1;
	@FXML private Label destMeasuringPointCol1;
	@FXML private Label originMeasuringPointCol2;
	@FXML private Label destMeasuringPointCol2;
	@FXML private Label originMeasuringPointCol3;
	@FXML private Label destMeasuringPointCol3;
	
	@FXML private ComboBox<String> exceptTrainsOptionACombobox;
	@FXML private ComboBox<String> exceptTrainsOptionBCombobox;

	@FXML private RadioButton a_exceptTrainsBasedOnExternalTimesRadioButton;
	@FXML private RadioButton b_exceptTrainsBasedOnScheduledLatenessRadioButton;
	@FXML private RadioButton c_exceptTrainsBasedOnExternalAndScheduledLatenessRadioButton;
	@FXML private RadioButton d_doNotExceptTrainsRadioButton;

	@FXML private RadioButton methodology1RadioButton;
	@FXML private RadioButton methodology2RadioButton;

	@FXML private RadioButton applyOTPThresholdRadioButton;
	@FXML private RadioButton doNotApplyOTPThresholdRadioButton;

	@FXML private CheckBox suppressZeroEntryTrainsResultsCheckBox;

	@FXML private TextField train1TextField;
	@FXML private TextField node1TextField;
	@FXML private TextField departureTime1TextField;

	@FXML private TextField train2TextField;
	@FXML private TextField node2TextField;
	@FXML private TextField departureTime2TextField;

	@FXML private TextField train3TextField;
	@FXML private TextField node3TextField;
	@FXML private TextField departureTime3TextField;

	@FXML private TextField train4TextField;
	@FXML private TextField node4TextField;
	@FXML private TextField departureTime4TextField;

	@FXML private TextField train5TextField;
	@FXML private TextField node5TextField;
	@FXML private TextField departureTime5TextField;

	@FXML private TextField train6TextField;
	@FXML private TextField node6TextField;
	@FXML private TextField departureTime6TextField;

	@FXML private TextField train7TextField;
	@FXML private TextField node7TextField;
	@FXML private TextField departureTime7TextField;

	@FXML private TextField train8TextField;
	@FXML private TextField node8TextField;
	@FXML private TextField departureTime8TextField;

	@FXML private TextField train9TextField;
	@FXML private TextField node9TextField;
	@FXML private TextField departureTime9TextField;

	@FXML private TextField train10TextField;
	@FXML private TextField node10TextField;
	@FXML private TextField departureTime10TextField;

	@FXML private TextField train11TextField;
	@FXML private TextField node11TextField;
	@FXML private TextField departureTime11TextField;

	@FXML private TextField train12TextField;
	@FXML private TextField node12TextField;
	@FXML private TextField departureTime12TextField;

	@FXML private TextField train13TextField;
	@FXML private TextField node13TextField;
	@FXML private TextField departureTime13TextField;

	@FXML private TextField train14TextField;
	@FXML private TextField node14TextField;
	@FXML private TextField departureTime14TextField;

	@FXML private TextField train15TextField;
	@FXML private TextField node15TextField;
	@FXML private TextField departureTime15TextField;

	@FXML private TextField train16TextField;
	@FXML private TextField node16TextField;
	@FXML private TextField departureTime16TextField;

	@FXML private TextField train17TextField;
	@FXML private TextField node17TextField;
	@FXML private TextField departureTime17TextField;

	@FXML private TextField train18TextField;
	@FXML private TextField node18TextField;
	@FXML private TextField departureTime18TextField;

	@FXML private TextField train19TextField;
	@FXML private TextField node19TextField;
	@FXML private TextField departureTime19TextField;

	@FXML private TextField train20TextField;
	@FXML private TextField node20TextField;
	@FXML private TextField departureTime20TextField;

	@FXML private TextField train21TextField;
	@FXML private TextField node21TextField;
	@FXML private TextField departureTime21TextField;

	@FXML private TextField train22TextField;
	@FXML private TextField node22TextField;
	@FXML private TextField departureTime22TextField;

	@FXML private TextField train23TextField;
	@FXML private TextField node23TextField;
	@FXML private TextField departureTime23TextField;

	@FXML private TextField train24TextField;
	@FXML private TextField node24TextField;
	@FXML private TextField departureTime24TextField;

	@FXML private TextField originNode1ActualTextField;
	@FXML private TextField destinationNode1ActualTextField;

	@FXML private TextField originNode2ActualTextField;
	@FXML private TextField destinationNode2ActualTextField;

	@FXML private TextField originNode3ActualTextField;
	@FXML private TextField destinationNode3ActualTextField;

	@FXML private TextField originNode4ActualTextField;
	@FXML private TextField destinationNode4ActualTextField;

	@FXML private TextField originNode5ActualTextField;
	@FXML private TextField destinationNode5ActualTextField;

	@FXML private TextField originNode6ActualTextField;
	@FXML private TextField destinationNode6ActualTextField;

	@FXML private TextField originNode7ActualTextField;
	@FXML private TextField destinationNode7ActualTextField;

	@FXML private TextField originNode8ActualTextField;
	@FXML private TextField destinationNode8ActualTextField;

	@FXML private TextField originNode9ActualTextField;
	@FXML private TextField destinationNode9ActualTextField;

	@FXML private TextField originNode10ActualTextField;
	@FXML private TextField destinationNode10ActualTextField;

	@FXML private TextField originNode11ActualTextField;
	@FXML private TextField destinationNode11ActualTextField;

	@FXML private TextField originNode12ActualTextField;
	@FXML private TextField destinationNode12ActualTextField;

	@FXML private TextField originNode13ActualTextField;
	@FXML private TextField destinationNode13ActualTextField;

	@FXML private TextField originNode14ActualTextField;
	@FXML private TextField destinationNode14ActualTextField;

	@FXML private TextField originNode15ActualTextField;
	@FXML private TextField destinationNode15ActualTextField;

	@FXML private TextField originNode16ActualTextField;
	@FXML private TextField destinationNode16ActualTextField;

	@FXML private TextField originNode17ActualTextField;
	@FXML private TextField destinationNode17ActualTextField;

	@FXML private TextField originNode18ActualTextField;
	@FXML private TextField destinationNode18ActualTextField;

	@FXML private TextField originNode19ActualTextField;
	@FXML private TextField destinationNode19ActualTextField;

	@FXML private TextField originNode20ActualTextField;
	@FXML private TextField destinationNode20ActualTextField;

	@FXML private TextField originNode21ActualTextField;
	@FXML private TextField destinationNode21ActualTextField;

	@FXML private TextField originNode22ActualTextField;
	@FXML private TextField destinationNode22ActualTextField;

	@FXML private TextField originNode23ActualTextField;
	@FXML private TextField destinationNode23ActualTextField;

	@FXML private TextField originNode24ActualTextField;
	@FXML private TextField destinationNode24ActualTextField;

	@FXML private Button updateEntriesForScheduledButton;
	@FXML private Button updateEntriesForActualButton;

	@FXML private void initialize()
	{
		// Check for prefs
		prefs = Preferences.userRoot().node("BIAS");	

		// See if preference is stored for checking seed trains vs external times (option A)
		if (prefs.getBoolean("mo_exceptionsOptionA", a_defaultExceptTrainsBasedOnExternalSchedule))
		{
			a_exceptTrainsBasedOnExternalSchedule = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_exceptionsOptionA", true);
			enableAllControlsForLatenessToExternalSchedule();
			exceptTrainsOptionBCombobox.setDisable(true);
			a_exceptTrainsBasedOnExternalTimesRadioButton.setSelected(true);
		}
		else
		{
			a_exceptTrainsBasedOnExternalSchedule = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_exceptionsOptionA", false);
		}

		// See if preference is stored for checking seed trains vs time at scheduled origin (option B)
		if (prefs.getBoolean("mo_exceptionsOptionB", b_defaultExceptTrainsBasedOnRunTimeStatus))
		{
			b_exceptTrainsBasedOnRunTimeStatus = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_exceptionsOptionB", true);
			disableAllControlsForLatenessToExternalSchedule();
			exceptTrainsOptionACombobox.setDisable(false);
			b_exceptTrainsBasedOnScheduledLatenessRadioButton.setSelected(true);
		}
		else
		{
			b_exceptTrainsBasedOnRunTimeStatus = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_exceptionsOptionB", false);
		}

		// See if preference is stored for checking seed trains vs time at scheduled origin and external time (option C)
		if (prefs.getBoolean("mo_exceptionsOptionC", c_defaultExceptTrainsBasedOnExternalAndRunTimeStatus))
		{
			c_exceptTrainsBasedOnExternalAndRunTimeStatus = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_exceptionsOptionC", true);
			enableAllControlsForLatenessToExternalSchedule();
			exceptTrainsOptionBCombobox.setDisable(false);
			exceptTrainsOptionACombobox.setDisable(false);
			c_exceptTrainsBasedOnExternalAndScheduledLatenessRadioButton.setSelected(true);
		}
		else
		{
			c_exceptTrainsBasedOnExternalAndRunTimeStatus = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_exceptionsOptionC", false);
		}

		// See if preference is stored for NOT excepting trains (option D)
		if (prefs.getBoolean("mo_exceptionsOptionD", d_defaultDoNotExceptTrains))
		{
			d_doNotExceptTrains = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_exceptionsOptionD", true);
			disableAllControlsForLatenessToExternalSchedule();
			exceptTrainsOptionBCombobox.setDisable(true);
			exceptTrainsOptionACombobox.setDisable(true);
			d_doNotExceptTrainsRadioButton.setSelected(true);
		}
		else
		{
			d_doNotExceptTrains = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_exceptionsOptionD", false);
		}

		// See if preference is stored for applying OTP thresholds
		if (prefs.getBoolean("mo_applyOtpThreshold", defaultUseOtpThresholds))
		{
			useOtpThresholds = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_applyOtpThreshold", true);
			applyOTPThresholdRadioButton.setSelected(true);
		}
		else
		{
			useOtpThresholds = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_applyOtpThreshold", false);
			doNotApplyOTPThresholdRadioButton.setSelected(true);
		}

		// See if preference is stored for applying methodology 1
		if (prefs.getBoolean("mo_useMethodology1", defaultUseMethodology1))
		{
			useMethodology1 = true;
			useMethodology2 = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_useMethodology1", true);
			methodology1RadioButton.setSelected(true);
		}
		
		// See if preference is stored for applying methodology 2
		if (prefs.getBoolean("mo_useMethodology2", defaultUseMethodology2))
		{
			useMethodology1 = false;
			useMethodology2 = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_useMethodology2", true);
			methodology2RadioButton.setSelected(true);
		}

		// See if permissible minutes of delay is stored -- option A
		exceptTrainsOptionACombobox.setItems(permissibleMintuesOfDelayValues);

		boolean permissibleMintuesOfDelayValuesExistsA = prefs.get("mo_permissibleMinutesOfDelayA", null) != null;
		if (permissibleMintuesOfDelayValuesExistsA)
		{
			exceptTrainsOptionACombobox.getSelectionModel().select(prefs.get("mo_permissibleMinutesOfDelayA", defaultPermissibleMinutesOfDelayOptionA));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mo_permissibleMinutesOfDelayA", defaultPermissibleMinutesOfDelayOptionA);
			exceptTrainsOptionACombobox.getSelectionModel().select(defaultPermissibleMinutesOfDelayOptionA);
		}
		permissibleMinutesOfDelayOptionA = prefs.get("mo_permissibleMinutesOfDelayA", defaultPermissibleMinutesOfDelayOptionA);

		// See if permissible minutes of delay is stored -- option B
		exceptTrainsOptionBCombobox.setItems(permissibleMintuesOfDelayValues);

		boolean permissibleMintuesOfDelayValuesExistsB = prefs.get("mo_permissibleMinutesOfDelayB", null) != null;
		if (permissibleMintuesOfDelayValuesExistsB)
		{
			exceptTrainsOptionBCombobox.getSelectionModel().select(prefs.get("mo_permissibleMinutesOfDelayB", defaultPermissibleMinutesOfDelayOptionB));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mo_permissibleMinutesOfDelayB", defaultPermissibleMinutesOfDelayOptionB);
			exceptTrainsOptionBCombobox.getSelectionModel().select(defaultPermissibleMinutesOfDelayOptionB);
		}
		permissibleMinutesOfDelayOptionB = prefs.get("mo_permissibleMinutesOfDelayB", defaultPermissibleMinutesOfDelayOptionB);

		// See if scheduled entries are stored
		if ((prefs.get("mo_schedulePointEntries", "") != null) && (prefs.get("mo_schedulePointEntries", "") != ""))
		{
			schedulePointEntries = prefs.get("mo_schedulePointEntries", "");
			String schedulePointEntry[] = schedulePointEntries.split(",");

			for (int i = 0; i <  schedulePointEntry.length / 3; i++)
			{
				if (i == 0)
				{
					train1TextField.setText(schedulePointEntry[i*3]);
					node1TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime1TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 1)
				{
					train2TextField.setText(schedulePointEntry[i*3]);
					node2TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime2TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 2)
				{
					train3TextField.setText(schedulePointEntry[i*3]);
					node3TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime3TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 3)
				{
					train4TextField.setText(schedulePointEntry[i*3]);
					node4TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime4TextField.setText(schedulePointEntry[(i*3) + 2]);
				}

				else if (i == 4)
				{
					train5TextField.setText(schedulePointEntry[i*3]);
					node5TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime5TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 5)
				{
					train6TextField.setText(schedulePointEntry[i*3]);
					node6TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime6TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 6)
				{
					train7TextField.setText(schedulePointEntry[i*3]);
					node7TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime7TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 7)
				{
					train8TextField.setText(schedulePointEntry[i*3]);
					node8TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime8TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 8)
				{
					train9TextField.setText(schedulePointEntry[i*3]);
					node9TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime9TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 9)
				{
					train10TextField.setText(schedulePointEntry[i*3]);
					node10TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime10TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 10)
				{
					train11TextField.setText(schedulePointEntry[i*3]);
					node11TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime11TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 11)
				{
					train12TextField.setText(schedulePointEntry[i*3]);
					node12TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime12TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 12)
				{
					train13TextField.setText(schedulePointEntry[i*3]);
					node13TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime13TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 13)
				{
					train14TextField.setText(schedulePointEntry[i*3]);
					node14TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime14TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 14)
				{
					train15TextField.setText(schedulePointEntry[i*3]);
					node15TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime15TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 15)
				{
					train16TextField.setText(schedulePointEntry[i*3]);
					node16TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime16TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 16)
				{
					train17TextField.setText(schedulePointEntry[i*3]);
					node17TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime17TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 17)
				{
					train18TextField.setText(schedulePointEntry[i*3]);
					node18TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime18TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 18)
				{
					train19TextField.setText(schedulePointEntry[i*3]);
					node19TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime19TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 19)
				{
					train20TextField.setText(schedulePointEntry[i*3]);
					node20TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime20TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 20)
				{
					train21TextField.setText(schedulePointEntry[i*3]);
					node21TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime21TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 21)
				{
					train22TextField.setText(schedulePointEntry[i*3]);
					node22TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime22TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 22)
				{
					train23TextField.setText(schedulePointEntry[i*3]);
					node23TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime23TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
				else if (i == 23)
				{
					train24TextField.setText(schedulePointEntry[i*3]);
					node24TextField.setText(schedulePointEntry[(i*3) + 1]);
					departureTime24TextField.setText(schedulePointEntry[(i*3) + 2]);
				}
			}
		}

		// See if preference is stored for suppressing showing results and trains when no eligible reportings found
		if ((prefs.getBoolean("mo_suppressTrainsAndResultsWithNoEligibleReportings", defaultSuppressTrainsAndResultsWithNoEligibleReportings)))
		{
			suppressTrainsAndResultsWithNoEligibleReportings = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_suppressTrainsAndResultsWithNoEligibleReportings", true);
			suppressZeroEntryTrainsResultsCheckBox.setSelected(true);
		}
		else
		{
			suppressTrainsAndResultsWithNoEligibleReportings = false;
			suppressZeroEntryTrainsResultsCheckBox.setSelected(false);
		}

		// See if actual comparison entries are stored
		if ((prefs.get("mo_actualPointEntries", "") != null) && (prefs.get("mo_actualPointEntries", "") != ""))
		{
			actualPointEntries = prefs.get("mo_actualPointEntries", "");
			String actualScheduleEntry[] = actualPointEntries.split(",");

			for (int i = 0; i < actualScheduleEntry.length / 2; i++)
			{
				if (i == 0)
				{
					originNode1ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode1ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 1)
				{
					originNode2ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode2ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 2)
				{
					originNode3ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode3ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 3)
				{
					originNode4ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode4ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 4)
				{
					originNode5ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode5ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 5)
				{
					originNode6ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode6ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 6)
				{
					originNode7ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode7ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 7)
				{
					originNode8ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode8ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 8)
				{
					originNode9ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode9ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 9)
				{
					originNode10ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode10ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 10)
				{
					originNode11ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode11ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 11)
				{
					originNode12ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode12ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 12)
				{
					originNode13ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode13ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 13)
				{
					originNode14ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode14ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 14)
				{
					originNode15ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode15ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 15)
				{
					originNode16ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode16ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 16)
				{
					originNode17ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode17ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 17)
				{
					originNode18ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode18ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 18)
				{
					originNode19ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode19ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 19)
				{
					originNode20ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode20ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 20)
				{
					originNode21ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode21ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 21)
				{
					originNode22ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode22ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 22)
				{
					originNode23ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode23ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
				else if (i == 23)
				{
					originNode24ActualTextField.setText(actualScheduleEntry[i*2]);
					destinationNode24ActualTextField.setText(actualScheduleEntry[(i*2) + 1]);
				}
			}
		}
	}

	@FXML private void handleExceptTrainsOptionACombobox(ActionEvent e)
	{
		permissibleMinutesOfDelayOptionA = exceptTrainsOptionACombobox.getValue();
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("mo_permissibleMinutesOfDelayA", exceptTrainsOptionACombobox.getValue());
	}

	@FXML private void handleExceptTrainsOptionBCombobox(ActionEvent e)
	{
		permissibleMinutesOfDelayOptionB = exceptTrainsOptionBCombobox.getValue();
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("mo_permissibleMinutesOfDelayB", exceptTrainsOptionBCombobox.getValue());
	}

	@FXML private void handleA_exceptTrainsBasedOnExternalTimesRadioButton(ActionEvent e)
	{
		a_exceptTrainsBasedOnExternalSchedule = true;
		b_exceptTrainsBasedOnRunTimeStatus = false;
		c_exceptTrainsBasedOnExternalAndRunTimeStatus = false;
		d_doNotExceptTrains = false;

		enableAllControlsForLatenessToExternalSchedule();
		exceptTrainsOptionBCombobox.setDisable(true);
		exceptTrainsOptionACombobox.setDisable(false);
		
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.putBoolean("mo_exceptionsOptionA", true);
			prefs.putBoolean("mo_exceptionsOptionB", false);
			prefs.putBoolean("mo_exceptionsOptionC", false);
			prefs.putBoolean("mo_exceptionsOptionD", false);
		}
	}

	@FXML private void handleB_exceptTrainsBasedOnScheduledLatenessRadioButton(ActionEvent e)
	{
		a_exceptTrainsBasedOnExternalSchedule = false;
		b_exceptTrainsBasedOnRunTimeStatus = true;
		c_exceptTrainsBasedOnExternalAndRunTimeStatus = false;
		d_doNotExceptTrains = false;

		disableAllControlsForLatenessToExternalSchedule();
		exceptTrainsOptionBCombobox.setDisable(false);
		exceptTrainsOptionACombobox.setDisable(true);
		
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.putBoolean("mo_exceptionsOptionA", false);
			prefs.putBoolean("mo_exceptionsOptionB", true);
			prefs.putBoolean("mo_exceptionsOptionC", false);
			prefs.putBoolean("mo_exceptionsOptionD", false);
		}
	}

	@FXML private void handleC_exceptTrainsBasedOnExternalAndScheduledLatenessRadioButton(ActionEvent e)
	{
		a_exceptTrainsBasedOnExternalSchedule = false;
		b_exceptTrainsBasedOnRunTimeStatus = false;
		c_exceptTrainsBasedOnExternalAndRunTimeStatus = true;
		d_doNotExceptTrains = false;

		enableAllControlsForLatenessToExternalSchedule();
		exceptTrainsOptionBCombobox.setDisable(false);
		exceptTrainsOptionACombobox.setDisable(false);
		
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.putBoolean("mo_exceptionsOptionA", false);
			prefs.putBoolean("mo_exceptionsOptionB", false);
			prefs.putBoolean("mo_exceptionsOptionC", true);
			prefs.putBoolean("mo_exceptionsOptionD", false);
		}
	}

	@FXML private void handleD_doNotExceptTrainsRadioButton(ActionEvent e)
	{
		a_exceptTrainsBasedOnExternalSchedule = false;
		b_exceptTrainsBasedOnRunTimeStatus = false;
		c_exceptTrainsBasedOnExternalAndRunTimeStatus = false;
		d_doNotExceptTrains = true;

		disableAllControlsForLatenessToExternalSchedule();
		exceptTrainsOptionBCombobox.setDisable(true);
		exceptTrainsOptionACombobox.setDisable(true);
		
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.putBoolean("mo_exceptionsOptionA", false);
			prefs.putBoolean("mo_exceptionsOptionB", false);
			prefs.putBoolean("mo_exceptionsOptionC", false);
			prefs.putBoolean("mo_exceptionsOptionD", true);
		}
	}

	@FXML private void handleMethodology1RadioButton(ActionEvent e)
	{
		useMethodology1 = true;
		useMethodology2 = false;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.putBoolean("mo_useMethodology1", true);
			prefs.putBoolean("mo_useMethodology2", false);
		}
	}
	
	@FXML private void handleMethodology2RadioButton(ActionEvent e)
	{
		useMethodology1 = false;
		useMethodology2 = true;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.putBoolean("mo_useMethodology1", false);
			prefs.putBoolean("mo_useMethodology2", true);
		}
	}
	
	@FXML private void handleApplyOTPThresholdRadioButton(ActionEvent e)
	{
		useOtpThresholds = true;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putBoolean("mo_applyOtpThreshold", true);
	}

	@FXML private void handleDoNotApplyOTPThresholdRadioButton(ActionEvent e)
	{
		useOtpThresholds = false;
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.putBoolean("mo_applyOtpThreshold", false);
	}

	@FXML private void handleSuppressZeroEntryTrainsResultsCheckBox(ActionEvent e)
	{
		// See if preference is stored for suppressing showing results and trains when no eligible reportings found
		if (suppressTrainsAndResultsWithNoEligibleReportings)
		{
			suppressTrainsAndResultsWithNoEligibleReportings = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_suppressTrainsAndResultsWithNoEligibleReportings", false);
		}
		else
		{
			suppressTrainsAndResultsWithNoEligibleReportings = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_suppressTrainsAndResultsWithNoEligibleReportings", true);
		}
	}

	// Schedule Entry 1
	@FXML private void handleTextChangedTrain1TextField()
	{
		train1TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain1TextField()
	{
		String origText = train1TextField.getText().trim();
		train1TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train1TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode1TextField()
	{
		node1TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode1TextField()
	{
		String origText = node1TextField.getText().trim();
		node1TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node1TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime1TextField()
	{
		departureTime1TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime1TextField()
	{
		String origText = departureTime1TextField.getText().trim();
		departureTime1TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime1TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 2
	@FXML private void handleTextChangedTrain2TextField()
	{
		train2TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain2TextField()
	{
		String origText = train2TextField.getText().trim();
		train2TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train2TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train2TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode2TextField()
	{
		node2TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode2TextField()
	{
		String origText = node2TextField.getText().trim();
		node2TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node2TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node2TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime2TextField()
	{
		departureTime2TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime2TextField()
	{
		String origText = departureTime2TextField.getText().trim();
		departureTime2TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime2TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime2TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 3
	@FXML private void handleTextChangedTrain3TextField()
	{
		train3TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain3TextField()
	{
		String origText = train3TextField.getText().trim();
		train3TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train3TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train3TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode3TextField()
	{
		node3TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode3TextField()
	{
		String origText = node3TextField.getText().trim();
		node3TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node3TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node3TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime3TextField()
	{
		departureTime3TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime3TextField()
	{
		String origText = departureTime3TextField.getText().trim();
		departureTime3TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime3TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime3TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 4
	@FXML private void handleTextChangedTrain4TextField()
	{
		train4TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain4TextField()
	{
		String origText = train4TextField.getText().trim();
		train4TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train4TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train4TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode4TextField()
	{
		node4TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode4TextField()
	{
		String origText = node4TextField.getText().trim();
		node4TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node4TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node4TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime4TextField()
	{
		departureTime4TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime4TextField()
	{
		String origText = departureTime4TextField.getText().trim();
		departureTime4TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime4TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime4TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 5
	@FXML private void handleTextChangedTrain5TextField()
	{
		train5TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain5TextField()
	{
		String origText = train5TextField.getText().trim();
		train5TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train5TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train5TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode5TextField()
	{
		node5TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode5TextField()
	{
		String origText = node5TextField.getText().trim();
		node5TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node5TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node5TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime5TextField()
	{
		departureTime5TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime5TextField()
	{
		String origText = departureTime5TextField.getText().trim();
		departureTime5TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime5TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime5TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 6
	@FXML private void handleTextChangedTrain6TextField()
	{
		train6TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain6TextField()
	{
		String origText = train6TextField.getText().trim();
		train6TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train6TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train6TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode6TextField()
	{
		node6TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode6TextField()
	{
		String origText = node6TextField.getText().trim();
		node6TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node6TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node6TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime6TextField()
	{
		departureTime6TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime6TextField()
	{
		String origText = departureTime6TextField.getText().trim();
		departureTime6TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime6TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime6TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 7
	@FXML private void handleTextChangedTrain7TextField()
	{
		train7TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain7TextField()
	{
		String origText = train7TextField.getText().trim();
		train7TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train7TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train7TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode7TextField()
	{
		node7TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode7TextField()
	{
		String origText = node7TextField.getText().trim();
		node7TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node7TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node7TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime7TextField()
	{
		departureTime7TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime7TextField()
	{
		String origText = departureTime7TextField.getText().trim();
		departureTime7TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime7TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime7TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 8
	@FXML private void handleTextChangedTrain8TextField()
	{
		train8TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain8TextField()
	{
		String origText = train8TextField.getText().trim();
		train8TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train8TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train8TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode8TextField()
	{
		node8TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode8TextField()
	{
		String origText = node8TextField.getText().trim();
		node8TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node8TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node8TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime8TextField()
	{
		departureTime8TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime8TextField()
	{
		String origText = departureTime8TextField.getText().trim();
		departureTime8TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime8TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime8TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 9
	@FXML private void handleTextChangedTrain9TextField()
	{
		train9TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain9TextField()
	{
		String origText = train9TextField.getText().trim();
		train9TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train9TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train9TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode9TextField()
	{
		node9TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode9TextField()
	{
		String origText = node9TextField.getText().trim();
		node9TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node9TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node9TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime9TextField()
	{
		departureTime9TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime9TextField()
	{
		String origText = departureTime9TextField.getText().trim();
		departureTime9TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime9TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime9TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 10
	@FXML private void handleTextChangedTrain10TextField()
	{
		train10TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain10TextField()
	{
		String origText = train10TextField.getText().trim();
		train10TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train10TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train10TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode10TextField()
	{
		node10TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode10TextField()
	{
		String origText = node10TextField.getText().trim();
		node10TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node10TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node10TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime10TextField()
	{
		departureTime10TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime10TextField()
	{
		String origText = departureTime10TextField.getText().trim();
		departureTime10TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime10TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime10TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 11
	@FXML private void handleTextChangedTrain11TextField()
	{
		train11TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain11TextField()
	{
		String origText = train11TextField.getText().trim();
		train11TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train11TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train11TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode11TextField()
	{
		node11TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode11TextField()
	{
		String origText = node11TextField.getText().trim();
		node11TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node11TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node11TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime11TextField()
	{
		departureTime11TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime11TextField()
	{
		String origText = departureTime11TextField.getText().trim();
		departureTime11TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime11TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime11TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 12
	@FXML private void handleTextChangedTrain12TextField()
	{
		train12TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain12TextField()
	{
		String origText = train12TextField.getText().trim();
		train12TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train12TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train12TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode12TextField()
	{
		node12TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode12TextField()
	{
		String origText = node12TextField.getText().trim();
		node12TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node12TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node12TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime12TextField()
	{
		departureTime12TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime12TextField()
	{
		String origText = departureTime12TextField.getText().trim();
		departureTime12TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime12TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime12TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 13
	@FXML private void handleTextChangedTrain13TextField()
	{
		train13TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain13TextField()
	{
		String origText = train13TextField.getText().trim();
		train13TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train13TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train13TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode13TextField()
	{
		node13TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode13TextField()
	{
		String origText = node13TextField.getText().trim();
		node13TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node13TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node13TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime13TextField()
	{
		departureTime13TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime13TextField()
	{
		String origText = departureTime13TextField.getText().trim();
		departureTime13TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime13TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime13TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 14
	@FXML private void handleTextChangedTrain14TextField()
	{
		train14TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain14TextField()
	{
		String origText = train14TextField.getText().trim();
		train14TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train14TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train14TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode14TextField()
	{
		node14TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode14TextField()
	{
		String origText = node14TextField.getText().trim();
		node14TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node14TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node14TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime14TextField()
	{
		departureTime14TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime14TextField()
	{
		String origText = departureTime14TextField.getText().trim();
		departureTime14TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime14TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime14TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 15
	@FXML private void handleTextChangedTrain15TextField()
	{
		train15TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain15TextField()
	{
		String origText = train15TextField.getText().trim();
		train15TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train15TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train15TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode15TextField()
	{
		node15TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode15TextField()
	{
		String origText = node15TextField.getText().trim();
		node15TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node15TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node15TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime15TextField()
	{
		departureTime15TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime15TextField()
	{
		String origText = departureTime15TextField.getText().trim();
		departureTime15TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime15TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime15TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 16
	@FXML private void handleTextChangedTrain16TextField()
	{
		train16TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain16TextField()
	{
		String origText = train16TextField.getText().trim();
		train16TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train16TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train16TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode16TextField()
	{
		node16TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode16TextField()
	{
		String origText = node16TextField.getText().trim();
		node16TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node16TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node16TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime16TextField()
	{
		departureTime16TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime16TextField()
	{
		String origText = departureTime16TextField.getText().trim();
		departureTime16TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime16TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime16TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 17
	@FXML private void handleTextChangedTrain17TextField()
	{
		train17TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain17TextField()
	{
		String origText = train17TextField.getText().trim();
		train17TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train17TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train17TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode17TextField()
	{
		node17TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode17TextField()
	{
		String origText = node17TextField.getText().trim();
		node17TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node17TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node17TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime17TextField()
	{
		departureTime17TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime17TextField()
	{
		String origText = departureTime17TextField.getText().trim();
		departureTime17TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime17TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime17TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 18
	@FXML private void handleTextChangedTrain18TextField()
	{
		train18TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain18TextField()
	{
		String origText = train18TextField.getText().trim();
		train18TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train18TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train18TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode18TextField()
	{
		node18TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode18TextField()
	{
		String origText = node18TextField.getText().trim();
		node18TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node18TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node18TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime18TextField()
	{
		departureTime18TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime18TextField()
	{
		String origText = departureTime18TextField.getText().trim();
		departureTime18TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime18TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime18TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 19
	@FXML private void handleTextChangedTrain19TextField()
	{
		train19TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain19TextField()
	{
		String origText = train19TextField.getText().trim();
		train19TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train19TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train19TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode19TextField()
	{
		node19TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode19TextField()
	{
		String origText = node19TextField.getText().trim();
		node19TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node19TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node19TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime19TextField()
	{
		departureTime19TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime19TextField()
	{
		String origText = departureTime19TextField.getText().trim();
		departureTime19TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime19TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime19TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 20
	@FXML private void handleTextChangedTrain20TextField()
	{
		train20TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain20TextField()
	{
		String origText = train20TextField.getText().trim();
		train20TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train20TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train20TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode20TextField()
	{
		node20TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode20TextField()
	{
		String origText = node20TextField.getText().trim();
		node20TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node20TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node20TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime20TextField()
	{
		departureTime20TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime20TextField()
	{
		String origText = departureTime20TextField.getText().trim();
		departureTime20TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime20TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime20TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 21
	@FXML private void handleTextChangedTrain21TextField()
	{
		train21TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain21TextField()
	{
		String origText = train21TextField.getText().trim();
		train21TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train21TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train21TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode21TextField()
	{
		node21TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode21TextField()
	{
		String origText = node21TextField.getText().trim();
		node21TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node21TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node21TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime21TextField()
	{
		departureTime21TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime21TextField()
	{
		String origText = departureTime21TextField.getText().trim();
		departureTime21TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime21TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime21TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 22
	@FXML private void handleTextChangedTrain22TextField()
	{
		train22TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain22TextField()
	{
		String origText = train22TextField.getText().trim();
		train22TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train22TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train22TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode22TextField()
	{
		node22TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode22TextField()
	{
		String origText = node22TextField.getText().trim();
		node22TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node22TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node22TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime22TextField()
	{
		departureTime22TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime22TextField()
	{
		String origText = departureTime22TextField.getText().trim();
		departureTime22TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime22TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime22TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 23
	@FXML private void handleTextChangedTrain23TextField()
	{
		train23TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain23TextField()
	{
		String origText = train23TextField.getText().trim();
		train23TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train23TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train23TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode23TextField()
	{
		node23TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode23TextField()
	{
		String origText = node23TextField.getText().trim();
		node23TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node23TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node23TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime23TextField()
	{
		departureTime23TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime23TextField()
	{
		String origText = departureTime23TextField.getText().trim();
		departureTime23TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime23TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime23TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Schedule Entry 24
	@FXML private void handleTextChangedTrain24TextField()
	{
		train24TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTrain24TextField()
	{
		String origText = train24TextField.getText().trim();
		train24TextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(trainSymbolPattern.toString()))
			train24TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			train24TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedNode24TextField()
	{
		node24TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleNode24TextField()
	{
		String origText = node24TextField.getText().trim();
		node24TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			node24TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			node24TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDepartureTime24TextField()
	{
		departureTime24TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDepartureTime24TextField()
	{
		String origText = departureTime24TextField.getText().trim();
		departureTime24TextField.setText(origText.toUpperCase());	
		if (origText.toUpperCase().matches(nodePattern.toString()))
			departureTime24TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			departureTime24TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Update entries button for scheduled analysis
	@FXML private void handleUpdateEntriesForScheduledButton(ActionEvent e)
	{
		Boolean inputsConform = true;

		//Entry 1
		if (train1TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train1TextField.getText().trim();
			train1TextField.setText(origText.toUpperCase());
			train1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train1TextField.getText().trim().equals(""))
		{
			train1TextField.clear();
		}
		else
			inputsConform = false;

		if (node1TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node1TextField.getText().trim();
			node1TextField.setText(origText.toUpperCase());
			node1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node1TextField.getText().trim().equals(""))
		{
			node1TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime1TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime1TextField.getText().trim();
			departureTime1TextField.setText(origText.toUpperCase());
			departureTime1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime1TextField.getText().trim().equals(""))
		{
			departureTime1TextField.clear();
		}
		else
			inputsConform = false;

		//Entry 2
		if (train2TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train2TextField.getText().trim();
			train2TextField.setText(origText.toUpperCase());
			train2TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train2TextField.getText().trim().equals(""))
		{
			train2TextField.clear();
		}
		else
			inputsConform = false;

		if (node2TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node2TextField.getText().trim();
			node2TextField.setText(origText.toUpperCase());
			node2TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node2TextField.getText().trim().equals(""))
		{
			node2TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime2TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime2TextField.getText().trim();
			departureTime2TextField.setText(origText.toUpperCase());
			departureTime2TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime2TextField.getText().trim().equals(""))
		{
			departureTime2TextField.clear();
		}
		else
			inputsConform = false;

		//Entry 3
		if (train3TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train3TextField.getText().trim();
			train3TextField.setText(origText.toUpperCase());
			train3TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train3TextField.getText().trim().equals(""))
		{
			train3TextField.clear();
		}
		else
			inputsConform = false;

		if (node3TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node3TextField.getText().trim();
			node3TextField.setText(origText.toUpperCase());
			node3TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node3TextField.getText().trim().equals(""))
		{
			node3TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime3TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime3TextField.getText().trim();
			departureTime3TextField.setText(origText.toUpperCase());
			departureTime3TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime3TextField.getText().trim().equals(""))
		{
			departureTime3TextField.clear();
		}
		else
			inputsConform = false;

		//Entry 4
		if (train4TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train4TextField.getText().trim();
			train4TextField.setText(origText.toUpperCase());
			train4TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train4TextField.getText().trim().equals(""))
		{
			train4TextField.clear();
		}
		else
			inputsConform = false;

		if (node4TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node4TextField.getText().trim();
			node4TextField.setText(origText.toUpperCase());
			node4TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node4TextField.getText().trim().equals(""))
		{
			node4TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime4TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime4TextField.getText().trim();
			departureTime4TextField.setText(origText.toUpperCase());
			departureTime4TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime4TextField.getText().trim().equals(""))
		{
			departureTime4TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 5
		if (train5TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train5TextField.getText().trim();
			train5TextField.setText(origText.toUpperCase());
			train5TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train5TextField.getText().trim().equals(""))
		{
			train5TextField.clear();
		}
		else
			inputsConform = false;

		if (node5TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node5TextField.getText().trim();
			node5TextField.setText(origText.toUpperCase());
			node5TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node5TextField.getText().trim().equals(""))
		{
			node5TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime5TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime5TextField.getText().trim();
			departureTime5TextField.setText(origText.toUpperCase());
			departureTime5TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime5TextField.getText().trim().equals(""))
		{
			departureTime5TextField.clear();
		}
		else
			inputsConform = false;

		//Entry 6
		if (train6TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train6TextField.getText().trim();
			train6TextField.setText(origText.toUpperCase());
			train6TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train6TextField.getText().trim().equals(""))
		{
			train6TextField.clear();
		}
		else
			inputsConform = false;

		if (node6TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node6TextField.getText().trim();
			node6TextField.setText(origText.toUpperCase());
			node6TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node6TextField.getText().trim().equals(""))
		{
			node6TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime6TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime6TextField.getText().trim();
			departureTime6TextField.setText(origText.toUpperCase());
			departureTime6TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime6TextField.getText().trim().equals(""))
		{
			departureTime6TextField.clear();
		}
		else
			inputsConform = false;

		//Entry 7
		if (train7TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train7TextField.getText().trim();
			train7TextField.setText(origText.toUpperCase());
			train7TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train7TextField.getText().trim().equals(""))
		{
			train7TextField.clear();
		}
		else
			inputsConform = false;

		if (node7TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node7TextField.getText().trim();
			node7TextField.setText(origText.toUpperCase());
			node7TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node7TextField.getText().trim().equals(""))
		{
			node7TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime7TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime7TextField.getText().trim();
			departureTime7TextField.setText(origText.toUpperCase());
			departureTime7TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime7TextField.getText().trim().equals(""))
		{
			departureTime7TextField.clear();
		}
		else
			inputsConform = false;

		//Entry 8
		if (train8TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train8TextField.getText().trim();
			train8TextField.setText(origText.toUpperCase());
			train8TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train8TextField.getText().trim().equals(""))
		{
			train8TextField.clear();
		}
		else
			inputsConform = false;

		if (node8TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node8TextField.getText().trim();
			node8TextField.setText(origText.toUpperCase());
			node8TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node8TextField.getText().trim().equals(""))
		{
			node8TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime8TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime8TextField.getText().trim();
			departureTime8TextField.setText(origText.toUpperCase());
			departureTime8TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime8TextField.getText().trim().equals(""))
		{
			departureTime8TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 9
		if (train9TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train9TextField.getText().trim();
			train9TextField.setText(origText.toUpperCase());
			train9TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train9TextField.getText().trim().equals(""))
		{
			train9TextField.clear();
		}
		else
			inputsConform = false;

		if (node9TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node9TextField.getText().trim();
			node9TextField.setText(origText.toUpperCase());
			node9TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node9TextField.getText().trim().equals(""))
		{
			node9TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime9TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime9TextField.getText().trim();
			departureTime9TextField.setText(origText.toUpperCase());
			departureTime9TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime9TextField.getText().trim().equals(""))
		{
			departureTime9TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 10
		if (train10TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train10TextField.getText().trim();
			train10TextField.setText(origText.toUpperCase());
			train10TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train10TextField.getText().trim().equals(""))
		{
			train10TextField.clear();
		}
		else
			inputsConform = false;

		if (node10TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node10TextField.getText().trim();
			node10TextField.setText(origText.toUpperCase());
			node10TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node10TextField.getText().trim().equals(""))
		{
			node10TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime10TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime10TextField.getText().trim();
			departureTime10TextField.setText(origText.toUpperCase());
			departureTime10TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime10TextField.getText().trim().equals(""))
		{
			departureTime10TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 11
		if (train11TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train11TextField.getText().trim();
			train11TextField.setText(origText.toUpperCase());
			train11TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train11TextField.getText().trim().equals(""))
		{
			train11TextField.clear();
		}
		else
			inputsConform = false;

		if (node11TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node11TextField.getText().trim();
			node11TextField.setText(origText.toUpperCase());
			node11TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node11TextField.getText().trim().equals(""))
		{
			node11TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime11TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime11TextField.getText().trim();
			departureTime11TextField.setText(origText.toUpperCase());
			departureTime11TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime11TextField.getText().trim().equals(""))
		{
			departureTime11TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 12
		if (train12TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train12TextField.getText().trim();
			train12TextField.setText(origText.toUpperCase());
			train12TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train12TextField.getText().trim().equals(""))
		{
			train12TextField.clear();
		}
		else
			inputsConform = false;

		if (node12TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node12TextField.getText().trim();
			node12TextField.setText(origText.toUpperCase());
			node12TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node12TextField.getText().trim().equals(""))
		{
			node12TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime12TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime12TextField.getText().trim();
			departureTime12TextField.setText(origText.toUpperCase());
			departureTime12TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime12TextField.getText().trim().equals(""))
		{
			departureTime12TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 13
		if (train13TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train13TextField.getText().trim();
			train13TextField.setText(origText.toUpperCase());
			train13TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train13TextField.getText().trim().equals(""))
		{
			train13TextField.clear();
		}
		else
			inputsConform = false;

		if (node13TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node13TextField.getText().trim();
			node13TextField.setText(origText.toUpperCase());
			node13TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node13TextField.getText().trim().equals(""))
		{
			node13TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime13TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime13TextField.getText().trim();
			departureTime13TextField.setText(origText.toUpperCase());
			departureTime13TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime13TextField.getText().trim().equals(""))
		{
			departureTime13TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 14
		if (train14TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train14TextField.getText().trim();
			train14TextField.setText(origText.toUpperCase());
			train14TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train14TextField.getText().trim().equals(""))
		{
			train14TextField.clear();
		}
		else
			inputsConform = false;

		if (node14TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node14TextField.getText().trim();
			node14TextField.setText(origText.toUpperCase());
			node14TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node14TextField.getText().trim().equals(""))
		{
			node14TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime14TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime14TextField.getText().trim();
			departureTime14TextField.setText(origText.toUpperCase());
			departureTime14TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime14TextField.getText().trim().equals(""))
		{
			departureTime14TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 15
		if (train15TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train15TextField.getText().trim();
			train15TextField.setText(origText.toUpperCase());
			train15TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train15TextField.getText().trim().equals(""))
		{
			train15TextField.clear();
		}
		else
			inputsConform = false;

		if (node15TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node15TextField.getText().trim();
			node15TextField.setText(origText.toUpperCase());
			node15TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node15TextField.getText().trim().equals(""))
		{
			node15TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime15TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime15TextField.getText().trim();
			departureTime15TextField.setText(origText.toUpperCase());
			departureTime15TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime15TextField.getText().trim().equals(""))
		{
			departureTime15TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 16
		if (train16TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train16TextField.getText().trim();
			train16TextField.setText(origText.toUpperCase());
			train16TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train16TextField.getText().trim().equals(""))
		{
			train16TextField.clear();
		}
		else
			inputsConform = false;

		if (node16TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node16TextField.getText().trim();
			node16TextField.setText(origText.toUpperCase());
			node16TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node16TextField.getText().trim().equals(""))
		{
			node16TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime16TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime16TextField.getText().trim();
			departureTime16TextField.setText(origText.toUpperCase());
			departureTime16TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime16TextField.getText().trim().equals(""))
		{
			departureTime16TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 17
		if (train17TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train17TextField.getText().trim();
			train17TextField.setText(origText.toUpperCase());
			train17TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train17TextField.getText().trim().equals(""))
		{
			train17TextField.clear();
		}
		else
			inputsConform = false;

		if (node17TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node17TextField.getText().trim();
			node17TextField.setText(origText.toUpperCase());
			node17TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node17TextField.getText().trim().equals(""))
		{
			node17TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime17TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime17TextField.getText().trim();
			departureTime17TextField.setText(origText.toUpperCase());
			departureTime17TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime17TextField.getText().trim().equals(""))
		{
			departureTime17TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 18
		if (train18TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train18TextField.getText().trim();
			train18TextField.setText(origText.toUpperCase());
			train18TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train18TextField.getText().trim().equals(""))
		{
			train18TextField.clear();
		}
		else
			inputsConform = false;

		if (node18TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node18TextField.getText().trim();
			node18TextField.setText(origText.toUpperCase());
			node18TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node18TextField.getText().trim().equals(""))
		{
			node18TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime18TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime18TextField.getText().trim();
			departureTime18TextField.setText(origText.toUpperCase());
			departureTime18TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime18TextField.getText().trim().equals(""))
		{
			departureTime18TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 19
		if (train19TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train19TextField.getText().trim();
			train19TextField.setText(origText.toUpperCase());
			train19TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train19TextField.getText().trim().equals(""))
		{
			train19TextField.clear();
		}
		else
			inputsConform = false;

		if (node19TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node19TextField.getText().trim();
			node19TextField.setText(origText.toUpperCase());
			node19TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node19TextField.getText().trim().equals(""))
		{
			node19TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime19TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime19TextField.getText().trim();
			departureTime19TextField.setText(origText.toUpperCase());
			departureTime19TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime19TextField.getText().trim().equals(""))
		{
			departureTime19TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 20
		if (train20TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train20TextField.getText().trim();
			train20TextField.setText(origText.toUpperCase());
			train20TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train20TextField.getText().trim().equals(""))
		{
			train20TextField.clear();
		}
		else
			inputsConform = false;

		if (node20TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node20TextField.getText().trim();
			node20TextField.setText(origText.toUpperCase());
			node20TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node20TextField.getText().trim().equals(""))
		{
			node20TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime20TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime20TextField.getText().trim();
			departureTime20TextField.setText(origText.toUpperCase());
			departureTime20TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime20TextField.getText().trim().equals(""))
		{
			departureTime20TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 21
		if (train21TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train21TextField.getText().trim();
			train21TextField.setText(origText.toUpperCase());
			train21TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train21TextField.getText().trim().equals(""))
		{
			train21TextField.clear();
		}
		else
			inputsConform = false;

		if (node21TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node21TextField.getText().trim();
			node21TextField.setText(origText.toUpperCase());
			node21TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node21TextField.getText().trim().equals(""))
		{
			node21TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime21TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime21TextField.getText().trim();
			departureTime21TextField.setText(origText.toUpperCase());
			departureTime21TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime21TextField.getText().trim().equals(""))
		{
			departureTime21TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 22
		if (train22TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train22TextField.getText().trim();
			train22TextField.setText(origText.toUpperCase());
			train22TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train22TextField.getText().trim().equals(""))
		{
			train22TextField.clear();
		}
		else
			inputsConform = false;

		if (node22TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node22TextField.getText().trim();
			node22TextField.setText(origText.toUpperCase());
			node22TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node22TextField.getText().trim().equals(""))
		{
			node22TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime22TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime22TextField.getText().trim();
			departureTime22TextField.setText(origText.toUpperCase());
			departureTime22TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime22TextField.getText().trim().equals(""))
		{
			departureTime22TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 23
		if (train23TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train23TextField.getText().trim();
			train23TextField.setText(origText.toUpperCase());
			train23TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train23TextField.getText().trim().equals(""))
		{
			train23TextField.clear();
		}
		else
			inputsConform = false;

		if (node23TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node23TextField.getText().trim();
			node23TextField.setText(origText.toUpperCase());
			node23TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node23TextField.getText().trim().equals(""))
		{
			node23TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime23TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime23TextField.getText().trim();
			departureTime23TextField.setText(origText.toUpperCase());
			departureTime23TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime23TextField.getText().trim().equals(""))
		{
			departureTime23TextField.clear();
		}
		else
			inputsConform = false;

		// Entry 24
		if (train24TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train24TextField.getText().trim();
			train24TextField.setText(origText.toUpperCase());
			train24TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (train24TextField.getText().trim().equals(""))
		{
			train24TextField.clear();
		}
		else
			inputsConform = false;

		if (node24TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node24TextField.getText().trim();
			node24TextField.setText(origText.toUpperCase());
			node24TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (node24TextField.getText().trim().equals(""))
		{
			node24TextField.clear();
		}
		else
			inputsConform = false;

		if (departureTime24TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime24TextField.getText().trim();
			departureTime24TextField.setText(origText.toUpperCase());
			departureTime24TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (departureTime24TextField.getText().trim().equals(""))
		{
			departureTime24TextField.clear();
		}
		else
			inputsConform = false;

		if (inputsConform)
		{
			String entriesToWriteToRegistry = "";
			if ((!train1TextField.getText().equals("")) && (!node1TextField.getText().equals(""))  && (!departureTime1TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train1TextField.getText()+","+node1TextField.getText()+","+departureTime1TextField.getText()+",";
			}
			else if ((train1TextField.getText().trim().equals("")) && (node1TextField.getText().trim().equals(""))  && (departureTime1TextField.getText().trim().equals("")))
			{
				train1TextField.clear();
				node1TextField.clear();
				departureTime1TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train2TextField.getText().equals("")) && (!node2TextField.getText().equals(""))  && (!departureTime2TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train2TextField.getText()+","+node2TextField.getText()+","+departureTime2TextField.getText()+",";
			}
			else if ((train2TextField.getText().trim().equals("")) && (node2TextField.getText().trim().equals(""))  && (departureTime2TextField.getText().trim().equals("")))
			{
				train2TextField.clear();
				node2TextField.clear();
				departureTime2TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train3TextField.getText().equals("")) && (!node3TextField.getText().equals(""))  && (!departureTime3TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train3TextField.getText()+","+node3TextField.getText()+","+departureTime3TextField.getText()+",";
			}
			else if ((train3TextField.getText().trim().equals("")) && (node3TextField.getText().trim().equals(""))  && (departureTime3TextField.getText().trim().equals("")))
			{
				train3TextField.clear();
				node3TextField.clear();
				departureTime3TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train4TextField.getText().equals("")) && (!node4TextField.getText().equals(""))  && (!departureTime4TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train4TextField.getText()+","+node4TextField.getText()+","+departureTime4TextField.getText()+",";
			}
			else if ((train4TextField.getText().trim().equals("")) && (node4TextField.getText().trim().equals(""))  && (departureTime4TextField.getText().trim().equals("")))
			{
				train4TextField.clear();
				node4TextField.clear();
				departureTime4TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train5TextField.getText().equals("")) && (!node5TextField.getText().equals(""))  && (!departureTime5TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train5TextField.getText()+","+node5TextField.getText()+","+departureTime5TextField.getText()+",";
			}
			else if ((train5TextField.getText().trim().equals("")) && (node5TextField.getText().trim().equals(""))  && (departureTime5TextField.getText().trim().equals("")))
			{
				train5TextField.clear();
				node5TextField.clear();
				departureTime5TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train6TextField.getText().equals("")) && (!node6TextField.getText().equals(""))  && (!departureTime6TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train6TextField.getText()+","+node6TextField.getText()+","+departureTime6TextField.getText()+",";
			}
			else if ((train6TextField.getText().trim().equals("")) && (node6TextField.getText().trim().equals(""))  && (departureTime6TextField.getText().trim().equals("")))
			{
				train6TextField.clear();
				node6TextField.clear();
				departureTime6TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train7TextField.getText().equals("")) && (!node7TextField.getText().equals(""))  && (!departureTime7TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train7TextField.getText()+","+node7TextField.getText()+","+departureTime7TextField.getText()+",";
			}
			else if ((train7TextField.getText().trim().equals("")) && (node7TextField.getText().trim().equals(""))  && (departureTime7TextField.getText().trim().equals("")))
			{
				train7TextField.clear();
				node7TextField.clear();
				departureTime7TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train8TextField.getText().equals("")) && (!node8TextField.getText().equals(""))  && (!departureTime8TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train8TextField.getText()+","+node8TextField.getText()+","+departureTime8TextField.getText()+",";
			}
			else if ((train8TextField.getText().trim().equals("")) && (node8TextField.getText().trim().equals(""))  && (departureTime8TextField.getText().trim().equals("")))
			{
				train8TextField.clear();
				node8TextField.clear();
				departureTime8TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train9TextField.getText().equals("")) && (!node9TextField.getText().equals(""))  && (!departureTime9TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train9TextField.getText()+","+node9TextField.getText()+","+departureTime9TextField.getText()+",";
			}
			else if ((train9TextField.getText().trim().equals("")) && (node9TextField.getText().trim().equals("")) && (departureTime9TextField.getText().trim().equals("")))
			{
				train9TextField.clear();
				node9TextField.clear();
				departureTime9TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train10TextField.getText().equals("")) && (!node10TextField.getText().equals(""))  && (!departureTime10TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train10TextField.getText()+","+node10TextField.getText()+","+departureTime10TextField.getText()+",";
			}
			else if ((train10TextField.getText().trim().equals("")) && (node10TextField.getText().trim().equals("")) && (departureTime10TextField.getText().trim().equals("")))
			{
				train10TextField.clear();
				node10TextField.clear();
				departureTime10TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train11TextField.getText().equals("")) && (!node11TextField.getText().equals(""))  && (!departureTime11TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train11TextField.getText()+","+node11TextField.getText()+","+departureTime11TextField.getText()+",";
			}
			else if ((train11TextField.getText().trim().equals("")) && (node11TextField.getText().trim().equals("")) && (departureTime11TextField.getText().trim().equals("")))
			{
				train11TextField.clear();
				node11TextField.clear();
				departureTime11TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train12TextField.getText().equals("")) && (!node12TextField.getText().equals(""))  && (!departureTime12TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train12TextField.getText()+","+node12TextField.getText()+","+departureTime12TextField.getText()+",";
			}
			else if ((train12TextField.getText().trim().equals("")) && (node12TextField.getText().trim().equals(""))  && (departureTime12TextField.getText().trim().equals("")))
			{
				train12TextField.clear();
				node12TextField.clear();
				departureTime12TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train13TextField.getText().equals("")) && (!node13TextField.getText().equals(""))  && (!departureTime13TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train13TextField.getText()+","+node13TextField.getText()+","+departureTime13TextField.getText()+",";
			}
			else if ((train13TextField.getText().trim().equals("")) && (node13TextField.getText().trim().equals("")) && (departureTime13TextField.getText().trim().equals("")))
			{
				train13TextField.clear();
				node13TextField.clear();
				departureTime13TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train14TextField.getText().equals("")) && (!node14TextField.getText().equals(""))  && (!departureTime14TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train14TextField.getText()+","+node14TextField.getText()+","+departureTime14TextField.getText()+",";
			}
			else if ((train14TextField.getText().trim().equals("")) && (node14TextField.getText().trim().equals("")) && (departureTime14TextField.getText().trim().equals("")))
			{
				train14TextField.clear();
				node14TextField.clear();
				departureTime14TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train15TextField.getText().equals("")) && (!node15TextField.getText().equals(""))  && (!departureTime15TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train15TextField.getText()+","+node15TextField.getText()+","+departureTime15TextField.getText()+",";
			}
			else if ((train15TextField.getText().trim().equals("")) && (node15TextField.getText().trim().equals("")) && (departureTime15TextField.getText().trim().equals("")))
			{
				train15TextField.clear();
				node15TextField.clear();
				departureTime15TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train16TextField.getText().equals("")) && (!node16TextField.getText().equals(""))  && (!departureTime16TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train16TextField.getText()+","+node16TextField.getText()+","+departureTime16TextField.getText()+",";
			}
			else if ((train16TextField.getText().trim().equals("")) && (node16TextField.getText().trim().equals("")) && (departureTime16TextField.getText().trim().equals("")))
			{
				train16TextField.clear();
				node16TextField.clear();
				departureTime16TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train17TextField.getText().equals("")) && (!node17TextField.getText().equals(""))  && (!departureTime17TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train17TextField.getText()+","+node17TextField.getText()+","+departureTime17TextField.getText()+",";
			}
			else if ((train17TextField.getText().trim().equals("")) && (node17TextField.getText().trim().equals("")) && (departureTime17TextField.getText().trim().equals("")))
			{
				train17TextField.clear();
				node17TextField.clear();
				departureTime17TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train18TextField.getText().equals("")) && (!node18TextField.getText().equals(""))  && (!departureTime18TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train18TextField.getText()+","+node18TextField.getText()+","+departureTime18TextField.getText()+",";
			}
			else if ((train18TextField.getText().trim().equals("")) && (node18TextField.getText().trim().equals("")) && (departureTime18TextField.getText().trim().equals("")))
			{
				train18TextField.clear();
				node18TextField.clear();
				departureTime18TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train19TextField.getText().equals("")) && (!node19TextField.getText().equals(""))  && (!departureTime19TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train19TextField.getText()+","+node19TextField.getText()+","+departureTime19TextField.getText()+",";
			}
			else if ((train19TextField.getText().trim().equals("")) && (node19TextField.getText().trim().equals("")) && (departureTime19TextField.getText().trim().equals("")))
			{
				train19TextField.clear();
				node19TextField.clear();
				departureTime19TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train20TextField.getText().equals("")) && (!node20TextField.getText().equals(""))  && (!departureTime20TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train20TextField.getText()+","+node20TextField.getText()+","+departureTime20TextField.getText()+",";
			}
			else if ((train20TextField.getText().trim().equals("")) && (node20TextField.getText().trim().equals("")) && (departureTime20TextField.getText().trim().equals("")))
			{
				train20TextField.clear();
				node20TextField.clear();
				departureTime20TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train21TextField.getText().equals("")) && (!node21TextField.getText().equals(""))  && (!departureTime21TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train21TextField.getText()+","+node21TextField.getText()+","+departureTime21TextField.getText()+",";
			}
			else if ((train21TextField.getText().trim().equals("")) && (node21TextField.getText().trim().equals("")) && (departureTime21TextField.getText().trim().equals("")))
			{
				train21TextField.clear();
				node21TextField.clear();
				departureTime21TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train22TextField.getText().equals("")) && (!node22TextField.getText().equals(""))  && (!departureTime22TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train22TextField.getText()+","+node22TextField.getText()+","+departureTime22TextField.getText()+",";
			}
			else if ((train22TextField.getText().trim().equals("")) && (node22TextField.getText().trim().equals("")) && (departureTime22TextField.getText().trim().equals("")))
			{
				train22TextField.clear();
				node22TextField.clear();
				departureTime22TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train23TextField.getText().equals("")) && (!node23TextField.getText().equals(""))  && (!departureTime23TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train23TextField.getText()+","+node23TextField.getText()+","+departureTime23TextField.getText()+",";
			}
			else if ((train23TextField.getText().trim().equals("")) && (node23TextField.getText().trim().equals("")) && (departureTime23TextField.getText().trim().equals("")))
			{
				train23TextField.clear();
				node23TextField.clear();
				departureTime23TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}
			if ((!train24TextField.getText().equals("")) && (!node24TextField.getText().equals(""))  && (!departureTime24TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train24TextField.getText()+","+node24TextField.getText()+","+departureTime24TextField.getText()+",";
			}
			else if ((train24TextField.getText().trim().equals("")) && (node24TextField.getText().trim().equals("")) && (departureTime24TextField.getText().trim().equals("")))
			{
				train24TextField.clear();
				node24TextField.clear();
				departureTime24TextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if (inputsConform)
			{
				updateEntriesForScheduledButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("mo_schedulePointEntries", entriesToWriteToRegistry);

				schedulePointEntries = entriesToWriteToRegistry;
			}
			else
				updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		}
		else
			updateEntriesForScheduledButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Update entries button
	@FXML private void handleUpdateEntriesForActualButton(ActionEvent e)
	{
		Boolean inputsConform = true;

		// Actual Schedule Entry 1
		if (originNode1ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode1ActualTextField.getText().trim();
			originNode1ActualTextField.setText(origText.toUpperCase());
			originNode1ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode1ActualTextField.getText().trim().equals(""))
		{
			originNode1ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode1ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode1ActualTextField.getText().trim();
			destinationNode1ActualTextField.setText(origText.toUpperCase());
			destinationNode1ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode1ActualTextField.getText().trim().equals(""))
		{
			destinationNode1ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 2
		if (originNode2ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode2ActualTextField.getText().trim();
			originNode2ActualTextField.setText(origText.toUpperCase());
			originNode2ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode2ActualTextField.getText().trim().equals(""))
		{
			originNode2ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode2ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode2ActualTextField.getText().trim();
			destinationNode2ActualTextField.setText(origText.toUpperCase());
			destinationNode2ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode2ActualTextField.getText().trim().equals(""))
		{
			destinationNode2ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 3
		if (originNode3ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode3ActualTextField.getText().trim();
			originNode3ActualTextField.setText(origText.toUpperCase());
			originNode3ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode3ActualTextField.getText().trim().equals(""))
		{
			originNode3ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode3ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode3ActualTextField.getText().trim();
			destinationNode3ActualTextField.setText(origText.toUpperCase());
			destinationNode3ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode3ActualTextField.getText().trim().equals(""))
		{
			destinationNode3ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 4
		if (originNode4ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode4ActualTextField.getText().trim();
			originNode4ActualTextField.setText(origText.toUpperCase());
			originNode4ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode4ActualTextField.getText().trim().equals(""))
		{
			originNode4ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode4ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode4ActualTextField.getText().trim();
			destinationNode4ActualTextField.setText(origText.toUpperCase());
			destinationNode4ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode4ActualTextField.getText().trim().equals(""))
		{
			destinationNode4ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 5
		if (originNode5ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode5ActualTextField.getText().trim();
			originNode5ActualTextField.setText(origText.toUpperCase());
			originNode5ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode5ActualTextField.getText().trim().equals(""))
		{
			originNode5ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode5ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode5ActualTextField.getText().trim();
			destinationNode5ActualTextField.setText(origText.toUpperCase());
			destinationNode5ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode5ActualTextField.getText().trim().equals(""))
		{
			destinationNode5ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 6
		if (originNode6ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode6ActualTextField.getText().trim();
			originNode6ActualTextField.setText(origText.toUpperCase());
			originNode6ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode6ActualTextField.getText().trim().equals(""))
		{
			originNode6ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode6ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode6ActualTextField.getText().trim();
			destinationNode6ActualTextField.setText(origText.toUpperCase());
			destinationNode6ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode6ActualTextField.getText().trim().equals(""))
		{
			destinationNode6ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 7
		if (originNode7ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode7ActualTextField.getText().trim();
			originNode7ActualTextField.setText(origText.toUpperCase());
			originNode7ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode7ActualTextField.getText().trim().equals(""))
		{
			originNode7ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode7ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode7ActualTextField.getText().trim();
			destinationNode7ActualTextField.setText(origText.toUpperCase());
			destinationNode7ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode7ActualTextField.getText().trim().equals(""))
		{
			destinationNode7ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 8
		if (originNode8ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode8ActualTextField.getText().trim();
			originNode8ActualTextField.setText(origText.toUpperCase());
			originNode8ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode8ActualTextField.getText().trim().equals(""))
		{
			originNode8ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode8ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode8ActualTextField.getText().trim();
			destinationNode8ActualTextField.setText(origText.toUpperCase());
			destinationNode8ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode8ActualTextField.getText().trim().equals(""))
		{
			destinationNode8ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 9
		if (originNode9ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode9ActualTextField.getText().trim();
			originNode9ActualTextField.setText(origText.toUpperCase());
			originNode9ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode9ActualTextField.getText().trim().equals(""))
		{
			originNode9ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode9ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode9ActualTextField.getText().trim();
			destinationNode9ActualTextField.setText(origText.toUpperCase());
			destinationNode9ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode9ActualTextField.getText().trim().equals(""))
		{
			destinationNode9ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 10
		if (originNode10ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode10ActualTextField.getText().trim();
			originNode10ActualTextField.setText(origText.toUpperCase());
			originNode10ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode10ActualTextField.getText().trim().equals(""))
		{
			originNode10ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode10ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode10ActualTextField.getText().trim();
			destinationNode10ActualTextField.setText(origText.toUpperCase());
			destinationNode10ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode10ActualTextField.getText().trim().equals(""))
		{
			destinationNode10ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 11
		if (originNode11ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode11ActualTextField.getText().trim();
			originNode11ActualTextField.setText(origText.toUpperCase());
			originNode11ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode11ActualTextField.getText().trim().equals(""))
		{
			originNode11ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode11ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode11ActualTextField.getText().trim();
			destinationNode11ActualTextField.setText(origText.toUpperCase());
			destinationNode11ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode11ActualTextField.getText().trim().equals(""))
		{
			destinationNode11ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 12
		if (originNode12ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode12ActualTextField.getText().trim();
			originNode12ActualTextField.setText(origText.toUpperCase());
			originNode12ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode12ActualTextField.getText().trim().equals(""))
		{
			originNode12ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode12ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode12ActualTextField.getText().trim();
			destinationNode12ActualTextField.setText(origText.toUpperCase());
			destinationNode12ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode12ActualTextField.getText().trim().equals(""))
		{
			destinationNode12ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 13
		if (originNode13ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode13ActualTextField.getText().trim();
			originNode13ActualTextField.setText(origText.toUpperCase());
			originNode13ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode13ActualTextField.getText().trim().equals(""))
		{
			originNode13ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode13ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode13ActualTextField.getText().trim();
			destinationNode13ActualTextField.setText(origText.toUpperCase());
			destinationNode13ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode13ActualTextField.getText().trim().equals(""))
		{
			destinationNode13ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 14
		if (originNode14ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode14ActualTextField.getText().trim();
			originNode14ActualTextField.setText(origText.toUpperCase());
			originNode14ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode14ActualTextField.getText().trim().equals(""))
		{
			originNode14ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode14ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode14ActualTextField.getText().trim();
			destinationNode14ActualTextField.setText(origText.toUpperCase());
			destinationNode14ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode14ActualTextField.getText().trim().equals(""))
		{
			destinationNode14ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 15
		if (originNode15ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode15ActualTextField.getText().trim();
			originNode15ActualTextField.setText(origText.toUpperCase());
			originNode15ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode15ActualTextField.getText().trim().equals(""))
		{
			originNode15ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode15ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode15ActualTextField.getText().trim();
			destinationNode15ActualTextField.setText(origText.toUpperCase());
			destinationNode15ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode15ActualTextField.getText().trim().equals(""))
		{
			destinationNode15ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 16
		if (originNode16ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode16ActualTextField.getText().trim();
			originNode16ActualTextField.setText(origText.toUpperCase());
			originNode16ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode16ActualTextField.getText().trim().equals(""))
		{
			originNode16ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode16ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode16ActualTextField.getText().trim();
			destinationNode16ActualTextField.setText(origText.toUpperCase());
			destinationNode16ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode16ActualTextField.getText().trim().equals(""))
		{
			destinationNode16ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 17
		if (originNode17ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode17ActualTextField.getText().trim();
			originNode17ActualTextField.setText(origText.toUpperCase());
			originNode17ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode17ActualTextField.getText().trim().equals(""))
		{
			originNode17ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode17ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode17ActualTextField.getText().trim();
			destinationNode17ActualTextField.setText(origText.toUpperCase());
			destinationNode17ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode17ActualTextField.getText().trim().equals(""))
		{
			destinationNode17ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 18
		if (originNode18ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode18ActualTextField.getText().trim();
			originNode18ActualTextField.setText(origText.toUpperCase());
			originNode18ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode18ActualTextField.getText().trim().equals(""))
		{
			originNode18ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode18ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode18ActualTextField.getText().trim();
			destinationNode18ActualTextField.setText(origText.toUpperCase());
			destinationNode18ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode18ActualTextField.getText().trim().equals(""))
		{
			destinationNode18ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 19
		if (originNode19ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode19ActualTextField.getText().trim();
			originNode19ActualTextField.setText(origText.toUpperCase());
			originNode19ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode19ActualTextField.getText().trim().equals(""))
		{
			originNode19ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode19ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode19ActualTextField.getText().trim();
			destinationNode19ActualTextField.setText(origText.toUpperCase());
			destinationNode19ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode19ActualTextField.getText().trim().equals(""))
		{
			destinationNode19ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 20
		if (originNode20ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode20ActualTextField.getText().trim();
			originNode20ActualTextField.setText(origText.toUpperCase());
			originNode20ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode20ActualTextField.getText().trim().equals(""))
		{
			originNode20ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode20ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode20ActualTextField.getText().trim();
			destinationNode20ActualTextField.setText(origText.toUpperCase());
			destinationNode20ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode20ActualTextField.getText().trim().equals(""))
		{
			destinationNode20ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 21
		if (originNode21ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode21ActualTextField.getText().trim();
			originNode21ActualTextField.setText(origText.toUpperCase());
			originNode21ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode21ActualTextField.getText().trim().equals(""))
		{
			originNode21ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode21ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode21ActualTextField.getText().trim();
			destinationNode21ActualTextField.setText(origText.toUpperCase());
			destinationNode21ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode21ActualTextField.getText().trim().equals(""))
		{
			destinationNode21ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 18
		if (originNode22ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode22ActualTextField.getText().trim();
			originNode22ActualTextField.setText(origText.toUpperCase());
			originNode22ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode22ActualTextField.getText().trim().equals(""))
		{
			originNode22ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode22ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode22ActualTextField.getText().trim();
			destinationNode22ActualTextField.setText(origText.toUpperCase());
			destinationNode22ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode22ActualTextField.getText().trim().equals(""))
		{
			destinationNode22ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 23
		if (originNode23ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode23ActualTextField.getText().trim();
			originNode23ActualTextField.setText(origText.toUpperCase());
			originNode23ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode23ActualTextField.getText().trim().equals(""))
		{
			originNode23ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode23ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode23ActualTextField.getText().trim();
			destinationNode23ActualTextField.setText(origText.toUpperCase());
			destinationNode23ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode23ActualTextField.getText().trim().equals(""))
		{
			destinationNode23ActualTextField.clear();
		}
		else
			inputsConform = false;

		// Actual Schedule Entry 24
		if (originNode24ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = originNode24ActualTextField.getText().trim();
			originNode24ActualTextField.setText(origText.toUpperCase());
			originNode24ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (originNode24ActualTextField.getText().trim().equals(""))
		{
			originNode24ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (destinationNode24ActualTextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = destinationNode24ActualTextField.getText().trim();
			destinationNode24ActualTextField.setText(origText.toUpperCase());
			destinationNode24ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (destinationNode24ActualTextField.getText().trim().equals(""))
		{
			destinationNode24ActualTextField.clear();
		}
		else
			inputsConform = false;

		if (inputsConform)
		{
			String entriesToWriteToRegistry = "";
			if ((!originNode1ActualTextField.getText().equals("")) && (!destinationNode1ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode1ActualTextField.getText()+","+destinationNode1ActualTextField.getText()+",";
			}
			else if ((originNode1ActualTextField.getText().trim().equals("")) && (destinationNode1ActualTextField.getText().trim().equals("")))
			{
				originNode1ActualTextField.clear();
				destinationNode1ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode2ActualTextField.getText().equals("")) && (!destinationNode2ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode2ActualTextField.getText()+","+destinationNode2ActualTextField.getText()+",";
			}
			else if ((originNode2ActualTextField.getText().trim().equals("")) && (destinationNode2ActualTextField.getText().trim().equals("")))
			{
				originNode2ActualTextField.clear();
				destinationNode2ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode3ActualTextField.getText().equals("")) && (!destinationNode3ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode3ActualTextField.getText()+","+destinationNode3ActualTextField.getText()+",";
			}
			else if ((originNode3ActualTextField.getText().trim().equals("")) && (destinationNode3ActualTextField.getText().trim().equals("")))
			{
				originNode3ActualTextField.clear();
				destinationNode3ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode4ActualTextField.getText().equals("")) && (!destinationNode4ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode4ActualTextField.getText()+","+destinationNode4ActualTextField.getText()+",";
			}
			else if ((originNode4ActualTextField.getText().trim().equals("")) && (destinationNode4ActualTextField.getText().trim().equals("")))
			{
				originNode4ActualTextField.clear();
				destinationNode4ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode5ActualTextField.getText().equals("")) && (!destinationNode5ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode5ActualTextField.getText()+","+destinationNode5ActualTextField.getText()+",";
			}
			else if ((originNode5ActualTextField.getText().trim().equals("")) && (destinationNode5ActualTextField.getText().trim().equals("")))
			{
				originNode5ActualTextField.clear();
				destinationNode5ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode6ActualTextField.getText().equals("")) && (!destinationNode6ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode6ActualTextField.getText()+","+destinationNode6ActualTextField.getText()+",";
			}
			else if ((originNode6ActualTextField.getText().trim().equals("")) && (destinationNode6ActualTextField.getText().trim().equals("")))
			{
				originNode6ActualTextField.clear();
				destinationNode6ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode7ActualTextField.getText().equals("")) && (!destinationNode7ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode7ActualTextField.getText()+","+destinationNode7ActualTextField.getText()+",";
			}
			else if ((originNode7ActualTextField.getText().trim().equals("")) && (destinationNode7ActualTextField.getText().trim().equals("")))
			{
				originNode7ActualTextField.clear();
				destinationNode7ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode8ActualTextField.getText().equals("")) && (!destinationNode8ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode8ActualTextField.getText()+","+destinationNode8ActualTextField.getText()+",";
			}
			else if ((originNode8ActualTextField.getText().trim().equals("")) && (destinationNode8ActualTextField.getText().trim().equals("")))
			{
				originNode8ActualTextField.clear();
				destinationNode8ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode9ActualTextField.getText().equals("")) && (!destinationNode9ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode9ActualTextField.getText()+","+destinationNode9ActualTextField.getText()+",";
			}
			else if ((originNode9ActualTextField.getText().trim().equals("")) && (destinationNode9ActualTextField.getText().trim().equals("")))
			{
				originNode9ActualTextField.clear();
				destinationNode9ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode10ActualTextField.getText().equals("")) && (!destinationNode10ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode10ActualTextField.getText()+","+destinationNode10ActualTextField.getText()+",";
			}
			else if ((originNode10ActualTextField.getText().trim().equals("")) && (destinationNode10ActualTextField.getText().trim().equals("")))
			{
				originNode10ActualTextField.clear();
				destinationNode10ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode11ActualTextField.getText().equals("")) && (!destinationNode11ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode11ActualTextField.getText()+","+destinationNode11ActualTextField.getText()+",";
			}
			else if ((originNode11ActualTextField.getText().trim().equals("")) && (destinationNode11ActualTextField.getText().trim().equals("")))
			{
				originNode11ActualTextField.clear();
				destinationNode11ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode12ActualTextField.getText().equals("")) && (!destinationNode12ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode12ActualTextField.getText()+","+destinationNode12ActualTextField.getText()+",";
			}
			else if ((originNode12ActualTextField.getText().trim().equals("")) && (destinationNode12ActualTextField.getText().trim().equals("")))
			{
				originNode12ActualTextField.clear();
				destinationNode12ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode13ActualTextField.getText().equals("")) && (!destinationNode13ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode13ActualTextField.getText()+","+destinationNode13ActualTextField.getText()+",";
			}
			else if ((originNode13ActualTextField.getText().trim().equals("")) && (destinationNode13ActualTextField.getText().trim().equals("")))
			{
				originNode13ActualTextField.clear();
				destinationNode13ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode14ActualTextField.getText().equals("")) && (!destinationNode14ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode14ActualTextField.getText()+","+destinationNode14ActualTextField.getText()+",";
			}
			else if ((originNode14ActualTextField.getText().trim().equals("")) && (destinationNode14ActualTextField.getText().trim().equals("")))
			{
				originNode14ActualTextField.clear();
				destinationNode14ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode15ActualTextField.getText().equals("")) && (!destinationNode15ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode15ActualTextField.getText()+","+destinationNode15ActualTextField.getText()+",";
			}
			else if ((originNode15ActualTextField.getText().trim().equals("")) && (destinationNode15ActualTextField.getText().trim().equals("")))
			{
				originNode15ActualTextField.clear();
				destinationNode15ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode16ActualTextField.getText().equals("")) && (!destinationNode16ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode16ActualTextField.getText()+","+destinationNode16ActualTextField.getText()+",";
			}
			else if ((originNode16ActualTextField.getText().trim().equals("")) && (destinationNode16ActualTextField.getText().trim().equals("")))
			{
				originNode16ActualTextField.clear();
				destinationNode16ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode17ActualTextField.getText().equals("")) && (!destinationNode17ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode17ActualTextField.getText()+","+destinationNode17ActualTextField.getText()+",";
			}
			else if ((originNode17ActualTextField.getText().trim().equals("")) && (destinationNode17ActualTextField.getText().trim().equals("")))
			{
				originNode17ActualTextField.clear();
				destinationNode17ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode18ActualTextField.getText().equals("")) && (!destinationNode18ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode18ActualTextField.getText()+","+destinationNode18ActualTextField.getText()+",";
			}
			else if ((originNode18ActualTextField.getText().trim().equals("")) && (destinationNode18ActualTextField.getText().trim().equals("")))
			{
				originNode18ActualTextField.clear();
				destinationNode18ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode19ActualTextField.getText().equals("")) && (!destinationNode19ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode19ActualTextField.getText()+","+destinationNode19ActualTextField.getText()+",";
			}
			else if ((originNode19ActualTextField.getText().trim().equals("")) && (destinationNode19ActualTextField.getText().trim().equals("")))
			{
				originNode19ActualTextField.clear();
				destinationNode19ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode20ActualTextField.getText().equals("")) && (!destinationNode20ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode20ActualTextField.getText()+","+destinationNode20ActualTextField.getText()+",";
			}
			else if ((originNode20ActualTextField.getText().trim().equals("")) && (destinationNode20ActualTextField.getText().trim().equals("")))
			{
				originNode20ActualTextField.clear();
				destinationNode20ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode21ActualTextField.getText().equals("")) && (!destinationNode21ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode21ActualTextField.getText()+","+destinationNode21ActualTextField.getText()+",";
			}
			else if ((originNode21ActualTextField.getText().trim().equals("")) && (destinationNode21ActualTextField.getText().trim().equals("")))
			{
				originNode21ActualTextField.clear();
				destinationNode21ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode22ActualTextField.getText().equals("")) && (!destinationNode22ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode22ActualTextField.getText()+","+destinationNode22ActualTextField.getText()+",";
			}
			else if ((originNode22ActualTextField.getText().trim().equals("")) && (destinationNode22ActualTextField.getText().trim().equals("")))
			{
				originNode22ActualTextField.clear();
				destinationNode22ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode23ActualTextField.getText().equals("")) && (!destinationNode23ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode23ActualTextField.getText()+","+destinationNode23ActualTextField.getText()+",";
			}
			else if ((originNode23ActualTextField.getText().trim().equals("")) && (destinationNode23ActualTextField.getText().trim().equals("")))
			{
				originNode23ActualTextField.clear();
				destinationNode23ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if ((!originNode24ActualTextField.getText().equals("")) && (!destinationNode24ActualTextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= originNode24ActualTextField.getText()+","+destinationNode24ActualTextField.getText()+",";
			}
			else if ((originNode24ActualTextField.getText().trim().equals("")) && (destinationNode24ActualTextField.getText().trim().equals("")))
			{
				originNode24ActualTextField.clear();
				destinationNode24ActualTextField.clear();
			}
			else
			{
				inputsConform = false;	
			}

			if (inputsConform)
			{
				updateEntriesForActualButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					prefs.put("mo_actualPointEntries", entriesToWriteToRegistry);

				actualPointEntries = entriesToWriteToRegistry;
			}
			else
				updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		}
		else
			updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 1 Actual
	@FXML private void handleTextChangedOrigin1ActualTextField()
	{
		originNode1ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode1ActualTextField()
	{
		String origText = originNode1ActualTextField.getText().trim();
		originNode1ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode1ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode1ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination1ActualTextField()
	{
		destinationNode1ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode1ActualTextField()
	{
		String origText = destinationNode1ActualTextField.getText().trim();
		destinationNode1ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode1ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode1ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 2 Actual
	@FXML private void handleTextChangedOrigin2ActualTextField()
	{
		originNode2ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode2ActualTextField()
	{
		String origText = originNode2ActualTextField.getText().trim();
		originNode2ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode2ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode2ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination2ActualTextField()
	{
		destinationNode2ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode2ActualTextField()
	{
		String origText = destinationNode2ActualTextField.getText().trim();
		destinationNode2ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode2ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode2ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 3 Actual
	@FXML private void handleTextChangedOrigin3ActualTextField()
	{
		originNode3ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode3ActualTextField()
	{
		String origText = originNode3ActualTextField.getText().trim();
		originNode3ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode3ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode3ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination3ActualTextField()
	{
		destinationNode3ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode3ActualTextField()
	{
		String origText = destinationNode3ActualTextField.getText().trim();
		destinationNode3ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode3ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode3ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 4 Actual
	@FXML private void handleTextChangedOrigin4ActualTextField()
	{
		originNode4ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode4ActualTextField()
	{
		String origText = originNode4ActualTextField.getText().trim();
		originNode4ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode4ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode4ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination4ActualTextField()
	{
		destinationNode4ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode4ActualTextField()
	{
		String origText = destinationNode4ActualTextField.getText().trim();
		destinationNode4ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode4ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode4ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 5 Actual
	@FXML private void handleTextChangedOrigin5ActualTextField()
	{
		originNode5ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode5ActualTextField()
	{
		String origText = originNode5ActualTextField.getText().trim();
		originNode5ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode5ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode5ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination5ActualTextField()
	{
		destinationNode5ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode5ActualTextField()
	{
		String origText = destinationNode5ActualTextField.getText().trim();
		destinationNode5ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode5ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode5ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 6 Actual
	@FXML private void handleTextChangedOrigin6ActualTextField()
	{
		originNode6ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode6ActualTextField()
	{
		String origText = originNode6ActualTextField.getText().trim();
		originNode6ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode6ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode6ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination6ActualTextField()
	{
		destinationNode6ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode6ActualTextField()
	{
		String origText = destinationNode6ActualTextField.getText().trim();
		destinationNode6ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode6ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode6ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 7 Actual
	@FXML private void handleTextChangedOrigin7ActualTextField()
	{
		originNode7ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode7ActualTextField()
	{
		String origText = originNode7ActualTextField.getText().trim();
		originNode7ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode7ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode7ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination7ActualTextField()
	{
		destinationNode7ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode7ActualTextField()
	{
		String origText = destinationNode7ActualTextField.getText().trim();
		destinationNode7ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode7ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode7ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 8 Actual
	@FXML private void handleTextChangedOrigin8ActualTextField()
	{
		originNode8ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode8ActualTextField()
	{
		String origText = originNode8ActualTextField.getText().trim();
		originNode8ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode8ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode8ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination8ActualTextField()
	{
		destinationNode8ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode8ActualTextField()
	{
		String origText = destinationNode8ActualTextField.getText().trim();
		destinationNode8ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode8ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode8ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 9 Actual
	@FXML private void handleTextChangedOrigin9ActualTextField()
	{
		originNode9ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode9ActualTextField()
	{
		String origText = originNode9ActualTextField.getText().trim();
		originNode9ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode9ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode9ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination9ActualTextField()
	{
		destinationNode9ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode9ActualTextField()
	{
		String origText = destinationNode9ActualTextField.getText().trim();
		destinationNode9ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode9ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode9ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 10 Actual
	@FXML private void handleTextChangedOrigin10ActualTextField()
	{
		originNode10ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode10ActualTextField()
	{
		String origText = originNode10ActualTextField.getText().trim();
		originNode10ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode10ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode10ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination10ActualTextField()
	{
		destinationNode10ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode10ActualTextField()
	{
		String origText = destinationNode10ActualTextField.getText().trim();
		destinationNode10ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode10ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode10ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 11 Actual
	@FXML private void handleTextChangedOrigin11ActualTextField()
	{
		originNode11ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode11ActualTextField()
	{
		String origText = originNode11ActualTextField.getText().trim();
		originNode11ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode11ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode11ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination11ActualTextField()
	{
		destinationNode11ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode11ActualTextField()
	{
		String origText = destinationNode11ActualTextField.getText().trim();
		destinationNode11ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode11ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode11ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 12 Actual
	@FXML private void handleTextChangedOrigin12ActualTextField()
	{
		originNode12ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode12ActualTextField()
	{
		String origText = originNode12ActualTextField.getText().trim();
		originNode12ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode12ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode12ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination12ActualTextField()
	{
		destinationNode12ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode12ActualTextField()
	{
		String origText = destinationNode12ActualTextField.getText().trim();
		destinationNode12ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode12ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode12ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 13 Actual
	@FXML private void handleTextChangedOrigin13ActualTextField()
	{
		originNode13ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode13ActualTextField()
	{
		String origText = originNode13ActualTextField.getText().trim();
		originNode13ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode13ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode13ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination13ActualTextField()
	{
		destinationNode13ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode13ActualTextField()
	{
		String origText = destinationNode13ActualTextField.getText().trim();
		destinationNode13ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode13ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode13ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 14 Actual
	@FXML private void handleTextChangedOrigin14ActualTextField()
	{
		originNode14ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode14ActualTextField()
	{
		String origText = originNode14ActualTextField.getText().trim();
		originNode14ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode14ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode14ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination14ActualTextField()
	{
		destinationNode14ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode14ActualTextField()
	{
		String origText = destinationNode14ActualTextField.getText().trim();
		destinationNode14ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode14ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode14ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 15 Actual
	@FXML private void handleTextChangedOrigin15ActualTextField()
	{
		originNode15ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode15ActualTextField()
	{
		String origText = originNode15ActualTextField.getText().trim();
		originNode15ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode15ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode15ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination15ActualTextField()
	{
		destinationNode15ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode15ActualTextField()
	{
		String origText = destinationNode15ActualTextField.getText().trim();
		destinationNode15ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode15ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode15ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 16 Actual
	@FXML private void handleTextChangedOrigin16ActualTextField()
	{
		originNode16ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode16ActualTextField()
	{
		String origText = originNode16ActualTextField.getText().trim();
		originNode16ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode16ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode16ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination16ActualTextField()
	{
		destinationNode16ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode16ActualTextField()
	{
		String origText = destinationNode16ActualTextField.getText().trim();
		destinationNode16ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode16ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode16ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 17 Actual
	@FXML private void handleTextChangedOrigin17ActualTextField()
	{
		originNode17ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode17ActualTextField()
	{
		String origText = originNode17ActualTextField.getText().trim();
		originNode17ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode17ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode17ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination17ActualTextField()
	{
		destinationNode17ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode17ActualTextField()
	{
		String origText = destinationNode17ActualTextField.getText().trim();
		destinationNode17ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode17ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode17ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 18 Actual
	@FXML private void handleTextChangedOrigin18ActualTextField()
	{
		originNode18ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode18ActualTextField()
	{
		String origText = originNode18ActualTextField.getText().trim();
		originNode18ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode18ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode18ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination18ActualTextField()
	{
		destinationNode18ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode18ActualTextField()
	{
		String origText = destinationNode18ActualTextField.getText().trim();
		destinationNode18ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode18ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode18ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 19 Actual
	@FXML private void handleTextChangedOrigin19ActualTextField()
	{
		originNode19ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode19ActualTextField()
	{
		String origText = originNode19ActualTextField.getText().trim();
		originNode19ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode19ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode19ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination19ActualTextField()
	{
		destinationNode19ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode19ActualTextField()
	{
		String origText = destinationNode19ActualTextField.getText().trim();
		destinationNode19ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode19ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode19ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 20 Actual
	@FXML private void handleTextChangedOrigin20ActualTextField()
	{
		originNode20ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode20ActualTextField()
	{
		String origText = originNode20ActualTextField.getText().trim();
		originNode20ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode20ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode20ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination20ActualTextField()
	{
		destinationNode20ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode20ActualTextField()
	{
		String origText = destinationNode20ActualTextField.getText().trim();
		destinationNode20ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode20ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode20ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 21 Actual
	@FXML private void handleTextChangedOrigin21ActualTextField()
	{
		originNode21ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode21ActualTextField()
	{
		String origText = originNode21ActualTextField.getText().trim();
		originNode21ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode21ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode21ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination21ActualTextField()
	{
		destinationNode21ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode21ActualTextField()
	{
		String origText = destinationNode21ActualTextField.getText().trim();
		destinationNode21ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode21ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode21ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 22 Actual
	@FXML private void handleTextChangedOrigin22ActualTextField()
	{
		originNode22ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode22ActualTextField()
	{
		String origText = originNode22ActualTextField.getText().trim();
		originNode22ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode22ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode22ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination22ActualTextField()
	{
		destinationNode22ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode22ActualTextField()
	{
		String origText = destinationNode22ActualTextField.getText().trim();
		destinationNode22ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode22ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode22ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 23 Actual
	@FXML private void handleTextChangedOrigin23ActualTextField()
	{
		originNode23ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode23ActualTextField()
	{
		String origText = originNode23ActualTextField.getText().trim();
		originNode23ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode23ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode23ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination23ActualTextField()
	{
		destinationNode23ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode23ActualTextField()
	{
		String origText = destinationNode23ActualTextField.getText().trim();
		destinationNode23ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode23ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode23ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	// Node set 24 Actual
	@FXML private void handleTextChangedOrigin24ActualTextField()
	{
		originNode24ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleOriginNode24ActualTextField()
	{
		String origText = originNode24ActualTextField.getText().trim();
		originNode24ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			originNode24ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			originNode24ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedDestination24ActualTextField()
	{
		destinationNode24ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesForActualButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleDestinationNode24ActualTextField()
	{
		String origText = destinationNode24ActualTextField.getText().trim();
		destinationNode24ActualTextField.setText(origText.toUpperCase());
		if (origText.toUpperCase().matches(nodePattern.toString()))
			destinationNode24ActualTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		else
			destinationNode24ActualTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	private void disableAllControlsForLatenessToExternalSchedule()
	{
		train1Label.setDisable(true);
		node1Label.setDisable(true);
		departureTime1Label.setDisable(true);
		train2Label.setDisable(true);
		node2Label.setDisable(true);
		departureTime2Label.setDisable(true);
		train3Label.setDisable(true);
		node3Label.setDisable(true);
		departureTime3Label.setDisable(true);
		train1TextField.setDisable(true);
		node1TextField.setDisable(true);
		departureTime1TextField.setDisable(true);
		train2TextField.setDisable(true);
		node2TextField.setDisable(true);
		departureTime2TextField.setDisable(true);
		train3TextField.setDisable(true);
		node3TextField.setDisable(true);
		departureTime3TextField.setDisable(true);
		train4TextField.setDisable(true);
		node4TextField.setDisable(true);
		departureTime4TextField.setDisable(true);
		train5TextField.setDisable(true);
		node5TextField.setDisable(true);
		departureTime5TextField.setDisable(true);
		train6TextField.setDisable(true);
		node6TextField.setDisable(true);
		departureTime6TextField.setDisable(true);
		train7TextField.setDisable(true);
		node7TextField.setDisable(true);
		departureTime7TextField.setDisable(true);
		train8TextField.setDisable(true);
		node8TextField.setDisable(true);
		departureTime8TextField.setDisable(true);
		train9TextField.setDisable(true);
		node9TextField.setDisable(true);
		departureTime9TextField.setDisable(true);
		train10TextField.setDisable(true);
		node10TextField.setDisable(true);
		departureTime10TextField.setDisable(true);
		train11TextField.setDisable(true);
		node11TextField.setDisable(true);
		departureTime11TextField.setDisable(true);
		train12TextField.setDisable(true);
		node12TextField.setDisable(true);
		departureTime12TextField.setDisable(true);
		train13TextField.setDisable(true);
		node13TextField.setDisable(true);
		departureTime13TextField.setDisable(true);
		train14TextField.setDisable(true);
		node14TextField.setDisable(true);
		departureTime14TextField.setDisable(true);
		train15TextField.setDisable(true);
		node15TextField.setDisable(true);
		departureTime15TextField.setDisable(true);
		train16TextField.setDisable(true);
		node16TextField.setDisable(true);
		departureTime16TextField.setDisable(true);
		train17TextField.setDisable(true);
		node17TextField.setDisable(true);
		departureTime17TextField.setDisable(true);
		train18TextField.setDisable(true);
		node18TextField.setDisable(true);
		departureTime18TextField.setDisable(true);
		train19TextField.setDisable(true);
		node19TextField.setDisable(true);
		departureTime19TextField.setDisable(true);
		train20TextField.setDisable(true);
		node20TextField.setDisable(true);
		departureTime20TextField.setDisable(true);
		train21TextField.setDisable(true);
		node21TextField.setDisable(true);
		departureTime21TextField.setDisable(true);
		train22TextField.setDisable(true);
		node22TextField.setDisable(true);
		departureTime22TextField.setDisable(true);
		train23TextField.setDisable(true);
		node23TextField.setDisable(true);
		departureTime23TextField.setDisable(true);
		train24TextField.setDisable(true);
		node24TextField.setDisable(true);
		departureTime24TextField.setDisable(true);

		updateEntriesForScheduledButton.setDisable(true);
	}

	private void enableAllControlsForLatenessToExternalSchedule()
	{
		train1Label.setDisable(false);
		node1Label.setDisable(false);
		departureTime1Label.setDisable(false);
		train2Label.setDisable(false);
		node2Label.setDisable(false);
		departureTime2Label.setDisable(false);
		train3Label.setDisable(false);
		node3Label.setDisable(false);
		departureTime3Label.setDisable(false);
		train1TextField.setDisable(false);
		node1TextField.setDisable(false);
		departureTime1TextField.setDisable(false);
		train2TextField.setDisable(false);
		node2TextField.setDisable(false);
		departureTime2TextField.setDisable(false);
		train3TextField.setDisable(false);
		node3TextField.setDisable(false);
		departureTime3TextField.setDisable(false);
		train4TextField.setDisable(false);
		node4TextField.setDisable(false);
		departureTime4TextField.setDisable(false);
		train5TextField.setDisable(false);
		node5TextField.setDisable(false);
		departureTime5TextField.setDisable(false);
		train6TextField.setDisable(false);
		node6TextField.setDisable(false);
		departureTime6TextField.setDisable(false);
		train7TextField.setDisable(false);
		node7TextField.setDisable(false);
		departureTime7TextField.setDisable(false);
		train8TextField.setDisable(false);
		node8TextField.setDisable(false);
		departureTime8TextField.setDisable(false);
		train9TextField.setDisable(false);
		node9TextField.setDisable(false);
		departureTime9TextField.setDisable(false);
		train10TextField.setDisable(false);
		node10TextField.setDisable(false);
		departureTime10TextField.setDisable(false);
		train11TextField.setDisable(false);
		node11TextField.setDisable(false);
		departureTime11TextField.setDisable(false);
		train12TextField.setDisable(false);
		node12TextField.setDisable(false);
		departureTime12TextField.setDisable(false);
		train13TextField.setDisable(false);
		node13TextField.setDisable(false);
		departureTime13TextField.setDisable(false);
		train14TextField.setDisable(false);
		node14TextField.setDisable(false);
		departureTime14TextField.setDisable(false);
		train15TextField.setDisable(false);
		node15TextField.setDisable(false);
		departureTime15TextField.setDisable(false);
		train16TextField.setDisable(false);
		node16TextField.setDisable(false);
		departureTime16TextField.setDisable(false);
		train17TextField.setDisable(false);
		node17TextField.setDisable(false);
		departureTime17TextField.setDisable(false);
		train18TextField.setDisable(false);
		node18TextField.setDisable(false);
		departureTime18TextField.setDisable(false);
		train19TextField.setDisable(false);
		node19TextField.setDisable(false);
		departureTime19TextField.setDisable(false);
		train20TextField.setDisable(false);
		node20TextField.setDisable(false);
		departureTime20TextField.setDisable(false);
		train21TextField.setDisable(false);
		node21TextField.setDisable(false);
		departureTime21TextField.setDisable(false);
		train22TextField.setDisable(false);
		node22TextField.setDisable(false);
		departureTime22TextField.setDisable(false);
		train23TextField.setDisable(false);
		node23TextField.setDisable(false);
		departureTime23TextField.setDisable(false);
		train24TextField.setDisable(false);
		node24TextField.setDisable(false);
		departureTime24TextField.setDisable(false);

		updateEntriesForScheduledButton.setDisable(false);
	}
	
	public static String getPermissibleMinutesOfDelayOptionAAsString()
	{
		return permissibleMinutesOfDelayOptionA;
	}
	
	public static String getPermissibleMinutesOfDelayOptionBAsString()
	{
		return permissibleMinutesOfDelayOptionB;
	}

	public static String getSchedulePointEntries()
	{
		return schedulePointEntries;
	}

	public static String getActualPointEntries()
	{
		return actualPointEntries;
	}

	public static Boolean getA_exceptTrainsBasedOnExternalSchedule()
	{
		return a_exceptTrainsBasedOnExternalSchedule;
	}
	
	public static Boolean getB_exceptTrainsBasedOnRunTimeStatus()
	{
		return b_exceptTrainsBasedOnRunTimeStatus;
	}
	
	public static Boolean getC_exceptTrainsBasedOnExternalAndRunTimeStatus()
	{
		return c_exceptTrainsBasedOnExternalAndRunTimeStatus;
	}

	public static Boolean getD_doNotExceptTrains()
	{
		return d_doNotExceptTrains;
	}

	public static Boolean getUseOtpThresholds()
	{
		return useOtpThresholds;
	}
	
	public static Boolean getUseMethodology1()
	{
		return useMethodology1;
	}
	
	public static Boolean getUseMethodology2()
	{
		return useMethodology2;
	}

	public static Boolean getSuppressTrainsAndResultsWithNoEligibleReportings()
	{
		return suppressTrainsAndResultsWithNoEligibleReportings;
	}
}