
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class RestApiMain {
    public static void main(String[] args) throws Exception {
        ApiClient apiClient = new ApiClient();
        Transcript transcript = new Transcript();
        transcript.setAudio_url("https://github.com/cr7vaibhav/Test_Audio/raw/main/i-like-turtles.mp3");
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(transcript);

        // POST REQUEST
        HttpRequest postRequest = HttpRequest.newBuilder() // this uses the builder pattern so we can call all our
                                                           // request in a chain of patterns
                .uri(new URI("https://api.assemblyai.com/v2/transcript")) // we give it a URL
                .header("Authorization", apiClient.makeRequest()) // the header with the api key
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest)) // we need to tell it what http request method
                                                                        // we want to use
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());

        System.out.println(postResponse.body());

        
        // since we need to pass in the id of the request we sent we need to grab it
        // we use gson

        transcript = gson.fromJson(postResponse.body(), Transcript.class);

        System.out.println(transcript.getId());

        // GET REQUEST
        HttpRequest postRequest = HttpRequest.newBuilder() // this uses the builder pattern so we can call all our
                                                           // request in a chain of patterns
                .uri(new URI("https://api.assemblyai.com/v2/transcript")) // we give it a URL
                .header("Authorization", apiClient.makeRequest()) // the header with the api key
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest)) // we need to tell it what http request method
                                                                        // we want to use
                .build();
    }
}
