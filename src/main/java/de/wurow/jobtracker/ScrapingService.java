package de.wurow.jobtracker;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapingService {

    private final JobSource source;
    private final JobFilter filter;
    private final JobOfferRepository jobOfferRepository;
    private final SearchProfileRepository searchProfileRepository;
    //private final TelegramNotifier telegramNotifier;

    public ScrapingService(JobSource source,
                           JobFilter filter,
                           JobOfferRepository jobOfferRepository,
                           SearchProfileRepository searchProfileRepository) {
        this.source = source;
        this.filter = filter;
        this.jobOfferRepository = jobOfferRepository;
        this.searchProfileRepository = searchProfileRepository;
    }

    public void run() throws Exception {
        List<SearchProfile> profiles = searchProfileRepository.findAll();

        for (SearchProfile profile: profiles) {
            List<JobOffer> found =  source.search(profile);
            List<JobOffer> filtered = filter.filter(found, profile.getPflicht(),profile.getAusschluss());

            List<JobOffer> newOffers = filtered.stream().filter(offer -> !jobOfferRepository.existsById(offer.getId())).toList();


            for (JobOffer offer: newOffers) {
                jobOfferRepository.save(offer);
                System.out.println(offer);
            }
        }
    }
}
