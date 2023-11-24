package com.bl.bias.app;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import com.bl.bias.exception.ErrorShutdown;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class BIASLaunch extends Application 
{
	// Version = Major release.minor release followed by date of software
	// 1.0 was 8/4/2021
	// 2.0 was 1/18/2022
	// 3.0 was 9/21/2022
	// 3.1 was 10/16/2022  (fixed bug in SSIM day of week)
	// 3.2 was 10/31/2022  (significantly sped up processing of bridge closure periods)
	// 3.3 was 12/12/2022  (added checks #3 and #4 for SSIM conversion)
	// 3.4 was 12/14/2022  (added submenus to GUI to group modules by functionality)
	// 3.5 was 1/26/2023   (added checks #5, #6 and #7 for SSIM conversion, added grade crossing speed analysis)
	// 3.6 was 2/15/2023   (added check #8 for SSIM conversion)
	// 3.7 was 3/20/2023   (added check #9 for SSIM conversion)
	// 3.8 was 5/4/2023    (fixed bug in conversion of local time to UTC when generating SSIM)
	// 3.9 was 7/10/2023   (added options for calculation of bridge closure periods)
	// 3.10 was 11/22/2023 (extended validity date until 1/31/2024)
	private final static String version = "3.10 (November 22, 2023)";
	private final static Integer lastIndexOfVersionToShowForReduced = 4;
 	//
	// Session validity
	private static Boolean validSession = true;
	//
	// Image files
	private final static String frameIconFileFL = "pic_frameIconFL.jpg";
	private final static String frameIconFileWE = "pic_frameIconWE.jpg";
	private static String frameIconToUse = "";
	private final static String headerFL = "pic_blHeaderBackgroundFL.png";
	private final static String headerWE = "pic_blHeaderBackgroundWE.png";
	private static String headerToUse = "";
	//
	// Expiration of this version of software
	private final static Integer softwareExpirationMonth = 1; // January = 1
	private final static Integer softwareExpirationDay = 31;
	private final static Integer softwareExpirationYear = 2024;
	//
	// User permissions
	private final static String permissions[] = 
	{
		// User, machine, logos, month expire, day expire, year expire, write prefs to registry, modules[]
		"WindschmittPeter, PWINDSCHMITT-P17, FL, 1, 31, 2024, TRUE, RTC Results Analysis, T-test Analysis, Bridge Closure Analysis, Maintenance Window Analysis, Radixx Res SSIM Conversion, Radixx Res SSIM Comparison, Grade Crossing Speed Analysis, USCG Bridge Compliance Analysis, General Config, Parse Config",
		};
	//************************************************************************************************

	@Override
	public void start(Stage primaryStage)
	{
		//  Check to see if software is valid given date
		Calendar cal = Calendar.getInstance();
		int currentDay = cal.get(Calendar.DAY_OF_MONTH);
		int currentMonth = cal.get(Calendar.MONTH);
		int currentYear = cal.get(Calendar.YEAR);

		if (currentYear > softwareExpirationYear)
			validSession = false;
		else if ((currentYear == softwareExpirationYear) && ((currentMonth + 1) > softwareExpirationMonth)) // Months are zero-based so add 1
			validSession = false;
		else if ((currentYear == softwareExpirationYear) && ((currentMonth + 1) == softwareExpirationMonth) && (currentDay > softwareExpirationDay)) // Months are zero-based so add 1
			validSession = false;

		if (permissions.length == 0)
			validSession = false;
		
		if (validSession)
		{
			//  From permissions, determine who this is
			BIASProcessPermissions whoAmI = new BIASProcessPermissions(permissions);

			//  If user has access to no modules then do not start app
			if (BIASProcessPermissions.getVerifiedUserModules().size() == 0)
				validSession = false;

			//  Determine if user is authorized for software based on date 
			if ((BIASProcessPermissions.getVerifiedUserExpirationYear() == null) || (BIASProcessPermissions.getVerifiedUserExpirationMonth() == null) || (BIASProcessPermissions.getVerifiedUserExpirationDay() == null))
				validSession = false;
			else if (currentYear > Integer.parseInt(BIASProcessPermissions.getVerifiedUserExpirationYear()))
				validSession = false;
			else if ((currentYear == Integer.parseInt(BIASProcessPermissions.getVerifiedUserExpirationYear())) && ((currentMonth + 1) > Integer.parseInt(BIASProcessPermissions.getVerifiedUserExpirationMonth()))) // Months are zero-based so add 1
				validSession = false;
			else if ((currentYear == Integer.parseInt(BIASProcessPermissions.getVerifiedUserExpirationYear()) && ((currentMonth + 1) == Integer.parseInt(BIASProcessPermissions.getVerifiedUserExpirationMonth()))) && (currentDay > Integer.parseInt(BIASProcessPermissions.getVerifiedUserExpirationDay()))) // Months are zero-based so add 1
				validSession = false;

			//  From permissions select logos and frame icon
			if (whoAmI.getVerifiedUserLocation() == null)
				validSession = false;
			else if (whoAmI.getVerifiedUserLocation().equals("FL"))
			{
				frameIconToUse = frameIconFileFL;
				headerToUse = headerFL;
			}
			else if (whoAmI.getVerifiedUserLocation().equals("WE"))
			{
				frameIconToUse = frameIconFileWE;
				headerToUse = headerWE;
			}
			else if (whoAmI.getVerifiedUserLocation().equals("ALL"))
			{
				Random randomGenerator=new Random();
				Integer selectLogo = randomGenerator.nextInt(2);
				if (selectLogo == 0)
				{
					frameIconToUse = frameIconFileFL;
					headerToUse = headerFL;
				}
				else
				{
					frameIconToUse = frameIconFileWE;
					headerToUse = headerWE;
				}
			}
			else
				validSession = false;
		}

		//  Main window
		Parent root1 = null;
		
		if (validSession)
		{
			try 
			{
				root1 = FXMLLoader.load(getClass().getResource("BIASMainWindow.fxml"));
			} 
			catch (IOException e) 
			{
				ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}

			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(frameIconToUse)));
			primaryStage.setTitle("BIAS v"+getReducedSoftwareVersion());
			primaryStage.setScene(new Scene(root1, 800, 500));
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		else
		{
			try 
			{
				root1 = FXMLLoader.load(getClass().getResource("BIASMainWindowExpired.fxml"));
			} 
			catch (IOException e) 
			{
				ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
			}

			primaryStage.setTitle("BIAS v"+getReducedSoftwareVersion());
			primaryStage.setScene(new Scene(root1, 800, 500));
			primaryStage.setResizable(false);
			primaryStage.show();
		}
	}
	
	public static String getSoftwareVersion() 
	{  
		return version;
	}

	public static String getReducedSoftwareVersion()
	{
		return version.substring(0, lastIndexOfVersionToShowForReduced);
	}

	public static String getFrameIconFile() 
	{
		return frameIconToUse;
	}

	public static String getHeaderFile() 
	{
		return headerToUse;
	}
	
	public static String getSoftwareExpirationDate() 
	{
		String softwareExpirationDate = softwareExpirationMonth +"/"+softwareExpirationDay+"/"+softwareExpirationYear;
		return softwareExpirationDate;
	}
}