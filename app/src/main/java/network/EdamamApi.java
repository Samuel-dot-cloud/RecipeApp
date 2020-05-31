package network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EdamamApi {

    //getting searched recipe
    @GET("")
    Call<EdamamRecipesSearchResponse> getQ(
            @Query("q") String recipe

    );
}
