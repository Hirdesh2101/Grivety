package com.example.grivety;

import java.util.List;

public class comment2 {

    String comment;
    String fullname;
    String Image;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public comment2(String comment, String fullname, String image) {
        this.comment = comment;
        this.fullname = fullname;
        Image = image;
    }
}
