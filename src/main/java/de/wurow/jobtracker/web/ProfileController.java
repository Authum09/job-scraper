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
}
