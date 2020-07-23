package ua.kiev.minaeva.imageFetcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.kiev.minaeva.imageFetcher.client.ImageScheduler;
import ua.kiev.minaeva.imageFetcher.entity.Image;
import ua.kiev.minaeva.imageFetcher.entity.ImageShort;
import ua.kiev.minaeva.imageFetcher.entity.PageResponse;
import ua.kiev.minaeva.imageFetcher.exception.ImageFetcherException;
import ua.kiev.minaeva.imageFetcher.service.ImageService;
import ua.kiev.minaeva.imageFetcher.storage.ImageShortStorage;
import ua.kiev.minaeva.imageFetcher.storage.ImageStorage;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageScheduler imageScheduler;

    @Value("${auth.url}")
    private String rootUrl;

    public Set<Image> searchByTerm(String term) throws ImageFetcherException {
        getAllImages();

        ImageStorage storedFull = ImageStorage.getInstance();

        Predicate<Image> imagePredicate = image ->
                (image.getAuthor() != null && image.getAuthor().toLowerCase().contains(term.toLowerCase())) ||
                        (image.getTags() != null && image.getTags().toLowerCase().contains(term.toLowerCase())) ||
                        (image.getCamera() != null && image.getCamera().toLowerCase().contains(term.toLowerCase()));

        return storedFull.getImages().stream()
                .filter(imagePredicate)
                .collect(Collectors.toSet());
    }

    @Scheduled(fixedRateString = "${cache.time}")
    private void getAllImages() throws ImageFetcherException {
        HttpEntity<String> request = getRequestWithToken();
        getAllImageShorts(request);
        getAllImageFulls(request);
    }

    private HttpEntity<String> getRequestWithToken() throws ImageFetcherException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = imageScheduler.getTokenByApiKey();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    private void getAllImageShorts(HttpEntity<String> request) throws ImageFetcherException {
        int i = 0;
        while (getByPage(i++, request));
    }

    private boolean getByPage(int pageNumber, HttpEntity<String> request) throws ImageFetcherException {
        RestTemplate restTemplate = new RestTemplate();
        String pageUrl = rootUrl + "/images?page=" + pageNumber;

        try {
            ResponseEntity<PageResponse> response = restTemplate.exchange(pageUrl, HttpMethod.GET,
                    request, PageResponse.class);
            Set<ImageShort> imageShorts = response.getBody().getPictures();
            ImageShortStorage stored = ImageShortStorage.getInstance();
            stored.getImages().addAll(imageShorts);
            return response.getBody().isHasMore();
        } catch (Exception e) {
            throw new ImageFetcherException("token is not valid");
        }
    }

    private void getAllImageFulls(HttpEntity<String> request) throws ImageFetcherException {
        Set<ImageShort> imageShorts = ImageShortStorage.getInstance().getImages();
        for (ImageShort imageShort : imageShorts) {
            String id = imageShort.getId();
            getById(id, request);
        }
    }

    private void getById(String id, HttpEntity<String> request) throws ImageFetcherException {
        RestTemplate restTemplate = new RestTemplate();
        String pageUrl = rootUrl + "/images/" + id;

        try {
            ResponseEntity<Image> response = restTemplate.exchange(pageUrl, HttpMethod.GET, request, Image.class);
            Image image = response.getBody();
            ImageStorage storedFull = ImageStorage.getInstance();
            storedFull.getImages().add(image);
        } catch (Exception e) {
            throw new ImageFetcherException("token is not valid");
        }
    }

}
