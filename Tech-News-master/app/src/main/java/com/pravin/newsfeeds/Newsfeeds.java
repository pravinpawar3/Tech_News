package com.pravin.newsfeeds;

/**
 * Created by pravin on 12/6/17.
 */

public class Newsfeeds {
    private String Description;
    private String Image;
    private String Title;
    private String Topic;

    public Newsfeeds() {

    }

    public Newsfeeds(String description, String image, String title, String topic) {
        Description = description;
        Image = image;
        Title = title;
        Topic = topic;
    }


    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
