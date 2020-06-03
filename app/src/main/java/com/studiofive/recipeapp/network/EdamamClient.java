package com.studiofive.recipeapp.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.studiofive.recipeapp.Constants.EDAMAM_BASE_URL;

public class EdamamClient {

    private static Retrofit retrofit = null;

    private EdamamApi edamamApi = null;
    private Context context;

    public EdamamClient(Context context){
        this.context = context;
    }

    public static synchronized Retrofit getClient(){

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

                  HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
                          logger.level(HttpLoggingInterceptor.Level.BODY);
                          okHttpClient.addInterceptor(interceptor).addInterceptor(logger);
            //appends base url to endpoints described
            retrofit = new Retrofit.Builder()
                    .baseUrl(EDAMAM_BASE_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
