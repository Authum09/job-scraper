package de.wurow.jobtracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapingService {

    private final JobSource source;
    private final JobFilter filter;
    private final JobOfferRepository jobOfferRepository;
    private final SearchProfileRepository searchProfileRepository;
    private final TelegramNotifier telegramNotifier;

    private static final Logger log = LoggerFactory.getLogger(ScrapingService.class);

    public ScrapingService(JobSource source,
                           JobFilter filter,
                           JobOfferRepository jobOfferRepository,
                           SearchProfileRepository searchProfileRepository,
                           TelegramNotifier telegramNotifier) {
        this.source = source;
        this.filter = filter;
        this.jobOfferRepository = jobOfferRepository;
        this.searchProfileRepository = searchProfileRepository;
        this.telegramNotifier = telegramNotifier;
    }

    public void run() {
        List<SearchProfile> profiles = searchProfileRepository.findAll();

        for (SearchProfile profile: profiles) {
            try {
                List<JobOffer> found =  source.search(profile);
                List<JobOffer> filtered = filter.filter(found, profile.getPflicht(),profile.getAusschluss());

                List<JobOffer> newOffers = filtered.stream().filter(offer -> !jobOfferRepository.existsById(offer.getId())).toList();


                for (JobOffer offer: newOffers) {
                    telegramNotifier.send(offer);
                    jobOfferRepository.save(offer);
                    log.info("Neues Angebot gesendet: {}", offer.getId());
                }
            } catch (Exception e) {
                log.error("Suche für Profil {} fehlgeschlagen", profile.getId(), e);
            }

        }
    }
}
