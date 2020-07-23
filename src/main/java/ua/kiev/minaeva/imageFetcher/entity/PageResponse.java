package ua.kiev.minaeva.imageFetcher.entity;

import lombok.Data;

import java.util.Set;

@Data
public class PageResponse {

    private Set<ImageShort> pictures;
    private int page;
    private int pageCount;
    private boolean hasMore;
}
