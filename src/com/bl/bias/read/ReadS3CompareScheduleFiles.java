package com.bl.bias.read;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bl.bias.app.BIASLaunch;
import com.bl.bias.objects.ServiceObject;
import com.bl.bias.tools.ConvertDateTime;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ReadS3CompareScheduleFiles 
{
	private String resultsMessage;
	private String accessToken;

	private ArrayList<ArrayList<ServiceObject>> coreDatesData = new ArrayList<ArrayList<ServiceObject>>();
	private ArrayList<ArrayList<ServiceObject>> analyzedDatesData = new ArrayList<ArrayList<ServiceObject>>();

	private Boolean validFile = true;
	
	private Boolean showDetailsForRetimedTrains;

	private HttpClient client;
	private HttpRequest request;
	private HttpResponse<String> response;

	private JSONObject responseAsJSON;

	public ReadS3CompareScheduleFiles(String profileName, String uri, String clientId, String clientSecret, String userName, String password, String grantType, String code, LocalDate startDate, LocalDate endDate, 
			LocalDate mondayBaseline, LocalDate tuesdayBaseline, LocalDate wednesdayBaseline, LocalDate thursdayBaseline, LocalDate fridayBaseline, LocalDate saturdayBaseline, LocalDate sundayBaseline, Boolean showDetailsForRetimedTrains) throws Exception 
	{
		resultsMessage = "\nAttempting to connect to S3's API ...\n";
		
		this.showDetailsForRetimedTrains = showDetailsForRetimedTrains;

		try
		{
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
				for (int i = 1 ; i <=7; i++)
				{
					ArrayList<ServiceObject> coreServicesOnADay = new ArrayList<ServiceObject>();
					String params = "";
					if (i == 1) 
						params = "date="+mondayBaseline+"&page_size=200";
					else if (i == 2) 
						params = "date="+tuesdayBaseline+"&page_size=200";
					else if (i == 3) 
						params = "date="+wednesdayBaseline+"&page_size=200";
					else if (i == 4) 
						params = "date="+thursdayBaseline+"&page_size=200";
					else if (i == 5) 
						params = "date="+fridayBaseline+"&page_size=200";
					else if (i == 6) 
						params = "date="+saturdayBaseline+"&page_size=200";
					else if (i == 7) 
						params = "date="+sundayBaseline+"&page_size=200";
					else
						System.exit(0);

					request = HttpRequest.newBuilder()
							.uri(URI.create(uri+"/api/v2/orientation/search-services?"+params))
							.header("Authorization", "Bearer " + accessToken)
							.GET()
							.build();

					response = client.send(request, HttpResponse.BodyHandlers.ofString());

					responseAsJSON = new JSONObject(response.body().toString());
					JSONArray servicesArray = responseAsJSON.getJSONObject("data").getJSONArray("services");

					//  Get individual service attributes
					for (int j = 0; j < servicesArray.length(); j++)
					{
						JSONObject obj = servicesArray.getJSONObject(j);
						Object serviceName = obj.get("name");
						Object serviceType = obj.getJSONObject("service_type").get("code");
						Object departureStation = obj.getJSONObject("departure_station").get("name");
						Object departureTimestamp = obj.getJSONObject("departure_station").get("departure_timestamp");
						String departureTimeAsString = departureTimestamp.toString().substring(departureTimestamp.toString().length()-13, departureTimestamp.toString().length());
						Object arrivalStation = obj.getJSONObject("arrival_station").get("name");
						Object arrivalTimestamp = obj.getJSONObject("arrival_station").get("arrival_timestamp");
						String arrivalTimeAsString = arrivalTimestamp.toString().substring(arrivalTimestamp.toString().length()-13, arrivalTimestamp.toString().length());

						ServiceObject coreServiceOnADay = new ServiceObject("CORE", String.valueOf(i), serviceName.toString(), serviceType.toString(), departureStation.toString(), departureTimeAsString, arrivalStation.toString(), arrivalTimeAsString);
						coreServicesOnADay.add(coreServiceOnADay);
					}
					coreDatesData.add(coreServicesOnADay);
				}
			}

			// Analysis dates
			if (validFile)
			{
				// For each day of requested range (start date --> end date)
				for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1))
				{
					String params = "date="+date+"&page_size=200";
					ArrayList<ServiceObject> actualServicesOnADay = new ArrayList<ServiceObject>();

					request = HttpRequest.newBuilder()
							.uri(URI.create(uri+"/api/v2/orientation/search-services?"+params))
							.header("Authorization", "Bearer " + accessToken)
							.GET()
							.build();

					response = client.send(request, HttpResponse.BodyHandlers.ofString());
					responseAsJSON = new JSONObject(response.body().toString());
					JSONArray servicesArray = responseAsJSON.getJSONObject("data").getJSONArray("services");

					//  Get individual service attributes
					for (int j = 0; j < servicesArray.length(); j++)
					{
						JSONObject obj = servicesArray.getJSONObject(j);

						Object serviceName = obj.get("name");
						Object serviceType = obj.getJSONObject("service_type").get("code");
						Object departureStation = obj.getJSONObject("departure_station").get("name");
						Object departureTimestamp = obj.getJSONObject("departure_station").get("departure_timestamp");
						String departureTimeAsString = departureTimestamp.toString().substring(departureTimestamp.toString().length()-13, departureTimestamp.toString().length());
						Object arrivalStation = obj.getJSONObject("arrival_station").get("name");
						Object arrivalTimestamp = obj.getJSONObject("arrival_station").get("arrival_timestamp");
						String arrivalTimeAsString = arrivalTimestamp.toString().substring(arrivalTimestamp.toString().length()-13, arrivalTimestamp.toString().length());

						ServiceObject actualServiceOnADay = new ServiceObject(date.toString(), String.valueOf(date.getDayOfWeek().getValue()), serviceName.toString(), serviceType.toString(), departureStation.toString(), departureTimeAsString, arrivalStation.toString(), arrivalTimeAsString);
						actualServicesOnADay.add(actualServiceOnADay);
					}				
					analyzedDatesData.add(actualServicesOnADay);
				}
				resultsMessage += "Loaded core schedules and all active schedules between "+startDate+" and "+endDate+"\n";
			}
		}
		catch (IOException e) 
		{
			validFile = false;

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Could not connect to specified URI.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
			alert.show();
		} 
		catch (InterruptedException e) {
			validFile = false;

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Could not connect to specified URI.");	
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(getClass().getResourceAsStream(BIASLaunch.getFrameIconFile())));
			alert.show();
		}
	}

	public ArrayList<ArrayList<ServiceObject>> getCoreDatesData()
	{
		return coreDatesData;
	}

	public ArrayList<ArrayList<ServiceObject>> getAnalyzedDatesData()
	{
		return analyzedDatesData;
	}
	
	public Boolean getShowDetailsForRetimedTrains()
	{
		return showDetailsForRetimedTrains;
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