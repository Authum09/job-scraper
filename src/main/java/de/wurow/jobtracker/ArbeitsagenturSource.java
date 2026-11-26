package de.wurow.jobtracker;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class ArbeitsagenturSource implements JobSource {
    private static final String SEARCH_URL = "https://rest.arbeitsagentur.de/jobboerse/jobsuche-service/pc/v6/jobs?";
    private static final String API_KEY = "jobboerse-jobsuche";
    private static final int PAGE_SIZE = 100;
    private final HttpClient client = HttpClient.newHttpClient();
    private final ArbeitsagenturParser parser;


    public ArbeitsagenturSource(ArbeitsagenturParser parser) {
        this.parser = parser;
    }

    @Override
    public List<JobOffer> search(SearchProfile c) throws IOException, InterruptedException {

        String url = buildSearchUrl(c);
        String json = fetch(url);

        return parser.parse(json);
    }


    private String buildSearchUrl(SearchProfile c) {
        String url = SEARCH_URL;
        url += "was=" + URLEncoder.encode(c.getBerufsbezeichnung(), StandardCharsets.UTF_8) + "&";
        url += "wo=" + URLEncoder.encode(c.getOrt(), StandardCharsets.UTF_8) + "&";
        url += "umkreis=" + c.getUmkreis() + "&";
        url += "angebotsart=" + c.getAngebotsart() + "&";
        url += "size=" + PAGE_SIZE;

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
