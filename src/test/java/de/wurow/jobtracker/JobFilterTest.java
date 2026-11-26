package de.wurow.jobtracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JobFilterTest {


    private final JobFilter filter = new JobFilter();

    private JobOffer offer(String title) {
        return new JobOffer("test:" + title, title, "Testfirma", "Testort", "https://www.google.com/", LocalDate.of(2026, 1, 1), "test");
    }

    @Test
    void keepsOfferWhenTitleContainsRequiredKeyword() {

        List<JobOffer> offers = List.of(offer("Ausbildung Fachinformatiker Anwendungsentwicklung"),
                offer("Ausbildung Fachinformatiker Systemintegration"));

        List<JobOffer> result = filter.filter(offers, List.of("anwendungsentwicklung"), List.of());

        assertThat(result).containsExactly(offers.getFirst());
    }

    @Test
    void removesOfferWhenTitleContainsExcludedKeyword() {

        List<JobOffer> offers = List.of(offer("Duales Studium Informatik"));

        List<JobOffer> result = filter.filter(offers, List.of(), List.of("Studium"));

        assertThat(result).isEmpty();
    }

    @Test
    void keepsEverythingWhenRequiredListIsEmpty() {

        List<JobOffer> offers = List.of(offer("Ausbildung Fachinformatiker Anwendungsentwicklung"),
                offer("Ausbildung Fachinformatiker Systemintegration"));

        List<JobOffer> result = filter.filter(offers, List.of(), List.of());

        assertThat(result).hasSize(2);
    }

    @Test
    void ignoresCaseOfKeywords() {
        List<JobOffer> offers = List.of(offer("ausbildung fachinformatiker anwendungsentwicklung"));

        List<JobOffer> result = filter.filter(offers, List.of("Anwendungsentwicklung"), List.of());

        assertThat(result).containsExactly(offers.getFirst());
    }

    @Test
    void acceptsAnyOfSeveralRequiredKeywords() {
        List<JobOffer> offers = List.of(offer("fachinformatiker anwendungsentwicklung"));

        List<JobOffer> result = filter.filter(offers, List.of("anwendungsentwicklung", "datascience"), List.of());

        assertThat(result).containsExactly(offers.getFirst());
    }

    @Test
    void excludedKeywordsWinsOverRequiredKeywords(){
        List<JobOffer> offers = List.of(offer("studium fachinformatiker anwendungsentwicklung"));

        List<JobOffer> result = filter.filter(offers,List.of("anwendungsentwicklung"),List.of("studium"));

        assertThat(result).isEmpty();
    }

}