package ua.kiev.minaeva.imageFetcher.service;

import ua.kiev.minaeva.imageFetcher.entity.Image;
import ua.kiev.minaeva.imageFetcher.exception.ImageFetcherException;

import java.util.Set;

public interface ImageService {

    Set<Image> searchByTerm(String term) throws ImageFetcherException;

}
