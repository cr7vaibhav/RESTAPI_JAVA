
# Assembly AI Transcription Service Integration

This Java application demonstrates how to use Assembly AI's API to transcribe audio files. It covers sending a POST request to create a transcript, retrieving the transcript status with a GET request, and displaying the final transcription.

## Prerequisites

- Java 11 or higher
- Maven for dependency management
- Assembly AI API Key

## Setup

1. Clone this repository:

    ```bash
    git clone https://github.com/your-username/assembly-ai-transcription.git
    cd assembly-ai-transcription
    ```

2. Install dependencies:

    Add the following dependencies to your `pom.xml`:

    ```xml
    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.8</version>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents.client5</groupId>
            <artifactId>httpclient5</artifactId>
            <version>5.1</version>
        </dependency>
    </dependencies>
    ```

3. Replace `YOUR_API_KEY_HERE` in the code with your Assembly AI API key.

## Usage

### Creating a Transcript

To create a transcript, use a `POST` request. The audio file's URL is passed in the request body.

```java
public class Transcript {
    private String audio_url;
    // getters and setters
}

Transcript transcript = new Transcript();
transcript.setAudio_url("https://github.com/cr7vaibhav/Test_Audio/raw/main/i-like-turtles.mp3");
Gson gson = new Gson();
String jsonRequest = gson.toJson(transcript);

HttpRequest postRequest = HttpRequest.newBuilder()
    .uri(new URI("https://api.assemblyai.com/v2/transcript"))
    .header("Authorization", "Bearer YOUR_API_KEY_HERE")
    .header("Content-Type", "application/json")
    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
    .build();

HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());
transcript = gson.fromJson(postResponse.body(), Transcript.class);
System.out.println("Transcript ID: " + transcript.getId());
```

### Retrieving the Transcript

After creating the transcript, repeatedly check its status with a `GET` request until it's completed.

```java
HttpRequest getRequest = HttpRequest.newBuilder()
    .uri(new URI("https://api.assemblyai.com/v2/transcript/" + transcript.getId()))
    .header("Authorization", "Bearer YOUR_API_KEY_HERE")
    .build();

while (true) {
    HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());
    transcript = gson.fromJson(getResponse.body(), Transcript.class);
    System.out.println("Status: " + transcript.getStatus());

    if ("completed".equals(transcript.getStatus()) || "error".equals(transcript.getStatus())) {
        break;
    }
    Thread.sleep(1000);  // Wait before sending the next request
}

System.out.println("Transcription completed: " + transcript.getText());
```

## Output Example

```
Transcript ID: 9bb162ce-50dd-42d9-b0be-39c3fb32e403
Status: processing
Status: completed
Transcription completed: I like turtles.
```

