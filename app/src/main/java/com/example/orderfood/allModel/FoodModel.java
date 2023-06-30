package com.example.orderfood.allModel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FoodModel implements Serializable {
    private String idFood;
    private String nameFood;
    private String descriptionFood;
    private float priceFood;
    private float saleFood;
    private String imageFood;
    private boolean popularFood;
    private String imageBanner;
    private String imageOther;

    public FoodModel() {
    }

    public FoodModel(String idFood, String nameFood, String descriptionFood, float priceFood, float saleFood, String imageFood, boolean popularFood, String imageBanner, String imageOther) {
        this.idFood=idFood;
        this.nameFood = nameFood;
        this.descriptionFood = descriptionFood;
        this.priceFood = priceFood;
        this.saleFood = saleFood;
        this.imageFood = imageFood;
        this.popularFood = popularFood;
        this.imageBanner = imageBanner;
        this.imageOther = imageOther;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getDescriptionFood() {
        return descriptionFood;
    }

    public void setDescriptionFood(String descriptionFood) {
        this.descriptionFood = descriptionFood;
    }

    public float getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(float priceFood) {
        this.priceFood = priceFood;
    }

    public float getSaleFood() {
        return saleFood;
    }

    public void setSaleFood(float saleFood) {
        this.saleFood = saleFood;
    }

    public String getImageFood() {
        return imageFood;
    }

    public void setImageFood(String imageFood) {
        this.imageFood = imageFood;
    }

    public boolean isPopularFood() {
        return popularFood;
    }

    public void setPopularFood(boolean popularFood) {
        this.popularFood = popularFood;
    }

    public String getImageBanner() {
        return imageBanner;
    }

    public void setImageBanner(String imageBanner) {
        this.imageBanner = imageBanner;
    }

    public String getImageOther() {
        return imageOther;
    }

    public void setImageOther(String imageOther) {
        this.imageOther = imageOther;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idFood", idFood);
        result.put("nameFood", nameFood);
        result.put("descriptionFood", descriptionFood);
        result.put("priceFood", priceFood);
        result.put("saleFood", saleFood);
        result.put("imageFood", imageFood);
        result.put("popularFood", popularFood);
        result.put("imageBanner", imageBanner);
        result.put("imageOther", imageOther);
        return result;
    }
}

