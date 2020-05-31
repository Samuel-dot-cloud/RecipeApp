package network;

import com.studiofive.recipeapp.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import models.Recipe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EdamamService {

    public static void findRecipes(String recipe, Callback callback){

        //create a new client to create and send requests
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

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

    //parse JSON data from API response
    public ArrayList<Recipe> processResults(Response response){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject edamamJSON = new JSONObject(jsonData);
            JSONArray recipesJSON = edamamJSON.getJSONArray("recipe");

            if (response.isSuccessful()){
                for (int i = 0; i < recipesJSON.length(); i++) {
                    JSONObject outputJSON = recipesJSON.getJSONObject(i);
                    String name = outputJSON.getString("label");
                    String source = outputJSON.getString("source");
                    String uri = outputJSON.getString("uri");
                    String image = outputJSON.getString("image");
                    String url = outputJSON.getString("url");
                    String shareAs = outputJSON.getString("shareAs");
                    double calories = outputJSON.getDouble("calories");
                    double totalTime = outputJSON.getDouble("totalTime");

                    ArrayList<String> ingredientLines = new ArrayList<>();
                    JSONArray ingredientsJSON = outputJSON.getJSONArray("ingredients");
                    for (int y = 0; y < ingredientsJSON.length(); y++){
                        ingredientLines.add(ingredientsJSON.getJSONObject(y).getString("text"));
                    }

                    Recipe recipe = new Recipe(name, source, uri, image, url, shareAs, calories, totalTime, ingredientLines);
                    recipes.add(recipe);

                }
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return recipes;
    }
}
