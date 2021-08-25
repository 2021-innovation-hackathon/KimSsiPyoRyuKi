package com.kims.hackathon.client.bucket;

import java.time.LocalDateTime;

public class Hocket {

    private String title;

    private String description;

    private String location;

    private byte[] thumbnailImage;

    private int perWeeks;

    private boolean isPublic;

    private boolean isAchieved;

    private boolean isRequireDate;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;


    public byte[] getThumbnailImage() {
        return thumbnailImage;
    }
    public int getPerWeeks() {
        return perWeeks;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public LocalDateTime getStartDateTime() {
        return startDateTime;
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
    public boolean isAchieved() {
        return isAchieved;
    }
    public boolean isPublic() {
        return isPublic;
    }
    public boolean isRequireDate() {
        return isRequireDate;
    }

    public void setAchieved(boolean achieved) {
        isAchieved = achieved;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setPerWeeks(int perWeeks) {
        this.perWeeks = perWeeks;
    }
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    public void setRequireDate(boolean isRequireDate) {
        this.isRequireDate = isRequireDate;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public void setThumbnailImage(byte[] thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
