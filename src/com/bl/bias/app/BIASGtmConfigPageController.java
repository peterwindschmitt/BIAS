package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BIASGtmConfigPageController 
{
	private static Preferences prefs;

	@FXML private TextArea userCategory1NameTextArea;
	@FXML private TextArea userCategory1TypesTextArea;
	@FXML private TextArea userCategory2NameTextArea;
	@FXML private TextArea userCategory2TypesTextArea;

	@FXML private Button updateUserCategory1Button;
	@FXML private Button updateUserCategory2Button;

	private static SimpleStringProperty userCategory1Name = new SimpleStringProperty();
	private static SimpleStringProperty userCategory1Types = new SimpleStringProperty();
	private static SimpleStringProperty userCategory2Name = new SimpleStringProperty();
	private static SimpleStringProperty userCategory2Types = new SimpleStringProperty();
	private static SimpleBooleanProperty validCustomAssignment1Exists = new SimpleBooleanProperty();
	private static SimpleBooleanProperty validCustomAssignment2Exists = new SimpleBooleanProperty();

	public BIASGtmConfigPageController()
	{
		prefs = Preferences.userRoot().node("BIAS");
		userCategory1Name.setValue("");
		userCategory1Types.setValue("");
		userCategory2Name.setValue("");
		userCategory2Types.setValue("");
		
		validCustomAssignment1Exists.setValue(false);
		validCustomAssignment2Exists.setValue(false);
	}

	@FXML private void initialize()
	{
		userCategory1Name.addListener((observable, oldValue, newValue) -> {
			notifyGtmAnalysisPageControllerOfChanges();
		});

		userCategory1Types.addListener((observable, oldValue, newValue) -> {
			notifyGtmAnalysisPageControllerOfChanges();
		});

		userCategory2Name.addListener((observable, oldValue, newValue) -> {
			notifyGtmAnalysisPageControllerOfChanges();
		});

		userCategory2Types.addListener((observable, oldValue, newValue) -> {
			notifyGtmAnalysisPageControllerOfChanges();
		});

		// See if preferences are stored for User-defined Category 1
		if ((prefs.get("tm_userCategory1Name", "") != null) && (prefs.get("tm_userCategory1Name", "") != "") && (prefs.get("tm_userCategory1Types", "") != null) && (prefs.get("tm_userCategory1Types", "") != ""))
		{
			userCategory1Name.setValue(prefs.get("tm_userCategory1Name", ""));
			userCategory1NameTextArea.setText(userCategory1Name.getValue());
			userCategory1Types.setValue(prefs.get("tm_userCategory1Types", ""));
			userCategory1TypesTextArea.setText(userCategory1Types.getValue());
			
			validCustomAssignment1Exists.setValue(true);
		}

		// See if preferences are stored for User-defined Category 2
		if ((prefs.get("tm_userCategory2Name", "") != null) && (prefs.get("tm_userCategory2Name", "") != "") && (prefs.get("tm_userCategory2Types", "") != null) && (prefs.get("tm_userCategory2Types", "") != ""))
		{
			userCategory2Name.setValue(prefs.get("tm_userCategory2Name", ""));
			userCategory2NameTextArea.setText(userCategory2Name.getValue());
			userCategory2Types.setValue(prefs.get("tm_userCategory2Types", ""));
			userCategory2TypesTextArea.setText(userCategory2Types.getValue());
			
			validCustomAssignment2Exists.setValue(true);
		}
	}

	@FXML private void handleUpdateUserCategory1Button(ActionEvent event)
	{
		String userCategory1NameInput = userCategory1NameTextArea.getText().trim();
		String userCategory1TypesInput = userCategory1TypesTextArea.getText().trim().replaceAll(",{2,}", ",");

		// Validate category name
		if ((userCategory1NameInput.isBlank()) && (userCategory1TypesInput.isBlank()))
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("tm_userCategory1Name", "");
				prefs.put("tm_userCategory1Types", "");
			}

			userCategory1Types.setValue("");
			userCategory1Name.setValue("");
			
			userCategory1NameTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			userCategory1TypesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			updateUserCategory1Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			
			validCustomAssignment1Exists.setValue(false);
		}
		else if (userCategory1NameInput.equals(userCategory2Name.getValue()))
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
				prefs.put("tm_userCategory1Name", userCategory1Name.getValue());
				prefs.put("tm_userCategory1Types", userCategory1Types.getValue());
			}

			userCategory1NameTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			userCategory1TypesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			updateUserCategory1Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			
			validCustomAssignment1Exists.setValue(true);
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
		if ((userCategory2NameInput.isBlank()) && (userCategory2TypesInput.isBlank()))
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
			{
				prefs.put("tm_userCategory2Name", "");
				prefs.put("tm_userCategory2Types", "");
			}
			
			userCategory2Types.setValue("");
			userCategory2Name.setValue("");
			
			userCategory2NameTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			userCategory2TypesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			updateUserCategory2Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			
			validCustomAssignment2Exists.setValue(false);
		}
		else if (userCategory2NameInput.equals(userCategory1Name.getValue()))
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
				prefs.put("tm_userCategory2Name", userCategory2NameInput);
				prefs.put("tm_userCategory2Types", userCategory2Types.getValue());
			}

			userCategory2NameTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			userCategory2TypesTextArea.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			updateUserCategory2Button.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
			
			validCustomAssignment2Exists.setValue(true);
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
	
	public static SimpleBooleanProperty getValidCustomAssignment1Exists()
	{
		return validCustomAssignment1Exists;
	}
	
	public static SimpleBooleanProperty getValidCustomAssignment2Exists()
	{
		return validCustomAssignment2Exists;
	}

	void notifyGtmAnalysisPageControllerOfChanges()
	{
		//BIASGtmPageController.changeMadeToCustomTypesInConfig();
	}
}