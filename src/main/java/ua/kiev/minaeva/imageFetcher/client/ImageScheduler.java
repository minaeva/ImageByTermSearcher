package ua.kiev.minaeva.imageFetcher.client;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.kiev.minaeva.imageFetcher.controller.AuthRequest;
import ua.kiev.minaeva.imageFetcher.exception.ImageFetcherException;

@Service
@Log
public class ImageScheduler {

    @Autowired
    private ImageClient imageClient;

    @Value("${auth.key}")
    private String apiKey;

    public String getTokenByApiKey() throws ImageFetcherException {
        log.info("PhotoScheduler: handling GET TOKEN BY API KEY request ");
        AuthRequest request = new AuthRequest(apiKey);
        return imageClient.getResponseByApiKey(request).getToken();
    }

}
