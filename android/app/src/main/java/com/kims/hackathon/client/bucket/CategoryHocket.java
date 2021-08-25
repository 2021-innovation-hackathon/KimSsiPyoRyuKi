package com.kims.hackathon.client.bucket;

public class CategoryHocket {
    private String imageUrl;
    private String title;
    private int likes;

    public String getTitle() {
        return title;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
