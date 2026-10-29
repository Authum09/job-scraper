package org.example;

import java.time.LocalDate;
import java.util.Objects;

public class JobOffer {
    private final String referenznummer;
    private final String titel;
    private final String firma;
    private final String ort;
    private final String link;
    private final LocalDate veroeffentlichtAm;
    private final String quelle;

    public JobOffer (String referenznummer,
                     String titel,
                     String firma,
                     String ort,
                     String link,
                     LocalDate veroeffentlichtAm,
                     String quelle) {
        this.referenznummer = referenznummer;
        this.titel = titel;
        this.firma = firma;
        this.ort = ort;
        this.link = link;
        this.veroeffentlichtAm = veroeffentlichtAm;
        this.quelle = quelle;
    }

    public String getReferenznummer() {
        return referenznummer;
    }

    public String getTitel() {
        return titel;
    }

    public String getFirma() {
        return firma;
    }

    public String getOrt() {
        return ort;
    }

    public String getLink() {
        return link;
    }

    public LocalDate getVeroeffentlichtAm() {
        return veroeffentlichtAm;
    }

    public String getQuelle() {
        return quelle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JobOffer jobOffer = (JobOffer) o;
        return Objects.equals(referenznummer, jobOffer.referenznummer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(referenznummer);
    }

    @Override
    public String toString() {
        return "JobOffer{" +
                "referenznummer='" + referenznummer + '\'' +
                ", titel='" + titel + '\'' +
                ", firma='" + firma + '\'' +
                ", ort='" + ort + '\'' +
                ", link='" + link + '\'' +
                ", veroeffentlichtAm=" + veroeffentlichtAm +
                ", quelle='" + quelle + '\'' +
                '}';
    }
}
