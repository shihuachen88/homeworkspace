package gov.doge.homework;

import java.net.http.*;
import java.net.URI;
import java.time.LocalDate;

public class EcfrDownloader {

		
		// TODO Auto-generated method stub
		private static final HttpClient client = HttpClient.newHttpClient();

	    public static final  String fetchTitle(int titleNumber) throws Exception {
	        String date = LocalDate.now().toString();
	        String url = String.format(
	            "https://www.ecfr.gov/api/versioner/v1/full/%s/title-%d.json",
	            date, titleNumber
	        );

	        HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .GET()
	            .build();

	        HttpResponse<String> response =
	            client.send(request, HttpResponse.BodyHandlers.ofString());

	        return response.body();
	    }
}
