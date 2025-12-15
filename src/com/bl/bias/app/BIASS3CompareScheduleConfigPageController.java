package com.bl.bias.app;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

public class BIASS3CompareScheduleConfigPageController
{	
	private static Preferences prefs;

	private static String uri1;
	private static String clientId1;
	private static String clientSecret1;
	private static String userName1;
	private static String password1;
	private static String grantType1;
	private static String code1;
	private static ObservableValue<String> profileName1AsObservable;
	private static String profileName1AsString;

	private static String uri2;
	private static String clientId2;
	private static String clientSecret2;
	private static String userName2;
	private static String password2;
	private static String grantType2;
	private static String code2;
	private static ObservableValue<String> profileName2AsObservable;
	private static String profileName2AsString;

	private static String defaultUri = ""; 
	private static String defaultClientId = ""; 
	private static String defaultClientSecret = ""; 
	private static String defaultUserName = ""; 
	private static String defaultPassword = "";
	private static String defaultGrantType = "";
	private static String defaultCode = "";
	private static String defaultProfileName = ""; 

	private static LocalDate mondayCoreDate;
	private static LocalDate tuesdayCoreDate;
	private static LocalDate wednesdayCoreDate;
	private static LocalDate thursdayCoreDate;
	private static LocalDate fridayCoreDate;
	private static LocalDate saturdayCoreDate;
	private static LocalDate sundayCoreDate;

	private ObservableList<String> validCoreDayList = FXCollections.observableList(new ArrayList<String>());

	private BIASS3CompareSchedulePageController biass3CompareSchedulePageController = new BIASS3CompareSchedulePageController();

	@FXML private Button testUri1Button;
	@FXML private Button updateAPI1ParametersButton;
	@FXML private Button useLastSavedAPI1ParametersButton;
	@FXML private Button testUri2Button;
	@FXML private Button updateAPI2ParametersButton;
	@FXML private Button useLastSavedAPI2ParametersButton;
	@FXML private Button clearAllDatesButton;

	@FXML private TextField uriTextField1;
	@FXML private TextField clientIdField1;
	@FXML private TextField clientSecretField1;
	@FXML private TextField userNameField1;
	@FXML private TextField passwordField1;
	@FXML private TextField grantTypeField1;
	@FXML private TextField codeField1;
	@FXML private TextField profileNameTextField1;

	@FXML private TextField uriTextField2;
	@FXML private TextField clientIdField2;
	@FXML private TextField clientSecretField2;
	@FXML private TextField userNameField2;
	@FXML private TextField passwordField2;
	@FXML private TextField grantTypeField2;
	@FXML private TextField codeField2;
	@FXML private TextField profileNameTextField2;

	@FXML private DatePicker mondayCoreDateDatePicker;
	@FXML private DatePicker tuesdayCoreDateDatePicker;
	@FXML private DatePicker wednesdayCoreDateDatePicker;
	@FXML private DatePicker thursdayCoreDateDatePicker;
	@FXML private DatePicker fridayCoreDateDatePicker;
	@FXML private DatePicker saturdayCoreDateDatePicker;
	@FXML private DatePicker sundayCoreDateDatePicker;

	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");

		validCoreDayList.addListener(new ListChangeListener<String>() {
			@Override
			//onChanged method
			public void onChanged(ListChangeListener.Change c) {
				biass3CompareSchedulePageController.alertOfConfigChange(validCoreDayList);
			}
		});

		// See if API 1 parameters are stored
		boolean uri1Exists = prefs.get("s3_uri1", null) != null;
		if (uri1Exists)
		{
			uriTextField1.setText(prefs.get("s3_uri1", defaultUri));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_uri1", defaultUri);
			uriTextField1.setText(prefs.get("s3_uri1", defaultUri));
		}
		uri1 = prefs.get("s3_uri1", defaultUri);

		boolean clientId1Exists = prefs.get("s3_clientId1", null) != null;
		if (clientId1Exists)
		{
			clientIdField1.setText(prefs.get("s3_clientId1", defaultClientId));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_clientId1", defaultClientId);
			clientIdField1.setText(prefs.get("s3_clientId1", defaultClientId));
		}
		clientId1 = prefs.get("s3_clientId1", defaultClientId);

