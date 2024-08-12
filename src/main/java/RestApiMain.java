
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

        HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString()); // this send the post
                                                                                                   // req

        System.out.println(postResponse.body());

        // since we need to pass in the id of the request we sent we need to grab it
        // we use gson

        transcript = gson.fromJson(postResponse.body(), Transcript.class);

        System.out.println(transcript.getId());

        // GET REQUEST
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.assemblyai.com/v2/transcript" + transcript.getId()))
                .header("Authorization", apiClient.makeRequest())
                // .GET() //doesnt need a request body //since GET is the default we dont need
                // it
                .build();

        while (true) {
            HttpResponse<String> getResponse = httpClient.send(postRequest, BodyHandlers.ofString()); //sends get request
            transcript = gson.fromJson(postResponse.body(), Transcript.class);
            System.out.println(transcript.getStatus());

            if("completed".equals(transcript.getStatus())|| "error".equals(transcript.getStatus())){
                break;
            }
            Thread.sleep(1000);//we have to wait atleast a second to send another req
        }

        //outside this while loop we know the processing has been completed 



        

    }
}
