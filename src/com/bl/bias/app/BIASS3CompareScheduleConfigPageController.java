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

	private static String uri;
	private static String host;
	private static String key;

	private static String defaultUri = ""; 
	private static String defaultHost = ""; 
	private static String defaultKey = ""; 

	private static LocalDate mondayCoreDate;
	private static LocalDate tuesdayCoreDate;
	private static LocalDate wednesdayCoreDate;
	private static LocalDate thursdayCoreDate;
	private static LocalDate fridayCoreDate;
	private static LocalDate saturdayCoreDate;
	private static LocalDate sundayCoreDate;

	private ObservableList<String> validCoreDayList = FXCollections.observableList(new ArrayList<String>());
	
	private BIASS3CompareSchedulePageController biass3CompareSchedulePageController = new BIASS3CompareSchedulePageController();
	
	@FXML private Button updateAPIParametersButton;
	@FXML private Button useLastSavedAPIParametersButton;
	@FXML private Button clearAllDatesButton;

	@FXML private TextField uriTextField;
	@FXML private TextField hostTextField;
	@FXML private TextField keyTextField;

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
		
		// See if API parameters are stored
		boolean uriExists = prefs.get("s3_uri", null) != null;
		if (uriExists)
		{
			uriTextField.setText(prefs.get("s3_uri", defaultUri));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_uri", defaultUri);
			uriTextField.setText(prefs.get("s3_uri", defaultUri));
		}
		uri = prefs.get("s3_uri", defaultUri);

		boolean hostExists = prefs.get("s3_host", null) != null;
		if (hostExists)
		{
			hostTextField.setText(prefs.get("s3_host", defaultHost));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_host", defaultHost);
			uriTextField.setText(prefs.get("s3_host", defaultHost));
		}
		host = prefs.get("s3_host", defaultHost);

		boolean keyExists = prefs.get("s3_key", null) != null;
		if (keyExists)
		{
			keyTextField.setText(prefs.get("s3_key", defaultKey));
		}
		else
		{
			if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
				prefs.put("s3_key", defaultKey);
			keyTextField.setText(prefs.get("s3_key", defaultKey));
		}
		key = prefs.get("s3_key", defaultKey);

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

	@FXML private void handleTextChangedURITextField()
	{
		uriTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedHostTextField()
	{
		hostTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleTextChangedKeyTextField()
	{
		keyTextField.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
	}

	@FXML private void handleUpdateAPIParametersButton()
	{
		uriTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		hostTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
		keyTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		uri = uriTextField.getText();
		host = hostTextField.getText();
		key = keyTextField.getText();

		if (BIASProcessPermissions.verifiedWriteUserPrefsToRegistry.toLowerCase().equals("true"))
		{
			prefs.put("s3_uri", uri);
			prefs.put("s3_host", host);
			prefs.put("s3_key", key);
		}
	}

	@FXML private void handleUseLastSavedAPIParametersButton()
	{
		uriTextField.setText(uri);
		uriTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		hostTextField.setText(host);
		hostTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

		keyTextField.setText(key);
		keyTextField.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
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

	public static String getUri()
	{
		return uri;
	}

	public static String getHost()
	{
		return host;
	}

	public static String getKey()
	{
		return key;
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