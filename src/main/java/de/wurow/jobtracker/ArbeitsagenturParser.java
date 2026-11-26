package de.wurow.jobtracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ArbeitsagenturParser {

    private static final String DETAIL_URL = "https://www.arbeitsagentur.de/jobsuche/jobdetail/";
    private static final String ID_PREFIX = "arbeitsagentur:";
    private static final String SOURCE_NAME = "Arbeitsagentur";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<JobOffer> parse(String json) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(json);


        List<JobOffer> jobOffers = new ArrayList<>();

        for (JsonNode job : root.path("ergebnisliste")) {
            LocalDate date = null;
            String publicationDate = job.path("datumErsteVeroeffentlichung").asText();
            String referenceNumber = job.path("referenznummer").asText();

            if (!publicationDate.isEmpty()) {
                date = LocalDate.parse(publicationDate);
            }


            JobOffer jobOffer = new JobOffer(ID_PREFIX + referenceNumber,
                    job.path("stellenangebotsTitel").asText(),
                    job.path("firma").asText(),
                    job.path("stellenlokationen").path(0).path("adresse").path("ort").asText(),
                    DETAIL_URL + referenceNumber,
                    date,
                    SOURCE_NAME);

            jobOffers.add(jobOffer);
        }

        return jobOffers;
    }
}
