package com.studiofive.recipeapp.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Recipe {
     String label;
     String source;
     String uri;
     String image;
     String url;
     String shareAs;
     List<String> ingredientLines = new ArrayList<>();
//    private ArrayList<String> mHealthLabels = new ArrayList<>();
     double calories;
     double totalTime;
    private String pushId;

    public Recipe(String label, String source, String uri, String image, String url, String shareAs, ArrayList<String> ingredientLines, double calories, double totalTime){
        this.label = label;
        this.source = source;
        this.uri = uri;
        this.image = image;
        this.url = url;
        this.shareAs = shareAs;
        this.ingredientLines = ingredientLines;
        this.calories = calories;
        this.totalTime = totalTime;
    }

    public Recipe() {
    }

    public String getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public String getUri() {
        return uri;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getShareAs() {
        return shareAs;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public double getCalories() {
        return calories;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
