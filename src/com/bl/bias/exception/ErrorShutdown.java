package com.bl.bias.exception;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorShutdown 
{
	public static void displayError(Exception e, String where)
	{
		Platform.runLater(new Runnable()
		{
			@Override
			public void run() 
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fatal Error Encountered");
				if ((e.getMessage() == null) && (where != null))
					alert.setContentText(e.getStackTrace()+" in "+e.getClass()+" in "+where);
				else if ((e.getMessage() == null) && (where == null))
					alert.setContentText(e.getStackTrace()+" in "+e.getClass());
				else
					alert.setContentText(e.getMessage()+"\n"+e.getStackTrace()+" in "+e.getClass()+" in "+where);

				alert.showAndWait();
				System.exit(0);
			}
		});
	}
}
