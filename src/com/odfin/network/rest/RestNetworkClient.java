package com.odfin.network.rest;

import com.odfin.domain.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RestNetworkClient {
    private final HttpClient httpClient;
    private final String baseUrl;
    private final Gson gson = new Gson();

    public RestNetworkClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.baseUrl = baseUrl;
    }

    public void sendMessage(Message msg) throws Exception {
        String json = gson.toJson(msg);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/messages"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        if (response.statusCode() != 201) {
            throw new RuntimeException("Fehler beim Senden: HTTP " + response.statusCode());
        }
    }

    public List<Message> getMessages() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/messages"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Fehler beim Abrufen: HTTP " + response.statusCode());
        }
        return gson.fromJson(response.body(), new TypeToken<List<Message>>(){}.getType());
    }
}
