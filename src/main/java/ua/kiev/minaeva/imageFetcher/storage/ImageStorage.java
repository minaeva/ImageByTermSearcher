package ua.kiev.minaeva.imageFetcher.storage;

import ua.kiev.minaeva.imageFetcher.entity.Image;
import ua.kiev.minaeva.imageFetcher.entity.ImageShort;
import ua.kiev.minaeva.imageFetcher.exception.ImageFetcherException;

import java.util.HashSet;
import java.util.Set;

public class ImageStorage {

    private static ImageStorage instance;
    private Set<Image> images;

    private ImageStorage() {
        images = new HashSet<>();
    }

    public static synchronized ImageStorage getInstance() {
        if (instance == null) {
            instance = new ImageStorage();
        }
        return instance;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void addImage(Image photo) throws Exception {
        if (images.contains(photo)) {
            throw new ImageFetcherException("Photo " + photo + " is already in the storage");
        }
        images.add(photo);
    }

}
