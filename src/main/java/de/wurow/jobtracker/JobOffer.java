package de.wurow.jobtracker;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class JobOffer {
    @Id
    private String id;
    private String titel;
    private String firma;
    private String ort;
    private String link;
    private LocalDate veroeffentlichtAm;
    private String quelle;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    protected JobOffer() {

    }

    public JobOffer(String referenznummer,
                    String titel,
                    String firma,
                    String ort,
                    String link,
                    LocalDate veroeffentlichtAm,
                    String quelle) {
        this.id = referenznummer;
        this.titel = titel;
        this.firma = firma;
        this.ort = ort;
        this.link = link;
        this.veroeffentlichtAm = veroeffentlichtAm;
        this.quelle = quelle;
        status = OfferStatus.NEU;
    }

    public String getId() {
        return id;
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

    public OfferStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JobOffer jobOffer)) return false;
        return Objects.equals(id, jobOffer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobOffer{" +
                "id='" + id + '\'' +
                ", titel='" + titel + '\'' +
                ", firma='" + firma + '\'' +
                ", ort='" + ort + '\'' +
                ", link='" + link + '\'' +
                ", veroeffentlichtAm=" + veroeffentlichtAm +
                ", quelle='" + quelle + '\'' +
                ", status=" + status +
                '}';
    }

    public void changeStatus(OfferStatus newStatus) {
        status = newStatus;
    }
}
