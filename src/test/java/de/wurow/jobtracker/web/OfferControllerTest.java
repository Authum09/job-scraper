package de.wurow.jobtracker.web;

import de.wurow.jobtracker.JobOffer;
import de.wurow.jobtracker.JobOfferRepository;
import de.wurow.jobtracker.OfferStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OfferController.class)
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobOfferRepository jobOfferRepository;


    @Test
    void returnsEmptyListWhenNoOfferExist() throws Exception {
        mockMvc.perform(get("/api/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void returnsOfferById() throws Exception {
        JobOffer offer = new JobOffer("arbeitsagentur:1", "Testtitel", "Testfirma", "Testort", "https://google.com", LocalDate.of(2026, 1, 1), "Testquelle");

        String id = "arbeitsagentur:1";

        when(jobOfferRepository.findById(id)).thenReturn(Optional.of(offer));

        mockMvc.perform(get("/api/offers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(offer.getId()))
                .andExpect(jsonPath("$.titel").value(offer.getTitel()));
    }

    @Test
    void returns404WhenNotFound() throws Exception {
        String id = "gibt-es-nicht";

        when(jobOfferRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/offers/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Angebot gibt-es-nicht nicht gefunden"));
    }

    @Test
    void filtersOffersByStatus() throws Exception {
        JobOffer offer = new JobOffer("arbeitsagentur:1", "Testtitel", "Testfirma", "Testort", "https://google.com", LocalDate.of(2026, 1, 1), "Testquelle");

        when(jobOfferRepository.findByStatus(OfferStatus.NEU)).thenReturn(List.of(offer));

        mockMvc.perform(get("/api/offers?status=NEU"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].status").value("NEU"));
    }

    @Test
    void rejectInvalidStatusParameter() throws Exception {
        mockMvc.perform(get("/api/offers?status=QUATSCH"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void updateStatusWithPatch() throws Exception {

        String id = "arbeitsagentur:1";
        JobOffer offer = new JobOffer(id, "Testtitel", "Testfirma", "Testort", "https://google.com", LocalDate.of(2026, 1, 1), "Testquelle");

        when(jobOfferRepository.findById(id)).thenReturn(Optional.of(offer));
        when(jobOfferRepository.save(any())).thenReturn(offer);

        mockMvc.perform(patch("/api/offers/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"INTERESSANT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("INTERESSANT"));
    }
}