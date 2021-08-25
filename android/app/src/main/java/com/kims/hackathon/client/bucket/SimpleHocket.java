package com.kims.hackathon.client.bucket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleHocket {
    private Long id;

    private String title;

    private LocalDateTime targetDate;

    private int likes;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시");

    public String getTitle() {
        return title;
    }
    public Long getId() {
        return id;
    }
    public int getLikes() {
        return likes;
    }
    public LocalDateTime getTargetDate() {
        return targetDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setTargetDate(LocalDateTime targetDate) {
        this.targetDate = targetDate;
    }

    public String getDateAsString() {
        return targetDate.format(dateTimeFormatter);
    }
}
