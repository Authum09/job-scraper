package org.example;

import java.util.List;

public record SearchCriteria(String berufsbezeichnung,
                             String ort,
                             int umkreis,
                             int angebotsart,
                             List<String> pflicht,
                             List<String> ausschluss) {
}
