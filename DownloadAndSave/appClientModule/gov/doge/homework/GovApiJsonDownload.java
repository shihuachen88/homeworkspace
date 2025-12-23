package gov.doge.homework;
	
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;



	@SuppressWarnings("unused")
	public class GovApiJsonDownload {

		public static void main(String[] args) {
			try {
		        // Example endpoint: list of Federal Register docs
		        // You can replace this with any valid eCFR/Federal API endpoint
		            String apiUrl = "https://www.federalregister.gov/api/v1/documents?per_page=5";

		            // Where to save JSON
		            Path outputFile = Path.of("gov_api_data.json");

		            downloadAndSaveJson(apiUrl, outputFile);

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }

		public static void downloadAndSaveJson(String apiUrl, Path outFile) throws IOException, InterruptedException {

		        HttpClient client = HttpClient.newHttpClient();
		        ObjectMapper mapper = new ObjectMapper();
		        mapper.enable(SerializationFeature.INDENT_OUTPUT);

		        HttpRequest request = HttpRequest.newBuilder()
		                .uri(URI.create(apiUrl))
		                .header("Accept", "application/json")
		                .GET()
		                .build();

		        HttpResponse<String> response =
		                client.send(request, HttpResponse.BodyHandlers.ofString());

		        if (response.statusCode() != 200) {
		            System.err.println("Failed: HTTP " + response.statusCode());
		            System.err.println(response.body());
		            return;
		        }

		        // Parse & preserve JSON
		        JsonNode root = mapper.readTree(response.body());

		        // Write pretty-printed JSON file
		        mapper.writeValue(outFile.toFile(), root);

		        System.out.println("JSON saved to " + outFile);
		        
		        /* NDJSON (one object per line)
		        for (JsonNode item : root.path("result")) {
		        	Writer writer.write(item.toString());
		        	writer.newLine();
		        }*/
		}
	}
