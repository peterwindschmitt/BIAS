package com.bl.bias.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.bl.bias.exception.ErrorShutdown;

public final class BIASProcessPermissions 
{
	static String verifiedUserName;
	static String verifiedUserMachine;
	static String verifiedUserLocation;
	static String verifiedUserExpirationMonth;
	static String verifiedUserExpirationDay;
	static String verifiedUserExpirationYear;
	static String verifiedWriteUserPrefsToRegistry;
	static ArrayList<String> verifiedUserModules = new ArrayList<String>();
	
	BIASProcessPermissions(String[] permissions)
	{
		for (int i = 0; i < permissions.length; i++)
		{
			String candidateUserName = permissions[i].split(",")[0].trim();
			String candidateUserMachine = permissions[i].split(",")[1].trim();
			String candidateUserLocation = permissions[i].split(",")[2].trim();
			String candidateUserExpirationMonth = permissions[i].split(",")[3].trim();
			String candidateUserExpirationDay = permissions[i].split(",")[4].trim();
			String candidateUserExpirationYear = permissions[i].split(",")[5].trim();
			String candidateWriteUserPrefsToRegistry = permissions[i].split(",")[6].trim();
			
			ArrayList<String> candidateUserModules = new ArrayList<String>();
			for (int j = 0; j < permissions[i].split(",").length - 7; j++)
			{	
				candidateUserModules.add(permissions[i].split(",")[j + 7]);
			}
			
			try 
			{
				if ((candidateUserName.equals(System.getProperty("user.name"))) && (candidateUserMachine.equals(InetAddress.getLocalHost().getHostName())))
				{
					verifiedUserName = candidateUserName; 
					verifiedUserMachine = candidateUserMachine;
					verifiedUserLocation = candidateUserLocation;
					verifiedUserExpirationMonth = candidateUserExpirationMonth;
					verifiedUserExpirationDay = candidateUserExpirationDay;
					verifiedUserExpirationYear = candidateUserExpirationYear;
					verifiedWriteUserPrefsToRegistry = candidateWriteUserPrefsToRegistry;
					verifiedUserModules = candidateUserModules;
				}
			} 
			catch (UnknownHostException e) 
			{
				{
					ErrorShutdown.displayError(e, this.getClass().getCanonicalName());
				}
			}
		}  	
	}
	
	public String getVerifiedUserName()
	{
		return verifiedUserName;
	}
	
	public String getVerifiedUserMachine()
	{
		return verifiedUserMachine;
	}
	
	public String getVerifiedUserLocation()
	{
		return verifiedUserLocation;
	}
	
	public static String getVerifiedUserExpirationMonth()
	{
		return verifiedUserExpirationMonth;
	}
	
	public static String getVerifiedUserExpirationDay()
	{
		return verifiedUserExpirationDay;
	}
	
	public static String getVerifiedUserExpirationYear()
	{
		return verifiedUserExpirationYear;
	}
	
	public static String getVerifiedWriteUserPrefsToRegistry()
	{
		return verifiedWriteUserPrefsToRegistry;
	}
	
	public static ArrayList<String> getVerifiedUserModules()
	{
		return verifiedUserModules;
	}
}