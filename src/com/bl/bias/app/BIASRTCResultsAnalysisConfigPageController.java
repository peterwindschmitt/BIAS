package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BIASRTCResultsAnalysisConfigPageController 
{
	private static Preferences prefs;

	private static Boolean moveFailedFiles;
	private static Boolean prependFailedFiles;
	private static Boolean purgeFailedFiles;
	private static Boolean checkPtcEquipped;
	private static Boolean checkAtcEquipped;
	private static Boolean generateRawData;
	private static Boolean generateSummaryResults;
	private static Boolean generateGraphs;
	private static Boolean outputAsString;
	private static Boolean outputAsSeconds;
	private static Boolean outputAsSerial;

	private static Boolean defaultMoveFailedFiles = true;
	private static Boolean defaultPrependFailedFiles = false;
	private static Boolean defaultPurgeFailedFiles = false;
	private static Boolean defaultCheckPtcEquipped = true;
	private static Boolean defaultCheckAtcEquipped = true;
	private static Boolean defaultGenerateRawData = true;
	private static Boolean defaultGenerateSummaryResults = true;
	private static Boolean defaultGenerateGraphs = true;
	private static Boolean defaultOutputAsString = true;
	private static Boolean defaultOutputAsSeconds = true;
	private static Boolean defaultOutputAsSerial = true;

	private static BooleanBinding disableGenerateRawDataCheckbox;
	private static BooleanBinding disableGenerateSummaryResultsCheckbox;
	private static BooleanBinding disableGenerateGraphsCheckbox;
	private static BooleanBinding disableOutputAsStringCheckbox;
	private static BooleanBinding disableOutputAsSecondsCheckbox;
	private static BooleanBinding disableOutputAsSerialCheckbox;

	@FXML private ComboBox<String> cleanFileCombobox;

	@FXML private CheckBox ptcEquippedCheckBox;
	@FXML private CheckBox atcEquippedCheckBox;
	@FXML private CheckBox generateRawDataCheckBox;
	@FXML private CheckBox generateSummaryResultsCheckBox;
	@FXML private CheckBox generateGraphsCheckBox;
	@FXML private CheckBox outputAsStringCheckBox;
	@FXML private CheckBox outputAsSecondsCheckBox;
	@FXML private CheckBox outputAsSerialCheckBox;

	@FXML private TextArea userCategory1NameTextArea;
	@FXML private TextArea userCategory1TypesTextArea;
	@FXML private TextArea userCategory2NameTextArea;
	@FXML private TextArea userCategory2TypesTextArea;

	@FXML private Button updateUserCategory1Button;
	@FXML private Button updateUserCategory2Button;

	private static SimpleStringProperty userCategory1Name = new SimpleStringProperty();
	private static SimpleStringProperty userCategory1Types = new SimpleStringProperty();;
	private static SimpleStringProperty userCategory2Name = new SimpleStringProperty();;
	private static SimpleStringProperty userCategory2Types = new SimpleStringProperty();;

	public BIASRTCResultsAnalysisConfigPageController()
	{
		prefs = Preferences.userRoot().node("BIAS");
		userCategory1Name.setValue("");
		userCategory1Types.setValue("");
		userCategory2Name.setValue("");
		userCategory2Types.setValue("");
	}

	@FXML private void initialize()
	{

		userCategory1Name.addListener((observable, oldValue, newValue) -> {
			notifyResultsAnalysisPageControllerOfChanges();
		});

		userCategory1Types.addListener((observable, oldValue, newValue) -> {
			notifyResultsAnalysisPageControllerOfChanges();
		});

		userCategory2Name.addListener((observable, oldValue, newValue) -> {
			notifyResultsAnalysisPageControllerOfChanges();
		});

		userCategory2Types.addListener((observable, oldValue, newValue) -> {
			notifyResultsAnalysisPageControllerOfChanges();
		});

		// DO NOT CHANGE THE ORDER OF THESE ITEMS
		cleanFileCombobox.getItems().add("Move the file to a 'Failed Dispatches' folder");	// Item index 0
		cleanFileCombobox.getItems().add("Prepend 'FAILED' to the name of the file");		// Item index 1
		cleanFileCombobox.getItems().add("Purge the file");									// Item index 2

		// See if preference is stored for failed file handling
		if (prefs.getBoolean("ra_moveFailedFiles", defaultMoveFailedFiles))
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("ra_moveFailedFiles", true);
				prefs.putBoolean("ra_prependFailedFiles", false);
				prefs.putBoolean("ra_purgeFailedFiles", false);
			}
			cleanFileCombobox.getSelectionModel().select(0);

			moveFailedFiles = true;
			prependFailedFiles = false;
			purgeFailedFiles = false;
		}

		if (prefs.getBoolean("ra_prependFailedFiles", defaultPrependFailedFiles))
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("ra_moveFailedFiles", false);
				prefs.putBoolean("ra_prependFailedFiles", true);
				prefs.putBoolean("ra_moveFailedFiles", false);
			}
			cleanFileCombobox.getSelectionModel().select(1);

			moveFailedFiles = false;
			prependFailedFiles = true;
			purgeFailedFiles = false;
		}

		if (prefs.getBoolean("ra_purgeFailedFiles", defaultPurgeFailedFiles))
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("ra_moveFailedFiles", false);
				prefs.putBoolean("ra_prependFailedFiles", false);
				prefs.putBoolean("ra_purgeFailedFiles", true);
			}
			cleanFileCombobox.getSelectionModel().select(2);

			moveFailedFiles = false;
			prependFailedFiles = false;
			purgeFailedFiles = true;
		}

		// See if preference is stored for checking for PTC equipped
		if (prefs.getBoolean("ra_checkPtcEquipped", defaultCheckPtcEquipped))
		{
			checkPtcEquipped = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_checkPtcEquipped", true);
			ptcEquippedCheckBox.setSelected(true);
		}
		else
		{
			checkPtcEquipped = false;
			ptcEquippedCheckBox.setSelected(false);
		}

		// See if preference is stored for checking for ATC equipped
		if (prefs.getBoolean("ra_checkAtcEquipped", defaultCheckAtcEquipped))
		{
			checkAtcEquipped = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_checkAtcEquipped", true);
			atcEquippedCheckBox.setSelected(true);
		}
		else
		{
			checkAtcEquipped = false;
			atcEquippedCheckBox.setSelected(false);
		}

		// See if preference is stored for generating raw data
		if (prefs.getBoolean("ra_generateRawData", defaultGenerateRawData))
		{
			generateRawData = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateRawData", true);
			generateRawDataCheckBox.setSelected(true);
		}
		else
		{
			generateRawData = false;
			generateRawDataCheckBox.setSelected(false);
		}

		// See if preference is stored for generating summary results
		if (prefs.getBoolean("ra_generateSummaryResults", defaultGenerateSummaryResults))
		{
			generateSummaryResults = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateSummaryResults", true);
			generateSummaryResultsCheckBox.setSelected(true);
		}
		else
		{
			generateSummaryResults = false;
			generateSummaryResultsCheckBox.setSelected(false);
		}

		// See if preference is stored for generating graphs
		if (prefs.getBoolean("ra_generateGraphs", defaultGenerateGraphs))
		{
			generateGraphs = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateGraphs", true);
			generateGraphsCheckBox.setSelected(true);
		}
		else
		{
			generateGraphs = false;
			generateGraphsCheckBox.setSelected(false);
		}

		// See if preference is stored for generating output as string
		if (prefs.getBoolean("ra_outputAsString", defaultOutputAsString))
		{
			outputAsString = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_outputAsString", true);
			outputAsStringCheckBox.setSelected(true);
		}
		else
		{
			outputAsString = false;
			outputAsStringCheckBox.setSelected(false);
		}

		// See if preference is stored for generating output as seconds
		if (prefs.getBoolean("ra_outputAsSeconds", defaultOutputAsSeconds))
		{
			outputAsSeconds = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_outputAsSeconds", true);
			outputAsSecondsCheckBox.setSelected(true);
		}
		else
		{
			outputAsSeconds = false;
			outputAsSecondsCheckBox.setSelected(false);
		}

		// See if preference is stored for generating output as serial
		if (prefs.getBoolean("ra_outputAsSerial", defaultOutputAsSerial))
		{
			outputAsSerial = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_outputAsSerial", true);
			outputAsSerialCheckBox.setSelected(true);
		}
		else
		{
			outputAsSerial = false;
			outputAsSerialCheckBox.setSelected(false);
		}

		// See if preferences are stored for User-defined Category 1
		if ((prefs.get("ra_userCategory1Name", "") != null) && (prefs.get("ra_userCategory1Name", "") != "") && (prefs.get("ra_userCategory1Types", "") != null) && (prefs.get("ra_userCategory1Types", "") != ""))
		{
			userCategory1Name.setValue(prefs.get("ra_userCategory1Name", ""));
			userCategory1NameTextArea.setText(userCategory1Name.getValue());
			userCategory1Types.setValue(prefs.get("ra_userCategory1Types", ""));
			userCategory1TypesTextArea.setText(userCategory1Types.getValue());
		}

		// See if preferences are stored for User-defined Category 2
		if ((prefs.get("ra_userCategory2Name", "") != null) && (prefs.get("ra_userCategory2Name", "") != "") && (prefs.get("ra_userCategory2Types", "") != null) && (prefs.get("ra_userCategory2Types", "") != ""))
		{
			userCategory2Name.setValue(prefs.get("ra_userCategory2Name", ""));
			userCategory2NameTextArea.setText(userCategory2Name.getValue());
			userCategory2Types.setValue(prefs.get("ra_userCategory2Types", ""));
			userCategory2TypesTextArea.setText(userCategory2Types.getValue());
		}

		// Set up Boolean Bindings
		disableGenerateRawDataCheckbox = (generateSummaryResultsCheckBox.selectedProperty().not().and(generateGraphsCheckBox.selectedProperty().not()));
		generateRawDataCheckBox.disableProperty().bind(disableGenerateRawDataCheckbox);

		disableGenerateSummaryResultsCheckbox = generateRawDataCheckBox.selectedProperty().not();
		generateSummaryResultsCheckBox.disableProperty().bind(disableGenerateSummaryResultsCheckbox);

		disableGenerateGraphsCheckbox = generateSummaryResultsCheckBox.selectedProperty().not();
		generateGraphsCheckBox.disableProperty().bind(disableGenerateGraphsCheckbox);

		disableOutputAsStringCheckbox = (outputAsSecondsCheckBox.selectedProperty().not().and(outputAsSerialCheckBox.selectedProperty().not()));
		outputAsStringCheckBox.disableProperty().bind(disableOutputAsStringCheckbox);

		disableOutputAsSecondsCheckbox = (outputAsStringCheckBox.selectedProperty().not().and(outputAsSerialCheckBox.selectedProperty().not()));
		outputAsSecondsCheckBox.disableProperty().bind(disableOutputAsSecondsCheckbox);

		disableOutputAsSerialCheckbox = (outputAsStringCheckBox.selectedProperty().not().and(outputAsSecondsCheckBox.selectedProperty().not()));
		outputAsSerialCheckBox.disableProperty().bind(disableOutputAsSerialCheckbox);
	}

	@FXML private void handleCleanFileCombobox(ActionEvent event) 
	{	
		Object selectedItem = cleanFileCombobox.getSelectionModel().getSelectedItem();
		if (selectedItem.toString().contains("Move"))
		{
			moveFailedFiles = true;
			prependFailedFiles = false;
			purgeFailedFiles = false;

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("ra_moveFailedFiles", true);			
				prefs.putBoolean("ra_prependFailedFiles", false);
				prefs.putBoolean("ra_purgeFailedFiles", false);
			}
		}
		else if (selectedItem.toString().contains("Prepend"))
		{
			moveFailedFiles = false;
			prependFailedFiles = true;
			purgeFailedFiles = false;

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("ra_moveFailedFiles", false);			
				prefs.putBoolean("ra_prependFailedFiles", true);
				prefs.putBoolean("ra_purgeFailedFiles", false);		
			}
		}
		else
		{
			moveFailedFiles = false;
			prependFailedFiles = false;
			purgeFailedFiles = true;

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.putBoolean("ra_moveFailedFiles", false);			
				prefs.putBoolean("ra_prependFailedFiles", false);
				prefs.putBoolean("ra_purgeFailedFiles", true);
			}
		}
	}

	@FXML private void handlePtcEquippedCheckbox(ActionEvent event)
	{
		if (checkPtcEquipped)
		{
			checkPtcEquipped = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_checkPtcEquipped", false);
		}
		else
		{
			checkPtcEquipped = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_checkPtcEquipped", true);
		}
	}

	@FXML private void handleAtcEquippedCheckbox(ActionEvent event)
	{
		if (checkAtcEquipped)
		{
			checkAtcEquipped = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_checkAtcEquipped", false);
		}
		else
		{
			checkAtcEquipped = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_checkAtcEquipped", true);
		}
	}

	@FXML private void handleGenerateRawDataCheckbox(ActionEvent event)
	{
		if (generateRawData)
		{
			generateRawData = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateRawData", false);
		}
		else
		{
			generateRawData = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateRawData", true);
		}
	}

	@FXML private void handleGenerateSummaryResultsCheckbox(ActionEvent event)
	{
		if ((generateSummaryResults) && (generateRawData))
		{
			generateSummaryResults = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateSummaryResults", false);

			generateGraphs = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateGraphs", false);
			generateGraphsCheckBox.setSelected(false);
		}
		else
		{
			generateSummaryResults = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateSummaryResults", true);
		}
	}

	@FXML private void handleGenerateGraphsCheckbox(ActionEvent event)
	{
		if (generateGraphs)
		{
			generateGraphs = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateGraphs", false);
		}
		else
		{
			generateGraphs = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_generateGraphs", true);
		}
	}

	@FXML private void handleOutputAsStringCheckbox(ActionEvent event)
	{
		if (outputAsString)
		{
			outputAsString = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_outputAsString", false);
		}
		else
		{
			outputAsString = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_outputAsString", true);
		}
	}

	@FXML private void handleOutputAsSecondsCheckbox(ActionEvent event)
	{
		if (outputAsSeconds)
		{
			outputAsSeconds = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_outputAsSeconds", false);
		}
		else
		{
			outputAsSeconds = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_outputAsSeconds", true);
		}
	}

	@FXML private void handleOutputAsSerialCheckbox(ActionEvent event)
	{
		if (outputAsSerial)
		{
			outputAsSerial = false;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_outputAsSerial", false);
		}
		else
		{
			outputAsSerial = true;
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.putBoolean("ra_outputAsSerial", true);
		}
	}

	@FXML private void handleUpdateUserCategory1Button(ActionEvent event)
	{
		String userCategory1NameInput = userCategory1NameTextArea.getText().trim();
		String userCategory1TypesInput = userCategory1TypesTextArea.getText().trim().replaceAll(",{2,}", ",");

		// Validate category name
		if ((userCategory1NameInput.equals("")) && (userCategory1TypesInput.equals("")))
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("ra_userCategory1Name", "");
				prefs.put("ra_userCategory1Types", "");
			}

			userCategory1NameTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			userCategory1TypesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			updateUserCategory1Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (userCategory1NameInput.equals(userCategory2Name))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Category names are the same.  Rename at least one.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();
		}
		else if ((userCategory1NameInput.matches("^[a-zA-Z\\s0-9_-]*$"))
				&& (userCategory1TypesInput.matches("^[^,][a-zA-Z\\s,0-9_-]*(,\\s?[a-zA-Z\\s,0-9_-])*[^,]$")) 
				&& (!userCategory1NameInput.equals(""))
				&& (!userCategory1TypesInput.equals(""))
				&& (!userCategory1TypesInput.substring(0).equals(",")))
		{
			userCategory1Name.setValue(userCategory1NameInput);
			userCategory1NameTextArea.setText(userCategory1Name.getValue());
			userCategory1Types.setValue(userCategory1TypesInput.replace(", ", ","));
			userCategory1TypesTextArea.setText(userCategory1Types.getValue());

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("ra_userCategory1Name", userCategory1Name.getValue());
				prefs.put("ra_userCategory1Types", userCategory1Types.getValue());
			}

			userCategory1NameTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			userCategory1TypesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			updateUserCategory1Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Only A-Z, 0-9, hyphen, underscore and blank spaces are permitted.  Separate multiple Train Types by ','.  Please try again.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();

			userCategory1NameTextArea.setText(userCategory1NameInput);
			userCategory1TypesTextArea.setText(userCategory1TypesInput);
		}
	}

	@FXML private void handleUpdateUserCategory2Button(ActionEvent event)
	{
		String userCategory2NameInput = userCategory2NameTextArea.getText().trim();
		String userCategory2TypesInput = userCategory2TypesTextArea.getText().trim().replaceAll(",{2,}", ",");

		// Validate category name
		if ((userCategory2NameInput.equals("")) && (userCategory2TypesInput.equals("")))
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("ra_userCategory2Name", "");
				prefs.put("ra_userCategory2Types", "");
			}

			userCategory2NameTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			userCategory2TypesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			updateUserCategory2Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else if (userCategory2NameInput.equals(userCategory1Name))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Category names are the same.  Rename at least one.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();
		}
		else if ((userCategory2NameInput.matches("^[a-zA-Z\\s0-9_-]*$"))
				&& (userCategory2TypesInput.matches("^[^,][a-zA-Z\\s,0-9_-]*(,\\s?[a-zA-Z\\s,0-9_-])*[^,]$")) 
				&& (!userCategory2NameInput.equals(""))
				&& (!userCategory2TypesInput.equals(""))
				&& (!userCategory2TypesInput.substring(0).equals(",")))
		{
			userCategory2Name.setValue(userCategory2NameInput);
			userCategory2NameTextArea.setText(userCategory2Name.getValue());
			userCategory2Types.setValue(userCategory2TypesInput.replace(", ", ","));
			userCategory2TypesTextArea.setText(userCategory2Types.getValue());

			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("ra_userCategory2Name", userCategory2NameInput);
				prefs.put("ra_userCategory2Types", userCategory2Types.getValue());
			}

			userCategory2NameTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			userCategory2TypesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			updateUserCategory2Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Only A-Z, 0-9, hyphen, underscore and blank spaces are permitted.  Separate multiple Train Types by ','.  Please try again.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(this.getClass().getResource(BIASLaunch.getFrameIconFile()).toString()));
			alert.show();

			userCategory2NameTextArea.setText(userCategory2NameInput);
			userCategory2TypesTextArea.setText(userCategory2TypesInput);
		}
	}

	@FXML private void handleUserCategory1NameTextArea()
	{
		userCategory1NameTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateUserCategory1Button.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleUserCategory1TypesTextArea()
	{
		userCategory1TypesTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateUserCategory1Button.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleUserCategory2NameTextArea()
	{
		userCategory2NameTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateUserCategory2Button.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleUserCategory2TypesTextArea()
	{
		userCategory2TypesTextArea.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		updateUserCategory2Button.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	public static Boolean getMoveFailedFiles()
	{
		return moveFailedFiles;
	}

	public static Boolean getPrependFailedFiles()
	{
		return prependFailedFiles;
	}

	public static Boolean getPurgeFailedFiles()
	{
		return purgeFailedFiles;
	}

	public static Boolean getCheckForPtcEquipped()
	{
		return checkPtcEquipped;
	}

	public static Boolean getCheckForAtcEquipped()
	{
		return checkAtcEquipped;
	}

	public static Boolean getGenerateRawData()
	{
		return generateRawData;
	}

	public static Boolean getSummaryResults()
	{
		return generateSummaryResults;
	}

	public static Boolean getGenerateGraphs()
	{
		return generateGraphs;
	}

	public static Boolean getOutputAsString()
	{
		return outputAsString;
	}

	public static Boolean getOutputAsSeconds()
	{
		return outputAsSeconds;
	}

	public static Boolean getOutputAsSerial()
	{
		return outputAsSerial;
	}

	public static SimpleStringProperty getUserCategory1Name()
	{
		return userCategory1Name;
	}

	public static SimpleStringProperty getUserCategory1Types()
	{
		return userCategory1Types;
	}

	public static SimpleStringProperty getUserCategory2Name()
	{
		return userCategory2Name;
	}

	public static SimpleStringProperty getUserCategory2Types()
	{
		return userCategory2Types;
	}

	void notifyResultsAnalysisPageControllerOfChanges()
	{
		BIASRTCResultsAnalysisPageController.changeMadeToCustomTypesInConfig();
	}
}