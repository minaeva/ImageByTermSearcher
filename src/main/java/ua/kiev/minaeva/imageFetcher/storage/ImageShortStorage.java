package ua.kiev.minaeva.imageFetcher.storage;

import ua.kiev.minaeva.imageFetcher.entity.ImageShort;
import ua.kiev.minaeva.imageFetcher.exception.ImageFetcherException;

import java.util.HashSet;
import java.util.Set;

public class ImageShortStorage {

    private static ImageShortStorage instance;
    private Set<ImageShort> images;

    private ImageShortStorage() {
        images = new HashSet<>();
    }

    public static synchronized ImageShortStorage getInstance() {
        if (instance == null) {
            instance = new ImageShortStorage();
        }
        return instance;
    }

    public Set<ImageShort> getImages() {
        return images;
    }

    public void addImage(ImageShort photo) throws Exception {
        if (images.contains(photo)) {
            throw new ImageFetcherException("Photo " + photo + " is already in the storage");
        }
        images.add(photo);
    }
}
