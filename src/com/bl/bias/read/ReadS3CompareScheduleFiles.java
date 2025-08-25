package com.bl.bias.read;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.bl.bias.tools.ConvertDateTime;

public class ReadS3CompareScheduleFiles 
{
	private String resultsMessage;

	private Boolean validFile = true;

	public ReadS3CompareScheduleFiles(String uri, String host, String key) throws Exception 
	{
		// Check API parameters in module's config 
		if ((uri != null) && (host != null) && (key != null))
		{
			resultsMessage = "\nAttempting to connect to S3's API ...\n";

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(uri))
					.header("X-RapidAPI-Host", host)
					.header("X-RapidAPI-Key", key)
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response = null;
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(response.body());
		}
		else
		{
			resultsMessage = "\nInvalid API URI, host or key specified!\n";
		}
		/*
		if ((rho.getValidHeader()) && (rco.getValidCarrier()) && (rfo.getValidFlightLegRecord()))
		{
			ssimText = rho.getHeader() + "\n"+rco.getCarrier() + "\n"+ rfo.getFlightLegs() + "\n" + rto.getTrailer();
			resultsMessage += "Read "+objectCount+" objects from spreadsheet \n";
			resultsMessage += "Finished parsing Excel file at "+ConvertDateTime.getTimeStamp()+"\n\n";
		}
		else
		{
			validFile = false;
			resultsMessage += "Error in reading objects from spreadsheet";
		}*/
	}

	public String getResultsMessage()
	{
		return resultsMessage;
	}

	public Boolean getValidFile()
	{
		return validFile;
	}
}