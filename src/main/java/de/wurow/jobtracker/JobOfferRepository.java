package de.wurow.jobtracker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, String> {

    List<JobOffer> findByStatus(OfferStatus status);
}
