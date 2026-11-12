package de.wurow.jobtracker;

import java.util.List;

public class JobFilter {
    public List<JobOffer> filter(List<JobOffer> jobOffers, List<String> pflicht, List<String> ausschluss) {
        return jobOffers.stream().filter(offer -> passt(offer, pflicht, ausschluss)).toList();
    }

    private boolean passt(JobOffer offer, List<String> pflicht, List<String> ausschluss) {
        String titel = offer.getTitel().toLowerCase();

        boolean pflichtErfuellt = pflicht.isEmpty() || pflicht.stream().anyMatch(p -> titel.contains(p.toLowerCase()));
        boolean keinAusschluss = ausschluss.stream().noneMatch(a -> titel.contains(a.toLowerCase()));

        return pflichtErfuellt && keinAusschluss;

    }
}
