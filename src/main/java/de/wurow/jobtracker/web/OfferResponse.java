package de.wurow.jobtracker.web;

import de.wurow.jobtracker.JobOffer;
import de.wurow.jobtracker.OfferStatus;

import java.time.LocalDate;

public record OfferResponse(String id,
                            String titel,
                            String firma,
                            String ort,
                            String link,
                            LocalDate veroeffentlichtAm,
                            String quelle,
                            OfferStatus status) {

    public static OfferResponse from (JobOffer offer) {
        return new OfferResponse(offer.getId(),
                offer.getTitel(),
                offer.getFirma(),
                offer.getOrt(),
                offer.getLink(),
                offer.getVeroeffentlichtAm(),
                offer.getQuelle(),
                offer.getStatus());
    }
}
