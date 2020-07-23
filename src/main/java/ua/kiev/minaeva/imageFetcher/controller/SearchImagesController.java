package ua.kiev.minaeva.imageFetcher.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.kiev.minaeva.imageFetcher.entity.Image;
import ua.kiev.minaeva.imageFetcher.exception.ImageFetcherException;
import ua.kiev.minaeva.imageFetcher.service.ImageService;

import java.util.Set;

@RestController
@AllArgsConstructor
@Log
public class SearchImagesController {

    private final ImageService imageService;

    @GetMapping("/search/{searchTerm}")
    public Set<Image> searchByTerm(@PathVariable final String searchTerm) throws ImageFetcherException {
        log.info("handling SEARCH BY TERM " + searchTerm + " request");
        return imageService.searchByTerm(searchTerm);
    }

}

