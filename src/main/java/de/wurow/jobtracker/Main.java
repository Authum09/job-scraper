package de.wurow.jobtracker;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {

        ConfigLoader configLoader = new ConfigLoader();
        SearchCriteria c = configLoader.load(Path.of("config.json"),SearchCriteria.class);
        JobSource source = new ArbeitsagenturSource();

        TelegramConfig telegramConfig = configLoader.load(Path.of("secrets.json"), TelegramConfig.class);

        TelegramNotifier notifier = new TelegramNotifier(telegramConfig.telegramToken(), telegramConfig.telegramChatId());

        List<JobOffer> jobOffers = source.search(c);
        JobFilter filter = new JobFilter();

        jobOffers = filter.filter(jobOffers, c.pflicht(), c.ausschluss());
        SeenStore seen = new SeenStore();

        Set<String> seenReferenceNumbers = seen.load();
        List<JobOffer> newOffers = jobOffers.stream().filter(offer -> !seenReferenceNumbers.contains(offer.getId())).toList();

        if (newOffers.isEmpty()) {
            notifier.send("Es wurden keine neuen Angebote gefunden");
            System.out.println("Keine neuen Angebote");
        }
        for (JobOffer job : newOffers) {
            notifier.send(job);
            seenReferenceNumbers.add(job.getId());
            System.out.println(job);
        }

        seen.save(seenReferenceNumbers);
    }
}