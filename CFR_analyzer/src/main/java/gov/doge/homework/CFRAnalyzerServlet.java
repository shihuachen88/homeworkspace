package gov.doge.homework;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.doge.homework.GovApiJsonDownload;

/**
 * Servlet implementation class CFRAnalyzerServlet
 */
@WebServlet(
		urlPatterns = { "/CFRAnalyzerServlet" }, 
		initParams = { 
				@WebInitParam(name = "CFR_API", value = "https://www.federalregister.gov/api/v1/documents?per_page=5")
		})

@SuppressWarnings("unused")
public class CFRAnalyzerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String API_URL = "https://www.federalregister.gov/api/v1/documents?per_page=5";
	private final ObjectMapper objectMapper = new ObjectMapper();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CFRAnalyzerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<html><body><h1>Hello World!</h1></body><html>");
//	}
	////////////////////////////////////////////////////////////////////////////////
		try {
	        // Example endpoint: list of Federal Register docs
	        // You can replace this with any valid eCFR/Federal API endpoint
	            String apiUrl = "https://www.federalregister.gov/api/v1/documents?per_page=5";

	            // Where to save JSON
	            Path outputFile = Path.of("gov_api_data.json");

	            GovApiJsonDownload.downloadAndSaveJson(apiUrl, outputFile);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	////////////////////////////////////////////////////////////////////////////////
//	 @Override
//	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//	            throws ServletException, IOException {

//	        response.setContentType("application/json");
//	        response.setCharacterEncoding("UTF-8");
	        

//	        try {
//	            Map<String, Integer> wordCounts = fetchAgencyWordCounts();
//
//	            // Convert Java map to JSON and write to response
//	            String jsonOutput = objectMapper.writeValueAsString(wordCounts);
//	            PrintWriter out = response.getWriter();
//	            out.print(jsonOutput);
//	            out.flush();
//	            
//	            out.println(jsonOutput.toString());
//	            
//	            out.println("<html><body><h1>output complet!</h1></body><html>");
//
//	        } catch (Exception e) {
//	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	            response.getWriter().write(
//	                "{\"error\":\"Failed to fetch agency data: " + e.getMessage() + "\"}"
//	            );
//	        }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	private Map<String, Integer> fetchAgencyWordCounts() throws Exception {

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> httpResponse =
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (httpResponse.statusCode() != 200) {
            throw new IOException("HTTP error " + httpResponse.statusCode());
        }

        JsonNode rootNode = objectMapper.readTree(httpResponse.body());
        JsonNode agenciesNode = rootNode.path("agencies"); // JSON array

        Map<String, Integer> counts = new HashMap<>();

        if (agenciesNode.isArray()) {
            Iterator<JsonNode> iterator = agenciesNode.elements();
            while (iterator.hasNext()) {
                JsonNode agency = iterator.next();

                // Change "raw_name" to the field your endpoint returns
                String rawName = agency.path("raw_name").asText(null);

                if (rawName != null && !rawName.isBlank()) {
                    int wordCount = countWords(rawName);
                    counts.put(rawName, wordCount);
                }
            }
        }

        return counts;
    }

    private int countWords(String input) {
        return input.trim().split("\\s+").length;
    }


}
