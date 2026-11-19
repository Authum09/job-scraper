package de.wurow.jobtracker.web;


import de.wurow.jobtracker.JobOfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final JobOfferRepository jobOfferRepository;

    public OfferController(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    @GetMapping
    public List<OfferResponse> getOffers() {
        return jobOfferRepository.findAll()
                .stream()
                .map(OfferResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public OfferResponse getOffer(@PathVariable String id) {
        return jobOfferRepository.findById(id)
                .map(OfferResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }


}