		boolean clientSecret1Exists = prefs.get("s3_clientSecret1", null) != null;
		if (clientSecret1Exists)
		{
			clientSecretField1.setText(prefs.get("s3_clientSecret1", defaultClientSecret));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_clientSecret1", defaultClientSecret);
			clientSecretField1.setText(prefs.get("s3_clientSecret1", defaultClientSecret));
		}
		clientSecret1 = prefs.get("s3_clientSecret1", defaultClientSecret);

		boolean userName1Exists = prefs.get("s3_userName1", null) != null;
		if (userName1Exists)
		{
			userNameField1.setText(prefs.get("s3_userName1", defaultUserName));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_userName1", defaultUserName);
			userNameField1.setText(prefs.get("s3_userName1", defaultUserName));
		}
		userName1 = prefs.get("s3_userName1", defaultUserName);

		boolean password1Exists = prefs.get("s3_password1", null) != null;
		if (password1Exists)
		{
			passwordField1.setText(prefs.get("s3_password1", defaultPassword));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_password1", defaultPassword);
			passwordField1.setText(prefs.get("s3_password1", defaultPassword));
		}
		password1 = prefs.get("s3_password1", defaultPassword);

		boolean grantType1Exists = prefs.get("s3_grantType1", null) != null;
		if (grantType1Exists)
		{
			grantTypeField1.setText(prefs.get("s3_grantType1", defaultGrantType));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_grantType1", defaultGrantType);
			grantTypeField1.setText(prefs.get("s3_grantType1", defaultGrantType));
		}
		grantType1 = prefs.get("s3_grantType1", defaultGrantType);


		boolean code1Exists = prefs.get("s3_code1", null) != null;
		if (code1Exists)
		{
			codeField1.setText(prefs.get("s3_code1", defaultCode));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_code1", defaultCode);
			codeField1.setText(prefs.get("s3_code1", defaultCode));
		}
		code1 = prefs.get("s3_code1", defaultCode);

		boolean profileName1Exists = prefs.get("s3_profileName1", null) != null;
		if (profileName1Exists)
		{
			profileNameTextField1.setText(prefs.get("s3_profileName1", defaultProfileName));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_profileName1", defaultProfileName);
			profileNameTextField1.setText(prefs.get("s3_profileName1", defaultProfileName));
		}
		profileName1AsObservable = new SimpleStringProperty(prefs.get("s3_profileName1", defaultProfileName));
		profileName1AsString = profileNameTextField1.getText();

		// See if API 2 parameters are stored
		boolean uri2Exists = prefs.get("s3_uri2", null) != null;
		if (uri2Exists)
		{
			uriTextField2.setText(prefs.get("s3_uri2", defaultUri));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_uri2", defaultUri);
			uriTextField2.setText(prefs.get("s3_uri2", defaultUri));
		}
		uri2 = prefs.get("s3_uri2", defaultUri);

		boolean clientId2Exists = prefs.get("s3_clientId2", null) != null;
		if (clientId2Exists)
		{
			clientIdField2.setText(prefs.get("s3_clientId2", defaultClientId));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_clientId2", defaultClientId);
			clientIdField2.setText(prefs.get("s3_clientId2", defaultClientId));
		}
		clientId2 = prefs.get("s3_clientId2", defaultClientId);

		boolean clientSecret2Exists = prefs.get("s3_clientSecret2", null) != null;
		if (clientSecret2Exists)
		{
			clientSecretField2.setText(prefs.get("s3_clientSecret2", defaultClientSecret));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_clientSecret2", defaultClientSecret);
			clientSecretField2.setText(prefs.get("s3_clientSecret2", defaultClientSecret));
		}
		clientSecret2 = prefs.get("s3_clientSecret2", defaultClientSecret);

		boolean userName2Exists = prefs.get("s3_userName2", null) != null;
		if (userName2Exists)
		{
			userNameField2.setText(prefs.get("s3_userName2", defaultUserName));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_userName2", defaultUserName);
			userNameField2.setText(prefs.get("s3_userName2", defaultUserName));
		}
		userName2 = prefs.get("s3_userName2", defaultUserName);

		boolean password2Exists = prefs.get("s3_password2", null) != null;
		if (password2Exists)
		{
			passwordField2.setText(prefs.get("s3_password2", defaultPassword));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_password2", defaultPassword);
			passwordField2.setText(prefs.get("s3_password2", defaultPassword));
		}
		password2 = prefs.get("s3_password2", defaultPassword);

