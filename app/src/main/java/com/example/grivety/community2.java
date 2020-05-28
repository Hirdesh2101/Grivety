package com.example.grivety;

public class community2 {

    String question;
    String Fullname;
    String image;

    public community2(String question, String fullname, String image) {
        this.question = question;
        Fullname = fullname;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }


}
