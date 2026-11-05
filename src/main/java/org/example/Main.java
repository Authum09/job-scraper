package org.example;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {

        SearchCriteria c = new SearchCriteria("Fachinformatiker für Anwendungsentwicklung", "Oer-Erkenschwick", 20, 4);
        JobSource source = new ArbeitsagenturSource();

        List<JobOffer> jobOffers = source.search(c);

        for (JobOffer job: jobOffers) {
            System.out.println(job);
        }

    }
}