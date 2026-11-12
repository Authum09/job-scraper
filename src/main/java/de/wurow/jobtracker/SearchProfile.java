package de.wurow.jobtracker;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SearchProfile {

    String berufsbezeichnung;
    String ort;
    int umkreis;
    int angebotsart;
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> pflicht;
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> ausschluss;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    protected SearchProfile() {

    }

    public SearchProfile(String berufsbezeichnung,
                         String ort,
                         int umkreis,
                         int angebotsart,
                         List<String> pflicht,
                         List<String> ausschluss) {
        this.berufsbezeichnung = berufsbezeichnung;
        this.ort = ort;
        this.umkreis = umkreis;
        this.angebotsart = angebotsart;
        this.pflicht = pflicht;
        this.ausschluss = ausschluss;
    }

    public String getBerufsbezeichnung() {
        return berufsbezeichnung;
    }

    public String getOrt() {
        return ort;
    }

    public int getUmkreis() {
        return umkreis;
    }

    public int getAngebotsart() {
        return angebotsart;
    }

    public List<String> getPflicht() {
        return pflicht;
    }

    public List<String> getAusschluss() {
        return ausschluss;
    }

    public Long getId() {
        return id;
    }
}
