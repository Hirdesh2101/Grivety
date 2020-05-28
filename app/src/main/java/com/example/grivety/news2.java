package com.example.grivety;

public class news2 {

    String news;
    String imageurl;

    public news2(String news, String imageurl) {
        this.news = news;
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}
