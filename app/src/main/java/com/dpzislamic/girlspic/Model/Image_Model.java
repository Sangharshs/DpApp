package com.dpzislamic.girlspic.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image_Model implements Serializable {
    @SerializedName("category")
    @Expose
    String category;
    @SerializedName("image")
    @Expose
    String image;
    @SerializedName("position")
    @Expose
    int position;

    public Image_Model(String category, String image, int position) {
        this.category = category;
        this.image = image;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public String setCategory(String category) {
        this.category = category;
        return category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Image_Model() {
        this.category = category;
        this.image = image;

    }



}
