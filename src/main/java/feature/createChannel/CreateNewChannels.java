package feature.createChannel;

import java.io.IOException;
import java.io.StringWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;

import entity.Owner;
import request.GraphAPIRequest;

public class CreateNewChannels {
	public CreateNewChannels(String teamId, String displayName, String description, String membershipType, Owner owner) {
		GraphAPIRequest gReq = new GraphAPIRequest();

		gReq.setTeamId(teamId);
		gReq.setACCESS_TOKEN(owner.getAccessToken());
		gReq.setOption(2);

		try {
			// HttpClient is used to send all requests
			HttpClient client = HttpClient.newHttpClient();

			// Part 1: create channel
			JsonObject jsonObject = Json.createObjectBuilder()
					.add("displayName", displayName)
					.add("description", description)
					.add("membershipType", membershipType)
					.build();

			// Convert JSON object into string
			StringWriter stringWriter = new StringWriter();
			try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
				jsonWriter.write(jsonObject);
			}
			String requestBody = stringWriter.toString();

			// Part 2: send post request to add new channels to team
			HttpRequest postRequest = gReq.postRequest(requestBody);
			HttpResponse<String> teamResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

			// Check response status and handle errors
			if (teamResponse.statusCode() == 201) {
				System.out.println("Channel created successfully.");
			} else {
				System.out.println("Failed to create channel. Response Code: " + teamResponse.statusCode());
				System.out.println("Response Body: " + teamResponse.body());
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
