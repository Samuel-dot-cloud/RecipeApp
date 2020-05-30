package com.studiofive.recipeapp;

import java.util.ArrayList;

public class Recipe {
    private String mLabel;
    private String mSource;
    private String mUri;
    private String mImage;
    private String mUrl;
    private String mShareAs;
    private ArrayList<String> mIngredientLines = new ArrayList<>();
//    private ArrayList<String> mIngredients = new ArrayList<>();
    private double mCalories;
    private double mTotalTime;

    public Recipe(String label, String source, String uri, String image, String url, String shareAs, ArrayList<String> ingredientLines,  double calories, double totalTime){
        this.mLabel = label;
        this.mSource = source;
        this.mUri = uri;
        this.mImage = image;
        this.mUrl = url;
        this.mShareAs = shareAs;
        this.mIngredientLines = ingredientLines;
        this.mCalories = calories;
        this.mTotalTime = totalTime;
    }

    public String getmLabel(){
        return mLabel;
    }

    public String getmSource(){
        return mSource;
    }

    public String getmUri(){
        return mUri;
    }

    public String getmImage(){
        return mImage;
    }

    public String getmUrl(){
        return mUrl;
    }

    public String getmShareAs(){
        return mShareAs;
    }

    public ArrayList<String> getmIngredientLines(){
        return mIngredientLines;
    }

    public double getmCalories(){
        return mCalories;
    }

    public double getmTotalTime(){
        return mTotalTime;
    }
}
