package com.studiofive.recipeapp.network;

import com.studiofive.recipeapp.Constants;
import com.studiofive.recipeapp.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class EdamamService {

    public static void findRecipes(String recipe, Callback callback) {

        //new OkHttpClient to create and send request
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

//Tying request to consumer SignPost object responsible for signature
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.EDAMAM_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.SEARCH, recipe);
        urlBuilder.addQueryParameter("app_id", Constants.EDAMAM_APP_ID);
        urlBuilder.addQueryParameter("app_key", Constants.EDAMAM_APP_KEY);
        urlBuilder.addQueryParameter("from", Constants.FROM);
        urlBuilder.addQueryParameter("to", Constants.TO);
        urlBuilder.addQueryParameter("calories", Constants.CALORIES);
        urlBuilder.addQueryParameter("health", Constants.HEALTH);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Recipe> processResults(Response response){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject edamamJSON = new JSONObject(jsonData);
            JSONArray recipeJSON = edamamJSON.getJSONArray("hits");
            if (response.isSuccessful()) {
                for (int i = 0; i < recipeJSON.length(); i++) {
                    JSONObject recipesJSON = recipeJSON.getJSONObject(i).optJSONObject("recipe");
                    String label = recipesJSON.getString("label");
                    String source = recipesJSON.getString("source");
                    String uri = recipesJSON.getString("uri");
                    String image = recipesJSON.getString("image");
                    String url = recipesJSON.getString("url");
                    String shareAs = recipesJSON.getString("shareAs");
                    double totalTime = recipesJSON.getDouble("totalTime");
                    double calories = recipesJSON.getDouble("calories");

                    ArrayList<String> ingredientLines = new ArrayList<>();
                    JSONArray ingredientLinesJSON = recipesJSON.getJSONArray("ingredients");
                    for (int y = 0; y < ingredientLinesJSON.length(); y++) {
                        ingredientLines.add(ingredientLinesJSON.getJSONObject(y).getString("text"));
                    }


                    Recipe recipe = new Recipe(label, source, uri, image, url, shareAs, ingredientLines, calories, totalTime);
                    recipes.add(recipe);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

}

