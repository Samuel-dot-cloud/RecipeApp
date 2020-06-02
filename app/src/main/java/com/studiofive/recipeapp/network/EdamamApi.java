package com.studiofive.recipeapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EdamamApi {

    //getting searched recipe
    @GET("search/")
    Call<EdamamRecipesSearchResponse> getQ(
            @Query("q") String recipe

    );
}
