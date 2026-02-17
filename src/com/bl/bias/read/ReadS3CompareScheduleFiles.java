package com.bl.bias.read;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bl.bias.objects.ServiceObject;
import com.bl.bias.tools.ConvertDateTime;

public class ReadS3CompareScheduleFiles 
{
	private String resultsMessage;
	private String accessToken;

	private ArrayList<ArrayList<ServiceObject>> coreDatesData = new ArrayList<ArrayList<ServiceObject>>();
	private ArrayList<ArrayList<ServiceObject>> analyzedDatesData = new ArrayList<ArrayList<ServiceObject>>();

	private Boolean validFile = true;

	private HttpClient client;
	private HttpRequest request;
	private HttpResponse<String> response;

	private JSONObject responseAsJSON;

	public ReadS3CompareScheduleFiles(String profileName, String uri, String clientId, String clientSecret, String userName, String password, String grantType, String code, LocalDate startDate, LocalDate endDate, 
			LocalDate mondayBaseline, LocalDate tuesdayBaseline, LocalDate wednesdayBaseline, LocalDate thursdayBaseline, LocalDate fridayBaseline, LocalDate saturdayBaseline, LocalDate sundayBaseline) throws Exception 
	{
		resultsMessage = "\nAttempting to connect to S3's API ...\n";

		// TODO:  Try/catch
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
			responseAsJSON = new JSONObject(response.body().toString());
			if (responseAsJSON.has("access_token")) 
			{
				accessToken = responseAsJSON.getString("access_token");
				resultsMessage += "Connected to "+profileName+" at "+ConvertDateTime.getTimeStamp()+"\n";
			} 
			else 
			{
				validFile = false;
			}
		}
		else
		{
			validFile = false;
			resultsMessage += "\nINVALID CREDENTIALS SPECIFIED!\n";
		}

		// Core schedule dates
		if (validFile)
		{
			// For each day of week (Monday = 1 .... Sunday = 7)
			ArrayList<ServiceObject> servicesOnADay = new ArrayList<ServiceObject>();
			for (int i = 1 ; i <=7; i++)
			{
				servicesOnADay.clear();
				String params = "";
				if (i == 1) 
					params = "schedule_date="+mondayBaseline+"&page_size=200";
				else if (i == 2) 
					params = "schedule_date="+tuesdayBaseline+"&page_size=200";
				else if (i == 3) 
					params = "schedule_date="+wednesdayBaseline+"&page_size=200";
				else if (i == 4) 
					params = "schedule_date="+thursdayBaseline+"&page_size=200";
				else if (i == 5) 
					params = "schedule_date="+fridayBaseline+"&page_size=200";
				else if (i == 6) 
					params = "schedule_date="+saturdayBaseline+"&page_size=200";
				else if (i == 7) 
					params = "schedule_date="+sundayBaseline+"&page_size=200";
				else
					System.exit(0);

				request = HttpRequest.newBuilder()
						.uri(URI.create(uri+"/api/v3/navigation/trips?"+params))
						.header("Authorization", "Bearer " + accessToken)
						.GET()
						.build();

				response = client.send(request, HttpResponse.BodyHandlers.ofString());
				responseAsJSON = new JSONObject(response.body().toString());
				JSONArray dataArray = responseAsJSON.getJSONArray("data");

				//  Get individual service attributes
				for (int j = 0; j < dataArray.length(); j++)
				{
					JSONObject obj = dataArray.getJSONObject(j);
					String serviceName = (String) obj.get("service_name");
					String serviceType = (String) obj.get("service_type_code");
					String serviceIdentifier = (String) obj.get("service_identifier");

					// Get info from service identifier
					Integer serviceIdentifierLength = serviceIdentifier.length();
					String destinationLocation = serviceIdentifier.substring(serviceIdentifierLength - 3, serviceIdentifierLength);
					String originLocation = serviceIdentifier.substring(serviceIdentifierLength - 7, serviceIdentifierLength - 4);
					String destinationDateTime = serviceIdentifier.substring(serviceIdentifierLength - 24, serviceIdentifierLength - 8);
					String originDateTime = serviceIdentifier.substring(serviceIdentifierLength - 41, serviceIdentifierLength - 25);

					ServiceObject serviceOnADay = new ServiceObject("CORE", serviceName, serviceType, originLocation, originDateTime, destinationLocation, destinationDateTime);
					servicesOnADay.add(serviceOnADay);
				}
				coreDatesData.add(servicesOnADay);
			}

			// Analysis dates
			// For each day of requested range (start date --> end date)
			for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1))
			{
				servicesOnADay.clear();
				String params = "schedule_date="+date+"&page_size=200";
				
				request = HttpRequest.newBuilder()
						.uri(URI.create(uri+"/api/v3/navigation/trips?"+params))
						.header("Authorization", "Bearer " + accessToken)
						.GET()
						.build();

				response = client.send(request, HttpResponse.BodyHandlers.ofString());
				responseAsJSON = new JSONObject(response.body().toString());
				JSONArray dataArray = responseAsJSON.getJSONArray("data");

				//  Get individual service attributes
				for (int j = 0; j < dataArray.length(); j++)
				{
					JSONObject obj = dataArray.getJSONObject(j);
					String serviceName = (String) obj.get("service_name");
					String serviceType = (String) obj.get("service_type_code");
					String serviceIdentifier = (String) obj.get("service_identifier");

					// Get info from service identifier
					Integer serviceIdentifierLength = serviceIdentifier.length();
					String destinationLocation = serviceIdentifier.substring(serviceIdentifierLength - 3, serviceIdentifierLength);
					String originLocation = serviceIdentifier.substring(serviceIdentifierLength - 7, serviceIdentifierLength - 4);
					String destinationDateTime = serviceIdentifier.substring(serviceIdentifierLength - 24, serviceIdentifierLength - 8);
					String originDateTime = serviceIdentifier.substring(serviceIdentifierLength - 41, serviceIdentifierLength - 25);

					ServiceObject serviceOnADay = new ServiceObject(date.toString(), serviceName, serviceType, originLocation, originDateTime, destinationLocation, destinationDateTime);
					servicesOnADay.add(serviceOnADay);
				}
				analyzedDatesData.add(servicesOnADay);
			}			
		}
		// TODO:  results message (including counts/dates)
	}

	public ArrayList<ArrayList<ServiceObject>> getCoreDatesDate()
	{
		return coreDatesData;
	}
	
	public ArrayList<ArrayList<ServiceObject>> getAnalyzedDatesDate()
	{
		return analyzedDatesData;
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