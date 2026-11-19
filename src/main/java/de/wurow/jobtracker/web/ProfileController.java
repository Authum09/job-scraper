package de.wurow.jobtracker.web;

import de.wurow.jobtracker.SearchProfile;
import de.wurow.jobtracker.SearchProfileRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final SearchProfileRepository searchProfileRepository;

    public ProfileController(SearchProfileRepository searchProfileRepository) {
        this.searchProfileRepository = searchProfileRepository;
    }

    @GetMapping
    public List<ProfileResponse> getProfiles() {
        return searchProfileRepository.findAll()
                .stream()
                .map(ProfileResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ProfileResponse getProfile(@PathVariable Long id) {
        return searchProfileRepository.findById(id)
                .map(ProfileResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse create(@Valid @RequestBody ProfileRequest request) {
        SearchProfile saved = searchProfileRepository.save(request.toEntity());
        return ProfileResponse.from(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!searchProfileRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        searchProfileRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ProfileResponse update (@PathVariable Long id, @Valid @RequestBody ProfileRequest request) {
        SearchProfile profile = searchProfileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        profile.update(request.berufsbezeichnung(), request.ort(), request.umkreis(), request.angebotsart(), request.pflicht(), request.ausschluss());

        return ProfileResponse.from(searchProfileRepository.save(profile));
    }
}
