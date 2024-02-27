package com.bl.bias.app;

import java.util.prefs.Preferences;

import javafx.fxml.FXML;

public class BIASModifiedOtpConfigPageController 
{
	private static Preferences prefs;
	
	@FXML private void initialize()
	{
		// Check for prefs
		prefs = Preferences.userRoot().node("BIAS");		
	}
}