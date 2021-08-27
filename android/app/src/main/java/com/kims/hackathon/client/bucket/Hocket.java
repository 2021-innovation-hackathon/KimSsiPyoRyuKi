package com.kims.hackathon.client.bucket;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hocket {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("location")
    private String location;

    @SerializedName("thumbnail_image")
    private byte[] thumbnailImage;

    @SerializedName("is_public")
    private boolean isPublic;

    @SerializedName("is_achieved")
    private boolean isAchieved;

    @SerializedName("is_require_date")
    private boolean isRequireDate;

    @SerializedName("category_titles")
    private List<String> categoryTitles = new ArrayList<>();

    @SerializedName("end_date")
    private LocalDate endDate;

    public byte[] getThumbnailImage() {
        return thumbnailImage;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public String getTitle() {
        return title;
    }
    public boolean isRequireDate() {
        return isRequireDate;
    }

    public List<String> getCategoryTitles() {
        return categoryTitles;
    }

    public boolean isAchieved() {
        return isAchieved;
    }
    public boolean isPublic() {
        return isPublic;
    }

    public void setAchieved(boolean achieved) {
        isAchieved = achieved;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    public void setThumbnailImage(byte[] thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setCategoryTitles(List<String> categoryTitles) {
        this.categoryTitles = categoryTitles;
    }
    public void setRequireDate(boolean requireDate) {
        isRequireDate = requireDate;
    }
}
