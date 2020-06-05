package com.studiofive.recipeapp.network;

import com.studiofive.recipeapp.models.Hit;
import com.studiofive.recipeapp.models.Recipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EdamamApi {

    //getting searched recipe
    @GET("search")
    Call<EdamamRecipesSearchResponse> getRecipe(@Query("q") String recipe, @Query("app_id")String app_id, @Query("app_key")String app_key, @Query("from")String from, @Query("to") String to, @Query("calories")String calories, @Query("health")String health);
//    Call<EdamamRecipesSearchResponse> getQ(@Query("q") String recipe, @Query("app_id")String app_id, @Query("app_key")String app_key, @Query("from")String from, @Query("to") String to, @Query("calories")String calories, @Query("health")String health);
}
