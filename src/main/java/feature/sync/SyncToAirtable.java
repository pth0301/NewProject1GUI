package feature.sync;

import java.io.IOException;
import java.io.StringWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

import javafx.concurrent.Task;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;

import entity.Owner;
import feature.JsonExtractor;
import request.AirtableAPIRequest;
import request.GraphAPIRequest;

public class SyncToAirtable extends Task<Void> {
	private String teamId;
	private String baseId;
	private String memberTableId;
	private String channelTableId;
	private String airtableAPIkey;
	private int period;
	private Owner owner;

	public SyncToAirtable(String teamId, String baseId, String memberTableId, String channelTableId, String airtableAPIkey, int period, Owner owner) {
		this.teamId = teamId;
		this.baseId = baseId;
		this.memberTableId = memberTableId;
		this.channelTableId = channelTableId;
		this.airtableAPIkey = airtableAPIkey;
		this.period = period;
		this.owner = owner;
	}

	@Override
	protected Void call() throws Exception {
		GraphAPIRequest gReq = new GraphAPIRequest();
		AirtableAPIRequest aReq = new AirtableAPIRequest();

		gReq.setTeamId(teamId);
		gReq.setACCESS_TOKEN(owner.getAccessToken());
		aReq.setBASE_ID(baseId);
		aReq.setMemberTableId(memberTableId);
		aReq.setChannelTableId(channelTableId);
		aReq.setAPI_KEY(airtableAPIkey);

		while (!isCancelled()) {
			for (int i = 1; i < 3; i++) {
				if (isCancelled()) {
					break;
				}

				gReq.setOption(i);
				aReq.setOption(i);
				if (i == 1) {
					System.out.println("#### SYNC MEMBERS ####");
				} else {
					System.out.println("#### SYNC CHANNELS ####");
				}
				sync(gReq, aReq);
			}

			System.out.println("Finish! Let check your Airtable!");

			// Sleep for the specified period, converting seconds to milliseconds
			try {
				Thread.sleep(period * 1000);
			} catch (InterruptedException e) {
				if (isCancelled()) {
					break;
				}
			}
		}

		return null;
	}

	private void sync(GraphAPIRequest gReq, AirtableAPIRequest aReq) {
		try {
			HttpClient client = HttpClient.newHttpClient();

			// PART 1: send getRequest to Team
			HttpRequest getRequest = gReq.getRequest();
			System.out.println("Get data from Team ...");
			HttpResponse<String> teamResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

			// PART 2: extract specific data (members/channels) and create a requestBody
			String requestBody = "";
			switch (gReq.getOption()) {
				case 1:
					JsonObject jsonMembers = new JsonExtractor().extractMembers(teamResponse);
					requestBody = convertToStr(jsonMembers);
					break;
				case 2:
					JsonObject jsonChannels = new JsonExtractor().extractChannels(teamResponse);
					requestBody = convertToStr(jsonChannels);
					break;
			}

			// PART 3: send postRequest to Airtable
			HttpRequest postRequest = aReq.postRequest(requestBody);
			System.out.println("Send data to Airtable ...");
			HttpResponse<String> airtableResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

		} catch (HttpTimeoutException e) {
			System.err.println("Request timed out: " + e.getMessage());
		} catch (IOException | InterruptedException e) {
			if (e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
				System.out.println("Task was interrupted");
			}
			e.printStackTrace();
		}
	}

	private static String convertToStr(JsonObject jsonObject) {
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.write(jsonObject);
		}
		return stringWriter.toString();
	}

	private static void showResponseStatus(HttpResponse<String> response) {
		System.out.println("Response Code: " + response.statusCode());
		System.out.println("Response Body: " + response.body());
	}
}
