package com.studiofive.recipeapp.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.studiofive.recipeapp.Constants.EDAMAM_BASE_URL;

public class EdamamClient {

    private static Retrofit retrofit = null;

    public static EdamamApi getClient(){

        //intercepts each request and adds an HTTP authorization header
        if (retrofit == null){
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
                  Interceptor interceptor = new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    };

            //appends base url to endpoints described
            retrofit = new Retrofit.Builder()
                    .baseUrl(EDAMAM_BASE_URL)
//                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(EdamamApi.class);
    }
}
