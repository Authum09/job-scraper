package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;



public class ArbeitsagenturSource {
    private static final String URL = "https://rest.arbeitsagentur.de/jobboerse/jobsuche-service/pc/v6/jobs?";
    private static final String API_KEY = "jobboerse-jobsuche";
    private final HttpClient CLIENT = HttpClient.newHttpClient();

    public String search (SearchCriteria c) throws Exception {
        String uri = URL;
        uri += "was=" + URLEncoder.encode(c.berufsbezeichnung(), StandardCharsets.UTF_8) + "&";
        uri += "wo=" + URLEncoder.encode(c.ort(), StandardCharsets.UTF_8) + "&";
        uri += "umkreis=" + c.umkreis() + "&";
        uri += "angebotsart=" + c.angebotsart();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).header("X-Api-Key", API_KEY).GET().build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println(response.statusCode());
        } else {
            throw new IOException ("Status Code: " + response.statusCode() + "\n" +
                    "Body: " + response.body());

        }
        System.out.println(response.statusCode());
        return response.body();
    }

}
