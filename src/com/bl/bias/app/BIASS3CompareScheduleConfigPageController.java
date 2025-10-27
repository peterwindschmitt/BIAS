package com.bl.bias.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class BIASS3CompareScheduleConfigPageController
{	
	private static Preferences prefs;

	private static String uri1;
	private static String host1;
	private static String key1;
	private static String connectionName1;
	private static String uri2;
	private static String host2;
	private static String key2;
	private static String connectionName2;
	
	private static String defaultUri = ""; 
	private static String defaultHost = ""; 
	private static String defaultKey = ""; 
	private static String defaultConnectionName = ""; 

	private static LocalDate mondayCoreDate;
	private static LocalDate tuesdayCoreDate;
	private static LocalDate wednesdayCoreDate;
	private static LocalDate thursdayCoreDate;
	private static LocalDate fridayCoreDate;
	private static LocalDate saturdayCoreDate;
	private static LocalDate sundayCoreDate;

	private ObservableList<String> validCoreDayList = FXCollections.observableList(new ArrayList<String>());

	private BIASS3CompareSchedulePageController biass3CompareSchedulePageController = new BIASS3CompareSchedulePageController();

	@FXML private Button updateAPI1ParametersButton;
	@FXML private Button useLastSavedAPI1ParametersButton;
	@FXML private Button updateAPI2ParametersButton;
	@FXML private Button useLastSavedAPI2ParametersButton;
	@FXML private Button clearAllDatesButton;

	@FXML private TextField uriTextField1;
	@FXML private TextField hostTextField1;
	@FXML private TextField keyTextField1;
	@FXML private TextField connectionNameTextField1;
	@FXML private TextField uriTextField2;
	@FXML private TextField hostTextField2;
	@FXML private TextField keyTextField2;
	@FXML private TextField connectionNameTextField2;

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

		boolean host1Exists = prefs.get("s3_host1", null) != null;
		if (host1Exists)
		{
			hostTextField1.setText(prefs.get("s3_host1", defaultHost));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_host1", defaultHost);
			hostTextField1.setText(prefs.get("s3_host1", defaultHost));
		}
		host1 = prefs.get("s3_host1", defaultHost);

		boolean key1Exists = prefs.get("s3_key1", null) != null;
		if (key1Exists)
		{
			keyTextField1.setText(prefs.get("s3_key1", defaultKey));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_key1", defaultKey);
			keyTextField1.setText(prefs.get("s3_key1", defaultKey));
		}
		key1 = prefs.get("s3_key1", defaultKey);
		
		boolean connectionName1Exists = prefs.get("s3_connectionName1", null) != null;
		if (connectionName1Exists)
		{
			connectionNameTextField1.setText(prefs.get("s3_connectionName1", defaultConnectionName));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_connectionName1", defaultConnectionName);
			connectionNameTextField1.setText(prefs.get("s3_connectionName1", defaultConnectionName));
		}
		connectionName1 = prefs.get("s3_connectionName1", defaultConnectionName);
		
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

		boolean host2Exists = prefs.get("s3_host2", null) != null;
		if (host2Exists)
		{
			hostTextField2.setText(prefs.get("s3_host2", defaultHost));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_host2", defaultHost);
			hostTextField2.setText(prefs.get("s3_host2", defaultHost));
		}
		host2 = prefs.get("s3_host2", defaultHost);

		boolean key2Exists = prefs.get("s3_key2", null) != null;
		if (key2Exists)
		{
			keyTextField2.setText(prefs.get("s3_key2", defaultKey));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_key2", defaultKey);
			keyTextField2.setText(prefs.get("s3_key2", defaultKey));
		}
		key2 = prefs.get("s3_key2", defaultKey);
		
		boolean connectionName2Exists = prefs.get("s3_connectionName2", null) != null;
		if (connectionName2Exists)
		{
			connectionNameTextField2.setText(prefs.get("s3_connectionName2", defaultConnectionName));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_connectionName2", defaultConnectionName);
			connectionNameTextField2.setText(prefs.get("s3_connectionName2", defaultConnectionName));
		}
		connectionName2 = prefs.get("s3_connectionName2", defaultConnectionName);

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

	@FXML private void handleTextChangedHostTextField1()
	{
		hostTextField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}
	
	@FXML private void handleTextChangedHostTextField2()
	{
		hostTextField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedKeyTextField1()
	{
		keyTextField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}
	
	@FXML private void handleTextChangedKeyTextField2()
	{
		keyTextField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedConnectionNameTextField1()
	{
		connectionNameTextField1.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}
	
	@FXML private void handleTextChangedConnectionNameTextField2()
	{
		connectionNameTextField2.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}
	
	@FXML private void handleUpdateAPI1ParametersButton()
	{
		uriTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		hostTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		keyTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		connectionNameTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		uri1 = uriTextField1.getText();
		host1 = hostTextField1.getText();
		key1 = keyTextField1.getText();
		connectionName1 = connectionNameTextField1.getText();

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.put("s3_uri1", uri1);
			prefs.put("s3_host1", host1);
			prefs.put("s3_key1", key1);
			prefs.put("s3_connectionName1", connectionName1);
		}
	}
	
	@FXML private void handleUpdateAPI2ParametersButton()
	{
		uriTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		hostTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		keyTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		connectionNameTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		uri2 = uriTextField2.getText();
		host2 = hostTextField2.getText();
		key2 = keyTextField2.getText();
		connectionName2 = connectionNameTextField2.getText();

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.put("s3_uri2", uri2);
			prefs.put("s3_host2", host2);
			prefs.put("s3_key2", key2);
			prefs.put("s3_connectionName2", connectionName2);
		}
	}

	@FXML private void handleUseLastSavedAPI1ParametersButton()
	{
		uriTextField1.setText(uri1);
		uriTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		hostTextField1.setText(host1);
		hostTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		keyTextField1.setText(key1);
		keyTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		
		connectionNameTextField1.setText(connectionName1);
		connectionNameTextField1.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
	}
	
	@FXML private void handleUseLastSavedAPI2ParametersButton()
	{
		uriTextField2.setText(uri2);
		uriTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		hostTextField2.setText(host2);
		hostTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		keyTextField2.setText(key2);
		keyTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		
		connectionNameTextField2.setText(connectionName2);
		connectionNameTextField2.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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

	public static String getUri1()
	{
		return uri1;
	}
	
	public static String getUri2()
	{
		return uri2;
	}

	public static String getHost1()
	{
		return host1;
	}
	
	public static String getHost2()
	{
		return host2;
	}

	public static String getKey1()
	{
		return key1;
	}
	
	public static String getKey2()
	{
		return key2;
	}
	
	public static String getConnectionName1()
	{
		return connectionName1;
	}
	
	public static String getConnectionName2()
	{
		return connectionName2;
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