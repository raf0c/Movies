package com.example.raf0c.movies.bean;

/**
 * Created by raf0c on 16/08/15.
 */
public class ImageItem {

    private String url;
    private String title;
    private String synopsis;

    public ImageItem(String url, String title, String synopsis) {
        this.url = url;
        this.title = title;
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
