package de.wurow.jobtracker.web;


import de.wurow.jobtracker.JobOffer;
import de.wurow.jobtracker.JobOfferRepository;
import de.wurow.jobtracker.OfferStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final JobOfferRepository jobOfferRepository;

    public OfferController(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    @GetMapping
    public List<OfferResponse> getOffers(@RequestParam(required = false) OfferStatus status) {

        List<JobOffer> offers = status == null ? jobOfferRepository.findAll() : jobOfferRepository.findByStatus(status);

        return offers
                .stream()
                .map(OfferResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public OfferResponse getOffer(@PathVariable String id) {
        return jobOfferRepository.findById(id)
                .map(OfferResponse::from)
                .orElseThrow(() -> new NotFoundException("Angebot " + id + " nicht gefunden"));

    }

    @PatchMapping("/{id}")
    public OfferResponse updateStatus(@PathVariable String id, @Valid @RequestBody StatusUpdateRequest request) {
        JobOffer offer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Angebot " + id + " nicht gefunden"));

        offer.changeStatus(request.status());

        return OfferResponse.from(jobOfferRepository.save(offer));
    }


}
