package de.wurow.jobtracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class ArbeitsagenturParserTest {

    private final ArbeitsagenturParser parser = new ArbeitsagenturParser();

    private String loadJson () throws IOException {
        return new String(getClass().getResourceAsStream("/arbeitsagentur-response.json").readAllBytes(), StandardCharsets.UTF_8);
    }

    @Test
    void parseAllFieldsOfCompleteEntry() throws IOException {
        List<JobOffer> offers = parser.parse(loadJson());

        JobOffer first = offers.getFirst();

        assertThat(first.getId()).isEqualTo("arbeitsagentur:10000-1111111111-S");
        assertThat(first.getTitel()).isEqualTo("Ausbildung zum Fachinformatiker für Anwendungsentwicklung (m/w/d)");
        assertThat(first.getFirma()).isEqualTo("Beispiel GmbH");
        assertThat(first.getOrt()).isEqualTo("Dortmund");
        assertThat(first.getLink()).isEqualTo("https://www.arbeitsagentur.de/jobsuche/jobdetail/10000-1111111111-S");
        assertThat(first.getVeroeffentlichtAm()).isEqualTo(LocalDate.of(2026,10,15));
        assertThat(first.getQuelle()).isEqualTo("Arbeitsagentur");
    }

    @Test
    void setsDateToNullWhenMissing() throws IOException {
        List<JobOffer> offers = parser.parse(loadJson());

        JobOffer second = offers.get(1);

        assertThat(second.getVeroeffentlichtAm()).isNull();
    }

    @Test
    void usesEmptyLocationWhenListIsEmpty() throws IOException {
        List<JobOffer> offers = parser.parse(loadJson());

        JobOffer third = offers.get(2);

        assertThat(third.getOrt()).isEmpty();
    }

    @Test
    void returnsEmptyListWhenResultListIsEmpty() throws JsonProcessingException {
        List<JobOffer> offers = parser.parse("{\"ergebnisliste\":[]}");

        assertThat(offers).isEmpty();
    }

    @Test
    void returnsEmptyListWhenResultListIsMissing() throws JsonProcessingException {
        List<JobOffer> offers = parser.parse("{}");

        assertThat(offers).isEmpty();
    }
}