package de.wurow.jobtracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupRunner implements CommandLineRunner {

    private final ScrapingService scrapingService;
    private final SearchProfileRepository searchProfileRepository;
    private final boolean runOnStartup;

    public StartupRunner(ScrapingService scrapingService, SearchProfileRepository searchProfileRepository, @Value("${scraping.run-on-startup:true}") boolean runOnStartup) {
        this.scrapingService = scrapingService;
        this.searchProfileRepository = searchProfileRepository;
        this.runOnStartup = runOnStartup;
    }

    @Override
    public void run(String... args) throws Exception {

        if (searchProfileRepository.count() == 0) {
            searchProfileRepository.save(new SearchProfile("Fachinformatiker für Anwendungsentwicklung", "Oer-Erkenschwick", 20, 4, List.of("anwendungsentwicklung"), List.of("studium")));
        }

        if (runOnStartup) {
            scrapingService.run();
        }
    }


}

