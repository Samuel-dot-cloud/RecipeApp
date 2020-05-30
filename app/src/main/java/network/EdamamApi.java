package com.studiofive.recipeapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EdamamApi {

    @GET("/search")
    Call<EdamamRecipesSearchResponse> getRecipes(
            @Query("q") String query
    );
}
