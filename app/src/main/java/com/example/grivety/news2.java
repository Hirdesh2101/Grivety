package com.example.grivety;

public class news2 {

    String news;
    String imageurl;
    String data;

    public news2(String news, String imageurl,String data) {
        this.news = news;
        this.imageurl = imageurl;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
