package org.example;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class Main {
    static void main(String[] args) throws Exception {

        ConfigLoader configLoader = new ConfigLoader();
        SearchCriteria c = configLoader.buildSearchCriteria(Path.of("config.json"));
        JobSource source = new ArbeitsagenturSource();

        List<JobOffer> jobOffers = source.search(c);
        JobFilter filter = new JobFilter();

        jobOffers = filter.filter(jobOffers, c.pflicht(), c.ausschluss());
        SeenStore seen = new SeenStore();

        Set<String> seenReferenceNumbers = seen.load();
        List<JobOffer> newOffers = jobOffers.stream().filter(s -> !seenReferenceNumbers.contains(s.getReferenznummer())).toList();

        if (newOffers.isEmpty()) {
            System.out.println("Keine neuen Angebote");
        }
        for (JobOffer job : newOffers) {
            seenReferenceNumbers.add(job.getReferenznummer());
            System.out.println(job);
        }

        seen.save(seenReferenceNumbers);
    }
}