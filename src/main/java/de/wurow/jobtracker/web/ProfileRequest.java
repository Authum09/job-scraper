package de.wurow.jobtracker.web;

import de.wurow.jobtracker.SearchProfile;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProfileRequest(@NotBlank String berufsbezeichnung,
                             @NotBlank String ort,
                             @Min(1) @Max(200) int umkreis,
                             @Min(1) int angebotsart,
                             @NotNull List<String> pflicht,
                             @NotNull List<String> ausschluss) {

    public SearchProfile toEntity () {
        return new SearchProfile(berufsbezeichnung(),
                ort(),
                umkreis(),
                angebotsart(),
                pflicht(),
                ausschluss());
    }
}
