package ua.kiev.minaeva.imageFetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ImageFetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageFetcherApplication.class, args);
	}

}
