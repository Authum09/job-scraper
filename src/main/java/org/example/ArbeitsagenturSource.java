package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ArbeitsagenturSource {
    private static final String SEARCH_URL = "https://rest.arbeitsagentur.de/jobboerse/jobsuche-service/pc/v6/jobs?";
    private static final String API_KEY = "jobboerse-jobsuche";
    private final HttpClient client = HttpClient.newHttpClient();
    final String DETAIL_URL = "https://www.arbeitsagentur.de/jobsuche/jobdetail/";

    public List<JobOffer> search(SearchCriteria c) throws IOException, InterruptedException {

        String url = buildSearchUrl(c);
        String json = fetch(url);

        return parse(json);
    }

    private List<JobOffer> parse(String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(json);


        List<JobOffer> jobOffers = new ArrayList<>();

        for (JsonNode job : root.path("ergebnisliste")) {
            LocalDate date = null;
            String publicationDate = job.path("datumErsteVeroeffentlichung").asText();
            String referenceNumber = job.path("referenznummer").asText();

            if (!publicationDate.isEmpty()) {
                date = LocalDate.parse(job.path("datumErsteVeroeffentlichung").asText());
            }


            JobOffer jobOffer = new JobOffer(referenceNumber,
                    job.path("stellenangebotsTitel").asText(),
                    job.path("firma").asText(),
                    job.path("stellenlokationen").path(0).path("adresse").path("ort").asText(),
                    DETAIL_URL + referenceNumber,
                    date,
                    "Arbeitsagentur");
            jobOffers.add(jobOffer);
        }

        return jobOffers;
    }

    private String buildSearchUrl(SearchCriteria c) {
        String url = SEARCH_URL;
        url += "was=" + URLEncoder.encode(c.berufsbezeichnung(), StandardCharsets.UTF_8) + "&";
        url += "wo=" + URLEncoder.encode(c.ort(), StandardCharsets.UTF_8) + "&";
        url += "umkreis=" + c.umkreis() + "&";
        url += "angebotsart=" + c.angebotsart();

        return url;
    }

    private String fetch(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("X-Api-Key", API_KEY).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Status Code: " + response.statusCode() + "\n" +
                    "Body: " + response.body());
        }


        return response.body();
    }


}
