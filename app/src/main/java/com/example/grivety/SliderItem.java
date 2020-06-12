package com.example.grivety;

class SliderItem {
    String Description;
    String ImageUrl;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public SliderItem(String description, String imageUrl) {
        Description = description;
        ImageUrl = imageUrl;
    }
}
