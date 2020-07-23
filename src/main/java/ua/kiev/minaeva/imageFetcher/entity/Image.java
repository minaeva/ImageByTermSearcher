package ua.kiev.minaeva.imageFetcher.entity;

import lombok.Data;

@Data
public class Image {

    private String id;
    private String author;
    private String camera;
    private String tags;
    private String cropped_picture;
    private String full_picture;

}
