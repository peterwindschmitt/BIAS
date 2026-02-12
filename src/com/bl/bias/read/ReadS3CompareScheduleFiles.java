package com.bl.bias.read;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import org.json.JSONObject;

public class ReadS3CompareScheduleFiles 
{
	private String resultsMessage;
	private String accessToken;

	private Boolean validFile = true;
	
	private HttpClient client;
	private HttpRequest request;
	private HttpResponse<String> response;
	
	private JSONObject json;
	
	public ReadS3CompareScheduleFiles(String profileName, String uri, String clientId, String clientSecret, String userName, String password, String grantType, String code, LocalDate startDate, LocalDate endDate, 
			LocalDate mondayBaseline, LocalDate tuesdayBaseline, LocalDate wednesdayBaseline, LocalDate thursdayBaseline, LocalDate fridayBaseline, LocalDate saturdayBaseline, LocalDate sundayBaseline) throws Exception 
	{
		resultsMessage = "\nAttempting to connect to S3's API ...\n";
		
		// Get access token
		if (((uri != null) && (clientId != null) && (clientSecret != null) && (userName != null) && (password != null) && (grantType != null) && (code != null)) &&
				((!uri.trim().equals("")) && (!clientId.trim().equals("")) && (!clientSecret.trim().equals("")) && (!userName.trim().equals("")) && (!password.trim().equals("")) && (!grantType.trim().equals("")) && (code.trim() != "")))
		{
			client = HttpClient.newHttpClient();
			request = HttpRequest.newBuilder()
					.uri(URI.create(uri+"/oauth/v2/token"))
					.POST(HttpRequest.BodyPublishers.ofString("{\"client_id\":\""+clientId+"\","
							+ "\"client_secret\":\""+clientSecret+"\","
							+ "\"username\":\""+userName+"\","
							+ "\"password\":\""+password+"\","
							+ "\"grant_type\":\""+grantType+"\","
							+ "\"code\":\""+code+"\"}"))
					.build();
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			json = new JSONObject(response.body().toString());
			if (json.has("access_token")) 
			{
				accessToken = json.getString("access_token");
			} 
			else 
			{
				validFile = false;
			}

			/*request = HttpRequest.newBuilder()
					.uri(URI.create(uri+"/api/v3/navigation/management/service-types"))
					.GET()
					.build();

			response = client.send(request, HttpResponse.BodyHandlers.ofString());*/

			//System.out.println("2 request: "+request.toString());
			//response = client.send(request, HttpResponse.BodyHandlers.ofString());
			//System.out.println("service types response: "+response.body().toString());

		}
		else
		{
			validFile = false;
			resultsMessage += "\nINVALID CREDENTIALS SPECIFIED!\n";
		}

		// Get core day schedules as specified in config
		if (validFile)
		{

			System.out.println("Access Token: " + accessToken);
			
			System.out.println("MONDAY'S representative core date is "+mondayBaseline.getDayOfWeek()+" "+mondayBaseline);
			System.out.println("TUESDAY'S representative core date is "+tuesdayBaseline.getDayOfWeek()+" "+tuesdayBaseline);
			System.out.println("WEDNESDAY'S representative core date is "+wednesdayBaseline.getDayOfWeek()+" "+wednesdayBaseline);
			System.out.println("THURSDAY'S representative core date is "+thursdayBaseline.getDayOfWeek()+" "+thursdayBaseline);
			System.out.println("FRIDAY'S representative core date is "+fridayBaseline.getDayOfWeek()+" "+fridayBaseline);
			System.out.println("SATURDAY'S representative core date is "+saturdayBaseline.getDayOfWeek()+" "+saturdayBaseline);
			System.out.println("SUNDAY'S representative core date is "+sundayBaseline.getDayOfWeek()+" "+sundayBaseline);
		}
	}

	public Boolean getValidFile()
	{
		return validFile;
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}
}