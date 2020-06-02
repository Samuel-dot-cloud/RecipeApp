package models;

//import org.parceler.Parcel;

import java.util.ArrayList;

//@Parcel
public class Recipe {
    private String mLabel;
    private String mSource;
    private String mUri;
    private String mImage;
    private String mUrl;
    private String mShareAs;
    private ArrayList<String> mIngredientLines = new ArrayList<>();
//    private ArrayList<String> mHealthLabels = new ArrayList<>();
    private double mCalories;
    private double mTotalTime;

    public Recipe(String label, String source, String uri, String image, String url, String shareAs, ArrayList<String> ingredientLines, double calories, double totalTime){
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

    public Recipe() {
    }

    public Recipe(String name, String source, String uri, String image, String url, String shareAs, double calories, double totalTime, ArrayList<String> ingredientLines) {
    }

    public String getLabel(){
        return mLabel;
    }

    public String getSource(){
        return mSource;
    }

    public String getUri(){
        return mUri;
    }

    public String getImage(){
        return mImage;
    }

    public String getUrl(){
        return mUrl;
    }

    public String getShareAs(){
        return mShareAs;
    }

    public ArrayList<String> getIngredientLines(){
        return mIngredientLines;
    }

//    public ArrayList<String> getHealthLabels() {
//        return mHealthLabels;
//    }

    public double getCalories(){
        return mCalories;
    }

    public double getTotalTime(){
        return mTotalTime;
    }
}
