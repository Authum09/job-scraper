package org.example;

import java.time.LocalDate;
import java.util.HashSet;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        LocalDate datum = LocalDate.of(2026,10,29);
        JobOffer a = new JobOffer("13060-970102598-S",
                "Ausbildung zum FIAE",
                "BFS health finance",
                "Dortmund",
                "https://www.arbeitsagentur.de/jobsuche/suche?suchbereich=ausbildung&was=Fachinformatiker-f%C3%BCr%20Anwendungsentwicklung&wo=45739%20Oer-Erkenschwick&umkreis=25&id=13660-970102598-S",
                datum,
                "Arbeitsagentur");

        JobOffer b = new JobOffer("13660-970102598-S",
                "Ausbildung zum FISI",
                "Bochum",
                "",
                "",
                LocalDate.of(2026,10,25),
                "");

        HashSet<JobOffer> test = new HashSet<>();
        test.add(a);
        test.add(b);
        System.out.println(a.equals(b));
        System.out.println("Size of hashset: " + test.size());
        SearchCriteria c = new SearchCriteria("Fachinformatiker für Anwendungsentwicklung", "Oer-Erkenschwick", 20, 4);
        ArbeitsagenturSource as = new ArbeitsagenturSource();
        System.out.println(as.search(c));
    }

}