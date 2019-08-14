package com.wolf.blogapp.Model;

public class Blog {
    public String title;
    public String desc;
    public String timestamp;
    public String userId;
    public String image;

    public Blog () {

    }

    public Blog(String title, String desc, String timestamp, String userId, String image) {
        this.title = title;
        this.desc = desc;
        this.timestamp = timestamp;
        this.userId = userId;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
