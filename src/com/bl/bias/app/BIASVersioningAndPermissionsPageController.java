package com.bl.bias.app;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BIASVersioningAndPermissionsPageController
{	
	private String bIASVersionAsString;
	private String availableModulesAsString;
	private String userValidThroughDateAsString;
	
	@FXML private Label bIASVersion;
	@FXML private Label javaFxVersion;
	@FXML private Label javaVersion;
	@FXML private Label osName;
	@FXML private Label userName;
	@FXML private Label userMachine;
	@FXML private Label availableModules;
	
	public BIASVersioningAndPermissionsPageController() 
	{
		bIASVersionAsString = BIASLaunch.getReducedSoftwareVersion()+" (valid through "+BIASLaunch.getSoftwareExpirationDate()+")";
		availableModulesAsString = BIASProcessPermissions.getVerifiedUserModules().toString().replace("[", "").replace("]", "").trim();
		userValidThroughDateAsString = BIASProcessPermissions.getVerifiedUserExpirationMonth()+"/"+BIASProcessPermissions.getVerifiedUserExpirationDay()+"/"+BIASProcessPermissions.getVerifiedUserExpirationYear();	
	}
	
	@FXML private void initialize() throws UnknownHostException 
	{
		 bIASVersion.setText(bIASVersionAsString);
		 javaFxVersion.setText(System.getProperty("javafx.version"));
		 javaVersion.setText(System.getProperty("java.version"));
		 osName.setText(System.getProperty("os.name"));
		 userName.setText(System.getProperty("user.name")+" (valid through "+userValidThroughDateAsString+")");
		 userMachine.setText(InetAddress.getLocalHost().getHostName());
		 availableModules.setText(availableModulesAsString);
	}
}