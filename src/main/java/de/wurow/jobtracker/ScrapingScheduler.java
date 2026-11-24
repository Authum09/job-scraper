package de.wurow.jobtracker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScrapingScheduler {

    private final ScrapingService scrapingService;

    public ScrapingScheduler(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @Scheduled(initialDelayString = "${scraping.interval:PT3H}", fixedDelayString = "${scraping.interval:PT3H}")
    public void scheduledRun() {
        scrapingService.run();
    }
}
