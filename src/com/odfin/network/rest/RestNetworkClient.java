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
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String baseUrl;
    private final Gson gson = new Gson();

    public RestNetworkClient(String baseUrl) { this.baseUrl = baseUrl; }

    public void sendMessage(Message msg) throws Exception {
        String json = gson.toJson(msg);
        HttpResponse<Void> resp = httpClient.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(baseUrl + "/messages"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                        .build(),
                HttpResponse.BodyHandlers.discarding());
        if (resp.statusCode() != 201) throw new RuntimeException("HTTP " + resp.statusCode());
    }

    public List<Message> getMessages() throws Exception {
        HttpResponse<String> resp = httpClient.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(baseUrl + "/messages"))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) throw new RuntimeException("HTTP " + resp.statusCode());
        return gson.fromJson(resp.body(), new TypeToken<List<Message>>(){}.getType());
    }
}