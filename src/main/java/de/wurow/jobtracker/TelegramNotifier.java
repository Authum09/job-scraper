package de.wurow.jobtracker;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TelegramNotifier {
    private static final String BASE_URL = "https://api.telegram.org/bot";
    private final HttpClient client = HttpClient.newHttpClient();
    private final String token;
    private final String chatId;

    public TelegramNotifier(String token, String chatId) {
        this.token = token;
        this.chatId = chatId;
    }

    public void send(String text) throws IOException, InterruptedException {
        String url = BASE_URL + token + "/sendMessage";
        String body = "chat_id=" + chatId + "&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Status Code: " + response.statusCode() + "\n"
                    + "BODY: " + response.body());
        }
    }

    public void send (JobOffer offer) throws IOException,InterruptedException {
        send(formatMessage(offer));
    }

    private String formatMessage (JobOffer offer) {
        return  "Titel: " + offer.getTitel() + "\n" +
                "Firma: " + offer.getFirma() + "\n" +
                "Ort: " + offer.getOrt() + "\n" +
                "Link: " + offer.getLink();
    }
}
