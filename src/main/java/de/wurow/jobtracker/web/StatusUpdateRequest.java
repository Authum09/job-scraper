package de.wurow.jobtracker.web;

import de.wurow.jobtracker.OfferStatus;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(
        @NotNull OfferStatus status
) {
}
