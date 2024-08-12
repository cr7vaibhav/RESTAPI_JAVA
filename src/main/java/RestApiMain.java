
import java.net.URI;
import java.net.http.HttpRequest;


public class RestApiMain {
    public static void main(String[] args) throws Exception {
     ApiClient apiClient=new ApiClient();
        //POST REQUEST
        HttpRequest postRequest = HttpRequest.newBuilder() //this uses the builder pattern so we can call all our request in a chain of patterns
                .uri(new URI("https://api.assemblyai.com/v2/transcript"))
                .header("Authorization", apiClient.makeRequest() )
                .build()

    }
}
