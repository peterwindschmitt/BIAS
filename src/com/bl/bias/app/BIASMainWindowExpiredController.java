package com.bl.bias.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.InetAddress;

public class BIASMainWindowExpiredController 
{
	@FXML private GridPane mainGridPane;
	
	@FXML private Button exitButton;
	
	@FXML private Label bIASDetailsLabel;
	
	@FXML private TextArea debugTextArea;
    
    @FXML private void initialize() throws IOException
	{
    	String bIASDetailAsString = "This version of BIAS (v"+BIASLaunch.getReducedSoftwareVersion()+") is/was valid through "+BIASLaunch.getSoftwareExpirationDate()+"!";
    	bIASDetailAsString += " If this date has not yet passed there are configuration errors or permission issues.  ";
    	bIASDetailAsString += "\n\nPlease copy the contents below and email to Peter Windschmitt (peter.windschmitt@gobrightline.com) for resolution.";
    	bIASDetailsLabel.setText(bIASDetailAsString);
    	
    	String debugTextAreaAsString = "BIASLaunch version "+BIASLaunch.getReducedSoftwareVersion();
		debugTextAreaAsString += "\nBIAS expiration "+BIASLaunch.getSoftwareExpirationDate();
		debugTextAreaAsString += "\nJavaFX version "+System.getProperty("javafx.version");
		debugTextAreaAsString += "\nJava version "+System.getProperty("java.version");
		debugTextAreaAsString += "\nOperating system "+System.getProperty("os.name");
		debugTextAreaAsString += "\n\nUser "+System.getProperty("user.name");
		debugTextAreaAsString += "\nOn machine "+InetAddress.getLocalHost().getHostName();
		
		if ((BIASProcessPermissions.getVerifiedUserExpirationMonth() != null) && (BIASProcessPermissions.getVerifiedUserExpirationDay() != null) && (BIASProcessPermissions.getVerifiedUserExpirationYear() != null))
			debugTextAreaAsString += "\nAccess expires "+BIASProcessPermissions.getVerifiedUserExpirationMonth()+"/"+BIASProcessPermissions.getVerifiedUserExpirationDay()+"/"+BIASProcessPermissions.getVerifiedUserExpirationYear();
		
		debugTextAreaAsString += "\n\nClass Paths:\n";
		debugTextAreaAsString += System.getProperty("java.class.path");
		debugTextAreaAsString += "\n\nWorking Directory:\n";
		debugTextAreaAsString += System.getProperty("user.dir");
		debugTextAreaAsString += "\n\nJava Directory:\n";
		debugTextAreaAsString += System.getProperty("java.home");
    	debugTextArea.setText(debugTextAreaAsString);
	}
    
    @FXML private void handleExitButton(ActionEvent event) throws IOException 
    {
    	System.exit(0);
    }
}