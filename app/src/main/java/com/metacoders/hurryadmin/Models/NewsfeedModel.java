package com.metacoders.hurryadmin.Models;

public class NewsfeedModel {
    String title , image , desc , timestamp , post_id ;

    public NewsfeedModel() {
    }

    public NewsfeedModel(String title, String image, String desc, String timestamp, String post_id) {
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.timestamp = timestamp;
        this.post_id = post_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
