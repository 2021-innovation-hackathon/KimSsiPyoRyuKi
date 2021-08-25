package com.kims.hackathon.client.account;

public class Account {
    private Long id;
    private String nickname;
    private String email;
    private String name;
    private String gender;
    private Integer age;
    private String profileImage;

    public Integer getAge() {
        return age;
    }
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }
    public String getName() {
        return name;
    }
    public String getNickname() {
        return nickname;
    }
    public String getProfileImage() {
        return profileImage;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
