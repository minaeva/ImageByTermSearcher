package ua.kiev.minaeva.imageFetcher.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.kiev.minaeva.imageFetcher.controller.AuthRequest;
import ua.kiev.minaeva.imageFetcher.controller.AuthResponse;
import ua.kiev.minaeva.imageFetcher.exception.ImageFetcherException;

@Component
public class ImageClient {

    @Value("${auth.url}")
    private String root;

    public AuthResponse getResponseByApiKey(AuthRequest authRequest) throws ImageFetcherException {

        String authUrl = root + "/auth";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthRequest> request = new HttpEntity<>(authRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(authUrl, request, AuthResponse.class);

        if (response.getBody() != null && response.getBody().isAuth()) {
            return response.getBody();
        } else {
            throw new ImageFetcherException("apiKey is not valid");
        }
    }

}
