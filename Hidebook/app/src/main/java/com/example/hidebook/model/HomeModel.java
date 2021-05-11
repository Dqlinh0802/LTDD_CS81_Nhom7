package com.example.hidebook.model;

public class HomeModel {

    private String userName, timetamp, profileImage, postImage, uid;
    private int likeCount;

    public HomeModel() {
    }

    public HomeModel(String userName, String timetamp, String profileImage, String postImage, String uid, int likeCount) {
        this.userName = userName;
        this.timetamp = timetamp;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.uid = uid;
        this.likeCount = likeCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimetamp() {
        return timetamp;
    }

    public void setTimetamp(String timetamp) {
        this.timetamp = timetamp;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
