package de.wurow.jobtracker;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SearchProfile {

    private String berufsbezeichnung;
    private String ort;
    private int umkreis;
    private int angebotsart;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> pflicht;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ausschluss;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


    public void update(String berufsbezeichnung,
                       String ort,
                       int umkreis,
                       int angebotsart,
                       List<String> pflicht,
                       List<String> ausschluss) {
        this.berufsbezeichnung = berufsbezeichnung;
        this.ort = ort;
        this.umkreis = umkreis;
        this.angebotsart = angebotsart;
        this.pflicht = new ArrayList<>(pflicht);
        this.ausschluss = new ArrayList<>(ausschluss);
    }
}
