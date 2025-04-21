package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	
	@FXML private Button updateAPIParametersButton;
	@FXML private Button useLastSavedAPIParametersButton;
	
	@FXML private TextField uriTextField;
	@FXML private TextField hostTextField;
	@FXML private TextField keyTextField;
	
	@FXML private void initialize() 
	{
		prefs = Preferences.userRoot().node("BIAS");

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
}