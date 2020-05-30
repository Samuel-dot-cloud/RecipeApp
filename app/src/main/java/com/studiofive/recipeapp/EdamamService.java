package com.studiofive.recipeapp;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class EdamamService {

    public static void findRecipes(String recipe, Callback callback){

        //create a new client to create and send requests
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.EDAMAM_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("sushi", Constants.SEARCH);
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
}