		boolean grantType2Exists = prefs.get("s3_grantType2", null) != null;
		if (grantType2Exists)
		{
			grantTypeField2.setText(prefs.get("s3_grantType2", defaultGrantType));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_grantType2", defaultGrantType);
			grantTypeField2.setText(prefs.get("s3_grantType2", defaultGrantType));
		}
		grantType2 = prefs.get("s3_grantType2", defaultGrantType);


		boolean code2Exists = prefs.get("s3_code2", null) != null;
		if (code2Exists)
		{
			codeField2.setText(prefs.get("s3_code2", defaultCode));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_code2", defaultCode);
			codeField2.setText(prefs.get("s3_code2", defaultCode));
		}
		code2 = prefs.get("s3_code2", defaultCode);

		boolean profileName2Exists = prefs.get("s3_profileName2", null) != null;
		if (profileName2Exists)
		{
			profileNameTextField2.setText(prefs.get("s3_profileName2", defaultProfileName));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_profileName2", defaultProfileName);
			profileNameTextField2.setText(prefs.get("s3_profileName2", defaultProfileName));
		}
		profileName2AsObservable = new SimpleStringProperty(prefs.get("s3_profileName2", defaultProfileName));
		profileName2AsString = profileNameTextField2.getText();

		// See if core dates are stored
		// Monday
		boolean mondayDateExists = prefs.get("s3_mondayCoreDate", null) != null;
		if ((mondayDateExists) && (!prefs.get("s3_mondayCoreDate", "").equals("")))
		{
			mondayCoreDateDatePicker.setValue(LocalDate.parse(prefs.get("s3_mondayCoreDate", "")));
			validCoreDayList.add("M");
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_mondayCoreDate", "");
		}

		mondayCoreDateDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				mondayCoreDate = mondayCoreDateDatePicker.getValue();
				if (mondayCoreDate == null)
					validCoreDayList.remove("M");
				else if (!validCoreDayList.contains("M"))
					validCoreDayList.add("M");

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				{					
					if (mondayCoreDate == null)
						prefs.put("s3_mondayCoreDate", "");
					else
						prefs.put("s3_mondayCoreDate", mondayCoreDate.toString());
				}
			}
		});

		// Tuesday
		boolean tuesdayDateExists = prefs.get("s3_tuesdayCoreDate", null) != null;
		if ((tuesdayDateExists) && (!prefs.get("s3_tuesdayCoreDate", "").equals("")))
		{
			tuesdayCoreDateDatePicker.setValue(LocalDate.parse(prefs.get("s3_tuesdayCoreDate", "")));
			validCoreDayList.add("T");
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_tuesdayCoreDate", "");
		}

		tuesdayCoreDateDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				tuesdayCoreDate = tuesdayCoreDateDatePicker.getValue();
				if (tuesdayCoreDate == null)
					validCoreDayList.remove("T");
				else if (!validCoreDayList.contains("T"))
					validCoreDayList.add("T");

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
					if (tuesdayCoreDate == null)
						prefs.put("s3_tuesdayCoreDate", "");
					else
						prefs.put("s3_tuesdayCoreDate", tuesdayCoreDate.toString());
			}
		});

		// Wednesday
		boolean wednesdayDateExists = prefs.get("s3_wednesdayCoreDate", null) != null;
		if ((wednesdayDateExists) && (!prefs.get("s3_wednesdayCoreDate", "").equals("")))
		{
			wednesdayCoreDateDatePicker.setValue(LocalDate.parse(prefs.get("s3_wednesdayCoreDate", "")));
			validCoreDayList.add("W");
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_wednesdayCoreDate", "");
		}

		wednesdayCoreDateDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				wednesdayCoreDate = wednesdayCoreDateDatePicker.getValue();
				if (wednesdayCoreDate == null)
					validCoreDayList.remove("W");
				else if (!validCoreDayList.contains("W"))
					validCoreDayList.add("W");

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				{
					if (wednesdayCoreDate == null)
						prefs.put("s3_wednesdayCoreDate", "");
					else
						prefs.put("s3_wednesdayCoreDate", wednesdayCoreDate.toString());
				}
			}
		});

		// Thursday
		boolean thursdayDateExists = prefs.get("s3_thursdayCoreDate", null) != null;
		if ((thursdayDateExists) && (!prefs.get("s3_thursdayCoreDate", "").equals("")))
		{
			thursdayCoreDateDatePicker.setValue(LocalDate.parse(prefs.get("s3_thursdayCoreDate", "")));
			validCoreDayList.add("R");
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_thursdayCoreDate", "");
		}

		thursdayCoreDateDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				thursdayCoreDate = thursdayCoreDateDatePicker.getValue();
				if (thursdayCoreDate == null)
					validCoreDayList.remove("R");
				else if (!validCoreDayList.contains("R"))
					validCoreDayList.add("R");

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				{
					if (thursdayCoreDate == null)
						prefs.put("s3_thursdayCoreDate", "");
					else
						prefs.put("s3_thursdayCoreDate", thursdayCoreDate.toString());
				}
			}
		});

		// Friday
		boolean fridayDateExists = prefs.get("s3_fridayCoreDate", null) != null;
		if ((fridayDateExists) && (!prefs.get("s3_fridayCoreDate", "").equals("")))
		{
			fridayCoreDateDatePicker.setValue(LocalDate.parse(prefs.get("s3_fridayCoreDate", "")));
			validCoreDayList.add("F");
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_fridayCoreDate", "");
		}

		fridayCoreDateDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				fridayCoreDate = fridayCoreDateDatePicker.getValue();
				if (fridayCoreDate == null)
					validCoreDayList.remove("F");
				else if (!validCoreDayList.contains("F"))
					validCoreDayList.add("F");

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				{
					if (fridayCoreDate == null)
						prefs.put("s3_fridayCoreDate", "");
					else
						prefs.put("s3_fridayCoreDate", fridayCoreDate.toString());
				}
			}
		});

		// Saturday
		boolean saturdayDateExists = prefs.get("s3_saturdayCoreDate", null) != null;
		if ((saturdayDateExists) && (!prefs.get("s3_saturdayCoreDate", "").equals("")))
		{
			saturdayCoreDateDatePicker.setValue(LocalDate.parse(prefs.get("s3_saturdayCoreDate", "")));
			validCoreDayList.add("Sa");
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_saturdayCoreDate", "");
		}

		saturdayCoreDateDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				saturdayCoreDate = saturdayCoreDateDatePicker.getValue();
				if (saturdayCoreDate == null)
					validCoreDayList.remove("Sa");
				else if (!validCoreDayList.contains("Sa"))
					validCoreDayList.add("Sa");

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				{
					if (saturdayCoreDate == null)
						prefs.put("s3_saturdayCoreDate", "");
					else
						prefs.put("s3_saturdayCoreDate", saturdayCoreDate.toString());
				}
			}
		});

		// Sunday
		boolean sundayDateExists = prefs.get("s3_sundayCoreDate", null) != null;
		if ((sundayDateExists) && (!prefs.get("s3_sundayCoreDate", "").equals("")))
		{
			sundayCoreDateDatePicker.setValue(LocalDate.parse(prefs.get("s3_sundayCoreDate", "")));
			validCoreDayList.add("Su");
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_sundayCoreDate", "");
		}

		sundayCoreDateDatePicker.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				sundayCoreDate = sundayCoreDateDatePicker.getValue();
				if (sundayCoreDate == null)
					validCoreDayList.remove("Su");
				else if (!validCoreDayList.contains("Su"))
					validCoreDayList.add("Su");

				if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				{
					if (sundayCoreDate == null)
						prefs.put("s3_sundayCoreDate", "");
					else
						prefs.put("s3_sundayCoreDate", sundayCoreDate.toString());
				}
			}
		});
	};

	@FXML private void handleTextChangedURITextField1()
	{
		uriTextField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedURITextField2()
	{
		uriTextField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedClientIdField1()
	{
		clientIdField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedClientIdField2()
	{
		clientIdField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedClientSecretField1()
	{
		clientSecretField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedClientSecretField2()
	{
		clientSecretField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedUserNameField1()
	{
		userNameField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedUserNameField2()
	{
		userNameField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedPasswordField1()
	{
		passwordField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedPasswordField2()
	{
		passwordField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedGrantTypeField1()
	{
		grantTypeField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedGrantTypeField2()
	{
		grantTypeField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedCodeField1()
	{
		codeField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedCodeField2()
	{
		codeField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedProfileNameTextField1()
	{
		profileNameTextField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedProfileNameTextField2()
	{
		profileNameTextField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleUpdateAPI1ParametersButton()
	{
		uriTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		clientIdField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		clientSecretField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		userNameField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		passwordField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		grantTypeField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		codeField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		profileNameTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		uri1 = uriTextField1.getText();
		clientId1 = clientIdField1.getText();
		clientSecret1 = clientSecretField1.getText();
		userName1 = userNameField1.getText();
		password1 = passwordField1.getText();
		grantType1 = grantTypeField1.getText();
		code1 = codeField1.getText();
		profileName1AsString = profileNameTextField1.getText();
		((SimpleStringProperty) profileName1AsObservable).set(profileNameTextField1.getText());

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.put("s3_uri1", uri1);
			prefs.put("s3_clientId1", clientId1);
			prefs.put("s3_clientSecret1", clientSecret1);
			prefs.put("s3_userName1", userName1);
			prefs.put("s3_password1", password1);
			prefs.put("s3_grantType1", grantType1);
			prefs.put("s3_code1", code1);
			prefs.put("s3_profileName1", profileName1AsString);
		}
	}

	@FXML private void handleUpdateAPI2ParametersButton()
	{
		uriTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		clientIdField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		clientSecretField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		userNameField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		passwordField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		grantTypeField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		codeField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		profileNameTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		uri2 = uriTextField2.getText();
		clientId2 = clientIdField2.getText();
		clientSecret2 = clientSecretField2.getText();
		userName2 = userNameField2.getText();
		password2 = passwordField2.getText();
		grantType2 = grantTypeField2.getText();
		code2 = codeField2.getText();
		profileName2AsString = profileNameTextField2.getText();
		((SimpleStringProperty) profileName2AsObservable).set(profileNameTextField2.getText());

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.put("s3_uri2", uri2);
			prefs.put("s3_clientId2", clientId2);
			prefs.put("s3_clientSecret2", clientSecret2);
			prefs.put("s3_userName2", userName2);
			prefs.put("s3_password2", password2);
			prefs.put("s3_grantType2", grantType2);
			prefs.put("s3_code2", code2);
			prefs.put("s3_profileName2", profileName2AsString);
		}
	}

	@FXML private void handleUseLastSavedAPI1ParametersButton()
	{
		uriTextField1.setText(uri1);
		uriTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		clientIdField1.setText(clientId1);
		clientIdField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		clientSecretField1.setText(clientSecret1);
		clientSecretField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		userNameField1.setText(userName1);
		userNameField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		passwordField1.setText(password1);
		passwordField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		grantTypeField1.setText(grantType1);
		grantTypeField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		codeField1.setText(code1);
		codeField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		profileNameTextField1.setText(profileName1AsString);
		profileNameTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
	}

	@FXML private void handleUseLastSavedAPI2ParametersButton()
	{
		uriTextField2.setText(uri2);
		uriTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		clientIdField2.setText(clientId2);
		clientIdField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		clientSecretField2.setText(clientSecret2);
		clientSecretField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		userNameField2.setText(userName2);
		userNameField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		passwordField2.setText(password2);
		passwordField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		grantTypeField2.setText(grantType2);
		grantTypeField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		codeField2.setText(code2);
		codeField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		profileNameTextField2.setText(profileName2AsString);
		profileNameTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
	}

	@FXML private void handleClearAllDatesButton()
	{
		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.put("s3_mondayCoreDate","");
			prefs.put("s3_tuesdayCoreDate","");
			prefs.put("s3_wednesdayCoreDate","");
			prefs.put("s3_thursdayCoreDate","");
			prefs.put("s3_fridayCoreDate","");
			prefs.put("s3_saturdayCoreDate","");
			prefs.put("s3_sundayCoreDate","");
		}

		mondayCoreDateDatePicker.getEditor().clear();
		tuesdayCoreDateDatePicker.getEditor().clear();
		wednesdayCoreDateDatePicker.getEditor().clear();
		thursdayCoreDateDatePicker.getEditor().clear();
		fridayCoreDateDatePicker.getEditor().clear();
		saturdayCoreDateDatePicker.getEditor().clear();
		sundayCoreDateDatePicker.getEditor().clear();

		mondayCoreDate = null;
		tuesdayCoreDate = null;
		wednesdayCoreDate = null;
		thursdayCoreDate = null;
		fridayCoreDate = null;
		saturdayCoreDate = null;
		sundayCoreDate = null;

		validCoreDayList.clear();
	}

	@FXML private void handleTestUri1Button()
	{
		if (uri1.trim() != "")
		{
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(uri1))
					.build();

			try 
			{
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				if ((response.statusCode() == 200) && (response.body().contains("S3 Passenger"))) 
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success!");
					alert.setHeaderText(null);
					alert.setContentText("Connection URI is valid.");	
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));			
					alert.show();
				} 
				else 
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Could not connect to specified URI.");	
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
					alert.show();
				}
			} 
			catch (IOException e) 
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Could not connect to specified URI.");	
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
				alert.show();
			} 
			catch (InterruptedException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Could not connect to specified URI.");	
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
				alert.show();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("No URI specified.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
			alert.show();
		}
		/*  SAVE FOR READ MODULE
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(uri1))
				.POST(HttpRequest.BodyPublishers.ofString("{\"client_id\":\""+clientId1+"\","
						+ "\"client_secret\":\""+clientSecret1+"\","
						+ "\"username\":\""+userName1+"\","
						+ "\"password\":\""+password1+"\","
						+ "\"grant_type\":\""+grantType1+"\","
						+ "\"code\":\""+code1+"\"}"))
				.build();
		 */
	}

	@FXML private void handleTestUri2Button()
	{
		if (uri2.trim() != "")
		{
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(uri2))
					.build();

			try 
			{
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				if ((response.statusCode() == 200) && (response.body().contains("S3 Passenger"))) 
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success!");
					alert.setHeaderText(null);
					alert.setContentText("Connection URI is valid.");	
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));			
					alert.show();
				} 
				else 
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Could not connect to specified URI.");	
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
					alert.show();
				}
			} 
			catch (IOException e) 
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Could not connect to specified URI.");	
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
				alert.show();
			} 
			catch (InterruptedException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Could not connect to specified URI.");	
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
				alert.show();
			}
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("No URI specified.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
			alert.show();
		}
	}

	public static String getUri1()
	{
		return uri1;
	}

	public static String getUri2()
	{
		return uri2;
	}

	public static String getClientId1()
	{
		return clientId1;
	}

	public static String getClientId2()
	{
		return clientId2;
	}

	public static String getClientSecret1()
	{
		return clientSecret1;
	}

	public static String getClientSecret2()
	{
		return clientSecret2;
	}

	public static String getUserName1()
	{
		return userName1;
	}

	public static String getUserName2()
	{
		return userName2;
	}

	public static String getPassword1()
	{
		return password1;
	}

	public static String getPassword2()
	{
		return password2;
	}

	public static String getGrantType1()
	{
		return grantType1;
	}

	public static String getGrantType2()
	{
		return grantType2;
	}

	public static String getCode1()
	{
		return code1;
	}

	public static String getCode2()
	{
		return code2;
	}

	public static ObservableValue<String> getProfileName1AsObservable()
	{
		return profileName1AsObservable;
	}

	public static ObservableValue<String> getProfileName2AsObservable()
	{
		return profileName2AsObservable;
	}

	public static LocalDate getMondayCoreDate()
	{
		return mondayCoreDate;
	}

	public static LocalDate getTuesdayCoreDate()
	{
		return tuesdayCoreDate;
	}

	public static LocalDate getWednesdayCoreDate()
	{
		return wednesdayCoreDate;
	}

	public static LocalDate getThursdayCoreDate()
	{
		return thursdayCoreDate;
	}

	public static LocalDate getFridayCoreDate()
	{
		return fridayCoreDate;
	}

	public static LocalDate getSaturdayCoreDate()
	{
		return saturdayCoreDate;
	}

	public static LocalDate getSundayCoreDate()
	{
		return sundayCoreDate;
	}

	public ObservableList<String> getValidCoreDatesMap()
	{
		return validCoreDayList;
	}
}