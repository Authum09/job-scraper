package de.wurow.jobtracker.web;

import de.wurow.jobtracker.JobOffer;
import de.wurow.jobtracker.SearchProfile;

import java.util.List;

public record ProfileResponse(Long id,
                              String berufsbezeichnung,
                              String ort,
                              int umkreis,
                              int angebotsart,
                              List<String> pflicht,
                              List<String> ausschluss) {

    public static ProfileResponse from (SearchProfile profile) {
        return new ProfileResponse(profile.getId(),
                profile.getBerufsbezeichnung(),
                profile.getOrt(),
                profile.getUmkreis(),
                profile.getAngebotsart(),
                profile.getPflicht(),
                profile.getAusschluss());
    }
}
