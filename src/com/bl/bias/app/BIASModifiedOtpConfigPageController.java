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

	private static Boolean checkTrainsForLateness; 
	private static Boolean generateReport;
	private static Boolean excludeTrains;
	private static String permissibleMinutesOfDelayAsString; 

	private static Boolean defaultCheckSeedTrainsForLateness = true; 
	private static Boolean defaultGenerateReport = true;
	private static String defaultPermissibleMinutesOfDelay = "5"; 

	private static String schedulePointEntries = ""; 

	final Pattern trainSymbolPattern = Pattern.compile("^[a-z-_A-Z0-9]+$");
	final Pattern timePattern = Pattern.compile("^(?:\\d|[01]\\d|2[0-3]):[0-5]\\d$");
	final Pattern nodePattern = Pattern.compile("^[a-z-_A-Z0-9.]+$");

	private static Preferences prefs;

	@FXML private Label ifNotLabel;
	@FXML private Label train1Label;
	@FXML private Label node1Label;
	@FXML private Label departureTime1Label;
	@FXML private Label train2Label;
	@FXML private Label node2Label;
	@FXML private Label departureTime2Label;
	@FXML private Label train3Label;
	@FXML private Label node3Label;
	@FXML private Label departureTime3Label;

	@FXML private ComboBox<String> permissibleMinutesOfDelayCombobox;

	@FXML private CheckBox checkTrainsCheckBox;

	@FXML private RadioButton generateReportRadioButton;
	@FXML private RadioButton excludeTrainsRadioButton;

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

	@FXML private Button updateEntriesButton;

	@FXML private void initialize()
	{
		// Check for prefs
		prefs = Preferences.userRoot().node("BIAS");	

		// See if preference is stored for checking seed trains lateness
		if (prefs.getBoolean("mo_checkTrainsForLateness", defaultCheckSeedTrainsForLateness))
		{
			checkTrainsForLateness = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_checkTrainsForLateness", true);
			checkTrainsCheckBox.setSelected(true);
			enableAllControls();
		}
		else
		{
			checkTrainsForLateness = false;
			checkTrainsCheckBox.setSelected(false);
			disableAllControls();
		}

		// See if permissible minutes of delay is stored
		permissibleMinutesOfDelayCombobox.setItems(permissibleMintuesOfDelayValues);

		boolean permissibleMintuesOfDelayValuesExists = prefs.get("mo_permissibleMinutesOfDelay", null) != null;
		if (permissibleMintuesOfDelayValuesExists)
		{
			permissibleMinutesOfDelayCombobox.getSelectionModel().select(prefs.get("mo_permissibleMinutesOfDelay", defaultPermissibleMinutesOfDelay));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mo_permissibleMinutesOfDelay", defaultPermissibleMinutesOfDelay);
			permissibleMinutesOfDelayCombobox.getSelectionModel().select(defaultPermissibleMinutesOfDelay);
		}
		permissibleMinutesOfDelayAsString = prefs.get("mo_permissibleMinutesOfDelay", defaultPermissibleMinutesOfDelay);

		// See if preference is stored for generating report vs excluding train from OTP
		if (prefs.getBoolean("mo_generateReport", defaultGenerateReport))
		{
			generateReport = true;
			excludeTrains = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("mo_generateReport", true);
				prefs.putBoolean("mo_excludeTrains", false);
			}
			generateReportRadioButton.setSelected(true);
		}
		else
		{
			generateReport = false;
			excludeTrains = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("mo_generateReport", false);
				prefs.putBoolean("mo_excludeTrains", true);
			}
			excludeTrainsRadioButton.setSelected(true);
		}

		// See if entries are stored
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
			}
		}
	}

	@FXML private void handlePermissibleMinutesOfDelayComboBox(ActionEvent e)
	{
		permissibleMinutesOfDelayAsString = permissibleMinutesOfDelayCombobox.getValue();
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			prefs.put("mo_permissibleMinutesOfDelay", permissibleMinutesOfDelayCombobox.getValue());
	}

	@FXML private void handleCheckTrainsCheckBox(ActionEvent e)
	{
		if (checkTrainsForLateness)
		{
			checkTrainsForLateness = false;
			disableAllControls();
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_checkTrainsForLateness", false);
		}
		else
		{
			checkTrainsForLateness = true;
			enableAllControls();
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("mo_checkTrainsForLateness", true);
		}
	}

	@FXML private void handleGenerateReportRadioButton(ActionEvent e)
	{
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.putBoolean("mo_generateReport", true);
			prefs.putBoolean("mo_excludeTrains", false);
		}
		generateReport = true;
		excludeTrains = false;
	}

	@FXML private void handleExcludeTrainsRadioButton(ActionEvent e)
	{
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.putBoolean("mo_generateReport", false);
			prefs.putBoolean("mo_excludeTrains", true);
		}
		generateReport = false;
		excludeTrains = true;
	}

	// Entry 1
	@FXML private void handleTextChangedTrain1TextField()
	{
		train1TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 2
	@FXML private void handleTextChangedTrain2TextField()
	{
		train2TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 3
	@FXML private void handleTextChangedTrain3TextField()
	{
		train3TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 4
	@FXML private void handleTextChangedTrain4TextField()
	{
		train4TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 5
	@FXML private void handleTextChangedTrain5TextField()
	{
		train5TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 6
	@FXML private void handleTextChangedTrain6TextField()
	{
		train6TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 7
	@FXML private void handleTextChangedTrain7TextField()
	{
		train7TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 8
	@FXML private void handleTextChangedTrain8TextField()
	{
		train8TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 9
	@FXML private void handleTextChangedTrain9TextField()
	{
		train9TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 10
	@FXML private void handleTextChangedTrain10TextField()
	{
		train10TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 11
	@FXML private void handleTextChangedTrain11TextField()
	{
		train11TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Entry 12
	@FXML private void handleTextChangedTrain12TextField()
	{
		train12TextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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
		updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
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

	// Update entries button
	@FXML private void handleUpdateEntriesButton(ActionEvent e)
	{
		Boolean inputsConform = true;

		//Entry 1
		if (train1TextField.getText().trim().toUpperCase().matches(trainSymbolPattern.toString()))
		{
			String origText = train1TextField.getText().trim();
			train1TextField.setText(origText.toUpperCase());
			train1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (node1TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node1TextField.getText().trim();
			node1TextField.setText(origText.toUpperCase());
			node1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime1TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime1TextField.getText().trim();
			departureTime1TextField.setText(origText.toUpperCase());
			departureTime1TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node2TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node2TextField.getText().trim();
			node2TextField.setText(origText.toUpperCase());
			node2TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime2TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime2TextField.getText().trim();
			departureTime2TextField.setText(origText.toUpperCase());
			departureTime2TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node3TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node3TextField.getText().trim();
			node3TextField.setText(origText.toUpperCase());
			node3TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime3TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime3TextField.getText().trim();
			departureTime3TextField.setText(origText.toUpperCase());
			departureTime3TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node4TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node4TextField.getText().trim();
			node4TextField.setText(origText.toUpperCase());
			node4TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime4TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime4TextField.getText().trim();
			departureTime4TextField.setText(origText.toUpperCase());
			departureTime4TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node5TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node5TextField.getText().trim();
			node5TextField.setText(origText.toUpperCase());
			node5TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime5TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime5TextField.getText().trim();
			departureTime5TextField.setText(origText.toUpperCase());
			departureTime5TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node6TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node6TextField.getText().trim();
			node6TextField.setText(origText.toUpperCase());
			node6TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime6TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime6TextField.getText().trim();
			departureTime6TextField.setText(origText.toUpperCase());
			departureTime6TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node7TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node7TextField.getText().trim();
			node7TextField.setText(origText.toUpperCase());
			node7TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime7TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime7TextField.getText().trim();
			departureTime7TextField.setText(origText.toUpperCase());
			departureTime7TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node8TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node8TextField.getText().trim();
			node8TextField.setText(origText.toUpperCase());
			node8TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime8TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime8TextField.getText().trim();
			departureTime8TextField.setText(origText.toUpperCase());
			departureTime8TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node9TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node9TextField.getText().trim();
			node9TextField.setText(origText.toUpperCase());
			node9TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime9TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime9TextField.getText().trim();
			departureTime9TextField.setText(origText.toUpperCase());
			departureTime9TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node10TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node10TextField.getText().trim();
			node10TextField.setText(origText.toUpperCase());
			node10TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime10TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime10TextField.getText().trim();
			departureTime10TextField.setText(origText.toUpperCase());
			departureTime10TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node11TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node11TextField.getText().trim();
			node11TextField.setText(origText.toUpperCase());
			node11TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime11TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime11TextField.getText().trim();
			departureTime11TextField.setText(origText.toUpperCase());
			departureTime11TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
		else
			inputsConform = false;

		if (node12TextField.getText().trim().toUpperCase().matches(nodePattern.toString()))
		{
			String origText = node12TextField.getText().trim();
			node12TextField.setText(origText.toUpperCase());
			node12TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
			inputsConform = false;

		if (departureTime12TextField.getText().trim().toUpperCase().matches(timePattern.toString()))
		{
			String origText = departureTime12TextField.getText().trim();
			departureTime12TextField.setText(origText.toUpperCase());
			departureTime12TextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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
			if ((!train2TextField.getText().equals("")) && (!node2TextField.getText().equals(""))  && (!departureTime2TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train2TextField.getText()+","+node2TextField.getText()+","+departureTime2TextField.getText()+",";
			}
			if ((!train3TextField.getText().equals("")) && (!node3TextField.getText().equals(""))  && (!departureTime3TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train3TextField.getText()+","+node3TextField.getText()+","+departureTime3TextField.getText()+",";
			}
			if ((!train4TextField.getText().equals("")) && (!node4TextField.getText().equals(""))  && (!departureTime4TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train4TextField.getText()+","+node4TextField.getText()+","+departureTime4TextField.getText()+",";
			}
			if ((!train5TextField.getText().equals("")) && (!node5TextField.getText().equals(""))  && (!departureTime5TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train5TextField.getText()+","+node5TextField.getText()+","+departureTime5TextField.getText()+",";
			}
			if ((!train6TextField.getText().equals("")) && (!node6TextField.getText().equals(""))  && (!departureTime6TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train6TextField.getText()+","+node6TextField.getText()+","+departureTime6TextField.getText()+",";
			}
			if ((!train7TextField.getText().equals("")) && (!node7TextField.getText().equals(""))  && (!departureTime7TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train7TextField.getText()+","+node7TextField.getText()+","+departureTime7TextField.getText()+",";
			}
			if ((!train8TextField.getText().equals("")) && (!node8TextField.getText().equals(""))  && (!departureTime8TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train8TextField.getText()+","+node8TextField.getText()+","+departureTime8TextField.getText()+",";
			}
			if ((!train9TextField.getText().equals("")) && (!node9TextField.getText().equals(""))  && (!departureTime9TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train9TextField.getText()+","+node9TextField.getText()+","+departureTime9TextField.getText()+",";
			}
			if ((!train10TextField.getText().equals("")) && (!node10TextField.getText().equals(""))  && (!departureTime10TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train10TextField.getText()+","+node10TextField.getText()+","+departureTime10TextField.getText()+",";
			}
			if ((!train11TextField.getText().equals("")) && (!node11TextField.getText().equals(""))  && (!departureTime11TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train11TextField.getText()+","+node11TextField.getText()+","+departureTime11TextField.getText()+",";
			}
			if ((!train12TextField.getText().equals("")) && (!node12TextField.getText().equals(""))  && (!departureTime12TextField.getText().equals("")))
			{
				entriesToWriteToRegistry+= train12TextField.getText()+","+node12TextField.getText()+","+departureTime12TextField.getText()+",";
			}

			updateEntriesButton.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("mo_schedulePointEntries", entriesToWriteToRegistry);

			schedulePointEntries = entriesToWriteToRegistry;
		}
		else
			updateEntriesButton.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	private void disableAllControls()
	{
		ifNotLabel.setDisable(true);
		generateReportRadioButton.setDisable(true);
		excludeTrainsRadioButton.setDisable(true);
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

		updateEntriesButton.setDisable(true);
	}

	private void enableAllControls()
	{
		ifNotLabel.setDisable(false);
		generateReportRadioButton.setDisable(false);
		excludeTrainsRadioButton.setDisable(false);
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
		
		updateEntriesButton.setDisable(false);
	}

	public static String getSchedulePointEntries()
	{
		return schedulePointEntries;
	}
}