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

private static EdamamApi api;
public static EdamamApi EdamamInstance(){
    if (api == null){
        api = EdamamClient.getClient().create(EdamamApi.class);
    }
    return api;
}
}
