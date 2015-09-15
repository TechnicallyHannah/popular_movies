package com.example.a712948.popularmovies.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * @author Hannah Paulson
 * @since 9/10/15.
 */
public class RestClient {
    private static ApiService apiService;
    private static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";

    static {
        setupRestClient();
    }

    private RestClient() {}

    public static ApiService get() {
        return apiService;
    }

    private static void setupRestClient() {

        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();
        apiService = restAdapter.create(ApiService.class);

    }
}